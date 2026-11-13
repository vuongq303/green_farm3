"""Verify the real pre-upgrade Kotlin backup with SQLite and an independent decoder."""
from pathlib import Path
import hashlib
import io
import sqlite3
import struct
import subprocess
import sys
import tempfile
import unittest

from test_save_archive_database_cloud import BRIDGE, PEERS

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PROBE = r'''
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.quanhoang.greenfarm3.migration.CloudMigrationBackup
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

class BackupContext(private val root:File):Context(){
 override fun getFilesDir()=File(root,"files").also{it.mkdirs()}
 override fun getDatabasePath(name:String)=File(root,"databases/$name").also{it.parentFile.mkdirs()}
 override fun getPackageName()="com.quanhoang.greenfarm3"
}
fun open(c:Context)=SQLiteDatabase.openOrCreateDatabase(c.getDatabasePath("greenfarm-progress.db").absolutePath,null,null)
fun prepare(c:Context){open(c).use{db->
 db.enableWriteAheadLogging()
 db.execSQL("CREATE TABLE save_slots(slot_id TEXT PRIMARY KEY,generation INTEGER,complete_snapshot_generation INTEGER,snapshot_format INTEGER,pose_snapshot INTEGER,created_at INTEGER,updated_at INTEGER)")
 db.execSQL("CREATE TABLE save_records(slot_id TEXT,store_name TEXT,record_id INTEGER,payload BLOB,checksum BLOB,deleted INTEGER,generation INTEGER,PRIMARY KEY(slot_id,store_name,record_id))")
 db.execSQL("CREATE TABLE save_commits(slot_id TEXT,generation INTEGER,committed_at INTEGER,changed_records INTEGER,is_snapshot INTEGER)")
 db.execSQL("CREATE TABLE legacy_imports(slot_id TEXT,store_name TEXT,imported_at INTEGER)")
 db.execSQL("CREATE TABLE save_transfer_identity(singleton INTEGER,version INTEGER,original_binding TEXT,gameplay_checksum BLOB)")
 db.execSQL("INSERT INTO save_slots VALUES('device-local',1,0,0,0,9007199254740991,2)")
 db.execSQL("INSERT INTO save_records VALUES('device-local','RMSHeader',1,?,?,0,1)",arrayOf(ByteArray(35){it.toByte()},ByteArray(32){7}))
 // SQLite values are dynamically typed; the logical backup must preserve a REAL too.
 db.execSQL("INSERT INTO legacy_imports VALUES('device-local','liên_kết_cũ',2.5)")
 db.execSQL("INSERT INTO save_transfer_identity VALUES(1,1,'original-fixture-binding',NULL)")
 db.version=4
}}
fun preserved(c:Context){open(c).use{db->db.beginTransaction();try{CloudMigrationBackup.preserve(db,c);db.setTransactionSuccessful()}finally{db.endTransaction()}}}
var checks=0
fun check(value:Boolean,message:String){if(!value)throw AssertionError(message);checks++;println("PASS $message")}
fun main(args:Array<String>){
 val root=File(args.single());val header=BackupContext(File(root,"header"));prepare(header)
 open(header).use{db->db.beginTransaction();try{
  CloudMigrationBackup.preserve(db,header)
  db.execSQL("CREATE TABLE cloud_state_fixture(singleton INTEGER)")
 }finally{db.endTransaction()}}
 val backup=File(header.filesDir,CloudMigrationBackup.FILE_NAME)
 check(backup.isFile,"header-only SQLite progress backed up before DDL without requiring a farm")
 open(header).use{db->check(db.version==4,"source user_version retained");check(!db.rawQuery("SELECT 1 FROM sqlite_master WHERE name='cloud_state_fixture'",null).use{it.moveToFirst()},"failed upgrade rolls back DDL but keeps recovery backup")}
 val first=backup.readBytes();preserved(header)
 check(backup.readBytes().contentEquals(first),"repeated migration preserves first checksum-verified copy")
 val partial=BackupContext(File(root,"partial"));prepare(partial)
 open(partial).use{it.execSQL("INSERT INTO save_records VALUES('device-local','save_gameplay',1,?,?,0,1)",arrayOf(byteArrayOf(3,0,-1),ByteArray(32){9}))}
 preserved(partial)
 check(File(partial.filesDir,CloudMigrationBackup.FILE_NAME).isFile,"malformed or incomplete gameplay bytes are preserved without game-format validation")
 val bad=BackupContext(File(root,"bad"));prepare(bad);preserved(bad)
 val badFile=File(bad.filesDir,CloudMigrationBackup.FILE_NAME)
 RandomAccessFile(badFile,"rw").use{it.seek(20);it.writeByte(it.readByte().toInt() xor 1)}
 val damaged=badFile.readBytes();var failed=false
 open(bad).use{db->db.beginTransaction();try{CloudMigrationBackup.preserve(db,bad);db.execSQL("DELETE FROM save_records");db.setTransactionSuccessful()}catch(expected:IOException){failed=true}finally{db.endTransaction()}}
 check(failed&&badFile.readBytes().contentEquals(damaged),"invalid existing backup blocks upgrade without replacing evidence")
 check(open(bad).use{it.rawQuery("SELECT COUNT(*) FROM save_records",null).use{c->c.moveToFirst();c.getInt(0)}}==1,"backup checksum failure retains original database rows")
 val outside=BackupContext(File(root,"outside"));prepare(outside);failed=false
 open(outside).use{try{CloudMigrationBackup.preserve(it,outside)}catch(expected:IllegalStateException){failed=true}}
 check(failed&&!File(outside.filesDir,CloudMigrationBackup.FILE_NAME).exists(),"backup refuses an inconsistent nontransactional read")
 val bounds=BackupContext(File(root,"bounds"));prepare(bounds)
 open(bounds).use{db->db.beginTransaction();try{repeat(2048){db.execSQL("INSERT INTO save_records VALUES('device-local',?,1,NULL,NULL,1,1)",arrayOf("bounded-fixture-$it"))};db.setTransactionSuccessful()}finally{db.endTransaction()}}
 failed=false
 open(bounds).use{db->db.beginTransaction();try{CloudMigrationBackup.preserve(db,bounds);db.setTransactionSuccessful()}catch(expected:IOException){failed=true}finally{db.endTransaction()}}
 check(failed&&!File(bounds.filesDir,CloudMigrationBackup.FILE_NAME).exists(),"record bounds reject publication without deleting oversized source")
 check(open(bounds).use{it.rawQuery("SELECT COUNT(*) FROM save_records",null).use{c->c.moveToFirst();c.getInt(0)}}==2049,"all source rows retained after bounded backup failure")
 val empty=BackupContext(File(root,"empty"))
 open(empty).use{db->db.beginTransaction();try{CloudMigrationBackup.preserve(db,empty);db.setTransactionSuccessful()}finally{db.endTransaction()}}
 check(!File(empty.filesDir,CloudMigrationBackup.FILE_NAME).exists(),"empty new database does not create a misleading recovery backup")
 println("CLOUD_MIGRATION_BACKUP_HOST_PASS checks=$checks")
}
'''


