#!/usr/bin/env python3
"""Prepare lookup-only binary dependencies for the recovered core Java sources.

The converted classfiles are compiler inputs only. Runtime SDK, stream and other
unrecovered classes must come from original DEX, never from this compile view.
"""

from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
import urllib.request
import zipfile

import make_core_compile_view
from android_build_tools import jdk_tool


APK_SHA256 = '34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b'
DEX_SHA256 = '3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4'
TOOL_URL = 'https://github.com/pxb1988/dex2jar/releases/download/v2.4/dex-tools-v2.4.zip'
TOOL_SHA256 = 'ee7c45eb3c1d2474a6145d8d447e651a736a22d9664b6d3d3be5a5a817dda23a'
TOOL_VERSION = '2.4'
CLASS_COUNT = 6383
CORE_SOURCE_COUNT = 108
ADDED_CORE_SOURCES = frozenset({
    'GameSaveStore', 'ProgressSaveController', 'PendingRewardState', 'EntityPoseState',
    'AsyncSaveQueue', 'SaveBatchPolicy', 'LanguageRestartController',
    'TutorialFlowController', 'MoreGamesRemoval',
})
CORE_PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480/'
RESOURCE_CLASSES = (
    'com/gameloft/android/ANMP/GloftGF2F/R', 'com/facebook/R',
    'com/google/android/gms/ads/R', 'com/google/android/gms/ads/impl/R',
    'com/google/firebase/messaging/R',
)
CONVERSION_ERROR = re.compile(r'\b(?:errors?|exceptions?|failed|failure)\b|[\w.$]+(?:Exception|Error)\b', re.I)


def file_sha256(path):
    digest = hashlib.sha256()
    with Path(path).open('rb') as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b''):
            digest.update(block)
    return digest.hexdigest()


def verify_pins(metadata):
    """Do not silently accept a changed game/tool version through metadata."""
    info = json.loads(Path(metadata).read_text())
    if (info.get('original_apk_sha256'), info.get('classes_dex_sha256'),
            info.get('dex2jar_class_count')) != (APK_SHA256, DEX_SHA256, CLASS_COUNT):
        raise ValueError('Bytecode metadata differs from the reviewed original APK pins')
    tools = [tool for tool in info.get('tools', []) if tool.get('name') == 'dex2jar']
    if len(tools) != 1 or (tools[0].get('version'), tools[0].get('source'),
                           tools[0].get('sha256')) != (TOOL_VERSION, TOOL_URL, TOOL_SHA256):
        raise ValueError('dex2jar metadata differs from the reviewed release pin')


def verify_original(apk, expected_apk=APK_SHA256, expected_dex=DEX_SHA256):
    if file_sha256(apk) != expected_apk:
        raise ValueError('Original APK SHA-256 differs from the reviewed release')
    with zipfile.ZipFile(apk) as archive:
        names = archive.namelist()
        if len(names) != len(set(names)):
            raise ValueError('Original APK contains duplicate entries')
        dex_files = [name for name in names if re.fullmatch(r'classes(?:[2-9]|[1-9][0-9]+)?\.dex', name)]
        if dex_files != ['classes.dex']:
            raise ValueError('Expected exactly the reviewed single classes.dex')
        digest = hashlib.sha256(archive.read('classes.dex')).hexdigest()
        if digest != expected_dex:
            raise ValueError('Original classes.dex SHA-256 differs from the reviewed release')
    return digest


def verify_tool_archive(path):
    if file_sha256(path) != TOOL_SHA256:
        raise ValueError('dex2jar release archive SHA-256 mismatch')


