#!/usr/bin/env python3
"""Run isolated storage checks with Android SQLite and the installed APK.

No APK is built or installed, and the normal player database is never opened.
Requires an already installed, debuggable schema4 APK plus its compiled classes.
Only probe classes become D8 program inputs; GameSaveStore comes from that APK.
"""

import argparse
from datetime import datetime, timedelta, timezone
import hashlib
import json
import os
from pathlib import Path
import re
import shlex
import subprocess
import time
import uuid

from android_build_tools import BUILD_TOOLS_VERSION, executable_name, jdk_tool
from verify_runtime_apk import dex_classes


ROOT = Path(__file__).resolve().parents[1]
TARGET_PACKAGE = 'com.quanhoang.greenfarm3'
LEGACY_PACKAGE = 'com.gameloft.android.ANMP.GloftGF2F'
STORE_CLASS = Path('com/quanhoang/greenfarm3/S800x480/GameSaveStore.class')


def process(command, *, timeout=45, data=None):
    result = subprocess.run([str(item) for item in command], input=data,
                            capture_output=True, timeout=timeout)
    return result, result.stdout.decode('utf-8', errors='replace'), result.stderr.decode('utf-8', errors='replace')


def checked(command, *, timeout=45, data=None):
    result, stdout, stderr = process(command, timeout=timeout, data=data)
    if result.returncode:
        raise RuntimeError('Command failed (exit ' + str(result.returncode) + '): '
                           + str(command[0]) + '\n' + stdout + stderr)
    return stdout, stderr


