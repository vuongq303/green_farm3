"""Protect original runtime artifacts while producing a Java lookup-only view."""

from pathlib import Path
import hashlib
import os
import struct
import sys
import tempfile
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import make_core_compile_view as view


class CoreCompileViewTest(unittest.TestCase):
    def setUp(self):
        self.source = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        if not self.source.exists(): self.skipTest("Requires verified original dex2jar artifact")

    def test_only_connection_visibility_changes_and_runtime_jar_is_untouched(self):
        before = hashlib.sha256(self.source.read_bytes()).hexdigest()
        with tempfile.TemporaryDirectory(prefix="core-compile-view-") as temporary:
            output = Path(temporary) / "compile-view.jar"
            view.generate(self.source, output)
            with zipfile.ZipFile(self.source) as original, zipfile.ZipFile(output) as modified:
                self.assertEqual(original.namelist(), modified.namelist())
                changes = []
                for name in original.namelist():
                    a, b = original.read(name), modified.read(name)
                    if a != b:
                        changes.append(name)
                        offset = view.class_access_offset(a)
                        flags = struct.unpack_from(">H", a, offset)[0]
                        self.assertEqual(b, a[:offset] + struct.pack(">H", flags & ~1) + a[offset + 2:])
                self.assertEqual(changes, [view.CONNECTION])
        self.assertEqual(hashlib.sha256(self.source.read_bytes()).hexdigest(), before)

    def test_refuses_to_overwrite_original_runtime_jar(self):
        with self.assertRaisesRegex(ValueError, "must never be overwritten"):
            view.generate(self.source, self.source)


if __name__ == "__main__": unittest.main()
