"""Protect compiler/runtime separation and original bytecode identity."""

import hashlib
import json
from pathlib import Path
import sys
import tempfile
import unittest
import warnings
import zipfile


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
import prepare_core_dependencies as prepare


class PrepareCoreDependenciesTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)

    def jar(self, name, entries):
        path = self.root / name
        with zipfile.ZipFile(path, 'w') as output:
            for key, data in entries.items():
                output.writestr(key, data, compress_type=zipfile.ZIP_DEFLATED)
        return path

    def test_reviewed_metadata_is_accepted_and_changed_pins_are_rejected(self):
        metadata = json.loads((ROOT / 'docs/ORIGINAL_BYTECODE_TOOLS.json').read_text())
        path = self.root / 'pins.json'
        path.write_text(json.dumps(metadata))
        prepare.verify_pins(path)
        for field, value in (('original_apk_sha256', '0' * 64), ('classes_dex_sha256', '1' * 64),
                             ('dex2jar_class_count', 100)):
            with self.subTest(field=field):
                changed = {**metadata, field: value}
                path.write_text(json.dumps(changed))
                with self.assertRaisesRegex(ValueError, 'reviewed original APK pins'):
                    prepare.verify_pins(path)
        metadata['tools'][1]['source'] = 'https://unreviewed.invalid/tool.zip'
        path.write_text(json.dumps(metadata))
        with self.assertRaisesRegex(ValueError, 'reviewed release pin'):
            prepare.verify_pins(path)

    def test_original_apk_and_dex_are_both_verified(self):
        dex = b'dex-original-bytes'
        apk = self.jar('original.apk', {'classes.dex': dex, 'assets/data.bin': b'data'})
        apk_hash = prepare.file_sha256(apk)
        dex_hash = hashlib.sha256(dex).hexdigest()
        self.assertEqual(prepare.verify_original(apk, apk_hash, dex_hash), dex_hash)
        with self.assertRaisesRegex(ValueError, 'APK SHA-256'):
            prepare.verify_original(apk, '0' * 64, dex_hash)
        with self.assertRaisesRegex(ValueError, 'classes.dex SHA-256'):
            prepare.verify_original(apk, apk_hash, '0' * 64)

    def test_unreviewed_multidex_or_duplicate_dex_is_rejected(self):
        for entries in ({'classes.dex': b'first', 'classes2.dex': b'second'}, {'asset': b'only'}):
            apk = self.jar('original.apk', entries)
            with self.assertRaisesRegex(ValueError, 'single classes.dex'):
                prepare.verify_original(apk, prepare.file_sha256(apk), '0' * 64)
        apk = self.jar('duplicate.apk', {'classes.dex': b'first'})
        with warnings.catch_warnings():
            warnings.simplefilter('ignore', UserWarning)
            with zipfile.ZipFile(apk, 'a') as output:
                output.writestr('classes.dex', b'duplicate')
        with self.assertRaisesRegex(ValueError, 'duplicate entries'):
            prepare.verify_original(apk, prepare.file_sha256(apk), '0' * 64)

    def test_tool_archive_must_match_published_reviewed_fingerprint(self):
        archive = self.root / 'untrusted.zip'
        archive.write_bytes(b'not-the-pinned-release')
        with self.assertRaisesRegex(ValueError, 'release archive SHA-256 mismatch'):
            prepare.verify_tool_archive(archive)

    def test_source_exclusion_keeps_partial_l_and_original_empty_g(self):
        source = self.root / 'source'
        source.mkdir()
        for name in ('a', 'ci', 'l'):
            (source / (name + '.java')).write_text('source-placeholder')
        names = prepare.source_classes(source, expected_count=2)
        self.assertEqual(names, {prepare.CORE_PACKAGE + 'a.class', prepare.CORE_PACKAGE + 'ci.class'})
        for helper in prepare.ADDED_CORE_SOURCES:
            (source / (helper + '.java')).write_text('reviewed new helper, no original identity')
        self.assertEqual(prepare.source_classes(source, expected_count=2), names)
        with self.assertRaisesRegex(ValueError, 'Expected 108 recovered core sources'):
            prepare.source_classes(source)
        (source / 'g.java').write_text('must-not-replace-original-empty-class')
        with self.assertRaisesRegex(ValueError, 'empty g class must remain'):
            prepare.source_classes(source, expected_count=3)

    def test_resource_exclusion_covers_only_five_reviewed_outer_and_nested_identities(self):
        for name in prepare.RESOURCE_CLASSES:
            self.assertTrue(prepare.is_resource_class(name + '.class'))
            self.assertTrue(prepare.is_resource_class(name + '$styleable.class'))
            self.assertFalse(prepare.is_resource_class(name + 'emainder.class'))
        self.assertFalse(prepare.is_resource_class('other/package/R.class'))
        self.assertFalse(prepare.is_resource_class('com/facebook/Request.class'))

    def test_reviewed_kotlin_streams_replace_binary_lookup_without_weakening_base_profile(self):
        source = self.root / 'app/src/main/java/com/quanhoang/greenfarm3/S800x480'
        kotlin = self.root / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
        source.mkdir(parents=True); kotlin.mkdir(parents=True)
        for name in ('a', 'ci', 'l'):
            (source / (name + '.java')).write_text('JADX reference')
        for name in ('l', 'g', 'GameSaveStore'):
            (kotlin / (name + '.kt')).write_text('Reviewed Kotlin source')
        names = prepare.source_classes(source, expected_count=2)
        self.assertEqual(names, {prepare.CORE_PACKAGE + n + '.class' for n in ('a', 'ci', 'l', 'g')})
        with self.assertRaisesRegex(ValueError, 'Expected 108 recovered core sources'):
            prepare.source_classes(source)

    def test_java_and_kotlin_same_game_identity_are_rejected(self):
        source = self.root / 'source'
        source.mkdir()
        (source / 'ci.java').write_text('old')
        (source / 'ci.kt').write_text('new')
        with self.assertRaisesRegex(ValueError, 'Duplicate Java/Kotlin'):
            prepare.source_classes(source, expected_count=1)

    def test_filter_removes_source_and_resource_classes_only_preserving_bytes(self):
        core = {prepare.CORE_PACKAGE + 'ci.class'}
        kept = {prepare.CORE_PACKAGE + 'l.class': b'original-partial-data-input',
                prepare.CORE_PACKAGE + 'g.class': b'original-empty-g',
                'javax/microedition/a/a.class': b'original-connection-flags-not-yet-modified',
                'com/facebook/Request.class': b'original-sdk-code',
                'META-INF/LICENSE': b'license'}
        removed = {prepare.CORE_PACKAGE + 'ci.class': b'old-game-code'}
        for name in prepare.RESOURCE_CLASSES:
            removed[name + '.class'] = b'old-r-outer'
            removed[name + '$string.class'] = b'old-r-inner'
        source = self.jar('source.jar', {**kept, **removed})
        before = source.read_bytes()
        output = self.root / 'filtered.jar'
        self.assertEqual(set(prepare.filter_dependencies(source, output, core)), set(removed))
        with zipfile.ZipFile(source) as old, zipfile.ZipFile(output) as new:
            self.assertEqual(set(new.namelist()), set(kept))
            for name, data in kept.items():
                self.assertEqual(new.read(name), data)
                self.assertEqual(old.getinfo(name).compress_type, new.getinfo(name).compress_type)
        self.assertEqual(source.read_bytes(), before)

    def test_filter_rejects_missing_core_class_and_same_input_output(self):
        source = self.jar('source.jar', {'other.class': b'other'})
        with self.assertRaisesRegex(ValueError, 'lacks recovered core identities'):
            prepare.filter_dependencies(source, self.root / 'filtered.jar', {prepare.CORE_PACKAGE + 'ci.class'})
        with self.assertRaisesRegex(ValueError, 'must not overwrite'):
            prepare.filter_dependencies(source, source, set())

    def test_conversion_rejects_logged_errors_wrong_count_and_duplicate_classes(self):
        source = self.jar('converted.jar', {'a.class': b'a', 'b.class': b'b'})
        self.assertEqual(prepare.validate_conversion(source, 'dex2jar original.apk -> result.jar', 2), 2)
        for message in ('ERROR: method conversion', 'java.lang.RuntimeException: bad control flow',
                        'conversion failed', 'Detail Error Information in File failed-error.zip'):
            with self.subTest(message=message):
                with self.assertRaisesRegex(ValueError, 'conversion errors'):
                    prepare.validate_conversion(source, message, 2)
        with self.assertRaisesRegex(ValueError, 'class count 2 differs from expected 6383'):
            prepare.validate_conversion(source, '')
        with warnings.catch_warnings():
            warnings.simplefilter('ignore', UserWarning)
            with zipfile.ZipFile(source, 'a') as output:
                output.writestr('a.class', b'duplicate')
        with self.assertRaisesRegex(ValueError, 'duplicate entries'):
            prepare.validate_conversion(source, '', 3)


if __name__ == '__main__':
    unittest.main()
