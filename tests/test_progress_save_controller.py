"""Execute production save/language controllers with deterministic Android peers.

The manual writer controls when detached snapshots become durable. No APK or
device is used; fixtures observe dialogs, alarms, and process termination.
"""
from pathlib import Path
import os
import subprocess
import tempfile
import unittest
import sys
import re

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import qualified_package, core_source_directory, kotlin_source_directory, type_name, adapt_fixture, legacy_fixture_names
PACKAGE = qualified_package().replace('.', '/')

ANDROID = {
    'android/os/SystemClock.java': '''package android.os; public class SystemClock {
        public static long now;public static long uptimeMillis(){return now;}
        public static long elapsedRealtime(){return now;} }''',
    'android/os/Build.java': '''package android.os;public class Build {
        public static class VERSION {public static int SDK_INT=28;} }''',
    'android/os/Process.java': '''package android.os;public class Process {
        public static int kills;public static int myPid(){return 7;}
        public static void killProcess(int pid){kills++;} }''',
    'android/view/KeyEvent.java': '''package android.view;public class KeyEvent {
        public static final int KEYCODE_MENU=82;public boolean longPress;public boolean isLongPress(){return longPress;} }''',
    'android/util/Log.java': '''package android.util;public class Log {
        public static int d(String a,String b){return 0;}
        public static int w(String a,String b,Throwable c){return 0;}
        public static int e(String a,String b,Throwable c){return 0;} }''',
    'android/content/Context.java': '''package android.content;public class Context {
        public static final String ALARM_SERVICE="alarm";
        public Object getSystemService(String name){return android.app.AlarmManager.instance;}
        public String getPackageName(){return "com.gameloft.android.ANMP.GloftGF2F";}
        public android.content.pm.PackageManager getPackageManager(){return new android.content.pm.PackageManager();} }''',
    'android/content/pm/PackageManager.java': '''package android.content.pm;public class PackageManager {
        public static boolean unavailable;public Object resolveActivity(android.content.Intent i,int f){return unavailable||i.component==null||!i.component.name.startsWith(i.component.packageName+".")?null:new Object();} }''',
    'android/content/ComponentName.java': '''package android.content;public class ComponentName {
        public final String packageName,name;public ComponentName(String p,String n){packageName=p;name=n;} }''',
    'android/content/Intent.java': '''package android.content;public class Intent {
        public static final String ACTION_MAIN="main",CATEGORY_LAUNCHER="launcher";
        public static final int FLAG_ACTIVITY_NEW_TASK=0x10000000,FLAG_ACTIVITY_CLEAR_TASK=0x8000;
        public ComponentName component;public int flags;public Intent(String action){}
        public Intent addCategory(String s){return this;}public Intent setComponent(ComponentName c){component=c;return this;}
        public Intent addFlags(int f){flags|=f;return this;} }''',
    'android/content/DialogInterface.java': '''package android.content;public interface DialogInterface {
        interface OnClickListener {void onClick(DialogInterface d,int which);}
        interface OnCancelListener {void onCancel(DialogInterface d);} }''',
    'android/app/Activity.java': '''package android.app;public class Activity extends android.content.Context {
        public boolean finishing;public boolean isFinishing(){return finishing;}
        public void runOnUiThread(Runnable r){r.run();} }''',
    'android/app/AlertDialog.java': '''package android.app;public class AlertDialog {
        public static Builder latest;public static class Builder {
            public String title,message;public boolean cancelable=true;
            public android.content.DialogInterface.OnClickListener positive,negative;
            public android.content.DialogInterface.OnCancelListener cancel;
            public Builder(Activity a){}public Builder setTitle(String s){title=s;return this;}
            public Builder setMessage(String s){message=s;return this;}
            public Builder setCancelable(boolean b){cancelable=b;return this;}
            public Builder setPositiveButton(String s,android.content.DialogInterface.OnClickListener l){positive=l;return this;}
            public Builder setNegativeButton(String s,android.content.DialogInterface.OnClickListener l){negative=l;return this;}
            public Builder setOnCancelListener(android.content.DialogInterface.OnCancelListener l){cancel=l;return this;}
            public AlertDialog show(){latest=this;return new AlertDialog();} } }''',
    'android/app/AlarmManager.java': '''package android.app;public class AlarmManager {
        public static final int ELAPSED_REALTIME_WAKEUP=2;public static AlarmManager instance=new AlarmManager();
        public static int exact,ordinary;public static boolean exactDenied,failAll;
        public void setExact(int t,long when,PendingIntent p){if(failAll)throw new IllegalStateException("alarm");if(exactDenied)throw new SecurityException("restricted");exact++;}
        public void set(int t,long when,PendingIntent p){if(failAll)throw new IllegalStateException("alarm");ordinary++;} }''',
    'android/app/PendingIntent.java': '''package android.app;public class PendingIntent {
        public static final int FLAG_CANCEL_CURRENT=0x10000000,FLAG_IMMUTABLE=0x4000000;
        public static android.content.Intent last;public static int flags;
        public static PendingIntent getActivity(android.content.Context c,int n,android.content.Intent i,int f){last=i;flags=f;return new PendingIntent();}
        public void cancel(){} }''',
    'com/quanhoang/greenfarm3/migration/SaveTransferGate.java': '''package com.quanhoang.greenfarm3.migration;
        public class SaveTransferGate {public static boolean preparing;
        public static boolean isPreparingTransfer(){return preparing;} }''',
    'com/quanhoang/greenfarm3/migration/SaveTransferActivity.java': '''package com.quanhoang.greenfarm3.migration;
        public class SaveTransferActivity {public static int launches;public static android.app.Activity last;
        public static void launch(android.app.Activity activity){launches++;last=activity;} }''',
    'android/widget/Toast.java': '''package android.widget;public class Toast {
        public static final int LENGTH_LONG=1;public static Toast makeText(android.content.Context c,String s,int n){return new Toast();}
        public void show(){} }''',
    'javax/microedition/midlet/MIDlet.java': '''package javax.microedition.midlet;public class MIDlet {
        public static android.app.Activity cnz,cnA;public static int keyDowns,keyUps;
        public boolean onKeyDown(int key,android.view.KeyEvent event){keyDowns++;return false;}
        public boolean onKeyUp(int key,android.view.KeyEvent event){keyUps++;return false;} }''',
}