def decode_backup(path):
    raw = path.read_bytes()
    if hashlib.sha256(raw[:-32]).digest() != raw[-32:]:
        raise AssertionError('backup digest mismatch')
    stream = io.BytesIO(raw[:-32])
    def number(fmt):
        size = struct.calcsize(fmt)
        return struct.unpack(fmt, stream.read(size))[0]
    def value_bytes():
        size = number('>i')
        if not 0 <= size <= 64 * 1024 * 1024:
            raise AssertionError('invalid field length')
        data = stream.read(size)
        if len(data) != size:
            raise AssertionError('truncated field')
        return data
    assert stream.read(8) == b'GF3DBK01'
    assert number('>i') == 1
    version = number('>i')
    tables = {}
    for _ in range(number('>i')):
        name, definition = value_bytes().decode(), value_bytes().decode()
        columns = [value_bytes().decode() for _ in range(number('>i'))]
        rows = []
        for _ in range(number('>i')):
            row = []
            for _ in columns:
                tag = number('>B')
                row.append(None if tag == 0 else number('>q') if tag == 1 else number('>d') if tag == 2 else value_bytes().decode() if tag == 3 else value_bytes() if tag == 4 else (_ for _ in ()).throw(AssertionError('invalid value tag')))
            rows.append(tuple(row))
        tables[name] = {'definition': definition, 'columns': columns, 'rows': rows}
    assert stream.read() == b''
    return version, tables


class CloudMigrationBackupTest(unittest.TestCase):
    def test_actual_kotlin_preserves_incomplete_sqlite_before_schema_upgrade(self):
        with tempfile.TemporaryDirectory(prefix='greenfarm-migration-backup-') as temporary:
            folder = Path(temporary)
            bridge = folder / 'sqlite_bridge.py'
            bridge.write_text(BRIDGE)
            peers = []
            for relative, text in PEERS.items():
                file = folder / relative
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(text)
                peers.append(file)
            probe = folder / 'MigrationBackupProbe.kt'
            probe.write_text(PROBE)
            classes = folder / 'classes'
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/migration/CloudMigrationBackup.kt'
            compile_mixed([source, probe], peers, classes)
            fixture = folder / 'fixtures'
            result = subprocess.run([str(jdk_tool('java')), '-DsqliteBridge=' + str(bridge), '-cp', runtime_classpath(classes),
                                     'MigrationBackupProbeKt', str(fixture)], text=True, capture_output=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('CLOUD_MIGRATION_BACKUP_HOST_PASS checks=', result.stdout)
            version, tables = decode_backup(fixture / 'header/files/greenfarm-before-cloud-migration.gf3dbbackup')
            self.assertEqual(version, 4)
            self.assertEqual(set(tables), {'save_slots', 'save_records', 'save_commits', 'legacy_imports', 'save_transfer_identity'})
            # Decode independently and compare every typed SQLite value/column/DDL.
            with sqlite3.connect(fixture / 'header/databases/greenfarm-progress.db') as connection:
                for name, stored in tables.items():
                    cursor = connection.execute('SELECT * FROM "' + name + '"')
                    self.assertEqual(stored['columns'], [c[0] for c in cursor.description])
                    self.assertEqual(stored['rows'], cursor.fetchall())
                    self.assertEqual(stored['definition'], connection.execute('SELECT sql FROM sqlite_master WHERE name=?', (name,)).fetchone()[0])
            _, incomplete = decode_backup(fixture / 'partial/files/greenfarm-before-cloud-migration.gf3dbbackup')
            self.assertTrue(any(row[1] == 'save_gameplay' and row[3] == b'\x03\x00\xff' for row in incomplete['save_records']['rows']))
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
