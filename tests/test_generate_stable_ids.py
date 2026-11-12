"""Guard original resource identities, including IDs in obfuscated Java/native code."""

from pathlib import Path
import os
import shutil
import subprocess
import sys
import tempfile
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import generate_stable_ids as stable


class StableIdsTest(unittest.TestCase):
    def test_all_public_ids_and_xml_names_are_preserved(self):
        source = ROOT / "app/src/main/res/values/public.xml"
        expected = {
            f"{stable.APP_PACKAGE}:{item.attrib['type']}/{item.attrib['name']}": int(item.attrib["id"], 0)
            for item in ET.parse(source).getroot()
        }
        actual = {
            name: int(value, 16)
            for name, value in (line.split(" = ") for line in stable.render(source).splitlines())
        }
        self.assertEqual(actual, expected)
        self.assertGreater(len(actual), 500)
        self.assertEqual(actual[f"{stable.APP_PACKAGE}:attr/adSize"], 0x7F01001E)
        self.assertEqual(actual[f"{stable.APP_PACKAGE}:style/Theme.IAPTheme"], 0x7F090013)

    def test_duplicate_id_conflicts_are_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            source = Path(temp) / "public.xml"
            source.write_text('<resources><public type="id" name="a" id="0x7f010000"/>'
                              '<public type="id" name="b" id="0x7f010000"/></resources>')
            with self.assertRaisesRegex(ValueError, "conflicting names"):
                stable.render(source)

    def test_aapt2_stable_ids_round_trip(self):
        sdk = Path(os.environ.get("ANDROID_HOME", "/nonexistent-android-sdk"))
        versions = sorted((sdk / "build-tools").glob("*/aapt2"))
        aapt2 = shutil.which("aapt2") or (str(versions[-1]) if versions else None)
        platforms = sorted((sdk / "platforms").glob("*/android.jar"))
        if not aapt2 or not platforms:
            self.skipTest("Requires installed AAPT2 and Android SDK platform")
        with tempfile.TemporaryDirectory() as temp:
            p = Path(temp)
            (p / "res/values").mkdir(parents=True)
            # Alphabetical aapt assignment would place a before z. Deliberately
            # reverse IDs to prove --stable-ids actually controls the linker.
            (p / "res/values/attrs.xml").write_text(
                '<resources><attr name="a" format="integer"/><attr name="z" format="integer"/></resources>')
            (p / "public.xml").write_text(
                '<resources><public type="attr" name="z" id="0x7f010000"/>'
                '<public type="attr" name="a" id="0x7f010001"/></resources>')
            (p / "AndroidManifest.xml").write_text(
                '<manifest xmlns:android="http://schemas.android.com/apk/res/android" '
                'package="example.probe"><application/></manifest>')
            (p / "ids.txt").write_text(stable.render(p / "public.xml", "example.probe"))
            subprocess.run([aapt2, "compile", "--dir", str(p / "res"), "-o", str(p / "resources.zip")], check=True)
            subprocess.run([aapt2, "link", "--manifest", str(p / "AndroidManifest.xml"),
                            "-I", str(platforms[-1]), str(p / "resources.zip"),
                            "--stable-ids", str(p / "ids.txt"), "--emit-ids", str(p / "linked.txt"),
                            "-o", str(p / "probe.apk")], check=True)
            self.assertEqual(set((p / "ids.txt").read_text().splitlines()),
                             set((p / "linked.txt").read_text().splitlines()))


if __name__ == "__main__":
    unittest.main()