def ensure_tool(cache, supplied_archive=None):
    """Use the pinned archive, with normal HTTPS proxy/CA verification on fetch."""
    cache = Path(cache)
    cache.mkdir(parents=True, exist_ok=True)
    archive = cache / 'dex-tools-v2.4.zip'
    if supplied_archive is not None:
        supplied_archive = Path(supplied_archive)
        verify_tool_archive(supplied_archive)
        if supplied_archive.resolve() != archive.resolve():
            with tempfile.NamedTemporaryFile(prefix='dex-tools-', suffix='.zip', dir=cache, delete=False) as stream:
                pending = Path(stream.name)
            try:
                shutil.copyfile(supplied_archive, pending)
                verify_tool_archive(pending)
                pending.replace(archive)
            finally:
                pending.unlink(missing_ok=True)
    if not archive.exists():
        with tempfile.NamedTemporaryFile(prefix='dex-tools-', suffix='.zip', dir=cache, delete=False) as stream:
            pending = Path(stream.name)
        try:
            # urllib inherits HTTPS_PROXY and the configured trusted CA bundle.
            # No unverified SSL context, mirror, or checksum override is used.
            with urllib.request.urlopen(TOOL_URL, timeout=120) as response, pending.open('wb') as output:
                shutil.copyfileobj(response, output)
            verify_tool_archive(pending)
            pending.replace(archive)
        finally:
            pending.unlink(missing_ok=True)
    verify_tool_archive(archive)

    # Extract from the verified archive each time; do not trust mutable extracted
    # executables/jars left by an earlier task. Execute Java directly, not a shell.
    tool_dir = cache / 'dex-tools-v2.4'
    with tempfile.TemporaryDirectory(prefix='dex-tools-extract-', dir=cache) as temp:
        temporary = Path(temp)
        with zipfile.ZipFile(archive) as source:
            for entry in source.infolist():
                target = (temporary / entry.filename).resolve()
                if not target.is_relative_to(temporary.resolve()):
                    raise ValueError('Unsafe path in pinned dex2jar archive')
            source.extractall(temporary)
        extracted = temporary / 'dex-tools-v2.4'
        if not (extracted / 'lib/dex-tools-v2.4.jar').is_file():
            raise ValueError('Pinned dex2jar archive has an unexpected layout')
        if tool_dir.exists():
            shutil.rmtree(tool_dir)
        extracted.replace(tool_dir)
    return tool_dir


def validate_conversion(jar, log, expected_count=CLASS_COUNT):
    if CONVERSION_ERROR.search(log):
        raise ValueError('dex2jar reported conversion errors; inspect conversion.log')
    with zipfile.ZipFile(jar) as archive:
        names = archive.namelist()
        if len(names) != len(set(names)):
            raise ValueError('Converted jar contains duplicate entries')
        count = sum(name.endswith('.class') for name in names)
        if count != expected_count:
            raise ValueError(f'Converted class count {count} differs from expected {expected_count}')
    return count


def convert_original(apk, tool_dir, cache, java):
    converted = cache / 'greenfarm-original.jar'
    report_file = cache / 'greenfarm-original.conversion.json'
    log_file = cache / 'conversion.log'
    if converted.exists() and report_file.exists() and log_file.exists():
        report = json.loads(report_file.read_text())
        expected = (APK_SHA256, DEX_SHA256, TOOL_SHA256, file_sha256(converted))
        actual = tuple(report.get(key) for key in
                       ('original_apk_sha256', 'original_dex_sha256', 'tool_archive_sha256', 'converted_jar_sha256'))
        if actual != expected:
            raise ValueError('Cached conversion provenance/checksum differs; remove the invalid cache to reconvert')
        validate_conversion(converted, log_file.read_text())
        return converted, report

    with tempfile.TemporaryDirectory(prefix='dex2jar-convert-', dir=cache) as temp:
        temporary = Path(temp)
        output = temporary / 'original.jar'
        command = [str(java), '-Xms512m', '-Xmx2048m', '-classpath', str(tool_dir / 'lib/*'),
                   'com.googlecode.dex2jar.tools.Dex2jarCmd', '-f', '-o', str(output), str(apk)]
        with log_file.open('w') as log:
            result = subprocess.run(command, cwd=temporary, stdout=log, stderr=subprocess.STDOUT)
        if result.returncode != 0:
            raise ValueError(f'dex2jar exited {result.returncode}; inspect {log_file}')
        if list(temporary.glob('*error*.zip')):
            raise ValueError('dex2jar produced a conversion-error archive')
        count = validate_conversion(output, log_file.read_text())
        digest = file_sha256(output)
        shutil.copyfile(output, converted)
    report = {'original_apk_sha256': APK_SHA256, 'original_dex_sha256': DEX_SHA256,
              'tool_url': TOOL_URL, 'tool_archive_sha256': TOOL_SHA256,
              'converted_jar_sha256': digest, 'converted_class_count': count,
              'conversion_exit': result.returncode, 'conversion_logged_errors': 0}
    report_file.write_text(json.dumps(report, indent=2) + '\n')
    return converted, report


