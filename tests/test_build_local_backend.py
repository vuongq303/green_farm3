"""Offline packaging bindings for the separate local-backend research variant.

Small ZIP/DEX fixtures model already verified reports. These tests check that
prepare() binds those reports to its exact inputs and preserves the base
payload. They do not validate executable bytecode, endpoint semantics, revision
generation, APK signing, service responses or device behavior.
"""

import copy
import json
import os
from pathlib import Path
import sys
import tempfile
import unittest
from unittest import mock
import warnings
import zipfile


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import build_local_backend as build


class LocalBackendPackagingTest(unittest.TestCase):
    def setUp(self):
        temporary = tempfile.TemporaryDirectory(prefix="local-backend-packaging-")
        self.addCleanup(temporary.cleanup)
        self.directory = Path(temporary.name)
        self.original = self.directory / "original.apk"
        self.base = self.directory / "core-source.apk"
        self.dex = self.directory / "endpoint.dex"
        self.output = self.directory / "prepared.apk"
        self.base_report_path = self.base.with_suffix(".provenance.json")
        self.patch_report_path = self.dex.with_suffix(".dex.json")
        self.original_metadata = b"original definition with original REVISION-2408"
        self.base_metadata = b"original definition with generated REVISION-2408"
        self.base_dex = b"dex\n035\0" + b"verified Java-core DEX fixture"
        self.dex.write_bytes(b"dex\n035\0" + b"same program with one Janus discovery URL fixture")
        self.make_archive(self.original, {
            "AndroidManifest.xml": b"original release manifest",
            "resources.arsc": b"original resource table",
            "res/raw/content.bin": self.original_metadata,
            "classes.dex": b"original DEX fixture",
            "assets/farm.bin": b"original gameplay asset",
            "lib/x86/libnativeU.so": b"original native library",
        })
        self.base_entries = {
            "AndroidManifest.xml": b"verified Java-core debug manifest",
            "resources.arsc": b"verified rebuilt resource table",
            "res/layout/current.xml": b"verified rebuilt layout",
            "res/raw/content.bin": self.base_metadata,
            "classes.dex": self.base_dex,
            "assets/farm.bin": b"original gameplay asset",
            "lib/x86/libnativeU.so": b"original native library",
            "support.txt": b"base metadata preserved verbatim",
            "META-INF/CERT.RSA": b"obsolete base signature",
            "META-INF/CERT.SF": b"obsolete base signature metadata",
            "META-INF/MANIFEST.MF": b"obsolete signing manifest",
        }
        self.make_archive(self.base, self.base_entries)
        original_hash = build.sha256(self.original.read_bytes())
        pin = mock.patch.object(build, "EXPECTED_APK_SHA256", original_hash)
        pin.start()
        self.addCleanup(pin.stop)
        self.baseline = {
            "mode": "java-core-with-original-dex-sdk-dependencies",
            "original_apk_sha256": original_hash,
            "output_apk_sha256": build.sha256(self.base.read_bytes()),
            "merged_dex_sha256": build.sha256(self.base_dex),
            "dex_merge": {
                "original_non_overlay_semantics_verified": True,
                "compiled_overlay_semantics_verified": True,
            },
            "build_revision_metadata": {
                "original_guard_accepts_generated_revision": True,
                "generated_metadata_sha256": build.sha256(self.base_metadata),
            },
        }
        self.patch = {
            "target_class": build.TARGET_CLASS,
            "target_method": build.TARGET_METHOD,
            "original_url": build.ORIGINAL_URL,
            "replacement_count": 1,
            "unchanged_other_classes_verified": True,
            "reverse_patch_semantics_verified": True,
            "input_dex_sha256": build.sha256(self.base_dex),
            "output_dex_sha256": build.sha256(self.dex.read_bytes()),
        }
        self.write_reports()

    @staticmethod
    def make_archive(path, entries, extra=()):
        with warnings.catch_warnings():
            warnings.simplefilter("ignore", UserWarning)
            with zipfile.ZipFile(path, "w") as archive:
                for name, data in list(entries.items()) + list(extra):
                    compression = (zipfile.ZIP_STORED if name.startswith("lib/")
                                   else zipfile.ZIP_DEFLATED)
                    archive.writestr(name, data, compress_type=compression)

    def write_reports(self):
        self.base_report_path.write_text(json.dumps(self.baseline))
        self.patch_report_path.write_text(json.dumps(self.patch))

    def prepare(self, output=None):
        return build.prepare(self.original, self.base, self.dex, output or self.output)

    def assert_rejected_before_output(self, message):
        with self.assertRaisesRegex(ValueError, message):
            self.prepare()
        self.assertFalse(self.output.exists())

    def test_replaces_only_dex_and_resets_revision_preimage_while_preserving_base(self):
        inputs = {p: p.read_bytes() for p in (
            self.original, self.base, self.base_report_path, self.dex, self.patch_report_path)}
        baseline, patch = self.prepare()
        self.assertEqual(baseline, self.baseline)
        self.assertEqual(patch, self.patch)
        with zipfile.ZipFile(self.base) as before, zipfile.ZipFile(self.output) as after:
            retained = [entry for entry in before.infolist()
                        if not build.SIGNATURE.fullmatch(entry.filename)]
            self.assertEqual(after.namelist(), [entry.filename for entry in retained])
            changed = {entry.filename for entry in retained
                       if before.read(entry) != after.read(entry.filename)}
            self.assertEqual(changed, {"classes.dex", "res/raw/content.bin"})
            self.assertEqual(after.read("classes.dex"), self.dex.read_bytes())
            # The shared revision generator requires the original content.bin
            # preimage, not the already generated revision in the Java-core APK.
            self.assertEqual(after.read("res/raw/content.bin"), self.original_metadata)
            for entry in retained:
                self.assertEqual(after.getinfo(entry.filename).compress_type, entry.compress_type)
                if entry.filename not in changed:
                    self.assertEqual(after.read(entry.filename), before.read(entry))
            self.assertEqual(after.read("AndroidManifest.xml"), self.base_entries["AndroidManifest.xml"])
            self.assertEqual(after.read("resources.arsc"), self.base_entries["resources.arsc"])
            self.assertEqual(after.read("assets/farm.bin"), self.base_entries["assets/farm.bin"])
            self.assertEqual(after.read("lib/x86/libnativeU.so"), self.base_entries["lib/x86/libnativeU.so"])
        for path, data in inputs.items():
            self.assertEqual(path.read_bytes(), data, str(path))

    def test_rejects_changed_original_or_base_apk(self):
        for source, message in ((self.original, "Unexpected original APK"),
                                (self.base, "Base APK provenance mismatch")):
            with self.subTest(source=source.name):
                valid = source.read_bytes()
                source.write_bytes(valid + b"unreported modification")
                self.assert_rejected_before_output(message)
                source.write_bytes(valid)

    def test_rejects_base_provenance_hashes_mode_and_false_semantic_proofs(self):
        cases = [
            ("mode", "binary-baseline"),
            ("original_apk_sha256", "0" * 64),
            ("output_apk_sha256", "0" * 64),
        ]
        valid = copy.deepcopy(self.baseline)
        for field, invalid in cases:
            with self.subTest(field=field):
                self.baseline = copy.deepcopy(valid)
                self.baseline[field] = invalid
                self.write_reports()
                self.assert_rejected_before_output("Base APK provenance mismatch")
        for field in ("original_non_overlay_semantics_verified", "compiled_overlay_semantics_verified"):
            with self.subTest(field=field):
                self.baseline = copy.deepcopy(valid)
                self.baseline["dex_merge"][field] = False
                self.write_reports()
                self.assert_rejected_before_output("Base APK provenance mismatch")

    def test_rejects_patch_hash_target_count_and_false_semantic_proofs(self):
        valid = copy.deepcopy(self.patch)
        cases = [
            ("target_class", "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/s;"),
            ("target_method", "aL(Ljava/lang/String;)V"),
            ("original_url", "https://other.example/"),
            ("replacement_count", 0),
            ("replacement_count", 2),
            ("replacement_count", True),
            ("unchanged_other_classes_verified", False),
            ("reverse_patch_semantics_verified", False),
            ("output_dex_sha256", "0" * 64),
        ]
        for field, invalid in cases:
            with self.subTest(field=field, invalid=invalid):
                self.patch = copy.deepcopy(valid)
                self.patch[field] = invalid
                self.write_reports()
                self.assert_rejected_before_output("Endpoint patch provenance mismatch")
        self.patch = valid
        self.write_reports()
        self.dex.write_bytes(self.dex.read_bytes() + b"unreported DEX change")
        self.assert_rejected_before_output("Endpoint patch provenance mismatch")

    def test_binds_patch_and_merge_reports_to_the_base_dex_bytes(self):
        for report, field in ((self.patch, "input_dex_sha256"),
                              (self.baseline, "merged_dex_sha256")):
            with self.subTest(field=field):
                valid = report[field]
                report[field] = "0" * 64
                self.write_reports()
                self.assert_rejected_before_output("differs from the verified base DEX")
                report[field] = valid

    def test_rejects_unverified_or_unbound_generated_revision_metadata(self):
        revision = self.baseline["build_revision_metadata"]
        for field, invalid in (("original_guard_accepts_generated_revision", False),
                               ("generated_metadata_sha256", "0" * 64)):
            with self.subTest(field=field):
                valid = revision[field]
                revision[field] = invalid
                self.write_reports()
                self.assert_rejected_before_output("Base build revision metadata mismatch")
                revision[field] = valid

    def test_rejects_multiple_dex_or_duplicate_entries_even_with_matching_base_hash(self):
        for name, data in (("classes2.dex", b"unexpected secondary DEX"),
                           ("AndroidManifest.xml", b"ambiguous duplicate manifest")):
            with self.subTest(name=name):
                self.make_archive(self.base, self.base_entries, extra=((name, data),))
                self.baseline["output_apk_sha256"] = build.sha256(self.base.read_bytes())
                self.write_reports()
                self.assert_rejected_before_output("Unexpected or duplicate APK entry set")

    def test_rejects_input_output_aliases_and_hardlinks_without_modifying_sources(self):
        for index, source in enumerate((self.original, self.base, self.base_report_path,
                                        self.dex, self.patch_report_path)):
            with self.subTest(source=source.name):
                contents = source.read_bytes()
                with self.assertRaisesRegex(ValueError, "overwrite"):
                    self.prepare(source)
                hardlink = self.directory / f"output-hardlink-{index}.apk"
                os.link(source, hardlink)
                with self.assertRaisesRegex(ValueError, "overwrite"):
                    self.prepare(hardlink)
                self.assertEqual(source.read_bytes(), contents)
                self.assertEqual(hardlink.read_bytes(), contents)


if __name__ == "__main__":
    unittest.main()