FIXTURE = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;import android.view.KeyEvent;
class GameSaveStore {
 static final int STATUS_FAILED=3;static boolean ready,loading,failFinish,failInitialize,failReopen;
 static int initializes,reopens,begins,logicalLanguage,durableLanguage,durableCredits,durablePending,applies;
 static boolean capturing,serialized,whole;static Snapshot captured;static WriteDispatcher dispatcher;
 interface WriteDispatcher {void changed(Snapshot snapshot);}
 static final class Snapshot {
  final int language,credits,pending;final boolean gameplay,whole;
  Snapshot(int l,int c,int p,boolean g,boolean w){language=l;credits=c;pending=p;gameplay=g;whole=w;}
  int recordCount(){return gameplay?(whole?5:3):1;}long byteCount(){return 35;}
 }
 static void initialize(){initializes++;if(failInitialize)throw new IllegalStateException("open");ready=true;}
 static void beginLoading(){loading=true;begins++;}
 static void finishLoading(){if(failFinish)throw new IllegalStateException("finish");loading=false;}
 static void close(){ready=false;}static void reopen(){reopens++;if(failReopen)throw new IllegalStateException("reopen");ready=true;}
 static void setWriteDispatcher(WriteDispatcher d){dispatcher=d;}
 static boolean isCaptureAvailable(){return ready&&!loading;}static int status(){return 0;}
 static void beginCapture(boolean farm){if(!isCaptureAvailable())throw new IllegalStateException("not loaded");capturing=true;serialized=false;whole=farm;}
 static void record(){serialized=true;captured=new Snapshot(ci.aYU,ch.credits,ch.pending,true,whole);}
 static void setLanguageWriteOverride(Integer language){}
 static void overrideCapturedLanguage(int language){captured=new Snapshot(language,captured.credits,captured.pending,captured.gameplay,captured.whole);}
 static Snapshot finishCapture(){capturing=false;if(!serialized)throw new IllegalStateException("incomplete");logicalLanguage=captured.language;return captured;}
 static void abortCapture(){capturing=false;}
 static Snapshot captureLanguageSetting(int language){if(!ready)throw new IllegalStateException("no header");logicalLanguage=language;return new Snapshot(language,0,0,false,false);}
 static void apply(Snapshot s){applies++;durableLanguage=s.language;if(s.gameplay){durableCredits=s.credits;durablePending=s.pending;}}
}
class AsyncSaveQueue {
 interface RetryableCheckpoint {}
 interface Listener {void completed(Result result);}
 static final class Result {
  final long sequence,durationNanos=1;final Object checkpoint;final RuntimeException failure;
  Result(Ticket t,boolean fail){sequence=t.sequence;checkpoint=t.checkpoint;failure=fail?new IllegalStateException("disk"):null;}
 }
 static final class Ticket {final GameSaveStore.Snapshot snapshot;final long sequence;final Object checkpoint;
  Ticket(GameSaveStore.Snapshot s,long n,Object c){snapshot=s;sequence=n;checkpoint=c;}}
 static AsyncSaveQueue current;final Listener listener;final List<Ticket> pending=new ArrayList<Ticket>();
 long durable;int submissions;boolean reject,awaitCompletes;
 AsyncSaveQueue(Listener l){listener=l;current=this;}
 void submit(GameSaveStore.Snapshot s,long n,Object c){if(reject)throw new IllegalStateException("queue");submissions++;pending.add(new Ticket(s,n,c));}
 void complete(boolean fail){if(pending.isEmpty())throw new AssertionError("No pending write");Ticket t=pending.remove(0);
  if(!fail){GameSaveStore.apply(t.snapshot);durable=Math.max(durable,t.sequence);}
  else if(t.checkpoint==null||t.checkpoint instanceof RetryableCheckpoint)pending.add(0,t);
  listener.completed(new Result(t,fail));}
 boolean retryPaused;boolean isRetryPaused(){return retryPaused;}void retryPending(){retryPaused=false;}
 boolean isIdle(){return pending.isEmpty();}long durableSequence(){return durable;}
 boolean awaitIdle(long timeout){if(awaitCompletes)while(!pending.isEmpty())complete(false);return isIdle();}void close(){}
}
class PendingRewardState {static int resets;static void resetForLoad(){resets++;}}
class EntityPoseState {static int resets;static void resetForLoad(){resets++;}}
class TutorialFlowController {static int invalidations;static void invalidateSession(){invalidations++;}}
class GloftGF2F extends javax.microedition.midlet.MIDlet {static GloftGF2F acx;static ci acw;void mj(){} /* ACTUAL_KEY_OVERRIDES */}
class GLLib {static void lb(){}static void la(){}}
class ch {static int pending,credits,settles,entities,loadClears;static boolean levelUpOnSettle;
 static void oM(){settles++;credits+=pending;pending=0;if(levelUpOnSettle){levelUpOnSettle=false;pending=2;ci.apA=0;ci.apz=14;ci.baa=true;}}
 static void oL(){entities=0;loadClears++;}}
