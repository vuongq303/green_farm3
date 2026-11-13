"""Run real archive installation Kotlin against host SQLite through Android peers.

The peers adapt Android's API to one persistent Python sqlite3 connection per
handle; they do not replace the archive, SQL, transaction or validation logic.
Only synthetic fixture databases are opened, with no emulator or player data.
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

BRIDGE = r'''
import base64, sqlite3, sys
path, readonly = sys.argv[1:]
connection = sqlite3.connect('file:' + path + '?mode=ro', uri=True, isolation_level=None) if readonly == '1' else sqlite3.connect(path, isolation_level=None)
connection.execute('PRAGMA busy_timeout=3000')
def decode(value):
    tag, data = value[:1], value[1:]
    if tag == 'N': return None
    if tag == 'I': return int(data)
    if tag == 'F': return float(data)
    value = base64.b64decode(data)
    return value if tag == 'B' else value.decode('utf-8')
def encode(value):
    if value is None: return 'N'
    if isinstance(value, int): return 'I' + str(value)
    if isinstance(value, float): return 'F' + repr(value)
    return ('B' if isinstance(value, bytes) else 'S') + base64.b64encode(value if isinstance(value, bytes) else str(value).encode()).decode()
for line in sys.stdin:
    parts = line.rstrip('\n').split('\t')
    try:
        if parts[0] == 'X':
            connection.close(); print('OK', flush=True); break
        sql = base64.b64decode(parts[1]).decode()
        cursor = connection.execute(sql, tuple(decode(value) for value in parts[2:]))
        if parts[0] == 'Q':
            rows = cursor.fetchall()
            print('\t'.join(['OK', str(len(rows)), str(len(cursor.description))] + [encode(column[0]) for column in cursor.description] + [encode(value) for row in rows for value in row]), flush=True)
        else:
            print('\t'.join(['OK', str(cursor.rowcount), str(cursor.lastrowid or 0)]), flush=True)
    except Exception as failure:
        print('ERR\t' + base64.b64encode(str(failure).encode()).decode(), flush=True)
'''

PEERS = {
'android/content/Context.java': r'''package android.content;
public abstract class Context {
 public abstract java.io.File getFilesDir(); public abstract java.io.File getDatabasePath(String name);
 public abstract String getPackageName(); public Context getApplicationContext(){return this;}
}''',
'android/content/ContentValues.java': r'''package android.content;
public class ContentValues {
 public final java.util.LinkedHashMap<String,Object> values=new java.util.LinkedHashMap<>();
 public void put(String k,String v){values.put(k,v);} public void put(String k,Integer v){values.put(k,v);}
 public void put(String k,Long v){values.put(k,v);} public void put(String k,byte[]v){values.put(k,v);}
 public void putNull(String k){values.put(k,null);}
}''',
'android/database/SQLException.java': r'''package android.database;
public class SQLException extends RuntimeException {public SQLException(String message){super(message);}}''',
'android/database/DatabaseErrorHandler.java': r'''package android.database;
public interface DatabaseErrorHandler {void onCorruption(android.database.sqlite.SQLiteDatabase database);}''',
'android/database/sqlite/SQLiteDatabaseCorruptException.java': r'''package android.database.sqlite;
public class SQLiteDatabaseCorruptException extends android.database.SQLException {public SQLiteDatabaseCorruptException(String m){super(m);}}''',
'android/database/Cursor.java': r'''package android.database;
public class Cursor implements java.io.Closeable {
 public static final int FIELD_TYPE_NULL=0,FIELD_TYPE_INTEGER=1,FIELD_TYPE_FLOAT=2,FIELD_TYPE_STRING=3,FIELD_TYPE_BLOB=4;
 private final Object[][]rows;private final String[]names;private int position=-1;
 public Cursor(Object[][]rows,String[]names){this.rows=rows;this.names=names;} public boolean moveToFirst(){position=0;return rows.length>0;}
 public boolean moveToNext(){return ++position<rows.length;}public boolean isNull(int column){return rows[position][column]==null;}
 public String getString(int c){return (String)rows[position][c];} public byte[]getBlob(int c){return (byte[])rows[position][c];}
 public long getLong(int c){return ((Number)rows[position][c]).longValue();}public int getInt(int c){return (int)getLong(c);}
 public double getDouble(int c){return ((Number)rows[position][c]).doubleValue();}
 public int getType(int c){Object v=rows[position][c];return v==null?FIELD_TYPE_NULL:v instanceof byte[]?FIELD_TYPE_BLOB:v instanceof String?FIELD_TYPE_STRING:v instanceof Double?FIELD_TYPE_FLOAT:FIELD_TYPE_INTEGER;}
 public String[]getColumnNames(){return names;}public int getCount(){return rows.length;}
 public void close(){}
}''',
'android/database/sqlite/SQLiteDatabase.java': r'''package android.database.sqlite;
import java.io.*;import java.nio.charset.StandardCharsets;import java.util.*;
import android.content.ContentValues;import android.database.*;
public class SQLiteDatabase implements java.io.Closeable {
 public static final int OPEN_READONLY=1,OPEN_READWRITE=0,ENABLE_WRITE_AHEAD_LOGGING=0x20000000,CONFLICT_IGNORE=4,CONFLICT_REPLACE=5;
 private final Process process;private final BufferedReader input;private final PrintWriter output;
 private boolean successful,transaction;
 private SQLiteDatabase(String path,int flags){
  try {
   process=new ProcessBuilder("python3","-u",System.getProperty("sqliteBridge"),path,(flags&OPEN_READONLY)!=0?"1":"0").redirectError(ProcessBuilder.Redirect.INHERIT).start();
   input=new BufferedReader(new InputStreamReader(process.getInputStream(),StandardCharsets.UTF_8));
   output=new PrintWriter(new OutputStreamWriter(process.getOutputStream(),StandardCharsets.UTF_8),true);
   if((flags&ENABLE_WRITE_AHEAD_LOGGING)!=0)enableWriteAheadLogging();
  }catch(IOException failure){throw new SQLException(failure.toString());}
 }
 public static SQLiteDatabase openDatabase(String path,Object factory,int flags,DatabaseErrorHandler handler){return new SQLiteDatabase(path,flags);}
 public static SQLiteDatabase openOrCreateDatabase(String path,Object factory,DatabaseErrorHandler handler){return new SQLiteDatabase(path,0);}
 public static boolean deleteDatabase(File file){boolean existed=file.exists();file.delete();new File(file+"-wal").delete();new File(file+"-shm").delete();return existed;}
 private static String encode(Object value){
  if(value==null)return "N";if(value instanceof Double||value instanceof Float)return "F"+value;if(value instanceof Number)return "I"+value;
  return (value instanceof byte[]?"B":"S")+Base64.getEncoder().encodeToString(value instanceof byte[]?(byte[])value:value.toString().getBytes(StandardCharsets.UTF_8));
 }
 private static Object decode(String value){
  if(value.equals("N"))return null;if(value.startsWith("I"))return Long.parseLong(value.substring(1));if(value.startsWith("F"))return Double.parseDouble(value.substring(1));
  byte[]bytes=Base64.getDecoder().decode(value.substring(1));return value.startsWith("B")?bytes:new String(bytes,StandardCharsets.UTF_8);
 }
 private synchronized String[]command(String operation,String sql,Object[]args){
  StringBuilder line=new StringBuilder(operation);
  if(sql!=null)line.append('\t').append(Base64.getEncoder().encodeToString(sql.getBytes(StandardCharsets.UTF_8)));
  if(args!=null)for(Object arg:args)line.append('\t').append(encode(arg));output.println(line);
  try {
   String answer=input.readLine();if(answer==null)throw new SQLException("SQLite peer exited");
   String[]parts=answer.split("\\t",-1);if(parts[0].equals("ERR"))throw new SQLException(new String(Base64.getDecoder().decode(parts[1]),StandardCharsets.UTF_8));return parts;
  }catch(IOException failure){throw new SQLException(failure.toString());}
 }
 public Cursor rawQuery(String sql,String[]args){
  String[]answer=command("Q",sql,args);int n=Integer.parseInt(answer[1]),m=Integer.parseInt(answer[2]);Object[][]rows=new Object[n][m];
  String[]names=new String[m];for(int j=0;j<m;j++)names[j]=(String)decode(answer[3+j]);
  for(int i=0;i<n;i++)for(int j=0;j<m;j++)rows[i][j]=decode(answer[3+m+i*m+j]);return new Cursor(rows,names);
 }
 public void execSQL(String sql){command("E",sql,null);}public void execSQL(String sql,Object[]args){command("E",sql,args);}
 public int getVersion(){try(Cursor c=rawQuery("PRAGMA user_version",null)){c.moveToFirst();return c.getInt(0);}}
 public void setVersion(int version){execSQL("PRAGMA user_version="+version);}
 public boolean enableWriteAheadLogging(){try(Cursor c=rawQuery("PRAGMA journal_mode=WAL",null)){c.moveToFirst();return c.getString(0).equalsIgnoreCase("wal");}}
 public void beginTransaction(){successful=false;execSQL("BEGIN IMMEDIATE");transaction=true;}public void beginTransactionNonExclusive(){beginTransaction();}
 public boolean inTransaction(){return transaction;}
 public void setTransactionSuccessful(){successful=true;}public void endTransaction(){execSQL(successful?"COMMIT":"ROLLBACK");successful=false;transaction=false;}
 private long insert(String table,ContentValues values,int conflict){
  String columns=String.join(",",values.values.keySet()), placeholders=String.join(",",Collections.nCopies(values.values.size(),"?"));
  String[]answer=command("E","INSERT "+(conflict==CONFLICT_IGNORE?"OR IGNORE ":conflict==CONFLICT_REPLACE?"OR REPLACE ":"")+"INTO "+table+" ("+columns+") VALUES ("+placeholders+")",values.values.values().toArray());
  return Long.parseLong(answer[2]);
 }
 public long insertWithOnConflict(String table,String nullHack,ContentValues values,int conflict){return insert(table,values,conflict);}
 public long insertOrThrow(String table,String nullHack,ContentValues values){return insert(table,values,0);}
 public int update(String table,ContentValues values,String where,String[]args){
  List<Object>all=new ArrayList<>(values.values.values());if(args!=null)Collections.addAll(all,args);
  List<String>sets=new ArrayList<>();for(String name:values.values.keySet())sets.add(name+"=?");
  return Integer.parseInt(command("E","UPDATE "+table+" SET "+String.join(",",sets)+(where==null?"":" WHERE "+where),all.toArray())[1]);
 }
 public int delete(String table,String where,String[]args){return Integer.parseInt(command("E","DELETE FROM "+table+(where==null?"":" WHERE "+where),args)[1]);}
 public void close(){command("X",null,null);try{process.waitFor();input.close();output.close();}catch(Exception failure){throw new SQLException(failure.toString());}}
}''',
}

PROBE = r'''
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.quanhoang.greenfarm3.migration.*
import java.io.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FixtureContext(private val root:File):Context(){
 override fun getFilesDir()=File(root,"files").also{it.mkdirs()}
 override fun getDatabasePath(name:String)=File(root,"databases/$name").also{it.parentFile.mkdirs()}
 override fun getPackageName()="com.quanhoang.greenfarm3"
}
fun fixture(binding:String="original-fixture-binding",generation:Long=17):SaveArchive.Snapshot {
 val gameplay=ByteArray(96);gameplay[0]=4
 val identity=binding.toByteArray();ByteBuffer.wrap(gameplay,1,4).order(ByteOrder.LITTLE_ENDIAN).putInt(identity.size);identity.copyInto(gameplay,5)
 fun words(magic:Int,size:Int)=ByteBuffer.allocate(12+size*4).putInt(magic).putInt(1).putInt(1).apply{repeat(size){putInt(0)}}.array()
 return SaveArchive.Snapshot("com.quanhoang.greenfarm3",generation,generation,1,1,1000,2000+generation,listOf(
  SaveArchive.Record("RMSHeader",ByteArray(35).also{it[4]=16},generation),SaveArchive.Record("save_gameplay",gameplay,generation),
  SaveArchive.Record("save_farm0",ByteArray(128){(it*7).toByte()},generation),
  SaveArchive.Record("pending_rewards_v1",words(0x47465257,28),generation),
  SaveArchive.Record("entity_pose_v1",words(0x47465053,6),generation),
  SaveArchive.Record("old_facebook_account_label","retained-old-link".toByteArray(),generation),
  SaveArchive.Record("removed-record",null,generation)))
}
fun database(c:Context)=SQLiteDatabase.openDatabase(c.getDatabasePath(SaveArchive.DATABASE_NAME).absolutePath,null,SQLiteDatabase.OPEN_READWRITE,null)
fun encoded(snapshot:SaveArchive.Snapshot)=ByteArrayOutputStream().also{SaveArchive.write(snapshot,it)}.toByteArray()
fun same(a:SaveArchive.Snapshot,b:SaveArchive.Snapshot)=encoded(a).contentEquals(encoded(b))
fun rejected(block:()->Unit){try{block();error("accepted unsafe install")}catch(expected:IOException){}}
fun state(c:Context)=database(c).use{db->db.rawQuery("SELECT owner_player_id,server_revision,adopted,last_ack_hash,conflict_code,schema_version FROM cloud_state WHERE singleton=1",null).use{
 it.moveToFirst();listOf(if(it.isNull(0))null else it.getString(0),it.getLong(1),it.getInt(2),if(it.isNull(3))null else it.getString(3),if(it.isNull(4))null else it.getString(4),it.getInt(5))}}
var checks=0
fun check(value:Boolean,message:String){if(!value)throw AssertionError(message);checks++;println("PASS $message")}
fun upgrade5(c:Context){database(c).use{db->
 db.beginTransaction();try{
  db.execSQL("CREATE TABLE cloud_state (singleton INTEGER PRIMARY KEY CHECK(singleton=1),owner_player_id TEXT,server_revision INTEGER NOT NULL DEFAULT 0,adopted INTEGER NOT NULL DEFAULT 0,last_ack_hash TEXT,conflict_code TEXT,schema_version INTEGER NOT NULL DEFAULT 1,origin_kind TEXT NOT NULL DEFAULT 'fresh')")
  db.execSQL("CREATE TABLE cloud_outbox (position TEXT PRIMARY KEY,operation_id TEXT NOT NULL,expected_revision INTEGER,payload BLOB NOT NULL,snapshot_hash TEXT NOT NULL,local_generation INTEGER NOT NULL)")
  db.execSQL("CREATE TABLE cloud_session_fixture (token TEXT NOT NULL)")
  db.execSQL("INSERT INTO cloud_session_fixture VALUES('private-session-never-export')")
  db.execSQL("INSERT INTO cloud_state(singleton) VALUES(1)");db.version=5;db.setTransactionSuccessful()
 }finally{db.endTransaction()}
}}
fun main(args:Array<String>){
 val root=File(args.single());val c=FixtureContext(File(root,"target"));val initial=fixture();val newer=fixture("restored-cloud-binding",18)
 SaveArchiveDatabase.install(c,initial)
 check(database(c).use{it.version}==4,"new manual import creates schema4")
 check(same(initial,SaveArchiveDatabase.read(c)),"schema4 export keeps every gameplay/account-label byte")
 upgrade5(c)
 val five=SaveArchiveDatabase.read(c)
 check(same(initial,five),"schema5 exports identical game-only snapshot")
 val bytes=encoded(five)
 check(ByteBuffer.wrap(bytes,12,4).int==4&&String(bytes,0,8)=="GF3SAVE1","portable archive header remains GF3SAVE1/schema4")
 check(!String(bytes,Charsets.ISO_8859_1).contains("private-session-never-export"),"private cloud session never appears in archive")
 SaveArchiveDatabase.install(c,newer)
 check(database(c).use{it.version}==5,"manual import into unlinked schema5 never downgrades database")
 check(state(c)==listOf<Any?>(null,0L,0,null,null,1),"virgin cloud state retained exactly")
 check(database(c).use{it.rawQuery("SELECT token FROM cloud_session_fixture",null).use{r->r.moveToFirst();r.getString(0)}}=="private-session-never-export","non-game tables preserved on import")
 database(c).use{it.execSQL("UPDATE cloud_state SET owner_player_id='player-A',server_revision=3,adopted=1,last_ack_hash=?,conflict_code='existing-conflict' WHERE singleton=1",arrayOf("a".repeat(64)))}
 val before=state(c)
 rejected{SaveArchiveDatabase.install(c,initial)}
 check(same(newer,SaveArchiveDatabase.read(c))&&state(c)==before,"manual linked-profile import refuses without changing world/session")
 database(c).use{it.execSQL("INSERT INTO cloud_outbox VALUES('head','fixture-operation',3,?,?,18)",arrayOf(encoded(newer),"b".repeat(64)))}
 rejected{SaveArchiveDatabase.installCloud(c,initial,"player-A",4,"c".repeat(64))}
 check(state(c)==before&&same(newer,SaveArchiveDatabase.read(c)),"pending outbox blocks cloud restore and retains all progress")
 database(c).use{it.execSQL("DELETE FROM cloud_outbox")}
 rejected{SaveArchiveDatabase.installCloud(c,initial,"player-B",4,"c".repeat(64))}
 rejected{SaveArchiveDatabase.installCloud(c,initial,"player-A",2,"c".repeat(64))}
 rejected{SaveArchiveDatabase.installCloud(c,initial,"player-A",3,"c".repeat(64))}
 check(state(c)==before,"foreign owner, stale revision and mismatched same-revision hash rejected")
 SaveTransferIdentity.initialize(c)
 check(SaveTransferIdentity.matches("restored-cloud-binding","this-device"),"old committed binding cached before restore")
 val restored=fixture("new-receipted-binding",19)
 database(c).use{it.execSQL("CREATE TRIGGER reject_import BEFORE INSERT ON save_records WHEN NEW.store_name='entity_pose_v1' BEGIN SELECT RAISE(ABORT,'injected install failure'); END")}
 var failed=false
 try{SaveArchiveDatabase.installCloud(c,restored,"player-A",4,"c".repeat(64))}catch(expected:android.database.SQLException){failed=true}
 check(failed&&same(newer,SaveArchiveDatabase.read(c))&&state(c)==before,"SQLite insertion failure rolls back records plus cloud receipt")
 check(SaveTransferIdentity.matches("restored-cloud-binding","this-device")&&!SaveTransferIdentity.matches("new-receipted-binding","this-device"),"failed transaction retains previous binding cache")
 val backup=File(c.filesDir,SaveArchiveDatabase.BACKUP_FILE)
 check(backup.isFile&&same(newer,backup.inputStream().use(SaveArchive::read)),"consistent synced archive backup retained after failed import")
 database(c).use{it.execSQL("DROP TRIGGER reject_import")}
 // Fail after records/identity were replaced, proving receipt and world share one transaction.
 database(c).use{it.execSQL("CREATE TRIGGER reject_receipt BEFORE UPDATE ON cloud_state BEGIN SELECT RAISE(ABORT,'injected receipt failure'); END")}
 failed=false
 try{SaveArchiveDatabase.installCloud(c,restored,"player-A",4,"c".repeat(64))}catch(expected:android.database.SQLException){failed=true}
 check(failed&&same(newer,SaveArchiveDatabase.read(c))&&state(c)==before,"receipt failure rolls back replaced gameplay and identity")
 database(c).use{it.execSQL("DROP TRIGGER reject_receipt")}
 SaveArchiveDatabase.installCloud(c,restored,"player-A",4,"c".repeat(64))
 check(same(restored,SaveArchiveDatabase.read(c)),"same-owner receipt installs complete snapshot")
 check(state(c)==listOf<Any?>("player-A",4L,1,"c".repeat(64),null,1),"same transaction commits revision/hash/adopted and clears conflict only")
 check(database(c).use{it.version}==5,"cloud restore retains schema5")
 check(database(c).use{it.rawQuery("SELECT origin_kind FROM cloud_state",null).use{r->r.moveToFirst();r.getString(0)}}=="remote-restore","controlled cloud restore records its source without changing owner")
 check(SaveTransferIdentity.matches("new-receipted-binding","this-device")&&!SaveTransferIdentity.matches("restored-cloud-binding","this-device"),"committed cloud restore invalidates stale binding cache")
 rejected{SaveArchiveDatabase.installCloud(c,newer,"player-A",3,"d".repeat(64))}
 check(same(restored,SaveArchiveDatabase.read(c)),"older cloud response cannot roll back newer restored revision")
 val fresh=FixtureContext(File(root,"missing"))
 rejected{SaveArchiveDatabase.installCloud(fresh,initial,"player-A",1,"a".repeat(64))}
 check(!fresh.getDatabasePath(SaveArchive.DATABASE_NAME).exists(),"cloud restore cannot create an unauthenticated destination")
 database(c).use{it.version=6}
 rejected{SaveArchiveDatabase.read(c)};rejected{SaveArchiveDatabase.install(c,initial)}
 check(database(c).use{it.version}==6,"unsupported schema retained without downgrade/reset")
 println("SAVE_ARCHIVE_CLOUD_HOST_PASS checks=$checks sqlite=python-sqlite3 production=Kotlin")
}
'''


class SaveArchiveDatabaseCloudTest(unittest.TestCase):
    def test_actual_kotlin_archive_installation_uses_real_sqlite_transactions(self):
        with tempfile.TemporaryDirectory(prefix='greenfarm-archive-cloud-') as temporary:
            root = Path(temporary)
            bridge = root / 'sqlite_bridge.py'
            bridge.write_text(BRIDGE)
            peers = []
            for relative, text in PEERS.items():
                file = root / relative
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(text)
                peers.append(file)
            probe = root / 'ArchiveCloudProbe.kt'
            probe.write_text(PROBE)
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/migration'
            classes = root / 'classes'
            compile_mixed([source / name for name in ('SaveArchive.kt', 'SaveArchiveDatabase.kt', 'SaveTransferIdentity.kt')] + [probe], peers, classes)
            result = subprocess.run([str(jdk_tool('java')), '-DsqliteBridge=' + str(bridge), '-cp', runtime_classpath(classes),
                                     'ArchiveCloudProbeKt', str(root / 'fixtures')], text=True, capture_output=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('SAVE_ARCHIVE_CLOUD_HOST_PASS checks=', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
