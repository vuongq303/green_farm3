"""Compare recovered pure CI methods with original converted DEX bytecode.

Requires a current :core-source:compileDebugJavaWithJavac output. A source/staging
identity check prevents testing a class produced before the latest source edits.
Unrelated Android/game initialization is omitted from the fixture shells.
"""
from pathlib import Path
import os
import struct
import subprocess
import tempfile
import unittest
import zipfile

from test_asprite_recovery import BASE, fixture
from test_gllib_recovered_algorithms import constant_pool, method_fixture as gllib_fixture


RUNNER = r'''
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class CIFlowComparison {
 static final String PKG="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Class<?> load(String path)throws Exception{return Class.forName(PKG+"ci",true,new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()},null));}
 static Object parse(Method m,String s)throws Exception{
  try{return m.invoke(null,s);}catch(InvocationTargetException e){return e.getTargetException().getClass().getName();}
 }
 static void field(Class<?> c,String name,Object value)throws Exception{Field f=c.getDeclaredField(name);f.setAccessible(true);f.set(null,value);}
 public static void main(String[] args)throws Exception{
  Class<?> old=load(args[0]),now=load(args[1]);
  Method a=old.getDeclaredMethod("dk",String.class),b=now.getDeclaredMethod("dk",String.class);
  String[] edges={null,"","0","123","a","a123","123a456","1,234.56","   1 2 .3  ","-12","2147483647","2147483648","9999999999999999999","\u0661\u0662\u0663","\u06f0\u06f1"};
  Random random=new Random(44220);int checks=0;
  for(int i=0;i<5000;i++){
   String value;
   if(i<edges.length)value=edges[i];else{
    StringBuilder s=new StringBuilder();String chars="01234567890123456789 ,.-x\u0661\u06f2";
    for(int j=0,n=random.nextInt(24);j<n;j++)s.append(chars.charAt(random.nextInt(chars.length())));value=s.toString();
   }
   Object x=parse(a,value),y=parse(b,value);
   if(!Objects.equals(x,y))throw new AssertionError("Numeric parse differs for "+value+": "+x+" / "+y);
   checks++;
  }
  char[][] table=new char[128][2];
  for(int i=0;i<table.length;i++){table[i][0]=(char)(i%6);table[i][1]=(char)(0xe000+i*4);}
  table[1604-1569][0]=2;for(int alef:new int[]{1570,1571,1573,1575})table[alef-1569][0]=1;
  field(old.getSuperclass(),"UY",table);field(now.getSuperclass(),"UY",table);
  field(old.getSuperclass(),"UZ",1569+table.length-1);field(now.getSuperclass(),"UZ",1569+table.length-1);
  Method oldShape=old.getDeclaredMethod("a",char[].class,int.class),newShape=now.getDeclaredMethod("a",char[].class,int.class);
  for(int i=0;i<2000;i++){
   char[] x=new char[random.nextInt(32)];for(int j=0;j<x.length;j++)x[j]=(char)(1560+random.nextInt(140));char[] y=x.clone();
   int length=random.nextInt(x.length+1);oldShape.invoke(null,x,length);newShape.invoke(null,y,length);
   if(!Arrays.equals(x,y))throw new AssertionError("CI shaping differs at "+i);
  }
  System.out.println(checks+" CI numeric-prefix cases and 2000 CI Arabic cases match original bytecode.");
 }
}
'''


class CIRecoveredFlowTest(unittest.TestCase):
    def test_numeric_prefix_and_arabic_lookahead(self):
        project = Path(__file__).resolve().parents[1]
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        directory = project / "core-source/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes"
        restored = directory / BASE / "ci.class"
        source = project / "app/src/main/java" / BASE / "ci.java"
        staged = project / "core-source/build/generated/core-java" / BASE / "ci.java"
        if not original.exists() or not restored.exists() or not (home / "bin/javac").exists():
            self.skipTest("Requires verified original conversion and compiled core-source CI")
        self.assertEqual(source.read_bytes(), staged.read_bytes(), "Compile core-source after changing CI")
        self.assertGreaterEqual(restored.stat().st_mtime_ns, staged.stat().st_mtime_ns,
                                "Compiled CI predates the staged source")
        with tempfile.TemporaryDirectory(prefix="ci-flow-") as temp, zipfile.ZipFile(original) as archive:
            root = Path(temp)
            for label, data in (("original", archive.read(BASE + "ci.class")), ("restored", restored.read_bytes())):
                output = root / label / BASE
                output.mkdir(parents=True)
                selected = fixture(data, {("dk", "(Ljava/lang/String;)I"), ("a", "([CI)V"),
                                          ("g", "(C)I"), ("h", "(C)C")})
                # Keep the real GLLib superclass, because CI inherits static g/h.
                _, raw_offset = constant_pool(data)
                superclass = struct.unpack_from(">H", data, raw_offset + 4)[0]
                _, selected_offset = constant_pool(selected)
                selected = bytearray(selected)
                struct.pack_into(">H", selected, selected_offset + 4, superclass)
                (output / "ci.class").write_bytes(selected)
                (output / "GLLib.class").write_bytes(gllib_fixture(archive.read(BASE + "GLLib.class")))
            runner = root / "CIFlowComparison.java"
            runner.write_text(RUNNER)
            subprocess.run([str(home / "bin/javac"), str(runner)], check=True, capture_output=True, text=True)
            result = subprocess.run([str(home / "bin/java"), "-cp", str(root), "CIFlowComparison",
                                     str(root / "original"), str(root / "restored")],
                                    capture_output=True, text=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn("5000 CI numeric-prefix cases and 2000 CI Arabic cases", result.stdout)
            print(result.stdout.strip())


if __name__ == "__main__": unittest.main()
