"""Use actual OS file locks across owned JVM processes to guard cross-package import."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed,runtime_classpath

CONTEXT='''package android.content; public abstract class Context { public abstract java.io.File getFilesDir(); }'''
IDENTITY='''package com.quanhoang.greenfarm3.migration; public final class SaveTransferIdentity { public static void initialize(android.content.Context context){} }'''
PROBE=r'''
import android.content.Context
import com.quanhoang.greenfarm3.migration.SaveTransferGate
import java.io.File
import java.io.IOException
fun main(args:Array<String>){
 val directory=File(args[1]);directory.mkdirs()
 val context=object:Context(){override fun getFilesDir():File=directory}
 when(args[0]){
  "holdGame"->{check(SaveTransferGate.enterGame(context));println("GAME_READY");System.out.flush();System.`in`.read()}
  "holdTransfer"->SaveTransferGate.withTransferLock(context){println("TRANSFER_READY");System.out.flush();System.`in`.read()}
  "transfer"->{try{SaveTransferGate.withTransferLock(context){println("TRANSFER_OPEN")}}catch(expected:IOException){println("TRANSFER_BLOCKED")}}
  "game"->println(if(SaveTransferGate.enterGame(context))"GAME_OPEN" else "GAME_BLOCKED")
  "freeze"->{check(!SaveTransferGate.isPreparingTransfer());check(SaveTransferGate.beginPreparation());check(SaveTransferGate.isPreparingTransfer());check(!SaveTransferGate.beginPreparation());SaveTransferGate.cancelPreparation();check(!SaveTransferGate.isPreparingTransfer());check(SaveTransferGate.beginPreparation());SaveTransferGate.cancelPreparation();println("FREEZE_PASS")}
 }
}
'''

class SaveTransferGateTest(unittest.TestCase):
 @classmethod
 def setUpClass(cls):
  cls.work=tempfile.TemporaryDirectory(prefix='greenfarm-transfer-gate-')
  cls.root=Path(cls.work.name)
  context=cls.root/'android/content/Context.java';context.parent.mkdir(parents=True);context.write_text(CONTEXT)
  identity=cls.root/'com/quanhoang/greenfarm3/migration/SaveTransferIdentity.java';identity.parent.mkdir(parents=True);identity.write_text(IDENTITY)
  probe=cls.root/'GateProbe.kt';probe.write_text(PROBE)
  compile_mixed([ROOT/'app/src/main/kotlin/com/quanhoang/greenfarm3/migration/SaveTransferGate.kt',probe],[context,identity],cls.root/'classes')
 @classmethod
 def tearDownClass(cls):cls.work.cleanup()
 def command(self,mode,directory):return [str(jdk_tool('java')),'-cp',runtime_classpath(self.root/'classes'),'GateProbeKt',mode,str(directory)]
 def run_probe(self,mode,directory):
  p=subprocess.run(self.command(mode,directory),capture_output=True,text=True,timeout=10)
  self.assertEqual(p.returncode,0,p.stdout+p.stderr)
  return p.stdout
 def holder(self,mode,directory,ready):
  p=subprocess.Popen(self.command(mode,directory),stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE,text=True)
  self.addCleanup(lambda:p.poll() is None and (p.kill(),p.wait(timeout=5)))
  self.assertEqual(p.stdout.readline().strip(),ready)
  return p
 def test_game_lifetime_blocks_transfer_and_kernel_releases_after_process_kill(self):
  with tempfile.TemporaryDirectory(prefix='gate-game-') as directory:
   game=self.holder('holdGame',directory,'GAME_READY')
   self.assertIn('TRANSFER_BLOCKED',self.run_probe('transfer',directory))
   game.kill();game.communicate(timeout=5)
   self.assertIn('TRANSFER_OPEN',self.run_probe('transfer',directory))
 def test_active_import_blocks_new_game_until_transfer_finishes(self):
  with tempfile.TemporaryDirectory(prefix='gate-import-') as directory:
   transfer=self.holder('holdTransfer',directory,'TRANSFER_READY')
   self.assertIn('GAME_BLOCKED',self.run_probe('game',directory))
   transfer.communicate('\n',timeout=5)
   self.assertEqual(transfer.returncode,0)
   self.assertIn('GAME_OPEN',self.run_probe('game',directory))
 def test_preparation_has_one_owner_and_failure_unfreezes(self):
  with tempfile.TemporaryDirectory(prefix='gate-freeze-') as directory:
   self.assertIn('FREEZE_PASS',self.run_probe('freeze',directory))

if __name__=='__main__':unittest.main()
