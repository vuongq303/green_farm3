#!/usr/bin/env python3
"""Package the single production APK with local saves and retired online SDKs.

Uses the existing source-merge, relocation, metadata, signing and alignment
pipeline. There is no second APK variant, backend URL or test-only game mode.
"""
import argparse, json, shutil, subprocess, tempfile, zipfile
from pathlib import Path
from build_binary_baseline import EXPECTED_APK_SHA256, SIGNATURE, sha256
from build_core_source import regenerate_build_revision, verify_assets_native
from android_build_tools import signing_tools, ensure_debug_keystore
from verify_runtime_apk import dex_classes, manifest_tree


def assert_offline(apk, sdk):
    root = manifest_tree(apk, sdk)
    forbidden_permissions = {'android.permission.INTERNET', 'android.permission.ACCESS_NETWORK_STATE',
                             'android.permission.ACCESS_WIFI_STATE', 'com.android.vending.BILLING'}
    for node in root['children']:
        if node['tag'] == 'uses-permission' and node['attributes'].get('name') in forbidden_permissions:
            raise ValueError('Online permission survived in the packaged APK')
    application = next(n for n in root['children'] if n['tag'] == 'application')
    if application['attributes'].get('allowBackup') != 'false':
        raise ValueError('Offline game must not enable automatic remote backup; use explicit local save export')
    for node in application['children']:
        name = node['attributes'].get('name', '')
        if any(x in name for x in ('firebase', 'Firebase', 'measurement', 'com.facebook.', 'PushNotification')):
            raise ValueError('Online startup component survived: ' + name)
    with zipfile.ZipFile(apk) as z:
        classes = dex_classes(z.read('classes.dex'))
        if any(n.startswith('Lcom/google/firebase/') or '/PushNotification/Firebase' in n for n in classes):
            raise ValueError('Firebase SDK definitions survived')
        if 'res/raw/google_services.json' in z.namelist():
            raise ValueError('Firebase project configuration survived')
        if any(n.startswith('firebase-') and n.endswith('.properties') for n in z.namelist()):
            raise ValueError('Firebase SDK metadata survived')
        if 'res/xml/network_security_config.xml' in z.namelist():
            raise ValueError('Unused online transport configuration survived')
        resources = z.read('resources.arsc') if 'resources.arsc' in z.namelist() else b''
        for name in ('default_web_client_id', 'google_crash_reporting_api_key',
                     'game_api_app_id', 'game_api_server_client_id',
                     'google_app_id', 'google_api_key', 'gcm_defaultSenderId',
                     'google_storage_bucket', 'firebase_database_url'):
            if any(name.encode(encoding) in resources for encoding in ('utf-8', 'utf-16le')):
                raise ValueError('Online account/project resource survived: ' + name)


def prepare(original, base, dex, output):
    original, base, dex, output = map(Path, (original, base, dex, output))
    reports = [base.with_suffix('.provenance.json'), dex.with_suffix('.dex.json'),
               dex.parent.parent / 'relocated/classes.dex.json']
    baseline, retirement, relocation = [json.loads(p.read_text()) for p in reports]
    if (sha256(original.read_bytes()) != EXPECTED_APK_SHA256 or
        baseline.get('output_apk_sha256') != sha256(base.read_bytes()) or
        baseline.get('dex_merge', {}).get('original_non_overlay_semantics_verified') is not True or
        baseline.get('dex_merge', {}).get('compiled_overlay_semantics_verified') is not True):
        raise ValueError('Source merge provenance mismatch')
    if (relocation.get('input_dex_sha256') != baseline.get('merged_dex_sha256') or
        relocation.get('inverse_canonical_semantics_verified') is not True or
        retirement.get('input_sha256') != relocation.get('output_dex_sha256') or
        retirement.get('output_sha256') != sha256(dex.read_bytes()) or
        retirement.get('firebase_classes_removed', 0) <= 0 or
        retirement.get('firebase_executable_references_remaining') != 0):
        raise ValueError('Offline SDK retirement provenance mismatch')
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(base) as before, zipfile.ZipFile(output, 'w') as after:
        if len(before.namelist()) != len(set(before.namelist())):
            raise ValueError('Duplicate base entries')
        if sha256(before.read('classes.dex')) != relocation['input_dex_sha256']:
            raise ValueError('Relocation input differs from the verified merged source')
        for entry in before.infolist():
            if SIGNATURE.fullmatch(entry.filename): continue
            if entry.filename.startswith('firebase-') and entry.filename.endswith('.properties'): continue
            if entry.filename == 'classes.dex': data = dex.read_bytes()
            elif entry.filename == 'res/raw/content.bin': data = source.read(entry.filename)
            else: data = before.read(entry)
            after.writestr(entry, data)
    verify_assets_native(original, output)
    return retirement


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('original', 'base', 'dex', 'output', 'sdk', 'keystore'):
        parser.add_argument('--' + name, required=True, type=Path)
    args = parser.parse_args()
    if args.output.resolve() in {args.original.resolve(), args.base.resolve(), args.dex.resolve(), args.keystore.resolve()}:
        raise ValueError('Output must not overwrite an input')
    args.output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='game-package-', dir=args.output.parent) as d:
        temp = Path(d); unsigned, aligned, signed = [temp/n for n in ('unsigned.apk','aligned.apk','signed.apk')]
        retirement = prepare(args.original,args.base,args.dex,unsigned)
        revision = regenerate_build_revision(args.original,unsigned,temp)
        tools = signing_tools(args.sdk); key = ensure_debug_keystore(args.keystore)
        subprocess.run([str(tools.zipalign),'-f','-P','16','4',str(unsigned),str(aligned)],check=True)
        subprocess.run([*tools.apksigner,'sign','--ks',str(key),'--ks-key-alias','androiddebugkey','--ks-pass','pass:android','--key-pass','pass:android','--out',str(signed),str(aligned)],check=True)
        subprocess.run([*tools.apksigner,'verify','--verbose',str(signed)],check=True)
        subprocess.run([str(tools.zipalign),'-c','-P','16','4',str(signed)],check=True)
        payload = verify_assets_native(args.original,signed)
        assert_offline(signed,args.sdk)
        shutil.copyfile(signed,args.output)
    args.output.with_suffix('.provenance.json').write_text(json.dumps({
        'mode':'local-sqlite-game','output_apk_sha256':sha256(args.output.read_bytes()),
        'sdk_retirement':retirement,'build_revision_metadata':revision,
        'unchanged_assets_native_sha256':payload},indent=2)+'\n')
    print('Built standalone game:',args.output)

if __name__=='__main__': main()
