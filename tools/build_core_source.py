#!/usr/bin/env python3
"""Sign a Java-core APK with original SDK DEX dependencies merged by dexlib2.

Unlike the binary baseline, this mode changes DEX: the core was compiled from
Java. Assets/native remain original. Do not label it an all-Java SDK rebuild.
"""

import argparse
import json
from pathlib import Path
import shutil
import subprocess
import tempfile
import zipfile

from build_binary_baseline import EXPECTED_APK_SHA256, DEX, SIGNATURE, is_resource, sha256
from android_build_tools import ensure_debug_keystore, jdk_tool, signing_tools
from prepare_core_dependencies import ADDED_CORE_SOURCES


def assemble(original, compiled, merged_dex, output):
    original, compiled, merged_dex, output = map(Path, (original, compiled, merged_dex, output))
    report_path = merged_dex.with_suffix(merged_dex.suffix + '.json')
    for source in (original, compiled, merged_dex, report_path):
        if (output.resolve() == source.resolve()
                or (output.exists() and source.exists() and output.samefile(source))):
            raise ValueError('Output must not overwrite any input or merger report')
    if sha256(original.read_bytes()) != EXPECTED_APK_SHA256:
        raise ValueError('Unexpected original APK')
    if not merged_dex.read_bytes().startswith(b'dex\n'):
        raise ValueError('Merged DEX header missing')
    report = json.loads(report_path.read_text())
    if (report.get('original_apk_sha256') != EXPECTED_APK_SHA256
            or report.get('overlay_apk_sha256') != sha256(compiled.read_bytes())
            or report.get('output_dex_sha256') != sha256(merged_dex.read_bytes())
            or report.get('original_non_overlay_semantics_verified') is not True
            or report.get('compiled_overlay_semantics_verified') is not True):
        raise ValueError('Merger provenance does not verify these exact inputs and output')
    core = Path(__file__).resolve().parents[1] / 'app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480'
    required = {'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/' + p.stem + ';'
                for p in core.glob('*.java') if p.stem != 'l' and p.stem not in ADDED_CORE_SOURCES}
    if not required.issubset(set(report.get('replaced_classes', []))):
        raise ValueError('Not every selected Java-core class was recompiled and replaced')
    additions = {'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/' + p.stem + ';'
                 for p in core.glob('*.java') if p.stem in ADDED_CORE_SOURCES}
    if not additions.issubset(set(report.get('added_classes', []))):
        raise ValueError('Not every reviewed progress helper was compiled and added')
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(compiled) as rebuilt:
        original_names, compiled_names = source.namelist(), rebuilt.namelist()
        if len(original_names) != len(set(original_names)):
            raise ValueError('Original APK contains duplicate entries')
        if len(compiled_names) != len(set(compiled_names)):
            raise ValueError('Compiled APK contains duplicate entries')
        if 'classes.dex' not in original_names:
            raise ValueError('Original APK lacks primary classes.dex')
        if not {'AndroidManifest.xml', 'resources.arsc'}.issubset(compiled_names):
            raise ValueError('Compiled APK lacks manifest or resource table')
        if any(name.startswith('res/') and not is_resource(name) for name in compiled_names):
            raise ValueError('Compiled APK contains an unsafe resource path')
        with zipfile.ZipFile(output, 'w') as out:
            for entry in source.infolist():
                if not is_resource(entry.filename) and not DEX.fullmatch(entry.filename) and not SIGNATURE.fullmatch(entry.filename):
                    out.writestr(entry, source.read(entry))
            for entry in rebuilt.infolist():
                if is_resource(entry.filename):
                    out.writestr(entry, rebuilt.read(entry))
            # Match original primary DEX's compression, but use newly merged code.
            info = source.getinfo('classes.dex')
            out.writestr(info, merged_dex.read_bytes())
    return report


def verify_assets_native(original, output):
    hashes = {}
    with zipfile.ZipFile(original) as before, zipfile.ZipFile(output) as after:
        names = sorted(n for n in before.namelist() if n.startswith(('assets/', 'lib/')) and not n.endswith('/'))
        actual = sorted(n for n in after.namelist() if n.startswith(('assets/', 'lib/')) and not n.endswith('/'))
        if names != actual:
            raise ValueError('Assets/native set changed')
        for name in names:
            if before.read(name) != after.read(name) or before.getinfo(name).compress_type != after.getinfo(name).compress_type:
                raise ValueError('Assets/native bytes or compression changed: ' + name)
            hashes[name] = sha256(before.read(name))
    return hashes


