#!/usr/bin/env python3
"""Run isolated Android SQLite tests of the production Kotlin save-transfer code.

Compiles the four real storage/archive/gate/identity files plus an opaque-record
fixture probe and Kotlin stdlib. Does not install an APK, launch/kill the game,
read its normal database or copy credentials. It is not a final-APK/UI test.
"""
from pathlib import Path
from datetime import datetime, timezone
import argparse
import hashlib
import json
import os
import re
import shlex
import subprocess
import sys
import uuid
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT/'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, kotlin_runtime_jars
from verify_runtime_apk import dex_classes


def checked(command, *, data=None, timeout=60):
    result = subprocess.run(list(map(str, command)), input=data, capture_output=True, timeout=timeout)
    output = result.stdout.decode('utf-8', errors='replace') + result.stderr.decode('utf-8', errors='replace')
    if result.returncode:
        raise RuntimeError('Command failed with exit ' + str(result.returncode) + '\n' + output)
    return output


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--sdk', type=Path, default=os.environ.get('ANDROID_HOME'))
    parser.add_argument('--serial', default='emulator-5554')
    parser.add_argument('--package', default='com.quanhoang.greenfarm3')
    parser.add_argument('--prefix', default='st_' + uuid.uuid4().hex[:12])
    parser.add_argument('--installed-runtime', action='store_true',
                        help='Load the production classes/stdlib from the installed complete APK; compile only the probe')
    parser.add_argument('--classes-root', type=Path,
                        help='Kotlin compiled classes used only to compile the probe for --installed-runtime')
    parser.add_argument('--output', type=Path, default=ROOT/'docs/kotlin-validation/save-transfer-sqlite.json')
    args = parser.parse_args()
    if args.sdk is None:
        raise ValueError('Set ANDROID_HOME or --sdk')
    if args.package not in ('com.gameloft.android.ANMP.GloftGF2F', 'com.quanhoang.greenfarm3'):
        raise ValueError('Use one of the two reviewed application IDs')
    if not re.fullmatch(r'st_[A-Za-z0-9_-]{1,32}', args.prefix):
        raise ValueError('Choose a fresh --prefix starting st_ and containing only safe ASCII characters')
    work = ROOT/'.local/save-transfer-probe'/args.prefix
    if work.exists():
        raise ValueError('The local probe prefix already exists; choose another')
    work.mkdir(parents=True)
    sources = [ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/migration'/name for name in (
        'SaveArchive.kt','SaveArchiveDatabase.kt','SaveTransferIdentity.kt','SaveTransferGate.kt')]
    probe = ROOT/'tools/SaveTransferDatabaseProbe.kt'
    android = args.sdk/'platforms/android-28/android.jar'
    classes_root = args.classes_root
    if args.installed_runtime:
        if classes_root is None:
            classes_root = ROOT/'app/build/tmp/kotlin-classes/debug'
        required = classes_root/'com/quanhoang/greenfarm3/migration/SaveArchive.class'
        if not required.is_file():
            raise ValueError('--classes-root must contain the real compiled migration classes')
        compile_mixed([probe], [], work/'classes', classpath=[android,classes_root])
    else:
        compile_mixed([*sources,probe], [], work/'classes', classpath=[android])
    program = work/'probe.jar'
    with zipfile.ZipFile(program, 'w') as archive:
        for file in sorted((work/'classes').rglob('*.class')):
            archive.write(file, file.relative_to(work/'classes').as_posix())
    dex = work/'dex'; dex.mkdir()
    d8 = args.sdk/'build-tools/35.0.0/lib/d8.jar'
    command = [jdk_tool('java'),'-cp',d8,'com.android.tools.r8.D8','--debug','--min-api','16',
               '--lib',android,'--output',dex]
    if args.installed_runtime:
        command.extend(['--classpath',classes_root])
        for jar in kotlin_runtime_jars(): command.extend(['--classpath',jar])
        command.append(program)
    else:
        command.extend([program,*kotlin_runtime_jars()])
    output = checked(command, timeout=60)
    (work/'d8.log').write_text(output)
    adb = [args.sdk/'platform-tools/adb', '-s', args.serial]
    private = checked([*adb, 'shell', shlex.join(['run-as',args.package,'pwd'])]).strip()
    if not private.startswith('/data/') or not private.endswith('/'+args.package):
        raise RuntimeError('Unexpected package private directory')
    code = 'files/save-transfer-code-'+args.prefix
    fixture = private+'/files/save-transfer-probe-'+args.prefix
    upload = 'mkdir -p '+shlex.quote(code)+' && cat > '+shlex.quote(code+'/probe.dex')
    dex_bytes = (dex/'classes.dex').read_bytes()
    if args.installed_runtime and any(not name.startswith('Lcom/quanhoang/greenfarm3/testsupport/SaveTransferDatabaseProbe') for name in dex_classes(dex_bytes)):
        raise RuntimeError('Runtime probe unexpectedly copied a production/dependency class')
    checked([*adb,'shell',shlex.join(['run-as',args.package,'sh','-c',upload])],data=dex_bytes)
    uploaded = checked([*adb,'shell',shlex.join(['run-as',args.package,'sha256sum',code+'/probe.dex'])]).split()[0]
    digest = hashlib.sha256(dex_bytes).hexdigest()
    if uploaded != digest:
        raise RuntimeError('Uploaded probe DEX hash differs')
    runtime_classpath = private+'/'+code+'/probe.dex'
    installed_hash = None
    if args.installed_runtime:
        paths = checked([*adb,'shell',shlex.join(['pm','path',args.package])])
        apks = [line.removeprefix('package:').strip() for line in paths.splitlines() if line.startswith('package:')]
        if len(apks) != 1 or not apks[0].startswith('/data/app/') or not apks[0].endswith('.apk'):
            raise RuntimeError('Expected one complete installed APK, without splits')
        installed_hash = checked([*adb,'shell',shlex.join(['sha256sum',apks[0]])]).split()[0]
        runtime_classpath += ':'+apks[0]
    command = 'CLASSPATH='+shlex.quote(runtime_classpath)+' app_process /system/bin com.quanhoang.greenfarm3.testsupport.SaveTransferDatabaseProbe '+shlex.quote(fixture)
    output = checked([*adb,'shell',shlex.join(['run-as',args.package,'sh','-c',command])],timeout=60)
    (work/'android.log').write_text(output)
    cases = [line.removeprefix('PASS ') for line in output.splitlines() if line.startswith('PASS ')]
    if 'SAVE_TRANSFER_PROBE_PASS checks=' not in output or not cases:
        raise RuntimeError('The probe did not complete')
    report = {
        'tested_at':datetime.now(timezone.utc).isoformat(),
        'device':args.serial, 'uid_package':args.package,
        'implementation':('production Kotlin from installed complete APK with real Android SQLite' if args.installed_runtime
                          else 'production Kotlin compiled from source with real Android SQLite'),
        'installed_apk_sha256':installed_hash,
        'final_apk_or_ui_test':False, 'normal_player_database_opened':False,
        'game_installed_started_stopped_or_reset':False,
        'fixture_prefix':args.prefix, 'probe_dex_sha256':digest,
        'sources_sha256':{str(file.relative_to(ROOT)):hashlib.sha256(file.read_bytes()).hexdigest() for file in [*sources,probe]},
        'passed':True,'checks_passed':len(cases),'cases':cases,
        'limitations':['Opaque synthetic payloads are not loadable game farms.',
                       'This is not an actual old-APK SAF export/new-APK import/UI validation.',
                       'Same-process overlapping-lock test; not a long concurrent-process restart stress test.']
    }
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report,indent=2,ensure_ascii=False)+'\n')
    args.output.with_suffix('.log').write_text(output)
    print(json.dumps(report,indent=2,ensure_ascii=False))

if __name__=='__main__':main()
