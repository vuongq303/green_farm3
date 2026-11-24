"""Exercise offline consent, continuation and telemetry guards with JVM peers."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from android_build_tools import jdk_tool
from test_aa_tile_renderer import extract_method

PACKAGE = 'com.quanhoang.greenfarm3.S800x480'
SHELL = '''package %PACKAGE%;
import java.util.*;
public class FarmGameEngine {
 static int aTL,aTP,aTQ,aTR,aTO,auE,auC,aZB,ash,asa,asi,aDi;
 static boolean bbi,bbj,bbk,bbl,bbm,bbr,aUb,aTU,aTS,aTK,aKr,aTY,asN,asr,asl,aDf,aMi,aDc,aKo,aTT;
 static long aDh;
 static String baQ,bbz,bby;
 static int[] aTW=new int[2];
 static com.gameloft.android2d.d.b baf;
 static int savedAge,savedGender;
 static boolean savedConsent, connected;
 static List<String> calls=new ArrayList<>();
 static void gX(int v){calls.add("input");}
 static boolean bg(int layer,int id){return id==20;}
 static void kp(int v){calls.add("popup:"+v);}
 static void E(int state,boolean v){calls.add("network-failed");}
 static int currentEngineState(){return 44;}
 static boolean bx(String command){return connected&&command.equals("isConnectedWifi");}
 static String MF(){throw new AssertionError("country request in offline mode");}
 static void ro(){savedAge=aTO;savedGender=auC;savedConsent=aUb;calls.add("header");}
 static void qP(){calls.add("gender-save");}
 static void fF(int v){calls.add("load:"+v);}
 static void fE(int v){calls.add("push:"+v);}
 static void fG(int v){calls.add("state:"+v);}
 static void qH(){calls.add("reset-online");}
 static void a(int event,String[] payload){throw new AssertionError("telemetry dispatched "+event);}
 %METHODS%

 static void check(boolean condition,String message){
  if(!condition)throw new AssertionError(message+"; calls="+calls);
 }

 static void newPlayer(int inputAge){
  aTL=inputAge;aUb=false;aTO=-1;aTU=false;asN=false;aTK=false;aTY=true;
  savedAge=-1;savedGender=-1;savedConsent=false;
  calls.clear();
 }

 public static void main(String[] ignored){
  LocalDevelopmentConfig.OFFLINE_PLAY=true;
  check(!LocalDevelopmentMode.isEnabled()&&LocalDevelopmentMode.skipLegacyBackend(),
        "offline play must skip legacy services without development bonuses");
  check(!canOpenAgePolicyLink(),"legal links require a network connection");
  connected=true;
  check(canOpenAgePolicyLink()&&!qO(),"legal links must remain available without enabling Janus");
  connected=false;
  for(int age:new int[]{0,-1}){
   newPlayer(age);
   check(offlineStartupState()==60,"fresh startup must show consent");
   Dt();
   check(!aUb&&calls.contains("popup:777"),"invalid age accepted "+age);
   check(!calls.contains("header")&&!calls.contains("gender-save")&&!calls.contains("load:18"),
         "invalid age triggered consent persistence or world loading");
   check(offlineStartupState()==60,"rejected consent must leave the age gate active");
  }
  for(int age:new int[]{15,16,30,120}){
   newPlayer(age);
   Dt();
   check(aUb&&!aTY&&calls.contains("load:18")&&!calls.contains("network-failed"),
         "local play must continue even without a network or tracker");
   check(aTK==(age>=16),"local policy threshold changed for age "+age);
   check(aTO==age&&auC==1&&calls.contains("gender-save"),"existing age and gender updates were lost");
   check(savedConsent&&savedAge==age&&savedGender==1,"saved header missing accepted preferences");
   for(int reopen=0;reopen<2;reopen++){
    aUb=false;aTO=-1;
    aUb=savedConsent;aTO=savedAge;
    check(offlineStartupState()==18,"reopen with saved consent must load the world");
   }
   aTO=-1;
   check(offlineStartupState()==60,"missing saved age must show consent again");
  }
  baf=new com.gameloft.android2d.d.b();
  cb(30,1);
  check(baf.calls==0,"offline mode sent profile telemetry");
  baf=null;

  LocalDevelopmentConfig.OFFLINE_PLAY=false;
  check(!LocalDevelopmentMode.skipLegacyBackend(),"legacy backend mode was disabled");
  cb(30,1);
  baf=new com.gameloft.android2d.d.b();
  cb(30,1);
  check(baf.calls==1,"legacy mode stopped sending profile telemetry");
  baf=null;
  newPlayer(30);
  Dt();
  check(!aUb&&calls.contains("network-failed")&&!calls.contains("load:18"),
        "normal backend gate changed");
  System.out.println("PASS local Accept and saved startup route");
 }
}
class bt {static boolean nY(){return false;}static void nU(){}}
class s {static boolean jp(){return true;}}
class cn {}
class w {static boolean Vp=true;}
class GameRuntime {static int Qx;static void be(String v){}}
'''

class LocalStartupTest(unittest.TestCase):
    def test_accept_uses_existing_local_loading_and_never_calls_backend(self):
        text = (ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/') / 'FarmGameEngine.java').read_text()
        methods = '\n'.join(extract_method(text, signature) for signature in (
            'static void Dt()',
            'public static void cb(int i, int i2)',
            'public static void W(int i, int i2, int i3)',
            'public static boolean qN()',
            'public static boolean qO()',
            'private static boolean canOpenAgePolicyLink()',
            'private static boolean hasNetworkConnection()',
            'private static int offlineStartupState()',
        ))
        with tempfile.TemporaryDirectory(prefix='greenfarm-local-startup-') as temporary:
            root = Path(temporary)
            sources = root / 'source'
            target = sources / PACKAGE.replace('.', '/')
            target.mkdir(parents=True)
            engine = target / 'FarmGameEngine.java'
            engine.write_text(SHELL.replace('%PACKAGE%', PACKAGE).replace('%METHODS%', methods))
            config = target / 'LocalDevelopmentConfig.java'
            config.write_text(f'package {PACKAGE}; public class LocalDevelopmentConfig {{ '
                              'public static boolean ENABLED, OFFLINE_PLAY, ONLINE_ACCOUNTS; }')
            wrapper = sources / 'com/gameloft/android/wrapper/au.java'
            wrapper.parent.mkdir(parents=True)
            wrapper.write_text('package com.gameloft.android.wrapper; public class au { public static void mA(int v){} public static void el(String v){} public static void em(String v){} }')
            sdk = sources / 'com/gameloft/a/b'
            sdk.mkdir(parents=True)
            for name, source in {
                'a': 'public static boolean fZ(){throw new AssertionError("SDK readiness requested");}',
                'i': 'public static i Wv(){throw new AssertionError("privacy service requested");} public boolean hv(String v){return false;} public m Ww(){return null;} public void a(int age,String country,Object callback){throw new AssertionError("backend request");}',
                'm': 'public String bxA; public int bxy;',
            }.items():
                (sdk / (name+'.java')).write_text('package com.gameloft.a.b; public class '+name+' {'+source+'}')
            tracker = sources / 'com/gameloft/android2d/d/b.java'
            tracker.parent.mkdir(parents=True)
            tracker.write_text('package com.gameloft.android2d.d; public class b { public int calls; '
                               'public void OnResume(){throw new AssertionError("tracker resumed");} '
                               'public void b(int event,String[] payload){calls++;} }')
            output = root / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/') / 'LocalDevelopmentMode.kt'], list(sources.rglob('*.java')), output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), PACKAGE+'.FarmGameEngine'], capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS local Accept', result.stdout)

if __name__ == '__main__': unittest.main()
