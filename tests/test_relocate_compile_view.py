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
    def test_scoped_method_and_field_references_do_not_rename_other_owner(self):
        with tempfile.TemporaryDirectory(prefix='compile-member-relocation-') as temporary:
            directory = Path(temporary)
            engine = directory / 'ci.java'
            engine.write_text('package com.gameloft.android.ANMP.GloftGF2F.S800x480; public class ci {public static int aYR; public static int qD(){return1;} }'.replace('return1','return 1'))
            caller = directory / 'Caller.java'
            caller.write_text('''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
public class Caller {public int read(){return ci.aYR + ci.qD() + Other.qD();}}
class Other {public static int qD(){return 7;}}
''')
            result = subprocess.run([str(jdk_tool('javac')), '-source','8','-target','8','-d',str(directory),str(engine),str(caller)],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stderr)
            old_caller = directory / (relocate.OLD + '/S800x480/Caller.class')
            typed = relocate.rewrite_class(old_caller.read_bytes(),rename_symbols=True)
            renamed, changes = relocate.rewrite_engine_member_references(typed)
            self.assertEqual({x['after'] for x in changes},{'currentTutorialStepIndex','currentEngineState'})
            output = directory / 'out' / (relocate.NEW + '/S800x480/Caller.class')
            output.parent.mkdir(parents=True);output.write_bytes(renamed)
            javap = jdk_tool('javac').with_name('javap.exe' if os.name == 'nt' else 'javap')
            result = subprocess.run([str(javap),'-c','-classpath',str(directory/'out'),relocate.NEW.replace('/','.')+'.S800x480.Caller'],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stderr)
            self.assertIn('FarmGameEngine.currentTutorialStepIndex:I',result.stdout)
            self.assertIn('FarmGameEngine.currentEngineState:()I',result.stdout)
            self.assertIn('Other.qD:()I',result.stdout)

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
