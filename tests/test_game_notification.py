"""Run actual callback popup code and extracted GUI4 setup/input on the JVM."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest
from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, kotlin_source_directory, qualified_package
from kotlin_test_tools import compile_mixed, runtime_classpath


class GameNotificationTest(unittest.TestCase):
    def test_native_component_decisions_back_and_compensation_barrier(self):
        engine = (core_source_directory() / 'FarmGameEngine.java').read_text()
        setup = extract_method(engine, 'static void showGameNotification(String title, String message)')
        update = extract_method(engine, 'static boolean updateGameNotification(int phase)')
        push = extract_method(engine, 'static void fE(int i)')
        source = '''package PACKAGE;
class GameRuntime {static long Tn,To;static int key=-1;static boolean back;static int jO(){return key;}static boolean dA(int k){return back;}}
class w {static boolean Vl;}
class android_placeholder {}
class TutorialFlowController {static int opens;static boolean openModal(int a,int b,int c,boolean d){opens++;return false;}}
class SystemClock {static long elapsedRealtime(){return 0;}}
class FarmGameEngine {
 static int aML,aMH,Qy=16,state,pops,popTransitions,updates,layout,apA=-1,apz,apB=3,aST=-1,currentTutorialStepIndex=8,aYS=-1;
 static long bap;static boolean awV;static boolean lN(int i){return false;}
 static int currentEngineState(){return state;}
 static void Ff(){state=apz;apA=-1;}static boolean aME,aMF;static String aMw,aMx,aMK;
 static boolean[] visible=new boolean[80];
 static void Ir(){java.util.Arrays.fill(visible,false);}
 static void e(int gui,int element,boolean show){if(gui!=4)throw new AssertionError("wrong GUI");visible[element]=show;}
 static void T(int a,int b,int c){layout=c;}
 PUSH
 static void fT(int id){updates++;}
 static void qB(){pops++;apA=1;}
 static void qG(){if(apA==1){state=18;apA=-1;popTransitions++;}}
 SETUP
 UPDATE
}
public class NotificationProbe {
 static int decisions;static boolean choice;
 static void show(boolean dismiss){GameNotification.show("title","body","restart","later",dismiss,positive->{decisions++;choice=positive;return kotlin.Unit.INSTANCE;});}
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 public static void main(String[] args){
  show(true);
  check(FarmGameEngine.state==20&&FarmGameEngine.aML==13&&FarmGameEngine.layout==10,"did not reuse engine20/GUI4");
  check(TutorialFlowController.opens==0,"notification stole or was blocked by tutorial ownership");
  check(FarmGameEngine.visible[15]&&FarmGameEngine.visible[16],"original buttons missing");
  check(GameNotification.positiveLabel().equals("restart")&&GameNotification.negativeLabel().equals("later"),"localized labels lost");
  for(int frame=0;frame<100;frame++)check(FarmGameEngine.updateGameNotification(2),"native input escaped to tutorial rules");
  check(decisions==0&&FarmGameEngine.updates==100,"idle popup dismissed or stopped sprite updates");
  GameNotification.choose(true);GameNotification.choose(true);check(decisions==1&&choice&&FarmGameEngine.pops==1,"duplicate restart");
  show(true);check(FarmGameEngine.popTransitions==1,"new prompt overwrote pending pop");GameRuntime.key=19;FarmGameEngine.updateGameNotification(2);
  check(decisions==2&&!choice&&!GameNotification.isVisible(),"Back did not defer");
  show(false);GameNotification.choose(false);
  check(GameNotification.isVisible()&&decisions==2&&!FarmGameEngine.visible[16],"compensation barrier canceled before durability");
  GameNotification.choose(true);check(decisions==3&&choice,"explicit retry missing");
  for(int language:new int[]{0,1,2,3,4,5,7,9,10,11,12,15,16,20}){
   String[] text=GameNotification.storageFailure(language);check(text.length==2&&!text[0].isEmpty()&&!text[1].isEmpty(),"missing storage translation");
  }
  System.out.println("PASS native GUI4 setup/input, idle frames, exactly-once decisions, Back/Later, durable compensation barrier, 14 locales");
 }
}
'''.replace('PACKAGE', qualified_package()).replace('SETUP', setup).replace('UPDATE', update).replace('PUSH', push).replace('class android_placeholder {}', '')
        with tempfile.TemporaryDirectory() as temp:
            folder = Path(temp)
            java = folder / 'NotificationProbe.java'
            java.write_text(source)
            compile_mixed([kotlin_source_directory() / 'GameNotification.kt'], [java], folder)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(folder), qualified_package()+'.NotificationProbe'], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            print(result.stdout.strip())