def regenerate_build_revision(original, unsigned, directory):
    """Refresh DEX-size build metadata; preserve the original SDK integrity guard."""
    resource = 'res/raw/content.bin'
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(unsigned) as compiled:
        original_metadata = source.read(resource)
        if compiled.read(resource) != original_metadata:
            raise ValueError('Build definition changed before revision regeneration')
        # Exactly wrapper.b.eb's suffix / ZipEntry.getSize calculation.
        original_size = sum(i.file_size for i in source.infolist() if i.filename.endswith('.dex'))
        compiled_size = sum(i.file_size for i in compiled.infolist() if i.filename.endswith('.dex'))
    directory = Path(directory)
    old, new = directory / 'original-content.bin', directory / 'generated-content.bin'
    old.write_bytes(original_metadata)
    java = jdk_tool('java')
    tool = Path(__file__).resolve().with_name('UpdateBuildRevision.java')
    subprocess.run([str(java), str(tool), str(old), str(new),
                    str(original_size), str(compiled_size)], check=True)
    metadata = new.read_bytes()
    report = json.loads(new.with_suffix(new.suffix + '.json').read_text())
    if (report.get('other_definition_bytes_preserved') is not True
            or report.get('original_guard_accepts_generated_revision') is not True
            or report.get('generated_metadata_sha256') != sha256(metadata)
            or report.get('original_metadata_sha256') != sha256(original_metadata)
            or report.get('original_dex_bytes') != original_size
            or report.get('rebuilt_dex_bytes') != compiled_size):
        raise ValueError('Generated build revision was not verified')
    rewritten = directory / 'revision-updated.apk'
    with zipfile.ZipFile(unsigned) as before, zipfile.ZipFile(rewritten, 'w') as after:
        for entry in before.infolist():
            data = metadata if entry.filename == resource else before.read(entry)
            after.writestr(entry, data)
    with zipfile.ZipFile(unsigned) as before, zipfile.ZipFile(rewritten) as after:
        if before.namelist() != after.namelist():
            raise ValueError('Revision generation changed APK entry names')
        for entry in before.infolist():
            if entry.filename != resource and before.read(entry) != after.read(entry.filename):
                raise ValueError('Revision generation changed another APK entry: ' + entry.filename)
            if entry.compress_type != after.getinfo(entry.filename).compress_type:
                raise ValueError('Revision generation changed ZIP compression')
    rewritten.replace(unsigned)
    return report


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('original', 'compiled', 'merged-dex', 'sdk', 'keystore', 'output'):
        parser.add_argument('--' + name, required=True, type=Path)
    args = parser.parse_args()
    output = args.output.resolve()
    inputs = (args.original, args.compiled, args.merged_dex, args.keystore,
              args.merged_dex.with_suffix(args.merged_dex.suffix + '.json'))
    if any(output == source.resolve()
           or (output.exists() and source.exists() and output.samefile(source)) for source in inputs):
        raise ValueError('Output must not overwrite any input, signing key or merger report')
    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='core-source-', dir=output.parent) as directory:
        temp = Path(directory)
        unsigned, aligned, signed = (temp / name for name in ('unsigned.apk', 'aligned.apk', 'signed.apk'))
        merge_report = assemble(args.original, args.compiled, args.merged_dex, unsigned)
        # Reject corrupted payloads before invoking zipalign or the signing key.
        verify_assets_native(args.original, unsigned)
        revision_report = regenerate_build_revision(args.original, unsigned, temp)
        tools = signing_tools(args.sdk)
        keystore = ensure_debug_keystore(args.keystore)
        subprocess.run([str(tools.zipalign), '-f', '-P', '16', '4', str(unsigned), str(aligned)], check=True)
        subprocess.run([*tools.apksigner, 'sign', '--ks', str(keystore),
                        '--ks-key-alias', 'androiddebugkey', '--ks-pass', 'pass:android',
                        '--key-pass', 'pass:android', '--out', str(signed), str(aligned)], check=True)
        subprocess.run([*tools.apksigner, 'verify', '--verbose', str(signed)], check=True)
        subprocess.run([str(tools.zipalign), '-c', '-P', '16', '4', str(signed)], check=True)
        payload = verify_assets_native(args.original, signed)
        with zipfile.ZipFile(signed) as z:
            if z.read('classes.dex') != args.merged_dex.read_bytes():
                raise ValueError('Packaged DEX differs from verified merger output')
        shutil.copyfile(signed, output)
    provenance = {'mode': 'java-core-with-original-dex-sdk-dependencies',
                  'original_apk_sha256': EXPECTED_APK_SHA256,
                  'compiled_core_apk_sha256': sha256(args.compiled.read_bytes()),
                  'output_apk_sha256': sha256(output.read_bytes()),
                  'merged_dex_sha256': sha256(args.merged_dex.read_bytes()),
                  'dex_merge': merge_report, 'build_revision_metadata': revision_report,
                  'unchanged_assets_native_sha256': payload}
    output.with_suffix('.provenance.json').write_text(json.dumps(provenance, indent=2) + '\n')
    print('Built Java-core APK with original SDK DEX dependencies:', output)


if __name__ == '__main__':
    main()
