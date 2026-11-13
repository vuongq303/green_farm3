#!/usr/bin/env python3
"""Build the complete debug APK with verified SDK/game Janus bootstrap URLs."""

import argparse
import json
from pathlib import Path
import shutil
import subprocess
import tempfile
import zipfile

from build_binary_baseline import EXPECTED_APK_SHA256, SIGNATURE, sha256
from build_core_source import regenerate_build_revision, verify_assets_native
from android_build_tools import ensure_debug_keystore, signing_tools
from verify_runtime_apk import GOOGLE_IDENTIFIER_CLASSES


TARGET_CLASS = 'Lcom/gameloft/a/b/a;'
CORE_TARGET_CLASS = 'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/s;'
TARGET_CLASSES = [TARGET_CLASS, CORE_TARGET_CLASS]
TARGET_METHOD = 'aL(Ljava/lang/String;)Ljava/lang/String;'
ORIGINAL_URL = 'https://eve.gameloft.com/'
SDK_RETIREMENT_OWNERS = sorted(('Ljavax/microedition/midlet/MIDlet;', 'Ljavax/microedition/midlet/o;',
    'Lcom/gameloft/a/b/i;', 'Lcom/gameloft/adsmanager/AdsManager;', 'Lcom/gameloft/glads/GLAds;'))


