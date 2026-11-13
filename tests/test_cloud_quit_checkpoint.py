"""Execute production quit checkpoint bodies with deterministic clock/network peers.

The test does not model farm storage or pretend to acknowledge a real server.
It checks the critical boundary: earned rewards before snapshot, local durability
before cleanup, bounded network wait, and continuation only on the game thread.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from android_build_tools import jdk_tool


def production_function(source, signature):
    start = source.index(signature)
    brace = source.index('{', start)
    depth = 1
    end = brace + 1
    while depth:
        depth += (source[end] == '{') - (source[end] == '}')
        end += 1
    return source[start:end]


PEERS = r'''
import java.util.ArrayDeque

object SystemClock { var now = 0L; fun elapsedRealtime() = now }
object Log { fun w(tag: String, message: String) = 0 }
object ProgressSaveController {
 var value = 0; var saved = -1; var flushes = 0; var allowed = true;var dirty=false
 fun hasPendingChanges() = dirty
 fun flushForLifecycle(): Boolean { flushes++; if (!allowed) return false; saved=value;dirty=false;return true }
}
class ManualWorker {
 val jobs = ArrayDeque<Runnable>()
 fun execute(action: Runnable) { jobs.add(action) }
 fun runOne() { jobs.remove().run() }
}
object BackendCloudController {
 val worker = ManualWorker()
 var quitAction: Runnable? = null
 var quitCheckpointStarted = false
 var quitCheckpointDone = false
 var quitDeadlineMillis = 0L
 var failNetwork = false; var networkRequests = 0
 fun flushPending(maximum: Int, deadline: Long) { networkRequests++; if(failNetwork)throw IllegalStateException("network") }
 fun safeCode(failure: Exception) = "TEST_FAILURE"
 // ACTUAL_METHODS
 fun reset() {
  worker.jobs.clear();quitAction=null;quitCheckpointStarted=false;quitCheckpointDone=false
  quitDeadlineMillis=0;failNetwork=false;networkRequests=0;SystemClock.now=0
  ProgressSaveController.value=0;ProgressSaveController.saved=-1
  ProgressSaveController.flushes=0;ProgressSaveController.allowed=true;ProgressSaveController.dirty=false
 }
}

fun main() {
 val cloud=BackendCloudController
 val store=ProgressSaveController
 val gameThread=Thread.currentThread()
 var settled=0;var cleaned=0
 cloud.reset()
 cloud.requestQuit(Runnable { settled++;store.value=7 },Runnable {
  check(Thread.currentThread()===gameThread);check(store.saved==7);cleaned++;store.value=-99
 })
 cloud.requestQuit(Runnable { error("double settlement") },Runnable { error("double cleanup") })
 check(settled==1&&cleaned==0)
 cloud.frameFinished()
 check(store.saved==7&&cleaned==0&&cloud.worker.jobs.size==1)
 // Running network work never invokes game cleanup on the writer.
 val background=Thread { cloud.worker.runOne() }
 background.start();background.join()
 check(cleaned==0&&cloud.networkRequests==1)
 cloud.frameFinished()
 check(cleaned==1&&store.saved==7&&store.value == -99&&cloud.quitAction==null)
 check(store.flushes==1) // No fresh serializer timestamp after a clean cloud ACK.
 cloud.frameFinished();check(cleaned==1)

 // An unavailable local checkpoint must prevent both network and cleanup.
 cloud.reset();cleaned=0;store.allowed=false
 cloud.requestQuit(Runnable { cleaned++ })
 cloud.frameFinished();check(cleaned==0&&cloud.worker.jobs.isEmpty())
 store.allowed=true;store.value=11;cloud.frameFinished()
 check(store.saved==11&&cloud.worker.jobs.size==1)
 cloud.worker.runOne();store.dirty=true;store.allowed=false;cloud.frameFinished();check(cleaned==0)
 store.allowed=true;cloud.frameFinished();check(cleaned==1)

 // A blocked/busy cloud writer cannot keep quit waiting beyond the deadline.
 // A later SDK mutation is still locally durable before cleanup.
 cloud.reset();cleaned=0;store.value=20
 cloud.requestQuit(Runnable { check(store.saved==21);cleaned++ })
 cloud.frameFinished();check(store.saved==20)
 SystemClock.now=7999;cloud.frameFinished();check(cleaned==0)
 store.value=21;store.dirty=true;SystemClock.now=8000;cloud.frameFinished()
 check(cleaned==1&&cloud.quitAction==null&&cloud.networkRequests==0)
 // Old work finally completing cannot finish a subsequent request early.
 val old=cloud.worker.jobs.remove()
 cloud.requestQuit(Runnable { cleaned++ });cloud.frameFinished()
 check(!cloud.quitCheckpointDone)
 old.run();check(!cloud.quitCheckpointDone)
 cloud.worker.runOne();cloud.frameFinished();check(cleaned==2)

 // Server failure still leaves cleanup contingent on the durable local copy.
 cloud.reset();cleaned=0;cloud.failNetwork=true;store.value=30
 cloud.requestQuit(Runnable { check(store.saved==30);cleaned++ })
 cloud.frameFinished();cloud.worker.runOne();check(cleaned==0)
 cloud.frameFinished();check(cleaned==1)
 println("PASS actual quit checkpoint: reward settlement, duplicate input, local failure, game-thread cleanup, bounded busy writer and late work")
}
'''


class CloudQuitCheckpointTest(unittest.TestCase):
    def test_production_quit_boundary(self):
        source = (ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/BackendCloudController.kt').read_text()
        signatures = (
            '@JvmStatic fun requestQuit(continuation: Runnable)',
            '@JvmStatic fun requestQuit(preparation: Runnable, continuation: Runnable)',
            '@JvmStatic fun frameFinished()',
        )
        methods = '\n'.join(production_function(source, signature) for signature in signatures)
        with tempfile.TemporaryDirectory(prefix='cloud-quit-check-') as temporary:
            directory = Path(temporary)
            file = directory / 'CloudQuitChecks.kt'
            file.write_text(PEERS.replace('// ACTUAL_METHODS', methods))
            compile_mixed([file], [], directory)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(directory), 'CloudQuitChecksKt'], capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual quit checkpoint', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
