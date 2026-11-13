package com.quanhoang.greenfarm3.migration

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabaseCorruptException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest

/** Transfers one verified committed SQLite slot. Call while holding SaveTransferGate's process lock. */
object SaveArchiveDatabase {
    const val BACKUP_FILE = "greenfarm-before-import.gf3save"
    private const val SLOT = SaveArchive.SLOT_ID
    private const val CLOUD_SCHEMA_VERSION = 5
    private const val MAX_SERVER_REVISION = 9_007_199_254_740_991L
    private data class CloudReceipt(val playerId: String, val revision: Long, val hash: String)
    private val preserveCorruptDatabase = DatabaseErrorHandler {
        // The platform default deletes corrupt database/WAL files. An export
        // or failed import must retain the user's original bytes for recovery.
        throw SQLiteDatabaseCorruptException("Tiến độ SQLite bị hỏng; dữ liệu được giữ nguyên để phục hồi.")
    }

    fun read(context: Context): SaveArchive.Snapshot {
        val path = context.getDatabasePath(SaveArchive.DATABASE_NAME)
        if (!path.isFile) throw IOException("Chưa có bản lưu SQLite để xuất. Hãy vào nông trại và lưu ở bản cũ trước.")
        // API16–28 has no read-only transaction API: beginTransaction() on a
        // read-only handle throws SQLITE_READONLY. Keep a WAL/FULL connection
        // for this SELECT-only snapshot transaction; no game table is mutated.
        val db = SQLiteDatabase.openDatabase(path.absolutePath, null,
            SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING, preserveCorruptDatabase)
        try {
            if (db.version !in SaveArchive.ARCHIVE_SCHEMA_VERSION..CLOUD_SCHEMA_VERSION) {
                throw IOException("Schema bản lưu chưa được hỗ trợ; dữ liệu được giữ nguyên. Hãy dùng bản khôi phục hỗ trợ schema4 hoặc schema5.")
            }
            db.execSQL("PRAGMA synchronous=FULL")
            db.beginTransactionNonExclusive()
            try {
                val snapshot = readSlot(db, context.packageName)
                db.setTransactionSuccessful()
                return snapshot
            } finally { db.endTransaction() }
        } finally { db.close() }
    }

    private fun readSlot(db: SQLiteDatabase, sourcePackage: String): SaveArchive.Snapshot {
        db.rawQuery("PRAGMA integrity_check", null).use { cursor ->
            if (!cursor.moveToFirst() || cursor.getString(0) != "ok") throw IOException("SQLite không vượt qua kiểm tra toàn vẹn; dữ liệu được giữ nguyên.")
        }
        val slot = db.rawQuery("SELECT generation,complete_snapshot_generation,snapshot_format,pose_snapshot,created_at,updated_at FROM save_slots WHERE slot_id=?", arrayOf(SLOT))
        val metadata = slot.use {
            if (!it.moveToFirst()) throw IOException("Không tìm thấy checkpoint nông trại.")
            longArrayOf(it.getLong(0), it.getLong(1), it.getLong(2), it.getLong(3), it.getLong(4), it.getLong(5))
        }
        val records = ArrayList<SaveArchive.Record>()
        db.rawQuery("SELECT store_name,payload,checksum,deleted,generation,record_id FROM save_records WHERE slot_id=? ORDER BY store_name", arrayOf(SLOT)).use { rows ->
            while (rows.moveToNext()) {
                if (rows.getInt(5) != 1) throw IOException("Record ID không được hỗ trợ.")
                val deleted = rows.getInt(3)
                if (deleted !in 0..1) throw IOException("Cờ bản ghi không hợp lệ.")
                val payload: ByteArray? = if (rows.isNull(1)) null else rows.getBlob(1)
                val expected: ByteArray? = if (rows.isNull(2)) null else rows.getBlob(2)
                if (deleted == 1) {
                    if (payload != null || expected != null) throw IOException("Tombstone không hợp lệ.")
                } else if (payload == null || expected == null || !MessageDigest.isEqual(expected, SaveArchive.checksum(payload))) {
                    throw IOException("Checksum SQLite không hợp lệ; chưa xuất hoặc thay đổi dữ liệu.")
                }
                records.add(SaveArchive.Record(rows.getString(0), payload, rows.getLong(4)))
            }
        }
        return SaveArchive.Snapshot(sourcePackage, metadata[0], metadata[1], metadata[2].toInt(),
            metadata[3].toInt(), metadata[4], metadata[5], records).also(SaveArchive::validate)
    }

