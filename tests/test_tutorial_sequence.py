"""Exercise production tutorial ownership and extracted input/condition paths.

The small peers below record UI/entity boundaries. No Android rendering or
storage is simulated, and the original quest counters/predicates are untouched.
"""
from pathlib import Path
import os
import re
import subprocess
import tempfile
import unittest

from test_aa_tile_renderer import extract_method
import sys
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "tools"))
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import (qualified_package, core_source_directory, kotlin_source_directory, type_name, adapt_fixture, legacy_fixture_names)

ROOT = Path(__file__).resolve().parents[1]
PACKAGE = qualified_package().replace('.', '/')
SOURCE = core_source_directory()

SHELL = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class GLLib {
 static int event,x=200,y=150,drains,Qy=16; static boolean pending;
 static boolean dA(int i){return event==2;} static int dG(int i){return x;} static int dH(int i){return y;}
 static void lb(){drains++;pending=false;} static void la(){event=0;}
}
class Sprite {}
class aa {
 int aaP;boolean finished;static int created;
 aa(Sprite sprite,int x,int y){created++;}
 void Y(int animation,int repeats){aaP=animation;}boolean lQ(){return finished;}void dV(int elapsed){}
}
class Constants {static final int APP_INDEXING_SCHEDULE_INTERVAL_MS=1000;}
class AdRequest {static final int MAX_CONTENT_URL_LENGTH=512;}
class Tracker {void b(int i,String[]s){}}
class ch {
 long tutorialEatingEpoch,tutorialGrowthEpoch;
 static ch[]als=new ch[2];ch aoi;boolean anw;aa aoR;
 int Kx,aoC,aol,aom,aot,aou,Bj,HL; static boolean pickup; static int crops=6;
 static int a(int a,int b,ch[]out,int n){if(a==3&&pickup)out[0]=new ch();return 0;}
 static int b(int a,int b,int c,ch[]out,int n){if(b==54){for(ch e=als[1];e!=null;e=e.aoi)if(e.Kx==b&&e.aoC==c){out[0]=e;return 1;}return 0;}return crops;}
 boolean pm(){return false;} boolean pn(){return false;} boolean po(){return false;} boolean pK(){return false;}
 boolean fz(int i){return (HL&i)!=0;} boolean oZ(){return true;} void pb(){} void fo(int i){} void fx(int i){}
 boolean anN; void y(int x,int y,int t){}
 /* EATING_COMPLETION */
 /* GROWTH_RESTORE */
}
public class ci {
 static int aYR,aYS=-1,ayf,aCi,avR,avD=-1,avQ=-1,avP=-1,aAG=-1,aAH=-1,aAI=-1,aAJ=-1,aAK=-1;
 static int ayF,aqD,level=1,ayt,ayu,ayv,ayw,ayx,ayy,ayr,ays,ayC,ayB,aAM,aZB,axj=-1;
 static int aVF,awW,guiRebuilds,objectiveActions;static int[]aUT={101},aZQ={100,101};
 static boolean awM,awN,awU,awS;static boolean[]shown=new boolean[100],enabled=new boolean[100];
 static long tutorialUiCallbackEpoch;
 static boolean awV,axV,axW,aym,aMo,aye,ayp,auY,aYW,awO;
 static String aqK="",axX=""; static Tracker baf; static ch aZj=new ch(),aZi,avE,avF,aZk,aZl,aZm,aZn;
 static short[][][]atc=new short[5][100][24];
 static Sprite[]aZf=new Sprite[14];static void aa(int a,int b,int c){throw new AssertionError("unexpected economy/objective callback");}
 static int FC(){return level;} static void Er(){aYR++;axV=axW=false;}
 static int Fh(){return (4<<16)|4;}static boolean Fd(){return GLLib.event==1;}
 static boolean Fc(){return GLLib.event==2;}static int Fb(){return 0;}static boolean hy(int i){return false;}
 static void hj(int i){} static void Et(){} static void hc(int i){} static void bl(int a,int b){}
 static void H(int a,int b,int c){}static void Ff(){}
 static void c(boolean b,int x,int y){}static void c(int[]a,int[]b){}static void f(int[]a,int[]b){}static void g(int[]a,int[]b){}
 static void b(ch c,int x,int y){}static void b(boolean a,boolean b,boolean c){} static void aC(boolean a){}
 static void a(boolean z,ch c){}static void a(ch c,boolean z){}static void aT(boolean z){}static void aD(boolean z){}static void aT(int x){}
 static void e(int a,int b,boolean c){if(a==1)shown[b]=c;}static void f(int a,int b,boolean c){if(a==1)enabled[b]=c;}static boolean bg(int a,int b){return false;}
 static boolean MI(){return false;} static int KR(){return 0;}static boolean bn(int a,int b){return true;}
 static int gl(int i){return 0;} static void bm(int a,int b){}static int bE(int a,int b){return -1;}
 static boolean DO(){return false;}static void fE(int i){}static void gX(int i){}static void DW(){}static void Em(){}
 static void Es(){}static void El(){}static int jO(){return 0;}static int qD(){return 19;}static boolean bh(int a,int b){return false;}
 static void rP(){}static String dt(int i){return "";}static void dC(String s){}static void DF(){}static void gM(int i){guiRebuilds++;}
 static int[]aVe={2000},aVf={17};static void bb(int a,int b){objectiveActions++;}
'''

RUNNER = r'''
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static void reset(int step){aYR=step;axV=true;axW=true;ayf=0;aym=false;level=1;avE=null;ch.pickup=false;ch.crops=6;TutorialFlowController.invalidateSession();TutorialFlowController.frameStarted();}
 static void queueUi(){GLLib.event=1;Fi();GLLib.event=2;Fi();check(aAJ> -1,"UI callback enqueued");}
 public static void main(String[]args){
  // Modal callbacks carry the epoch of the step and session that opened them.
  TutorialFlowController.invalidateSession();
  check(TutorialFlowController.openModal(20,3,-1,false),"open first modal");
  check(!TutorialFlowController.openModal(27,3,-1,false),"one modal owner");
  check(!TutorialFlowController.acceptModal(20,3,-1,false),"opening gesture cannot close modal");
  TutorialFlowController.pointerPressed();
  check(TutorialFlowController.acceptModal(20,3,-1,false),"new gesture accepts modal");
  check(!TutorialFlowController.acceptModal(20,3,-1,false),"duplicate callback rejected");
  check(TutorialFlowController.inputConsumed(3,-1,false),"underlying input consumed");
  check(TutorialFlowController.openModal(20,6,-1,false),"next step owns next modal");
  check(!TutorialFlowController.acceptModal(20,6,-1,false),"same gesture cannot skip next modal");
  TutorialFlowController.pointerPressed();
  check(!TutorialFlowController.acceptModal(20,7,-1,false),"old step callback rejected");
  check(TutorialFlowController.openModal(20,7,-1,false),"replace invalid step owner");
  TutorialFlowController.invalidateSession();TutorialFlowController.pointerPressed();
  check(!TutorialFlowController.acceptModal(20,7,-1,false),"old session callback rejected");
  check(TutorialFlowController.openModal(20,-1,-1,false),"ordinary modal remains available");
  check(TutorialFlowController.acceptModal(20,-1,-1,false),"ordinary modal unaffected");
  check(TutorialFlowController.openModal(27,10,-1,false),"action requests quest modal");
  TutorialFlowController.modalActivated(27,11,-1,false);
  TutorialFlowController.pointerPressed();
  check(TutorialFlowController.acceptModal(27,11,-1,false),"modal binds to the resulting step");
  TutorialFlowController.pointerPressed();
  TutorialFlowController.closeModal(27);
  check(!TutorialFlowController.inputConsumed(11,-1,false),"closing cannot consume a newer gesture");
  long callback=TutorialFlowController.captureCallback(32,-1,false);
  check(TutorialFlowController.callbackCurrent(callback,32,-1,false),"live animation callback");
  check(!TutorialFlowController.callbackCurrent(callback,33,-1,false),"old step animation callback");
  callback=TutorialFlowController.captureCallback(32,-1,false);
  TutorialFlowController.invalidateSession();
  check(!TutorialFlowController.callbackCurrent(callback,32,-1,false),"old session animation callback");
  ch animal=new ch();reset(32);awO=false;
  animal.tutorialEatingEpoch=TutorialFlowController.captureCallback(aYR,aYS,awV);
  animal.finishEatingTutorial();check(aYR==33&&awO&&!axW&&!axV,"current eating callback advances once");
  animal.finishEatingTutorial();check(aYR==33,"duplicate eating callback");
  reset(18);awO=false;animal.tutorialEatingEpoch=TutorialFlowController.captureCallback(aYR,aYS,awV);
  animal.finishEatingTutorial();check(aYR==18&&!awO,"unrelated animal cannot jump to step33");
  reset(32);awO=false;animal.tutorialEatingEpoch=TutorialFlowController.captureCallback(aYR,aYS,awV);
  TutorialFlowController.invalidateSession();animal.finishEatingTutorial();
  check(aYR==32&&!awO,"old world eating animation cannot advance tutorial");
  reset(5);queueUi();check(acceptTutorialUiCallback(),"current delayed GUI callback");
  reset(5);queueUi();aYR=6;check(!acceptTutorialUiCallback(),"old step delayed GUI callback");
  reset(5);queueUi();TutorialFlowController.invalidateSession();
  check(!acceptTutorialUiCallback(),"old session delayed GUI callback");
  reset(-1);queueUi();check(tutorialUiCallbackEpoch==0&&acceptTutorialUiCallback(),"ordinary GUI callback preserved");
  // Cold restore after pR grew definition16 to17, before its cosmetic completion.
  reset(18);axV=false;axW=false;ch grown=new ch();grown.Kx=54;grown.aoC=17;ch.als[1]=grown;
  int effects=aa.created;TutorialFlowController.restoreAfterLoad();
  check(grown.aoR!=null&&aa.created==effects+1&&!axW&&aYR==18,"restored growth only arms cosmetic completion");
  aa effect=grown.aoR;TutorialFlowController.restoreAfterLoad();
  check(grown.aoR==effect&&aa.created==effects+1,"growth restore idempotent");
  for(int n=0;n<20;n++){grown.tickGrowth();TutorialFlowController.frameStarted();conditionStep();}
  check(aYR==18&&!axW,"loaded adult cannot bypass incomplete growth animation");
  effect.finished=true;grown.tickGrowth();TutorialFlowController.frameStarted();conditionStep();
  check(aYR==19&&grown.aoR==null&&grown.aoC==17,"restored real growth completion advances exactly once");
  reset(18);axW=false;grown=new ch();grown.Kx=54;grown.aoC=17;ch baby=new ch();baby.Kx=54;baby.aoC=16;grown.aoi=baby;ch.als[1]=grown;
  TutorialFlowController.restoreAfterLoad();check(grown.aoR==null&&!axW,"remaining baby owns actual growth action");
  grown.aoi=null;ch duplicate=new ch();duplicate.Kx=54;duplicate.aoC=17;grown.aoi=duplicate;
  TutorialFlowController.restoreAfterLoad();check(grown.aoR==null&&duplicate.aoR==null,"ambiguous adult cannot be guessed");
  grown.aoi=null;aYR=32;TutorialFlowController.restoreAfterLoad();check(grown.aoR==null,"unrelated step unchanged");
  aYR=18;aZB=1;TutorialFlowController.restoreAfterLoad();check(grown.aoR==null,"visiting farm unchanged");aZB=0;
  // Ep has hidden the book while reconstructing the world at persisted step12.
  reset(12);axV=false;axW=false;shown[53]=enabled[53]=false;
  int rebuilds=guiRebuilds;TutorialFlowController.restoreWorldUi();
  check(shown[53]&&enabled[53]&&guiRebuilds==rebuilds+1,"cold quest control rebuilt for display and input");
  check(aYR==12&&!axV&&!axW&&!awN&&objectiveActions==0,"UI reconstruction does not initialize/advance/claim quests");
  TutorialFlowController.frameStarted();conditionStep();check(aYR==12&&!axW,"restored book still requires objective Go action");
  tO();check(aYR==13&&awN&&objectiveActions==1,"actual Go handler advances original quest guide");
  for(int step:new int[]{10,11}){reset(step);TutorialFlowController.restoreWorldUi();check(shown[53]&&enabled[53]&&shown[65]==(step==10)&&shown[66]==(step==11),"original book hint for step "+step);}
  reset(13);shown[53]=enabled[53]=false;TutorialFlowController.restoreWorldUi();check(!shown[53]&&!enabled[53],"later tutorial owns its controls");
  TutorialFlowController.frameStarted();
  check(TutorialFlowController.beginStep(1,5,1000,true),"intro is active sequence");
  check(!TutorialFlowController.beginStep(2,5,1000,true),"update sequence cannot race intro");
  check(!TutorialFlowController.beginStep(1,5,1000,true),"step runs once per frame");
  // Actual Es conditions reject an unrelated/late axW signal.
  for(int step:new int[]{5,8,14,38,43,46,58,61}){
   reset(step);conditionStep();check(aYR==step&&!axW,"foreign completion flag advanced step "+step);
   TutorialFlowController.frameStarted();
   if(step==14){avE=new ch();avE.Kx=54;avE.aoC=16;}
   else if(step==58)aym=true;else if(step==61)level=2;else ayf=8;
   conditionStep();check(aYR==step+1,"real condition did not advance step "+step);
  }
  // Actual jN(8): a blank-farm release cannot acknowledge an action step.
  atc[4][2][2]=100;atc[4][2][3]=100;
  for(int step:new int[]{5,8,14,38,43,46,58,61}){
   reset(step);axW=false;GLLib.event=2;jN(8);check(!axW,"farm release skipped action step "+step);
  }
  reset(3);axW=false;GLLib.event=2;GLLib.x=200;GLLib.y=150;jN(8);
  check(axW&&GLLib.event==0&&GLLib.drains==3,"speech consumes its release once");
  axW=false;jN(8);check(!axW,"duplicate speech release");
  for(int[]point:new int[][]{{1000,150},{200,1000},{200,20}}){
   reset(3);axW=false;GLLib.event=2;GLLib.x=point[0];GLLib.y=point[1];jN(8);check(!axW,"speech bounds");
  }
  System.out.println("PASS tutorial condition, modal ownership, stale callback, rapid gesture and speech input checks");
 }
}
'''


def selected_condition_method(source):
    body = extract_method(source, 'static void Es()')
    selected = []
    for step in (5, 8, 12, 14, 18, 38, 43, 46, 58, 61):
        marker = (r'^            case R\.styleable\.CardView_contentPaddingBottom /\* 12 \*/:\s*$'
                  if step == 12 else r'^            case ' + str(step) + r':\s*$')
        start = re.search(marker, body, re.M).start()
        following = re.search(r'^            case ', body[start + 1:], re.M)
        end = start + 1 + following.start() if following else body.rfind('        }')
        selected.append(body[start:end])
    return ('static void conditionStep(){\n'
            'if(!TutorialFlowController.beginStep(1,aYR,aYS,awV))return;\nswitch(aYR){\n'
            + '\n'.join(selected) + '\n}}')


class TutorialSequenceTest(unittest.TestCase):
    def test_production_conditions_and_input(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        javac, java = home / 'bin/javac', home / 'bin/java'
        if not javac.is_file():
            self.skipTest('Requires installed JDK')
        source = legacy_fixture_names((SOURCE / (type_name('ci') + '.java')).read_text())
        methods = '\n'.join(extract_method(source, signature) for signature in (
            'static boolean jN(int i)', 'private static boolean isIntroSpeechStep(',
            'private static void consumeTutorialGesture()', 'static void Fi()',
            'private static boolean acceptTutorialUiCallback()', 'static void tO()'))
        self.assertIn('if (i > -1 && acceptTutorialUiCallback())',
                      extract_method(source, 'public void Mr()'))
        entry = extract_method(source, 'static boolean jM(int i)')
        self.assertIn('Ep();\n            }\n            TutorialFlowController.restoreWorldUi();', entry)
        # First two original quest-guide branches, including the actual Go
        # transition to intro13; later unrelated guides are outside this fixture.
        task = extract_method(source, 'static void tR()')
        task = task[:task.index(' else if (i == aZQ[2]')] + '\n}'
        # Same constants the original game uses; no objective rules in peers.
        methods = methods.replace('Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS', '1000')
        actor_source = legacy_fixture_names((SOURCE / (type_name('ch') + '.java')).read_text())
        completion = extract_method(actor_source, 'private void finishEatingTutorial()')
        # The fixture invokes the exact callback directly after the animation
        # boundary; production pH invokes it only after aoO.lQ().
        self.assertIn('finishEatingTutorial();', extract_method(actor_source, 'void pH()'))
        completion = completion.replace('private void ', 'void ', 1)
        restore = '\n'.join(extract_method(actor_source, signature) for signature in (
            'private void startGrowthEffect()', 'void restoreTutorialGrowthEffect()'))
        # Keep the actual animation-complete branch from pG, including clearing
        # the effect and the original tutorial/objective side effects.
        growth = extract_method(actor_source, 'void pG()')
        start = growth.index('            if (this.aoR.lQ()) {')
        end = growth.index('\n        }', start)
        tick = 'void tickGrowth(){if(aoR==null)return;int i=aoR.aaP;\n' + growth[start:end] + '\n}'
        shell = SHELL.replace('/* EATING_COMPLETION */', completion).replace('/* GROWTH_RESTORE */', restore + tick)
        fixture = (shell + methods + task + selected_condition_method(source) + RUNNER).replace(
            'R.styleable.CardView_contentPaddingBottom /* 12 */', '12')
        with tempfile.TemporaryDirectory(prefix='tutorial-sequence-') as temporary:
            directory = Path(temporary)
            package = directory / PACKAGE
            package.mkdir(parents=True)
            main = package / (type_name('ci') + '.java')
            main.write_text(adapt_fixture(fixture))
            helper = kotlin_source_directory() / 'TutorialFlowController.kt'
            compile_mixed([helper], [main], directory)
            result = subprocess.run([str(java), '-cp', runtime_classpath(directory),
                                     PACKAGE.replace('/', '.') + '.' + type_name('ci')],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS tutorial condition', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
