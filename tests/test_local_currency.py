"""Run the production new-player initialization and currency arithmetic on the JVM.

Only unrelated level tables, save scheduling and build flag are fixture peers.
The actual numeric wrappers and original serialized currency codecs run here.
SQLite persistence itself is covered by the native SQLite harness.
"""
from pathlib import Path
import os
import re
import subprocess
import sys
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import qualified_package, core_source_directory, kotlin_source_directory

FIXTURE = r'''package PACKAGE;
class LocalDevelopmentMode {static boolean enabled;static boolean isEnabled(){return enabled;}}
class ProgressSaveController {static int critical;static void requestCriticalSave(){critical++;}}
class j {int[][]KW=new int[99][4];static j bY(int table){return new j();}}
class FarmGameEngine {
 static int aBe=COIN_CAP,aBg=CASH_CAP,aCY=0,aCZ=0;
 static int[]aZw={500,50},aBv,aBw,aBh,aCR;static short[]aBx;
 static bk aBc,aCP;static bi aBd,aCQ;static boolean aBI,aBH,hasUnsavedGameChanges;
 static int questEvents;
 static void hL(int i){}static void hM(int i){}static void ia(int i){}static void ib(int i){}
 static void ic(int i){}static void id(int i){}static void bP(int a,int b){}
 static void aa(int a,int b,int c){questEvents++;}
METHODS
}
public class LocalCurrencyChecks {
 static void check(boolean b,String message){if(!b)throw new AssertionError(message);}
 static void balances(int coins,int cash){FarmGameEngine.aBI=FarmGameEngine.aBH=false;FarmGameEngine.hJ(coins);FarmGameEngine.hK(cash);}
 public static void main(String[]args)throws Exception{
  LocalDevelopmentMode.enabled=false;FarmGameEngine.aY(true);
  check(FarmGameEngine.FA()==500&&FarmGameEngine.FB()==50,"normal defaults changed");
  LocalDevelopmentMode.enabled=true;FarmGameEngine.aY(true);
  check(FarmGameEngine.FA()==99999999&&FarmGameEngine.FB()==3000000,"local defaults not existing maximums");
  // Existing-save initialization must leave balances and their integrity peers alone.
  balances(12345,0);Object coin=FarmGameEngine.aBc,cash=FarmGameEngine.aBd;
  FarmGameEngine.aY(false);
  check(FarmGameEngine.FA()==12345&&FarmGameEngine.FB()==0&&FarmGameEngine.aBc==coin&&FarmGameEngine.aBd==cash,"existing balances overwritten");
  LocalDevelopmentMode.enabled=false;FarmGameEngine.aY(false);
  check(FarmGameEngine.FA()==12345&&FarmGameEngine.FB()==0,"normal existing balances overwritten");
  // The save codec writes obfuscated long coins and int cash, rather than doubles.
  for(int shift:new int[]{0,1,4,9}){
   FarmGameEngine.aCY=shift;FarmGameEngine.aCZ=-1413816336;
   balances(99999999,3000000);
   java.io.ByteArrayOutputStream bytes=new java.io.ByteArrayOutputStream();
   java.io.DataOutputStream out=new java.io.DataOutputStream(bytes);
   out.writeLong(FarmGameEngine.w(FarmGameEngine.FA()));out.writeInt(FarmGameEngine.ih(FarmGameEngine.FB()));out.close();
   balances(1,2);
   java.io.DataInputStream in=new java.io.DataInputStream(new java.io.ByteArrayInputStream(bytes.toByteArray()));
   FarmGameEngine.hJ((int)FarmGameEngine.x(in.readLong()));FarmGameEngine.hK(FarmGameEngine.ii(in.readInt()));
   check(FarmGameEngine.FA()==99999999&&FarmGameEngine.FB()==3000000,"currency codec lost precision");
  }
  FarmGameEngine.aCY=0;FarmGameEngine.aCZ=0;
  balances(99999999,3000000);FarmGameEngine.hS(Integer.MAX_VALUE);FarmGameEngine.hT(Integer.MAX_VALUE);
  check(FarmGameEngine.FA()==99999999&&FarmGameEngine.FB()==3000000,"positive overflow escaped cap");
  balances(0,0);FarmGameEngine.hS(Integer.MIN_VALUE);FarmGameEngine.hT(Integer.MIN_VALUE);
  check(FarmGameEngine.FA()==-99999999&&FarmGameEngine.FB()==-3000000,"negative overflow escaped debt floor");
  balances(30,40);FarmGameEngine.hS(20);FarmGameEngine.hT(10);FarmGameEngine.hS(-15);FarmGameEngine.hT(-25);
  check(FarmGameEngine.FA()==35&&FarmGameEngine.FB()==25,"ordinary economy changed");
  check(ProgressSaveController.critical==4&&FarmGameEngine.questEvents==2,"save/quest side effects changed");
  check(FarmGameEngine.hasUnsavedGameChanges,"currency mutation did not become dirty");
  System.out.println("PASS opt-in currency caps, existing/zero balances, real wrapper+save codec roundtrip, overflow clamps and economy side effects");
 }
}
'''


class LocalCurrencyTest(unittest.TestCase):
    def test_defaults_codec_and_safe_arithmetic(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25'))
        java = home / 'bin/java'
        if not java.is_file():
            self.skipTest('Requires installed JDK')
        source = (core_source_directory() / 'FarmGameEngine.java').read_text()
        signatures = (
            'static void aY(boolean z)', 'static void hJ(int i)', 'static void hK(int i)',
            'static int FA()', 'static int FB()', 'static void hS(int i)', 'static void hT(int i)',
            'static bk v(long j)', 'static bi ij(int i)', 'static int a(bi biVar)',
            'static long a(bk bkVar)', 'static int ih(int i)', 'static int ii(int i)',
            'static long w(long j)', 'static long x(long j)',
        )
        fixture = FIXTURE.replace('PACKAGE', qualified_package()).replace('METHODS', '\n'.join(extract_method(source, s) for s in signatures))
        for token, field in [('COIN_CAP', 'aBe'), ('CASH_CAP', 'aBg')]:
            fixture = fixture.replace(token, re.search(rf'\b{field} = (\d+);', source).group(1))
        with tempfile.TemporaryDirectory(prefix='local-currency-') as temporary:
            root = Path(temporary)
            peer = root / 'LocalCurrencyChecks.java'
            peer.write_text(fixture)
            output = root / 'classes'
            kotlin = kotlin_source_directory()
            compile_mixed([kotlin / (name + '.kt') for name in ('bi', 'bk', 'ce', 'cf', 'bo', 'al')], [peer], output)
            result = subprocess.run([str(java), '-cp', runtime_classpath(output), qualified_package()+'.LocalCurrencyChecks'],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            self.assertIn('PASS opt-in currency caps', result.stdout)


if __name__ == '__main__':
    unittest.main()
