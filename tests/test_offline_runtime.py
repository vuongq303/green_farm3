"""Exercise retired transport ABI and the production exit/save barrier."""
from pathlib import Path
import subprocess,sys,tempfile,unittest
from test_aa_tile_renderer import extract_method
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed,runtime_classpath
P='com.quanhoang.greenfarm3.S800x480'

class OfflineRuntimeTest(unittest.TestCase):
    def run_mixed(self, kotlin, peers, main, java_sources=()):
        with tempfile.TemporaryDirectory() as d:
            d=Path(d);files=[]
            for name,text in peers.items():
                f=d/name;f.parent.mkdir(parents=True,exist_ok=True);f.write_text(text);files.append(f)
            compile_mixed(kotlin,[*java_sources,*files],d)
            r=subprocess.run([str(jdk_tool('java')),'-cp',runtime_classpath(d),main],capture_output=True,text=True,timeout=15)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)

    def test_retired_requests_never_authenticate_wait_or_spawn_transport_workers(self):
        java=ROOT/'app/src/main/java'/P.replace('.','/')
        kt=ROOT/'app/src/main/kotlin'/P.replace('.','/')
        peers={'ar.java':'package '+P+'; public interface ar {}', 'Peers.java':'''package PACKAGE;
class bc{} class bx{}
class OfflineTransportProbe {
 public static void main(String[]args){
  int threads=Thread.activeCount();
  for(int i=0;i<100;i++) {
   new cj().run();as.Init();as.a("http://192.0.2.1/unreachable","payload",null,600000);as.dV(16);
   s.jn();if(s.jp()||s.fZ())throw new AssertionError("fake online session");
   am old=new am(false);old.c("http://192.0.2.1/unreachable","payload",1);old.run();
   if(old.isInProgress()||!old.BT||old.BV!=0)throw new AssertionError("pending or fake HTTP success");
  }
  if(Thread.activeCount()>threads)throw new AssertionError("transport thread created");
 }
}'''.replace('PACKAGE',P)}
        self.run_mixed([kt/'as.kt',kt/'cj.kt'],peers,P+'.OfflineTransportProbe',[java/'s.java',java/'am.java'])

    def test_launcher_destruction_does_not_kill_the_midlet_and_enables_checkpoint(self):
        source=(ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/Start.kt').read_text()
        self.assertIn('MIDlet.cnR = true',source)
        destroy=extract_method(source,'override fun onDestroy()')
        self.assertNotIn('System.exit',destroy)
        self.assertNotIn('finishActivity',destroy)
        self.assertIn('requestLifecycleFlush()',destroy)

    def test_normal_exit_requires_durable_save_before_world_teardown(self):
        code=(ROOT/'app/src/main/kotlin'/P.replace('.','/')/'GloftGF2F.kt').read_text()
        method=extract_method(code,'public override fun al(unconditional: Boolean)')
        with tempfile.TemporaryDirectory() as d:
            p=Path(d)/'Exit.kt';p.write_text('''package probe
open class MIDlet {open fun al(unconditional:Boolean){}}
object State {var trace="";var committed=false;var fail=false}
class FarmGameEngine {companion object {
 fun rE(){State.trace+="clock;"}
 fun EZ(){check(State.committed);State.trace+="teardown;"}
}}
object ProgressSaveController {fun worldUnloaded(){check(State.committed);State.trace+="unload;"};fun flushForLifecycle():Boolean {State.trace+="flush;";State.committed=!State.fail;return State.committed}}
object GameRuntime {var Qn=1;fun be(s:String){State.trace+="quit;"}}
class GloftGF2F:MIDlet(){var shutdownComplete=false;init{acx=this;acw=FarmGameEngine()}; companion object{var acx:GloftGF2F?=null;var acw:FarmGameEngine?=null}; METHOD}
fun main(){val game=GloftGF2F();State.fail=true;game.al(true);check(State.trace=="clock;flush;"&&GameRuntime.Qn==1)
 State.trace="";State.fail=false;game.al(true);check(State.trace=="clock;flush;unload;teardown;quit;"&&GameRuntime.Qn==-1)
 val trace=State.trace;game.al(true);check(State.trace==trace)
 val stale=GloftGF2F();val active=GloftGF2F();State.trace="";stale.al(true);check(State.trace==""&&GloftGF2F.acw!=null);active.al(true);check(State.committed)}
'''.replace('METHOD',method))
            compile_mixed([p],[],Path(d))
            r=subprocess.run([str(jdk_tool('java')),'-cp',runtime_classpath(d),'probe.ExitKt'],capture_output=True,text=True,timeout=10)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
