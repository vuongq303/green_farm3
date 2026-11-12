"""Packaging preflight checks, independent of Android or a real signing key.

Synthetic merger reports model an already verified merge stage. These tests
exercise provenance binding and payload selection, not executable DEX behavior.
MergeCoreDex's separate integration checks cover actual bytecode preservation.
"""

from pathlib import Path
import json
import os
import sys
import tempfile
import unittest
from unittest import mock
import warnings
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import build_core_source as build


class CoreSourcePackagingTest(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory(prefix="core-source-packaging-")
        self.addCleanup(self.temporary.cleanup)
        self.directory = Path(self.temporary.name)
        self.original = self.directory / "original.apk"
        self.compiled = self.directory / "compiled.apk"
        self.merged = self.directory / "classes.dex"
        self.report_path = self.directory / "classes.dex.json"
        self.output = self.directory / "unsigned.apk"
        with zipfile.ZipFile(self.original, "w") as archive:
            archive.writestr("AndroidManifest.xml", b"original manifest")
            archive.writestr("resources.arsc", b"original resource table")
            archive.writestr("res/layout/original.xml", b"old layout")
            archive.writestr("classes.dex", b"original dex fixture")
            archive.writestr("META-INF/GAMELOFT.RSA", b"obsolete signature")
            archive.writestr("META-INF/GAMELOFT.SF", b"obsolete signature metadata")
            archive.writestr("assets/farm.bin", b"original gameplay data", compress_type=zipfile.ZIP_DEFLATED)
            archive.writestr("lib/x86/libnativeU.so", b"original native bytes", compress_type=zipfile.ZIP_STORED)
            archive.writestr("support.txt", b"original metadata")
        self.make_compiled()
        self.merged.write_bytes(b"dex\n035\0" + bytes(104))
        self.original_hash = build.sha256(self.original.read_bytes())
        patch = mock.patch.object(build, "EXPECTED_APK_SHA256", self.original_hash)
        patch.start(); self.addCleanup(patch.stop)
        core = ROOT / "app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480"
        selected = {"Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/" + source.stem + ";"
                    for source in core.glob("*.java") if source.stem != "l" and source.stem not in build.ADDED_CORE_SOURCES}
        self.assertEqual(len(selected), 108, "Update the reviewed source profile explicitly if its coverage changes")
        self.report = {"original_apk_sha256": self.original_hash,
                       "overlay_apk_sha256": build.sha256(self.compiled.read_bytes()),
                       "output_dex_sha256": build.sha256(self.merged.read_bytes()),
                       "original_non_overlay_semantics_verified": True,
                       "compiled_overlay_semantics_verified": True,
                       "replaced_classes": sorted(selected),
                       "added_classes": sorted("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/" + name + ";"
                                               for name in build.ADDED_CORE_SOURCES)}
        self.write_report()

    def make_compiled(self, omit=(), duplicate_manifest=False, unsafe=False):
        with warnings.catch_warnings():
            warnings.simplefilter("ignore", UserWarning)
            with zipfile.ZipFile(self.compiled, "w") as archive:
                if "AndroidManifest.xml" not in omit: archive.writestr("AndroidManifest.xml", b"compiled manifest")
                if "resources.arsc" not in omit: archive.writestr("resources.arsc", b"compiled resource table")
                archive.writestr("res/layout/current.xml", b"compiled layout")
                archive.writestr("classes.dex", b"partial core dex; must be replaced")
                archive.writestr("assets/farm.bin", b"compiled payload must not replace original")
                archive.writestr("lib/x86/libnativeU.so", b"compiled native must not replace original")
                archive.writestr("META-INF/WRONG.RSA", b"must not be copied")
                archive.writestr("compile-view.jar", b"lookup-only jar must never be packaged")
                if duplicate_manifest: archive.writestr("AndroidManifest.xml", b"ambiguous manifest")
                if unsafe: archive.writestr("res/../classes.dex", b"not a resource")

    def write_report(self):
        self.report_path.write_text(json.dumps(self.report))

    def rebind_compiled_hash(self):
        self.report["overlay_apk_sha256"] = build.sha256(self.compiled.read_bytes())
        self.write_report()

    def assemble(self, output=None):
        return build.assemble(self.original, self.compiled, self.merged, output or self.output)

    @staticmethod
    def change_zip(path, name, *, data=None, compression=None, remove=False):
        replacement = path.with_suffix(".changed.zip")
        with zipfile.ZipFile(path) as before, zipfile.ZipFile(replacement, "w") as after:
            for entry in before.infolist():
                if entry.filename == name:
                    if remove: continue
                    contents = before.read(entry) if data is None else data
                    if compression is not None: entry.compress_type = compression
                    after.writestr(entry, contents)
                else: after.writestr(entry, before.read(entry))
        replacement.replace(path)

    def test_assembles_verified_dex_resources_and_original_payload_only(self):
        self.assemble()
        hashes = build.verify_assets_native(self.original, self.output)
        self.assertEqual(set(hashes), {"assets/farm.bin", "lib/x86/libnativeU.so"})
        with zipfile.ZipFile(self.output) as archive:
            self.assertEqual(archive.read("classes.dex"), self.merged.read_bytes())
            self.assertEqual(archive.read("AndroidManifest.xml"), b"compiled manifest")
            self.assertEqual(archive.read("resources.arsc"), b"compiled resource table")
            self.assertEqual(archive.read("support.txt"), b"original metadata")
            self.assertNotIn("res/layout/original.xml", archive.namelist())
            self.assertNotIn("compile-view.jar", archive.namelist())
            self.assertFalse(any(name.endswith((".RSA", ".SF")) for name in archive.namelist()))

    def test_rejects_hash_or_semantic_report_mismatch_before_output(self):
        for field in ("original_apk_sha256", "overlay_apk_sha256", "output_dex_sha256",
                      "original_non_overlay_semantics_verified", "compiled_overlay_semantics_verified"):
            with self.subTest(field=field):
                previous = self.report[field]
                self.report[field] = False if previous is True else "0" * 64
                self.write_report()
                with self.assertRaisesRegex(ValueError, "provenance"): self.assemble()
                self.assertFalse(self.output.exists())
                self.report[field] = previous
        self.write_report()

    def test_rejects_missing_selected_java_core_coverage(self):
        self.report["replaced_classes"].remove("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/GLLib;")
        self.write_report()
        with self.assertRaisesRegex(ValueError, "every selected Java-core"): self.assemble()
        self.assertFalse(self.output.exists())

    def test_progress_helpers_must_be_verified_as_additions_not_replacements(self):
        helper = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/GameSaveStore;"
        self.report["added_classes"].remove(helper)
        self.report["replaced_classes"].append(helper)
        self.write_report()
        with self.assertRaisesRegex(ValueError, "reviewed progress helper"): self.assemble()
        self.assertFalse(self.output.exists())

    def test_rejects_changed_original_apk(self):
        self.original.write_bytes(self.original.read_bytes() + b"changed")
        with self.assertRaisesRegex(ValueError, "Unexpected original APK"): self.assemble()

    def test_rejects_missing_or_ambiguous_resource_inputs_before_output(self):
        for missing in ("AndroidManifest.xml", "resources.arsc"):
            with self.subTest(missing=missing):
                self.make_compiled(omit=(missing,)); self.rebind_compiled_hash()
                with self.assertRaisesRegex(ValueError, "manifest or resource table"): self.assemble()
                self.assertFalse(self.output.exists())
        self.make_compiled(duplicate_manifest=True); self.rebind_compiled_hash()
        with self.assertRaisesRegex(ValueError, "duplicate entries"): self.assemble()
        self.assertFalse(self.output.exists())
        self.make_compiled(unsafe=True); self.rebind_compiled_hash()
        with self.assertRaisesRegex(ValueError, "unsafe resource path"): self.assemble()
        self.assertFalse(self.output.exists())

    def test_rejects_input_output_aliases_including_hardlinks(self):
        for source in (self.original, self.compiled, self.merged, self.report_path):
            with self.subTest(source=source.name):
                with self.assertRaisesRegex(ValueError, "overwrite"): self.assemble(source)
        alias = self.directory / "aliased-output.apk"
        os.link(self.original, alias)
        with self.assertRaisesRegex(ValueError, "overwrite"): self.assemble(alias)
        self.assertEqual(build.sha256(self.original.read_bytes()), self.original_hash)

    def test_detects_asset_and_native_bytes_compression_or_set_changes(self):
        for name, options in (("assets/farm.bin", {"data": b"tampered"}),
                              ("lib/x86/libnativeU.so", {"data": b"tampered"}),
                              ("lib/x86/libnativeU.so", {"compression": zipfile.ZIP_DEFLATED}),
                              ("assets/farm.bin", {"remove": True})):
            with self.subTest(name=name, options=options):
                self.assemble()
                self.change_zip(self.output, name, **options)
                with self.assertRaisesRegex(ValueError, "Assets/native"): build.verify_assets_native(self.original, self.output)

    def test_main_rejects_tampered_unsigned_payload_before_any_signing_command(self):
        original_assemble = build.assemble
        def corrupt(*args):
            result = original_assemble(*args)
            self.change_zip(args[3], "assets/farm.bin", data=b"tampered before signing")
            return result
        key = self.directory / "debug.keystore"; key.write_bytes(b"test-only key placeholder")
        argv = ["build_core_source.py", "--original", str(self.original), "--compiled", str(self.compiled),
                "--merged-dex", str(self.merged), "--sdk", str(self.directory / "sdk"),
                "--keystore", str(key), "--output", str(self.directory / "signed-output.apk")]
        with mock.patch.object(sys, "argv", argv), mock.patch.object(build, "assemble", side_effect=corrupt), \
                mock.patch.object(build.subprocess, "run") as command:
            with self.assertRaisesRegex(ValueError, "Assets/native"): build.main()
        command.assert_not_called()
        self.assertFalse((self.directory / "signed-output.apk").exists())


if __name__ == "__main__": unittest.main()
