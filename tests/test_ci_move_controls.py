"""Run the actual recovered move handlers with deterministic Android-free peers.

The peers record collision/UI/economy calls; this is a handler and layout test,
not an Android touch dispatch or rendered-pixel test.
"""
from pathlib import Path
import os
import subprocess
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
import sys
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

SOURCE = current_source_path('ci')
METHODS = (
    'static int[] layoutMoveControls(',
    'static boolean movePositionChanged(',
    'static boolean canConfirmMovePlacement()',
    'static boolean isMovePreviewActive()',
    'static boolean isRequiredTutorialPlacement()',
    'static boolean handleMovePlacementBack(int key)',
    'static void cancelMovePreviewForInterruption()',
    'private static void processPendingMoveInterruption()',
    'public void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 gl10, int width, int height)',
    'protected void U(int i, int i2)',
    'static void aD(boolean z)',
    'static void aT(boolean z)',
    'static void aE(boolean z)',
    'static void rV()',
    'static void sc()',
    'static void se()',
    'static void sv()',
    'static void rU()',
    'private static void confirmMovePlacement()',
    'private static boolean updateMovePreviewInput()',
)

SHELL = r'''
public class ci extends GLLib {
static int avD,avM,avN,avK,avL,aYR,aYS=-1,aBy,aBz,avV,avW,aLV,aSX,aTD,aCi,aAG=-1,aAH=-1,aAI=-1,aAJ=-1,aAK=-1;
static int aAu,aAv,aZp,aAk,aAl,aAm,aAn;static long aAh;static boolean aAi,aAj,moveInterruptionRequested;
private int moveSurfaceWidth=-1,moveSurfaceHeight=-1;
static int aBy0,aBz0,baj,bai,aMg0,aZB,ayf;
static boolean avI,avJ,avO,atw,aTC,baa,axV,axW,axK,bax,invalid;
static ch avE,avF,aZk,aZn,aZj=new ch(),aZi=new ch(),aZo,aSW,aMg,moveOriginalActor;
static aa awf,awg;
static int moveOriginalTransform;
static boolean moveConfirmRequested,movePlacementConfirmed;
static int commits,moveEvents,collisions,restoredCollision,saves,inventoryReturns,removedPreviews,quitEffects,returnedCategory,returnedDefinition;
static ch returnedActor;
static boolean[] shown=new boolean[100],enabled=new boolean[100];
static void e(int g,int i,boolean z){shown[i]=z;}
static void f(int g,int i,boolean z){enabled[i]=z;}
static void Ff(){} static void gM(int i){} static void gK(int i){}
static void aC(boolean z){} static void b(boolean z,ch c){} static void b(boolean x,boolean y,boolean z){}
static void aA(boolean z){}static void a(boolean z,ch c){}static void Ek(){}
static void DZ(){}static void gX(int i){quitEffects++;}static void su(){} static void k(ch c){collisions++;} static void n(ch c){restoredCollision++;}static void DT(){}
static ch Fm(){return null;}static void d(ch c,boolean z){}static void a(int x,int y,int z,ch c){inventoryReturns+=z;returnedCategory=x;returnedDefinition=y;returnedActor=c;}
static boolean t(ch c){return invalid;} static String dt(int i){return "invalid";}static void m(String s,int x,int y){}
static void a(int x,ch c){}static boolean hO(int n){return true;}static boolean hP(int n){return true;}
static void b(int n,short s){}static void a(int n,short s){}static int FA(){return 0;}static int FB(){return 0;}static void fE(int i){}
static void DP(){if(!canConfirmMovePlacement()||avE==null)return;commits++;if(movePositionChanged(avM,avN,avE.aol,avE.aom))moveEvents++;avE=null;}
static void c(int[]a,int[]b){b[0]=a[0];b[1]=a[1];}static void f(int[]a,int[]b){c(a,b);}static void g(int[]a,int[]b){c(a,b);}
static int bE(int x,int y){return x<0||y<0?-1:0;}static boolean isMoveControlTouch(int x,int y){return x>900;}
static void reset(int tutorial){aZB=0;ayf=0;moveInterruptionRequested=false;avD=3;aYR=tutorial;avM=10;avN=20;avE=new ch();avE.aol=10;avE.aom=20;avE.Kx=54;avE.aoO.dU(3);avE.aoO.dS(71);moveOriginalActor=avE;moveOriginalTransform=avE.aoO.aaN;movePlacementConfirmed=false;moveConfirmRequested=false;invalid=false;avI=avO=false;avJ=false;commits=moveEvents=collisions=restoredCollision=saves=inventoryReturns=removedPreviews=quitEffects=0;returnedActor=null;returnedCategory=returnedDefinition=-1;axW=axV=false;aAH=aAI=aAK=-1;GLLib.event=0;}
static void ok(boolean value,String label){if(!value)throw new AssertionError(label);}
static void testLayout(){
 int[][]sizes={{800,480},{480,320},{320,240},{240,320},{128,240}};
 int[]yes={-17,-35,72,65},no={-9,-28,66,70};
 for(int[]size:sizes)for(int x=-1000;x<1400;x+=37)for(int y=-800;y<1000;y+=41){
  int[]p=layoutMoveControls(x,y,size[0],size[1],yes,no);
  int ax=p[0]+yes[0],ay=p[1]+yes[1],bx=p[2]+no[0],by=p[3]+no[1];
  ok(ax>=0&&ay>=0&&ax+yes[2]<=size[0]&&ay+yes[3]<=size[1],"confirm in viewport");
  ok(bx>=0&&by>=0&&bx+no[2]<=size[0]&&by+no[3]<=size[1],"cancel in viewport");
  ok(ax+yes[2]<=bx||bx+no[2]<=ax||ay+yes[3]<=by||by+no[3]<=ay,"touch targets separated");
 }
}
static void required(int step,int mode,int kind,int definition){
 reset(step);avD=mode;avE.Kx=kind;avE.aoC=definition;
}
static void testTutorialPlacement(){
 // Selection and action steps must agree, including the pickup-to-next-frame gap.
 int[][]placements={{14,0,54,16},{15,0,54,16},{42,1,49,0},{43,1,49,0},{52,4,50,9},{53,4,50,9}};
 for(int[]p:placements){
  required(p[0],p[1],p[2],p[3]);ch actor=avE;actor.aol=40;actor.aom=50;axV=true;
  aD(true);ok(shown[83]&&enabled[83]&&!shown[84]&&!enabled[84],"mandatory placement keeps confirm and disables cancel at "+p[0]);
  sv();rV();sc();se();ok(handleMovePlacementBack(19),"mandatory placement consumes Android Back");
  ok(avE==actor&&avD==p[1]&&aYR==p[0]&&actor.aol==40&&actor.aom==50&&axV&&!axW,"all cancel handlers preserve required preview and tutorial at "+p[0]);
  ok(inventoryReturns==0&&removedPreviews==0&&commits==0&&saves==0&&quitEffects==0,"blocked cancellation has no item, economy or UI side effects");
  if(p[1]!=1){DP();ok(commits==0&&avE==actor,"map does not implicitly buy or confirm required item");}
  cancelMovePreviewForInterruption();ok(avE==actor&&avD==p[1],"interruption cannot cancel mandatory item");
  moveInterruptionRequested=true;GLLib.seedPending();processPendingMoveInterruption();
  ok(avE==actor&&avD==p[1]&&!moveInterruptionRequested&&GLLib.pendingCount()==0&&GLLib.allIdle(),"interrupted input drains without abandoning mandatory preview");
 }
 for(int mode:new int[]{0,4}){
  required(mode==0?15:53,mode,mode==0?54:50,mode==0?16:9);ch actor=avE;
  aZk=actor;GLLib.event=1;GLLib.x=10;GLLib.y=20;updateMovePreviewInput();
  GLLib.event=2;GLLib.x=60;GLLib.y=70;updateMovePreviewInput();
  GLLib.event=3;updateMovePreviewInput();
  ok(actor.aol==60&&actor.aom==70&&commits==0&&avE==actor,"mandatory item remains draggable without implicit commit");
  invalid=true;rU();ok(avE==actor&&commits==0,"invalid required position remains in placement");
  invalid=false;rU();ok(avE==null&&avD==-1&&commits==1&&saves==1&&movePlacementConfirmed,"valid required placement reaches tutorial success boundary");
  rU();ok(commits==1&&inventoryReturns==0,"duplicate confirm cannot duplicate or refund placed item");
 }
 for(int mode:new int[]{0,3,4}){
  reset(-1);avD=mode;ch original=avE;original.aol=80;original.aom=90;
  ok(!handleMovePlacementBack(24)&&!handleMovePlacementBack(11)&&avE==original,"Menu/other key preserves ordinary placement");
  ok(handleMovePlacementBack(19)&&avE==null&&avD==-1,"Back cancels ordinary purchase, move or inventory placement");
  ok(inventoryReturns==(mode==4?1:0)&&commits==0&&saves==0,"Back cancellation retains normal refund without commit");
  if(mode==3)ok(original.aol==10&&original.aom==20,"Back restores existing object's original coordinates");
  ok(!handleMovePlacementBack(19)&&inventoryReturns==(mode==4?1:0),"duplicate Back cannot refund twice");
 }
 // The lock is derived from the loaded step/item each time, never latched.
 for(int complete:new int[]{-1,0})for(int mode:new int[]{0,4}){
  required(mode==0?15:53,mode,mode==0?54:50,mode==0?16:9);aD(true);
  ok(!enabled[84],"required preview starts locked before loaded completion");
  ch canceled=avE;aYR=complete;aD(true);ok(shown[84]&&enabled[84],"loaded/completed tutorial restores cancel without stale lock");
  sv();sv();ok(avE==null&&avD==-1&&commits==0&&saves==0,"completed tutorial cancellation has no purchase or save side effects");
  ok(inventoryReturns==(mode==4?1:0)&&removedPreviews==1,"inventory item returns exactly once; purchase preview never adds inventory");
  if(mode==4)ok(returnedActor==canceled&&returnedCategory==3&&returnedDefinition==9,"refund retains exact selected building/category identity");
 }
 for(int step:new int[]{13,16,41,44,51,54}){
  required(step,4,50,9);aD(true);ok(enabled[84],"other tutorial steps keep cancel");sv();ok(avE==null,"other tutorial step cancellation runs normally");
 }
 required(15,0,54,17);aD(true);ok(enabled[84],"unrelated animal remains cancelable");sv();ok(avE==null,"unrelated animal can cancel");
 required(53,4,50,10);aD(true);ok(enabled[84],"unrelated inventory building remains cancelable");sv();ok(avE==null&&inventoryReturns==1,"unrelated item refunded normally");
 required(43,1,49,1);aD(true);ok(enabled[84],"unrelated repeat purchase remains cancelable");sv();ok(avE==null,"other plot definition cancellation unchanged");
 required(43,1,49,0);ayf=2;aD(true);ok(enabled[84],"finished plot objective releases cancellation immediately");sv();ok(avE==null,"plot completion cannot retain a stale placement lock");
 required(15,3,54,16);ch actor=avE;actor.aol=80;sv();ok(avE==null&&actor.aol==10,"moving an existing item is not a required tutorial purchase");
 required(53,4,50,9);aZB=1;aD(true);ok(enabled[84],"visiting farm has no own-farm tutorial lock");sv();ok(avE==null&&inventoryReturns==1,"visit cancellation unchanged");
 required(15,0,54,16);avE=null;aD(true);ok(!isRequiredTutorialPlacement()&&!handleMovePlacementBack(19),"step without selected item cannot latch cancellation lock");
 required(15,0,54,16);handleMovePlacementBack(19);reset(-1);avD=0;
 ok(handleMovePlacementBack(19)&&avE==null,"reloaded completed state carries no lock from previous tutorial world");
}
public static void main(String[]args){
 testLayout();
 reset(-1);aD(true);ok(shown[83]&&shown[84]&&enabled[84],"completed tutorial offers cancel");
 ch actor=avE;actor.aol=40;actor.aom=50;actor.aoO.dU(2);actor.aoO.dS(250);sv();
 ok(actor.aol==10&&actor.aom==20&&actor.aoO.aaN==3,"cancel rolls back actual sprite transform");
 ok(actor.aoO.lL()==0,"sprite elapsed timer is not mistaken for transform");
 ok(avD==-1&&avE==null&&aYR==-1,"normal cancel keeps completed tutorial");
 ok(moveEvents==0&&commits==0&&saves==0,"cancel does not reward or commit");
 testTutorialPlacement();
 for(int transform=0;transform<8;transform++){
  reset(-1);actor=avE;actor.aoO.dU(transform);int expected=actor.aoO.aaN;moveOriginalTransform=expected;
  actor.aoO.dU((transform+3)%8);sv();ok(actor.aoO.aaN==expected,"all raw transforms restore without remapping twice");
 }
 reset(-1);actor=avE;int flags=2|64|16777216;actor.HL=flags;actor.fy(16777216);avJ=true;sv();
 ok(actor.HL==flags&&actor.qrCalls==1&&!avJ,"cancel restores only original clipping flag once");
 sv();ok(actor.HL==flags&&actor.qrCalls==1,"duplicate cancel cannot re-register clipping flag");
 reset(-1);actor=avE;actor.aol=90;actor.aoO.dU(2);cancelMovePreviewForInterruption();
 ok(actor.aol==10&&actor.aom==20&&actor.aoO.aaN==3&&avD==-1,"pause rolls back preview transform");
 reset(-1);avE.aol=30;DP();ok(commits==0&&avE!=null,"map cannot implicitly confirm");
 invalid=true;rU();ok(commits==0&&avD==3&&avE!=null,"invalid confirm keeps preview");
 invalid=false;actor=avE;rU();ok(commits==1&&moveEvents==1&&saves==1&&actor.aol==30,"x-only valid confirm");
 rU();ok(commits==1&&moveEvents==1&&saves==1,"duplicate release is no-op");
 reset(-1);avE.aom=40;rU();ok(moveEvents==1,"y-only valid move counts");
 reset(-1);rU();ok(moveEvents==0,"unchanged pose does not count");
 reset(-1);actor=avE;aZk=actor;GLLib.event=1;GLLib.x=10;GLLib.y=20;updateMovePreviewInput();
 GLLib.event=2;GLLib.x=60;GLLib.y=70;updateMovePreviewInput();
 GLLib.event=3;updateMovePreviewInput();ok(actor.aol==60&&actor.aom==70&&avD==3&&commits==0,"drag/release is preview only");
 sv();ok(actor.aol==10&&actor.aom==20&&moveEvents==0,"drag cancel not objective event");
 reset(-1);actor=avE;avI=true;aAH=65619;GLLib.event=2;GLLib.x=60;GLLib.y=70;updateMovePreviewInput();
 ok(actor.aol==10&&actor.aom==20&&!avI&&commits==0,"captured UI cannot move map actor");
 reset(-1);aZk=null;int pans=aZj.pans;GLLib.event=1;updateMovePreviewInput();
 ok(aZj.pans==pans+1&&avD==3&&commits==0,"map press pans without confirming");
 reset(-1);actor=avE;actor.aol=80;actor.aom=90;aAH=aAJ=65619;GLLib.Uu=0;GLLib.seedPending();
 ci game=new ci();game.U(-1,-1);
 ok(moveInterruptionRequested&&actor.aol==80&&avD==3,"cancel queues game-thread rollback, no UI-thread world mutation");
 ok(aAH==-1&&aAJ==-1&&commits==0,"cancel clears GUI capture without fake confirmation");
 processPendingMoveInterruption();
 ok(!moveInterruptionRequested&&actor.aol==10&&actor.aom==20&&avD==-1,"next game frame rolls back canceled preview");
 ok(GLLib.pendingCount()==0&&GLLib.allIdle(),"all original pointer pending events drained and pointers idle");
 reset(-1);GLLib.Uu=0;game.U(20,30);ok(!moveInterruptionRequested&&GLLib.pendingCount()==2,"valid primary release retained in scalar and pointer buffers");
 GLLib.lb();GLLib.la();LanguageRestartController.blocked=true;game.U(20,30);
 ok(GLLib.pendingCount()==0&&!moveInterruptionRequested,"language checkpoint blocks ordinary pointer release");
 game.U(-1,-1);ok(moveInterruptionRequested,"Android cancel still queues cleanup while language is blocked");
 processPendingMoveInterruption();LanguageRestartController.blocked=false;
 GLLib.lb();GLLib.la();GLLib.Uu=1;game.U(-1,-1);
 ok(!moveInterruptionRequested&&GLLib.pendingCount()==1,"secondary release does not cancel primary preview");
 GLLib.lb();GLLib.la();
 reset(-1);actor=avE;actor.aol=80;ci resized=new ci();resized.onSurfaceChanged(null,800,480);
 ok(!moveInterruptionRequested&&actor.aol==80,"first surface callback preserves active preview");
 resized.onSurfaceChanged(null,800,480);ok(!moveInterruptionRequested,"same-size surface recreation is not a resize");
 resized.onSurfaceChanged(null,320,240);ok(moveInterruptionRequested&&actor.aol==80&&resized.surfaceChanges==3,"resize queues rollback and preserves superclass surface callback");
 processPendingMoveInterruption();ok(actor.aol==10&&actor.aom==20&&avD==-1,"resize rollback occurs at game-thread boundary");
 reset(-1);avD=0;actor=avE;aAI=65619;GLLib.event=3;GLLib.x=60;GLLib.y=70;applyRawReleaseFixture();
 ok(actor.aol==10&&actor.aom==20,"raw purchase release respects GUI capture before DO");
 aAI=-1;GLLib.x=950;applyRawReleaseFixture();ok(actor.aol==10&&actor.aom==20,"disabled control bounds block raw map release");
 GLLib.x=60;applyRawReleaseFixture();ok(actor.aol==60&&actor.aom==70&&commits==0,"map release may reposition preview without committing");
 reset(-1);avD=1;actor=avE;aAI=65619;GLLib.event=3;applyRawReleaseFixture();ok(actor.aol==60&&actor.aom==70,"repeat placement retains original release flow");
 ok(movePositionChanged(1,2,3,2)&&movePositionChanged(1,2,1,3)&&!movePositionChanged(1,2,1,2),"axis predicates");
 System.out.println("PASS movement preview, valid explicit confirm, cancellation/facing/tutorial placement ownership, input capture, duplicate release and 14,300 edge layouts");
}
'''

