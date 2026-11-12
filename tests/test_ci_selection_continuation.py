"""Compare CI selection setup with the original method's unchanged Code.

Only test fixtures provide GUI peers; the production method is freshly extracted
from source, and its original counterpart comes from the verified DEX conversion.
"""
from pathlib import Path
import os
import shutil
import struct
import subprocess
import tempfile
import unittest
import zipfile

import test_gllib_recovered_algorithms as fixtures
from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
FIELDS = {'atw', 'atx', 'axo', 'avR', 'atc', 'awr', 'aws', 'awt', 'awu',
          'avP', 'aww', 'awv', 'awq', 'awk', 'atm', 'atf', 'aEe', 'aEh'}
RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class SelectionContinuationComparison {
 static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Class<?> peer(Class<?> c,String n)throws Exception{return Class.forName(P+n,true,c.getClassLoader());}
 static Field field(Class<?> c,String n)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);return f;}
 static void set(Class<?> c,String n,Object value)throws Exception{field(c,n).set(null,value);}
 static Object get(Class<?> c,String n)throws Exception{return field(c,n).get(null);}
 static Class<?> load(String p)throws Exception{return Class.forName(P+"ci",true,new URLClassLoader(new URL[]{Paths.get(p).toUri().toURL()},null));}
 static String run(Class<?> c,boolean enabled,int mode,int selection,int mask,int failure,boolean previous)throws Exception{
  Class<?> helpers=peer(c,"GLLib"),bank=peer(c,"j"),sprite=peer(c,"a");
  List<String> calls=(List<String>)get(helpers,"calls");calls.clear();set(helpers,"failure",failure);set(helpers,"refreshes",0);
  short[][][] controls=new short[2][96][24];for(int i=0;i<96;i++)controls[1][i][2]=(short)(200-i*7);
  int[][] layers=new int[4][13];layers[3][8]=27;layers[3][12]=31;
  set(c,"atc",controls);set(c,"atm",layers);set(c,"atx",previous);set(c,"atw",false);
  for(String n:new String[]{"axo","avR","avP","aww","awv","awq","awk"})set(c,n,5);
  for(String n:new String[]{"awr","aws","awt","awu"})set(c,n,new int[]{91,92});
  set(c,"aEe",new int[]{1,1,1,1});set(c,"aEh",new int[4]);
  Object sprites=Array.newInstance(sprite,21);Array.set(sprites,20,sprite.getConstructor().newInstance());set(c,"atf",sprites);
  int[][] rows=new int[68][20];
  for(int i=0;i<rows.length;i++){rows[i][3]=100+i;rows[i][4]=200+i;rows[i][5]=mode==0?i:i%4;rows[i][6]=i+7;rows[i][8]=0;rows[i][19]=0;}
  for(int i=64;i<68;i++)rows[i][8]=((mask>>(i-64))&1)==0?99:0;
  Object data=bank.getConstructor(int[][].class).newInstance((Object)rows);set(bank,"value",data);
  String outcome="return";Method target=c.getDeclaredMethod("c",boolean.class,int.class,int.class);target.setAccessible(true);
  try{target.invoke(null,enabled,mode,selection);}catch(InvocationTargetException caught){outcome=caught.getCause().getClass().getName();}
  StringBuilder result=new StringBuilder(outcome).append(calls);
  for(String n:new String[]{"atw","atx","axo","avR","avP","aww","awv","awq","awk"})result.append('/').append(n).append('=').append(get(c,n));
  for(String n:new String[]{"awr","aws","awt","awu"})result.append('/').append(n).append('=').append(Arrays.toString((int[])get(c,n)));
  return result+"/controls="+Arrays.deepToString(controls)+"/layers="+Arrays.deepToString(layers);
 }
 public static void main(String[] args)throws Exception{
  Class<?> old=load(args[0]),now=load(args[1]);int cases=0;
  for(boolean enabled:new boolean[]{false,true})for(boolean previous:new boolean[]{false,true})
   for(int mode=0;mode<3;mode++)for(int mask=0;mask<16;mask++)for(int failure=0;failure<3;failure++){
    int selection=mode==2?1:2;String x=run(old,enabled,mode,selection,mask,failure,previous),y=run(now,enabled,mode,selection,mask,failure,previous);
    if(!x.equals(y))throw new AssertionError("mode="+mode+" mask="+mask+" failure="+failure+" enabled="+enabled+" previous="+previous+"\noriginal="+x+"\nrestored="+y);cases++;
   }
  System.out.println("PASS: "+cases+" original/recovered selection, first-match continuation and failure outcomes match");
 }
}
'''


class SelectionContinuationTest(unittest.TestCase):
    def test_selection_setup_first_match_and_common_cleanup(self):
        jar = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', '/workspace/tooling/recovery-bytecode/greenfarm-original.jar'))
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-21.0.9+10'))
        java, javac = home/'bin/java', home/'bin/javac'
        if not jar.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original conversion and JDK')
        text = (ROOT/'app/src/main/java'/PACKAGE/'ci.java').read_text()
        prefix = 'package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
        fields = '''static boolean atw,atx;static int axo,avR,avP,aww,awv,awq,awk;
                    static short[][][] atc;static int[] awr,aws,awt,awu,aEe,aEh;
                    static int[][] atm;static a[] atf;'''
        with tempfile.TemporaryDirectory(prefix='ci-selection-differential-') as temp:
            root = Path(temp);source=root/'sources'/PACKAGE;source.mkdir(parents=True)
            (source/'ci.java').write_text(prefix+'public class ci extends GLLib {'+fields+
                                         extract_method(text,'static void c(boolean z, int i, int i2)')+'}')
            (source/'j.java').write_text(prefix+'''public class j {public static j value;public int[][] KW;
                public j(int[][] data){KW=data;}public static j bY(int id){GLLib.calls.add("bank:"+id);return value;}}''')
            (source/'a.java').write_text(prefix+'public class a {public int bB(int frame){return -13;}}')
            (source/'GLLib.java').write_text(prefix+'''public class GLLib {
                public static java.util.List<String> calls=new java.util.ArrayList<>();public static int refreshes,failure;
                public static void e(int a,int b,boolean c){calls.add("e:"+a+":"+b+":"+c);}
                public static void Ff(){calls.add("Ff");}public static void gK(int a){calls.add("gK:"+a);}
                public static void hd(int a){calls.add("hd:"+a);}public static void sr(){calls.add("sr");}
                public static void gM(int a){calls.add("gM:"+a);}public static void bl(int a,int b){calls.add("bl:"+a+":"+b);}
                public static void E(int a,int b,int c){calls.add("E:"+a+":"+b+":"+c);}
                public static void bj(int a,int b){calls.add("bj:"+a+":"+b);}
                public static int FC(){return 3;}public static int ii(int a){return a;}
                public static void gR(int a){calls.add("gR:"+a);if(++refreshes>1)throw new AssertionError("Repeated matched refresh");
                    if(failure==1)throw new IllegalStateException("refresh");if(failure==2)throw new AssertionError("refresh");}
            }''')
            restored=root/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=root/'original';shutil.copytree(restored,original)
            previous_methods,previous_fields=fixtures.METHODS,fixtures.FIELDS
            try:
                fixtures.METHODS={('c','(ZII)V')};fixtures.FIELDS=FIELDS
                with zipfile.ZipFile(jar) as archive:
                    data=archive.read(PACKAGE+'/ci.class');selected=bytearray(fixtures.method_fixture(data))
                _,original_offset=fixtures.constant_pool(data);_,offset=fixtures.constant_pool(selected)
                # Retain the real superclass name; its test-only methods record calls.
                struct.pack_into('>H',selected,offset+4,struct.unpack_from('>H',data,original_offset+4)[0])
            finally:
                fixtures.METHODS,fixtures.FIELDS=previous_methods,previous_fields
            (original/PACKAGE/'ci.class').write_bytes(selected)
            runner=root/'SelectionContinuationComparison.java';runner.write_text(RUNNER)
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(java),'-cp',str(root),'SelectionContinuationComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 576',result.stdout)
            print(result.stdout.strip())


if __name__=='__main__':unittest.main()
