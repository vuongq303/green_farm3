"""Execute the real offline startup predicates without Android initialization.

The SDK peers are fixture-only. The engine's actual predicates, authentication
launcher and original farm/online startup choice are compiled unchanged. Actual
APK boot with the Internet permission removed is a separate emulator check.
"""
from pathlib import Path
import re
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, game_source, qualified_package
from test_aa_tile_renderer import extract_method


class OfflineStartupTest(unittest.TestCase):
    def test_existing_local_route_avoids_network_and_auth_without_changing_normal_mode(self):
        engine = game_source().read_text()
        federation = (core_source_directory() / 's.java').read_text()
        # Reuse the actual original branch in the language/startup continuation,
        # rather than restating the desired state transition in the fixture.
        startup = extract_method(engine, 'static boolean il(int i)')
        choice = re.search(r'if \(qN\(\)\) \{\s*fF\(60\);\s*\} else \{\s*fF\(18\);\s*\}', startup)
        self.assertIsNotNone(choice)
        methods = '\n'.join(extract_method(engine, signature) for signature in (
            'public static boolean qN()', 'public static boolean qO()', 'static void jn()'))
        source = r'''
package %PACKAGE%;
import java.util.concurrent.*;
class OfflineTestConfig {static boolean active; public static boolean enabled(){return active;}}
class w {static boolean XY;}
class am {
 static int created;String domain;
 am(boolean ignored){created++;}
 void bP(String value){domain=value;}
}
class s {
 static String Mz;static int Mw;static am My;
 %JP%
 %AK%
}
class cj implements Runnable {
 static CountDownLatch ran;
 public void run(){ran.countDown();}
}
public class FarmGameEngine {
 static boolean aps,mobile,wifi,throwIfNetworkRead;
 static int reads,nextState;
 static boolean bx(String name){
  if(throwIfNetworkRead)throw new SecurityException("No network permission in offline APK");
  reads++;return name.equals("isConnectedMobile")?mobile:wifi;
 }
 static void fF(int state){nextState=state;}
 static void startupChoice(){%CHOICE%}
 %METHODS%
 static void check(boolean value,String reason){if(!value)throw new AssertionError(reason);}
 public static void main(String[] args)throws Exception {
  int checks=0;
  for(boolean setting:new boolean[]{false,true}) {
   OfflineTestConfig.active=true;w.XY=setting;reads=0;throwIfNetworkRead=true;
   mobile=wifi=true;aps=false;nextState=-1;
   check(!qN(),"Offline mode must never enable federation");checks++;
   check(!qO()&&reads==0,"Offline startup must not query unavailable network permission");checks++;
   startupChoice();check(nextState==18,"Offline startup must take original local farm route");checks++;
   cj.ran=new CountDownLatch(1);jn();
   check(!cj.ran.await(60,TimeUnit.MILLISECONDS),"Offline boot spawned an authentication worker");checks++;
   s.Mz="old";s.Mw=9;s.My=new am(false);am old=s.My;int created=am.created;
   s.aK("eveListDataCenters");
   check(s.My==old&&am.created==created&&s.Mz.equals("old")&&s.Mw==9,
    "Offline boot initialized a datacenter request");checks++;

   OfflineTestConfig.active=false;throwIfNetworkRead=false;nextState=-1;
   check(qN()==setting,"Normal federation feature setting changed");checks++;
   startupChoice();check(nextState==(setting?60:18),"Normal boot route changed");checks++;
   for(boolean m:new boolean[]{false,true})for(boolean f:new boolean[]{false,true}){
    mobile=m;wifi=f;reads=0;
    check(qO()==(m||f),"Normal connectivity result changed");checks++;
    check(reads==(m?1:2),"Normal short-circuit connectivity lookup changed");checks++;
   }
   cj.ran=new CountDownLatch(1);aps=true;jn();
   check(!cj.ran.await(60,TimeUnit.MILLISECONDS),"Existing aps guard changed");checks++;
   aps=false;jn();check(cj.ran.await(2,TimeUnit.SECONDS),"Normal authentication launcher disabled");checks++;
   s.aK("eveListDataCenters");
   check(s.Mw==0&&s.Mz.equals("eveListDataCenters")&&s.My!=old&&s.My.domain.equals("Eve"),
    "Normal datacenter initialization changed");checks++;
  }
  System.out.println("PASS: "+checks+" actual startup/connectivity/auth route checks");
 }
}
'''
        source = (source.replace('%PACKAGE%', qualified_package()).replace('%METHODS%', methods)
                  .replace('%JP%', extract_method(federation, 'public static boolean jp()'))
                  .replace('%AK%', extract_method(federation, 'public static void aK(String str)'))
                  .replace('%CHOICE%', choice.group()))
        with tempfile.TemporaryDirectory(prefix='greenfarm-offline-startup-') as temp:
            directory = Path(temp)
            java = directory / 'FarmGameEngine.java'
            java.write_text(source)
            result = subprocess.run([str(jdk_tool('javac')), '-d', str(directory), str(java)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(directory),
                                     qualified_package() + '.FarmGameEngine'],
                                    capture_output=True, text=True, timeout=15)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS: 36 actual startup/connectivity/auth route checks', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
