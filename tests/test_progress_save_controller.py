"""Behavior checks for the actual scheduler and extracted original state machine.

Android views, storage and serializers are fixtures. These tests exercise save
ordering/retries without claiming SQLite, rendering or device coverage.
"""
from pathlib import Path
import os
import subprocess
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'

FIXTURE = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class GameSaveStore {
 static final int STATUS_IDLE=0,STATUS_SAVING=1,STATUS_SAVED=2,STATUS_FAILED=3;
 static boolean ready,loading,failFinish,failReopen,failInitialize;
 static int state,initializes,reopens,begins;
 static void initialize(){initializes++;if(failInitialize)throw new IllegalStateException("open");ready=true;state=0;}
 static void beginLoading(){loading=true;begins++;}
 static void finishLoading(){if(failFinish)throw new IllegalStateException("finish");loading=false;}
 static void close(){ready=false;}
 static void reopen(){reopens++;if(failReopen)throw new IllegalStateException("reopen");ready=true;state=0;}
 static boolean isReady(){return ready;}static int status(){return state;}
}
class PendingRewardState {static int resets;static void resetForLoad(){resets++;}}
class EntityPoseState {static int resets;static void resetForLoad(){resets++;}}
class GloftGF2F {static GloftGF2F acx;void mj(){}}
class ch {static int pending,credits,settles,entities,loadClears;static boolean levelUpOnSettle;static void oM(){settles++;credits+=pending;pending=0;if(levelUpOnSettle){levelUpOnSettle=false;pending=2;ci.apA=0;ci.apz=14;}}static void oL(){entities=0;loadClears++;}}
class ci {
 static int avD=-1,aZB,attempts,writes,visitorWrites,cancels,clears,storedCredits,storedPending;
 static Object avE,baf;static ch aZi,aZj;static int queued;static boolean baa,bab,failWrite,skipWrite,legacyThrottle,requestDuringCommit,forcedSeen;
 static int apA=-1,apz=-1,apB=-1,auN,asi;static int[]apC=new int[32],apV=new int[128];
 static void state(int...stack){apB=stack.length-1;System.arraycopy(stack,0,apC,0,stack.length);}
 static void FN(){queued=0;}
 static void cancelMovePreviewForInterruption(){cancels++;avD=-1;avE=null;}
 static void av(boolean ignored){
  attempts++;forcedSeen=ProgressSaveController.isForcedCheckpoint();
  if(skipWrite||(legacyThrottle&&!forcedSeen))return;
  if(failWrite){GameSaveStore.ready=false;GameSaveStore.state=3;ProgressSaveController.saveFailed();return;}
  writes++;storedCredits=ch.credits;storedPending=ch.pending;GameSaveStore.state=2;
  if(requestDuringCommit){requestDuringCommit=false;ProgressSaveController.requestSave();}
  ProgressSaveController.committed();
 }
 static void rs(){visitorWrites++;av(false);}
 static boolean fJ(int action){if(action==5&&isPersistedWorldState(qD())){ch.oM();clears++;}return false;}
 static void gD(int state){}static void gE(int state){}
'''

RUNNER = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import android.os.SystemClock;import java.lang.reflect.*;
public class ProgressSaveChecks {
 static void check(boolean b,String message){if(!b)throw new AssertionError(message);}
 static Object field(String name)throws Exception{Field f=ProgressSaveController.class.getDeclaredField(name);f.setAccessible(true);return f.get(null);}
 static void set(String name,Object value)throws Exception{Field f=ProgressSaveController.class.getDeclaredField(name);f.setAccessible(true);f.set(null,value);}
 static void reset(){
  GameSaveStore.failFinish=GameSaveStore.failReopen=GameSaveStore.failInitialize=false;
  GameSaveStore.initializes=GameSaveStore.reopens=GameSaveStore.begins=0;
  ci.attempts=ci.writes=ci.visitorWrites=ci.cancels=ci.clears=ci.storedCredits=ci.storedPending=0;
  ci.baa=ci.bab=ci.failWrite=ci.skipWrite=ci.legacyThrottle=ci.requestDuringCommit=ci.forcedSeen=false;
  ci.avD=-1;ci.aZB=0;ci.avE=null;ci.aZi=ci.aZj=null;ci.queued=0;ci.apA=ci.apz=-1;ci.state(18);
  ch.pending=ch.credits=ch.settles=ch.entities=ch.loadClears=0;ch.levelUpOnSettle=false;SystemClock.now=10000;
  ProgressSaveController.initialize();
 }
 static void ready(){ProgressSaveController.globalLoadFinished(false,false);ProgressSaveController.farmLoadFinished();ProgressSaveController.enterWorld();}
 static void loaderAndTimer()throws Exception{
  reset();ProgressSaveController.requestSave();ProgressSaveController.frameFinished();
  check(ci.writes==0,"pre-load save");
  ProgressSaveController.globalLoadFinished(false,false);ProgressSaveController.enterWorld();
  ProgressSaveController.frameFinished();check(ci.writes==0,"farm barrier bypassed");
  ProgressSaveController.farmLoadFinished();ProgressSaveController.enterWorld();ProgressSaveController.frameFinished();
  check(ci.writes==1&&!GameSaveStore.loading,"initial completed world not saved");
  for(int i=0;i<100;i++){SystemClock.now++;ProgressSaveController.frameFinished();}
  check(ci.writes==1,"wrote on every frame");SystemClock.now+=8000;ProgressSaveController.frameFinished();
  check(ci.writes==2,"periodic checkpoint missing");
  SystemClock.now+=501;ci.avD=3;ProgressSaveController.requestSave();ProgressSaveController.frameFinished();
  check(ci.writes==2,"saved unconfirmed move");ci.avD=-1;ci.state(4);ProgressSaveController.frameFinished();
  check(ci.writes==2,"saved partial loader state");ci.state(18);ProgressSaveController.frameFinished();
  check(ci.writes==3,"dirty save lost after barrier");
 }
 static void failedSaveRetry()throws Exception{
  reset();ready();ci.failWrite=true;ProgressSaveController.frameFinished();check(ci.attempts==1&&ci.writes==0,"failure fixture");
  for(int i=0;i<100;i++){SystemClock.now++;ProgressSaveController.frameFinished();}
  check(ci.attempts==1,"failed writes retried each frame");
  ci.failWrite=false;SystemClock.now+=8000;ProgressSaveController.frameFinished();
  check(ci.writes==1&&GameSaveStore.reopens==1,"retry did not reopen dirty RAM save");
  SystemClock.now+=501;ci.requestDuringCommit=true;ProgressSaveController.requestSave();ProgressSaveController.frameFinished();
  int before=ci.writes;SystemClock.now+=501;ProgressSaveController.frameFinished();
  check(ci.writes==before+1,"request during commit was acknowledged too early");
  ci.skipWrite=true;check(!ProgressSaveController.flushForLifecycle(),"declined serializer treated as durable save");
  ci.skipWrite=false;ci.legacyThrottle=true;check(ProgressSaveController.flushForLifecycle()&&ci.forcedSeen,"lifecycle obeyed obsolete device throttle");
 }
 static void loadRetry()throws Exception{
  reset();GameSaveStore.failFinish=true;ready();check(ProgressSaveController.isLoadBlocked(),"completion failure unblocked");
  int opened=GameSaveStore.initializes;set("retryLoad",Boolean.TRUE);GameSaveStore.failFinish=false;
  ProgressSaveController.frameFinished();
  check(!ProgressSaveController.isLoadBlocked()&&ci.writes==1&&GameSaveStore.initializes==opened,"completion retry lost live RAM or deadlocked");
  reset();ProgressSaveController.loadingFailed(47,new Exception("decode"));
  ch.entities=5;ci.aZi=new ch();ci.aZj=new ch();ci.queued=3;
  GameSaveStore.failInitialize=true;set("retryLoad",Boolean.TRUE);
  check(!ProgressSaveController.allowLoadStep()&&((Integer)field("failedLoadStep"))==47,"retry failure lost loader step");
  check(ch.entities==5&&ch.loadClears==0,"failed storage reopen discarded partial RAM before recovery");
  GameSaveStore.failInitialize=false;set("retryLoad",Boolean.TRUE);int resets=EntityPoseState.resets;
  check(ProgressSaveController.allowLoadStep()&&EntityPoseState.resets==resets+1,"entity retry flag not reset");
  check(ch.entities==0&&ch.loadClears==1&&ci.aZi==null&&ci.aZj==null&&ci.queued==0,"retry retained partial entities/actor references/commands");
  ch.entities=5;check(ProgressSaveController.allowLoadStep()&&ch.entities==5&&ch.loadClears==1,"successful following load step cleared reconstructed farm");
  check(!ProgressSaveController.isLoadBlocked(),"successful loader retry blocked");
 }
 static void transitionFailure()throws Exception{
  reset();ready();ProgressSaveController.frameFinished();SystemClock.now+=501;
  ci.state(18,20);ci.apA=2;ci.apz=37;ch.pending=7;ci.failWrite=true;
  ci.qG();
  check(ci.apA==2&&ci.apz==37&&ci.apB==1&&ci.qD()==20&&ci.clears==0,"failed transition consumed command/cleared RAM");
  check(ch.credits==7&&ch.pending==0,"pre-unload reward not settled");
  int attempts=ci.attempts;
  for(int i=0;i<100;i++){SystemClock.now++;ci.qG();ProgressSaveController.frameFinished();}
  check(ci.attempts==attempts&&ch.credits==7,"transition busy-retried or awarded twice");
  SystemClock.now+=8000;ci.failWrite=false;ci.legacyThrottle=true;ci.qG();
  check(ci.apA==-1&&ci.qD()==4&&ci.clears==1&&ci.storedCredits==7&&ci.forcedSeen,"transition retry did not durably flush before cleanup");
  int writes=ci.writes;SystemClock.now+=9000;ProgressSaveController.frameFinished();
  check(ci.writes==writes&&!ProgressSaveController.canSerialize(),"serialized cleared world at frame end");
  check(ch.credits==7,"cleanup duplicated settled reward");
 }
 static void predicted(int action,int target,boolean expected,int...stack){
  ci.state(stack);ci.apA=action;ci.apz=target;
  check(ci.pendingTransitionUnloadsWorld()==expected,"wrong removal boundary action="+action+" target="+target);
 }
 static void transitionLevelUp(){
  reset();ready();ProgressSaveController.frameFinished();SystemClock.now+=501;
  ci.apA=2;ci.apz=37;ch.pending=7;ch.levelUpOnSettle=true;ci.qG();
  check(ci.qD()==14&&ci.apB==1&&ci.apC[0]==18&&ci.clears==0,"settlement level-up callback was discarded or cleaned world");
  check(ci.storedCredits==7&&ci.storedPending==2,"level-up economy and new pending rewards not durable");
  check(ProgressSaveController.canSerialize(),"level-up push disarmed intact world");
 }
 static void transitionBoundaries(){
  predicted(0,4,false,18);predicted(1,-1,false,18,20);predicted(1,-1,true,18);
  predicted(2,37,true,18,20);predicted(2,37,false,1,20);
  predicted(3,20,true,18);predicted(3,20,false,18,20);
  predicted(4,18,false,18,20);predicted(4,1,true,1,18,20);
  predicted(4,99,true,18,20);predicted(5,-1,false,18,20,20);
  predicted(6,18,true,1,18);predicted(6,18,false,18,20);
  predicted(6,1,true,1,18,20);predicted(6,1,false,18,20);
  predicted(6,20,true,1,18,20);predicted(6,20,false,18,20,20);
  predicted(0,20,false,37);predicted(1,-1,true,37);predicted(2,18,true,37,20);
  predicted(4,37,false,37,20);predicted(4,1,true,1,37,20);
 }
 static void visitorSave(){
  reset();ready();ProgressSaveController.frameFinished();SystemClock.now+=501;
  ci.apA=2;ci.apz=37;ci.qG();check(!ProgressSaveController.canSerialize(),"own unload left writes armed");
  ci.aZB=1;ci.state(37);ProgressSaveController.enterWorld();ProgressSaveController.requestSave();
  SystemClock.now+=501;ProgressSaveController.frameFinished();
  check(ci.visitorWrites==1,"visitor entry did not rearm global-only save");
  SystemClock.now+=501;ci.apA=2;ci.apz=18;ci.qG();
  check(ci.visitorWrites==2&&ci.clears==2&&!ProgressSaveController.canSerialize(),"visitor unload not durably flushed/blocked");
  ci.aZB=0;ci.state(18);ProgressSaveController.farmLoadFinished();ProgressSaveController.enterWorld();
  SystemClock.now+=501;ProgressSaveController.frameFinished();
  check(ci.visitorWrites==2&&ProgressSaveController.canSerialize(),"return to own farm stayed blocked or used visitor serializer");
 }
 public static void main(String[]args)throws Exception{
  loaderAndTimer();failedSaveRetry();loadRetry();transitionFailure();transitionLevelUp();transitionBoundaries();visitorSave();
  System.out.println("PASS save barriers, timer, failure backoff, concurrent request, load retries, durable transitions and visitor mode");
 }
}
'''


class ProgressSaveControllerTest(unittest.TestCase):
    def test_actual_controller_and_transition(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        suffix = '.exe' if os.name == 'nt' else ''
        javac, java = home / 'bin' / ('javac' + suffix), home / 'bin' / ('java' + suffix)
        sdk = Path(os.environ.get('ANDROID_HOME', '/workspace/tooling/android-sdk'))
        jars = sorted((sdk / 'platforms').glob('*/android.jar'))
        if not javac.is_file() or not java.is_file() or not jars:
            self.skipTest('Requires installed JDK and Android SDK')
        source = ROOT / 'app/src/main/java' / PACKAGE
        entity = (source / 'ci.java').read_text()
        methods = '\n'.join(extract_method(entity, signature) for signature in
                            ('static int qD()', 'static boolean isPersistedWorldState(int state)',
                             'static boolean pendingTransitionUnloadsWorld()', 'static void qG()'))
        # The same own-farm reconstruction path is used after a visit as at
        # cold startup; flags must reset before lists/poses are reconstructed.
        own_load = entity.split('case 47:\n                cK("LOADING ENTITIES - GRID RESET");', 1)[1].split('case 48:', 1)[0]
        for helper in ('EntityPoseState', 'PendingRewardState'):
            self.assertLess(own_load.index(helper + '.resetForLoad()'), own_load.index('Fr();'))
            self.assertLess(own_load.index(helper + '.resetForLoad()'), own_load.index(helper + '.restoreSnapshot()'))
        for handler in ('static boolean jM(int i)', 'static boolean jP(int i)'):
            self.assertIn('ProgressSaveController.enterWorld();', extract_method(entity, handler))
        with tempfile.TemporaryDirectory(prefix='progress-save-check-') as temporary:
            folder = Path(temporary)
            clock = folder / 'android/os/SystemClock.java'
            clock.parent.mkdir(parents=True)
            clock.write_text('package android.os; public class SystemClock {public static long now;public static long uptimeMillis(){return now;}}')
            midlet = folder / 'javax/microedition/midlet/MIDlet.java'
            midlet.parent.mkdir(parents=True)
            midlet.write_text('package javax.microedition.midlet; public class MIDlet {public static android.app.Activity cnz,cnA;}')
            fixture, runner = folder / 'Fixture.java', folder / 'ProgressSaveChecks.java'
            fixture.write_text(FIXTURE + methods + '\n}')
            runner.write_text(RUNNER)
            result = subprocess.run([str(javac), '-source', '8', '-target', '8', '-cp', str(jars[-1]),
                                     '-d', str(folder), str(clock), str(midlet), str(fixture), str(runner),
                                     str(source / 'ProgressSaveController.java')], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), '-cp', os.pathsep.join((str(folder), str(jars[-1]))),
                                     PACKAGE.replace('/', '.') + '.ProgressSaveChecks'], capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS save barriers', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