def prepare(original, base, patched_dex, output, relocated_dex=None, disabled_sdk_dex=None, pruned_ads_dex=None, pruned_facebook_dex=None):
    original, base, patched_dex, output = map(Path, (original, base, patched_dex, output))
    base_report = base.with_suffix('.provenance.json')
    patch_report = patched_dex.with_suffix(patched_dex.suffix + '.json')
    inputs = [original, base, base_report, patched_dex, patch_report]
    for optional in (relocated_dex, disabled_sdk_dex, pruned_ads_dex, pruned_facebook_dex):
        if optional is not None:
            optional = Path(optional)
            inputs.extend((optional, optional.with_suffix(optional.suffix + '.json')))
    for source in inputs:
        if output.resolve() == source.resolve() or (
                output.exists() and source.exists() and output.samefile(source)):
            raise ValueError('Output must not overwrite an input or provenance report')
    if sha256(original.read_bytes()) != EXPECTED_APK_SHA256:
        raise ValueError('Unexpected original APK')
    baseline = json.loads(base_report.read_text())
    patch = json.loads(patch_report.read_text())
    relocation = None
    packaged_dex = patched_dex
    if relocated_dex is not None:
        packaged_dex = Path(relocated_dex)
        relocation = json.loads(packaged_dex.with_suffix(packaged_dex.suffix + '.json').read_text())
        if (relocation.get('schema_version') != 1
                or relocation.get('from_package') != 'com.gameloft.android.ANMP.GloftGF2F'
                or relocation.get('to_package') != 'com.quanhoang.greenfarm3'
                or relocation.get('input_dex_sha256') != sha256(patched_dex.read_bytes())
                or relocation.get('output_dex_sha256') != sha256(packaged_dex.read_bytes())
                or relocation.get('legacy_game_class_definitions_remaining') != 0
                or relocation.get('inverse_canonical_semantics_verified') is not True):
            raise ValueError('Game package relocation provenance mismatch')
    if disabled_sdk_dex is not None:
        disabled_sdk_dex = Path(disabled_sdk_dex)
        policy = json.loads(disabled_sdk_dex.with_suffix(disabled_sdk_dex.suffix + '.json').read_text())
        expected_counts = {'facebook_lifecycle_calls_disabled': 12, 'ads_lifecycle_calls_disabled': 12,
                           'native_fullscreen_queries_disabled': 2, 'native_back_handlers_disabled': 1}
        if (policy.get('schema_version') != 2
                or policy.get('policy') != 'facebook-and-ads-lifecycle-disabled'
                or policy.get('target_class') != 'Ljavax/microedition/midlet/MIDlet;'
                or policy.get('target_classes') != SDK_RETIREMENT_OWNERS
                or policy.get('native_bridge_calls_disabled') != 6
                or policy.get('total_sdk_invocation_sites_disabled') != 33
                or type(policy.get('native_ads_manager_invocations_remaining')) is not int
                or policy.get('native_ads_manager_invocations_remaining') != 0
                or policy.get('all_native_ads_manager_invocations_audited') is not True
                or policy.get('per_owner_inverse_semantics_verified') is not True
                or any(type(policy.get(key)) is not int or policy.get(key) != value
                       for key, value in expected_counts.items())
                or policy.get('instruction_widths_preserved') is not True
                or policy.get('inverse_target_semantics_verified') is not True
                or policy.get('unchanged_other_classes_verified') is not True
                or policy.get('input_dex_sha256') != sha256(packaged_dex.read_bytes())
                or policy.get('output_dex_sha256') != sha256(disabled_sdk_dex.read_bytes())):
            raise ValueError('Disabled SDK lifecycle provenance mismatch')
        packaged_dex = disabled_sdk_dex
    if pruned_ads_dex is not None:
        pruned_ads_dex = Path(pruned_ads_dex)
        pruning = json.loads(pruned_ads_dex.with_suffix(pruned_ads_dex.suffix + '.json').read_text())
        if (disabled_sdk_dex is None or pruning.get('schema_version') != 1
                or pruning.get('policy') != 'remove-google-ads-presentation-sdk'
                or type(pruning.get('removed_class_count')) is not int or pruning['removed_class_count'] != 1880
                or pruning.get('retained_identifier_classes') != sorted(GOOGLE_IDENTIFIER_CLASSES)
                or any(pruning.get(key) is not True for key in (
                    'retained_structural_reference_audit_verified', 'retained_reflection_string_audit_verified',
                    'retained_class_semantics_verified', 'inverse_class_set_semantics_verified'))
                or pruning.get('input_dex_sha256') != sha256(packaged_dex.read_bytes())
                or pruning.get('output_dex_sha256') != sha256(pruned_ads_dex.read_bytes())):
            raise ValueError('Google Ads pruning provenance mismatch')
        packaged_dex = pruned_ads_dex
    if pruned_facebook_dex is not None:
        pruned_facebook_dex = Path(pruned_facebook_dex)
        facebook = json.loads(pruned_facebook_dex.with_suffix(pruned_facebook_dex.suffix + '.json').read_text())
        counts = {'removed_facebook_class_count': 717, 'removed_provider_bridge_count': 24, 'removed_class_count': 741}
        if (pruned_ads_dex is None or facebook.get('schema_version') != 1
                or facebook.get('policy') != 'remove-facebook-sdk-and-provider-bridge'
                or any(type(facebook.get(key)) is not int or facebook.get(key) != value for key, value in counts.items())
                or facebook.get('retired_avatar_method') != 'Lcom/gameloft/android2d/socialnetwork/i;->UN()Ljavax/microedition/lcdui/ao;'
                or facebook.get('original_profile_canonical_sha256') != '5b1b329c8487c068c8a2e1b4dbd2a0fdfabfa7a96faf7ec01acb0098ccd7fd12'
                or any(facebook.get(key) is not True for key in (
                    'retained_structural_reference_audit_verified', 'retained_reflection_string_audit_verified',
                    'retired_nullable_avatar_method_verified', 'other_profile_data_and_methods_preserved',
                    'retained_class_semantics_verified', 'inverse_class_set_and_method_semantics_verified',
                    'global_ad_native_invocation_audit_verified'))
                or type(facebook.get('native_ads_manager_invocations_remaining')) is not int
                or facebook.get('native_ads_manager_invocations_remaining') != 0
                or facebook.get('input_dex_sha256') != sha256(packaged_dex.read_bytes())
                or facebook.get('output_dex_sha256') != sha256(pruned_facebook_dex.read_bytes())):
            raise ValueError('Facebook SDK pruning provenance mismatch')
        packaged_dex = pruned_facebook_dex
    if (baseline.get('mode') != 'java-core-with-original-dex-sdk-dependencies'
            or baseline.get('original_apk_sha256') != EXPECTED_APK_SHA256
            or baseline.get('output_apk_sha256') != sha256(base.read_bytes())
            or baseline.get('dex_merge', {}).get('original_non_overlay_semantics_verified') is not True
            or baseline.get('dex_merge', {}).get('compiled_overlay_semantics_verified') is not True):
        raise ValueError('Base APK provenance mismatch')
    if (patch.get('schema_version') != 2
            or patch.get('target_classes') != TARGET_CLASSES
            or patch.get('target_method') != TARGET_METHOD
            or patch.get('original_url') != ORIGINAL_URL
            or type(patch.get('replacement_count')) is not int
            or patch.get('replacement_count') != 3
            or patch.get('replacement_counts') != {TARGET_CLASS: 1, CORE_TARGET_CLASS: 2}
            or patch.get('unchanged_other_classes_verified') is not True
            or patch.get('reverse_patch_semantics_verified') is not True
            or patch.get('output_dex_sha256') != sha256(patched_dex.read_bytes())):
        raise ValueError('Endpoint patch provenance mismatch')
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(base) as before:
        names = before.namelist()
        if len(names) != len(set(names)) or any(
                n.startswith('classes') and n.endswith('.dex') and n != 'classes.dex' for n in names):
            raise ValueError('Unexpected or duplicate APK entry set')
        dex_hash = sha256(before.read('classes.dex'))
        if dex_hash != patch.get('input_dex_sha256') or dex_hash != baseline.get('merged_dex_sha256'):
            raise ValueError('Endpoint patch input differs from the verified base DEX')
        resource = 'res/raw/content.bin'
        revision = baseline.get('build_revision_metadata', {})
        if (revision.get('original_guard_accepts_generated_revision') is not True
                or sha256(before.read(resource)) != revision.get('generated_metadata_sha256')):
            raise ValueError('Base build revision metadata mismatch')
        with zipfile.ZipFile(output, 'w') as after:
            for entry in before.infolist():
                if SIGNATURE.fullmatch(entry.filename):
                    continue
                if entry.filename == 'classes.dex':
                    data = packaged_dex.read_bytes()
                elif entry.filename == resource:
                    # The shared metadata generator verifies this original preimage,
                    # then changes only REVISION-2408 for the patched DEX size.
                    data = source.read(resource)
                else:
                    data = before.read(entry)
                after.writestr(entry, data)
    verify_assets_native(original, output)
    return baseline, patch


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('original', 'base', 'patched-dex', 'output', 'sdk', 'keystore'):
        parser.add_argument('--' + name, required=True, type=Path)
    parser.add_argument('--relocated-dex', type=Path)
    parser.add_argument('--disabled-sdk-dex', type=Path)
    parser.add_argument('--pruned-ads-dex', type=Path)
    parser.add_argument('--pruned-facebook-dex', type=Path)
    args = parser.parse_args()
    output = args.output.resolve()
    inputs = (args.original, args.base, args.patched_dex, args.keystore,
              args.base.with_suffix('.provenance.json'),
              args.patched_dex.with_suffix(args.patched_dex.suffix + '.json'))
    if args.relocated_dex is not None:
        inputs += (args.relocated_dex, args.relocated_dex.with_suffix(args.relocated_dex.suffix + '.json'))
    if args.disabled_sdk_dex is not None:
        inputs += (args.disabled_sdk_dex, args.disabled_sdk_dex.with_suffix(args.disabled_sdk_dex.suffix + '.json'))
    if args.pruned_ads_dex is not None:
        inputs += (args.pruned_ads_dex, args.pruned_ads_dex.with_suffix(args.pruned_ads_dex.suffix + '.json'))
    if args.pruned_facebook_dex is not None:
        inputs += (args.pruned_facebook_dex, args.pruned_facebook_dex.with_suffix(args.pruned_facebook_dex.suffix + '.json'))
    if any(output == p.resolve() or (output.exists() and p.exists() and output.samefile(p)) for p in inputs):
        raise ValueError('Output must not overwrite an input, key or provenance report')
    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='local-backend-', dir=output.parent) as directory:
        temp = Path(directory)
        unsigned, aligned, signed = [temp / n for n in ('unsigned.apk', 'aligned.apk', 'signed.apk')]
        baseline, patch = prepare(args.original, args.base, args.patched_dex, unsigned, args.relocated_dex, args.disabled_sdk_dex, args.pruned_ads_dex, args.pruned_facebook_dex)
        if args.relocated_dex is not None:
            from verify_runtime_apk import manifest
            relocation = json.loads(args.relocated_dex.with_suffix(args.relocated_dex.suffix + '.json').read_text())
            if relocation.get('application_id') != manifest(unsigned, args.sdk)[0]:
                raise ValueError('Relocated provider identities do not match the packaged application ID')
        revision = regenerate_build_revision(args.original, unsigned, temp)
        tools = signing_tools(args.sdk)
        keystore = ensure_debug_keystore(args.keystore)
        subprocess.run([str(tools.zipalign), '-f', '-P', '16', '4', str(unsigned), str(aligned)], check=True)
        subprocess.run([*tools.apksigner, 'sign', '--ks', str(keystore),
                        '--ks-key-alias', 'androiddebugkey', '--ks-pass', 'pass:android',
                        '--key-pass', 'pass:android', '--out', str(signed), str(aligned)], check=True)
        subprocess.run([*tools.apksigner, 'verify', '--verbose', str(signed)], check=True)
        subprocess.run([str(tools.zipalign), '-c', '-P', '16', '4', str(signed)], check=True)
        payload = verify_assets_native(args.original, signed)
        with zipfile.ZipFile(signed) as apk:
            packaged_dex_hash = sha256((args.pruned_facebook_dex or args.pruned_ads_dex or args.disabled_sdk_dex or args.relocated_dex or args.patched_dex).read_bytes())
            if sha256(apk.read('classes.dex')) != packaged_dex_hash:
                raise ValueError('Signed APK DEX differs from verified endpoint patch')
        shutil.copyfile(signed, output)
    report = {
        'mode': 'java-core-with-local-janus-backend',
        'original_apk_sha256': EXPECTED_APK_SHA256,
        'base_apk_sha256': baseline['output_apk_sha256'],
        'output_apk_sha256': sha256(output.read_bytes()),
        'endpoint_patch': patch,
        'packaged_dex_sha256': packaged_dex_hash,
        'package_relocation': (json.loads(args.relocated_dex.with_suffix(args.relocated_dex.suffix + '.json').read_text())
                               if args.relocated_dex else None),
        'sdk_lifecycle_policy': (json.loads(args.disabled_sdk_dex.with_suffix(args.disabled_sdk_dex.suffix + '.json').read_text())
                                 if args.disabled_sdk_dex else None),
        'google_ads_pruning': (json.loads(args.pruned_ads_dex.with_suffix(args.pruned_ads_dex.suffix + '.json').read_text())
                               if args.pruned_ads_dex else None),
        'facebook_sdk_pruning': (json.loads(args.pruned_facebook_dex.with_suffix(args.pruned_facebook_dex.suffix + '.json').read_text())
                                 if args.pruned_facebook_dex else None),
        'build_revision_metadata': revision,
        'unchanged_assets_native_sha256': payload,
        'scope': 'SDK and game Janus discovery constructors; unrelated legacy service clients are not migrated.',
    }
    output.with_suffix('.provenance.json').write_text(json.dumps(report, indent=2) + '\n')
    print('Built complete debug APK with configured backend:', output)


if __name__ == '__main__':
    main()
