"""Exercise the production cloud journal with actual SQLite transactions and reopen."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

from test_save_archive_database_cloud import BRIDGE, PEERS, PROBE as ARCHIVE_PROBE

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

# Reuse synthetic opaque-record/context constructors, not a model of the journal.
FIXTURE = ARCHIVE_PROBE.split('var checks=0', 1)[0]
PROBE = r'''
var checks=0
fun check(value:Boolean,message:String){if(!value)throw AssertionError(message);checks++;println("PASS $message")}
fun transaction(c:Context,block:(SQLiteDatabase)->Unit){database(c).use{db->db.beginTransaction();try{block(db);db.setTransactionSuccessful()}finally{db.endTransaction()}}}
fun mutate(db:SQLiteDatabase,generation:Long,value:Byte){
 val farm=ByteArray(128){(it*7).toByte()};farm[0]=value
 db.execSQL("UPDATE save_records SET payload=?,checksum=?,generation=? WHERE slot_id=? AND store_name='save_farm0' AND record_id=1",arrayOf(farm,SaveArchive.checksum(farm),generation,"device-local"))
 db.execSQL("UPDATE save_slots SET generation=?,complete_snapshot_generation=?,updated_at=? WHERE slot_id=?",arrayOf<Any>(generation,generation,2000L+generation,"device-local"))
 db.execSQL("INSERT INTO save_commits(slot_id,generation,committed_at,changed_records,is_snapshot,snapshot_format,pose_snapshot) VALUES(?,?,?,1,1,1,1)",arrayOf<Any>("device-local",generation,2000L+generation))
}
fun main(args:Array<String>){
 val c=FixtureContext(File(args.single(),"journal"));SaveArchiveDatabase.install(c,fixture())
 transaction(c){db->com.quanhoang.greenfarm3.S800x480.CloudSaveJournal.migrate(db,c);db.version=5}
 val journal=com.quanhoang.greenfarm3.S800x480.CloudSaveJournal
 database(c).use{db->journal.requireSchema(db);check(journal.state(db).playerId==null&&!journal.hasPending(db),"additive migration leaves unlinked owner and no fabricated pending work")}
 check(File(c.filesDir,"greenfarm-before-cloud-migration.gf3dbbackup").isFile,"migration preserves a logical pre-schema5 backup")
 transaction(c){db->journal.bindPlayer(db,"fixture-player-A");journal.adoptLocal(db,c)}
 val initial=database(c).use{db->journal.head(db)!!}
 check(database(c).use{journal.state(it).originKind}=="approved-local-copy","explicit local-copy approval has durable origin metadata")
 var grantRejected=false
 try{transaction(c){journal.authorizeAdoption(it,initial.copy(operationId=java.util.UUID.randomUUID().toString()),"u".repeat(43))}}catch(expected:IllegalStateException){grantRejected=true}
 check(grantRejected&&database(c).use{journal.head(it)!!.adoptionToken}==null,"grant for a different operation cannot authorize a pending copy")
 grantRejected=false
 try{transaction(c){journal.authorizeAdoption(it,initial.copy(snapshotHash="f".repeat(64)),"u".repeat(43))}}catch(expected:IllegalStateException){grantRejected=true}
 check(grantRejected&&database(c).use{journal.head(it)!!.adoptionToken}==null,"grant for a different snapshot hash is refused")
 grantRejected=false
 try{transaction(c){db->db.execSQL("UPDATE cloud_state SET origin_kind='fresh'");journal.authorizeAdoption(db,initial,"u".repeat(43))}}catch(expected:IllegalStateException){grantRejected=true}
 check(grantRejected&&database(c).use{journal.head(it)!!.adoptionToken}==null,"fresh profiles cannot authorize a legacy local-copy grant")
 transaction(c){journal.authorizeAdoption(it,initial,"u".repeat(43))}
 check(database(c).use{journal.head(it)!!.adoptionToken}=="u".repeat(43),"matching adoption grant persists privately across connection reopen")
 check(java.util.UUID.fromString(initial.operationId).toString()==initial.operationId&&initial.expectedRevision==0L,"adopted snapshot has durable UUID and initial expected revision")
 check(initial.snapshotHash==journal.hash(initial.payload),"queued archive hash matches exact immutable payload")
 val original=SaveArchive.read(ByteArrayInputStream(initial.payload))
 check(original.records.any{it.name=="old_facebook_account_label"}&&original.generation==17L,"original account-label data included without assigning its account identity")
 check(database(c).use{journal.state(it).playerId}=="fixture-player-A","explicit fixture adoption retains one verified owner")
 var failed=false
 try{transaction(c){journal.bindPlayer(it,"fixture-player-B")}}catch(expected:IllegalStateException){failed=true}
 check(failed&&database(c).use{journal.state(it).playerId}=="fixture-player-A","binding cannot silently replace an existing player")
 database(c).use{db->db.beginTransaction();try{mutate(db,18,41);journal.enqueueCommittedFarm(db,c.packageName)}finally{db.endTransaction()}}
 database(c).use{db->
  check(journal.snapshot(db,c.packageName).generation==17L,"rollback restores the original complete farm generation")
  check(journal.head(db)!!.operationId==initial.operationId&&db.rawQuery("SELECT COUNT(*) FROM cloud_outbox",null).use{it.moveToFirst();it.getInt(0)}==1,"same transaction rollback removes uncommitted queued snapshot")
 }
 transaction(c){db->mutate(db,18,41);journal.enqueueCommittedFarm(db,c.packageName)}
 val firstTail=database(c).use{db->db.rawQuery("SELECT operation_id FROM cloud_outbox WHERE position='tail'",null).use{it.moveToFirst();it.getString(0)}}
 transaction(c){db->mutate(db,19,42);journal.enqueueCommittedFarm(db,c.packageName)}
 transaction(c){db->mutate(db,20,43);journal.enqueueCommittedFarm(db,c.packageName)}
 var latestTail=""
 database(c).use{db->
  check(journal.head(db)!!.operationId==initial.operationId,"in-flight head remains immutable while local farm changes")
  check(db.rawQuery("SELECT COUNT(*) FROM cloud_outbox",null).use{it.moveToFirst();it.getInt(0)}==2,"multiple mutations coalesce to at most one head plus one tail")
  db.rawQuery("SELECT operation_id,payload,snapshot_hash,local_generation,expected_revision FROM cloud_outbox WHERE position='tail'",null).use{
   it.moveToFirst();latestTail=it.getString(0)
   val payload=it.getBlob(1);val decoded=SaveArchive.read(ByteArrayInputStream(payload))
   check(latestTail!=firstTail&&it.getLong(3)==20L&&it.isNull(4),"tail replaces superseded work and waits for head revision")
   check(journal.hash(payload)==it.getString(2)&&decoded.generation==20L&&decoded.records.single{it.name=="save_farm0"}.payload!![0]==43.toByte(),"tail contains the complete latest farm, not a partial delta")
  }
 }
 // Each database(c).use opens a fresh real SQLite process/connection.
 check(database(c).use{journal.head(it)!!.operationId}==initial.operationId,"process connection reopen retains the pending operation UUID")
 fun badAck(work:com.quanhoang.greenfarm3.S800x480.CloudSaveJournal.Work,revision:Long,current:Long,hash:String){
  var rejected=false;try{transaction(c){journal.acknowledge(it,work,revision,current,hash)}}catch(expected:IllegalStateException){rejected=true}
  check(rejected&&database(c).use{journal.head(it)!!.operationId}==initial.operationId,"invalid acknowledgement retains original pending head")
 }
 badAck(initial,1,1,"f".repeat(64))
 badAck(initial.copy(operationId=java.util.UUID.randomUUID().toString()),1,1,initial.snapshotHash)
 badAck(initial,1,2,initial.snapshotHash)
 transaction(c){journal.acknowledge(it,initial,1,1,initial.snapshotHash)}
 val promoted=database(c).use{journal.head(it)!!}
 check(promoted.operationId==latestTail&&promoted.expectedRevision==1L&&promoted.localGeneration==20L,"exact committed acknowledgement promotes latest tail with new expected revision")
 check(database(c).use{journal.state(it).revision}==1L,"only matching committed acknowledgement advances durable revision")
 failed=false
 try{transaction(c){journal.acknowledge(it,initial,1,1,initial.snapshotHash)}}catch(expected:IllegalStateException){failed=true}
 check(failed&&database(c).use{journal.head(it)!!.operationId}==promoted.operationId,"duplicate old acknowledgement cannot clear newer pending work")
 transaction(c){journal.recordConflict(it,"revision_conflict")}
 check(database(c).use{journal.state(it).conflict}=="revision_conflict"&&database(c).use{journal.head(it)!!.operationId}==promoted.operationId,"CAS conflict is durable and retains work for explicit resolution")
 failed=false
 try{transaction(c){db->
  val marker=byteArrayOf(1)
  db.execSQL("INSERT INTO save_records(slot_id,store_name,record_id,payload,checksum,deleted,generation) VALUES(?,?,1,?,?,0,20)",arrayOf("device-local","offline_test_balances_v1",marker,SaveArchive.checksum(marker)))
  journal.enqueueCommittedFarm(db,c.packageName)
 }}catch(expected:IllegalStateException){failed=true}
 check(failed&&database(c).use{journal.head(it)!!.operationId}==promoted.operationId,"offline-test marker is never queued into the backend")
 check(database(c).use{db->db.rawQuery("SELECT 1 FROM save_records WHERE store_name='offline_test_balances_v1'",null).use{!it.moveToFirst()}}&&database(c).use{journal.snapshot(it,c.packageName).generation}==20L,"rejected test data rolls back while existing farm remains unchanged")
 failed=false
 try{transaction(c){db->db.execSQL("DROP TABLE cloud_outbox");journal.requireSchema(db)}}catch(expected:IllegalStateException){failed=true}
 check(failed&&database(c).use{journal.head(it)!!.operationId}==promoted.operationId,"missing cloud table fails without resetting journal or progress")
 transaction(c){journal.acknowledge(it,promoted,2,2,promoted.snapshotHash)}
 database(c).use{db->check(!journal.hasPending(db)&&journal.state(db).revision==2L&&journal.state(db).lastAckHash==promoted.snapshotHash&&journal.state(db).conflict==null,"latest acknowledged snapshot commits revision/hash and clears only resolved work")}
 check(database(c).use{journal.snapshot(it,c.packageName).generation}==20L,"acknowledgements never mutate gameplay or replay rewards")
 println("CLOUD_SAVE_JOURNAL_HOST_PASS checks=$checks")
}
'''


class CloudSaveJournalTest(unittest.TestCase):
    def test_actual_journal_atomic_pending_work_coalescing_ack_and_restart(self):
        with tempfile.TemporaryDirectory(prefix='greenfarm-cloud-journal-') as temporary:
            folder = Path(temporary)
            bridge = folder / 'sqlite_bridge.py'
            bridge.write_text(BRIDGE)
            peers = []
            for relative, text in PEERS.items():
                peer = folder / relative
                peer.parent.mkdir(parents=True, exist_ok=True)
                peer.write_text(text)
                peers.append(peer)
            probe = folder / 'CloudJournalProbe.kt'
            probe.write_text(FIXTURE + PROBE)
            migration = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/migration'
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/CloudSaveJournal.kt'
            classes = folder / 'classes'
            compile_mixed([source, probe] + [migration / name for name in
                ('SaveArchive.kt', 'SaveArchiveDatabase.kt', 'SaveTransferIdentity.kt', 'CloudMigrationBackup.kt')], peers, classes)
            result = subprocess.run([str(jdk_tool('java')), '-DsqliteBridge=' + str(bridge), '-cp', runtime_classpath(classes),
                                     'CloudJournalProbeKt', str(folder / 'fixtures')], text=True, capture_output=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('CLOUD_SAVE_JOURNAL_HOST_PASS checks=', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