PEERS = r'''
class ch{int aol,aom,Kx,aoC,aoU,ant,Bj,pans,qrCalls;int HL=2;boolean aoT,anw;int aon;aa aoO=new aa();
 /* ACTUAL_FLAGS */ void qr(){qrCalls++;}void fp(int i){aoO.dU(0);aoO.dS(0);}boolean oO(){return false;}
 boolean oY(){return false;}
 void oN(){}void qq(){ci.removedPreviews++;}int qp(){return Kx==50?3:0;}void fj(int i){}void pb(){pans++;}void pc(){} }
class b{static boolean Ka;}
class GLLib{static int event,x,y;static long Tn,To;int surfaceChanges;
 public void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 gl10,int width,int height){surfaceChanges++;}
 static boolean dB(int i){return event==1;}static boolean dC(int i){return event==2;}
 static boolean dA(int i){return event==3;}static int dG(int i){return x;}static int dH(int i){return y;}
 static int dI(int i){return x;}static int dJ(int i){return y;}
 private static boolean Ur,Us,Ut;private static int Uk,Ul,Um,Up,Uq;protected static int Uu;
 private static int[]Uv,Uw,Ux,Uy,Uz,UA,UB;private static boolean[]UC,UD,UE;
 static void c(boolean b,String message){if(!b)throw new AssertionError(message);}
 static void seedPending(){la();Ur=Us=Ut=true;for(int i=0;i<w.ZB;i++)UC[i]=UD[i]=UE[i]=true;}
 static int pendingCount(){int n=(Ur?1:0)+(Us?1:0)+(Ut?1:0);for(int i=0;i<w.ZB;i++)n+=(UC[i]?1:0)+(UD[i]?1:0)+(UE[i]?1:0);return n;}
 static boolean allIdle(){if(Uk!=0)return false;for(int i:Uz)if(i!=0)return false;return true;}
 /* ACTUAL_POINTER_METHODS */ }
class w{static int ZB=2;static boolean Vl;}
class SystemClock{static long elapsedRealtime(){return 0;}}
class ProgressSaveController{static void requestSave(){ci.saves++;}}
class LanguageRestartController{static boolean blocked;static boolean blocksGameplay(){return blocked;}}
'''


