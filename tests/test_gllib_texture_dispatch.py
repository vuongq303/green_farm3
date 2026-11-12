"""Protect the original texture-coordinate overload in both GLLib draw paths.

Inspect the two compiled draw methods against original converted bytecode. The
six-int overload handles pointer input, so Java overload selection must preserve
the original four float arguments even when their source values are integers.
"""

from pathlib import Path
import os
import re
import subprocess
import tempfile
import unittest
import zipfile

from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
PACKAGE = "com/gameloft/android/ANMP/GloftGF2F/S800x480"
OWNER = PACKAGE + "/GLLib"
EXPORT = "--add-exports=java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED"
NORMAL = "(L" + PACKAGE + "/aj;L" + PACKAGE + "/ay;IIIIIFFI)V"
UV = "(L" + PACKAGE + "/aj;L" + PACKAGE + "/ay;IIIIIFFFFFFFF)V"
TEXTURE = "(IIFFFF)V"
POINTER = "(IIIIII)Z"
SIGNATURES = (
    "public static void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4, int i5, float f, float f2, int i6)",
    "public static void a(aj ajVar, ay ayVar, int i, int i2, int i3, int i4, int i5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8)",
)

SCANNER = r'''
import java.nio.file.*;import jdk.internal.org.objectweb.asm.*;
public class TextureDispatchScanner {
 public static void main(String[] args)throws Exception {
  final String owner="com/gameloft/android/ANMP/GloftGF2F/S800x480/GLLib";
  final String peers="Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/ay;";
  new ClassReader(Files.readAllBytes(Paths.get(args[0]))).accept(new ClassVisitor(Opcodes.ASM8){
   public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){
    if(!name.equals("a")||!desc.equals("("+peers+"IIIIIFFI)V")&&!desc.equals("("+peers+"IIIIIFFFFFFFF)V"))return null;
    System.out.println("M\t"+desc);
    return new MethodVisitor(Opcodes.ASM8){
     public void visitMethodInsn(int opcode,String target,String called,String descriptor,boolean itf){
      if(target.equals(owner)&&called.equals("a")&&(descriptor.equals("(IIFFFF)V")||descriptor.equals("(IIIIII)Z")))
       System.out.println("I\t"+desc+"\t"+descriptor);
     }
    };
   }
  },ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);
 }
}
'''

PEERS = r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class aj {}
class ay {int aiq,air;}
class w {static boolean Zo;static int VQ;}
public class GLLib {
 static ay SC;static boolean SX;static float SV,SW,Rk;static int TX,SA,SM;static int[][] TY;
 static void a(aj graphics,int mode,int kind){}
 static void kC(){}static void dm(int count){}static boolean kv(){return false;}
 static void a(float x,float y,float width,float height,int transform){}
 static void a(float a,float b,float c,float d,float e,float f,float g,float h){}
 static void a(float a,float b,float c,float d,float e,float f,float g,float h,boolean flipped){}
 static void a(int width,int height,float x,float y,float sourceWidth,float sourceHeight){}
 static boolean a(int type,int id,int x,int y,int width,int height){return false;}
 static void a(int kind,int degrees,float x,float y){}
 static void b(int kind,float degrees,float x,float y){}
 %METHODS%
}
'''


class GLLibTextureDispatchTest(unittest.TestCase):
    def test_compiled_draw_paths_keep_original_float_texture_overload(self):
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        java, javac = home / "bin/java", home / "bin/javac"
        classes = ROOT / "core-source/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes"
        restored = classes / (OWNER + ".class")
        source = ROOT / "app/src/main/java" / (OWNER + ".java")
        staged = ROOT / "core-source/build/generated/core-java" / (OWNER + ".java")
        if not original.is_file() or not restored.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest("Requires verified original conversion, compiled core-source GLLib and JDK")
        self.assertEqual(source.read_bytes(), staged.read_bytes(), "Compile core-source after changing GLLib")
        self.assertGreaterEqual(restored.stat().st_mtime_ns, staged.stat().st_mtime_ns,
                                "Compiled GLLib predates the staged source")
        with tempfile.TemporaryDirectory(prefix="gllib-texture-dispatch-") as temp:
            root = Path(temp)
            scanner = root / "TextureDispatchScanner.java"
            scanner.write_text(SCANNER)
            result = subprocess.run([str(javac), EXPORT, str(scanner)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

            def dispatch(path):
                result = subprocess.run([str(java), EXPORT, "-cp", str(root), "TextureDispatchScanner", str(path)],
                                        capture_output=True, text=True, timeout=30)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                methods = {}
                for line in result.stdout.splitlines():
                    parts = line.split("\t")
                    if parts[0] == "M":
                        methods[parts[1]] = []
                    elif parts[0] == "I":
                        methods[parts[1]].append(parts[2])
                return methods

            baseline = root / "original-GLLib.class"
            with zipfile.ZipFile(original) as archive:
                baseline.write_bytes(archive.read(OWNER + ".class"))
            expected = dispatch(baseline)
            self.assertEqual(expected, {NORMAL: [TEXTURE], UV: [TEXTURE]},
                             "Original texture call sites changed")
            self.assertEqual(dispatch(restored), expected,
                             "Texture drawing selected the six-int pointer overload")

            # Compile the actual source methods independently, then reintroduce
            # the lost casts only in a temporary copy to verify regression sensitivity.
            methods = "\n".join(extract_method(source.read_text(), signature) for signature in SIGNATURES)
            for label, body in (("current", methods), ("lost-casts", re.sub(r"\(float\)\s*(i(?:[234])?)\b", r"\1", methods))):
                fixture = root / label / "GLLib.java"
                fixture.parent.mkdir()
                fixture.write_text(PEERS.replace("%METHODS%", body))
                output = root / label / "classes"
                result = subprocess.run([str(javac), "-source", "8", "-target", "8", "-d", str(output), str(fixture)],
                                        capture_output=True, text=True)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                actual = dispatch(output / (OWNER + ".class"))
                if label == "current":
                    self.assertEqual(actual, expected)
                else:
                    self.assertEqual(actual, {NORMAL: [POINTER], UV: [POINTER]},
                                     "Lost-cast mutation must reproduce the incorrect overload")
                    self.assertNotEqual(actual, expected)
            print("PASS: both compiled draw paths match original texture descriptors; lost-cast mutations select the pointer overload")


if __name__ == "__main__":
    unittest.main()