def source_classes(source_dir, expected_count=CORE_SOURCE_COUNT):
    paths = sorted(Path(source_dir).glob('*.java'))
    # New progress helpers have no identity in the pinned original APK. Keep
    # the 108 recovered identities strict and exclude only reviewed additions
    # from this compile-only dependency filtering count.
    names = {CORE_PACKAGE + path.stem + '.class' for path in paths
             if path.stem != 'l' and path.stem not in ADDED_CORE_SOURCES}
    if CORE_PACKAGE + 'g.class' in names:
        raise ValueError('The original empty g class must remain a binary dependency')
    if len(names) != expected_count:
        raise ValueError(f'Expected {expected_count} recovered core sources, found {len(names)}')
    return names


def is_resource_class(name):
    return any(name == root + '.class' or (name.startswith(root + '$') and name.endswith('.class'))
               for root in RESOURCE_CLASSES)


def filter_dependencies(source, output, core_classes):
    """Exclude source-owned identities, keeping all other class bytes unchanged."""
    if Path(source).resolve() == Path(output).resolve():
        raise ValueError('Filtered output must not overwrite the original converted jar')
    removed = []
    with zipfile.ZipFile(source) as archive, zipfile.ZipFile(output, 'w') as result:
        names = archive.namelist()
        if len(names) != len(set(names)):
            raise ValueError('Converted jar contains duplicate entries')
        missing = sorted(core_classes - set(names))
        if missing:
            raise ValueError('Original jar lacks recovered core identities: ' + ', '.join(missing))
        for entry in archive.infolist():
            if entry.filename in core_classes or is_resource_class(entry.filename):
                removed.append(entry.filename)
            else:
                result.writestr(entry, archive.read(entry))
    return removed


def prepare(project_root, original=None, output_dir=None, cache=None, tool_archive=None, java=None):
    root = Path(project_root).resolve()
    original = Path(original or root / 'green-farm-3.apk').resolve()
    output_dir = Path(output_dir or root / 'app/build/original-dependencies').resolve()
    cache = Path(cache or root / '.local/bytecode-tools').resolve()
    if output_dir / 'compile-view.jar' == original:
        raise ValueError('Compile view must not overwrite the original APK')
    verify_pins(root / 'docs/ORIGINAL_BYTECODE_TOOLS.json')
    verify_original(original)
    tool_dir = ensure_tool(cache, tool_archive)
    if java is None:
        java = jdk_tool('java')
    converted, conversion = convert_original(original, tool_dir, cache, java)
    core = source_classes(root / 'app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480')
    output_dir.mkdir(parents=True, exist_ok=True)
    output = output_dir / 'compile-view.jar'
    with tempfile.TemporaryDirectory(prefix='compile-dependencies-', dir=output_dir) as temp:
        filtered = Path(temp) / 'filtered-compiler-input.jar'
        removed = filter_dependencies(converted, filtered, core)
        view = make_core_compile_view.generate(filtered, output)
    with zipfile.ZipFile(output) as archive:
        names = archive.namelist()
        if CORE_PACKAGE + 'l.class' not in names or CORE_PACKAGE + 'g.class' not in names:
            raise ValueError('Compile dependencies lost original l or empty g class')
        remaining_count = sum(name.endswith('.class') for name in names)
    provenance = {'purpose': 'compile-only; never package this jar in the game',
                  'runtime_source': 'original classes.dex, not converted classfiles',
                  **conversion, 'excluded_core_count': len(core), 'excluded_classes': removed,
                  'compile_class_count': remaining_count,
                  'compile_view_sha256': file_sha256(output), 'compile_view_changes': view['changes']}
    output.with_suffix('.provenance.json').write_text(json.dumps(provenance, indent=2) + '\n')
    print(f'Prepared compile-only dependencies: {output}; {len(core)} core classes excluded, '
          f'{remaining_count} binary class identities retained.')
    return provenance


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--project-root', type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument('--original', type=Path)
    parser.add_argument('--output-dir', type=Path)
    parser.add_argument('--cache', type=Path)
    parser.add_argument('--tool-archive', type=Path, help='Optional already downloaded pinned release archive')
    args = parser.parse_args()
    prepare(args.project_root, args.original, args.output_dir, args.cache, args.tool_archive)
