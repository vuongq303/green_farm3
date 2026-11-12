#!/usr/bin/env python3
"""Build a debug baseline from original DEX and Gradle-linked resources.

This is deliberately NOT a Java source build. Keep the executable game code,
assets and native libraries byte-for-byte while validating the restored resource
and manifest pipeline. No ad, purchase, root or online checks are patched.
"""

import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
import zipfile


EXPECTED_APK_SHA256 = '34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b'
DEX = re.compile(r'classes(?:[2-9]|[1-9][0-9]+)?\.dex$')
SIGNATURE = re.compile(r'META-INF/(?:MANIFEST\.MF|[^/]+\.(?:SF|RSA|DSA|EC))$', re.I)


def sha256(data):
    return hashlib.sha256(data).hexdigest()


def is_resource(name):
    # A resource archive must not smuggle executable/payload entries through a
    # traversal spelling such as res/../classes.dex.
    if ('\\' in name or '\x00' in name or name.startswith('/') or '//' in name
            or any(part in ('.', '..') for part in name.rstrip('/').split('/'))):
        return False
    return name in ('AndroidManifest.xml', 'resources.arsc') or name.startswith('res/')


def is_payload(name):
    return bool(DEX.fullmatch(name)) or name.startswith(('assets/', 'lib/'))


def assemble_unsigned(original, resources, destination):
    """Replace only linked resources/manifest; never carry over old signatures."""
    if Path(destination).resolve() in (Path(original).resolve(), Path(resources).resolve()):
        raise ValueError('Destination must not overwrite an input APK')
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(resources) as linked:
        names = linked.namelist()
        if len(names) != len(set(names)):
            raise ValueError('Linked resource APK contains duplicate entries')
        if not {'AndroidManifest.xml', 'resources.arsc'}.issubset(names):
            raise ValueError('Linked resource APK lacks manifest or resource table')
        if any(not is_resource(n) for n in names):
            raise ValueError('Unexpected non-resource content in linked resource APK')
        if not any(DEX.fullmatch(n) for n in source.namelist()):
            raise ValueError('Original APK contains no DEX')
        if len(source.namelist()) != len(set(source.namelist())):
            raise ValueError('Original APK contains duplicate entries')
        with zipfile.ZipFile(destination, 'w') as out:
            for entry in source.infolist():
                if not is_resource(entry.filename) and not SIGNATURE.fullmatch(entry.filename):
                    out.writestr(entry, source.read(entry))
            for entry in linked.infolist():
                out.writestr(entry, linked.read(entry))


def verify_payload(original, rebuilt):
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(rebuilt) as out:
        names = sorted(n for n in source.namelist() if is_payload(n) and not n.endswith('/'))
        actual = sorted(n for n in out.namelist() if is_payload(n) and not n.endswith('/'))
        if names != actual:
            raise ValueError('Rebuilt APK changed the DEX/assets/native file set')
        hashes = {}
        for name in names:
            before, after = source.read(name), out.read(name)
            if before != after:
                raise ValueError('Payload changed: ' + name)
            if source.getinfo(name).compress_type != out.getinfo(name).compress_type:
                raise ValueError('Payload compression changed: ' + name)
            hashes[name] = sha256(before)
        return hashes


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--original', type=Path, required=True)
    parser.add_argument('--resources', type=Path, required=True)
    parser.add_argument('--sdk', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    parser.add_argument('--keystore', type=Path, required=True)
    args = parser.parse_args()
    original = args.original.resolve()
    if sha256(original.read_bytes()) != EXPECTED_APK_SHA256:
        raise ValueError('Original APK checksum differs; review the supplied version before rebuilding')
    output = args.output.resolve()
    if output in (original, args.resources.resolve()):
        raise ValueError('Output must not overwrite an input APK')
    tools = args.sdk / 'build-tools/35.0.0'
    keytool = Path(os.environ.get('JAVA_HOME', '')) / 'bin/keytool'
    if not keytool.is_file():
        keytool = shutil.which('keytool')
    if not keytool:
        raise ValueError('JDK keytool is required')
    keystore = args.keystore.resolve()
    keystore.parent.mkdir(parents=True, exist_ok=True)
    if not keystore.exists():
        subprocess.run([str(keytool), '-genkeypair', '-keystore', str(keystore),
                        '-storepass', 'android', '-alias', 'androiddebugkey', '-keypass', 'android',
                        '-keyalg', 'RSA', '-keysize', '2048', '-validity', '10000',
                        '-dname', 'CN=Android Debug,O=Android,C=US'], check=True)
    output.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='baseline-', dir=output.parent) as temp:
        temp = Path(temp)
        unsigned, aligned, signed = (temp / n for n in ('unsigned.apk', 'aligned.apk', 'signed.apk'))
        assemble_unsigned(original, args.resources, unsigned)
        subprocess.run([str(tools / 'zipalign'), '-f', '-P', '16', '4', str(unsigned), str(aligned)], check=True)
        subprocess.run([str(tools / 'apksigner'), 'sign', '--ks', str(keystore),
                        '--ks-key-alias', 'androiddebugkey', '--ks-pass', 'pass:android',
                        '--key-pass', 'pass:android', '--out', str(signed), str(aligned)], check=True)
        subprocess.run([str(tools / 'apksigner'), 'verify', '--verbose', str(signed)], check=True)
        subprocess.run([str(tools / 'zipalign'), '-c', '-P', '16', '4', str(signed)], check=True)
        hashes = verify_payload(original, signed)
        shutil.copyfile(signed, output)
    provenance = {'mode': 'binary-baseline-not-java-source-build',
                  'original_apk_sha256': EXPECTED_APK_SHA256,
                  'output_apk_sha256': sha256(output.read_bytes()),
                  'unchanged_payload_sha256': hashes,
                  'resource_apk_sha256': sha256(args.resources.read_bytes())}
    output.with_suffix('.provenance.json').write_text(json.dumps(provenance, indent=2) + '\n')
    print(f'Built binary baseline: {output}; {len(hashes)} DEX/assets/native entries unchanged.')


if __name__ == '__main__':
    main()
