"""Compare recovered rendering overload dispatch with original APK bytecode.

The manifest records methods with proven lost float conversions. Each original
method uses the float overload and never the conflicting integer overload.
Decompiled Java can duplicate common branches, so current float counts need not
equal DEX counts; the conflicting overload must still be absent. Only invocation
descriptors are inspected. Compiled classes must match current staged sources.
"""

from collections import Counter
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest
import zipfile


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from asm_tools import asm_jar
from android_build_tools import executable_name
from game_source_context import (current_source_path, current_compiled_path, current_staged_path, legacy_class_bytes, legacy_core_source)


PACKAGE = "com/gameloft/android/ANMP/GloftGF2F/S800x480"
AJ = "L" + PACKAGE + "/aj;"
AY = "L" + PACKAGE + "/ay;"
SPRITE_FLOAT = "(" + AJ + "IFFI)V"
SPRITE_INT = "(" + AJ + "IIII)V"
SPRITE_FLOAT_EXTRA = "(" + AJ + "IFFII)V"
SPRITE_INT_EXTRA = "(" + AJ + "IIIII)V"

# Class, containing method, target class, float descriptor, integer descriptor,
# original float invocation count. Original integer counts must be zero: a new
# mixed-overload case requires separate call-site proof before joining this set.
CASES = [
    ("aw", "d(" + AJ + "IIIII)V", "a", SPRITE_FLOAT, SPRITE_INT, 1),
    *[("ci", method, "a", SPRITE_FLOAT, SPRITE_INT, count) for method, count in (
        ("DV()V", 1), ("EA()V", 1), ("El()V", 3), ("Ep()V", 1),
        ("GM()V", 1), ("Ke()V", 1), ("Kp()V", 3),
        ("a(I[SIII)V", 2), ("a([SIIZ)V", 1), ("iF(I)Z", 1),
        ("ik(I)Z", 1), ("il(I)Z", 2), ("im(I)Z", 2),
        ("jR(I)Z", 2), ("kx(I)Z", 1), ("lA(I)Z", 1), ("lm(I)Z", 1),
    )],
    ("ci", "Ke()V", "a", SPRITE_FLOAT_EXTRA, SPRITE_INT_EXTRA, 1),
    ("ci", "Y(III)V", "GLLib", "(" + AJ + "FFFFZ)V", "(" + AJ + "IIIIZ)V", 2),
    ("ci", "c(IIIIII)V", "GLLib", "(FFFFII)V", "(IIIIII)Z", 1),
    ("bf", "a(" + AJ + "Ljava/lang/String;[CIIIIZZZ)V", "a", SPRITE_FLOAT, SPRITE_INT, 4),
    *[("a", method, "a", SPRITE_FLOAT, SPRITE_INT, count) for method, count in (
        ("a(" + AJ + "IIIII)V", 1),
        ("a(" + AJ + "IIIIIIZ)V", 1),
        ("a(" + AJ + "Ljava/lang/String;[CIIIIZZZ)V", 4),
        ("a([IIIIIZZ)V", 1),
    )],
    *[("ch", method, "a", SPRITE_FLOAT, SPRITE_INT, count) for method, count in (
        ("aE(II)V", 1), ("pI()V", 3), ("r(IIII)V", 1),
    )],
    ("GLLib", "a(" + AJ + AY + "IIIIIFFI)V", "GLLib", "(IIFFFF)V", "(IIIIII)Z", 1),
    ("GLLib", "a(" + AJ + AY + "IIIIIFFFFFFFF)V", "GLLib", "(IIFFFF)V", "(IIIIII)Z", 1),
]
CLASSES = {case[0] for case in CASES}

