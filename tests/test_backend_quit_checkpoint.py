"""Run the actual exit branches and legacy-container bridge in a JVM shell.

The coordinator is a deterministic peer here; the real journal/SQLite worker has
its own tests. This verifies settlement of earned rewards before the immutable
checkpoint, deferred shutdown afterward and retirement of the old upload.
"""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import game_source
from test_aa_tile_renderer import extract_method


class BackendQuitCheckpointTest(unittest.TestCase):
    def test_real_quit_cleanup_is_deferred_and_false_local_container_is_preserved(self):
        source = game_source().read_text()
        exit_method = extract_method(source, 'public static void IT()')
        prefix = exit_method[:exit_method.index('            case 1:')]
        first = exit_method[exit_method.index('            case 1:'):exit_method.index('            case 2:')]
        second = exit_method[exit_method.index('            case 24:'):exit_method.index('            case 55:')]
        methods = prefix + first + second + '}}\n' + extract_method(source, 'static void aZ(boolean z)') + '\n' + extract_method(source, 'static void Gc()')
        settlement = extract_method(game_source().with_name('FarmEntity.java').read_text(), 'static void oM()')
        settlement = settlement.replace('FarmGameEngine.', 'ExitProbe.')
        fixture = r'''
import java.util.*;
public class ExitProbe {
 static List<String> calls=new ArrayList<>();
 static String aOH="",aOI="",aOJ="",aCG="auto",aCF="old-notification";
 static int aOG,avD=-1,aZB,axt,aMZ=7,aCy=1,aCz=1,aBa=1,state;
 static boolean awV,aOO,bbE=true,aCD,arg,bbs;
 static byte[]aCw,aCx;
 static int aBc=1900,aBd=50,xp,inventory;
 static Tracker baf=new Tracker(),baW=new Tracker();
 static class Tracker {void Close(){calls.add("tracker-close");}void NF(){calls.add("tracker-save");}}
 static class FarmEntity {
  static FarmEntity[]als=new FarmEntity[6];FarmEntity aoi;
  int Kx=47,anG,anM;boolean anX=true;
  FarmEntity(int kind,int amount,FarmEntity next){anG=kind;anM=amount;aoi=next;}
  void oN(){calls.add("reward-particle-removed");}
  %SETTLEMENT%
 }
 static class GameRuntime {static int Qn;static void be(String value){calls.add("quit-log");}static String jr(){return "binding";}static boolean bx(String value){if(!value.contains("ngừng sử dụng"))throw new AssertionError("Legacy restore notice claimed success");calls.add("legacy-restore-notice");return true;}}
 static class GloftGF2F {static GloftGF2F acx=new GloftGF2F();String bY(String key){return key;}}
 static class s {static void f(String binding,boolean encoded){calls.add("binding");}static String gX(){return "client";}}
 static class bq {
  static int ct(String name){calls.add("container-open");return 0;}
  static int n(byte[]data,int length){calls.add("container-record");return 0;}
  static int nG(){return 2;}
  static int i(String description,boolean online){if(online)throw new AssertionError("Legacy online transport reached");calls.add("container-local-commit");return 0;}
 }
 static class BackendCloudController {
  static Runnable pending;static int requested,uploads;
  static boolean isQuitPending(){return pending!=null;}
  static void requestQuit(Runnable preparation,Runnable next){if(pending!=null)throw new AssertionError("duplicate quit");requested++;pending=next;preparation.run();}
  static void localCommitAvailable(){uploads++;}
  static void checkpointFinished(){calls.add("snapshot-durable");Runnable next=pending;pending=null;next.run();}
 }
 static void sv(){calls.add("settle-move");}static void rE(){calls.add("rE");}
 static void EZ(){calls.add("EZ");}static void Ms(){calls.add("Ms");}
 static void HH(){calls.add("HH");}static void rz(){calls.add("local-release");}
 static int currentEngineState(){return state;}static void qB(){calls.add("close-confirmation");}
 static void be(String message){calls.add("tracking-log");}
 static void hX(int amount){xp+=amount;}static void hS(long amount){aBc+=amount;}
 static void hT(int amount){aBd+=amount;}static void hV(int amount){inventory+=amount;}
 static void hU(int amount){inventory+=amount;}static void O(int context,int kind,int amount){inventory+=amount;}
 static void hg(int amount){inventory+=amount;}static void bW(int context,int kind){}
 static int ii(int value){return value;}static int a(int value){return value;}
 static boolean qN(){throw new AssertionError("Local container checked online readiness");}
 static String rv(){return "binding";}
 %METHODS%
 static void check(boolean okay,String message){if(!okay)throw new AssertionError(message+" "+calls);}
 public static void main(String[]args){
  int checks=0;
  for(int action:new int[]{1,24})for(int mode:new int[]{-1,3})for(boolean tutorial:new boolean[]{false,true}){
   calls.clear();aOG=action;avD=mode;awV=tutorial;GameRuntime.Qn=0;bbE=true;aOO=false;
   aBc=1900;aBd=50;xp=inventory=0;
   FarmEntity.als[3]=new FarmEntity(36,25,new FarmEntity(37,2,new FarmEntity(6,1,new FarmEntity(59,(3<<16)|119,null))));
   int requests=BackendCloudController.requested;
   IT();check(GameRuntime.Qn==0&&bbE&&!aOO&&!calls.contains("local-release"),"Quit shut down before checkpoint");
   check(aBc==1925&&aBd==51&&xp==2&&inventory==3,"Earned rewards were not settled before checkpoint");
   check(BackendCloudController.requested==requests+1,"Quit did not enqueue once");
   aOH="pending";IT();check(aOH.equals("pending")&&BackendCloudController.requested==requests+1,"Pending quit allowed another action");
   BackendCloudController.checkpointFinished();
   check(calls.indexOf("snapshot-durable")>calls.lastIndexOf("reward-particle-removed")&&calls.indexOf("snapshot-durable")<calls.indexOf("local-release"),"Reward/checkpoint/shutdown order changed");
   check(GameRuntime.Qn==-1&&!bbE&&aBc==1925&&aBd==51&&xp==2&&inventory==3,"Exit did not finish or rewards replayed");
   checks+=6;
  }
  calls.clear();aCD=true;aCw=new byte[]{1};aCx=new byte[]{2};
  aZ(true);check(BackendCloudController.uploads==1&&calls.isEmpty()&&aCD,"Online bridge invoked old container or fake ACK");checks++;
  aZ(false);check(Collections.frequency(calls,"container-record")==2&&calls.contains("container-local-commit")&&aCD,"Local container branch changed");checks++;
  calls.clear();state=18;Gc();check(calls.equals(Arrays.asList("legacy-restore-notice"))&&aCF.equals("old-notification"),"Retired restore altered farm or notification");checks++;
  calls.clear();state=43;Gc();check(calls.equals(Arrays.asList("legacy-restore-notice","close-confirmation"))&&aCF.equals("old-notification"),"Retired restore did not safely close its own confirmation");checks++;
  System.out.println("PASS: "+checks+" actual quit-order/container checks; no cleanup before checkpoint or legacy network upload");
 }
}
'''.replace('%METHODS%', methods).replace('%SETTLEMENT%', settlement)
        with tempfile.TemporaryDirectory(prefix='greenfarm-quit-checkpoint-') as temp:
            directory = Path(temp)
            peer = directory / 'ExitProbe.java'
            peer.write_text(fixture)
            result = subprocess.run([str(jdk_tool('javac')), '-d', str(directory), str(peer)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(directory), 'ExitProbe'],
                                    capture_output=True, text=True, timeout=10)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS: 52 actual quit-order/container checks', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