    /** Import is all-or-nothing. A valid existing snapshot is backed up and synced before replacement. */
    fun install(context: Context, snapshot: SaveArchive.Snapshot) {
        install(context, snapshot, null)
    }

    /**
     * Restore only the already authenticated player's cloud snapshot. The caller
     * verifies the downloaded archive's SHA-256 before supplying its receipt,
     * closes GameSaveStore and excludes cloud writers for this whole operation.
     * It must not use this path to adopt a local save into another guest account.
     */
    fun installCloud(context: Context, snapshot: SaveArchive.Snapshot, playerId: String, revision: Long, hash: String) {
        if (playerId.isEmpty() || playerId.length > 128 || playerId.any { it <= ' ' } ||
            revision !in 0..MAX_SERVER_REVISION || !hash.matches(Regex("[0-9a-f]{64}"))) {
            throw IOException("Thông tin xác nhận bản lưu máy chủ không hợp lệ; dữ liệu hiện tại được giữ nguyên.")
        }
        install(context, snapshot, CloudReceipt(playerId, revision, hash))
    }

    private fun install(context: Context, snapshot: SaveArchive.Snapshot, receipt: CloudReceipt?) {
        SaveArchive.validate(snapshot)
        val path = context.getDatabasePath(SaveArchive.DATABASE_NAME)
        val newlyCreated = !path.exists()
        if (newlyCreated && receipt != null) {
            throw IOException("Chưa có phiên người chơi đã xác minh để nhận bản lưu máy chủ.")
        }
        if (!newlyCreated) {
            val existing = SQLiteDatabase.openDatabase(path.absolutePath, null, SQLiteDatabase.OPEN_READONLY, preserveCorruptDatabase)
            val hasProgress = try {
                requireInstallAllowed(existing, receipt)
                existing.rawQuery("SELECT COUNT(*) FROM save_records WHERE slot_id=? AND store_name IN('save_gameplay','save_farm0') AND deleted=0", arrayOf(SLOT)).use {
                    it.moveToFirst(); it.getLong(0) > 0
                }
            } finally { existing.close() }
            if (hasProgress) {
                val previous = read(context) // An incomplete/corrupt existing world is retained, never silently erased.
                writeBackup(context, previous)
            }
        }
        path.parentFile?.mkdirs()
        val db = SQLiteDatabase.openOrCreateDatabase(path.absolutePath, null, preserveCorruptDatabase)
        var installed = false
        try {
            db.enableWriteAheadLogging()
            db.execSQL("PRAGMA synchronous=FULL")
            db.execSQL("PRAGMA foreign_keys=ON")
            db.beginTransaction()
            try {
                val destinationVersion = db.version
                if (newlyCreated && destinationVersion == 0 && receipt == null) createSchema(db)
                else requireInstallAllowed(db, receipt) // Recheck after obtaining the SQLite writer lock.
                db.execSQL("DELETE FROM save_records WHERE slot_id=?", arrayOf(SLOT))
                db.execSQL("DELETE FROM save_commits WHERE slot_id=?", arrayOf(SLOT))
                val slot = ContentValues().apply {
                    put("slot_id", SLOT)
                    put("generation", snapshot.generation)
                    put("complete_snapshot_generation", snapshot.completeSnapshotGeneration)
                    put("snapshot_format", snapshot.snapshotFormat)
                    put("pose_snapshot", snapshot.poseSnapshot)
                    put("created_at", snapshot.createdAtMillis)
                    put("updated_at", snapshot.updatedAtMillis)
                }
                // Retain prior legacy-import markers so old RMS files cannot resurface
                // after importing an archive which intentionally lacks those records.
                db.insertWithOnConflict("save_slots", null, slot, SQLiteDatabase.CONFLICT_IGNORE)
                if (db.update("save_slots", slot, "slot_id=?", arrayOf(SLOT)) != 1) {
                    throw IOException("Không cập nhật được slot bản lưu.")
                }
                for (record in snapshot.records) {
                    db.insertOrThrow("save_records", null, ContentValues().apply {
                        put("slot_id", SLOT)
                        put("store_name", record.name)
                        put("record_id", 1)
                        put("generation", record.generation)
                        put("deleted", if (record.payload == null) 1 else 0)
                        if (record.payload == null) { putNull("payload"); putNull("checksum") }
                        else { put("payload", record.payload); put("checksum", SaveArchive.checksum(record.payload)) }
                    })
                    db.insertWithOnConflict("legacy_imports", null, ContentValues().apply {
                        put("slot_id", SLOT); put("store_name", record.name); put("imported_at", System.currentTimeMillis())
                    }, SQLiteDatabase.CONFLICT_IGNORE)
                }
                db.insertOrThrow("save_commits", null, ContentValues().apply {
                    put("slot_id", SLOT)
                    put("generation", snapshot.completeSnapshotGeneration)
                    put("committed_at", snapshot.updatedAtMillis)
                    put("changed_records", snapshot.records.size)
                    put("is_snapshot", 1)
                    put("snapshot_format", snapshot.snapshotFormat)
                    put("pose_snapshot", snapshot.poseSnapshot)
                })
                val gameplay = snapshot.records.first { it.name == "save_gameplay" }.payload!!
                db.execSQL("CREATE TABLE IF NOT EXISTS save_transfer_identity (singleton INTEGER PRIMARY KEY CHECK(singleton=1), version INTEGER NOT NULL CHECK(version=1), original_binding TEXT NOT NULL, gameplay_checksum BLOB NOT NULL CHECK(length(gameplay_checksum)=32))")
                db.delete("save_transfer_identity", null, null)
                db.insertOrThrow("save_transfer_identity", null, ContentValues().apply {
                    put("singleton", 1); put("version", 1)
                    put("original_binding", SaveArchive.gameplayBinding(gameplay))
                    put("gameplay_checksum", SaveArchive.checksum(gameplay))
                })
                if (receipt != null) installCloudReceipt(db, receipt)
                if (destinationVersion == 0) db.version = SaveArchive.ARCHIVE_SCHEMA_VERSION
                // Re-read checksums, required records and metadata before allowing this transaction to commit.
                readSlot(db, context.packageName)
                db.setTransactionSuccessful()
            } finally { db.endTransaction() }
            installed = true
            // Invalidate only after commit. A failed import keeps the previous
            // binding cached, matching the rolled-back original gameplay blob.
            SaveTransferIdentity.invalidate()
        } finally {
            db.close()
            // This removes only a new, failed import's own empty database; never existing player data.
            if (newlyCreated && !installed) SQLiteDatabase.deleteDatabase(path)
        }
    }

