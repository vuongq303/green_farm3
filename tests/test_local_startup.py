"""Run the production Accept continuation with unavailable backend boundary peers."""
from pathlib import Path
import os
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
 static boolean bbi,bbj,bbk,bbl,bbm,bbr,aUb,aTU,aTS,aTK,aKr,asN,asr,asl,aDf,aMi,aDc,aKo,aTT;
 static long aDh;
 static String baQ,bbz,bby;
 static int[] aTW=new int[2];
 static List<String> calls=new ArrayList<>();
 static void gX(int v){calls.add("input");}
 static boolean bg(int layer,int id){return id==20;}
 static void kp(int v){calls.add("popup:"+v);}
 static void E(int state,boolean v){calls.add("network-failed");}
 static int currentEngineState(){return 44;}
 static boolean qO(){return false;}
 static boolean qN(){return !LocalDevelopmentMode.isEnabled();}
 static String MF(){throw new AssertionError("country request in offline mode");}
 static void ro(){calls.add("header");}
 static void qP(){calls.add("gender-save");}
 static void cb(int age,int gender){calls.add("profile:"+age+":"+gender);}
 static void fF(int v){calls.add("load:"+v);}
 static void fE(int v){calls.add("push:"+v);}
 static void fG(int v){calls.add("state:"+v);}
 static void qH(){calls.add("reset-online");}
 static void W(int a,int b,int c){calls.add("age-result:"+c);}
 %METHOD%
 public static void main(String[] ignored){
  LocalDevelopmentConfig.ENABLED=true;
  for(int age:new int[]{0,-1,15,16,30,120}){
   aTL=age;aUb=false;aTU=false;asN=false;aTK=false;calls.clear();
   Dt();
   if(age<=0){
    if(aUb || !calls.contains("popup:777"))throw new AssertionError("invalid age accepted "+age+calls);
   } else {
    if(!aUb || !calls.contains("load:18") || calls.contains("network-failed"))throw new AssertionError("offline continuation "+calls);
    if(aTK!=(age>=16))throw new AssertionError("local policy threshold "+age);
    if(aTO!=age || auC!=1 || !calls.contains("gender-save") || !calls.contains("profile:"+age+":1"))throw new AssertionError("local player preferences "+calls);
   }
  }
  LocalDevelopmentConfig.ENABLED=false;aTL=30;aUb=false;calls.clear();Dt();
  if(aUb || !calls.contains("network-failed") || calls.contains("load:18"))throw new AssertionError("normal backend gate changed "+calls);
  System.out.println("PASS local Accept: invalid age, local16 policy, existing gender/profile save, offline loading, normal backend gate");
 }
}
class bt {static boolean nY(){return false;}static void nU(){}}
class cn {}
class w {static boolean Vp=true;}
class GameRuntime {static int Qx;static void be(String v){}}
'''

class LocalStartupTest(unittest.TestCase):
    def test_accept_uses_existing_local_loading_and_never_calls_backend(self):
        text = (ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/') / 'FarmGameEngine.java').read_text()
        method = extract_method(text, 'static void Dt()')
        with tempfile.TemporaryDirectory(prefix='greenfarm-local-startup-') as temporary:
            root = Path(temporary)
            sources = root / 'source'
            target = sources / PACKAGE.replace('.', '/')
            target.mkdir(parents=True)
            engine = target / 'FarmGameEngine.java'
            engine.write_text(SHELL.replace('%PACKAGE%', PACKAGE).replace('%METHOD%', method))
            config = target / 'LocalDevelopmentConfig.java'
            config.write_text('package '+PACKAGE+'; public class LocalDevelopmentConfig { public static boolean ENABLED; public static boolean ONLINE_ACCOUNTS=false; }')
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
            output = root / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/') / 'LocalDevelopmentMode.kt'], list(sources.rglob('*.java')), output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), PACKAGE+'.FarmGameEngine'], capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS local Accept', result.stdout)

if __name__ == '__main__': unittest.main()