def compiled_classes(explicit):
    if explicit:
        directory = Path(explicit).resolve()
        if not (directory / STORE_CLASS).is_file():
            raise ValueError('--classes-root lacks the compiled schema4 GameSaveStore')
        return directory
    preferred = ROOT / 'app/build/tmp/kotlin-classes/debug'
    if (preferred / STORE_CLASS).is_file():
        return preferred
    matches = sorted({Path(str(path)[:-len(str(STORE_CLASS))]).resolve()
                      for path in (ROOT / 'app/build').rglob(STORE_CLASS.name)
                      if path.as_posix().endswith(STORE_CLASS.as_posix())})
    if len(matches) != 1:
        raise ValueError('Build schema4 debug first or supply --classes-root explicitly')
    return matches[0]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--serial', default='emulator-5554')
    parser.add_argument('--package', choices=(TARGET_PACKAGE, LEGACY_PACKAGE), default=TARGET_PACKAGE,
                        help='Installed new app or legacy migration bridge; storage code always uses the new namespace')
    parser.add_argument('--sdk', type=Path,
                        default=os.environ.get('ANDROID_HOME') or os.environ.get('ANDROID_SDK_ROOT'))
    parser.add_argument('--classes-root', type=Path)
    parser.add_argument('--prefix', default='s4_' + str(int(time.time())) + '_' + uuid.uuid4().hex[:6])
    parser.add_argument('--timeout', type=int, default=45)
    parser.add_argument('--output', type=Path, default=ROOT / 'docs/sqlite-validation/probe-results.json')
    args = parser.parse_args()
    package = args.package
    if not re.fullmatch(r's4_[A-Za-z0-9_-]{1,24}', args.prefix):
        raise ValueError('--prefix must begin with s4_ and contain at most 27 safe characters')
    if not 5 <= args.timeout <= 60:
        raise ValueError('--timeout must be between 5 and 60 seconds per owned command')
    if args.sdk is None:
        raise ValueError('Set ANDROID_HOME/ANDROID_SDK_ROOT or pass --sdk')
    sdk = args.sdk.resolve()
    android = sdk / 'platforms/android-28/android.jar'
    d8 = sdk / 'build-tools' / BUILD_TOOLS_VERSION / 'lib/d8.jar'
    adb = sdk / 'platform-tools' / executable_name('adb')
    for item in (android, d8, adb):
        if not item.is_file():
            raise ValueError('Required Android tool is missing: ' + str(item))
    classes_root = compiled_classes(args.classes_root)
    work = ROOT / '.local/game-save-probe' / args.prefix
    if work.exists():
        raise ValueError('This probe run prefix already exists; choose a fresh --prefix')
    work.mkdir(parents=True)
    classes = work / 'classes'
    dex_output = work / 'dex'
    classes.mkdir()
    dex_output.mkdir()
    classpath = os.pathsep.join((str(android), str(classes_root),
                               str(ROOT / 'app/build/original-dependencies/compile-view.jar')))
    stdout, stderr = checked([jdk_tool('javac'), '-source', '8', '-target', '8',
                              '-cp', classpath, '-d', classes,
                              ROOT / 'tools/GameSaveStoreProbe.java'], timeout=args.timeout)
    (work / 'javac.log').write_text(stdout + stderr)
    programs = sorted(classes.glob('GameSaveStoreProbe*.class'))
    if not programs or len(list(classes.rglob('*.class'))) != len(programs):
        raise RuntimeError('Unexpected program class: the probe must not package app/helper classes')
    stdout, stderr = checked([jdk_tool('java'), '-cp', d8, 'com.android.tools.r8.D8',
                              '--debug', '--min-api', '16', '--lib', android,
                              '--classpath', classes_root,
                              '--classpath', ROOT / 'app/build/original-dependencies/compile-view.jar',
                              '--output', dex_output, *programs],
                             timeout=args.timeout)
    (work / 'd8.log').write_text(stdout + stderr)
    dex = dex_output / 'classes.dex'
    definitions = dex_classes(dex.read_bytes())
    if not definitions or any(not name.startswith('LGameSaveStoreProbe') for name in definitions):
        raise RuntimeError('Probe DEX contains unexpected classes or a copied storage implementation')

    adb_command = [adb, '-s', args.serial]

    def shell(command, *, timeout=None, data=None):
        return checked([*adb_command, 'shell', command],
                       timeout=args.timeout if timeout is None else timeout, data=data)

    paths, _ = shell(shlex.join(['pm', 'path', package]))
    apks = [line.removeprefix('package:').strip() for line in paths.splitlines() if line.startswith('package:')]
    if len(apks) != 1 or not apks[0].startswith('/data/app/') or not apks[0].endswith('.apk'):
        raise RuntimeError('Expected one installed complete APK, without split APKs')
    installed_apk = apks[0]
    private_dir, _ = shell(shlex.join(['run-as', package, 'pwd']))
    private_dir = private_dir.strip()
    if not private_dir.startswith('/data/') or not private_dir.endswith('/' + package):
        raise RuntimeError('Unexpected run-as application directory')
    relative_code_directory = 'files/sqlite-probe-code-' + args.prefix
    relative_dex = relative_code_directory + '/probe.dex'
    # The bytes are written by run-as, giving them the app UID and data-file
    # SELinux label. No /data/local/tmp accessibility assumption is required.
    upload = 'mkdir -p ' + shlex.quote(relative_code_directory) + ' && cat > ' + shlex.quote(relative_dex)
    shell(shlex.join(['run-as', package, 'sh', '-c', upload]), data=dex.read_bytes())
    uploaded_hash, _ = shell(shlex.join(['run-as', package, 'sha256sum', relative_dex]))
    dex_hash = hashlib.sha256(dex.read_bytes()).hexdigest()
    if uploaded_hash.split()[0] != dex_hash:
        raise RuntimeError('Uploaded probe DEX checksum mismatch')
    installed_hash, _ = shell(shlex.join(['sha256sum', installed_apk]))
    runtime_classpath = private_dir + '/' + relative_dex + ':' + installed_apk
    matrix = [
        ('seed', 'main', 0), ('verify', 'main', 0),
        ('crash-uncommitted', 'main', 17), ('verify', 'main', 0),
        ('crash-committed', 'main', 18), ('verify-committed', 'main', 0),
        ('legacy', 'legacy', 0), ('legacy-no-header', 'noheader', 0),
        ('missing-records-and-imports', 'legacydamage', 0),
        ('missing-critical-legacy', 'criticalmissing', 0),
        ('migrate-v1', 'migration1', 0), ('migrate-v3', 'migration3', 0),
        ('seed', 'corrupt', 0), ('corrupt-read', 'corrupt', 0),
        ('seed', 'missing', 0), ('missing-records', 'missing', 0),
        ('seed', 'allmissing', 0), ('missing-all-tables', 'allmissing', 0),
        ('seed', 'posemissing', 0), ('missing-pose', 'posemissing', 0),
        ('seed', 'writefailure', 0), ('sqlite-write-error', 'writefailure', 0),
        ('seed', 'physicalcorrupt', 0), ('corrupt-file', 'physicalcorrupt', 0),
        ('seed', 'repair', 0), ('repair', 'repair', 0),
        ('async-snapshots', 'async', 0),
    ]
    output = args.output.resolve()
    output.parent.mkdir(parents=True, exist_ok=True)
    report = {
        'started_at': datetime.now(timezone(timedelta(hours=7))).isoformat(timespec='seconds'),
        'report_timezone': 'Asia/Saigon',
        'required_schema': 4,
        'device': args.serial,
        'application_id': package,
        'storage_code_package': 'com.quanhoang.greenfarm3.S800x480',
        'installed_apk_sha256': installed_hash.split()[0],
        'probe_dex_sha256': dex_hash,
        'probe_dex_classes': sorted(definitions),
        'storage_implementation': 'GameSaveStore from installed complete APK',
        'sqlite_implementation': 'Android platform SQLite; no mock database',
        'isolated_fixture_prefix': args.prefix,
        'normal_player_database_opened': False,
        'passed': False,
        'cases': [],
    }
    log = output.with_name('probe-' + args.prefix + '.log')

    def save_report():
        output.write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n')

    save_report()
    with log.open('w', encoding='utf-8') as stream:
        for number, (command, suffix, expected_exit) in enumerate(matrix, 1):
            fixture = args.prefix + '_' + suffix
            remote = shlex.join(['run-as', package, 'env', 'GREENFARM_PROBE_PACKAGE=' + package, 'CLASSPATH=' + runtime_classpath,
                                 'app_process', '/system/bin', 'GameSaveStoreProbe', command, fixture])
            began = time.monotonic()
            try:
                result, stdout, stderr = process([*adb_command, 'shell', remote], timeout=args.timeout)
                markers = []
                for line in stdout.splitlines():
                    try:
                        marker = json.loads(line)
                        if isinstance(marker, dict) and marker.get('command') == command:
                            markers.append(marker)
                    except json.JSONDecodeError:
                        pass
                if expected_exit:
                    marked = any(marker.get('expected_exit') == expected_exit for marker in markers)
                else:
                    marked = any(marker.get('passed') is True and marker.get('isolated') is True
                                 for marker in markers)
                passed = result.returncode == expected_exit and marked
                entry = {'number': number, 'command': command, 'fixture': fixture,
                         'expected_exit': expected_exit, 'actual_exit': result.returncode,
                         'passed': passed, 'seconds': round(time.monotonic() - began, 3)}
                stream.write(json.dumps(entry) + '\n' + stdout + stderr + '\n')
                stream.flush()
            except subprocess.TimeoutExpired:
                entry = {'number': number, 'command': command, 'fixture': fixture,
                         'passed': False, 'error': 'owned probe command timed out',
                         'seconds': round(time.monotonic() - began, 3)}
            report['cases'].append(entry)
            save_report()
            print(json.dumps(entry), flush=True)
            if not entry['passed']:
                raise RuntimeError('Storage probe failed; retained isolated fixtures and report: ' + str(output))
    report['passed'] = True
    report['case_count'] = len(report['cases'])
    report['finished_at'] = datetime.now(timezone(timedelta(hours=7))).isoformat(timespec='seconds')
    save_report()
    print(json.dumps({'passed': True, 'cases': len(matrix), 'report': str(output)}), flush=True)


if __name__ == '__main__':
    main()
