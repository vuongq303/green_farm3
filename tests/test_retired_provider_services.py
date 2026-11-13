"""Execute provider retirement at the actual Kotlin/JVM and packed GUI boundaries.

This intentionally does not compare removed ad rewards to the original SDK's
behavior. Gameplay/model bytecode comparisons remain in their separate suites.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import game_source, kotlin_source_directory, qualified_package
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_aa_tile_renderer import extract_method
import test_more_games_removal as more_games_fixtures


class RetiredProviderServicesTest(unittest.TestCase):
    def test_packed_gui_cannot_render_or_reactivate_provider_buttons(self):
        fixture = more_games_fixtures.MoreGamesRemovalTest(methodName='test_actual_gui_methods_remove_rendering_input_and_hitboxes')
        fixture.setUp()
        source = fixture.fixture()
        source = source.replace('[50][160]', '[50][400]').replace('n<160', 'n<400')
        source = source.replace('int[][] removed={{1,34},{1,38},{27,22},{27,23}};',
                                'int[][] removed={{15,32},{15,33},{15,40},{9,4},{9,8},{29,6},{1,81},{8,318},{8,319}};')
        source = source.replace('int[][] retained={{1,4},{1,5},{1,7},{1,46},{1,82},{27,8},{27,10},{27,12},{26,22}};',
                                'int[][] retained={{1,4},{1,5},{1,7},{1,46},{8,320},{9,3},{15,30}};')
        source = source.replace('gK(1);gK(27);', 'gK(1);gK(8);gK(9);gK(15);gK(29);')
        self.assertIn('removed={{15,32}', source)
        result = fixture.run_fixture(source)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        print('Retired Facebook/video-ad controls: ' + result.stdout.strip())

    def test_callbacks_never_initialize_native_ads_or_grant_currency(self):
        original = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        android = Path(os.environ.get('ANDROID_HOME', '/workspace/tooling/android-sdk')) / 'platforms/android-28/android.jar'
        if not original.is_file() or not android.is_file():
            self.skipTest('Requires original SDK compile definitions and Android28 API')
        engine = game_source().read_text()
        methods = '\n'.join(extract_method(engine, signature) for signature in (
            'static void Jd()',
            'public static void a(int i, int i2, String str, String str2, int i3, int i4)',
            'public static void a(int i, int i2, boolean z, String str, String str2, String str3, int i3, int i4)'))
        source = r'''
package %PACKAGE%;
class GloftGF2F {static FarmGameEngine acw;}
class s {static String js(){throw new AssertionError("Ad init read guest credentials");}}
class bz {static bz om(){throw new AssertionError("Ad reward accessed account profile");}void on(){}}
class Tracker {void OnResume(){throw new AssertionError("Retired callback resumed tracking");}}
class bu {static int clears;static void clear(){clears++;}}
public class FarmGameEngine {
 public static int aTL,aYU,auC;
 static int coins=1900,cash=50;
 static boolean aPg,aPk,bbx,apX,aYN,aRc;
 static short[][][]atc=new short[30][12][24];
 static Tracker baf;
 public String dr(int language){throw new AssertionError("Ad initialization read game language");}
 static void forbidden(){throw new AssertionError("Retired provider mutated game state");}
 static void hT(int amount){cash+=amount;}
 static void ro(){forbidden();}static void bq(boolean show){forbidden();}
 static void kp(int modal){forbidden();}static void qB(){forbidden();}
 static boolean Lc(){throw new AssertionError("Reward handler reached original authorization branch");}
 static int currentEngineState(){throw new AssertionError("Retired ad-view callback reached game state");}
 static void Mj(){forbidden();}static void fE(int state){forbidden();}static void kX(int state){forbidden();}
 %METHODS%
 static void check(boolean value,String reason){if(!value)throw new AssertionError(reason);}
 public static void main(String[]args){
  check(!ServiceAvailability.facebookLoginEnabled(),"Facebook policy enabled");
  check(!ServiceAvailability.advertisementsEnabled(),"Advertising policy enabled");
  for(int farm:new int[]{0,1,10,11})for(int state=0;state<66;state++){
   int expected=state==47?(farm==0?18:37):state;
   check(ServiceAvailability.startupDestination(state,farm)==expected,"Unrelated startup state changed");
   check(ServiceAvailability.isRetiredAdScreen(state)==(state==61||state==62||state==63||state==65),"Wrong retired screen");
  }
  b bridge=new b();
  // The retained Facebook ABI has no provider import, dispatch or fabricated
  // authenticated state. Null/empty arguments must remain safe after SDK pruning.
  bt.I(null,null);bt.ar(64,64);bt.eL(16);bt.nT();bt.nU();bt.nV();
  bt.a((ai)null,(ai[])null,null,null);bt.a((ai[])null,(ai[])null,null,null);
  bt.b(null,null,null,null,null,null,null,null,null);bt.g(null,null,null,null);
  new cm().run();
  check(!bt.nW()&&!bt.nX()&&!bt.nY(),"Removed Facebook ABI reported an authenticated state");
  check(bt.getPassword().equals("")&&bt.nZ().getId().equals("")&&bt.nZ().getName().equals(""),"Removed Facebook ABI fabricated identity");
  check(bt.oa()==0&&bt.ob()==null&&bt.eM(-1)==null&&bt.cD(null)==null,"Removed Facebook ABI fabricated friends or image data");
  check(bu.clears==0,"Provider retirement cleared stored data implicitly");
  // If these real Kotlin entrypoints regress to the SDK's native methods, the
  // host JVM throws UnsatisfiedLinkError. No native mock reports success.
  bridge.CreateSingleAdsManagerInstance();bridge.Init();
  b.ar("enter_section_iap_shop");b.showIncentivized("enter_section_video_bubble");
  check(!b.isAdOnScreen(3),"Retired ad reported onscreen");check(!b.Jn,"Ad SDK marked initialized");
  int checks=0;
  for(String placement:new String[]{"enter_section_iap_shop","enter_section_video_bubble",
    "enter_section_video_lucky_wheel","enter_section_video_daily_stamp","unknown",null}){
   for(int n=0;n<1000;n++){
    b.JO=b.JP=b.JQ=b.JR=0;b.JK=b.JL=b.JM=b.JN=false;b.Kb=false;
    b.JX=b.JY=b.JZ=b.Ka=false;
    bridge.OnAdError(3,placement);bridge.OnAdFinished(3,0,placement,null,0,0);bridge.e(placement,n%5);
    a(n,0,(n&1)==0,null,placement,null,0,0);a(n,0,placement,null,0,0);Jd();
    check(coins==1900&&cash==50&&!aPg&&!aYN&&!aRc,"Retired callback granted or changed progress");
    check(!b.JX&&!b.JY&&!b.JZ&&!b.Ka&&!b.Jn&&!b.JK&&!b.JL&&!b.JM&&!b.JN&&!b.Kb,
      "Retired callback armed reward or availability state");
    checks++;
   }
  }
  System.out.println("PASS: "+checks+" real callback traces; no SDK init, reward, credential read or unrelated startup redirect");
 }
}
'''.replace('%PACKAGE%', qualified_package()).replace('%METHODS%', methods)
        with tempfile.TemporaryDirectory(prefix='greenfarm-retired-providers-') as temp:
            directory = Path(temp)
            peer = directory / 'FarmGameEngine.java'
            peer.write_text(source)
            profile = directory / 'ai.java'
            profile.write_text('package ' + qualified_package() + '; public class ai {public String getId(){return "";}public String getName(){return "";}}')
            image = directory / 'ay.java'
            image.write_text('package ' + qualified_package() + '; public class ay {}')
            compile_mixed([kotlin_source_directory() / 'ServiceAvailability.kt',
                           kotlin_source_directory() / 'b.kt', kotlin_source_directory() / 'bt.kt',
                           kotlin_source_directory() / 'cm.kt'], [peer, profile, image], directory,
                          classpath=(original, android))
            for emitted in ('bt.class', 'bt$Companion.class', 'cm.class', 'b.class', 'b$Companion.class'):
                bytecode = (directory / qualified_package().replace('.', '/') / emitted).read_bytes()
                self.assertNotIn(b'com/gameloft/android2d/socialnetwork/l', bytecode)
                self.assertNotIn(b'com/facebook/', bytecode)
                self.assertNotIn(b'com/gameloft/adsmanager/', bytecode)
                self.assertNotIn(b'com/gameloft/android2d/adsmanager/', bytecode)
                self.assertNotIn(b'com/gameloft/android2d/c/a', bytecode)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(directory, original, android),
                                     qualified_package() + '.FarmGameEngine'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS: 6000 real callback traces', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