class CIMoveControlsTest(unittest.TestCase):
    def test_actual_move_handlers_and_layout(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        java, javac = home / 'bin/java', home / 'bin/javac'
        if not java.exists() or not javac.exists():
            self.skipTest('Requires installed JDK')
        source = legacy_core_source('ci')
        methods = '\n'.join(extract_method(source, signature) for signature in METHODS)
        farm_input = extract_method(source, 'static boolean jM(int i)')
        release = extract_method(farm_input, 'if (GLLib.dA(0)) {\n                    if (aZi.oY())')
        methods += '\nstatic boolean applyRawReleaseFixture(){' + release + '\nreturn false;}'
        animation_source = legacy_core_source('aa')
        # These are the real accessors/remapping, not a test definition of facing.
        animation = 'class aa{protected int aaN,aaR;' + '\n'.join(
            extract_method(animation_source, signature) for signature in (
                'public int lL()', 'public void dS(int i)', 'public final void dU(int i)')) + '}'
        actor_source = legacy_core_source('ch')
        peers = PEERS.replace('/* ACTUAL_FLAGS */', '\n'.join(
            extract_method(actor_source, signature) for signature in (
                'void fx(int i)', 'void fy(int i)', 'boolean fz(int i)')))
        pointer_source = legacy_core_source('GLLib')
        peers = peers.replace('/* ACTUAL_POINTER_METHODS */', '\n'.join(
            extract_method(pointer_source, signature) for signature in (
                'public static final void la()', 'public static final void lb()',
                'public static void S(int i, int i2)', 'public static void m(int i, int i2, int i3)',
                'private static final void dz(int i)', 'private static void T(int i, int i2)',
                'protected void U(int i, int i2)', 'private void n(int i, int i2, int i3)')))
        with tempfile.TemporaryDirectory(prefix='ci-move-handlers-') as temporary:
            directory = Path(temporary)
            fixture = directory / 'ci.java'
            fixture.write_text(SHELL + methods + '\n}\n' + peers + animation)
            gl = directory / 'javax/microedition/khronos/opengles/GL10.java'
            gl.parent.mkdir(parents=True)
            gl.write_text('package javax.microedition.khronos.opengles; public interface GL10 {}')
            result = subprocess.run([str(javac), '-source', '8', '-target', '8', str(fixture), str(gl)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), '-cp', str(directory), 'ci'], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS movement preview', result.stdout)


if __name__ == '__main__':
    unittest.main()
