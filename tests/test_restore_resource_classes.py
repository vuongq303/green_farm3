"""Resource-linking regressions that would compile but corrupt SDK behavior."""

from pathlib import Path
import re
import sys
import tempfile
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import restore_resource_classes as restore


class ResourceAliasesTest(unittest.TestCase):
    def test_every_sdk_resource_constant_uses_resource_identity(self):
        public = {
            int(e.attrib["id"], 0): (e.attrib["type"], e.attrib["name"])
            for e in ET.parse(ROOT / "app/src/main/res/values/public.xml").getroot()
        }
        checked = 0
        with tempfile.TemporaryDirectory() as temp:
            paths = restore.generate(ROOT, Path(temp))
            self.assertEqual(len(paths), 4)
            self.assertFalse((Path(temp) / Path(*restore.APP_PACKAGE.split(".")) / "R.java").exists())
            for package, output in zip(restore.SDK_PACKAGES, paths):
                source = (ROOT / "app/src/main/java" / Path(*package.split(".")) / "R.java").read_text()
                generated = output.read_text()
                self.assertIsNone(restore.RESOURCE_LITERAL.search(generated))
                for field, literal in re.findall(r"int (\w+) = (0x7f[0-9a-fA-F]{6});", source):
                    kind, name = public[int(literal, 16)]
                    escaped = name.replace(".", "_").replace("-", "_").replace(":", "_")
                    self.assertIn(f"int {field} = {restore.APP_PACKAGE}.R.{kind}.{escaped};", generated)
                    checked += 1
        self.assertGreater(checked, 150, "The complete SDK R inputs must be exercised")

    def test_styleable_array_order_and_all_index_constants_are_preserved(self):
        resources = restore.read_public_resources(ROOT / "app/src/main/res/values/public.xml")
        for package in restore.SDK_PACKAGES:
            source = (ROOT / "app/src/main/java" / Path(*package.split(".")) / "R.java").read_text()
            generated = restore.render_alias(source, resources, package)
            if "class styleable" not in source:
                continue
            old = source.split("class styleable", 1)[1]
            new = generated.split("class styleable", 1)[1]
            self.assertEqual(old, new, "SDK styleable ordering and offsets must not follow aapt's new sort order")
        facebook = (ROOT / "app/src/main/java/com/facebook/R.java").read_text()
        self.assertIn("CardView_contentPaddingRight = 0x0000000a", facebook)
        self.assertIn("CardView_contentPaddingTop = 0x0000000b", facebook)
        self.assertIn("CardView_contentPaddingBottom = 0x0000000c", facebook)

    def test_unknown_app_resource_id_is_rejected(self):
        with self.assertRaisesRegex(ValueError, "Unmapped resource ID"):
            restore.render_alias(
                "package com.facebook;\npublic final class R { public static final int broken = 0x7fffffff; }",
                {}, "com.facebook",
            )

    def test_dotted_style_name_uses_aapt_field_escaping(self):
        resources = restore.read_public_resources(ROOT / "app/src/main/res/values/public.xml")
        self.assertEqual(resources[0x7F090013], f"{restore.APP_PACKAGE}.R.style.Theme_IAPTheme")


if __name__ == "__main__":
    unittest.main()
