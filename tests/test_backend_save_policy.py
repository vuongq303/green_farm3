"""Exercise the production legacy-upload policy with a deterministic clock."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import qualified_package, kotlin_source_directory, core_source_directory
from test_aa_tile_renderer import extract_method

PROBE = r'''
public class BackendSavePolicyProbe {
 static void check(boolean condition,String message){if(!condition)throw new AssertionError(message);}
 public static void main(String[]args){
  BackendSavePolicy policy=new BackendSavePolicy();policy.reset();
  policy.captured(99,"old local save");policy.durable(99);
  check(policy.begin(10000)==null,"loading existing local save scheduled remote upload");
  // A=0, B=150, C=350, D=600: only D is sent at 1100, after durability.
  policy.changed(100,0);policy.captured(100,"A");policy.durable(100);
  policy.changed(101,150);policy.captured(101,"B");policy.durable(101);
  policy.changed(102,350);policy.captured(102,"C");policy.durable(102);
  policy.changed(103,600);policy.captured(103,"D");
  check(policy.begin(1099)==null,"trailing debounce fired early");
  check(policy.begin(1100)==null,"non-durable payload was uploaded");
  policy.durable(103);BackendSavePolicy.Upload d=policy.begin(1100);
  check(d!=null&&d.getPayload().equals("D"),"latest payload not sent at trailing 500ms");
  check(policy.begin(10000)==null,"duplicate request while upload in flight");
  policy.changed(104,1150);policy.captured(104,"E");policy.durable(104);
  check(policy.begin(1700)==null,"new action created second concurrent upload");
  check(!policy.completed("unrelated response",true,1750),"unrelated callback acknowledged save");
  check(policy.completed(d.getTag(),true,1750),"matching completion missing");
  BackendSavePolicy.Upload e=policy.begin(1750);check(e!=null&&e.getPayload().equals("E"),"new pending change lost after old response");
  check(policy.completed(e.getTag(),false,1800),"failure callback missing");
  check(policy.begin(2299)==null,"failed request retried without backoff");
  BackendSavePolicy.Upload retry=policy.begin(2300);check(retry!=null&&retry.getPayload().equals("E"),"failure discarded latest payload");
  check(!policy.completed(e.getTag(),true,2350),"old attempt callback acknowledged a retry");
  policy.completed(retry.getTag(),true,2400);check(policy.begin(9999)==null,"unchanged state uploaded again");
  policy.changed(105,10000);policy.captured(105,"E");policy.durable(105);
  check(policy.begin(10500)==null,"byte-identical payload uploaded again");
  policy.changed(106,11000);policy.captured(106,"F");policy.durable(106);
  BackendSavePolicy.Upload f=policy.begin(11500);check(f!=null,"new changes never resumed");
  policy.reset();check(!policy.completed(f.getTag(),true,12000),"prior session callback contaminated new session");
  policy.captured(200,"old persisted farm");policy.durable(200);check(policy.begin(99999)==null,"reset scheduled historical upload");
  // Continuous actions have no 2s/50-operation override in the BACKEND policy.
  for(int n=0;n<100;n++){policy.changed(201+n,n*50);policy.captured(201+n,"value"+n);policy.durable(201+n);check(policy.begin(n*50+49)==null,"backend max-wait flushed continuous actions");}
  check(policy.begin(5449)==null&&policy.begin(5450)!=null,"continuous trailing delay incorrect");
  policy.reset();policy.changed(400,0);policy.captured(400,"retry");policy.durable(400);
  long now=500;
  for(int n=0;n<5;n++){BackendSavePolicy.Upload upload=policy.begin(now);check(upload!=null,"retry prematurely stopped");policy.completed(upload.getTag(),false,now);now+=16000;}
  check(policy.begin(now)==null,"failed requests retried forever without a new mutation");
  policy.changed(401,now);policy.captured(401,"new mutation");policy.durable(401);check(policy.begin(now+500)!=null,"new mutation cannot retry retained state");
  System.out.println("PASS backend trailing debounce: offline/local history excluded, durable payload, single request, stale callbacks, bounded retries and unchanged suppression");
 }
}
'''

class BackendSavePolicyTest(unittest.TestCase):
    def test_real_request_pool_acceptance(self):
        source = (core_source_directory() / 's.java').read_text()
        methods = '\n'.join(extract_method(source, signature) for signature in (
            'public static synchronized boolean tryEnqueueFarmSave(',
            'public static synchronized boolean tryEnqueueProfileSave(',
            'private static int jf()', 'private static synchronized int cz(',
            'private static synchronized void a(int i, Object[] objArr, String[] strArr, String[] strArr2, String str, String str2)'))
        fixture = r'''class LocalDevelopmentMode {static boolean enabled;static boolean isEnabled(){return enabled;}}
class w {static boolean YB=true,YC=true;static int Yf=1;}
class t {static int Ot=1,Ov=2;}
class GLURLConnection {static final int HTTP_OK=200;}
class GameRuntime {static void c(boolean ignored,String message){}}
class s {
 static int[] MQ={-1};static Object[][] MR=new Object[1][];static String[] MU=new String[1],MV=new String[1];
 static String[][] MS=new String[1][],MT=new String[1][];static int ML;static String MO;static boolean MX,fillLocator;
 static void aI(String service){if(!Thread.holdsLock(s.class))throw new AssertionError("unlocked pool");if(fillLocator)MQ[0]=88;}
 static void cA(int slot){}
 __METHODS__
}
public class FarmPoolProbe {
 static void check(boolean ok,String text){if(!ok)throw new AssertionError(text);}
 public static void main(String[]args){
  BackendSavePolicy policy=new BackendSavePolicy();policy.reset();policy.changed(1,0);policy.captured(1,"farm");policy.durable(1);
  BackendSavePolicy.Upload upload=policy.begin(500);check(upload!=null,"policy never became due");
  s.MQ[0]=9;check(!s.tryEnqueueFarmSave("player",upload.getPayload(),upload.getTag()),"full pool reported accepted");
  policy.completed(upload.getTag(),false,500);s.MQ[0]=-1;
  BackendSavePolicy.Upload retry=policy.begin(1000);check(retry!=null&&s.tryEnqueueFarmSave("player",retry.getPayload(),retry.getTag()),"rejected save lost retry");
  check(s.MQ[0]==t.Ot&&s.MR[0][1].equals("Farm")&&s.MR[0][2].equals("farm")&&s.MU[0].equals(retry.getTag()),"real pool entry differs from payload/tag");
  check(policy.begin(10000)==null,"accepted request duplicated before callback");
  policy.completed(retry.getTag(),true,10000);check(policy.begin(20000)==null,"accepted save not acknowledged");
  s.MQ[0]=-1;s.fillLocator=true;check(!s.tryEnqueueFarmSave("player","latest","tag"),"locator consumed last pool slot but save reported accepted");
  s.fillLocator=false;s.MQ[0]=-1;LocalDevelopmentMode.enabled=true;check(!s.tryEnqueueFarmSave("p","f","t")&&s.MQ[0]==-1,"local-mode enqueued remote save");
  LocalDevelopmentMode.enabled=false;check(s.tryEnqueueProfileSave("p","_level","3","level-tag"),"profile request rejected free slot");
  check(s.MQ[0]==t.Ov&&s.MR[0][1].equals("_level")&&s.MR[0][2].equals("3")&&s.MU[0].equals("level-tag"),"profile pool fields incorrect");
  check(!s.tryEnqueueProfileSave("p","_manor","4","manor-tag"),"full profile pool accepted request");
  System.out.println("PASS real request pool: accepted fields/tag, full rejection, locator capacity, policy retry and local isolation");
 }
}
'''
        with tempfile.TemporaryDirectory(prefix='farm-request-pool-') as temp:
            folder = Path(temp)
            probe = folder / 'FarmPoolProbe.java'
            probe.write_text('package ' + qualified_package() + ';\n' + fixture.replace('__METHODS__', methods))
            output = folder / 'classes'
            compile_mixed([kotlin_source_directory() / 'BackendSavePolicy.kt'], [probe], output)
            result = subprocess.run([str(Path(os.environ['JAVA_HOME']) / 'bin/java'), '-cp', runtime_classpath(output), qualified_package() + '.FarmPoolProbe'], capture_output=True, text=True, timeout=30)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            print(result.stdout, end='')

    def test_actual_policy(self):
        with tempfile.TemporaryDirectory(prefix='backend-save-policy-') as temp:
            folder = Path(temp)
            probe = folder / 'BackendSavePolicyProbe.java'
            probe.write_text('package ' + qualified_package() + ';\n' + PROBE)
            output = folder / 'classes'
            compile_mixed([kotlin_source_directory() / 'BackendSavePolicy.kt'], [probe], output)
            java = Path(os.environ['JAVA_HOME']) / 'bin/java'
            result = subprocess.run([str(java), '-cp', runtime_classpath(output), qualified_package() + '.BackendSavePolicyProbe'], capture_output=True, text=True, timeout=30)
            self.assertEqual(0, result.returncode, result.stdout + result.stderr)
            print(result.stdout, end='')

if __name__ == '__main__':
    unittest.main()
