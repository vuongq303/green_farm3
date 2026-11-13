package com.quanhoang.greenfarm3.S800x480

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.quanhoang.greenfarm3.migration.SaveArchive
import com.quanhoang.greenfarm3.migration.CloudMigrationBackup
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID

/** Durable cloud work shares the exact transaction which commits the local farm. */
object CloudSaveJournal {
    const val SCHEMA_VERSION = 1
    private const val SLOT = "device-local"
    private const val MAX_REVISION = 9007199254740991L

    data class State(val playerId: String?, val revision: Long, val adopted: Boolean,
        val lastAckHash: String?, val conflict: String?, val originKind: String)
    data class Work(val operationId: String, val expectedRevision: Long, val payload: ByteArray,
        val snapshotHash: String, val localGeneration: Long, val adoptionToken: String? = null)

    /** Called by SQLiteOpenHelper inside its upgrade transaction, before changing schema. */
    fun migrate(db: SQLiteDatabase, context: Context) {
        if (hasTable(db, "cloud_state") || hasTable(db, "cloud_outbox")) {
            requireSchema(db)
            return
        }
        CloudMigrationBackup.preserve(db, context)
        db.execSQL("CREATE TABLE cloud_state (singleton INTEGER PRIMARY KEY CHECK(singleton=1), schema_version INTEGER NOT NULL CHECK(schema_version=1), owner_player_id TEXT, server_revision INTEGER NOT NULL DEFAULT 0 CHECK(server_revision>=0 AND server_revision<=9007199254740991), adopted INTEGER NOT NULL DEFAULT 0 CHECK(adopted IN(0,1)), last_ack_hash TEXT, conflict_code TEXT, origin_kind TEXT NOT NULL DEFAULT 'fresh' CHECK(origin_kind IN('fresh','approved-local-copy','remote-restore')))")
        db.execSQL("INSERT INTO cloud_state(singleton,schema_version,server_revision,adopted) VALUES(1,1,0,0)")
        db.execSQL("CREATE TABLE cloud_outbox (position TEXT PRIMARY KEY CHECK(position IN('head','tail')), operation_id TEXT NOT NULL UNIQUE, expected_revision INTEGER CHECK(expected_revision>=0 AND expected_revision<=9007199254740991), payload BLOB NOT NULL, snapshot_hash TEXT NOT NULL CHECK(length(snapshot_hash)=64), local_generation INTEGER NOT NULL CHECK(local_generation>0), adoption_token TEXT, CHECK((position='head' AND expected_revision IS NOT NULL) OR (position='tail' AND expected_revision IS NULL)))")
    }

    fun requireSchema(db: SQLiteDatabase) {
        check(hasTable(db, "cloud_state") && hasTable(db, "cloud_outbox")) { "Cloud journal schema is missing; progress retained" }
        db.rawQuery("SELECT schema_version FROM cloud_state WHERE singleton=1", null).use {
            check(it.moveToFirst() && it.getInt(0) == SCHEMA_VERSION) { "Cloud journal metadata is invalid; progress retained" }
        }
        db.rawQuery("SELECT 1 FROM cloud_outbox WHERE position='tail' AND NOT EXISTS(SELECT 1 FROM cloud_outbox WHERE position='head')", null).use {
            check(!it.moveToFirst()) { "Cloud journal has an orphan pending snapshot; progress retained" }
        }
        val state = state(db)
        check(!state.adopted || state.playerId != null) { "Cloud journal ownership is missing; progress retained" }
        check(state.revision == 0L || state.lastAckHash != null) { "Cloud journal acknowledgement is missing; progress retained" }
        head(db)?.let { check(it.expectedRevision == state.revision) { "Cloud pending revision is inconsistent; progress retained" } }
    }

    fun state(db: SQLiteDatabase): State = db.rawQuery(
        "SELECT owner_player_id,server_revision,adopted,last_ack_hash,conflict_code,origin_kind FROM cloud_state WHERE singleton=1", null).use {
        check(it.moveToFirst()) { "Cloud journal metadata is missing" }
        State(if (it.isNull(0)) null else it.getString(0), it.getLong(1), it.getInt(2) == 1,
            if (it.isNull(3)) null else it.getString(3), if (it.isNull(4)) null else it.getString(4), it.getString(5))
    }

