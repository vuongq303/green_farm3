"""Compare module-color selection with the original unchanged render method.

Test-only rendering peers record color/target calls. The original method Code
is retained unchanged; current source is extracted and compiled for every run.
No fixture classes or graphics replacements are packaged into the game.
"""
from pathlib import Path
import os
import shutil
import struct
import subprocess
import tempfile
import unittest
import zipfile

from test_aa_tile_renderer import extract_method
from test_asprite_recovery import fixture
from test_gllib_recovered_algorithms import constant_pool


ROOT = Path(__file__).resolve().parents[1]
import sys
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class SpriteTintComparison {
 static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Field field(Class<?> c,String n)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);return f;}
 static void set(Class<?> c,Object o,String n,Object v)throws Exception{field(c,n).set(o,v);}
 static Object get(Class<?> c,Object o,String n)throws Exception{return field(c,n).get(o);}
 static Class<?> peer(Class<?> c,String n)throws Exception{return Class.forName(P+n,true,c.getClassLoader());}
 static Class<?> load(String p)throws Exception{return Class.forName(P+"a",true,new URLClassLoader(new URL[]{Paths.get(p).toUri().toURL()},null));}
 static Object allocate(Class<?> c)throws Exception{Field f=sun.misc.Unsafe.class.getDeclaredField("theUnsafe");f.setAccessible(true);return ((sun.misc.Unsafe)f.get(null)).allocateInstance(c);}
 static String run(Class<?> c,int flags,int effects,int base,int raw,int tint,boolean target,int index,boolean nullTypes)throws Exception{
  Object o=allocate(c);Class<?> w=peer(c,"w"),g=peer(c,"GLLib");
  set(w,null,"Zj",(flags&1)!=0);set(w,null,"Zm",(flags&2)!=0);
  set(w,null,"Zo",false);set(w,null,"Zp",false);set(w,null,"Zl",false);
  set(c,o,"Ia",nullTypes?null:new byte[]{(byte)253,(byte)253});set(c,o,"Ic",new int[]{raw,raw^0x00010203});
  set(c,o,"Gx","/atlas/module.bin");set(c,o,"Gy",9);
  set(g,null,"TX",effects);int[][] table=new int[19][11];table[14][0]=tint;set(g,null,"TY",table);
  set(g,null,"baseColor",base);set(g,null,"target",target);
  List<String> calls=(List<String>)get(g,null,"calls");calls.clear();
  Method m=c.getDeclaredMethod("a",peer(c,"aj"),int.class,float.class,float.class,float.class,float.class,int.class);m.setAccessible(true);
  String outcome="return";try{m.invoke(o,null,index,12.5f,19.25f,5.0f,7.5f,0);}catch(InvocationTargetException e){outcome=e.getCause().getClass().getName();}
  if(!outcome.equals("return")||calls.stream().noneMatch(s->s.startsWith("opaque:")||s.startsWith("alpha:")))
   throw new AssertionError("Valid fixture did not select its color: "+outcome+calls);
  return outcome+calls;
 }
 public static void main(String[] args)throws Exception{
  Class<?> old=load(args[0]),now=load(args[1]);int checks=0;
  for(int flags=0;flags<4;flags++)for(int effects:new int[]{0,512,16384,512|16384,262144|16384})
   for(int base:new int[]{0x00123456,0xff123456,0x80123456,0x00000000})for(int raw:new int[]{0x00abcdef,0xff010203,0x800a0b0c})
   for(int tint:new int[]{0x00fedcba,0xffaabbcc,0x80445566})for(boolean target:new boolean[]{false,true})
   for(int index=0;index<2;index++)for(boolean nullTypes:new boolean[]{false,true}){
    String x=run(old,flags,effects,base,raw,tint,target,index,nullTypes),y=run(now,flags,effects,base,raw,tint,target,index,nullTypes);
    if(!x.equals(y))throw new AssertionError("flags="+flags+" effects="+effects+" base="+base+" raw="+raw+" tint="+tint+" target="+target+" index="+index+"\noriginal="+x+"\nrestored="+y);checks++;
   }
  System.out.println("PASS: "+checks+" original/recovered grayscale, tint, base-color, alpha and target outcomes match");
 }
}
'''


class SpriteTintBranchTest(unittest.TestCase):
    def test_color_effect_guards_and_common_target_restore(self):
        jar=Path(os.environ.get('GREENFARM_ORIGINAL_JAR','/workspace/tooling/recovery-bytecode/greenfarm-original.jar'))
        home=Path(os.environ.get('JAVA_HOME','/workspace/tooling/jdk-21.0.9+10'))
        java,javac=home/'bin/java',home/'bin/javac'
        if not jar.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original conversion and JDK')
        text=legacy_core_source('a')
        signature='private void a(aj ajVar, int i, float f, float f2, float f3, float f4, int i2)'
        prefix='package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
        with tempfile.TemporaryDirectory(prefix='sprite-tint-differential-') as temp:
            root=Path(temp);source=root/'sources'/PACKAGE;source.mkdir(parents=True)
            (source/'a.java').write_text(prefix+'''public class a extends GLLib {
                int[] Ic;byte[] Ia;short[] GI;String Gx;int Gy;'''+extract_method(text,signature)+'}')
            (source/'aj.java').write_text(prefix+'public class aj {}')
            (source/'be.java').write_text(prefix+'public class be {public final String name;public be(String n){name=n;}}')
            (source/'w.java').write_text(prefix+'public class w {public static boolean Zj,Zm,Zo,Zp,Zl;}')
            (source/'AdRequest.java').write_text(prefix+'public class AdRequest {public static final int MAX_CONTENT_URL_LENGTH=512;}')
            (source/'GLURLConnection.java').write_text(prefix+'public class GLURLConnection {public static final int BUFFER_SIZE=16384;}')
            (source/'Constants.java').write_text(prefix+'public class Constants {public static final int APP_INDEXING_SCHEDULE_INTERVAL_MS=1000;}')
            (source/'GLLib.java').write_text(prefix+'''public class GLLib {
                public static int TX,baseColor;public static int[][] TY;public static boolean target;
                public static java.util.List<String> calls=new java.util.ArrayList<>();
                public int bH(int module){calls.add("base:"+module);return baseColor;}
                public int bW(int module){throw new AssertionError("Unselected geometry module");}
                public static int dx(int color){calls.add("grayscale:"+Integer.toHexString(color));return Integer.rotateLeft(color,7)^0x00123456;}
                public static boolean lB(){calls.add("target-enabled:"+target);return target;}
                public static be lD(){calls.add("save-target");return new be("saved");}
                public static be o(String path,int bank){calls.add("find-target:"+path+":"+bank);return new be("module");}
                public static void a(be target){calls.add("target:"+target.name);}
                public static void dk(int color){calls.add("opaque:"+Integer.toHexString(color));}
                public static void dl(int color){calls.add("alpha:"+Integer.toHexString(color));}
                public static void b(aj g,float x,float y,float w,float h,int a,int b,int flags){throw new AssertionError("Unselected gradient");}
                public static void c(aj g,float x,float y,float w,float h,int a,int b,int flags){throw new AssertionError("Unselected alpha gradient");}
                public static void a(aj g,float x,float y,float x2,float y2,boolean z){throw new AssertionError("Unselected line");}
                public static void a(aj g,float x,float y,float x2,float y2,float x3,float y3,boolean z){throw new AssertionError("Unselected triangle");}
                public static void a(aj g,float x,float y,float w,float h,int a,int b,boolean z){throw new AssertionError("Unselected arc");}
                public static void b(aj g,float x,float y,float w,float h,int a,int b,boolean z){throw new AssertionError("Unselected filled arc");}
                public static void b(aj g,float x,float y,float w,float h,boolean z){throw new AssertionError("Unselected fill");}
                public static void c(aj g,float x,float y,float w,float h,boolean z){throw new AssertionError("Unselected outline");}
            }''')
            restored=root/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=root/'original';shutil.copytree(restored,original)
            with zipfile.ZipFile(jar) as archive:
                data=archive.read(PACKAGE+'/a.class')
                selected=bytearray(fixture(data,{('a','(L'+PACKAGE+'/aj;IFFFFI)V')}))
            cp,offset=constant_pool(selected)
            superclass=next(i for i,v in cp.items() if isinstance(v,tuple) and v[0]==7 and cp[v[1]]==PACKAGE+'/GLLib')
            struct.pack_into('>H',selected,offset+4,superclass)
            (original/PACKAGE/'a.class').write_bytes(selected)
            runner=root/'SpriteTintComparison.java';runner.write_text(RUNNER)
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(java),'-cp',str(root),'SpriteTintComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 5760',result.stdout)
            print(result.stdout.strip())


if __name__=='__main__':unittest.main()
