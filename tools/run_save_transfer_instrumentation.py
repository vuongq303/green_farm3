#!/usr/bin/env python3
"""Build/run a separate save-transfer fixture instrumentation APK, using the existing debug key.

No game code is copied into the test DEX. Instrumentation starts the target App
process, so coordinate a normal game checkpoint before running. The real game
SQLite path is never opened by the test; every database uses a fresh ContextWrapper
fixture below files/save-transfer-probe-*. --prepare-only never uses ADB.
"""
from pathlib import Path
from datetime import datetime,timezone
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

ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed,kotlin_runtime_jars
from verify_runtime_apk import dex_classes

TEST_PACKAGE='com.quanhoang.greenfarm3.savetransfertest'
RUNNER='com.quanhoang.greenfarm3.testsupport.SaveTransferInstrumentation'


def checked(command, *, timeout=60):
    result=subprocess.run(list(map(str,command)),capture_output=True,text=True,timeout=timeout)
    output=result.stdout+result.stderr
    if result.returncode:raise RuntimeError('Command failed with exit '+str(result.returncode)+'\n'+output)
    return output


def prepare(args,work):
    android=args.sdk/'platforms/android-28/android.jar'
    classes_root=args.classes_root or ROOT/'app/build/tmp/kotlin-classes/debug'
    if not (classes_root/'com/quanhoang/greenfarm3/migration/SaveArchive.class').is_file():
        raise ValueError('--classes-root must contain the production Kotlin migration classes')
    compile_mixed([ROOT/'tools/SaveTransferDatabaseProbe.kt'],[ROOT/'tools/SaveTransferInstrumentation.java'],
                  work/'classes',classpath=[android,classes_root])
    program=work/'probe.jar'
    with zipfile.ZipFile(program,'w') as archive:
        for file in sorted((work/'classes').rglob('*.class')):
            archive.write(file,file.relative_to(work/'classes').as_posix())
    dex=work/'dex';dex.mkdir(exist_ok=True)
    build_tools=args.sdk/'build-tools/35.0.0'
    command=[jdk_tool('java'),'-cp',build_tools/'lib/d8.jar','com.android.tools.r8.D8','--debug','--min-api','16',
             '--lib',android,'--output',dex,'--classpath',classes_root]
    for jar in kotlin_runtime_jars():command.extend(['--classpath',jar])
    command.append(program)
    (work/'d8.log').write_text(checked(command))
    names=dex_classes((dex/'classes.dex').read_bytes())
    if not names or any(not name.startswith('Lcom/quanhoang/greenfarm3/testsupport/') for name in names):
        raise RuntimeError('The test DEX must contain test helpers only; production implementation must come from target APK')
    manifest=work/'AndroidManifest.xml'
    manifest.write_text('''<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="'''+TEST_PACKAGE+'''" android:versionCode="1" android:versionName="1">
<uses-sdk android:minSdkVersion="16" android:targetSdkVersion="28"/>
<application android:label="GF3 Save Transfer Tests" android:debuggable="true"/>
<instrumentation android:name="'''+RUNNER+'''" android:targetPackage="'''+args.package+'''" android:functionalTest="false" android:handleProfiling="false"/>
</manifest>''')
    resource_apk=work/'resources.apk'
    checked([build_tools/'aapt2','link','--manifest',manifest,'-I',android,'--min-sdk-version','16',
             '--target-sdk-version','28','-o',resource_apk])
    unsigned=work/'unsigned.apk'
    with zipfile.ZipFile(resource_apk) as source,zipfile.ZipFile(unsigned,'w') as output:
        for entry in source.infolist():output.writestr(entry,source.read(entry.filename))
        output.write(dex/'classes.dex','classes.dex')
    aligned=work/'aligned.apk'
    checked([build_tools/'zipalign','-f','4',unsigned,aligned])
    apk=work/'save-transfer-test.apk'
    keystore=ROOT/'.local/baseline-debug.keystore'
    if not keystore.is_file():raise ValueError('The existing project debug key is required; do not generate another signing identity')
    checked([jdk_tool('java'),'-cp',build_tools/'lib/apksigner.jar','com.android.apksigner.ApkSignerTool','sign',
             '--ks',keystore,'--ks-key-alias','androiddebugkey','--ks-pass','pass:android','--key-pass','pass:android',
             '--min-sdk-version','16','--v4-signing-enabled','false','--out',apk,aligned])
    metadata={'test_apk':str(apk),'test_apk_sha256':hashlib.sha256(apk.read_bytes()).hexdigest(),
              'target_package':args.package,'test_package':TEST_PACKAGE,'runner':RUNNER,
              'probe_dex_sha256':hashlib.sha256((dex/'classes.dex').read_bytes()).hexdigest(),
              'test_dex_definitions':sorted(names),
              'source_sha256':{str(file.relative_to(ROOT)):hashlib.sha256(file.read_bytes()).hexdigest() for file in
                               [ROOT/'tools/SaveTransferDatabaseProbe.kt',ROOT/'tools/SaveTransferInstrumentation.java']},
              'production_classes_copied_to_test_dex':False}
    (work/'prepared.json').write_text(json.dumps(metadata,indent=2)+'\n')
    return metadata


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--sdk',type=Path,default=os.environ.get('ANDROID_HOME'))
    parser.add_argument('--serial',default='emulator-5554')
    parser.add_argument('--package',default='com.quanhoang.greenfarm3')
    parser.add_argument('--classes-root',type=Path)
    parser.add_argument('--work-dir',type=Path,default=ROOT/'.local/save-transfer-instrumentation')
    parser.add_argument('--prepare-only',action='store_true')
    parser.add_argument('--use-prepared',action='store_true')
    parser.add_argument('--prefix',default='st_'+uuid.uuid4().hex[:12])
    parser.add_argument('--output',type=Path,default=ROOT/'docs/kotlin-validation/save-transfer-sqlite.json')
    args=parser.parse_args()
    if args.sdk is None:raise ValueError('Set ANDROID_HOME or --sdk')
    if args.package not in ('com.gameloft.android.ANMP.GloftGF2F','com.quanhoang.greenfarm3'):raise ValueError('Use a reviewed target package')
    if not re.fullmatch(r'st_[A-Za-z0-9_-]{1,32}',args.prefix):raise ValueError('Use a fresh safe st_ prefix')
    work=args.work_dir.resolve()
    if not work.is_relative_to((ROOT/'.local').resolve()):raise ValueError('Build test outputs below this repository .local directory')
    work.mkdir(parents=True,exist_ok=True)
    metadata=json.loads((work/'prepared.json').read_text()) if args.use_prepared else prepare(args,work)
    if metadata['target_package']!=args.package:raise ValueError('Prepared APK targets another application ID')
    apk=Path(metadata['test_apk'])
    if hashlib.sha256(apk.read_bytes()).hexdigest()!=metadata['test_apk_sha256']:raise ValueError('Prepared test APK hash changed')
    if args.prepare_only:
        print(json.dumps(metadata,indent=2));return
    adb=[args.sdk/'platform-tools/adb','-s',args.serial]
    paths=checked([*adb,'shell',shlex.join(['pm','path',args.package])])
    apks=[line.removeprefix('package:').strip() for line in paths.splitlines() if line.startswith('package:')]
    if len(apks)!=1 or not apks[0].startswith('/data/app/') or not apks[0].endswith('.apk'):raise RuntimeError('Expected one complete installed target APK')
    installed_hash=checked([*adb,'shell',shlex.join(['sha256sum',apks[0]])]).split()[0]
    (work/'install.log').write_text(checked([*adb,'install','-r',apk]))
    output=checked([*adb,'shell',shlex.join(['am','instrument','-w','-r','-e','fixture_prefix',args.prefix,TEST_PACKAGE+'/'+RUNNER])],timeout=120)
    (work/'instrumentation.log').write_text(output)
    result={}
    for line in output.splitlines():
        if line.startswith('INSTRUMENTATION_RESULT: '):
            key,separator,value=line.removeprefix('INSTRUMENTATION_RESULT: ').partition('=')
            if separator:result[key]=value
    passed=result.get('probe_status')=='passed'
    cases=json.loads(result.get('checks_json','[]'))
    report={'tested_at':datetime.now(timezone.utc).isoformat(),'device':args.serial,'target_package':args.package,
            'installed_apk_sha256':installed_hash,'test_apk_sha256':metadata['test_apk_sha256'],
            'implementation':'production Kotlin from complete installed target APK; real Android SQLite; attached target Application',
            'probe_entry':'separate instrumentation APK, test-only DEX definitions verified',
            'production_classes_copied_to_test_dex':False,'normal_player_database_opened':False,
            'target_process_recreated_by_instrumentation':True,'fixture_prefix':args.prefix,
            'passed':passed,'checks_passed':len(cases),'cases':cases,'instrumentation_result':result,
            'limits':['Opaque synthetic payloads are not loadable game farms.','This is not SAF UI export/import or whole-game validation.']}
    args.output.parent.mkdir(parents=True,exist_ok=True)
    args.output.write_text(json.dumps(report,ensure_ascii=False,indent=2)+'\n')
    args.output.with_suffix('.log').write_text(output)
    print(json.dumps(report,ensure_ascii=False,indent=2))
    if not passed:raise SystemExit(1)

if __name__=='__main__':main()