SCANNER = r'''
import java.nio.file.*;import java.util.*;import org.objectweb.asm.*;
public class CoreFloatDispatchScanner {
 public static void main(String[] args)throws Exception {
  Map<String,Set<String>> selected=new HashMap<>();
  for(String line:Files.readAllLines(Paths.get(args[0]))){
   String[] pair=line.split("\t",2);selected.computeIfAbsent(pair[0],key->new HashSet<>()).add(pair[1]);
  }
  for(String type:new TreeSet<>(selected.keySet())){
   new ClassReader(Files.readAllBytes(Paths.get(args[1]).resolve(type+".class"))).accept(new ClassVisitor(Opcodes.ASM8){
    public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){
     String method=name+desc;if(!selected.get(type).contains(method))return null;
     System.out.println("M\t"+type+"\t"+method);
     return new MethodVisitor(Opcodes.ASM8){
      public void visitMethodInsn(int opcode,String owner,String called,String descriptor,boolean itf){
       System.out.println("I\t"+type+"\t"+method+"\t"+owner+"."+called+descriptor);
      }
     };
    }
   },ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);
  }
 }
}
'''


class CoreFloatDispatchTest(unittest.TestCase):
    def test_proven_float_calls_never_select_the_conflicting_integer_overload(self):
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        java, javac = (home / 'bin' / executable_name(name) for name in ('java', 'javac'))
        classes = ROOT / "app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes"
        if not original.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest("Requires verified original conversion and JDK")
        asm = asm_jar()
        for name in sorted(CLASSES):
            source = current_source_path(name)
            staged = current_staged_path(name)
            compiled = current_compiled_path(name)
            if not compiled.is_file():
                self.skipTest("Requires compiled app core " + name)
            self.assertEqual(source.read_bytes(), staged.read_bytes(),
                             "Compile app after changing " + name)
            self.assertGreaterEqual(compiled.stat().st_mtime_ns, staged.stat().st_mtime_ns,
                                    "Compiled " + name + " predates the staged source")
        with tempfile.TemporaryDirectory(prefix="core-float-dispatch-") as temp:
            root = Path(temp)
            scanner = root / "CoreFloatDispatchScanner.java"
            scanner.write_text(SCANNER)
            result = subprocess.run([str(javac), '-cp', str(asm), str(scanner)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            manifest = root / "methods.tsv"
            manifest.write_text("".join(name + "\t" + method + "\n"
                                        for name, method in sorted({(case[0], case[1]) for case in CASES})))
            baseline = root / "original"
            baseline.mkdir()
            with zipfile.ZipFile(original) as archive:
                for name in CLASSES:
                    (baseline / (name + ".class")).write_bytes(archive.read(PACKAGE + "/" + name + ".class"))

            def invocations(directory):
                classpath = os.pathsep.join((str(root), str(asm)))
                result = subprocess.run([str(java), "-cp", classpath, "CoreFloatDispatchScanner",
                                         str(manifest), str(directory)], capture_output=True, text=True, timeout=30)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                methods = {}
                for line in result.stdout.splitlines():
                    fields = line.split("\t")
                    key = tuple(fields[1:3])
                    if fields[0] == "M":
                        methods[key] = Counter()
                    elif fields[0] == "I":
                        methods[key][fields[3]] += 1
                return methods

            before = invocations(baseline)
            normalized = root / "current-owner-fixture"
            normalized.mkdir()
            for name in CLASSES:
                (normalized / (name + ".class")).write_bytes(legacy_class_bytes(current_compiled_path(name).read_bytes()))
            after = invocations(normalized)
            for name, method, target, float_descriptor, integer_descriptor, original_count in CASES:
                with self.subTest(type=name, method=method, target=target, descriptor=float_descriptor):
                    key = (name, method)
                    self.assertIn(key, before, "Missing original method")
                    self.assertIn(key, after, "Missing compiled method")
                    float_call = PACKAGE + "/" + target + ".a" + float_descriptor
                    integer_call = PACKAGE + "/" + target + ".a" + integer_descriptor
                    self.assertEqual(before[key][float_call], original_count,
                                     "Original float invocation count changed")
                    self.assertEqual(before[key][integer_call], 0,
                                     "Mixed original overloads require individual call-site proof")
                    self.assertGreater(after[key][float_call], 0,
                                       "Original float rendering target is absent")
                    self.assertEqual(after[key][integer_call], 0,
                                     "Lost float conversions selected a conflicting integer overload")
            print("PASS: " + str(len(CASES)) + " rendering overload pairs across six current classes preserve original float targets and exclude conflicting integer targets")


if __name__ == "__main__":
    unittest.main()
