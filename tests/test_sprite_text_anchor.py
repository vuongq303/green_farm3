"""Compare multiline text placement with original DEX method bytecode."""
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
public class TextAnchorComparison {
 static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Field field(Class<?> c,String n)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);return f;}
 static void set(Class<?> c,Object o,String n,Object v)throws Exception{field(c,n).set(o,v);}
 static Object get(Class<?> c,Object o,String n)throws Exception{return field(c,n).get(o);}
 static Class<?> peer(Class<?> c,String n)throws Exception{return Class.forName(P+n,true,c.getClassLoader());}
 static Class<?> load(String p)throws Exception{return Class.forName(P+"a",true,new URLClassLoader(new URL[]{Paths.get(p).toUri().toURL()},null));}
 static Object allocate(Class<?> c)throws Exception{Field f=sun.misc.Unsafe.class.getDeclaredField("theUnsafe");f.setAccessible(true);return ((sun.misc.Unsafe)f.get(null)).allocateInstance(c);}
 static String run(Class<?> c,int flags,int anchor,int lines,int start,int count,int limit,int clipping)throws Exception{
  Object o=allocate(c);Class<?> w=peer(c,"w"),g=peer(c,"GLLib");
  set(w,null,"XG",(flags&1)!=0);set(w,null,"XE",(flags&2)!=0);set(w,null,"Zo",(flags&4)!=0);
  set(c,o,"Jc",(flags&4)==0?-1:384);set(c,o,"IO",15);set(c,o,"HI",new int[]{7});set(c,o,"IV",true);set(c,o,"IU",false);set(c,o,"IN",(flags&8)!=0);
  for(String n:new String[]{"Je","Jf","Jg"})set(c,null,n,-1);set(c,null,"II",99);
  set(g,null,"SF",clipping==0?-1000:70);set(g,null,"SH",clipping==0?2000:40);
  List<String> calls=(List<String>)get(g,null,"calls");calls.clear();
  int stride=4+((flags&1)!=0?1:0)+((flags&2)!=0?1:0);short[] table=new short[1+lines*stride];table[0]=(short)lines;
  for(int i=0;i<lines;i++){int at=i*stride;table[at+1]=(short)(i*5);table[at+2]=4;table[at+3]=(short)(30+i*7);table[at+4]=(short)(15+i*3);if((flags&1)!=0)table[at+5]=(short)(3+((i&1)==0?4096:8192));}
  Class<?> aj=peer(c,"aj");Method m=c.getDeclaredMethod("a",aj,String.class,short[].class,int.class,int.class,int.class,int.class,int.class,int.class);
  m.setAccessible(true);String outcome="return";
  try{m.invoke(o,null,"a b c d e f g h i j k l m n o p q r s t",table,120,100,start,count,anchor,limit);}catch(InvocationTargetException e){outcome=e.getCause().getClass().getName();}
  return outcome+calls+"/palette="+Arrays.toString((int[])get(c,o,"HI"))+"/IV="+get(c,o,"IV")+"/IU="+get(c,o,"IU")+"/Je="+get(c,null,"Je")+"/Jf="+get(c,null,"Jf")+"/Jg="+get(c,null,"Jg");
 }
 public static void main(String[] args)throws Exception{
  Class<?> old=load(args[0]),now=load(args[1]);int checks=0;
  for(int flags=0;flags<16;flags++)for(int anchor:new int[]{0,1,2,3,8,10,32,33,34,40,43})for(int lines=1;lines<=4;lines++)
   for(int start=0;start<Math.min(2,lines);start++)for(int count:new int[]{-1,1,2})for(int clipping=0;clipping<2;clipping++){
    int limit=(checks%3==0)?3:-1;String x=run(old,flags,anchor,lines,start,count,limit,clipping),y=run(now,flags,anchor,lines,start,count,limit,clipping);
    if(!x.equals(y))throw new AssertionError("flags="+flags+" anchor="+anchor+" lines="+lines+" start="+start+" count="+count+"\noriginal="+x+"\nrestored="+y);checks++;
   }
  System.out.println("PASS: "+checks+" original/recovered multiline anchor, clipping, range and style outcomes match");
 }
}
'''


class SpriteTextAnchorTest(unittest.TestCase):
    def test_original_multiline_positions_and_anchor_precedence(self):
        jar=Path(os.environ.get('GREENFARM_ORIGINAL_JAR','/workspace/tooling/recovery-bytecode/greenfarm-original.jar'))
        home=Path(os.environ.get('JAVA_HOME','/workspace/tooling/jdk-21.0.9+10'))
        java,javac=home/'bin/java',home/'bin/javac'
        if not jar.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original conversion and JDK')
        text=legacy_core_source('a')
        signature='public void a(aj ajVar, String str, short[] sArr, int i, int i2, int i3, int i4, int i5, int i6)'
        prefix='package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
        with tempfile.TemporaryDirectory(prefix='sprite-anchor-differential-') as temp:
            root=Path(temp);source=root/'sources'/PACKAGE;source.mkdir(parents=True)
            (source/'a.java').write_text(prefix+'''public class a extends GLLib {
                static int II,Je,Jf,Jg;int[] HI;int IO,Jc;boolean IV,IU,IN;'''+extract_method(text,signature)+'}')
            (source/'aj.java').write_text(prefix+'public class aj {}')
            (source/'w.java').write_text(prefix+'public class w {public static boolean XG,XE,Zo;}')
            (source/'GLLib.java').write_text(prefix+'''public class GLLib {
                public static int SF,SH;public static java.util.List<String> calls=new java.util.ArrayList<>();
                public int hA(){return 3;}public static int b(short[] data,int line){return line*3-2;}
                public void bQ(int palette){calls.add("palette:"+palette);}
                public void a(aj g,String text,int x,int y,int anchor,boolean z){calls.add("draw:"+x+":"+y+":"+anchor+":"+z);}
            }''')
            restored=root/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=root/'original';shutil.copytree(restored,original)
            with zipfile.ZipFile(jar) as archive:
                data=archive.read(PACKAGE+'/a.class')
                selected=bytearray(fixture(data,{('a','(L'+PACKAGE+'/aj;Ljava/lang/String;[SIIIIII)V')}))
            cp,offset=constant_pool(selected)
            superclass=next(i for i,v in cp.items() if isinstance(v,tuple) and v[0]==7 and cp[v[1]]==PACKAGE+'/GLLib')
            struct.pack_into('>H',selected,offset+4,superclass)
            (original/PACKAGE/'a.class').write_bytes(selected)
            runner=root/'TextAnchorComparison.java';runner.write_text(RUNNER)
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(java),'-cp',str(root),'TextAnchorComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 7392',result.stdout)
            print(result.stdout.strip())


if __name__=='__main__':unittest.main()
