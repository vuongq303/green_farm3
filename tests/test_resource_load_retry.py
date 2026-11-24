"""Execute production load-step retry gating; no Android/gameplay emulation."""
from pathlib import Path
import subprocess
import tempfile
import unittest
import sys
from test_aa_tile_renderer import extract_method
ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory,qualified_package

class ResourceLoadRetryTest(unittest.TestCase):
    def test_failed_steps_never_advance_and_retry_is_bounded(self):
        engine=(core_source_directory()/'FarmGameEngine.java').read_text()
        signatures=['static void qR()', 'private static void resetResourceLoadRetry()', 'static void requestResourceLoadRetry()', 'private static boolean allowResourceLoadStep()', 'private static void resourceLoadFailed(int step, Exception failure, boolean temporary)', 'static void qV()']
        methods='\n'.join(extract_method(engine,s) for s in signatures)
        code='''package PACKAGE;
class SystemClock {static long now;static long uptimeMillis(){return now;}}
class GameRuntime {static class ResourceFailure extends RuntimeException {ResourceFailure(){super("asset=/11 stage=read index=7");}}}
class GameSaveStore {static boolean loading;static int begins;static boolean isLoading(){return loading;}static void beginLoading(){loading=true;begins++;}}
class ProgressSaveController {
 static boolean critical,allowed=true;static int storageErrors,resourceErrors;
 static boolean allowLoadStep(){return allowed;}
 static boolean isCriticalLoadFailure(int step,Exception e){return critical;}
 static void loadingFailed(int step,Exception e){storageErrors++;allowed=false;}
 static void showResourceLoadError(int step,Exception e){resourceErrors++;}
}
class FarmGameEngine {
 static int resourceFailureIndex=-1,resourceFailureAttempts,asj,ask=2,aso,apB,apF=6000;
 static long resourceRetryAt;static boolean resourceLoadBlocked,resourceRetryRequested,asm,asl,asn,asq;
 static int[] ash={1,2},apC={4};static String aND="ready";
 static int attempts,failures;static boolean unknown;
 static void fM(int step){attempts++;if(failures-->0){if(unknown)throw new IllegalStateException("bad phase");throw new GameRuntime.ResourceFailure();}}
 METHODS
}
public class LoadRetryProbe {
 static void check(boolean value,String message){if(!value)throw new AssertionError(message);}
 static void reset(){FarmGameEngine.ash=new int[]{1,2};FarmGameEngine.qR();FarmGameEngine.attempts=0;FarmGameEngine.failures=0;FarmGameEngine.unknown=false;ProgressSaveController.critical=false;ProgressSaveController.allowed=true;GameSaveStore.loading=false;GameSaveStore.begins=0;SystemClock.now=0;}
 public static void main(String[] args){
  reset();FarmGameEngine.failures=3;FarmGameEngine.qV();check(FarmGameEngine.asj==0,"failed asset advanced loader");
  for(int i=0;i<100;i++)FarmGameEngine.qV();check(FarmGameEngine.attempts==1,"backoff spun resource loader");
  SystemClock.now=250;FarmGameEngine.qV();SystemClock.now=750;FarmGameEngine.qV();
  check(FarmGameEngine.attempts==3&&ProgressSaveController.resourceErrors==1&&FarmGameEngine.asj==0,"retry budget/error gate invalid");
  SystemClock.now=100000;for(int i=0;i<100;i++)FarmGameEngine.qV();check(FarmGameEngine.attempts==3,"unbounded retry after exhaustion");
  FarmGameEngine.requestResourceLoadRetry();FarmGameEngine.qV();check(FarmGameEngine.asj==1&&GameSaveStore.begins==1,"explicit retry reset save/advanced wrong step");
  FarmGameEngine.qV();check(FarmGameEngine.asm,"loading never reached ready after successful repair");
  reset();FarmGameEngine.failures=1;FarmGameEngine.unknown=true;FarmGameEngine.qV();check(FarmGameEngine.asj==0&&FarmGameEngine.resourceLoadBlocked,"unknown error silently advanced loader");
  reset();FarmGameEngine.failures=1;FarmGameEngine.unknown=true;ProgressSaveController.critical=true;FarmGameEngine.qV();check(ProgressSaveController.storageErrors==1&&FarmGameEngine.asj==0,"storage error entered resource retry");
  reset();FarmGameEngine.failures=1;FarmGameEngine.qV();SystemClock.now=250;FarmGameEngine.qV();check(FarmGameEngine.asj==1,"temporary failure could not recover");
  reset();FarmGameEngine.ash[0]=48;FarmGameEngine.failures=1;FarmGameEngine.unknown=true;FarmGameEngine.qV();check(FarmGameEngine.asj==1,"optional tracking failure blocked game");
  reset();FarmGameEngine.ash[0]=127;FarmGameEngine.failures=1;FarmGameEngine.qV();check(FarmGameEngine.asj==0,"optional SDK phase swallowed real resource error");
  for(int reopen=0;reopen<5;reopen++){reset();FarmGameEngine.qV();FarmGameEngine.qV();check(FarmGameEngine.asm,"subsequent startup inherited blocked state");}
  System.out.println("PASS resource-step gating: no false readiness, backoff/3 attempts, manual retry, SQLite isolation, five new pipelines");
 }
}
'''.replace('PACKAGE',qualified_package()).replace('METHODS',methods)
        with tempfile.TemporaryDirectory() as tmp:
            folder=Path(tmp);java=folder/'LoadRetryProbe.java';java.write_text(code)
            log=folder/'android/util/Log.java';log.parent.mkdir(parents=True);log.write_text('package android.util;public class Log {public static int e(String a,String b,Throwable c){return 0;}public static int w(String a,String b,Throwable c){return 0;}}')
            result=subprocess.run([str(jdk_tool('javac')),'-d',tmp,str(java),str(log)],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stderr)
            result=subprocess.run([str(jdk_tool('java')),'-cp',tmp,qualified_package()+'.LoadRetryProbe'],capture_output=True,text=True,timeout=10)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr);print(result.stdout.strip())
