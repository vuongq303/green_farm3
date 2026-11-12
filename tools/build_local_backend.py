#!/usr/bin/env python3
"""Build a separate research APK with one verified Janus discovery URL change."""

import argparse
import json
from pathlib import Path
import shutil
import subprocess
import tempfile
import zipfile

from build_binary_baseline import EXPECTED_APK_SHA256, SIGNATURE, sha256
from build_core_source import regenerate_build_revision, verify_assets_native


TARGET_CLASS = 'Lcom/gameloft/a/b/a;'
TARGET_METHOD = 'aL(Ljava/lang/String;)Ljava/lang/String;'
ORIGINAL_URL = 'https://eve.gameloft.com/'


def prepare(original, base, patched_dex, output):
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
    if (baseline.get('mode') != 'java-core-with-original-dex-sdk-dependencies'
            or baseline.get('original_apk_sha256') != EXPECTED_APK_SHA256
            or baseline.get('output_apk_sha256') != sha256(base.read_bytes())
            or baseline.get('dex_merge', {}).get('original_non_overlay_semantics_verified') is not True
            or baseline.get('dex_merge', {}).get('compiled_overlay_semantics_verified') is not True):
        raise ValueError('Base APK provenance mismatch')
    if (patch.get('target_class') != TARGET_CLASS
            or patch.get('target_method') != TARGET_METHOD
            or patch.get('original_url') != ORIGINAL_URL
            or type(patch.get('replacement_count')) is not int
            or patch.get('replacement_count') != 1
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
                    data = patched_dex.read_bytes()
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
    args = parser.parse_args()
    output = args.output.resolve()
    inputs = (args.original, args.base, args.patched_dex, args.keystore,
              args.base.with_suffix('.provenance.json'),
              args.patched_dex.with_suffix(args.patched_dex.suffix + '.json'))
    if any(output == p.resolve() or (output.exists() and p.exists() and output.samefile(p)) for p in inputs):
        raise ValueError('Output must not overwrite an input, key or provenance report')
    output.parent.mkdir(parents=True, exist_ok=True)
    build_tools = args.sdk / 'build-tools/35.0.0'
    with tempfile.TemporaryDirectory(prefix='local-backend-', dir=output.parent) as directory:
        temp = Path(directory)
        unsigned, aligned, signed = [temp / n for n in ('unsigned.apk', 'aligned.apk', 'signed.apk')]
        baseline, patch = prepare(args.original, args.base, args.patched_dex, unsigned)
        revision = regenerate_build_revision(args.original, unsigned, temp)
        subprocess.run([str(build_tools / 'zipalign'), '-f', '-P', '16', '4', str(unsigned), str(aligned)], check=True)
        subprocess.run([str(build_tools / 'apksigner'), 'sign', '--ks', str(args.keystore),
                        '--ks-key-alias', 'androiddebugkey', '--ks-pass', 'pass:android',
                        '--key-pass', 'pass:android', '--out', str(signed), str(aligned)], check=True)
        subprocess.run([str(build_tools / 'apksigner'), 'verify', '--verbose', str(signed)], check=True)
        subprocess.run([str(build_tools / 'zipalign'), '-c', '-P', '16', '4', str(signed)], check=True)
        payload = verify_assets_native(args.original, signed)
        with zipfile.ZipFile(signed) as apk:
            if sha256(apk.read('classes.dex')) != patch['output_dex_sha256']:
                raise ValueError('Signed APK DEX differs from verified endpoint patch')
        shutil.copyfile(signed, output)
    report = {
        'mode': 'java-core-with-local-janus-backend',
        'original_apk_sha256': EXPECTED_APK_SHA256,
        'base_apk_sha256': baseline['output_apk_sha256'],
        'output_apk_sha256': sha256(output.read_bytes()),
        'endpoint_patch': patch,
        'build_revision_metadata': revision,
        'unchanged_assets_native_sha256': payload,
        'scope': 'One Janus discovery URL; other legacy network clients are not migrated.',
    }
    output.with_suffix('.provenance.json').write_text(json.dumps(report, indent=2) + '\n')
    print('Built separate local-backend research APK:', output)


if __name__ == '__main__':
    main()
