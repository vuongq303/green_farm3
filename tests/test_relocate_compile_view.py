"""Compiler-only relocation preserves classfile structure and modified UTF8."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
import relocate_compile_view as relocate


class RelocateCompileViewTest(unittest.TestCase):
    def test_real_class_round_trip_arrays_signatures_and_modified_utf8(self):
        with tempfile.TemporaryDirectory(prefix='compile-view-relocation-') as temporary:
            directory = Path(temporary)
            source = directory / 'Sample.java'
            source.write_text(r'''package com.gameloft.android.ANMP.GloftGF2F;
import java.util.List;
public class Sample {
 public Sample[][] field;
 public List<Sample> generic;
 public static String text="\u0000\ud800com.gameloft.android.ANMP.GloftGF2F";
 public Sample echo(Sample input) {return input;}
}''')
            result = subprocess.run([str(jdk_tool('javac')), '-source', '8', '-target', '8',
                                     '-d', str(directory), str(source)],capture_output=True,text=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            original = directory / 'original.jar'
            raw = (directory / (relocate.OLD + '/Sample.class')).read_bytes()
            with zipfile.ZipFile(original, 'w') as archive:
                archive.writestr(relocate.OLD + '/Sample.class', raw)
                archive.writestr('META-INF/LICENSE', b'license stays unchanged')
            output = directory / 'compile-view.jar'
            report = relocate.relocate(original, output)
            self.assertTrue(report['all_class_bytes_round_trip_verified'])
            with zipfile.ZipFile(output) as archive:
                changed = archive.read(relocate.NEW + '/Sample.class')
                self.assertEqual(relocate.rewrite_class(changed, True), raw)
                self.assertEqual(archive.read('META-INF/LICENSE'), b'license stays unchanged')
                self.assertNotIn(relocate.OLD + '/Sample.class', archive.namelist())
            javap = jdk_tool('javac').with_name('javap.exe' if os.name == 'nt' else 'javap')
            result = subprocess.run([str(javap), '-classpath', str(output),
                                     relocate.NEW.replace('/', '.') + '.Sample'],capture_output=True,text=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertIn('com.quanhoang.greenfarm3.Sample[][] field', result.stdout)
            self.assertIn('List<com.quanhoang.greenfarm3.Sample> generic', result.stdout)
            self.assertNotIn(relocate.OLD.replace('/', '.'), result.stdout)


if __name__ == '__main__':
    unittest.main()
