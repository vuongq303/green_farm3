"""Full production global-gameplay codec across fresh host JVMs.

The complete ax/restoreGlobalGameplay/rt bodies and byte/economy codecs execute;
only storage, SDK identity and resource/quest side effects are peers. This is NOT
an Android lifecycle/SQLite test and does not simulate the separate farm entity
record. It verifies global inventory, currencies, quests and timestamp arrays.
"""
from pathlib import Path
import hashlib
import re
import subprocess
import sys
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PACKAGE = 'com.quanhoang.greenfarm3.S800x480'
JAVA = ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/')
KOTLIN = ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/')

ENGINE_HELPERS = (
    'static boolean ax(boolean z)', 'static boolean restoreGlobalGameplay(boolean z)',
    'static void rt()', 'static String o(byte[] bArr, int i)',
    'static int b(byte[] bArr, int i, String str)', 'static String rv()', 'static String rl()',
    'static int ih(int i)', 'static int ii(int i)', 'static bi ij(int i)',
    'static bk v(long j)', 'static long w(long j)', 'static long x(long j)',
    'static int a(bi biVar)', 'static long a(bk bkVar)',
    'static int GJ()', 'static int GK()', 'static int GY()',
    'static void iG(int i)', 'static void iH(int i)', 'static void iS(int i)', 'static void FX()',
)
RUNTIME_HELPERS = (
    'public static int a(byte[] bArr, int i, byte b)',
    'public static int a(byte[] bArr, int i, short s)',
    'public static int d(byte[] bArr, int i, int i2)',
    'public static int a(byte[] bArr, int i, long j)',
    'public static byte j(byte[] bArr, int i)', 'public static short k(byte[] bArr, int i)',
    'public static int l(byte[] bArr, int i)', 'public static long m(byte[] bArr, int i)',
    'public static void a(Object obj, int i, Object obj2, int i2, int i3)',
    'public static int a(byte[] bArr, int i, byte[] bArr2)',
    'public static int b(byte[] bArr, int i, byte[] bArr2)',
)

PEERS = r'''
class AndroidWebView {static final String UTF_8="UTF-8";}
class AppEventsConstants {static final String EVENT_PARAM_VALUE_NO="0";}
class SystemClock {static long elapsedRealtime(){return 123456789L;}}
class w {static boolean Vl=true;}
class j {int KV;int[][]KW=new int[0][0];static j bY(int kind){throw new AssertionError("unexpected legacy resource access");}}
class db {String bcf;}
class ProgressSaveController {
 static boolean atSnapshotBoundary(){return true;}static boolean canSerialize(){return true;}
 static void requestSave(){throw new AssertionError("unexpected deferred capture");}
}
class GameRuntime {
 static byte[] record;static long Tn,To;static int reads,writes;
 static byte[] B(String name,String binding){if(!name.equals("save_gameplay"))throw new AssertionError(name);reads++;return record==null?null:record.clone();}
 static void a(String name,byte[]data,String binding){if(!name.equals("save_gameplay"))throw new AssertionError(name);writes++;record=data.clone();}
 %RUNTIME%
}
class FarmGameEngine extends GameRuntime {
 %FIELDS%
 // Resource-dependent side effects are deliberately outside this codec test.
 static int FC(){return 5;}static void id(int n){throw new AssertionError("unexpected legacy level resource");}
 static void ro(){throw new AssertionError("restore attempted header write");}
 static void j(int quest,int index,boolean reset){}static void Ex(){}static void LJ(){}static void JV(){}
 static void Hp(){throw new AssertionError("new-game initialization must not run");}
 static void a(int i,int kind,long start,int count,boolean done,byte mode){throw new AssertionError("recipe peers not configured");}
 static void a(int i,int kind,long start,int count,byte mode){throw new AssertionError("recipe peers not configured");}
 %ENGINE%
}
'''