    fun hasFarm(db: SQLiteDatabase): Boolean = db.rawQuery(
        "SELECT 1 FROM save_records WHERE slot_id=? AND store_name='save_gameplay' AND record_id=1 AND deleted=0", arrayOf(SLOT)).use { it.moveToFirst() }

    fun hasCompleteFarm(db: SQLiteDatabase): Boolean = db.rawQuery(
        "SELECT 1 FROM save_slots WHERE slot_id=? AND complete_snapshot_generation>0", arrayOf(SLOT)).use { it.moveToFirst() } && hasFarm(db)

    fun bindPlayer(db: SQLiteDatabase, playerId: String) {
        val state = state(db)
        check(state.playerId == null || state.playerId == playerId) { "Cloud owner changed; progress retained" }
        if (state.playerId == null) db.execSQL("UPDATE cloud_state SET owner_player_id=? WHERE singleton=1 AND owner_player_id IS NULL", arrayOf<Any>(playerId))
    }

    /** Local state is not claimed as an existing Facebook/account identity. User approves a copy. */
    fun adoptLocal(db: SQLiteDatabase, context: Context) {
        check(db.inTransaction()) { "Adoption requires a transaction" }
        check(state(db).revision == 0L) { "Cannot adopt over an existing server revision" }
        if (hasFarm(db)) writeBackup(context, snapshot(db, context.packageName), "greenfarm-before-cloud-adoption.gf3save")
        db.execSQL("UPDATE cloud_state SET adopted=1,origin_kind=? WHERE singleton=1", arrayOf<Any>(if (hasFarm(db)) "approved-local-copy" else "fresh"))
        if (hasFarm(db)) enqueueCommittedFarm(db, context.packageName)
    }

    /** Called before the enclosing local farm transaction commits, never from the UI/network. */
    fun enqueueCommittedFarm(db: SQLiteDatabase, sourcePackage: String) {
        check(db.inTransaction()) { "Cloud pending state must commit with its farm" }
        val state = state(db)
        if (!state.adopted || state.playerId == null) return
        val snapshot = snapshot(db, sourcePackage)
        check(snapshot.records.none { it.name == "offline_test_balances_v1" }) { "Offline test progress cannot be uploaded" }
        val payload = encode(snapshot)
        val head = head(db)
        val position = if (head == null) "head" else "tail"
        db.insertWithOnConflict("cloud_outbox", null, ContentValues().apply {
            put("position", position)
            put("operation_id", UUID.randomUUID().toString())
            if (head == null) put("expected_revision", state.revision) else putNull("expected_revision")
            put("payload", payload)
            put("snapshot_hash", hash(payload))
            put("local_generation", snapshot.generation)
        }, SQLiteDatabase.CONFLICT_REPLACE).also { check(it >= 0L) { "Cloud snapshot was not queued" } }
    }

    fun head(db: SQLiteDatabase): Work? = db.rawQuery(
        "SELECT operation_id,expected_revision,payload,snapshot_hash,local_generation,adoption_token FROM cloud_outbox WHERE position='head'", null).use {
        if (!it.moveToFirst()) null else Work(it.getString(0), it.getLong(1), it.getBlob(2), it.getString(3), it.getLong(4), if (it.isNull(5)) null else it.getString(5))
    }

    fun hasPending(db: SQLiteDatabase): Boolean = db.rawQuery("SELECT 1 FROM cloud_outbox LIMIT 1", null).use { it.moveToFirst() }

    fun authorizeAdoption(db: SQLiteDatabase, work: Work, token: String) {
        check(db.inTransaction()) { "Adoption grant requires a transaction" }
        check(state(db).originKind == "approved-local-copy" && work.expectedRevision == 0L) { "Local copy was not explicitly approved" }
        check(token.matches(Regex("[A-Za-z0-9_-]{43}"))) { "Invalid adoption grant" }
        val pending = head(db) ?: throw IOException("Adoption grant has no pending operation")
        check(pending.operationId == work.operationId && pending.snapshotHash == work.snapshotHash) { "Adoption grant does not match the pending local copy" }
        val updated = db.update("cloud_outbox", ContentValues().apply { put("adoption_token", token) },
            "position='head' AND operation_id=? AND snapshot_hash=?", arrayOf(work.operationId, work.snapshotHash))
        check(updated == 1) { "Adoption grant was not retained" }
    }