    private fun requireInstallAllowed(db: SQLiteDatabase, receipt: CloudReceipt?) {
        if (db.version !in SaveArchive.ARCHIVE_SCHEMA_VERSION..CLOUD_SCHEMA_VERSION) {
            throw IOException("Schema bản lưu hiện tại không được hỗ trợ; chưa nhập dữ liệu.")
        }
        fun hasTable(name: String): Boolean = db.rawQuery(
            "SELECT 1 FROM sqlite_master WHERE type='table' AND name=?", arrayOf(name)
        ).use { it.moveToFirst() }
        val stateTable = hasTable("cloud_state")
        val outboxTable = hasTable("cloud_outbox")
        if (!stateTable && !outboxTable && db.version == SaveArchive.ARCHIVE_SCHEMA_VERSION && receipt == null) return
        if (db.version != CLOUD_SCHEMA_VERSION || !stateTable || !outboxTable) {
            throw IOException("Cấu trúc đồng bộ chưa được hỗ trợ; bản lưu và phiên hiện tại được giữ nguyên.")
        }
        db.rawQuery("SELECT 1 FROM cloud_outbox LIMIT 1", null).use {
            if (it.moveToFirst()) throw IOException("Còn tiến độ chờ đồng bộ. Hãy đồng bộ hoặc xử lý xung đột trong game trước khi thay bản lưu.")
        }
        db.rawQuery("SELECT owner_player_id,server_revision,adopted,last_ack_hash,conflict_code,schema_version FROM cloud_state WHERE singleton=1", null).use {
            if (!it.moveToFirst()) {
                if (receipt != null) throw IOException("Không tìm thấy phiên người chơi đã xác minh; bản lưu máy chủ chưa được nhập.")
                return
            }
            if (it.getInt(5) != 1) throw IOException("Phiên đồng bộ không được hỗ trợ; dữ liệu được giữ nguyên.")
            if (receipt == null) {
                if (!it.isNull(0) || it.getLong(1) != 0L || it.getInt(2) != 0 || !it.isNull(3) || !it.isNull(4)) {
                    throw IOException("Bản lưu đã liên kết máy chủ. Hãy dùng luồng khôi phục/xử lý hồ sơ trong game; không thể nhập tệp lên phiên đang liên kết.")
                }
            } else if (it.isNull(0) || it.getString(0) != receipt.playerId || it.getLong(1) > receipt.revision ||
                (it.getLong(1) == receipt.revision && !it.isNull(3) && it.getString(3) != receipt.hash)) {
                throw IOException("Bản lưu máy chủ không khớp phiên hoặc đã cũ; tiến độ hiện tại được giữ nguyên.")
            }
        }
    }

