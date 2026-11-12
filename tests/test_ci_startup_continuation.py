"""Compare startup continuation with the original converted fM bytecode.

The current case 38 is freshly compiled in a test-only shell. A second shell
receives the entire original fM method through ASM, preserving its instructions,
branches and exception handlers. Peer methods record calls or throw controlled
failures; no fixture is packaged in the application.
"""

from pathlib import Path
import os
import shutil
import subprocess
import sys
import tempfile
import unittest
import zipfile

from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from asm_tools import asm_jar
from android_build_tools import executable_name

PACKAGE = "com/gameloft/android/ANMP/GloftGF2F/S800x480"

SHELL = r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
public class ci {
 public static java.util.List<String> calls=new java.util.ArrayList<>();
 public static Throwable failure;
 public static int mlMode,mpMode,mpValue,state;
 public static int aTL,aTO,aTR,aTQ,aTP,auC,aYU,aYJ;
 public static boolean aUb,aTK,aTS,asw;
 public static String[][] ate;
 public static short[][][] atc;
 public static void cK(String value){calls.add("cK:"+value);}
 public static boolean rp(){calls.add("rp");return true;}
 public static void Mk(){calls.add("Mk");}
 public static int X(int month,int day,int year){calls.add("X:"+month+":"+day+":"+year);return 17;}
 public static void Mh(){calls.add("Mh");}
 public static int qD(){calls.add("qD");return state;}
 public static void gD(int value){calls.add("gD:"+value);}
 public static void p(int value,boolean visible){calls.add("p:"+value+":"+visible);}
 public static void e(int layer,int item,boolean visible){calls.add("e:"+layer+":"+item+":"+visible);}
 public static String dt(int value){calls.add("dt:"+value);return state==2?null:"unused";}
 static void raise(int mode,String marker){
  if(mode==1){failure=new IllegalStateException(marker);throw (RuntimeException)failure;}
  if(mode==2){failure=new AssertionError(marker);throw (Error)failure;}
 }
 public static void Ml(){calls.add("Ml");raise(mlMode,"Ml");}
 public static int Mp(){calls.add("Mp");raise(mpMode,"Mp");return mpValue;}
 %METHOD%
}
'''

COPIER = r'''
import java.nio.file.*;
import org.objectweb.asm.*;
public class OriginalStartupFixture {
 public static void main(String[] args)throws Exception {
  ClassWriter output=new ClassWriter(0);
  new ClassReader(Files.readAllBytes(Paths.get(args[0]))).accept(new ClassVisitor(Opcodes.ASM8,output){
   public void visit(int version,int access,String name,String sig,String parent,String[] interfaces){
    super.visit(Opcodes.V1_5,access,name,sig,parent,interfaces);
   }
   public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){
    return name.equals("fM")&&desc.equals("(I)V")?null:super.visitMethod(access,name,desc,sig,exceptions);
   }
   public void visitEnd(){}
  },ClassReader.SKIP_FRAMES);
  int[] copied={0};
  new ClassReader(Files.readAllBytes(Paths.get(args[1]))).accept(new ClassVisitor(Opcodes.ASM8){
   public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){
    if(!name.equals("fM")||!desc.equals("(I)V"))return null;
    copied[0]++;return output.visitMethod(access,name,desc,sig,exceptions);
   }
  },ClassReader.SKIP_FRAMES);
  if(copied[0]!=1)throw new AssertionError("Original fM not found");
  output.visitEnd();Files.write(Paths.get(args[0]),output.toByteArray());
 }
}
'''

RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class StartupContinuationComparison {
 static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Class<?> load(String path)throws Exception {
  return Class.forName(P+"ci",true,new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()},null));
 }
 static void set(Class<?> type,String name,Object value)throws Exception {type.getField(name).set(null,value);}
 static String run(Class<?> type,int ml,int mp,int value,int state,int saved,int entered,boolean input,int language)throws Exception {
  List<String> calls=(List<String>)type.getField("calls").get(null);calls.clear();
  set(type,"failure",null);set(type,"mlMode",ml);set(type,"mpMode",mp);set(type,"mpValue",value);
  set(type,"state",state);set(type,"aTO",saved);set(type,"aTL",entered);set(type,"aUb",input);
  set(type,"auC",input?1:2);set(type,"aYU",language);set(type,"aYJ",-777);
  set(type,"aTR",2000);set(type,"aTQ",4);set(type,"aTP",12);
  String[][] text=new String[1][5];text[0][4]="before";set(type,"ate",text);
  short[][][] gui=new short[1][2][6];gui[0][1][5]=-99;set(type,"atc",gui);
  Method method=type.getDeclaredMethod("fM",int.class);method.setAccessible(true);String result="ok";
  try{method.invoke(null,38);}catch(InvocationTargetException exception){
   Throwable failure=exception.getCause();
   result=failure.getClass().getName()+":same="+(failure==type.getField("failure").get(null));
  }
  return result+"/aYJ="+type.getField("aYJ").getInt(null)+"/aTL="+type.getField("aTL").getInt(null)
   +"/aUb="+type.getField("aUb").getBoolean(null)+"/text="+text[0][4]+"/gui="+gui[0][1][5]+"/"+calls;
 }
 public static void main(String[] args)throws Exception {
  Class<?> original=load(args[0]),restored=load(args[1]);int checks=0;
  for(int ml=0;ml<3;ml++)for(int mp=0;mp<3;mp++)for(int value:new int[]{0,1,7})
   for(int state:new int[]{2,4})for(int saved:new int[]{-1,21})for(boolean input:new boolean[]{false,true})
    for(int language:new int[]{0,7}){
     String before=run(original,ml,mp,value,state,saved,-1,input,language);
     String after=run(restored,ml,mp,value,state,saved,-1,input,language);
     if(!before.equals(after))throw new AssertionError("Ml="+ml+" Mp="+mp+" value="+value+" state="+state
      +" saved="+saved+" input="+input+" language="+language+"\noriginal="+before+"\nrestored="+after);
     checks++;
    }
  System.out.println("PASS: "+checks+" original/recovered startup continuation traces, values and throwable identities match");
 }
}
'''