RUNNER = r'''
package com.quanhoang.greenfarm3.S800x480;
import java.lang.reflect.*;import java.nio.file.*;import java.util.*;
public class GlobalGameplayCodecCheck {
 static final String UUID_A="11111111-2222-3333-4444-555555555555";
 static void check(boolean b,String m){if(!b)throw new AssertionError(m);}
 static void allocate()throws Exception {
  for(Field f:FarmGameEngine.class.getDeclaredFields()){
   Class<?> t=f.getType();
   if(t.isArray()) {
    int dimensions=0;while(t.isArray()){dimensions++;t=t.getComponentType();}
    int[]size=new int[dimensions];Arrays.fill(size,128);
    Object value=Array.newInstance(t,size);f.set(null,value);
   } else if(t==Vector.class) f.set(null,new Vector());
   else if(t==String.class) f.set(null,"");
  }
  FarmGameEngine.aCw=null;FarmGameEngine.aCB=4;FarmGameEngine.asL=406;
  FarmGameEngine.aCY=3;FarmGameEngine.aCZ=0x1357;
  FarmGameEngine.iG(0);FarmGameEngine.iH(0);FarmGameEngine.iS(0);
  FarmGameEngine.avD=-1;FarmGameEngine.aVG=6;FarmGameEngine.aVH=20;FarmGameEngine.azZ=20;
  FarmGameEngine.aBa=FarmGameEngine.ih(5);
  FarmGameEngine.aBb=new bi(0);FarmGameEngine.aBc=new bk(0);FarmGameEngine.aBd=new bi(0);
  FarmGameEngine.aAW=new int[5][32];FarmGameEngine.aCX=new int[5][32];
 }
 static void seed()throws Exception {
  allocate();
  FarmGameEngine.currentTutorialStepIndex=17;FarmGameEngine.aBa=FarmGameEngine.ih(5);
  FarmGameEngine.aBb=new bi(38900);FarmGameEngine.aBc=new bk(5483210);FarmGameEngine.aBd=new bi(438);
  FarmGameEngine.aBH=true;FarmGameEngine.aBI=false;FarmGameEngine.asJ=true;
  FarmGameEngine.aKo=true;FarmGameEngine.auT=false;FarmGameEngine.auU=true;FarmGameEngine.awP=true;
  FarmGameEngine.aIJ=100;FarmGameEngine.axp=3123131123L;FarmGameEngine.aUm=4567890123L;
  FarmGameEngine.aJo=FarmGameEngine.w(987654L);FarmGameEngine.aJa=885566L;FarmGameEngine.bav=99887766L;
  FarmGameEngine.aBl=FarmGameEngine.ih(27);FarmGameEngine.aBn=FarmGameEngine.ih(39);
  for(int i=0;i<100;i++)FarmGameEngine.aII[i]=i%9;
  for(int row=0;row<5;row++)for(int i=0;i<32;i++)FarmGameEngine.aAW[row][i]=FarmGameEngine.ih((row+1)*100+i);
  for(int i=0;i<6;i++){
   FarmGameEngine.aUU[i]=i+10;FarmGameEngine.aVD[i]=i*3;FarmGameEngine.aVc[i]=FarmGameEngine.ih(i+2);
   FarmGameEngine.aVk[i]=FarmGameEngine.ih(i+4);FarmGameEngine.aVs[i]=FarmGameEngine.ih(i+7);
   FarmGameEngine.aIO[i]=1212121234L+i*137;
  }
  for(int i=0;i<10;i++){
   for(int col=0;col<6;col++){
    FarmGameEngine.azx[i][col]=col==5?FarmGameEngine.ih(i+col):i*50+col;
    FarmGameEngine.azy[i][col]=i*70+col;
   }
   for(int col=0;col<7;col++)FarmGameEngine.azz[i][col]=1616161616L+i*1000+col*59;
  }
  FarmGameEngine.aTl[0]="farm name";FarmGameEngine.aTl[1]="quest-state";
  FarmGameEngine.aRp="7";FarmGameEngine.aRq="player-fixture";FarmGameEngine.atH="accepted";
  FarmGameEngine.aWf=2;
  for(int i=0;i<2;i++){
   FarmGameEngine.aWg[i]="friend-"+i;FarmGameEngine.aWh[i]=i+5;FarmGameEngine.aWi[i]=i*4;
   FarmGameEngine.aWl[i]=FarmGameEngine.ih(i+9);FarmGameEngine.aWW[i]="recipe-"+i;
   FarmGameEngine.aWm[i]=12345+i;FarmGameEngine.aWn[i]=98765+i;FarmGameEngine.aWo[i]=76543+i;
  }
  FarmGameEngine.aWG.addElement("pending-friend");
  FarmGameEngine.aXq=2;
  for(int i=0;i<2;i++){FarmGameEngine.aXr[i]="reward-"+i;FarmGameEngine.aXs[i]=i+1;}
 }
 static void verify(){
  check(FarmGameEngine.currentTutorialStepIndex==17,"tutorial changed");
  check(FarmGameEngine.aBb.getValue()==38900,"XP changed");
  check(FarmGameEngine.aBc.getValue()==5483210L,"coins changed");
  check(FarmGameEngine.aBd.getValue()==438,"cash changed");
  check(FarmGameEngine.aIJ==100,"global actor counter changed");
  check(FarmGameEngine.aUm==4567890123L&&FarmGameEngine.axp==3123131123L,"timestamps changed");
  check(FarmGameEngine.x(FarmGameEngine.aJo)==987654L,"encoded timestamp changed");
  for(int row=0;row<5;row++)for(int i=0;i<32;i++)check(FarmGameEngine.ii(FarmGameEngine.aAW[row][i])==(row+1)*100+i,"inventory changed");
  for(int i=0;i<100;i++)check(FarmGameEngine.aII[i]==i%9,"global actor index changed");
  check(FarmGameEngine.aWG.size()==1&&FarmGameEngine.aWG.firstElement().equals("pending-friend"),"friend queue changed");
 }
 static void fail(byte[]data,SaveRestoreException.Code code)throws Exception {
  allocate();GameRuntime.record=data;byte[]before=data.clone();int writes=GameRuntime.writes;
  try{FarmGameEngine.ax(false);throw new AssertionError("malformed save accepted");}
  catch(SaveRestoreException expected){check(expected.getCode()==code,"wrong code "+expected.getCode()+" expected "+code);}
  check(GameRuntime.writes==writes&&Arrays.equals(before,GameRuntime.record),"restore overwrote source");
 }
 public static void main(String[]args)throws Exception {
  System.setProperty("HDIDFV",UUID_A);Path file=Paths.get(args[1]);
  if(args[0].equals("save")){
   seed();FarmGameEngine.rt();check(GameRuntime.record!=null,"encoder did not save");
   check(FarmGameEngine.aCy>4000,"full global record was not encoded");
   Files.write(file,GameRuntime.record);System.out.println("PASS full global save");return;
  }
  byte[]original=Files.readAllBytes(file);
  if(args[0].equals("bad")){
   fail(new byte[0],SaveRestoreException.Code.SAVE_DECODE_FAILED);
   fail(Arrays.copyOf(original,40),SaveRestoreException.Code.SAVE_DECODE_FAILED);
   fail(Arrays.copyOf(original,500),SaveRestoreException.Code.SAVE_DECODE_FAILED);
   byte[]future=original.clone();future[0]=5;fail(future,SaveRestoreException.Code.SAVE_VERSION_UNSUPPORTED);
   byte[]negative=original.clone();negative[0]=(byte)255;fail(negative,SaveRestoreException.Code.SAVE_VERSION_UNSUPPORTED);
   byte[]length=original.clone();Arrays.fill(length,1,5,(byte)255);fail(length,SaveRestoreException.Code.SAVE_DECODE_FAILED);
   allocate();GameRuntime.record=null;check(!FarmGameEngine.ax(false),"missing save accepted as existing");
   check(GameRuntime.writes==0,"missing-save read wrote data");
   System.out.println("PASS malformed global records refused; original retained");return;
  }
  allocate();System.setProperty("HDIDFV",args[2]);GameRuntime.record=original.clone();
  com.gameloft.android.wrapper.au.forbidden=true;
  check(FarmGameEngine.ax(false),"local record rejected");verify();
  check(GameRuntime.reads==1&&GameRuntime.writes==0,"restore performed extra reads/writes");
  check(Arrays.equals(original,GameRuntime.record),"restore mutated saved bytes");
  // Compare all serialized fields byte-for-byte with the same encoder binding
  // and deterministic clock; this catches fields omitted by handpicked asserts.
  System.setProperty("HDIDFV",UUID_A);FarmGameEngine.rt();
  if(!Arrays.equals(original,GameRuntime.record)) {
   int at=0;while(at<original.length&&original[at]==GameRuntime.record[at])at++;
   throw new AssertionError("full global serialization differs at "+at);
  }
  check(Arrays.equals(Files.readAllBytes(file),original),"original file overwritten");
  System.out.println("PASS full global cold process and UUID change");
 }
}
'''


class GlobalGameplayCodecTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.java = jdk_tool('java')
        cls.temporary = tempfile.TemporaryDirectory(prefix='greenfarm-full-global-codec-')
        cls.addClassCleanup(cls.temporary.cleanup)
        cls.directory = Path(cls.temporary.name)
        engine = (JAVA / 'FarmGameEngine.java').read_text()
        runtime = (JAVA / 'GameRuntime.java').read_text()
        methods = '\n'.join(extract_method(engine, signature) for signature in ENGINE_HELPERS)
        fields = []
        # Source declarations only; no initializer/global engine startup is run.
        for match in re.finditer(r'^    (?:(?:public|private|protected|final) )*static (\S+) (\w+)(?: = [^;\n]*)?;$', engine, re.M):
            typ, name = match.groups()
            if re.search(r'\b' + re.escape(name) + r'\b', methods):
                fields.append('static ' + typ + ' ' + name + ';')
        fixture = ('package ' + PACKAGE + ';\nimport java.util.*;\n' + PEERS
                   .replace('%FIELDS%', '\n'.join(fields))
                   .replace('%ENGINE%', methods)
                   .replace('%RUNTIME%', '\n'.join(extract_method(runtime, signature) for signature in RUNTIME_HELPERS)))
        (cls.directory / 'Fixture.java').write_text(fixture)
        (cls.directory / 'GlobalGameplayCodecCheck.java').write_text(RUNNER)
        sdk = cls.directory / 'com/gameloft/android/wrapper/au.java'
        sdk.parent.mkdir(parents=True)
        sdk.write_text('package com.gameloft.android.wrapper;public class au {public static boolean forbidden;public static String Os(){if(forbidden)throw new AssertionError("local codec requested SDK identity");return System.getProperty("HDIDFV");}}')
        compile_mixed([KOTLIN / (name + '.kt') for name in ('bi', 'bk', 'ce', 'cf', 'al', 'bo')],
                      [cls.directory / 'Fixture.java', cls.directory / 'GlobalGameplayCodecCheck.java', sdk,
                       JAVA / 'SaveRestoreException.java'], cls.directory / 'classes')

    def run_check(self, action, filename, identity='66666666-7777-8888-9999-000000000000'):
        result = subprocess.run([str(self.java), '-cp', runtime_classpath(self.directory / 'classes'),
                                 PACKAGE + '.GlobalGameplayCodecCheck', action, str(filename), identity],
                                capture_output=True, text=True, timeout=45)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn('PASS', result.stdout)

    def test_full_global_state_roundtrips_across_twenty_fresh_processes_with_changed_uuid(self):
        file = self.directory / 'global-save.bin'
        self.run_check('save', file)
        digest = hashlib.sha256(file.read_bytes()).digest()
        for attempt in range(20):
            with self.subTest(cold_process=attempt + 1):
                self.run_check('load', file, f'{attempt:08d}-7777-8888-9999-000000000000')
                self.assertEqual(hashlib.sha256(file.read_bytes()).digest(), digest)

    def test_complete_decoder_rejects_malformed_and_future_records_without_write(self):
        file = self.directory / 'malformed-seed.bin'
        self.run_check('save', file)
        self.run_check('bad', file)


if __name__ == '__main__':
    unittest.main()