    fun acknowledge(db: SQLiteDatabase, work: Work, revision: Long, currentRevision: Long, hash: String) {
        check(db.inTransaction()) { "Cloud acknowledgement requires a transaction" }
        val current = head(db) ?: throw IOException("Cloud acknowledgement has no pending operation")
        check(current.operationId == work.operationId && current.snapshotHash == work.snapshotHash && hash == work.snapshotHash) { "Cloud acknowledgement does not match the pending snapshot" }
        check(revision == work.expectedRevision + 1 && revision in 1..MAX_REVISION && currentRevision == revision) { "Cloud acknowledgement conflicts with a newer revision" }
        val state = state(db)
        check(state.revision == work.expectedRevision) { "Cloud revision changed while saving" }
        db.execSQL("UPDATE cloud_state SET server_revision=?,last_ack_hash=?,conflict_code=NULL WHERE singleton=1", arrayOf<Any>(revision, hash))
        db.delete("cloud_outbox", "position=? AND operation_id=?", arrayOf("head", work.operationId))
        db.execSQL("UPDATE cloud_outbox SET position='head',expected_revision=? WHERE position='tail'", arrayOf<Any>(revision))
    }

    fun recordConflict(db: SQLiteDatabase, code: String) {
        db.execSQL("UPDATE cloud_state SET conflict_code=? WHERE singleton=1", arrayOf<Any>(code))
    }

    /** The exact existing data-only GF3SAVE1 codec, not a new model of game fields. */
    fun snapshot(db: SQLiteDatabase, sourcePackage: String): SaveArchive.Snapshot {
        val metadata = db.rawQuery("SELECT generation,complete_snapshot_generation,snapshot_format,pose_snapshot,created_at,updated_at FROM save_slots WHERE slot_id=?", arrayOf(SLOT)).use {
            check(it.moveToFirst()) { "Local save slot is missing" }
            longArrayOf(it.getLong(0), it.getLong(1), it.getLong(2), it.getLong(3), it.getLong(4), it.getLong(5))
        }
        val records = ArrayList<SaveArchive.Record>()
        db.rawQuery("SELECT store_name,payload,checksum,deleted,generation,record_id FROM save_records WHERE slot_id=? ORDER BY store_name", arrayOf(SLOT)).use {
            while (it.moveToNext()) {
                check(it.getInt(5) == 1) { "Unsupported game record ID" }
                val deleted = it.getInt(3)
                check(deleted in 0..1) { "Invalid game record tombstone" }
                val payload = if (it.isNull(1)) null else it.getBlob(1)
                val checksum = if (it.isNull(2)) null else it.getBlob(2)
                if (deleted == 1) check(payload == null && checksum == null) { "Invalid deleted game record" }
                else check(payload != null && checksum != null && MessageDigest.isEqual(SaveArchive.checksum(payload), checksum)) { "Game record checksum mismatch" }
                records.add(SaveArchive.Record(it.getString(0), payload, it.getLong(4)))
            }
        }
        return SaveArchive.Snapshot(sourcePackage, metadata[0], metadata[1], metadata[2].toInt(), metadata[3].toInt(), metadata[4], metadata[5], records).also(SaveArchive::validate)
    }

    fun encode(snapshot: SaveArchive.Snapshot): ByteArray = ByteArrayOutputStream().also { SaveArchive.write(snapshot, it) }.toByteArray()
    fun hash(payload: ByteArray): String = SaveArchive.checksum(payload).joinToString("") { "%02x".format(it.toInt() and 255) }

    fun writeBackup(context: Context, snapshot: SaveArchive.Snapshot, name: String) {
        val destination = File(context.filesDir, name)
        if (destination.exists()) {
            destination.inputStream().use { SaveArchive.read(it) }
            return // Preserve the first consistent pre-change copy.
        }
        val temporary = File(context.filesDir, "$name.pending")
        FileOutputStream(temporary).use { stream -> SaveArchive.write(snapshot, stream); stream.fd.sync() }
        check(temporary.renameTo(destination)) { "Could not preserve the pre-cloud save backup" }
    }

    private fun hasTable(db: SQLiteDatabase, name: String): Boolean = db.rawQuery(
        "SELECT 1 FROM sqlite_master WHERE type='table' AND name=?", arrayOf(name)).use { it.moveToFirst() }
}
