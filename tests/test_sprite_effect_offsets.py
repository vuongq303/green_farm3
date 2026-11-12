"""Compare sprite transform branches with original, unchanged method Code."""
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


ROOT=Path(__file__).resolve().parents[1]
PACKAGE='com/gameloft/android/ANMP/GloftGF2F/S800x480'
RUNNER=r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class SpriteEffectOffsetComparison {
 static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static Field field(Class<?> c,String n)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);return f;}
 static void set(Class<?> c,Object o,String n,Object v)throws Exception{field(c,n).set(o,v);}
 static Object get(Class<?> c,Object o,String n)throws Exception{return field(c,n).get(o);}
 static Class<?> peer(Class<?> c,String n)throws Exception{return Class.forName(P+n,true,c.getClassLoader());}
 static Class<?> load(String p)throws Exception{return Class.forName(P+"a",true,new URLClassLoader(new URL[]{Paths.get(p).toUri().toURL()},null));}
 static Object allocate(Class<?> c)throws Exception{Field f=sun.misc.Unsafe.class.getDeclaredField("theUnsafe");f.setAccessible(true);return ((sun.misc.Unsafe)f.get(null)).allocateInstance(c);}
 static String run(Class<?> c,boolean module,int transform,int flags,boolean zo,int effect,int scaling,int mapping,int paint)throws Exception{
  Object o=allocate(c);Class<?> g=peer(c,"GLLib"),w=peer(c,"w"),aj=peer(c,"aj");
  List<String> calls=(List<String>)get(g,null,"calls");calls.clear();
  for(String n:new String[]{"Xz","Xl","Xk","Xg","Zn","XN"})set(w,null,n,false);
  for(String n:new String[]{"WY","Xi","Xp"})set(w,null,n,true);
  set(w,null,"Zo",zo);set(w,null,"Xm",(flags&1)!=0);set(w,null,"WC",mapping!=0);set(w,null,"Zp",(flags&2)!=0);
  set(g,null,"TX",effect==0?0:262144);set(g,null,"Rk",16);set(g,null,"offx",-5+flags);set(g,null,"offy",11-effect);
  set(g,null,"moduleFlags",flags|((paint&1)!=0?16:0));set(g,null,"paint",paint==0?0:2);
  int[][] effects=new int[19][11];effects[18][1]=scaling==0?100:130;effects[18][3]=150;effects[18][4]=75;
  effects[18][5]=effect%2;effects[18][6]=new int[]{0,0,1,16,0,-17}[effect];effects[18][10]=effect==4?1:0;
  set(g,null,"TY",effects);
  set(c,null,"HW",new int[]{1,0,3,2,6,7,4,5});set(c,null,"HX",new int[]{2,3,0,1,5,4,7,6});set(c,null,"HY",new int[]{4,5,6,7,3,2,1,0});
  set(c,null,"HU",new float[8]);set(c,null,"HQ",paint==0?0:1);set(c,null,"Ir",false);set(c,null,"Iu",-1);
  set(c,o,"Hg",new short[]{0});set(c,o,"Hi",new short[]{3});set(c,o,"Hh",new byte[]{3});set(c,o,"Ho",new byte[]{(byte)flags});
  set(c,o,"Hz",0);set(c,o,"HL",0);set(c,o,"HS",paint==0?1:2);set(c,o,"Hs",mapping==0?-1:0);
  set(c,o,"HA",mapping==0?0:262144|(mapping==1?524288:0));set(c,o,"HI",new int[]{7});set(c,o,"Hb",new byte[]{4});
  set(c,o,"Hp",new short[][]{{0,2,2}});set(c,o,"Hq",new short[][]{{0,0,7,-4,0,0}});set(c,o,"Hr",new byte[][]{{0,0,-3,6,0,0}});
  set(c,o,"GE",new short[]{0,31,45,16});set(c,o,"GF",new short[]{0,17,23,16});set(c,o,"GG",new byte[]{0,31,45,16});set(c,o,"GH",new byte[]{0,17,23,16});
  String outcome="return";Method m=module?c.getDeclaredMethod("a",aj,int.class,int.class,float.class,float.class,int.class):c.getDeclaredMethod("a",aj,int.class,int.class,int.class,int.class,int.class);
  m.setAccessible(true);
  try{if(module)m.invoke(o,null,0,0,100.25f,80.5f,transform);else m.invoke(o,null,0,0,100,80,transform);}
  catch(InvocationTargetException failure){outcome=failure.getCause().getClass().getName();}
  if(!outcome.equals("return")||calls.stream().noneMatch(s->s.startsWith("frame:")||s.startsWith("module:")))
   throw new AssertionError("Valid fixture did not finish its draw: "+outcome+calls);
  return outcome+calls+"/effects="+Arrays.deepToString(effects)+"/HI="+Arrays.toString((int[])get(c,o,"HI"));
 }
 public static void main(String[] args)throws Exception{
  Class<?> old=load(args[0]),now=load(args[1]);int checks=0;
  for(boolean module:new boolean[]{false,true})for(int transform=0;transform<8;transform++)for(int flags=0;flags<8;flags++)
   for(boolean zo:new boolean[]{false,true})for(int effect=0;effect<6;effect++)for(int scaling=0;scaling<2;scaling++)
    for(int mapping=0;mapping<3;mapping++)for(int paint=0;paint<2;paint++){
     String x=run(old,module,transform,flags,zo,effect,scaling,mapping,paint),y=run(now,module,transform,flags,zo,effect,scaling,mapping,paint);
     if(!x.equals(y))throw new AssertionError("module="+module+" transform="+transform+" flags="+flags+" zo="+zo+" effect="+effect+" scaling="+scaling+" mapping="+mapping+" paint="+paint+"\noriginal="+x+"\nrestored="+y);checks++;
    }
  System.out.println("PASS: "+checks+" original/recovered animation and module transform/effect call traces match");
 }
}
'''


class SpriteEffectOffsetTest(unittest.TestCase):
    def test_animation_and_module_offset_preservation(self):
        jar=Path(os.environ.get('GREENFARM_ORIGINAL_JAR','/workspace/tooling/recovery-bytecode/greenfarm-original.jar'))
        home=Path(os.environ.get('JAVA_HOME','/workspace/tooling/jdk-21.0.9+10'))
        java,javac=home/'bin/java',home/'bin/javac'
        if not jar.is_file() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original conversion and JDK')
        text=(ROOT/'app/src/main/java'/PACKAGE/'a.java').read_text()
        signatures=('public void a(aj ajVar, int i, int i2, int i3, int i4, int i5)',
                    'public void a(aj ajVar, int i, int i2, float f, float f2, int i3)')
        prefix='package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
        fields='''static int[] HW,HX,HY;static float[] HU;static boolean Ir,It,Ix;static int Is,Iu,Iv,Iw,HQ;
            int Hz,HL,Hs,HA,HS;short[] Hg,Hi,GE,GF;byte[] Hh,Ho,Hb,GG,GH;int[] HI;
            short[][] Hp,Hq;byte[][] Hr;'''
        with tempfile.TemporaryDirectory(prefix='sprite-offset-differential-') as temp:
            root=Path(temp);source=root/'sources'/PACKAGE;source.mkdir(parents=True)
            (source/'a.java').write_text(prefix+'public class a extends GLLib {'+fields+'\n'+
                                       '\n'.join(extract_method(text,s) for s in signatures)+'}')
            (source/'aj.java').write_text(prefix+'public class aj {}')
            (source/'w.java').write_text(prefix+'public class w {public static boolean Xz,Xm,Xl,Xk,Zo,Xg,WC,Xi,WY,Xp,Zp,Zn,XN;}')
            (source/'GLURLConnection.java').write_text(prefix+'public class GLURLConnection {public static final int BUFFER_SIZE=16384;}')
            (source/'Constants.java').write_text(prefix+'public class Constants {public static final int APP_INDEXING_SCHEDULE_INTERVAL_MS=1000;}')
            (source/'GLLib.java').write_text(prefix+'''public class GLLib {
                public static int TX,Rk,offx,offy,moduleFlags,paint;public static int[][] TY;
                public static GLLib QD;public static java.util.List<String> calls=new java.util.ArrayList<>();
                public static void be(String s){calls.add("log:"+s);}public static void l(int e,int k,int v){calls.add("effect:"+e+":"+k+":"+v);TY[e][k]=v;}
                public int bu(int i){return offx;}public int bv(int i){return offy;}
                public int v(int i,int j){return 0;}public int bD(int i){return moduleFlags;}public int bC(int i){return 1;}
                public int bw(int i){return offx;}public int bx(int i){return offy;}public int hJ(){return paint;}
                public int by(int i){return 20;}public int bz(int i){return 30;}public int bA(int i){return 2;}public int bB(int i){return 3;}
                public void a(aj g,int i,float x,float y,int flags){calls.add("frame:"+i+":"+x+":"+y+":"+flags);}
                public void a(aj g,int i,float x,float y,int flags,int width,int height){calls.add("module:"+i+":"+x+":"+y+":"+flags+":"+width+":"+height);}
                public void a(a s,int i,int n,int flags,int x,int y,boolean enabled){calls.add("startCallback");}
                public void a(a s,int i,int n,int x,int y,boolean enabled){calls.add("endCallback");}
                public static void a(float f,float f2,float f3,float f4,float f5,float f6,float f7,float f8,float f9,float f10,float f11,float f12){calls.add("perspective");}
                public static float kA(){return 0;}public static float kB(){return 0;}
            }''')
            restored=root/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=root/'original';shutil.copytree(restored,original)
            with zipfile.ZipFile(jar) as archive:
                data=archive.read(PACKAGE+'/a.class')
                selected=bytearray(fixture(data,{('a','(L'+PACKAGE+'/aj;IIIII)V'),('a','(L'+PACKAGE+'/aj;IIFFI)V')}))
            cp,offset=constant_pool(selected)
            superclass=next(i for i,v in cp.items() if isinstance(v,tuple) and v[0]==7 and cp[v[1]]==PACKAGE+'/GLLib')
            struct.pack_into('>H',selected,offset+4,superclass)
            # Supply original lookup arrays without executing unrelated clinit.
            fields=offset+8+2*struct.unpack_from('>H',selected,offset+6)[0]
            count=struct.unpack_from('>H',selected,fields)[0];fields+=2
            for _ in range(count):
                flags,_,_,attributes=struct.unpack_from('>HHHH',selected,fields)
                struct.pack_into('>H',selected,fields,flags & ~0x10)
                fields+=8
                for _ in range(attributes):
                    fields+=6+struct.unpack_from('>I',selected,fields+2)[0]
            (original/PACKAGE/'a.class').write_bytes(selected)
            runner=root/'SpriteEffectOffsetComparison.java';runner.write_text(RUNNER)
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(java),'-cp',str(root),'SpriteEffectOffsetComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 18432',result.stdout)
            print(result.stdout.strip())


if __name__=='__main__':unittest.main()
