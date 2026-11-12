"""Differential quest checks against the original APK's converted instructions.

The fixtures isolate target/action matching, objective counters and completion
transitions; Android initialization, economy/UI and ownership census peers are
recorders. No test fixture is packaged into the game. Paid objective skips and
informational tutorial steps are outside this test and are not redefined here.
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

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
SIGNATURES = (
    'static int a(boolean z, int i, int i2, int i3, int i4, boolean z2)',
    'static int c(int i, int i2, int i3, boolean z)',
    'static boolean lP(int i)',
    'static boolean lF(int i)',
    'static int ih(int i)',
    'static int ii(int i)',
)

SHELL = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
public class ci {
public static int aCY=9,aCZ=-1413816336,aVM; public static char aVG=3; public static boolean awC;public static char awD;
public static short[] aZG=new short[76],aZE=new short[24];public static int[]aZJ=new int[10];
public static int[]aVD=new int[3],aVc=new int[3],aVd=new int[3],aVe=new int[3],aVf=new int[3],aVk=new int[3],aVl=new int[3],aVm=new int[3],aVn=new int[3],aVs=new int[3],aVt=new int[3],aVu=new int[3],aVv=new int[3];
public static boolean[]aVh=new boolean[3],aVp=new boolean[3],aVx=new boolean[3]; public static String trace="";
public static void ab(int a,int b,int c){trace+="ab:"+a+":"+b+":"+c+";";}public static void gX(int a){trace+="gx:"+a+";";} public static void J(int i,boolean z){aVD[i]=3;trace+="done:"+i+";";}public static void a(boolean z,int i,char c){trace+="notify:"+i+":"+(int)c+";";}public static void lQ(int i){trace+="census:"+i+";";}
public static int aVF,aYS=-1,aYR=-1,ban,bao;public static boolean aVL,aYW,awV;
public static void gG(int i){}public static void DF(){}public static void gM(int i){}public static void Ll(){}public static void gW(int i){}public static int jO(){return 0;}public static String dt(int i){return "";}public static void dC(String s){}public static void tN(){trace+="claim;";}public static void tJ(){trace+="dismiss;";}public static void Lo(){}public static void Ln(){}
'''

COPIER = r'''import java.nio.file.*;import org.objectweb.asm.*;
public class CopyQuest {
static boolean wanted(String n,String d){return n.equals("a")&&d.equals("(ZIIIIZ)I")||n.equals("c")&&d.equals("(IIIZ)I")||(n.equals("lP")||n.equals("lF"))&&d.equals("(I)Z");}
public static void main(String[]x)throws Exception{ClassWriter w=new ClassWriter(0);new ClassReader(Files.readAllBytes(Paths.get(x[0]))).accept(new ClassVisitor(Opcodes.ASM8,w){public void visit(int v,int a,String n,String s,String p,String[]i){super.visit(Opcodes.V1_5,a,n,s,p,i);} public MethodVisitor visitMethod(int a,String n,String d,String s,String[]e){return wanted(n,d)?null:super.visitMethod(a,n,d,s,e);}public void visitEnd(){}},ClassReader.SKIP_FRAMES);new ClassReader(Files.readAllBytes(Paths.get(x[1]))).accept(new ClassVisitor(Opcodes.ASM8){public MethodVisitor visitMethod(int a,String n,String d,String s,String[]e){return wanted(n,d)?w.visitMethod(a,n,d,s,e):null;}},ClassReader.SKIP_FRAMES);w.visitEnd();Files.write(Paths.get(x[0]),w.toByteArray());}}
'''

RUNNER = r'''import java.net.*;import java.nio.file.*;import java.lang.reflect.*;import java.util.*;
public class CompareQuest{
static Class<?> load(String s)throws Exception{Class<?>c=Class.forName("com.gameloft.android.ANMP.GloftGF2F.S800x480.ci",true,new URLClassLoader(new URL[]{Paths.get(s).toUri().toURL()},null));checkQuestOpen(c);return c;}
static Object get(Class<?>c,String n)throws Exception{return c.getField(n).get(null);}static void set(Class<?>c,String n,Object v)throws Exception{c.getField(n).set(null,v);}static Object call(Class<?>c,String n,Class<?>[]d,Object...a)throws Exception{Method m=c.getDeclaredMethod(n,d);m.setAccessible(true);return m.invoke(null,a);}
static void checkQuestOpen(Class<?>c)throws Exception{set(c,"aVF",1);for(int state=1;state<=3;state++){set(c,"aVD",new int[]{1,state,3});set(c,"aVc",new int[]{17,23,31});set(c,"trace","");for(int repeat=0;repeat<3;repeat++){call(c,"lF",new Class[]{int.class},1);if(!Arrays.equals((int[])get(c,"aVD"),new int[]{1,state==1?2:state,3})||!Arrays.equals((int[])get(c,"aVc"),new int[]{17,23,31})||!get(c,"trace").equals(""))throw new AssertionError("Opening/tracking must not complete, change counters, or claim rewards");}}}
static void setup(Class<?>c,long seed)throws Exception{Random r=new Random(seed);for(String n:new String[]{"aZG","aZE"}){short[]v=(short[])get(c,n);for(int i=0;i<v.length;i++)v[i]=(short)(2000+i);}int[]z=(int[])get(c,"aZJ");for(int i=0;i<z.length;i++)z[i]=4000+i;set(c,"trace","");set(c,"aVM",-1);set(c,"awC",false);set(c,"awD",(char)0);for(String n:new String[]{"aVD","aVf","aVn","aVv"}){int[]v=(int[])get(c,n);for(int i=0;i<3;i++)v[i]=n.equals("aVD")?r.nextInt(4):r.nextInt(42);}for(String n:new String[]{"aVe","aVm","aVu"}){int[]v=(int[])get(c,n);for(int i=0;i<3;i++)v[i]=r.nextBoolean()?2000+r.nextInt(80):r.nextInt(20);}for(String n:new String[]{"aVc","aVd","aVk","aVl","aVs","aVt"}){int[]v=(int[])get(c,n);for(int i=0;i<3;i++)v[i]=(Integer)call(c,"ih",new Class[]{int.class},r.nextInt(6));}for(String n:new String[]{"aVh","aVp","aVx"}){boolean[]v=(boolean[])get(c,n);for(int i=0;i<3;i++)v[i]=r.nextBoolean();}}
static String snapshot(Class<?>c)throws Exception{String s=get(c,"trace")+"|"+get(c,"aVM")+"|"+get(c,"awC")+"|"+get(c,"awD");for(String n:new String[]{"aVD","aVc","aVd","aVk","aVl","aVs","aVt"})s+="|"+Arrays.toString((int[])get(c,n));for(String n:new String[]{"aVh","aVp","aVx"})s+="|"+Arrays.toString((boolean[])get(c,n));return s;}
public static void main(String[]a)throws Exception{Class<?>o=load(a[0]),n=load(a[1]);Random r=new Random(44220);for(Class<?>c:new Class[]{o,n})setup(c,1);Class<?>[]pred={boolean.class,int.class,int.class,int.class,int.class,boolean.class};for(int i=0;i<20000;i++){Object[]p={r.nextBoolean(),r.nextBoolean()?2989+r.nextInt(11):2000+r.nextInt(80),r.nextBoolean()?2000+r.nextInt(80):r.nextInt(5000),r.nextInt(42),r.nextInt(42),r.nextBoolean()};Object x=call(o,"a",pred,p),y=call(n,"a",pred,p);if(!x.equals(y))throw new AssertionError("predicate "+Arrays.toString(p)+":"+x+"/"+y);}for(int i=0;i<10000;i++){setup(o,i);setup(n,i);Object[]p={r.nextBoolean()?2000+r.nextInt(80):r.nextInt(20),r.nextInt(8)-2,r.nextInt(42),r.nextBoolean()};Object x=call(o,"c",new Class[]{int.class,int.class,int.class,boolean.class},p),y=call(n,"c",new Class[]{int.class,int.class,int.class,boolean.class},p);if(!x.equals(y)||!snapshot(o).equals(snapshot(n)))throw new AssertionError("progress case "+i+" "+Arrays.toString(p)+"\n"+snapshot(o)+"\n"+snapshot(n));}for(int i=0;i<1000;i++){setup(o,i);setup(n,i);Object x=call(o,"lP",new Class[]{int.class},i%3),y=call(n,"lP",new Class[]{int.class},i%3);if(!x.equals(y)||!snapshot(o).equals(snapshot(n)))throw new AssertionError("complete "+i);}System.out.println("PASS 20000 objective predicates, 10000 progress traces, 1000 completion traces match original bytecode");}}
'''

class CIQuestProgressTest(unittest.TestCase):
    def test_original_matching_progress_and_completion(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        java = home / 'bin' / executable_name('java')
        javac = home / 'bin' / executable_name('javac')
        original = Path(os.environ.get('GREENFARM_ORIGINAL_JAR',
                        ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        if not original.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original conversion and installed JDK')
        asm = asm_jar()
        source = (ROOT / 'app/src/main/java' / PACKAGE / 'ci.java').read_text()
        methods = '\n'.join(extract_method(source, signature) for signature in SIGNATURES)
        with tempfile.TemporaryDirectory(prefix='ci-quest-differential-') as temporary:
            directory = Path(temporary)
            fixture = directory / 'source' / PACKAGE / 'ci.java'
            fixture.parent.mkdir(parents=True)
            fixture.write_text(SHELL + methods + '\n}')
            restored, baseline = directory / 'restored', directory / 'original'
            result = subprocess.run([str(javac), '-source', '8', '-target', '8',
                                     '-d', str(restored), str(fixture)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            shutil.copytree(restored, baseline)
            bytecode = directory / 'original-ci.class'
            with zipfile.ZipFile(original) as archive:
                bytecode.write_bytes(archive.read(PACKAGE + '/ci.class'))
            copier, runner = directory / 'CopyQuest.java', directory / 'CompareQuest.java'
            copier.write_text(COPIER)
            runner.write_text(RUNNER)
            result = subprocess.run([str(javac), '-cp', str(asm), str(copier), str(runner)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), '-cp', os.pathsep.join((str(directory), str(asm))),
                                     'CopyQuest', str(baseline / PACKAGE / 'ci.class'), str(bytecode)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), '-cp', str(directory), 'CompareQuest',
                                     str(baseline), str(restored)], capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS 20000 objective predicates, 10000 progress traces, 1000 completion traces', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
