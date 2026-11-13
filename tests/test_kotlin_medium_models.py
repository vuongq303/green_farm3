"""Compare converted Kotlin models/algorithms with the original APK's real JVM bytecode.

Only Android time and the packed resource reader are deterministic test peers.
This is not an Android, SDK authentication, or rendering test.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
NAMES = ('e', 'bv', 'bs', 'be', 'j', 'x', 'bl', 'ah', 'bt', 'cy', 'b')

PEERS = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class w {static boolean Vl=true;static int Zq=4;}
class GLLib {
 static long Tn=51,To=17;
 public String dr(int language){return "en";}
 static byte[][]tables=new byte[34][];
 static StringBuilder log=new StringBuilder();
 static void be(String value){log.append(value).append('|');}
 static void bk(String value){log.append("open:").append(value).append('|');}
 static void ke(){log.append("close|");}
 static byte[]db(int index){return tables[index];}
 static int l(byte[]v,int at){return(v[at]&255)|((v[at+1]&255)<<8)|((v[at+2]&255)<<16)|(v[at+3]<<24);}
 static void b(int value,byte[]v,int at){v[at]=(byte)(value>>>24);v[at+1]=(byte)(value>>>16);v[at+2]=(byte)(value>>>8);v[at+3]=(byte)value;}
 static short k(byte[]v,int at){return(short)((v[at]&255)|(v[at+1]<<8));}
}
'''

RUNNER = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;import java.security.*;import java.io.*;
public class MediumKotlinChecks {
 static final MessageDigest digest;
 static {try{digest=MessageDigest.getInstance("SHA-256");}catch(Exception e){throw new RuntimeException(e);}}
 static void word(int v){digest.update((byte)v);digest.update((byte)(v>>>8));digest.update((byte)(v>>>16));digest.update((byte)(v>>>24));}
 static void text(String v){try{digest.update(String.valueOf(v).getBytes("UTF-8"));}catch(Exception e){throw new RuntimeException(e);}}
 static void floats(float[][]v){for(float[]row:v)for(float f:row)word(Float.floatToIntBits(f));}
 static String thrown(Runnable action){try{action.run();return "none";}catch(Throwable e){return e.getClass().getName();}}
 static void matrixChecks(){
  Random r=new Random(379231);
  for(int trial=0;trial<1000;trial++){
   float[][]av=new float[4][4],bv=new float[4][4];
   for(int i=0;i<4;i++)for(int k=0;k<4;k++){av[i][k]=Float.intBitsToFloat(r.nextInt());bv[i][k]=Float.intBitsToFloat(r.nextInt());}
   be a=new be(av),b=new be(bv);floats(a.b(b).aiV);floats(a.c(b).aiV);
   cd in=new cd(),out=new cd();for(int k=0;k<4;k++)in.alf[k]=Float.intBitsToFloat(r.nextInt());
   a.a(in,out);for(float f:out.alf)word(Float.floatToIntBits(f));
   a.a(in,in);for(float f:in.alf)word(Float.floatToIntBits(f));
   a.ns();floats(a.aiV);a.nr();floats(a.aiV);
   float angle=Float.intBitsToFloat(r.nextInt());floats(be.g(angle).aiV);floats(be.h(angle).aiV);floats(be.i(angle).aiV);
  }
  text(thrown(()->new be((float[][])null).nr()));
  text(thrown(()->new be(new float[5][4]).nr()));
 }
 static void randomChecks(){
  for(long seed:new long[]{0,1,-1,Long.MIN_VALUE,Long.MAX_VALUE,31337177}){
   android.os.SystemClock.now=234567;
   bs random=new bs();random.t(seed);
   for(int i=0;i<200;i++)word(random.nextInt());
   for(int length:new int[]{0,1,4,19,20,21,41,120}){byte[]v=new byte[length+7];Arrays.fill(v,(byte)79);random.g(v,3,length);digest.update(v);}
   text(thrown(()->random.g(null,0,0)));text(thrown(()->random.g(null,0,1)));
   text(thrown(()->random.nextBytes(null)));
   random.setSeed(0);for(int i=0;i<20;i++)word(random.nextInt(333));
   random.setSeed(seed);byte[]v=new byte[500];random.nextBytes(v);digest.update(v);
  }
 }
 static void cloudChecks(){
  e save=new e();text(save.hR());text(save.hW());text(save.hU());text(save.hX());word(save.hQ());
  text(thrown(()->save.hV()));
  for(int size:new int[]{0,1,17,200,0}){byte[]v=new byte[size];for(int i=0;i<size;i++)v[i]=(byte)(i*19);save.a(new bp(v));Arrays.fill(v,(byte)71);digest.update(save.hT().data);}
  save.l(Long.MIN_VALUE);text(Long.toString(save.hS()));save.as(null);save.at("player");save.av(null);save.b((byte)255);save.au("old");save.au(null);
  text(save.hX());text(save.hU());text(save.hR());text(save.hV());word(save.hQ());
  text(thrown(()->save.a(save.hT())));digest.update(save.hT().data);
 }
 static void profilerChecks(){
  android.os.SystemClock.now=1;bl p=new bl("profiler");p.nz();
  for(int n=0;n<30;n++){android.os.SystemClock.now+=n*13-5;p.nA();if(n%4==0)p.nz();if(n%3==0){p.nC();p.nC();}word(p.getCount());text(Long.toString(p.nB()));text(p.toString());}
  bl q=new bl("other");word(p.a(q));word(((Comparable)p).compareTo(null));text(Boolean.toString(p.equals(new bl("profiler"))));
  text(thrown(()->new bl(null).equals(new bl(null))));text(thrown(()->((Comparable)p).compareTo("wrong")));
 }
 static void putInt(byte[]v,int at,int n){v[at]=(byte)n;v[at+1]=(byte)(n>>>8);v[at+2]=(byte)(n>>>16);v[at+3]=(byte)(n>>>24);}
 static void tableChecks(){
  text(thrown(()->j.bZ(0)));GLLib.log.setLength(0);
  Random random=new Random(546243);
  for(int table=0;table<34;table++){
   int rows=table%6,cols=table%5;byte[]v=new byte[8+cols+rows*cols*4];putInt(v,0,rows);putInt(v,4,cols);int at=8+cols;
   for(int col=0;col<cols;col++)v[8+col]=(byte)new int[]{1,2,4}[random.nextInt(3)];
   for(int row=0;row<rows;row++)for(int col=0;col<cols;col++)for(int k=0;k<v[8+col];k++)v[at++]=(byte)random.nextInt();
   GLLib.tables[table]=v;
  }
  j.il();for(int table=0;table<34;table++){j value=j.bY(table);word(value.KU);word(value.KV);if(value.KW!=null)for(int[]row:value.KW)for(int n:row)word(n);}
  j.bZ(7);text(GLLib.log.toString());text(thrown(()->j.bY(-1)));text(thrown(()->j.bY(34)));
 }
 static void adsChecks(){
  b bridge=new b();
  for(String placement:new String[]{"enter_section_iap_shop","enter_section_video_bubble","enter_section_video_lucky_wheel","enter_section_video_daily_stamp","unknown",null}){
   for(int kind:new int[]{0,3,9})for(int status:new int[]{-1,0,1,2,3}){
    b.JO=b.JP=b.JQ=b.JR=77;b.JK=b.JL=b.JM=b.JN=false;b.Kb=false;
    text(thrown(()->bridge.OnAdError(kind,placement)));
    text(thrown(()->bridge.OnAdFinished(kind,7,placement,null,14,17)));
    text(thrown(()->bridge.e(placement,status)));
    word(b.JO);word(b.JP);word(b.JQ);word(b.JR);text(""+b.JK+b.JL+b.JM+b.JN+b.Kb);
   }
  }
 }
 static void finish(String group){StringBuilder hex=new StringBuilder();for(byte v:digest.digest())hex.append(String.format("%02x",v&255));System.out.println("PASS medium Kotlin differential "+group+" "+hex);}
 public static void main(String[]args){matrixChecks();finish("matrix");randomChecks();finish("random");cloudChecks();finish("cloud");profilerChecks();finish("profiler");tableChecks();finish("tables");adsChecks();finish("ads");}
}
'''


class KotlinMediumModelsTest(unittest.TestCase):
    maxDiff = None
    def test_original_bytecode_matches_converted_models(self):
        original = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        if not original.is_file():
            self.skipTest('Original APK dex2jar artifact is required')
        core = Path(os.environ.get('KOTLIN_MEDIUM_SOURCE_DIR', ROOT / 'app/src/main/kotlin' / PACKAGE))
        android = Path(os.environ.get('ANDROID_HOME', '/workspace/tooling/android-sdk')) / 'platforms/android-28/android.jar'
        if not android.is_file():
            self.skipTest('Android28 compile API is required')
        # Separate JVMs avoid accidental loading of an original class in place of Kotlin.
        with tempfile.TemporaryDirectory(prefix='greenfarm-medium-kotlin-') as temporary:
            work = Path(temporary)
            old, new = work / 'original', work / 'kotlin'
            old.mkdir(); new.mkdir()
            with zipfile.ZipFile(original) as archive:
                for folder in (old, new):
                    for name in ('k', 'bp', 'cd'):
                        target = folder / PACKAGE / (name + '.class'); target.parent.mkdir(parents=True, exist_ok=True)
                        target.write_bytes(archive.read(PACKAGE + '/' + name + '.class'))
                for name in ('e', 'bs', 'be', 'j', 'bl', 'b'):
                    (old / PACKAGE / (name + '.class')).write_bytes(archive.read(PACKAGE + '/' + name + '.class'))
            peer = work / 'Peers.java'; peer.write_text(PEERS)
            clock = work / 'SystemClock.java'; clock.write_text('package android.os; public class SystemClock {public static long now=234567; public static long elapsedRealtime(){return now;}}')
            runner = work / 'MediumKotlinChecks.java'; runner.write_text(RUNNER)
            for folder in (old, new):
                result = subprocess.run([str(jdk_tool('javac')), '-encoding', 'UTF-8', '-source', '8', '-target', '8', '-classpath', str(folder), '-d', str(folder), str(peer), str(clock)], capture_output=True, text=True)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            compile_mixed([core / (name + '.kt') for name in ('e', 'bs', 'be', 'j', 'bl', 'b')], [], new,
                          classpath=(ROOT / 'app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes', original, android))
            # Call sites compile once against the original descriptors, then run unchanged on Kotlin.
            result = subprocess.run([str(jdk_tool('javac')), '-source', '8', '-target', '8', '-classpath', str(old), '-d', str(work), str(runner)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            outputs = []
            for folder in (old, new):
                result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(folder, work, original), PACKAGE.replace('/', '.') + '.MediumKotlinChecks'], capture_output=True, text=True, timeout=60)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                outputs.append(result.stdout.strip())
            self.assertEqual(outputs[0], outputs[1])
            self.assertIn('PASS medium Kotlin differential', outputs[1])
            print(outputs[1])
