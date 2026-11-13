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

    def policy_fixture(self):
        disabled = self.directory / 'disabled-sdk.dex'
        disabled.write_bytes(b'dex\n035\0SDK lifecycle policy fixture')
        policy = {'schema_version': 2, 'policy': 'facebook-and-ads-lifecycle-disabled',
                  'target_class': 'Ljavax/microedition/midlet/MIDlet;',
                  'target_classes': build.SDK_RETIREMENT_OWNERS,
                  'native_bridge_calls_disabled': 6, 'total_sdk_invocation_sites_disabled': 33,
                  'native_ads_manager_invocations_remaining': 0, 'all_native_ads_manager_invocations_audited': True,
                  'per_owner_inverse_semantics_verified': True,
                  'facebook_lifecycle_calls_disabled': 12, 'ads_lifecycle_calls_disabled': 12,
                  'native_fullscreen_queries_disabled': 2, 'native_back_handlers_disabled': 1,
                  'instruction_widths_preserved': True, 'inverse_target_semantics_verified': True,
                  'unchanged_other_classes_verified': True,
                  'input_dex_sha256': build.sha256(self.dex.read_bytes()),
                  'output_dex_sha256': build.sha256(disabled.read_bytes())}
        report = disabled.with_suffix('.dex.json')
        report.write_text(json.dumps(policy))
        return disabled, report, policy

    def test_sdk_policy_is_bound_to_exact_previous_and_final_dex(self):
        disabled, report, policy = self.policy_fixture()
        build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled)
        with zipfile.ZipFile(self.output) as output:
            self.assertEqual(output.read('classes.dex'), disabled.read_bytes())
        self.output.unlink()
        for field, invalid in (('input_dex_sha256', '0' * 64), ('output_dex_sha256', '0' * 64),
                               ('facebook_lifecycle_calls_disabled', 11), ('native_back_handlers_disabled', True),
                               ('inverse_target_semantics_verified', False), ('unchanged_other_classes_verified', False),
                               ('instruction_widths_preserved', False), ('target_class', 'Lwrong/Owner;'),
                               ('schema_version', 1), ('native_bridge_calls_disabled', 5),
                               ('total_sdk_invocation_sites_disabled', 27), ('native_ads_manager_invocations_remaining', False),
                               ('native_ads_manager_invocations_remaining', 1), ('all_native_ads_manager_invocations_audited', False),
                               ('per_owner_inverse_semantics_verified', False), ('target_classes', [])):
            changed = dict(policy); changed[field] = invalid; report.write_text(json.dumps(changed))
            with self.subTest(field=field), self.assertRaisesRegex(ValueError, 'SDK lifecycle provenance'):
                build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled)
            self.assertFalse(self.output.exists())

    def test_sdk_policy_dex_and_proof_cannot_be_overwritten(self):
        disabled, report, _ = self.policy_fixture()
        for output in (disabled, report):
            before = output.read_bytes()
            with self.assertRaisesRegex(ValueError, 'overwrite'):
                build.prepare(self.original, self.base, self.dex, output, disabled_sdk_dex=disabled)
            self.assertEqual(output.read_bytes(), before)

    def test_sdk_policy_follows_relocation_without_weakening_endpoint_binding(self):
        relocated = self.directory / 'relocated.dex'; relocated.write_bytes(b'relocated DEX fixture')
        relocation = {'schema_version': 1, 'from_package': 'com.gameloft.android.ANMP.GloftGF2F',
                      'to_package': 'com.quanhoang.greenfarm3',
                      'input_dex_sha256': build.sha256(self.dex.read_bytes()),
                      'output_dex_sha256': build.sha256(relocated.read_bytes()),
                      'legacy_game_class_definitions_remaining': 0, 'inverse_canonical_semantics_verified': True}
        relocated.with_suffix('.dex.json').write_text(json.dumps(relocation))
        disabled, report, policy = self.policy_fixture()
        with self.assertRaisesRegex(ValueError, 'SDK lifecycle provenance'):
            build.prepare(self.original, self.base, self.dex, self.output, relocated, disabled)
        policy['input_dex_sha256'] = build.sha256(relocated.read_bytes()); report.write_text(json.dumps(policy))
        build.prepare(self.original, self.base, self.dex, self.output, relocated, disabled)
        with zipfile.ZipFile(self.output) as output:
            self.assertEqual(output.read('classes.dex'), disabled.read_bytes())

    def test_google_ads_pruning_requires_the_closed_set_and_previous_policy_dex(self):
        disabled, _, _ = self.policy_fixture()
        pruned = self.directory / 'pruned-ads.dex'; pruned.write_bytes(b'dex\n035\0closed Google Ads set removed')
        proof = {'schema_version': 1, 'policy': 'remove-google-ads-presentation-sdk',
                 'removed_class_count': 1880, 'retained_identifier_classes': sorted(build.GOOGLE_IDENTIFIER_CLASSES),
                 'retained_structural_reference_audit_verified': True, 'retained_reflection_string_audit_verified': True,
                 'retained_class_semantics_verified': True, 'inverse_class_set_semantics_verified': True,
                 'input_dex_sha256': build.sha256(disabled.read_bytes()),
                 'output_dex_sha256': build.sha256(pruned.read_bytes())}
        report = pruned.with_suffix('.dex.json'); report.write_text(json.dumps(proof))
        build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled, pruned_ads_dex=pruned)
        with zipfile.ZipFile(self.output) as output:
            self.assertEqual(output.read('classes.dex'), pruned.read_bytes())
        self.output.unlink()
        for field, invalid in (('removed_class_count', 1879), ('retained_identifier_classes', []),
                               ('retained_structural_reference_audit_verified', False), ('retained_reflection_string_audit_verified', False),
                               ('retained_class_semantics_verified', False), ('inverse_class_set_semantics_verified', False),
                               ('input_dex_sha256', '0' * 64), ('output_dex_sha256', '0' * 64)):
            changed = dict(proof); changed[field] = invalid; report.write_text(json.dumps(changed))
            with self.subTest(field=field), self.assertRaisesRegex(ValueError, 'Google Ads pruning provenance'):
                build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled, pruned_ads_dex=pruned)
            self.assertFalse(self.output.exists())

    def test_facebook_pruning_binds_original_profile_and_exact_sdk_set(self):
        disabled, _, _ = self.policy_fixture()
        google = self.directory / 'google-free.dex'; google.write_bytes(b'dex\n035\0Google Ads removed')
        google_proof = {'schema_version': 1, 'policy': 'remove-google-ads-presentation-sdk',
            'removed_class_count': 1880, 'retained_identifier_classes': sorted(build.GOOGLE_IDENTIFIER_CLASSES),
            'retained_structural_reference_audit_verified': True, 'retained_reflection_string_audit_verified': True,
            'retained_class_semantics_verified': True, 'inverse_class_set_semantics_verified': True,
            'input_dex_sha256': build.sha256(disabled.read_bytes()), 'output_dex_sha256': build.sha256(google.read_bytes())}
        google.with_suffix('.dex.json').write_text(json.dumps(google_proof))
        facebook = self.directory / 'facebook-free.dex'; facebook.write_bytes(b'dex\n035\0Facebook removed, real profile data retained')
        proof = {'schema_version': 1, 'policy': 'remove-facebook-sdk-and-provider-bridge',
            'removed_facebook_class_count': 717, 'removed_provider_bridge_count': 24, 'removed_class_count': 741,
            'retired_avatar_method': 'Lcom/gameloft/android2d/socialnetwork/i;->UN()Ljavax/microedition/lcdui/ao;',
            'original_profile_canonical_sha256': '5b1b329c8487c068c8a2e1b4dbd2a0fdfabfa7a96faf7ec01acb0098ccd7fd12',
            'retained_structural_reference_audit_verified': True, 'retained_reflection_string_audit_verified': True,
            'retired_nullable_avatar_method_verified': True, 'other_profile_data_and_methods_preserved': True,
            'retained_class_semantics_verified': True, 'inverse_class_set_and_method_semantics_verified': True,
            'native_ads_manager_invocations_remaining': 0, 'global_ad_native_invocation_audit_verified': True,
            'input_dex_sha256': build.sha256(google.read_bytes()), 'output_dex_sha256': build.sha256(facebook.read_bytes())}
        report = facebook.with_suffix('.dex.json'); report.write_text(json.dumps(proof))
        build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled, pruned_ads_dex=google, pruned_facebook_dex=facebook)
        with zipfile.ZipFile(self.output) as output: self.assertEqual(output.read('classes.dex'), facebook.read_bytes())
        self.output.unlink()
        for field, invalid in (('removed_facebook_class_count', 716), ('removed_provider_bridge_count', 23),
                ('retired_avatar_method', 'wrong'), ('original_profile_canonical_sha256', '0' * 64),
                ('other_profile_data_and_methods_preserved', False), ('inverse_class_set_and_method_semantics_verified', False),
                ('global_ad_native_invocation_audit_verified', False), ('native_ads_manager_invocations_remaining', 1),
                ('input_dex_sha256', '0' * 64), ('output_dex_sha256', '0' * 64)):
            changed = dict(proof); changed[field] = invalid; report.write_text(json.dumps(changed))
            with self.subTest(field=field), self.assertRaisesRegex(ValueError, 'Facebook SDK pruning provenance'):
                build.prepare(self.original, self.base, self.dex, self.output, disabled_sdk_dex=disabled, pruned_ads_dex=google, pruned_facebook_dex=facebook)
            self.assertFalse(self.output.exists())


if __name__ == "__main__":
    unittest.main()
