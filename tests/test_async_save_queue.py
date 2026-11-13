"""Run the actual queue and batch policy with a deterministic JVM writer.

The writer uses versioned absolute values and latch-controlled transactions. It
does not emulate Android or claim SQLite durability; the real storage probe
covers those concerns separately.
"""

from pathlib import Path
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import qualified_package, kotlin_source_directory, adapt_fixture

PACKAGE = qualified_package().replace('.', '/')
CORE = kotlin_source_directory()

STORE = r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;
import java.util.concurrent.*;

public final class GameSaveStore {
 public static final class Snapshot {
  final Map<String,Integer> records;
  final Map<String,Long> versions;
  Snapshot(Map<String,Integer> records,Map<String,Long> versions){
   this.records=Collections.unmodifiableMap(new LinkedHashMap<>(records));
   this.versions=Collections.unmodifiableMap(new HashMap<>(versions));
  }
  int recordCount(){return records.size();}
  long byteCount(){return records.size()*4L;}
 }
 static Snapshot snapshot(long version,Object...pairs){
  Map<String,Integer> records=new LinkedHashMap<>();Map<String,Long> versions=new HashMap<>();
  for(int n=0;n<pairs.length;n+=2){String key=(String)pairs[n];records.put(key,(Integer)pairs[n+1]);versions.put(key,version);}
  return new Snapshot(records,versions);
 }
 static Snapshot coalesceSnapshots(Snapshot older,Snapshot newer){
  Map<String,Integer> records=new LinkedHashMap<>(older.records);Map<String,Long> versions=new HashMap<>(older.versions);
  for(Map.Entry<String,Integer> record:newer.records.entrySet()){
   Long before=versions.get(record.getKey()),after=newer.versions.get(record.getKey());
   if(before==null||after>=before){records.put(record.getKey(),record.getValue());versions.put(record.getKey(),after);}
  }
  return new Snapshot(records,versions);
 }
 static final class Step {
  final boolean fail;
  final CountDownLatch entered=new CountDownLatch(1),release;
  volatile Snapshot snapshot;
  Step(boolean fail,boolean blocked){this.fail=fail;release=new CountDownLatch(blocked?1:0);}
 }
 static final Deque<Step> plan=new ArrayDeque<>();
 static final List<Snapshot> attempts=new ArrayList<>();
 static Map<String,Integer> durable=new LinkedHashMap<>();
 static int active,maxActive,commits;
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static void waitFor(CountDownLatch latch,String message){
  try{check(latch.await(5,TimeUnit.SECONDS),message);}catch(InterruptedException interrupted){throw new AssertionError(interrupted);}
 }
 static synchronized void reset(){
  check(active==0,"reset while writing");plan.clear();attempts.clear();durable.clear();maxActive=commits=0;
 }
 static synchronized Step step(boolean fail,boolean blocked){Step result=new Step(fail,blocked);plan.add(result);return result;}
 static synchronized int calls(){return attempts.size();}
 static synchronized int value(String key){Integer value=durable.get(key);return value==null?-1:value;}
 static void applySnapshot(Snapshot snapshot){
  Step step;Map<String,Integer> transaction;
  synchronized(GameSaveStore.class){
   step=plan.poll();check(step!=null,"unexpected write/retry");attempts.add(snapshot);active++;maxActive=Math.max(maxActive,active);
   transaction=new LinkedHashMap<>(durable);transaction.putAll(snapshot.records);step.snapshot=snapshot;
  }
  step.entered.countDown();
  try{
   waitFor(step.release,"writer was never released");
   if(step.fail)throw new IllegalStateException("injected transaction failure");
   synchronized(GameSaveStore.class){durable=transaction;commits++;}
  }finally{synchronized(GameSaveStore.class){active--;}}
 }
}
'''

RUNNER = r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class AsyncSaveQueueProbe {
 static void check(boolean ok,String message){GameSaveStore.check(ok,message);}
 static void entered(GameSaveStore.Step step){GameSaveStore.waitFor(step.entered,"write did not start");}
 static final class Results implements AsyncSaveQueue.Listener {
  final LinkedBlockingQueue<AsyncSaveQueue.Result> values=new LinkedBlockingQueue<>();
  public void completed(AsyncSaveQueue.Result result){values.add(result);}
  AsyncSaveQueue.Result next(){
   try{AsyncSaveQueue.Result value=values.poll(5,TimeUnit.SECONDS);check(value!=null,"completion missing");return value;}
   catch(InterruptedException interrupted){throw new AssertionError(interrupted);}
  }
 }
 static void rejects(Runnable action,String message){
  try{action.run();}catch(IllegalStateException expected){return;}throw new AssertionError(message);
 }
 static void policy(){
  SaveBatchPolicy batch=new SaveBatchPolicy();
  check(!batch.isDirty()&&!batch.due(100000),"empty policy is due");
  batch.changed(0,0);batch.changed(0,-1);check(!batch.isDirty(),"nonchanges started batch");
  batch.changed(0,1);check(batch.isDirty()&&!batch.due(199),"debounce fired early");
  check(batch.due(200),"200ms debounce missing");batch.captured();
  check(!batch.isDirty()&&!batch.due(100000),"capture did not reset batch");
  batch.changed(1000,1);batch.changed(1150,1);
  check(!batch.due(1349)&&batch.due(1350),"debounce did not follow newest change");batch.captured();
  for(int now=2000;now<2500;now+=50){batch.changed(now,1);check(!batch.due(now),"continuous changes flushed early");}
  batch.changed(2499,1);check(!batch.due(2499)&&batch.due(2500),"continuous changes postponed 500ms max wait");
  batch.captured();batch.changed(3000,49);check(!batch.due(3000),"49 operations flushed early");
  batch.changed(3000,1);check(batch.due(3000),"50th operation did not flush immediately");
  batch.captured();batch.changed(4000,Long.MAX_VALUE);check(batch.due(4000),"large batch overflowed threshold");
  batch.captured();batch.changed(5000,1);check(!batch.due(5199)&&batch.due(5200),"old max wait leaked into new batch");
  System.out.println("PASS policy: 200ms debounce, 500ms continuous-change cap, 50 operations and reset");
 }
 static void coalescing(){
  GameSaveStore.reset();Results results=new Results();AsyncSaveQueue queue=new AsyncSaveQueue(results);
  GameSaveStore.Step first=GameSaveStore.step(false,true),second=GameSaveStore.step(false,true);
  queue.submit(GameSaveStore.snapshot(1,"coins",10,"firstOnly",11),1,null);entered(first);
  queue.submit(GameSaveStore.snapshot(2,"coins",20,"secondOnly",22),2,null);
  queue.submit(GameSaveStore.snapshot(3,"coins",30,"thirdOnly",33),3,null);
  check(GameSaveStore.calls()==1&&GameSaveStore.active==1,"second writer ran beside blocked first");
  check(!queue.awaitIdle(0)&&!queue.awaitIdle(10)&&!queue.isIdle(),"awaitIdle discarded active/pending work");
  rejects(queue::close,"close accepted active/pending work");
  first.release.countDown();entered(second);AsyncSaveQueue.Result a=results.next();
  check(a.sequence==1&&a.failure==null&&queue.durableSequence()==1,"A acknowledgement included B/C");
  check(GameSaveStore.value("coins")==10&&GameSaveStore.value("secondOnly")==-1,"pending data published before commit");
  check(second.snapshot.recordCount()==3&&second.snapshot.records.get("coins")==30
    &&second.snapshot.records.get("secondOnly")==22&&second.snapshot.records.get("thirdOnly")==33,"B/C were not coalesced by absolute value");
  check(!queue.isIdle()&&!queue.awaitIdle(0),"A completion dropped newer pending write");
  second.release.countDown();check(queue.awaitIdle(5000),"coalesced write never drained");
  AsyncSaveQueue.Result newest=results.next();
  check(newest.sequence==3&&newest.records==3&&newest.bytes==12&&newest.durationNanos>=0,"coalesced result metadata wrong");
  check(queue.durableSequence()==3&&GameSaveStore.calls()==2&&GameSaveStore.commits==2
    &&GameSaveStore.maxActive==1&&GameSaveStore.value("coins")==30&&GameSaveStore.value("firstOnly")==11,"bounded single-writer commit failed");
  check(results.values.isEmpty(),"coalesced ticket produced phantom completion");
  queue.close();rejects(()->queue.submit(GameSaveStore.snapshot(4,"coins",40),4,null),"closed queue accepted work");
  System.out.println("PASS coalescing: blocked A, one pending B/C, one active writer, A cannot acknowledge B/C, close retains work");
 }
 static void retry(){
  GameSaveStore.reset();Results results=new Results();AsyncSaveQueue queue=new AsyncSaveQueue(results);
  GameSaveStore.Step failure=GameSaveStore.step(true,true),retry=GameSaveStore.step(false,true);
  GameSaveStore.Snapshot original=GameSaveStore.snapshot(1,"coins",10,"retryOnly",17);
  queue.submit(original,1,null);entered(failure);failure.release.countDown();
  AsyncSaveQueue.Result failed=results.next();
  check(failed.sequence==1&&failed.failure!=null&&failed.records==2&&failed.bytes==8,"failure acknowledgement wrong");
  check(queue.durableSequence()==0&&GameSaveStore.commits==0&&GameSaveStore.value("coins")==-1,"failed transaction published/acknowledged");
  check(!queue.awaitIdle(0),"failed absolute snapshot discarded");
  queue.submit(GameSaveStore.snapshot(2,"coins",15,"newerOnly",19),2,null);entered(retry);
  check(retry.snapshot.records.get("coins")==15&&retry.snapshot.records.get("retryOnly")==17
    &&retry.snapshot.records.get("newerOnly")==19,"retry lost old-only/newer absolute records");
  check(original.records.get("coins")==10&&queue.durableSequence()==0,"retry mutated original or acknowledged early");
  retry.release.countDown();check(queue.awaitIdle(5000),"successful retry did not drain");
  AsyncSaveQueue.Result saved=results.next();
  check(saved.sequence==2&&saved.failure==null&&queue.durableSequence()==2&&GameSaveStore.calls()==2
    &&GameSaveStore.commits==1&&GameSaveStore.maxActive==1&&GameSaveStore.value("coins")==15,"absolute retry double-applied/dropped data");
  queue.close();System.out.println("PASS retry: first failure retained, no partial publish, newer absolute values survive one atomic retry");
 }
 static void checkpoint(){
  GameSaveStore.reset();Results results=new Results();AsyncSaveQueue queue=new AsyncSaveQueue(results);Object language=new Object();
  GameSaveStore.Step failure=GameSaveStore.step(true,true);
  queue.submit(GameSaveStore.snapshot(1,"language",7,"coins",10),1,language);entered(failure);failure.release.countDown();
  AsyncSaveQueue.Result failed=results.next();
  check(failed.checkpoint==language&&failed.failure!=null&&failed.sequence==1,"checkpoint failure did not preserve identity");
  check(queue.awaitIdle(1500)&&queue.isIdle()&&GameSaveStore.calls()==1&&queue.durableSequence()==0,"canceled language checkpoint was automatically retried");
  GameSaveStore.Step rollback=GameSaveStore.step(false,true);
  queue.submit(GameSaveStore.snapshot(2,"language",16,"coins",10),2,null);entered(rollback);
  check(rollback.snapshot.records.get("language")==16,"canceled language selection leaked into next write");
  rollback.release.countDown();check(queue.awaitIdle(5000),"rollback never drained");
  AsyncSaveQueue.Result saved=results.next();
  check(saved.checkpoint==null&&saved.failure==null&&GameSaveStore.calls()==2&&GameSaveStore.value("language")==16
    &&GameSaveStore.value("coins")==10&&queue.durableSequence()==2,"explicit rollback did not persist original language/gameplay");
  queue.close();System.out.println("PASS checkpoint: failure completes once, no automatic canceled-language retry, explicit old-language recovery persists");
 }
 static void interruptWait()throws Exception{
  GameSaveStore.reset();Results results=new Results();AsyncSaveQueue queue=new AsyncSaveQueue(results);
  GameSaveStore.Step writing=GameSaveStore.step(false,true);queue.submit(GameSaveStore.snapshot(1,"coins",10),1,null);entered(writing);
  AtomicBoolean waitResult=new AtomicBoolean(true),interrupted=new AtomicBoolean(false);CountDownLatch waiting=new CountDownLatch(1);
  Thread waiter=new Thread(()->{waiting.countDown();waitResult.set(queue.awaitIdle(5000));interrupted.set(Thread.currentThread().isInterrupted());});
  waiter.start();GameSaveStore.waitFor(waiting,"waiter not ready");waiter.interrupt();waiter.join(5000);
  check(!waiter.isAlive()&&!waitResult.get()&&interrupted.get(),"awaitIdle swallowed interruption");
  rejects(queue::close,"interrupted waiter discarded active write");writing.release.countDown();
  check(queue.awaitIdle(5000)&&queue.durableSequence()==1,"interrupted wait lost pending save");results.next();queue.close();
  System.out.println("PASS lifecycle: interrupted/timeout wait preserves pending data; close only succeeds after completion");
 }
 public static void main(String[] args)throws Exception{
  switch(args[0]){case "policy":policy();break;case "coalescing":coalescing();break;
   case "retry":retry();break;case "checkpoint":checkpoint();break;case "interrupt":interruptWait();break;
   default:throw new AssertionError(args[0]);}
 }
}
'''


class AsyncSaveQueueTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.temporary = tempfile.TemporaryDirectory(prefix='async-save-queue-')
        cls.addClassCleanup(cls.temporary.cleanup)
        cls.directory = Path(cls.temporary.name)
        source = cls.directory / PACKAGE
        source.mkdir(parents=True)
        files = []
        for name, text in [('GameSaveStore', STORE), ('AsyncSaveQueueProbe', RUNNER)]:
            path = source / (name + '.java')
            path.write_text(adapt_fixture(text))
            files.append(str(path))
        compile_mixed([CORE / 'AsyncSaveQueue.kt', CORE / 'SaveBatchPolicy.kt'], files, cls.directory)

    def run_case(self, name):
        result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(self.directory),
                                 PACKAGE.replace('/', '.') + '.AsyncSaveQueueProbe', name],
                                capture_output=True, text=True, timeout=20)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn('PASS ', result.stdout)
        print(result.stdout.strip())

    def test_200ms_debounce_500ms_max_wait_and_50_operations(self):
        self.run_case('policy')

    def test_one_writer_bounded_pending_and_acknowledgement(self):
        self.run_case('coalescing')

    def test_failure_retains_absolute_snapshot_and_coalesces_newer_values(self):
        self.run_case('retry')

    def test_failed_language_checkpoint_does_not_retry_canceled_selection(self):
        self.run_case('checkpoint')

    def test_await_idle_interruption_and_close_never_discard_pending_work(self):
        self.run_case('interrupt')


if __name__ == '__main__':
    unittest.main()