class CIStartupContinuationTest(unittest.TestCase):
    def test_crm_success_caught_exception_and_error_continuation(self):
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        java, javac = (home / 'bin' / executable_name(name) for name in ('java', 'javac'))
        if not original.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest("Requires verified original conversion and JDK")
        asm = asm_jar()
        source = (ROOT / "app/src/main/java" / PACKAGE / "ci.java").read_text()
        method = extract_method(source, "static void fM(int i)")
        start = method.index("            case 38:")
        end = method.index("            case 39:", start)
        branch = "static void fM(int i){switch(i){" + method[start:end] + "}}"
        with tempfile.TemporaryDirectory(prefix="ci-startup-differential-") as temp:
            root = Path(temp)
            sources = root / "source" / PACKAGE
            sources.mkdir(parents=True)
            (sources / "ci.java").write_text(SHELL.replace("%METHOD%", branch))
            peer = root / "source/com/gameloft/android/wrapper/au.java"
            peer.parent.mkdir(parents=True)
            peer.write_text('''package com.gameloft.android.wrapper;
                import com.gameloft.android.ANMP.GloftGF2F.S800x480.ci;
                public class au {public static void el(String value){ci.calls.add("el:"+value);}
                public static void em(String value){ci.calls.add("em:"+value);}}''')
            # The whole original fM contains references in unexecuted states.
            # dex2jar can allocate their locals differently between conversions,
            # making JVM type-merge verification resolve this opaque peer before
            # state 38 executes. Supply the same type to both fixture classpaths;
            # no original instructions or state-38 behavior are rewritten.
            midlet = sources / "GloftGF2F.java"
            midlet.write_text('''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
                public class GloftGF2F {}''')
            restored = root / "restored"
            result = subprocess.run([str(javac), "-source", "8", "-target", "8", "-d", str(restored),
                                     str(sources / "ci.java"), str(peer), str(midlet)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            baseline = root / "original"
            shutil.copytree(restored, baseline)
            original_class = root / "original-ci.class"
            with zipfile.ZipFile(original) as archive:
                original_class.write_bytes(archive.read(PACKAGE + "/ci.class"))
            copier = root / "OriginalStartupFixture.java"
            copier.write_text(COPIER)
            runner = root / "StartupContinuationComparison.java"
            runner.write_text(RUNNER)
            result = subprocess.run([str(javac), '-cp', str(asm), str(copier), str(runner)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            classpath = os.pathsep.join((str(root), str(asm)))
            result = subprocess.run([str(java), "-cp", classpath, "OriginalStartupFixture",
                                     str(baseline / PACKAGE / "ci.class"), str(original_class)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), "-cp", str(root), "StartupContinuationComparison",
                                     str(baseline), str(restored)], capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn("PASS: 432", result.stdout)
            print(result.stdout.strip())


if __name__ == "__main__":
    unittest.main()
