"""Production MIDlet bootstrap + Start resume control flow with deterministic peers."""
from pathlib import Path
import subprocess
import tempfile
import unittest
import sys
from test_aa_tile_renderer import extract_method
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed,runtime_classpath

class StartupLifecycleTest(unittest.TestCase):
    def test_failed_initialization_can_retry_without_duplicate_engine(self):
        root=ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
        method=extract_method((root/'GloftGF2F.kt').read_text(),'public override fun mj()')
        kotlin='''package probe
open class MIDlet {open fun mj(){};fun jf(s:String){};companion object{@JvmField var cnA:Any?=Any()}}
object WrapperDeviceContext {fun getContext():Any?=Any()}
object SaveTransferGate {fun enterGame(c:Any):Boolean=true}
class GloftGF2F:MIDlet(){init{acx=this}
 METHOD
 companion object{@JvmField var acw:FarmGameEngine?=null;@JvmField var acx:GloftGF2F?=null}
}
'''.replace('METHOD',method)
        peers='''package probe;
class GameRuntime {static FarmGameEngine QD;}
class ProgressSaveController {static int storageErrors,engineErrors,opens;static boolean failStorage;
 static void initialize(){opens++;if(failStorage)throw new IllegalStateException("db");}
 static void showStartupError(){storageErrors++;}static void showEngineStartupError(){engineErrors++;}}
class FarmGameEngine {
 static boolean failConstructor,failInit;static int constructed,initialized,invalidated;boolean failed;
 FarmGameEngine(Object m,Object d){GameRuntime.QD=this;constructed++;if(failConstructor)throw new IllegalStateException("constructor");}
 void Init(){if(GloftGF2F.acw!=this)throw new AssertionError("bootstrap ABI not published");if(failInit)throw new IllegalStateException("Init");initialized++;}
 void markInitializationFailed(){failed=true;invalidated++;}
}
public class StartupProbe {
 static void check(boolean v,String s){if(!v)throw new AssertionError(s);}
 static void reset(){GloftGF2F.acw=null;FarmGameEngine.failConstructor=FarmGameEngine.failInit=ProgressSaveController.failStorage=false;}
 public static void main(String[]args)throws Exception{
  reset();GloftGF2F first=new GloftGF2F();ProgressSaveController.failStorage=true;first.mj();
  check(GloftGF2F.acw==null&&FarmGameEngine.constructed==0,"engine used unavailable SQLite");
  ProgressSaveController.failStorage=false;FarmGameEngine.failConstructor=true;first.mj();
  check(GloftGF2F.acw==null&&GameRuntime.QD.failed,"constructor failure was left active");
  FarmGameEngine.failConstructor=false;FarmGameEngine.failInit=true;first.mj();FarmGameEngine broken=GameRuntime.QD;
  check(GloftGF2F.acw==null&&broken.failed,"Init failure poisoned singleton");
  FarmGameEngine.failInit=false;first.mj();check(GloftGF2F.acw!=null&&GloftGF2F.acw!=broken&&!GloftGF2F.acw.failed,"retry did not initialize a fresh candidate");
  int count=FarmGameEngine.constructed;first.mj();check(count==FarmGameEngine.constructed,"repeated resume duplicated active engine");
  reset();GloftGF2F second=new GloftGF2F();java.util.concurrent.CountDownLatch go=new java.util.concurrent.CountDownLatch(1);
  Thread a=new Thread(()->{try{go.await();}catch(Exception e){throw new AssertionError(e);}first.mj();});
  Thread b=new Thread(()->{try{go.await();}catch(Exception e){throw new AssertionError(e);}second.mj();});
  count=FarmGameEngine.constructed;a.start();b.start();go.countDown();a.join(5000);b.join(5000);
  check(!a.isAlive()&&!b.isAlive()&&FarmGameEngine.constructed==count+1,"multiple MIDlets started duplicate engine");
  System.out.println("PASS bootstrap: SQLite barrier, constructor/Init rollback, fresh retry, repeated resume, concurrent MIDlets");
 }
}
'''
        start=peers.index('class FarmGameEngine {');end=peers.index('public class StartupProbe {')
        farm='package probe;\npublic '+peers[start:end]
        peers=peers[:start]+peers[end:]
        self.run_fixture(kotlin,{'probe/StartupProbe.java':peers,'probe/FarmGameEngine.java':farm,
            'javax/microedition/lcdui/r.java':'package javax.microedition.lcdui;public class r {public static Object b(Object o){return new Object();}}',
            'android/util/Log.java':'package android.util;public class Log {public static int e(String a,String b,Throwable c){return 0;}}'},'probe.StartupProbe')

    def test_resume_uses_layout_readiness_without_fixed_ui_sleep(self):
        method=extract_method((ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/Start.kt').read_text(),'override fun onResume()')
        kotlin='''package probe
open class Base {var resumed=0;open fun onResume(){resumed++}}
object MIDlet {var coi=true}
class Start:Base(){var bcT=0;var bcU=0;var isFinishing=false;val packageManager=Any()
 METHOD
}
'''.replace('METHOD',method)
        runner='''package probe;
public class ResumeProbe {
 public static void main(String[]args){Start s=new Start();long start=System.nanoTime();s.onResume();long elapsed=System.nanoTime()-start;
  if(s.getResumed()!=1||com.gameloft.android.wrapper.ae.calls!=0)throw new AssertionError("cold layout gate bypassed");
  s.setBcT(800);s.setBcU(480);s.onResume();if(com.gameloft.android.wrapper.ae.calls!=1)throw new AssertionError("ready warm view was not resumed");
  s.setFinishing(true);s.onResume();if(com.gameloft.android.wrapper.ae.calls!=1)throw new AssertionError("destroyed view relaunched");
  System.out.println("PASS layout-ready resume; cold callback elapsed_us="+(elapsed/1000));
 }
}'''
        peer='''package com.gameloft.android.wrapper;public class ae {public static int bfS,bfT,calls;public static String bfU;public static boolean bfZ;
 public ae(Object a,float b,String[]c,String[]d,String e){}public static void a(Object manager,int w,int h){calls++;}}'''
        # A source guard complements execution without adding a timing-flaky assertion.
        self.assertNotIn('Thread.sleep',method)
        self.run_fixture(kotlin,{'probe/ResumeProbe.java':runner,'com/gameloft/android/wrapper/ae.java':peer},'probe.ResumeProbe')

    def run_fixture(self,kotlin,peers,main):
        with tempfile.TemporaryDirectory() as tmp:
            folder=Path(tmp);source=folder/'Peers.kt';source.write_text(kotlin);files=[]
            for name,text in peers.items():
                path=folder/name;path.parent.mkdir(parents=True,exist_ok=True);path.write_text(text);files.append(path)
            compile_mixed([source],files,folder)
            result=subprocess.run([str(jdk_tool('java')),'-cp',runtime_classpath(folder),main],capture_output=True,text=True,timeout=15)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr);print(result.stdout.strip())