class ColdEngineInitProbe {static int initializations;static boolean failInitialization=true;}
class ci {
 static {ColdEngineInitProbe.initializations++;if(ColdEngineInitProbe.failInitialization)
  throw new IllegalStateException("Engine initialized without its MIDlet");}
 static int avD=-1,aZB,attempts,visitorWrites,cancels,clears,queued,aYU,aOD,auy,committedCallbacks,aYR=-1,aYS=-1;
 static int aAG,aAH,aAI,aAJ,aAK,aZp;static Object avE,baf,aZn;static ch aZi,aZj;
 static boolean baa,bab,skipWrite,throwCapture,forcedSeen,aAi,aAj,awV;
 static int apA=-1,apz=-1,apB=-1,auN,asi;static int[]apC=new int[32],apV=new int[128];
 static void state(int...stack){apB=stack.length-1;System.arraycopy(stack,0,apC,0,stack.length);}
 static void FN(){queued=0;}static void cancelMovePreviewForInterruption(){cancels++;avD=-1;avE=null;}
 static void av(boolean ignored){attempts++;forcedSeen=ProgressSaveController.isForcedCheckpoint();
  if(throwCapture)throw new IllegalStateException("serializer");if(!skipWrite)GameSaveStore.record();}
 static void rs(){visitorWrites++;av(false);}
 static boolean fJ(int action){if(action==5&&isPersistedWorldState(qD())){ch.oM();clears++;}return false;}
 static void gD(int state){}static void gE(int state){}
 static void progressSaveCommitted(){committedCallbacks++;}
 static void fE(int next){apz=next;apA=0;}
 static void qB(){state(18);apA=-1;}
 static void showGameNotification(String title,String message){
  state(20);apA=-1;
  new android.app.AlertDialog.Builder(new android.app.Activity()).setTitle(title).setMessage(message)
   .setPositiveButton(GameNotification.positiveLabel(),(d,w)->GameNotification.choose(true))
   .setNegativeButton(GameNotification.negativeLabel(),(d,w)->GameNotification.choose(false))
   .setOnCancelListener(d->GameNotification.choose(false)).show();
 }
'''

RUNNER = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import android.os.SystemClock;import android.app.*;import javax.microedition.midlet.MIDlet;import java.lang.reflect.*;
public class ProgressSaveChecks {
 static void check(boolean b,String m){if(!b)throw new AssertionError(m);}
 static void set(Class<?> c,String name,Object value)throws Exception{Field f=c.getDeclaredField(name);f.setAccessible(true);f.set(null,value);}
 static AsyncSaveQueue queue(){return AsyncSaveQueue.current;}
 static void reset()throws Exception{
  if(AsyncSaveQueue.current!=null)AsyncSaveQueue.current.pending.clear();
  GameSaveStore.failFinish=GameSaveStore.failReopen=GameSaveStore.failInitialize=false;
  GameSaveStore.initializes=GameSaveStore.reopens=GameSaveStore.begins=GameSaveStore.applies=0;
  GameSaveStore.logicalLanguage=GameSaveStore.durableLanguage=GameSaveStore.durableCredits=GameSaveStore.durablePending=0;
  ci.attempts=ci.visitorWrites=ci.cancels=ci.clears=ci.committedCallbacks=0;ci.baa=ci.bab=ci.skipWrite=ci.throwCapture=ci.forcedSeen=false;
  ci.avD=-1;ci.aZB=0;ci.aYU=ci.aOD=ci.auy=0;ci.avE=null;ci.aZi=ci.aZj=null;ci.queued=0;ci.apA=ci.apz=-1;ci.state(18);
  ci.aYR=ci.aYS=-1;ci.awV=false;GloftGF2F.acw=new ci();MIDlet.keyDowns=MIDlet.keyUps=0;
  ch.pending=ch.credits=ch.settles=ch.entities=ch.loadClears=0;ch.levelUpOnSettle=false;SystemClock.now=10000;
  MIDlet.cnz=MIDlet.cnA=null;AlertDialog.latest=null;AlarmManager.exact=AlarmManager.ordinary=android.os.Process.kills=0;
  com.quanhoang.greenfarm3.migration.SaveTransferActivity.launches=0;com.quanhoang.greenfarm3.migration.SaveTransferActivity.last=null;
  com.quanhoang.greenfarm3.migration.SaveTransferGate.preparing=false;
  AlarmManager.failAll=AlarmManager.exactDenied=false;android.content.pm.PackageManager.unavailable=false;
  set(LanguageRestartController.class,"restartState",0);set(LanguageRestartController.class,"restartPromptVisible",false);set(LanguageRestartController.class,"restartHandoff",null);
  set(LanguageRestartController.class,"menuKeyHeld",false);set(LanguageRestartController.class,"menuOpenRequested",false);
  set(GameNotification.class,"decision",null);
  ProgressSaveController.initialize();
 }
 static void ready(){ProgressSaveController.globalLoadFinished(false,false);ProgressSaveController.farmLoadFinished();ProgressSaveController.enterWorld();}
 static void frame(long elapsed){SystemClock.now+=elapsed;ProgressSaveController.frameFinished();}
 static void complete(boolean fail){queue().complete(fail);ProgressSaveController.frameFinished();}
 static void readySaved()throws Exception{reset();ready();frame(0);frame(500);check(queue().pending.size()==1,"initial batch not captured");complete(false);}
 static void finishPending(){for(int i=0;i<20&&!queue().isIdle();i++)complete(false);check(queue().isIdle(),"writer failed to quiesce");}
 static void actionMatrixAndCriticalFlush()throws Exception{
  for(int count:new int[]{1,10,50,100}){
   readySaved();int before=ci.attempts;
   for(int i=1;i<=count;i++){ch.credits=i;ProgressSaveController.requestSave();frame(10);}
   frame(500);finishPending();
   check(GameSaveStore.durableCredits==count,"accepted action state lost");
   check(ci.attempts-before<=(count+49)/50+1,"continuous actions serialized once per operation");
  }
  readySaved();ch.credits=83;ProgressSaveController.requestCriticalSave();frame(0);
  check(!queue().isIdle()&&GameSaveStore.durableCredits!=83,"critical save waited for debounce or wrote on game thread");
  finishPending();check(GameSaveStore.durableCredits==83,"critical checkpoint lost");
  ch.credits=91;ProgressSaveController.requestSave();int applied=GameSaveStore.applies;
  ProgressSaveController.requestLifecycleFlush();
  check(!queue().isIdle()&&GameSaveStore.applies==applied,"Android lifecycle waited for SQLite");
  finishPending();check(GameSaveStore.durableCredits==91,"lifecycle snapshot lost");
  System.out.println("PASS 1/10/50/100 accepted-state batches, critical debit bypass and nonblocking lifecycle capture");
 }
 static void retryNoticePreservesExistingQuestDialog()throws Exception{
  readySaved();queue().retryPaused=true;ci.state(20);frame(0);
  check(!GameNotification.isVisible(),"storage notice replaced the existing quest/tutorial dialog");
  ci.state(18);frame(0);
  check(GameNotification.isVisible(),"storage notice did not appear after the quest closed");
  GameNotification.choose(true);check(!queue().retryPaused,"explicit retry did not resume the writer");
 }
 static void loaderBatchAndTimer()throws Exception{
  reset();ProgressSaveController.requestSave();frame(500);check(ci.attempts==0,"saved before global load");
  ProgressSaveController.globalLoadFinished(false,false);ProgressSaveController.enterWorld();frame(500);check(ci.attempts==0,"farm load barrier bypassed");
  ProgressSaveController.farmLoadFinished();ProgressSaveController.enterWorld();frame(0);frame(499);
  check(ci.attempts==0,"batch wrote before debounce");frame(1);check(ci.attempts==1&&GameSaveStore.applies==0,"capture did SQLite synchronously or was missing");
  check(ci.committedCallbacks==0,"legacy save effects ran before durable ack");
  check(ProgressSaveController.hasPendingChanges(),"pending writer invisible");complete(false);check(!ProgressSaveController.hasPendingChanges(),"durable ack not consumed");
  for(int i=0;i<100;i++)frame(1);check(ci.attempts==1&&ci.committedCallbacks==1,"per-frame capture or duplicate legacy callback");frame(7900);check(ci.attempts==2,"periodic checkpoint missing");complete(false);
  ci.avD=3;ProgressSaveController.requestSave();frame(0);frame(501);check(ci.attempts==2,"captured unconfirmed move");
  ci.avD=-1;ci.state(4);frame(1);check(ci.attempts==2,"captured partial loader");ci.state(18);frame(1);check(ci.attempts==3,"dirty save lost behind barrier");finishPending();
 }
 static void continuousBatchAndNewMutation()throws Exception{
  readySaved();int before=ci.attempts;for(int i=0;i<21;i++){ci.baa=true;frame(100);}
  check(ci.attempts==before+1,"continuous operations exceeded 2000ms maximum wait");finishPending();
  ProgressSaveController.requestSave();frame(0);frame(500);int captured=ci.attempts;int callbacks=ci.committedCallbacks;
  ci.bab=true;frame(1);complete(false);check(ProgressSaveController.hasPendingChanges(),"new mutation acknowledged by older commit");
  check(ci.committedCallbacks==callbacks,"legacy callback ignored newer dirty gameplay");
  frame(500);check(ci.attempts==captured+1,"new mutation not captured after older ack");finishPending();
 }
 static void immutableSnapshotAndFailure()throws Exception{
  readySaved();ch.credits=7;ch.pending=3;ci.baa=true;frame(0);frame(500);ch.credits=19;ch.pending=5;complete(false);
  check(GameSaveStore.durableCredits==7&&GameSaveStore.durablePending==3,"writer observed live RAM");
  ci.baa=true;frame(0);frame(500);int captures=ci.attempts;complete(true);
  for(int i=0;i<20;i++)frame(10);check(ci.attempts==captures,"SQLite failure caused per-frame serialization");
  complete(false);check(GameSaveStore.durableCredits==19,"absolute retry lost detached values");
  ci.skipWrite=true;check(!ProgressSaveController.flushForLifecycle(),"declined serializer became durable save");
  ci.skipWrite=false;queue().awaitCompletes=true;check(ProgressSaveController.flushForLifecycle()&&ci.forcedSeen,"lifecycle flush failed to await durability");
 }
 static void loadRetry()throws Exception{
  reset();GameSaveStore.failFinish=true;ready();check(ProgressSaveController.isLoadBlocked(),"load completion failure unblocked");
  set(ProgressSaveController.class,"retryLoad",true);GameSaveStore.failFinish=false;frame(0);frame(500);finishPending();check(!ProgressSaveController.isLoadBlocked(),"load retry deadlocked");
  reset();ProgressSaveController.loadingFailed(47,new Exception("decode"));ch.entities=5;ci.aZi=new ch();ci.aZj=new ch();ci.queued=3;
  GameSaveStore.failInitialize=true;set(ProgressSaveController.class,"retryLoad",true);check(!ProgressSaveController.allowLoadStep()&&ch.entities==5&&ch.loadClears==0,"failed reopen discarded RAM");
  GameSaveStore.failInitialize=false;set(ProgressSaveController.class,"retryLoad",true);int resets=EntityPoseState.resets;
  check(ProgressSaveController.allowLoadStep()&&EntityPoseState.resets==resets+1,"loader retry did not reset decoder");check(ch.entities==0&&ci.aZi==null&&ci.aZj==null&&ci.queued==0,"retained partial entities");
 }
 static void loadRetryWaitsForWriter()throws Exception{
  readySaved();ProgressSaveController.requestSave();frame(0);frame(500);
  ProgressSaveController.loadingFailed(-1,new Exception("finish"));set(ProgressSaveController.class,"retryLoad",true);frame(0);
  check(ProgressSaveController.isLoadBlocked(),"load retry bypassed pending writer");complete(false);
  check(!ProgressSaveController.isLoadBlocked(),"retry click was discarded while writer was busy");
 }
 static void transitions()throws Exception{
  readySaved();ci.state(18,20);ci.apA=2;ci.apz=37;ch.pending=7;ci.qG();
  check(ci.apA==2&&ci.apz==37&&ci.clears==0,"unload consumed command before ack");check(ch.credits==7&&ch.pending==0,"reward not settled before capture");
  complete(true);int attempts=ci.attempts;ci.qG();check(ci.clears==0&&ci.attempts==attempts&&ch.credits==7,"failed unload recaptured or awarded twice");
  complete(false);ci.qG();check(ci.apA==-1&&ci.qD()==4&&ci.clears==1,"durable unload did not resume");check(GameSaveStore.durableCredits==7&&!ProgressSaveController.canSerialize(),"cleared world writable");
  frame(9000);check(ci.attempts==attempts,"serialized cleared world");
  readySaved();ci.apA=2;ci.apz=37;ch.pending=7;ch.levelUpOnSettle=true;ci.qG();
  check(ci.qD()==18&&ci.apA==0&&ci.clears==0,"settlement callback did not wait");complete(false);ci.qG();
  check(ci.qD()==14&&ci.apC[0]==18&&ci.clears==0,"level-up callback discarded or world cleaned");check(GameSaveStore.durableCredits==7&&GameSaveStore.durablePending==2,"level-up snapshot incomplete");
 }
 static void startLanguage(int language){MIDlet.cnz=new Activity();LanguageRestartController.select(language);
  check(LanguageRestartController.frameStarted(),"accepted choice did not freeze frame");}
 static void accept(){AlertDialog.latest.positive.onClick(null,1);LanguageRestartController.frameStarted();}
 static void languageCancelAndRestart()throws Exception{
  readySaved();int before=ci.attempts;startLanguage(16);
  check(AlertDialog.latest==null,"success popup shown before durability");
  check(ci.aYU==0&&android.os.Process.kills==0,"language changed before SQLite ack");finishPending();
  check(AlertDialog.latest.message.startsWith("The language setting has been saved"),"popup not in current language");
  check(GameSaveStore.durableLanguage==16&&ci.aYU==0&&android.os.Process.kills==0,"deferred locale or progress lost");
  AlertDialog.latest.negative.onClick(null,-1);
  check(!LanguageRestartController.blocksGameplay()&&ci.aYU==0,"Later changed live assets or froze session");
  ch.credits=31;ci.baa=true;frame(0);frame(500);finishPending();
  check(GameSaveStore.durableLanguage==16&&GameSaveStore.durableCredits==31,"later gameplay overwrote selected language");
  startLanguage(0);finishPending();
  check(GameSaveStore.durableLanguage==0&&ci.aYU==0&&!LanguageRestartController.blocksGameplay()
    &&!GameNotification.isVisible()&&android.os.Process.kills==0,"choosing current locale failed to cancel deferred language");
  startLanguage(16);finishPending();accept();accept();
  check(GameSaveStore.durableLanguage==16&&android.os.Process.kills==1,"restart did not happen once");
  check(AlarmManager.exact==1&&PendingIntent.last.component.name.endsWith(".Start"),"restart target invalid");
  check((PendingIntent.flags&PendingIntent.FLAG_IMMUTABLE)!=0,"pending intent mutable");
 }
 static void languageFailureRetry()throws Exception{
  readySaved();int epoch=TutorialFlowController.invalidations;startLanguage(16);complete(true);finishPending();
  check(ci.aYU==0&&!LanguageRestartController.blocksGameplay()&&android.os.Process.kills==0,"failure changed session");
  check(TutorialFlowController.invalidations==epoch,"failure invalidated tutorial modal");
  check(AlertDialog.latest.message.startsWith("Could not save progress"),"save error not in old language");
  accept();finishPending();AlarmManager.exactDenied=true;accept();
  check(GameSaveStore.durableLanguage==16&&AlarmManager.ordinary==1&&android.os.Process.kills==1,"retry/alarm fallback failed");
 }
 static void languageLateMutation()throws Exception{
  readySaved();startLanguage(16);ch.credits=17;ci.baa=true;complete(false);
  check(ci.aYU==0&&android.os.Process.kills==0&&!queue().isIdle(),"late mutation skipped");finishPending();
  check(GameSaveStore.durableCredits==17&&GameSaveStore.durableLanguage==16,"late gameplay not durable");accept();
  check(android.os.Process.kills==1,"restart missing after acknowledgement");
 }
 static void languageHeaderAndHandoff()throws Exception{
  reset();ci.state(26);GameSaveStore.durableCredits=91;startLanguage(16);
  check(!queue().pending.get(0).snapshot.gameplay&&ci.attempts==0,"pre-world language invented gameplay");finishPending();
  check(ci.aYU==0&&GameSaveStore.durableLanguage==16&&GameSaveStore.durableCredits==91,"header-only change reset gameplay");
  accept();readySaved();startLanguage(16);finishPending();AlarmManager.failAll=true;accept();
  check(ci.aYU==0&&GameSaveStore.durableLanguage==16&&android.os.Process.kills==0,"handoff failure damaged saved/live setting");
  check(AlertDialog.latest.message.startsWith("Your progress has been saved"),"handoff error reported save failure");
  int captures=ci.attempts;AlarmManager.failAll=false;accept();
  check(ci.attempts==captures&&android.os.Process.kills==1,"handoff retry repeated gameplay save");
 }
 static void languageLateCaptureFailure()throws Exception{
  readySaved();startLanguage(16);ch.credits=17;ci.baa=true;ci.throwCapture=true;complete(false);
  check(android.os.Process.kills==0&&ci.aYU==0,"failed recapture restarted or changed RAM language");ci.throwCapture=false;finishPending();
  check(GameSaveStore.logicalLanguage==0&&GameSaveStore.durableLanguage==0,"failed recapture retained canceled language");
 }
 static void languageCommittedPreferenceRollback()throws Exception{
  readySaved();startLanguage(16);complete(false);
  check(GameSaveStore.durableLanguage==0&&!queue().isIdle(),"preference changed before gameplay was durable");
  ch.credits=29;ci.baa=true;ci.throwCapture=true;complete(false);
  check(GameSaveStore.durableLanguage==16&&ci.aYU==0&&LanguageRestartController.blocksGameplay(),"failed post-preference capture falsely promised old setting");
  check(android.os.Process.kills==0&&!queue().isIdle(),"failed post-preference capture did not enqueue compensation");
  complete(true);check(LanguageRestartController.blocksGameplay()&&GameSaveStore.durableLanguage==16,"failed rollback unlocked old-language session before durability");
  ci.throwCapture=false;complete(false);check(GameSaveStore.durableLanguage==0&&ci.aYU==0&&!LanguageRestartController.blocksGameplay(),"durable compensation did not restore old session");
  check(AlertDialog.latest.message.startsWith("Could not save progress"),"rollback did not deliver retry after durability");
 }
 static void languageRejectedInitialPreference()throws Exception{
  reset();ci.state(26);queue().reject=true;startLanguage(16);
  check(ci.aYU==0&&GameSaveStore.durableLanguage==0&&android.os.Process.kills==0,"rejected initial preference changed persisted session");
  queue().reject=false;frame(1000);finishPending();
  check(GameSaveStore.logicalLanguage==0,"rejected preference left new language in logical cache");
 }
 static void tutorialHardwareOptions()throws Exception{
  readySaved();ci.state(19);ci.aYR=16;GloftGF2F game=new GloftGF2F();android.view.KeyEvent event=new android.view.KeyEvent();
  int captures=ci.attempts,credits=ch.credits;
  check(game.onKeyDown(82,event)&&game.onKeyDown(82,event)&&game.onKeyUp(82,event),"tutorial MENU pair was not consumed");
  check(ci.apA==-1&&ci.attempts==captures,"hardware callback mutated game stack or serialized");
  check(!LanguageRestartController.frameStarted()&&ci.apA==0&&ci.apz==15,"game thread did not enqueue normal Options");
  check(ci.aYR==16&&ch.credits==credits&&ci.attempts==captures,"Options shortcut changed tutorial, economy, or persistence");
  ci.qG();check(ci.qD()==15&&ci.qE()==19,"Options push discarded tutorial parent");
  ci.fE(5);ci.qG();check(ci.qD()==5&&ci.qE()==15,"normal Language picker lacks Options parent for Back");
  check(MIDlet.keyDowns==0&&MIDlet.keyUps==0,"consumed MENU leaked to original game key route");
  check(!game.onKeyDown(4,event)&&!game.onKeyUp(4,event)&&MIDlet.keyDowns==1&&MIDlet.keyUps==1,"ordinary key did not delegate exactly once");
  readySaved();game=new GloftGF2F();ci.aYR=-1;
  check(!game.onKeyDown(82,event)&&!game.onKeyUp(82,event)&&MIDlet.keyDowns==1&&MIDlet.keyUps==1,"non-tutorial MENU changed original behavior");
  ci.aYR=12;ci.avD=3;game.onKeyDown(82,event);game.onKeyUp(82,event);LanguageRestartController.frameStarted();check(ci.apA==-1,"MENU interrupted an unconfirmed move");
  ci.avD=-1;ci.apA=1;game.onKeyDown(82,event);game.onKeyUp(82,event);LanguageRestartController.frameStarted();check(ci.apA==1,"MENU replaced a pending transition");
  ci.apA=-1;ci.aZB=1;game.onKeyDown(82,event);game.onKeyUp(82,event);LanguageRestartController.frameStarted();check(ci.apA==-1,"MENU opened own-farm options while visiting");
  ci.aZB=0;check(game.onKeyDown(82,event)&&game.onKeyUp(82,event),"eligible MENU request missing");ci.avD=3;
  LanguageRestartController.frameStarted();check(ci.apA==-1,"queued MENU bypassed game-thread eligibility recheck");
 }
 static void saveTransferFreeze()throws Exception{
  readySaved();ci.aYR=16;ci.state(19);
  int captures=ci.attempts,credits=ch.credits,submissions=queue().submissions;
  com.quanhoang.greenfarm3.migration.SaveTransferGate.preparing=true;
  check(LanguageRestartController.blocksGameplay(),"save transfer did not block gameplay input");
  for(int frame=0;frame<5;frame++)check(LanguageRestartController.frameStarted(),"save transfer did not stop game frame updates");
  check(ci.apA==-1&&ci.aYR==16&&ch.credits==credits&&ci.attempts==captures&&queue().submissions==submissions,
    "freeze hook changed gameplay or duplicated checkpoint while transfer owns it");
  com.quanhoang.greenfarm3.migration.SaveTransferGate.preparing=false;
  check(!LanguageRestartController.blocksGameplay()&&!LanguageRestartController.frameStarted(),"transfer completion did not restore ordinary session");
 }
 static void saveTransferLongPress()throws Exception{
  readySaved();GloftGF2F game=new GloftGF2F();ci.aYR=16;android.view.KeyEvent event=new android.view.KeyEvent();event.longPress=true;
  Activity actualActivity=new Activity();MIDlet.cnA=actualActivity;MIDlet.cnz=new Activity();
  int captures=ci.attempts;
  check(game.onKeyDown(82,event),"long MENU not consumed for save transfer");
  check(com.quanhoang.greenfarm3.migration.SaveTransferActivity.launches==1
    &&com.quanhoang.greenfarm3.migration.SaveTransferActivity.last==actualActivity,"save transfer used unattached MIDlet or wrong Activity");
  check(!LanguageRestartController.frameStarted()&&ci.apA==-1&&ci.attempts==captures,"long MENU also opened tutorial Options or serialized during key callback");
  actualActivity.finishing=true;ci.aYR=-1;
  check(!game.onKeyDown(82,event)&&com.quanhoang.greenfarm3.migration.SaveTransferActivity.launches==1,"finishing Activity opened migration");
  MIDlet.cnA=null;check(!game.onKeyDown(82,event),"missing real Activity did not use original key route");
  check(!game.onKeyDown(4,event)&&com.quanhoang.greenfarm3.migration.SaveTransferActivity.launches==1,"non MENU long press opened migration");
  check(!game.onKeyDown(82,null),"null KeyEvent changed non-tutorial route");
 }
 static void coldNoWriter(){
  check(ColdEngineInitProbe.initializations==0,"cold fixture initialized engine before flush");
  check(ProgressSaveController.flushForLifecycle(),"cold process with no writer rejected no-op flush");
  check(ColdEngineInitProbe.initializations==0,"cold no-writer flush initialized engine without MIDlet");
  check(GameSaveStore.initializes==0,"cold no-writer flush initialized storage");
  System.out.println("PASS cold no-writer lifecycle flush: engine class was not initialized without MIDlet");
 }
 public static void main(String[]args)throws Exception{
  if(args.length>0&&args[0].equals("coldNoWriter")){coldNoWriter();return;}
  ColdEngineInitProbe.failInitialization=false;
  loaderBatchAndTimer();continuousBatchAndNewMutation();immutableSnapshotAndFailure();loadRetry();loadRetryWaitsForWriter();transitions();
  retryNoticePreservesExistingQuestDialog();actionMatrixAndCriticalFlush();languageCancelAndRestart();languageFailureRetry();languageLateMutation();languageHeaderAndHandoff();languageLateCaptureFailure();languageCommittedPreferenceRollback();languageRejectedInitialPreference();tutorialHardwareOptions();saveTransferLongPress();saveTransferFreeze();
  System.out.println("PASS actual save/language controllers: batching, detached snapshots, durable transitions, language cancel/retry, late mutations and restart handoff");
 }
}
'''


class ProgressSaveControllerTest(unittest.TestCase):
    def test_actual_controllers_and_transition(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        suffix = '.exe' if os.name == 'nt' else ''
        javac, java = home / 'bin' / ('javac' + suffix), home / 'bin' / ('java' + suffix)
        if not javac.is_file() or not java.is_file():
            self.skipTest('Requires installed JDK for isolated Java fixtures')
        source = core_source_directory()
        entity = legacy_fixture_names((source / (type_name('ci') + '.java')).read_text(encoding='utf-8'))
        methods = '\n'.join(extract_method(entity, signature) for signature in
                            ('static int qD()', 'static int qE()', 'static boolean isPersistedWorldState(int state)',
                             'static boolean pendingTransitionUnloadsWorld()', 'static void qG()'))
        kotlin_source = kotlin_source_directory()
        kotlin_midlet = kotlin_source / 'GloftGF2F.kt'
        if kotlin_midlet.is_file():
            midlet_source = kotlin_midlet.read_text(encoding='utf-8')
            key_methods = '\n'.join(extract_method(midlet_source, signature) for signature in
                                    ('override fun onKeyDown(', 'override fun onKeyUp('))
        else:
            midlet_source = (source / 'GloftGF2F.java').read_text(encoding='utf-8')
            key_methods = '\n'.join(extract_method(midlet_source, signature) for signature in
                                    ('public boolean onKeyDown(int keyCode, KeyEvent event)',
                                     'public boolean onKeyUp(int keyCode, KeyEvent event)'))
        with tempfile.TemporaryDirectory(prefix='progress-save-check-') as temporary:
            folder = Path(temporary)
            peers = []
            for relative, content in ANDROID.items():
                peer = folder / relative
                peer.parent.mkdir(parents=True, exist_ok=True)
                peer.write_text(adapt_fixture(content).replace('com.gameloft.android.ANMP.GloftGF2F', qualified_package().rsplit('.', 1)[0]), encoding='utf-8')
                peers.append(peer)
            fixture, runner = folder / 'Fixture.java', folder / 'ProgressSaveChecks.java'
            actual = [kotlin_source / name for name in ('ProgressSaveController.kt', 'SaveBatchPolicy.kt', 'LanguageRestartController.kt', 'GameNotification.kt')]
            fixture_text = FIXTURE
            if kotlin_midlet.is_file():
                # Isolate only the two unchanged production override bodies,
                # just as the previous Java fixture did; peers are not gameplay.
                fixture_text = re.sub(r'(?m)^class GloftGF2F .*$', '', fixture_text)
                midlet_peer = folder / 'GloftGF2FKeyPeer.kt'
                midlet_peer.write_text(adapt_fixture('package ' + PACKAGE.replace('/', '.') + '\n' +
                    'import android.view.KeyEvent\nimport javax.microedition.midlet.MIDlet\nimport com.quanhoang.greenfarm3.migration.SaveTransferActivity\n' +
                    'class GloftGF2F : MIDlet() { fun mj() {}\n' + key_methods + '\n' +
                    'companion object { @JvmField var acx: GloftGF2F? = null; @JvmField var acw: ci? = null } }'),
                    encoding='utf-8')
                actual.append(midlet_peer)
                # The Kotlin ABI field acw exposes ci. Match the production
                # public engine type rather than an inaccessible peer type.
                fixture_text, ci_body = fixture_text.split('class ci {', 1)
                ci_peer = folder / (type_name('ci') + '.java')
                ci_peer.write_text(adapt_fixture('package ' + PACKAGE.replace('/', '.') + ';\n' +
                    'public class ci {' + ci_body + methods + '\n}'), encoding='utf-8')
                peers.append(ci_peer)
            else:
                fixture_text = fixture_text.replace('/* ACTUAL_KEY_OVERRIDES */', key_methods) + methods + '\n}'
            fixture.write_text(adapt_fixture(fixture_text), encoding='utf-8')
            runner.write_text(adapt_fixture(RUNNER), encoding='utf-8')
            compile_mixed(actual, [*peers, fixture, runner], folder)
            cold = subprocess.run([str(java), '-cp', runtime_classpath(folder),
                                   PACKAGE.replace('/', '.') + '.ProgressSaveChecks', 'coldNoWriter'],
                                  capture_output=True, text=True, timeout=20)
            self.assertEqual(cold.returncode, 0, cold.stdout + cold.stderr)
            self.assertIn('PASS cold no-writer lifecycle flush', cold.stdout)
            print(cold.stdout.strip())
            result = subprocess.run([str(java), '-cp', runtime_classpath(folder), PACKAGE.replace('/', '.') + '.ProgressSaveChecks'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual save/language controllers', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