    private fun installCloudReceipt(db: SQLiteDatabase, receipt: CloudReceipt) {
        val values = ContentValues().apply {
            put("server_revision", receipt.revision)
            put("last_ack_hash", receipt.hash)
            put("adopted", 1)
            putNull("conflict_code")
            put("origin_kind", "remote-restore")
        }
        if (db.update("cloud_state", values, "singleton=1 AND owner_player_id=? AND schema_version=1", arrayOf(receipt.playerId)) != 1) {
            throw IOException("Phiên người chơi đã thay đổi; chưa ghi đè tiến độ hiện tại.")
        }
    }

    private fun writeBackup(context: Context, snapshot: SaveArchive.Snapshot) {
        val destination = File(context.filesDir, BACKUP_FILE)
        val temporary = File(context.filesDir, BACKUP_FILE + ".pending")
        FileOutputStream(temporary).use { output ->
            SaveArchive.write(snapshot, output)
            output.fd.sync()
        }
        if (!temporary.renameTo(destination)) {
            temporary.delete()
            throw IOException("Không thể tạo bản sao trước khi nhập. Bản lưu hiện tại chưa bị thay đổi.")
        }
    }

    private fun createSchema(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE save_slots (slot_id TEXT PRIMARY KEY NOT NULL, generation INTEGER NOT NULL DEFAULT 0 CHECK(generation>=0), created_at INTEGER NOT NULL, updated_at INTEGER NOT NULL, complete_snapshot_generation INTEGER NOT NULL DEFAULT 0, snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1)), pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1)))")
        db.execSQL("CREATE TABLE save_records (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, record_id INTEGER NOT NULL CHECK(record_id=1), payload BLOB, checksum BLOB, deleted INTEGER NOT NULL DEFAULT 0 CHECK(deleted IN(0,1)), generation INTEGER NOT NULL CHECK(generation>=0), PRIMARY KEY(slot_id,store_name,record_id), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id), CHECK((deleted=1 AND payload IS NULL AND checksum IS NULL) OR (deleted=0 AND payload IS NOT NULL AND checksum IS NOT NULL AND length(checksum)=32 AND length(payload)<=67108864)))")
        db.execSQL("CREATE TABLE save_commits (slot_id TEXT NOT NULL, generation INTEGER NOT NULL, committed_at INTEGER NOT NULL, changed_records INTEGER NOT NULL, is_snapshot INTEGER NOT NULL CHECK(is_snapshot IN(0,1)), snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1)), pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1)), PRIMARY KEY(slot_id,generation), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))")
        db.execSQL("CREATE TABLE legacy_imports (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, imported_at INTEGER NOT NULL, PRIMARY KEY(slot_id,store_name), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))")
        db.execSQL("CREATE INDEX save_records_generation ON save_records(slot_id,generation)")
    }
}
