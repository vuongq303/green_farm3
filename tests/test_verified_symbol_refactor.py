"""Prove reviewed names resolve by owner, preserving unrelated short names/data."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from rename_verified_symbols import migrate, renamed_kotlin_text

PACKAGE = 'test.greenfarm'


class VerifiedSymbolRefactorTest(unittest.TestCase):
    def fixture(self, root):
        java, kotlin = root / 'java', root / 'kotlin'
        java.mkdir()
        kotlin.mkdir()
        files = {
            'GLLib.java': 'package test.greenfarm; public abstract class GLLib { public GLLib(Object value) {} }',
            'ch.java': 'package test.greenfarm; class ch { ch() {} ci owner; }',
            'ci.java': '''package test.greenfarm;
public final class ci extends GLLib {
 static int aYR; public static boolean baa; static boolean bab; static int qD;
 static int qD() { return aYR; } static int qE() { return 2; }
 static int qD(int value) { return value; }
 public ci() { super(null); }
 String text = "ci ch GLLib aYR baa"; // ci aYR must remain literal/comment data
 int sameSpelling(int aYR) { int ci = 3; return aYR + ci + test.greenfarm.ci.aYR; }
 int unrelated(Other other) { return other.aYR; }
 int calls() { int qD = 4; return ci.qD() + ci.qE() + Other.qD() + ci.qD(7) + qD; }
 ch spawn() { return new ch(); }
 Class<?> lock() { synchronized(ci.class) { return ch.class; } }
}''',
            'Other.java': 'package test.greenfarm; class Other { int aYR; boolean baa; ci ci; static int qD() { return 3; } }',
        }
        for name, content in files.items():
            (java / name).write_text(content)
        (kotlin / 'Helper.kt').write_text('''package test.greenfarm
// ci.aYR is a comment, not code
fun read(engine: ci, entity: ch): Int {
    val literal = "ci ch GLLib aYR baa"
    return ci.aYR + ci.qD() + ci.qE() + ci.qD + Other.qD() + ci.qD(7) + if (ci.baa) 1 else 0
}
''')
        return java, kotlin

    def test_resolved_owner_and_constructor_names_preserve_shadows_and_data(self):
        with tempfile.TemporaryDirectory(prefix='verified-symbol-refactor-') as temporary:
            root = Path(temporary)
            java, kotlin = self.fixture(root)
            before = {path.name: path.read_bytes() for path in java.glob('*.java')}
            report = root / 'report.json'
            result = migrate(java, kotlin, PACKAGE, '', report, tool_directory=root / 'tool')
            self.assertFalse(result['applied'])
            self.assertEqual(before, {path.name: path.read_bytes() for path in java.glob('*.java')})
            result = migrate(java, kotlin, PACKAGE, '', report, apply=True, tool_directory=root / 'tool')
            self.assertTrue(result['applied'])
            self.assertFalse((java / 'ci.java').exists())
            engine = (java / 'FarmGameEngine.java').read_text()
            self.assertIn('public FarmGameEngine()', engine)
            self.assertIn('int sameSpelling(int aYR) { int ci = 3; return aYR + ci + test.greenfarm.FarmGameEngine.currentTutorialStepIndex;', engine)
            self.assertIn('return other.aYR;', engine)
            self.assertIn('static int currentEngineState()', engine)
            self.assertIn('static int previousEngineState()', engine)
            self.assertIn('int qD = 4; return FarmGameEngine.currentEngineState() + FarmGameEngine.previousEngineState() + Other.qD() + FarmGameEngine.qD(7) + qD;', engine)
            self.assertIn('"ci ch GLLib aYR baa"; // ci aYR must remain literal/comment data', engine)
            self.assertIn('static boolean bab;', engine)
            self.assertIn('synchronized(FarmGameEngine.class)', engine)
            other = (java / 'Other.java').read_text()
            self.assertIn('int aYR; boolean baa; FarmGameEngine ci;', other)
            helper = (kotlin / 'Helper.kt').read_text()
            self.assertIn('engine: FarmGameEngine, entity: FarmEntity', helper)
            self.assertIn('FarmGameEngine.currentTutorialStepIndex', helper)
            self.assertIn('FarmGameEngine.hasUnsavedGameChanges', helper)
            self.assertIn('FarmGameEngine.currentEngineState() + FarmGameEngine.previousEngineState() + FarmGameEngine.qD + Other.qD() + FarmGameEngine.qD(7)', helper)
            self.assertIn('// ci.aYR is a comment', helper)
            self.assertIn('"ci ch GLLib aYR baa"', helper)
            subprocess.run([str(jdk_tool('javac')), '-d', str(root / 'compiled'),
                            *map(str, java.glob('*.java'))], check=True, capture_output=True, text=True)

    def test_kotlin_unknown_shadows_and_templates_are_rejected(self):
        for source in ('val ci = other', 'fun call(ci: Any) = ci', 'fun call() = "${ci.aYR}"'):
            with self.subTest(source=source), self.assertRaisesRegex(ValueError, 'binding|interpolation'):
                renamed_kotlin_text(source, PACKAGE)
        source = 'fun other() = other.ci.aYR + other.aYR // ci.aYR'
        changed, count = renamed_kotlin_text(source, PACKAGE)
        self.assertEqual(changed, source)
        self.assertEqual(count, 0)


if __name__ == '__main__':
    unittest.main()
