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


TARGET_CLASS = 'Lcom/gameloft/a/b/a;'
CORE_TARGET_CLASS = 'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/s;'
TARGET_CLASSES = [TARGET_CLASS, CORE_TARGET_CLASS]
TARGET_METHOD = 'aL(Ljava/lang/String;)Ljava/lang/String;'
ORIGINAL_URL = 'https://eve.gameloft.com/'


def prepare(original, base, patched_dex, output, relocated_dex=None):
    original, base, patched_dex, output = map(Path, (original, base, patched_dex, output))
    base_report = base.with_suffix('.provenance.json')
    patch_report = patched_dex.with_suffix(patched_dex.suffix + '.json')
    for source in (original, base, base_report, patched_dex, patch_report):
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
    args = parser.parse_args()
    output = args.output.resolve()
    inputs = (args.original, args.base, args.patched_dex, args.keystore,
              args.base.with_suffix('.provenance.json'),
              args.patched_dex.with_suffix(args.patched_dex.suffix + '.json'))
    if args.relocated_dex is not None:
        inputs += (args.relocated_dex, args.relocated_dex.with_suffix(args.relocated_dex.suffix + '.json'))
    if any(output == p.resolve() or (output.exists() and p.exists() and output.samefile(p)) for p in inputs):
        raise ValueError('Output must not overwrite an input, key or provenance report')
    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='local-backend-', dir=output.parent) as directory:
        temp = Path(directory)
        unsigned, aligned, signed = [temp / n for n in ('unsigned.apk', 'aligned.apk', 'signed.apk')]
        baseline, patch = prepare(args.original, args.base, args.patched_dex, unsigned, args.relocated_dex)
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
            packaged_dex_hash = sha256((args.relocated_dex or args.patched_dex).read_bytes())
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
        'build_revision_metadata': revision,
        'unchanged_assets_native_sha256': payload,
        'scope': 'SDK and game Janus discovery constructors; unrelated legacy service clients are not migrated.',
    }
    output.with_suffix('.provenance.json').write_text(json.dumps(report, indent=2) + '\n')
    print('Built complete debug APK with configured backend:', output)


if __name__ == '__main__':
    main()
