"""APK repacking must preserve executable/data payload, not just produce a ZIP."""

import copy
import hashlib
import json
from pathlib import Path
import shutil
import sys
import tempfile
from types import SimpleNamespace
import unittest
from unittest import mock
import warnings
import zipfile


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
import build_binary_baseline as baseline
import android_build_tools


class BinaryBaselineTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.original = self.root / 'original.apk'
        self.resources = self.root / 'resources.ap_'
        self.rebuilt = self.root / 'rebuilt.apk'
        self.payload = {
            'classes.dex': (b'dex-original-primary', zipfile.ZIP_DEFLATED),
            'classes2.dex': (b'dex-original-secondary', zipfile.ZIP_STORED),
            'assets/packs/farm.bin': (b'original-farm-pack' * 40, zipfile.ZIP_DEFLATED),
            'lib/x86/libnativeU.so': (b'original-native-elf', zipfile.ZIP_STORED),
        }
        self.old_resources = {
            'AndroidManifest.xml': (b'old-manifest', zipfile.ZIP_DEFLATED),
            'resources.arsc': (b'old-table', zipfile.ZIP_STORED),
            'res/raw/removed.bin': (b'old-only-resource', zipfile.ZIP_STORED),
        }
        self.linked = {
            'AndroidManifest.xml': (b'new-manifest', zipfile.ZIP_DEFLATED),
            'resources.arsc': (b'new-table', zipfile.ZIP_STORED),
            'res/drawable/icon.png': (b'new-image', zipfile.ZIP_DEFLATED),
        }
        signatures = {name: (b'old-signature', zipfile.ZIP_DEFLATED) for name in (
            'META-INF/MANIFEST.MF', 'META-INF/GAMELOFT.SF', 'META-INF/GAMELOFT.RSA',
            'META-INF/other.DSA', 'META-INF/lower.ec')}
        metadata = {
            'META-INF/LICENSE.txt': (b'license-must-survive', zipfile.ZIP_DEFLATED),
            'play-services-ads.properties': (b'version=15.0.0', zipfile.ZIP_STORED),
        }
        self.write(self.original, {**self.payload, **self.old_resources, **signatures, **metadata})
        self.write(self.resources, self.linked)

    @staticmethod
    def write(path, entries):
        with zipfile.ZipFile(path, 'w') as archive:
            for name, (content, method) in entries.items():
                archive.writestr(name, content, compress_type=method)

    def rewrite(self, source, destination, *, omit=None, content=None, compression=None, extra=None):
        with zipfile.ZipFile(source) as archive, zipfile.ZipFile(destination, 'w') as output:
            for entry in archive.infolist():
                if entry.filename == omit:
                    continue
                info = copy.copy(entry)
                if compression and entry.filename == compression[0]:
                    info.compress_type = compression[1]
                data = archive.read(entry)
                if content and entry.filename == content[0]:
                    data = content[1]
                output.writestr(info, data)
            if extra:
                output.writestr(extra[0], extra[1])

    def test_repack_replaces_only_resources_and_keeps_payload_compression(self):
        baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
        hashes = baseline.verify_payload(self.original, self.rebuilt)
        self.assertEqual(hashes, {name: hashlib.sha256(data).hexdigest()
                                  for name, (data, _) in self.payload.items()})
        with zipfile.ZipFile(self.rebuilt) as archive:
            for name, (data, compression) in {**self.payload, **self.linked}.items():
                self.assertEqual(archive.read(name), data)
                self.assertEqual(archive.getinfo(name).compress_type, compression)
            self.assertNotIn('res/raw/removed.bin', archive.namelist())
            self.assertEqual(archive.read('META-INF/LICENSE.txt'), b'license-must-survive')
            self.assertEqual(archive.read('play-services-ads.properties'), b'version=15.0.0')
            self.assertFalse(any(baseline.SIGNATURE.fullmatch(name) for name in archive.namelist()))

    def test_main_uses_windows_jar_signer_argument_arrays_with_space_paths(self):
        sdk = self.root / 'Android SDK & tools'
        tools = sdk / 'build-tools/35.0.0'
        java = self.root / 'Java Home/bin/java.exe'
        zipalign, jar = tools / 'zipalign.exe', tools / 'lib/apksigner.jar'
        for path in (java, zipalign, jar):
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(b'fixture only; never executed')
        keystore = self.root / 'keys with spaces/debug.keystore'
        keystore.parent.mkdir()
        keystore.write_bytes(b'fixture key; reused without keytool')
        output = self.root / 'debug output/app-debug.apk'
        commands = []

        def emulate_external_copy(command, **kwargs):
            # Alignment/signature operations are modeled as copies. This checks
            # argv wiring and payload selection, not real signing or alignment.
            self.assertIsInstance(command, list)
            self.assertEqual(kwargs, {'check': True})
            self.assertFalse(any(value.lower().endswith(('.bat', '.cmd')) for value in command))
            commands.append(command)
            if command[0] == str(zipalign) and '-f' in command:
                shutil.copyfile(command[-2], command[-1])
            elif command[:3] == [str(java), '-jar', str(jar)] and 'sign' in command:
                self.assertEqual(command[command.index('--ks') + 1], str(keystore))
                shutil.copyfile(command[-1], command[command.index('--out') + 1])

        argv = ['build_binary_baseline.py', '--original', str(self.original),
                '--resources', str(self.resources), '--sdk', str(sdk),
                '--output', str(output), '--keystore', str(keystore)]
        simulated_os = SimpleNamespace(name='nt', environ={'JAVA_HOME': str(java.parent.parent)})
        pin = baseline.sha256(self.original.read_bytes())
        with mock.patch.object(sys, 'argv', argv), \
                mock.patch.object(baseline, 'EXPECTED_APK_SHA256', pin), \
                mock.patch.object(android_build_tools, 'os', simulated_os), \
                mock.patch.object(baseline.subprocess, 'run', side_effect=emulate_external_copy):
            baseline.main()
        self.assertEqual(len(commands), 4)
        self.assertEqual(commands[0][0], str(zipalign))
        self.assertEqual(commands[1][:4], [str(java), '-jar', str(jar), 'sign'])
        self.assertEqual(commands[2][:4], [str(java), '-jar', str(jar), 'verify'])
        self.assertEqual(commands[3][0], str(zipalign))
        self.assertEqual(baseline.verify_payload(self.original, output), {
            name: hashlib.sha256(data).hexdigest() for name, (data, _) in self.payload.items()})
        report = json.loads(output.with_suffix('.provenance.json').read_text())
        self.assertEqual(report['original_apk_sha256'], pin)
        self.assertEqual(report['output_apk_sha256'], baseline.sha256(output.read_bytes()))

    def test_unexpected_content_and_traversal_in_resource_archive_are_rejected(self):
        for name in ('classes.dex', 'assets/game.bin', 'lib/x86/fake.so', 'META-INF/OLD.RSA',
                     'res/../classes.dex', 'res//bad.png', 'res/./bad.png', 'res/dir\\bad.png'):
            with self.subTest(name=name):
                self.write(self.resources, {**self.linked, name: (b'unexpected', zipfile.ZIP_STORED)})
                with self.assertRaisesRegex(ValueError, 'Unexpected non-resource'):
                    baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
                self.assertFalse(self.rebuilt.exists())

    def test_missing_manifest_or_table_in_resource_archive_is_rejected(self):
        for name in ('AndroidManifest.xml', 'resources.arsc'):
            with self.subTest(name=name):
                self.write(self.resources, {key: value for key, value in self.linked.items() if key != name})
                with self.assertRaisesRegex(ValueError, 'lacks manifest or resource table'):
                    baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)

    def test_duplicate_entries_in_either_input_are_rejected(self):
        for target in ('resources', 'original'):
            with self.subTest(target=target):
                path = getattr(self, target)
                name = 'resources.arsc' if target == 'resources' else 'classes.dex'
                with warnings.catch_warnings():
                    warnings.simplefilter('ignore', UserWarning)
                    with zipfile.ZipFile(path, 'a') as archive:
                        archive.writestr(name, b'duplicate')
                with self.assertRaisesRegex(ValueError, 'duplicate entries'):
                    baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
                # Reset the input for the next subtest.
                self.write(path, self.linked if target == 'resources' else self.payload)

    def test_original_without_dex_is_rejected(self):
        self.write(self.original, self.old_resources)
        with self.assertRaisesRegex(ValueError, 'contains no DEX'):
            baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)

    def test_helper_cannot_overwrite_either_input(self):
        for destination in (self.original, self.resources):
            before = destination.read_bytes()
            with self.assertRaisesRegex(ValueError, 'must not overwrite an input'):
                baseline.assemble_unsigned(self.original, self.resources, destination)
            self.assertEqual(destination.read_bytes(), before)

    def test_verifier_rejects_changed_payload_bytes(self):
        baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
        bad = self.root / 'changed.apk'
        for name in self.payload:
            with self.subTest(name=name):
                self.rewrite(self.rebuilt, bad, content=(name, b'changed'))
                with self.assertRaisesRegex(ValueError, 'Payload changed'):
                    baseline.verify_payload(self.original, bad)

    def test_verifier_rejects_missing_or_extra_payload(self):
        baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
        bad = self.root / 'changed.apk'
        for name in self.payload:
            with self.subTest(missing=name):
                self.rewrite(self.rebuilt, bad, omit=name)
                with self.assertRaisesRegex(ValueError, 'changed the DEX/assets/native file set'):
                    baseline.verify_payload(self.original, bad)
        self.rewrite(self.rebuilt, bad, extra=('assets/new.bin', b'new-data'))
        with self.assertRaisesRegex(ValueError, 'changed the DEX/assets/native file set'):
            baseline.verify_payload(self.original, bad)

    def test_verifier_rejects_payload_recompression_even_with_unchanged_bytes(self):
        baseline.assemble_unsigned(self.original, self.resources, self.rebuilt)
        bad = self.root / 'recompressed.apk'
        self.rewrite(self.rebuilt, bad, compression=('lib/x86/libnativeU.so', zipfile.ZIP_DEFLATED))
        with self.assertRaisesRegex(ValueError, 'Payload compression changed'):
            baseline.verify_payload(self.original, bad)


if __name__ == '__main__':
    unittest.main()
