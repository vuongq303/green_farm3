"""Production packaging bindings for the main APK and its configured backend endpoint.

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
        self.base = self.directory / "base.apk"
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
            "schema_version": 2,
            "target_classes": build.TARGET_CLASSES,
            "target_method": build.TARGET_METHOD,
            "original_url": build.ORIGINAL_URL,
            "replacement_count": 3,
            "replacement_counts": {build.TARGET_CLASS: 1, build.CORE_TARGET_CLASS: 2},
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
            ("target_classes", [build.TARGET_CLASS]),
            ("schema_version", 1),
            ("replacement_counts", {build.TARGET_CLASS: 3}),
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

    def test_required_startup_guard_must_prove_original_sdk_branch_is_retained(self):
        def prepare_guarded():
            return build.prepare(self.original, self.base, self.dex, self.output,
                                 require_local_startup_guard=True)
        with self.assertRaisesRegex(ValueError, 'startup guard provenance'):
            prepare_guarded()
        valid = {
            'owner': 'Lcom/gameloft/android/wrapper/au;',
            'method': 'Qh()V',
            'predicate': 'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LocalDevelopmentMode;->skipLegacyBackend()Z',
            'original_false_branch_preserved': True,
            'reverse_patch_semantics_verified': True,
        }
        for field, bad in [('owner', 'Lwrong/Owner;'), ('method', 'Qh(I)V'),
                           ('predicate', 'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LocalDevelopmentMode;->isEnabled()Z'),
                           ('original_false_branch_preserved', False),
                           ('original_false_branch_preserved', 1),
                           ('reverse_patch_semantics_verified', False)]:
            self.patch['local_startup_guard'] = dict(valid, **{field: bad})
            self.write_reports()
            with self.subTest(field=field, value=bad), self.assertRaisesRegex(ValueError, 'startup guard provenance'):
                prepare_guarded()
            self.assertFalse(self.output.exists())
        self.patch['local_startup_guard'] = valid
        self.write_reports()
        prepare_guarded()
        self.assertTrue(self.output.exists())

    def test_network_diagnostics_require_exact_bound_sdk_hook_proof(self):
        def prepare_instrumented():
            return build.prepare(self.original, self.base, self.dex, self.output,
                                 require_network_diagnostics=True)
        with self.assertRaisesRegex(ValueError, 'Network diagnostics provenance'):
            prepare_instrumented()
        invalid = [
            dict(build.NETWORK_DIAGNOSTICS, owner='Lwrong/Owner;'),
            dict(build.NETWORK_DIAGNOSTICS, method='run(I)V'),
            dict(build.NETWORK_DIAGNOSTICS, helper='Lwrong/Helper;'),
            dict(build.NETWORK_DIAGNOSTICS, begin_calls=True),
            dict(build.NETWORK_DIAGNOSTICS, begin_calls=2),
            dict(build.NETWORK_DIAGNOSTICS, reverse_patch_semantics_verified=1),
            dict(build.NETWORK_DIAGNOSTICS, hook_counts=dict(build.NETWORK_DIAGNOSTICS['hook_counts'], failure=0)),
            dict(build.NETWORK_DIAGNOSTICS, hook_counts=dict(build.NETWORK_DIAGNOSTICS['hook_counts'], failure=True)),
            dict(build.NETWORK_DIAGNOSTICS, extra='unreviewed hook'),
        ]
        for proof in invalid:
            self.patch['network_diagnostics'] = proof
            self.write_reports()
            with self.subTest(proof=proof), self.assertRaisesRegex(ValueError, 'Network diagnostics provenance'):
                prepare_instrumented()
            self.assertFalse(self.output.exists())
            # Malformed optional proofs must not be accepted when the flag is absent.
            self.assert_rejected_before_output('Network diagnostics provenance')
        self.patch['network_diagnostics'] = copy.deepcopy(build.NETWORK_DIAGNOSTICS)
        self.write_reports()
        prepare_instrumented()
        self.assertTrue(self.output.exists())

    def test_facebook_lifecycle_requires_exact_bound_reference_only_proof(self):
        with self.assertRaisesRegex(ValueError, 'Facebook lifecycle'):
            build.prepare(self.original, self.base, self.dex, self.output,
                          require_legacy_facebook_lifecycle=True)
        good = copy.deepcopy(self.patch)
        good['legacy_facebook_lifecycle'] = copy.deepcopy(build.FACEBOOK_LIFECYCLE)
        self.patch = good
        self.write_reports()
        build.prepare(self.original, self.base, self.dex, self.output,
                      require_legacy_facebook_lifecycle=True)
        self.output.unlink()
        for mutate in (
            lambda proof: proof.update(call_count=True),
            lambda proof: proof.update(call_count=11),
            lambda proof: proof.update(owner='Lwrong/MIDlet;'),
            lambda proof: proof.update(helper_methods_verified=False),
            lambda proof: proof.update(method_references_only=False),
            lambda proof: proof.update(reverse_patch_semantics_verified=False),
            lambda proof: proof['method_counts'].update({'onCreate(Landroid/os/Bundle;)V': True}),
            lambda proof: proof['method_counts'].pop('onDestroy()V'),
        ):
            self.patch = copy.deepcopy(good)
            mutate(self.patch['legacy_facebook_lifecycle'])
            self.write_reports()
            self.assert_rejected_before_output('Facebook lifecycle')

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
