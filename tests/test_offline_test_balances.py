"""Exercise the one-shot grant with the game's protected money/level setters.

Storage is a transactional fixture here; Android SQLite durability is verified
separately by the existing APK probes. No test writes a real player database.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_aa_tile_renderer import extract_method

PACKAGE = 'com.quanhoang.greenfarm3.S800x480'
CORE = ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/')
KOTLIN = ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/')

FIXTURE = r'''
package com.quanhoang.greenfarm3.S800x480;
import java.util.*;
import java.nio.ByteBuffer;
import android.app.Activity;
import javax.microedition.midlet.MIDlet;

class OfflineTestConfig {
 static boolean enabled;
 public static boolean enabled(){return enabled;}
}
class GameSaveStore {
 static Map<String,byte[]> durable=new HashMap<>(),capture;
 static int reads,writes;
 public static byte[] read(String name){reads++;byte[]data=durable.get(name);return data==null?null:data.clone();}
 static void beginCapture(){if(capture!=null)throw new AssertionError("nested");capture=new HashMap<>();}
 public static void write(String name,byte[]data,int start,int length){
  if(capture==null)throw new AssertionError("marker outside gameplay capture");
  writes++;capture.put(name,Arrays.copyOfRange(data,start,start+length));
 }
 static Map<String,byte[]> finishCapture(){Map<String,byte[]>out=capture;capture=null;return out;}
 static void apply(Map<String,byte[]>out){
  if(!out.containsKey("save_gameplay")||!out.containsKey("offline_test_balances_v1"))throw new AssertionError("partial grant");
  Map<String,byte[]>next=new HashMap<>(durable);next.putAll(out);durable=next;
 }
}
public class FarmGameEngine {
 static bk aBc,aCP;static bi aBd,aCQ,aBb,aCO;
 static boolean aBI,aBH,hasUnsavedGameChanges,aCc;
 static int aBe=99_999_999,aBg=3_000_000,aZB,objectives;
 static int aBa,aCN,aBl,aCS,aBn,aCT,aBp,aCU,aBs,aCV,aCY=5,aCZ=0x13572468;
 static int[]aBv=new int[99];
 static int inventory=12,tutorial=23;
 static void aa(int first,int amount,int kind){objectives++;}
 static void DY(){throw new AssertionError("unexpected level callback");}
 static void hW(int amount){throw new AssertionError("unexpected level rewards");}
 METHODS

 static void check(boolean value,String message){if(!value)throw new AssertionError(message);}
 static void resetMoney(int coins,int cash){
  aBI=false;aBH=false;hJ(coins);hK(cash);hL(1);hM(7);ia(10);ib(10);ic(10);id(1);
  hasUnsavedGameChanges=false;aBg=3_000_000;
 }
 static Map<String,byte[]> snapshot(){
  GameSaveStore.beginCapture();
  GameSaveStore.write("save_gameplay",ByteBuffer.allocate(32).putInt(FA()).putInt(FB())
    .putInt(FC()).putInt(FD()).putInt(FI()).putInt(FJ()).putInt(FK()).putInt(FL()).array(),0,32);
  OfflineTestBalances.captureGrantMarker();
  return GameSaveStore.finishCapture();
 }
 public static void main(String[]args){
  MIDlet.cnA=new Activity("com.quanhoang.greenfarm3.offlinetest");
  resetMoney(1900,50);
  OfflineTestConfig.enabled=false;
  OfflineTestBalances.onWorldReady();
  check(FA()==1900&&FB()==50&&aBg==3_000_000&&GameSaveStore.reads==0,"normal build unchanged");
  check(FC()==1&&FD()==7&&FI()==10&&FJ()==10&&FK()==10&&FL()==1,"normal level/XP/limits unchanged");
  OfflineTestConfig.enabled=true;MIDlet.cnA=new Activity("com.quanhoang.greenfarm3");
  OfflineTestBalances.onWorldReady();
  check(FA()==1900&&FB()==50&&GameSaveStore.reads==0,"real profile guarded");
  MIDlet.cnA=null;OfflineTestBalances.onWorldReady();
  check(FA()==1900&&FB()==50,"missing context unchanged");
  MIDlet.cnA=new Activity("com.quanhoang.greenfarm3.offlinetest");
  aZB=1;OfflineTestBalances.onWorldReady();
  check(FA()==1900&&FB()==50&&GameSaveStore.reads==0,"visiting farm not granted");aZB=0;
  aBI=true;aBH=true;
  OfflineTestBalances.onWorldReady();
  check(FA()==9_999_999&&FB()==9_999_999,"initial exact grant");
  check(!aBI&&!aBH&&aBc==aCP&&aBd==aCQ,"protected values/shadows/sign flags");
  check(FC()==50&&aBa==aCN&&FD()==7,"real protected level max, XP preserved");
  check(FI()==60&&FJ()==25&&FK()==40&&FL()==20,"real max-level capacity limits");
  check(aBl==aCS&&aBn==aCT&&aBp==aCU&&aBs==aCV,"capacity shadows match");
  check(aBe==99_999_999&&aBg==9_999_999,"test cap only sufficient");
  check(hasUnsavedGameChanges&&objectives==0,"dirty without reward replay");
  check(GameSaveStore.writes==0&&GameSaveStore.durable.isEmpty(),"no marker commit before gameplay");
  hS(-100);hT(-250);
  check(FA()==9_999_899&&FB()==9_999_749,"normal deductions work above original cash cap");
  Map<String,byte[]>failed=snapshot(); // Failed worker: nothing becomes durable.
  check(GameSaveStore.durable.isEmpty(),"failed write did not commit marker");
  resetMoney(1900,50);OfflineTestBalances.onWorldReady();
  check(FA()==9_999_999&&FB()==9_999_999,"restart before commit repeats absolute seed");
  hS(-100);hT(-250);
  GameSaveStore.apply(snapshot());
  byte[]saved=GameSaveStore.durable.get("save_gameplay");
  resetMoney(ByteBuffer.wrap(saved).getInt(),ByteBuffer.wrap(saved).getInt(4));
  ByteBuffer restored=ByteBuffer.wrap(saved);restored.position(8);
  hL(restored.getInt());hM(restored.getInt());ia(restored.getInt());ib(restored.getInt());ic(restored.getInt());id(restored.getInt());
  OfflineTestBalances.onWorldReady();
  check(FA()==9_999_899&&FB()==9_999_749,"committed grant never refills on reopen");
  check(FC()==50&&FD()==7&&FI()==60&&FJ()==25&&FK()==40&&FL()==20,"level and capacities persisted");
  check(aBg==9_999_999,"cash cap restored every test session");
  hT(-100);check(FB()==9_999_649,"post-reopen spend does not clip to3m");
  hX(100);check(FC()==50&&FD()==7,"max-level XP path exits before reward/index logic");
  check(hY(FC())==1,"level50 threshold indexes within original table");
  check(inventory==12&&tutorial==23&&objectives==0,"inventory/tutorial/quests untouched");
  GameSaveStore.durable.put("offline_test_balances_v1",new byte[]{0});
  try{OfflineTestBalances.onWorldReady();throw new AssertionError("corrupt marker accepted");}
  catch(IllegalStateException expected){}
  check(FA()==9_999_899&&FB()==9_999_649,"invalid marker does not replace money");
  System.out.println("PASS offline grant: normal/profile/visit guards, protected money/level/limits, atomic marker, rollback/retry, spend/reopen, unchanged inventory/XP/tutorial");
 }
}
'''


class OfflineTestBalancesTest(unittest.TestCase):
    def test_actual_protected_currency_grant_and_atomic_once_marker(self):
        code = (CORE / 'FarmGameEngine.java').read_text()
        methods = '\n'.join(extract_method(code, signature) for signature in (
            'static int FA()', 'static int FB()', 'static void hJ(int i)',
            'static void hK(int i)', 'static void hS(int i)', 'static void hT(int i)',
            'static int FC()', 'static int FD()', 'static void hL(int i)', 'static void hM(int i)',
            'static int FI()', 'static int FJ()', 'static int FK()', 'static int FL()',
            'static void ia(int i)', 'static void ib(int i)', 'static void ic(int i)', 'static void id(int i)',
            'static int ih(int i)', 'static int ii(int i)', 'static void hX(int i)', 'static int hY(int i)',
            'static int a(bi biVar)', 'static long a(bk bkVar)',
            'static bi ij(int i)', 'static bk v(long j)'))
        with tempfile.TemporaryDirectory() as directory:
            directory = Path(directory)
            peer = directory / 'FarmGameEngine.java'
            peer.write_text(FIXTURE.replace('METHODS', methods))
            activity = directory / 'Activity.java'
            activity.write_text('package android.app;public class Activity {private String name;public Activity(String name){this.name=name;}public String getPackageName(){return name;}}')
            midlet = directory / 'MIDlet.java'
            midlet.write_text('package javax.microedition.midlet;public class MIDlet {public static android.app.Activity cnA;}')
            classes = directory / 'classes'
            compile_mixed([KOTLIN / f'{name}.kt' for name in (
                'OfflineTestBalances', 'bk', 'bi', 'cf', 'ce', 'al', 'bo')],
                [peer, activity, midlet], classes)
            java = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25')) / 'bin/java'
            result = subprocess.run([str(java), '-cp', runtime_classpath(classes), PACKAGE + '.FarmGameEngine'],
                                    text=True, capture_output=True, timeout=30)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            self.assertIn('PASS offline grant', result.stdout)

    def test_controller_hooks_keep_marker_inside_gameplay_snapshot(self):
        source = (KOTLIN / 'ProgressSaveController.kt').read_text()
        capture = source[source.index('private fun capture('):source.index('private fun failLanguageCheckpoint')]
        self.assertLess(capture.index('GameSaveStore.beginCapture'), capture.index('OfflineTestBalances.captureGrantMarker()'))
        self.assertLess(capture.index('OfflineTestBalances.captureGrantMarker()'), capture.index('GameSaveStore.finishCapture()'))
        world = source[source.index('fun enterWorld()'):source.index('fun allowLoadStep()')]
        self.assertLess(world.index('GameSaveStore.finishLoading()'), world.index('OfflineTestBalances.onWorldReady()'))
        self.assertLess(world.index('OfflineTestBalances.onWorldReady()'), world.index('worldReady = true'))


if __name__ == '__main__':
    unittest.main()
