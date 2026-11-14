package com.quanhoang.greenfarm3.S800x480

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.database.sqlite.SQLiteOpenHelper
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.TreeSet
import java.util.concurrent.locks.ReentrantLock
import java.util.regex.Pattern
import java.util.zip.CRC32

/** Durable storage for the unchanged encoded RMS records. A failed read is never a new save. */
object GameSaveStore {
    const val STATUS_IDLE = 0
    const val STATUS_SAVING = 1
    const val STATUS_SAVED = 2
    const val STATUS_FAILED = 3
    const val DATABASE_NAME = "greenfarm-progress.db"
    private const val VERSION = 4
    private const val MAX_RECORD_BYTES = 64 * 1024 * 1024
    private const val SLOT = "device-local"
    private val LOCK = ReentrantLock(true)
    private val LEGACY_FILE = Pattern.compile("^(.+)_1(?:_saved([0-9]+)|_CRC([0-9]+))?$")
    private val TOUCHED = HashSet<String>()
    private val RECORD_CHANGES = HashMap<String, ByteArray?>()
    private val APPLIED_VERSIONS = HashMap<String, Long>()
    private val CAPTURE = ThreadLocal<Capture>()
    private val APPLYING_SNAPSHOT = ThreadLocal<Boolean>()
    private val CACHE_LOCK = Any()
    // Private immutable values: capture never waits for the SQLite writer lock.
    @Volatile private var confirmedRecords: Map<String, ByteArray?> = Collections.emptyMap()
    @Volatile private var logicalRecords: Map<String, ByteArray?> = Collections.emptyMap()
    private var logicalVersions: Map<String, Long> = Collections.emptyMap()
    @Volatile private var logicalReady = false
    @Volatile private var writeDispatcher: WriteDispatcher? = null
    private var acceptedVersion = 0L
    @Volatile private var languageWriteOverride: Int? = null
    private var helper: Helper? = null
    // Keep this field name: the isolated Android durability probe inspects the live connection.
    private var database: SQLiteDatabase? = null
    private var recordWriter: SQLiteStatement? = null
    private var context: Context? = null
    private var depth = 0
    private var poisoned = false
    private var requireWholeFarm = false
    private var writes = 0
    private var nextGeneration = 0L
    @Volatile private var ready = false
    @Volatile private var loading = false
    @Volatile private var protectExistingSnapshot = false
    @Volatile private var currentStatus = STATUS_IDLE
    @Volatile private var error = ""
    @Volatile private var currentGeneration = 0L
    private val db: SQLiteDatabase get() = database!!

    class Failure(message: String, cause: Throwable?) : RuntimeException(message, cause)

    /** Detached absolute values, including null tombstones; safe to retry on one writer. */
    class Snapshot internal constructor(
        captured: Map<String, ByteArray?>,
        capturedVersions: Map<String, Long>,
        internal val wholeFarm: Boolean,
        internal val gameplaySnapshot: Boolean,
        internal val loadingHeaderOnly: Boolean
    ) {
        internal val records: Map<String, ByteArray?> = Collections.unmodifiableMap(HashMap(captured))
        internal val versions: Map<String, Long> = Collections.unmodifiableMap(HashMap(capturedVersions))
        private val bytes: Long = records.values.fold(0L) { total, value -> total + (value?.size ?: 0) }
        fun isWholeFarm(): Boolean = wholeFarm
        fun isGameplaySnapshot(): Boolean = gameplaySnapshot
        fun recordCount(): Int = records.size
        fun byteCount(): Long = bytes
    }

    private class Capture(val wholeFarm: Boolean, val gameplaySnapshot: Boolean, val base: Map<String, ByteArray?>) {
        val records = HashMap<String, ByteArray?>()
        val versions = HashMap<String, Long>()
        var poisoned = false
    }

    @JvmStatic fun beginCapture(wholeFarm: Boolean) = beginCapture(wholeFarm, true)
    @JvmStatic fun beginAuxCapture() = beginCapture(false, false)

    /** Only byte4 changes; decoding is not allowed to synthesize a fresh header. */
    @JvmStatic fun captureLanguageSetting(language: Int): Snapshot {
        if (language < 0 || language > 255 || !logicalReady) {
            throw Failure("Language settings cannot be captured before storage is ready", null)
        }
        synchronized(CACHE_LOCK) {
            val existing = logicalRecords["RMSHeader"]
            if (existing == null || existing.size < 35) throw Failure("The saved language header is missing or truncated", null)
            val header = Arrays.copyOf(existing, existing.size)
            header[4] = language.toByte()
            return acceptSnapshot(hashMapOf("RMSHeader" to header), false, false, true)
        }
    }

    private fun beginCapture(wholeFarm: Boolean, gameplaySnapshot: Boolean) {
        if (CAPTURE.get() != null) throw Failure("A game snapshot is already being captured", null)
        if (loading) throw Failure("Game progress is still loading", null)
        if (!logicalReady) throw Failure("Game storage is not ready for a snapshot", null)
        CAPTURE.set(Capture(wholeFarm, gameplaySnapshot, logicalRecords))
    }

    @JvmStatic fun finishCapture(): Snapshot {
        val capture = CAPTURE.get() ?: throw Failure("No game snapshot is being captured on this thread", null)
        try {
            if (capture.poisoned) throw Failure("The captured game snapshot has already failed", null)
            if (capture.gameplaySnapshot) {
                requireCaptured(capture, "RMSHeader")
                requireCaptured(capture, "save_gameplay")
                requireCaptured(capture, "pending_rewards_v1")
                if (capture.wholeFarm) {
                    requireCaptured(capture, "save_farm0")
                    requireCaptured(capture, "entity_pose_v1")
                }
            }
            val snapshot = Snapshot(capture.records, capture.versions, capture.wholeFarm, capture.gameplaySnapshot, false)
            publishLogicalSnapshot(snapshot)
            return snapshot
        } finally { CAPTURE.remove() }
    }

    /** Patch only the detached header; never swap live locale/font state during a frame. */
    @JvmStatic fun overrideCapturedLanguage(language: Int) {
        val capture = CAPTURE.get() ?: throw Failure("No active game capture", null)
        val header = capture.records["RMSHeader"]
        if (language !in 0..255 || header == null || header.size < 35) {
            throw captureFailed(capture, "Cannot update an incomplete language header")
        }
        val updated = header.copyOf()
        updated[4] = language.toByte()
        captureMutation(capture, "RMSHeader", updated)
    }

    /** Deferred selection also covers direct SDK/options RMSHeader writes. */
    @JvmStatic fun setLanguageWriteOverride(language: Int?) {
        require(language == null || language in 0..255)
        languageWriteOverride = language
    }

    @JvmStatic fun abortCapture() { CAPTURE.remove() }

    private fun requireCaptured(capture: Capture, name: String) {
        if (!capture.records.containsKey(name) || capture.records[name] == null) {
            throw captureFailed(capture, "Game snapshot is incomplete; existing progress was retained")
        }
    }

    @JvmStatic fun coalesceSnapshots(older: Snapshot?, newer: Snapshot?): Snapshot? {
        if (older == null) return newer
        if (newer == null) return older
        val merged = HashMap(older.records)
        val versions = HashMap(older.versions)
        for ((name, value) in newer.records) {
            val prior = versions[name]
            val next = newer.versions[name]!!
            if (prior == null || next >= prior) {
                merged[name] = value
                versions[name] = next
            }
        }
        val gameplay = older.gameplaySnapshot || newer.gameplaySnapshot
        val headerVersion = versions["RMSHeader"]
        val headerOnly = !gameplay && merged.size == 1 && merged["RMSHeader"] != null &&
            ((older.loadingHeaderOnly && headerVersion == older.versions["RMSHeader"]) ||
             (newer.loadingHeaderOnly && headerVersion == newer.versions["RMSHeader"]))
        return Snapshot(merged, versions, older.wholeFarm || newer.wholeFarm, gameplay, headerOnly)
    }

    /** Only the controller's writer calls this method; capture itself does no SQLite I/O. */
    @JvmStatic fun applySnapshot(snapshot: Snapshot?) {
        if (snapshot == null) throw Failure("No captured game snapshot to save", null)
        if (CAPTURE.get() != null) throw Failure("Cannot write SQLite while capturing game progress", null)
        var committed = false
        var begun = false
        APPLYING_SNAPSHOT.set(true)
        try {
            beginSave(snapshot.wholeFarm, snapshot.loadingHeaderOnly)
            begun = true
            for ((name, value) in snapshot.records) {
                val applied = APPLIED_VERSIONS[name]
                if (applied != null && applied > snapshot.versions[name]!!) {
                    TOUCHED.add(name)
                    continue
                }
                mutate(name, value, value == null)
            }
            if (snapshot.gameplaySnapshot) commitSave()
            else {
                try { finishLocked(false) }
                finally { LOCK.unlock() }
            }
            committed = true
            LOCK.lock()
            try {
                for ((name, version) in snapshot.versions) {
                    val applied = APPLIED_VERSIONS[name]
                    if (applied == null || applied < version) APPLIED_VERSIONS[name] = version
                }
            } finally { LOCK.unlock() }
        } finally {
            try { if (!committed && begun) abortSave() }
            finally { APPLYING_SNAPSHOT.remove() }
        }
    }

    private fun captureFailed(capture: Capture, message: String): Failure {
        capture.poisoned = true
        return Failure(message, null)
    }

    private fun captureName(capture: Capture, name: String?): String {
        if (name == null || name.isEmpty() || name.length > 512 || name.indexOf('\u0000') >= 0) {
            throw captureFailed(capture, "Invalid game record name")
        }
        return name.replace('/', 'S')
    }

    fun interface WriteDispatcher { fun changed(mutation: Snapshot) }
    @JvmStatic fun setWriteDispatcher(dispatcher: WriteDispatcher?) { writeDispatcher = dispatcher }
    @JvmStatic fun isCaptureAvailable(): Boolean = logicalReady && !loading

    private fun acceptSnapshot(records: Map<String, ByteArray?>, wholeFarm: Boolean,
        gameplaySnapshot: Boolean, loadingHeaderOnly: Boolean): Snapshot {
        synchronized(CACHE_LOCK) {
            val version = ++acceptedVersion
            val versions = HashMap<String, Long>()
            for (name in records.keys) versions[name] = version
            val snapshot = Snapshot(records, versions, wholeFarm, gameplaySnapshot, loadingHeaderOnly)
            publishLogicalSnapshot(snapshot)
            return snapshot
        }
    }

    private fun publishLogicalSnapshot(snapshot: Snapshot) {
        synchronized(CACHE_LOCK) {
            val accepted = HashMap<String, ByteArray?>()
            val versions = HashMap(logicalVersions)
            for ((name, value) in snapshot.records) {
                val prior = versions[name]
                val next = snapshot.versions[name]!!
                if (prior == null || next >= prior) {
                    accepted[name] = value
                    versions[name] = next
                }
            }
            logicalRecords = overlayRecords(logicalRecords, accepted)
            logicalVersions = Collections.unmodifiableMap(versions)
        }
    }

    private fun overlayRecords(base: Map<String, ByteArray?>, changes: Map<String, ByteArray?>): Map<String, ByteArray?> {
        val updated = HashMap(base)
        for ((name, value) in changes) {
            if (value == null) updated.remove(name) else updated[name] = value
        }
        return Collections.unmodifiableMap(updated)
    }

    private fun dispatchMutation(name: String, value: ByteArray?): Boolean {
        val dispatcher = writeDispatcher ?: return false
        if (loading) return false
        val mutation = acceptSnapshot(hashMapOf(name to value), false, false, false)
        dispatcher.changed(mutation)
        return true
    }

    @JvmStatic fun initialize() {
        val activity = com.gameloft.android.wrapper.au.getContext()
            ?: throw failed("Android context is not ready for game storage", null)
        initialize(activity.applicationContext ?: activity)
    }

    @JvmStatic fun initialize(suppliedContext: Context?) {
        if (suppliedContext == null) throw failed("Android context is not ready for game storage", null)
        LOCK.lock()
        try {
            if (context == null) context = suppliedContext
            openLocked()
        } finally { LOCK.unlock() }
    }

    @JvmStatic fun reopen() {
        LOCK.lock()
        try {
            if (depth != 0) throw failed("Cannot reopen storage while a save is pending", null)
            closeLocked()
            if (context == null) throw failed("Android context is not ready for game storage", null)
            openLocked()
        } finally { LOCK.unlock() }
    }

    @JvmStatic fun close() {
        LOCK.lock()
        try {
            if (depth != 0) throw failed("Cannot close storage while a save is pending", null)
            closeLocked()
            context = null
            confirmedRecords = Collections.emptyMap()
            logicalRecords = Collections.emptyMap()
            logicalVersions = Collections.emptyMap()
            logicalReady = false
            writeDispatcher = null
            languageWriteOverride = null
            APPLIED_VERSIONS.clear()
            loading = false
            protectExistingSnapshot = false
        } finally { LOCK.unlock() }
    }

    private fun closeLocked() {
        recordWriter?.close()
        recordWriter = null
        helper?.close()
        helper = null
        database = null
        ready = false
    }

    private fun openLocked() {
        if (depth != 0) throw failed("Cannot reopen storage during a pending save", null)
        if (context == null) {
            val activity = com.gameloft.android.wrapper.au.getContext()
                ?: throw failed("Android context is not ready for game storage", null)
            context = activity.applicationContext ?: activity
        }
        try {
            if (database?.isOpen != true) {
                recordWriter?.close()
                recordWriter = null
                val newHelper = Helper(context!!)
                helper = newHelper
                newHelper.setWriteAheadLoggingEnabled(true)
                database = newHelper.writableDatabase
            }
            db.beginTransaction()
            try {
                val slot = ContentValues()
                slot.put("slot_id", SLOT)
                slot.put("generation", 0L)
                slot.put("created_at", System.currentTimeMillis())
                slot.put("updated_at", System.currentTimeMillis())
                slot.put("complete_snapshot_generation", 0L)
                slot.put("snapshot_format", 0)
                slot.put("pose_snapshot", 0)
                val inserted = db.insertWithOnConflict("save_slots", null, slot, SQLiteDatabase.CONFLICT_IGNORE)
                if (inserted >= 0) {
                    val previous = db.rawQuery("SELECT MAX(generation) FROM (SELECT generation FROM save_records WHERE slot_id=? UNION ALL SELECT generation FROM save_commits WHERE slot_id=?)", arrayOf(SLOT, SLOT))
                    try {
                        if (previous.moveToFirst() && !previous.isNull(0) && previous.getLong(0) > 0) {
                            val recovered = ContentValues()
                            recovered.put("generation", previous.getLong(0))
                            db.update("save_slots", recovered, "slot_id=?", arrayOf(SLOT))
                        }
                    } finally { previous.close() }
                    val snapshots = db.rawQuery("SELECT generation,snapshot_format,pose_snapshot FROM save_commits WHERE slot_id=? AND is_snapshot=1 ORDER BY generation DESC LIMIT 1", arrayOf(SLOT))
                    try {
                        if (snapshots.moveToFirst() && !snapshots.isNull(0)) {
                            val recovered = ContentValues()
                            recovered.put("complete_snapshot_generation", snapshots.getLong(0))
                            recovered.put("snapshot_format", snapshots.getInt(1))
                            recovered.put("pose_snapshot", snapshots.getInt(2))
                            db.update("save_slots", recovered, "slot_id=?", arrayOf(SLOT))
                        }
                    } finally { snapshots.close() }
                }
                importLegacyLocked()
                val recordCount = db.rawQuery("SELECT COUNT(*) FROM save_records WHERE slot_id=?", arrayOf(SLOT))
                try {
                    recordCount.moveToFirst()
                    val count = recordCount.getLong(0)
                    if (slotNumber("generation") > 0 && count == 0L) throw failed("Previously saved records are missing; progress was not reset", null)
                    if (slotNumber("generation") == 0L && count > 0) {
                        val complete = readLocked("RMSHeader") != null && readLocked("save_gameplay") != null && readLocked("save_farm0") != null
                        val imported = ContentValues()
                        imported.put("generation", 1L)
                        imported.put("updated_at", System.currentTimeMillis())
                        if (complete) imported.put("complete_snapshot_generation", 1L)
                        db.update("save_slots", imported, "slot_id=?", arrayOf(SLOT))
                        db.execSQL("UPDATE save_records SET generation=1 WHERE slot_id=? AND generation=0", arrayOf<Any>(SLOT))
                        val commit = ContentValues()
                        commit.put("slot_id", SLOT)
                        commit.put("generation", 1L)
                        commit.put("committed_at", System.currentTimeMillis())
                        commit.put("changed_records", count)
                        commit.put("is_snapshot", if (complete) 1 else 0)
                        commit.put("snapshot_format", 0)
                        commit.put("pose_snapshot", 0)
                        db.insertOrThrow("save_commits", null, commit)
                    }
                } finally { recordCount.close() }
                if (slotNumber("complete_snapshot_generation") > 0 &&
                    (readLocked("RMSHeader") == null || readLocked("save_gameplay") == null || readLocked("save_farm0") == null)) {
                    throw failed("The saved snapshot is incomplete; progress was not reset", null)
                }
                if (slotNumber("snapshot_format") >= 1 && readLocked("pending_rewards_v1") == null) throw failed("Saved reward state is missing; progress was not reset", null)
                if (slotNumber("pose_snapshot") >= 1 && readLocked("entity_pose_v1") == null) throw failed("Saved entity orientation is missing; progress was not reset", null)
                db.setTransactionSuccessful()
            } finally { db.endTransaction() }
            currentGeneration = slotNumber("generation")
            refreshConfirmedRecordsLocked()
            ready = true
            if (currentStatus == STATUS_FAILED) currentStatus = STATUS_IDLE
            error = ""
        } catch (cause: Exception) { throw failed("Cannot open or migrate the game progress database", cause) }
    }

    @JvmStatic fun beginSave() = beginSave(true)

    @JvmStatic fun beginLoading() {
        LOCK.lock()
        try {
            if (depth != 0) throw failed("Cannot load while a save is pending", null)
            if (!ready) openLocked()
            loading = true
            protectExistingSnapshot = readLocked("save_gameplay") != null
            synchronized(CACHE_LOCK) {
                logicalRecords = confirmedRecords
                logicalVersions = Collections.emptyMap()
            }
        } finally { LOCK.unlock() }
    }

    @JvmStatic fun finishLoading() {
        LOCK.lock()
        try {
            if (!ready) throw failed("Saved game data has not loaded successfully", null)
            loading = false
            protectExistingSnapshot = false
        } finally { LOCK.unlock() }
    }

    @JvmStatic fun failLoad(message: String?) {
        LOCK.lock()
        try {
            loading = true
            protectExistingSnapshot = true
            failed(message ?: "Cannot restore saved progress", null)
        } finally { LOCK.unlock() }
    }
    @JvmStatic fun isLoading(): Boolean = loading
    @JvmStatic fun beginSave(wholeFarm: Boolean) = beginSave(wholeFarm, false)

    private fun beginSave(wholeFarm: Boolean, loadingHeaderOnly: Boolean) {
        if (CAPTURE.get() != null) throw Failure("Cannot open SQLite while capturing game progress", null)
        LOCK.lock()
        var acquired = false
        var started = false
        try {
            if (loading && !loadingHeaderOnly) throw failed("Game progress is still loading", null)
            if (depth == 0) {
                if (!ready) openLocked()
                db.beginTransaction()
                started = true
                poisoned = false
                writes = 0
                TOUCHED.clear()
                RECORD_CHANGES.clear()
                requireWholeFarm = wholeFarm
                nextGeneration = slotNumber("generation") + 1
                currentStatus = STATUS_SAVING
                error = ""
            } else {
                if (poisoned) throw failed("The pending game save has already failed", null)
                requireWholeFarm = requireWholeFarm || wholeFarm
            }
            depth++
            acquired = true
        } catch (cause: Exception) {
            if (started) db.endTransaction()
            throw failed("Cannot begin a game save", cause)
        } finally {
            // Match the original reentrant ownership across serialization and commit.
            if (!acquired) LOCK.unlock()
        }
    }

    @JvmStatic fun commitSave() {
        if (!LOCK.isHeldByCurrentThread || depth == 0) throw Failure("No pending game save on this thread", null)
        try {
            if (depth > 1) { depth--; return }
            try {
                if (!TOUCHED.contains("RMSHeader") || !TOUCHED.contains("save_gameplay") || !TOUCHED.contains("pending_rewards_v1") ||
                    (requireWholeFarm && (!TOUCHED.contains("save_farm0") || !TOUCHED.contains("entity_pose_v1")))) {
                    poisoned = true
                    error = "Game snapshot is incomplete; existing progress was retained"
                }
                if (transactionRecordLocked("RMSHeader") == null || transactionRecordLocked("save_gameplay") == null || transactionRecordLocked("pending_rewards_v1") == null ||
                    (requireWholeFarm && (transactionRecordLocked("save_farm0") == null || transactionRecordLocked("entity_pose_v1") == null))) {
                    poisoned = true
                    error = "Game snapshot records are missing; existing progress was retained"
                }
                if (!requireWholeFarm && transactionRecordLocked("save_farm0") == null) {
                    poisoned = true
                    error = "Own farm must be saved before saving a visited farm"
                }
                if (!requireWholeFarm && slotNumber("pose_snapshot") >= 1 && transactionRecordLocked("entity_pose_v1") == null) {
                    poisoned = true
                    error = "Own-farm orientation must be retained while visiting another farm"
                }
            } catch (cause: Exception) { failed("Cannot verify the complete game snapshot", cause) }
            finishLocked(true)
        } finally { LOCK.unlock() }
    }

    @JvmStatic fun abortSave() {
        if (!LOCK.isHeldByCurrentThread || depth == 0) return
        try {
            poisoned = true
            if (depth > 1) { depth--; return }
            try { db.endTransaction() }
            finally {
                depth = 0
                TOUCHED.clear()
                RECORD_CHANGES.clear()
                currentStatus = STATUS_FAILED
                if (error.isEmpty()) error = "Game save was cancelled; previous progress was retained"
            }
        } finally { LOCK.unlock() }
    }

    private fun finishLocked(snapshot: Boolean) {
        var successful = false
        var metadataChanged = false
        try {
            if (poisoned) throw failed(if (error.isEmpty()) "Game save failed; previous progress was retained" else error, null)
            metadataChanged = snapshot && (slotNumber("complete_snapshot_generation") == 0L || slotNumber("snapshot_format") < 1 ||
                (requireWholeFarm && slotNumber("pose_snapshot") < 1))
            if (writes > 0 || metadataChanged) {
                val slot = ContentValues()
                slot.put("generation", nextGeneration)
                slot.put("updated_at", System.currentTimeMillis())
                if (snapshot) {
                    slot.put("complete_snapshot_generation", nextGeneration)
                    slot.put("snapshot_format", 1)
                    if (requireWholeFarm) slot.put("pose_snapshot", 1)
                }
                if (db.update("save_slots", slot, "slot_id = ?", arrayOf(SLOT)) != 1) throw failed("Game save slot is missing", null)
                val commit = ContentValues()
                commit.put("slot_id", SLOT)
                commit.put("generation", nextGeneration)
                commit.put("committed_at", System.currentTimeMillis())
                commit.put("changed_records", writes)
                commit.put("is_snapshot", if (snapshot) 1 else 0)
                commit.put("snapshot_format", if (snapshot) 1 else 0)
                commit.put("pose_snapshot", if (snapshot) slotNumber("pose_snapshot") else 0L)
                db.insertOrThrow("save_commits", null, commit)
                db.execSQL("DELETE FROM save_commits WHERE slot_id=? AND generation<?", arrayOf<Any>(SLOT, Math.max(0L, nextGeneration - 31)))
            }
            db.setTransactionSuccessful()
            successful = true
        } catch (cause: Exception) { throw failed("Cannot commit game progress; previous save was retained", cause) }
        finally {
            try { db.endTransaction() }
            catch (cause: Exception) {
                successful = false
                throw failed("SQLite could not finish the game save", cause)
            } finally {
                depth = 0
                TOUCHED.clear()
                if (successful) {
                    if (writes > 0 || metadataChanged) currentGeneration = nextGeneration
                    publishCommittedRecordsLocked()
                    currentStatus = STATUS_SAVED
                    error = ""
                }
                RECORD_CHANGES.clear()
            }
        }
    }

    @JvmStatic fun read(storeName: String?): ByteArray? {
        val capture = CAPTURE.get()
        if (capture != null) {
            val name = captureName(capture, storeName)
            val value = if (capture.records.containsKey(name)) capture.records[name] else capture.base[name]
            return value?.copyOf()
        }
        if (writeDispatcher != null && !loading && logicalReady && APPLYING_SNAPSHOT.get() == null) {
            return logicalRecords[detachedName(storeName)]?.copyOf()
        }
        LOCK.lock()
        try {
            if (!ready) {
                if (depth != 0) throw failed("The pending game save has already failed", null)
                openLocked()
            }
            val name = normalizeName(storeName)
            importRequestedLegacyLocked(name)
            val value = readLocked(name)
            if (depth == 0) publishConfirmedRecordLocked(name, value)
            return value
        } catch (cause: Exception) { throw failed("Cannot load saved game data", cause) }
        finally { LOCK.unlock() }
    }

    private fun readLocked(name: String): ByteArray? {
        val cursor = db.query("save_records", arrayOf("payload", "checksum", "deleted"),
            "slot_id = ? AND store_name = ? AND record_id = 1", arrayOf(SLOT, name), null, null, null)
        try {
            if (!cursor.moveToFirst() || cursor.getInt(2) != 0) return null
            val payload = cursor.getBlob(0)
            val expected = cursor.getBlob(1)
            if (payload == null || payload.size > MAX_RECORD_BYTES || expected == null || !MessageDigest.isEqual(expected, digest(payload))) {
                throw failed("Saved record checksum is invalid: $name", null)
            }
            return payload
        } finally { cursor.close() }
    }

    @Throws(IOException::class)
    private fun importRequestedLegacyLocked(name: String) {
        val known = db.query("legacy_imports", arrayOf("store_name"), "slot_id=? AND store_name=?", arrayOf(SLOT, name), null, null, null)
        try { if (known.moveToFirst()) return } finally { known.close() }
        val own = depth == 0
        if (own) db.beginTransaction()
        try {
            val directory = if (com.gameloft.android.wrapper.d.bfb) context!!.getExternalFilesDir(null) else context!!.filesDir
            if (directory == null || !directory.isDirectory) throw IOException("Legacy save directory is unavailable")
            val files = directory.listFiles() ?: throw IOException("Cannot inspect legacy save directory")
            importLegacyStoreLocked(directory, name, files)
            if (own && slotNumber("generation") == 0L && readLocked(name) != null) {
                val imported = ContentValues()
                imported.put("generation", 1L)
                imported.put("updated_at", System.currentTimeMillis())
                db.update("save_slots", imported, "slot_id=?", arrayOf(SLOT))
                db.execSQL("UPDATE save_records SET generation=1 WHERE slot_id=? AND generation=0", arrayOf<Any>(SLOT))
            }
            if (own) db.setTransactionSuccessful()
        } finally { if (own) db.endTransaction() }
        if (own) currentGeneration = slotNumber("generation")
    }

    @JvmStatic fun write(storeName: String?, payload: ByteArray?, offset: Int, length: Int) {
        val language = languageWriteOverride
        if (storeName == "RMSHeader" && language != null && validPayload(payload, offset, length) && length >= 35) {
            val header = Arrays.copyOfRange(payload!!, offset, offset + length)
            header[4] = language.toByte()
            writeRecord(storeName, header, 0, header.size)
        } else writeRecord(storeName, payload, offset, length)
    }

    private fun writeRecord(storeName: String?, payload: ByteArray?, offset: Int, length: Int) {
        val capture = CAPTURE.get()
        if (capture != null) {
            if (!validPayload(payload, offset, length)) throw captureFailed(capture, "Invalid game record payload")
            val name = captureName(capture, storeName)
            if (capture.poisoned) throw captureFailed(capture, "The captured game snapshot has already failed")
            captureMutation(capture, name, Arrays.copyOfRange(payload!!, offset, offset + length))
            return
        }
        if (writeDispatcher != null && !loading && APPLYING_SNAPSHOT.get() == null) {
            if (!validPayload(payload, offset, length)) throw Failure("Invalid game record payload", null)
            val name = detachedName(storeName)
            if (deferWhileLoading(name)) return
            if (dispatchMutation(name, Arrays.copyOfRange(payload!!, offset, offset + length))) return
        }
        LOCK.lock()
        try {
            if (!validPayload(payload, offset, length)) throw failed("Invalid game record payload", null)
            val name = normalizeName(storeName)
            if (deferWhileLoading(name)) return
            mutate(name, Arrays.copyOfRange(payload!!, offset, offset + length), false)
        } finally { LOCK.unlock() }
    }

    private fun validPayload(payload: ByteArray?, offset: Int, length: Int): Boolean =
        payload != null && offset >= 0 && length >= 0 && length <= MAX_RECORD_BYTES && offset <= payload.size - length

    @JvmStatic fun delete(storeName: String?) {
        val capture = CAPTURE.get()
        if (capture != null) {
            val name = captureName(capture, storeName)
            if (capture.poisoned) throw captureFailed(capture, "The captured game snapshot has already failed")
            captureMutation(capture, name, null)
            return
        }
        if (writeDispatcher != null && !loading && APPLYING_SNAPSHOT.get() == null) {
            val name = detachedName(storeName)
            if (deferWhileLoading(name)) return
            if (dispatchMutation(name, null)) return
        }
        LOCK.lock()
        try {
            val name = normalizeName(storeName)
            if (deferWhileLoading(name)) return
            mutate(name, null, true)
        } finally { LOCK.unlock() }
    }

    private fun captureMutation(capture: Capture, name: String, value: ByteArray?) {
        if (!capture.wholeFarm && (name == "save_farm0" || name == "entity_pose_v1")) {
            if (value == null || !Arrays.equals(capture.base[name], value)) {
                throw captureFailed(capture, "A visited-farm snapshot cannot replace the saved own farm or its orientation")
            }
            return
        }
        capture.records[name] = value
        synchronized(CACHE_LOCK) { capture.versions[name] = ++acceptedVersion }
    }

    private fun deferWhileLoading(name: String): Boolean =
        loading && (protectExistingSnapshot || name == "save_gameplay" || name.startsWith("save_farm"))

    private fun mutate(name: String, payload: ByteArray?, deleted: Boolean) {
        LOCK.lock()
        val own = depth == 0
        try {
            if (!ready) {
                if (!own) throw failed("The pending game save has already failed", null)
                openLocked()
            }
            if (!own && poisoned) throw failed("The pending game save has already failed", null)
            if (own) {
                db.beginTransaction()
                poisoned = false
                writes = 0
                TOUCHED.clear()
                RECORD_CHANGES.clear()
                nextGeneration = slotNumber("generation") + 1
                depth = 1
                currentStatus = STATUS_SAVING
            }
            TOUCHED.add(name)
            // Confirmed cache is published only after COMMIT under this same lock.
            // Captured saves need no SELECT + SHA-256 of every unchanged blob.
            val cached = !own && APPLYING_SNAPSHOT.get() == true && logicalReady
            val current = if (cached) {
                if (RECORD_CHANGES.containsKey(name)) RECORD_CHANGES[name] else confirmedRecords[name]
            } else readLocked(name)
            if (!own && !requireWholeFarm && (name == "save_farm0" || name == "entity_pose_v1") &&
                (deleted || !Arrays.equals(current, payload))) {
                throw failed("A visited-farm save cannot replace the saved own farm or its orientation", null)
            }
            // Logical caches intentionally omit deleted names. A missing cached
            // value is therefore not evidence that SQLite already has a tombstone.
            // Query only its deleted bit, preserving first-delete/legacy-import
            // protection while avoiding duplicate INSERTs and metadata commits.
            val alreadyDeleted = deleted && if (cached && RECORD_CHANGES.containsKey(name)) {
                RECORD_CHANGES[name] == null
            } else if (cached && current != null) {
                false
            } else isDeletedLocked(name)
            if ((deleted && !alreadyDeleted) || (!deleted && !Arrays.equals(current, payload))) {
                putLocked(name, payload, deleted, nextGeneration)
                RECORD_CHANGES[name] = if (deleted) null else payload
                writes++
            }
            if (own) finishLocked(false)
        } catch (cause: Exception) {
            poisoned = true
            if (own && depth != 0) {
                try { db.endTransaction() }
                finally { depth = 0; TOUCHED.clear(); RECORD_CHANGES.clear() }
            }
            throw failed("Cannot save game data; changes remain unsaved", cause)
        } finally { LOCK.unlock() }
    }

    /** One writer owns LOCK; its transaction overlay is authoritative after each checked INSERT. */
    private fun transactionRecordLocked(name: String): ByteArray? {
        if (APPLYING_SNAPSHOT.get() == true && logicalReady) {
            return if (RECORD_CHANGES.containsKey(name)) RECORD_CHANGES[name] else confirmedRecords[name]
        }
        return readLocked(name)
    }

    private fun isDeletedLocked(name: String): Boolean {
        val cursor = db.query("save_records", arrayOf("deleted"), "slot_id=? AND store_name=? AND record_id=1", arrayOf(SLOT, name), null, null, null)
        try { return cursor.moveToFirst() && cursor.getInt(0) != 0 }
        finally { cursor.close() }
    }

    private fun putLocked(name: String, payload: ByteArray?, deleted: Boolean, generation: Long) {
        val statement = recordWriter ?: db.compileStatement(
            "INSERT OR REPLACE INTO save_records " +
                "(slot_id,store_name,record_id,payload,checksum,deleted,generation) VALUES (?,?,1,?,?,?,?)"
        ).also { recordWriter = it }
        statement.clearBindings()
        statement.bindString(1, SLOT)
        statement.bindString(2, name)
        if (deleted) {
            statement.bindNull(3)
            statement.bindNull(4)
        } else {
            statement.bindBlob(3, payload!!)
            statement.bindBlob(4, digest(payload))
        }
        statement.bindLong(5, if (deleted) 1L else 0L)
        statement.bindLong(6, generation)
        if (statement.executeInsert() < 0) throw failed("SQLite did not write the game record", null)
    }

    @JvmStatic fun hasGameplaySave(): Boolean {
        val capture = CAPTURE.get()
        if (capture != null) return (if (capture.records.containsKey("save_gameplay")) capture.records["save_gameplay"] else capture.base["save_gameplay"]) != null
        if (writeDispatcher != null && !loading && logicalReady && APPLYING_SNAPSHOT.get() == null) return logicalRecords["save_gameplay"] != null
        LOCK.lock()
        try {
            if (!ready) openLocked()
            return readLocked("save_gameplay") != null
        } catch (cause: Exception) { throw failed("Cannot inspect saved progress", cause) }
        finally { LOCK.unlock() }
    }

    @JvmStatic fun status(): Int = currentStatus
    @JvmStatic fun lastError(): String = error
    @JvmStatic fun generation(): Long = currentGeneration
    @JvmStatic fun isReady(): Boolean = ready

    private fun slotNumber(column: String): Long {
        val cursor = db.query("save_slots", arrayOf(column), "slot_id = ?", arrayOf(SLOT), null, null, null)
        try {
            if (!cursor.moveToFirst()) throw failed("Game save slot is missing", null)
            return cursor.getLong(0)
        } finally { cursor.close() }
    }

    private fun failed(message: String, cause: Throwable?): Failure {
        poisoned = true
        ready = false
        currentStatus = STATUS_FAILED
        error = message
        return Failure(message, cause)
    }

    private fun normalizeName(name: String?): String {
        if (name == null || name.isEmpty() || name.length > 512 || name.indexOf('\u0000') >= 0) throw failed("Invalid game record name", null)
        return name.replace('/', 'S')
    }
    private fun detachedName(name: String?): String {
        if (name == null || name.isEmpty() || name.length > 512 || name.indexOf('\u0000') >= 0) throw Failure("Invalid game record name", null)
        return name.replace('/', 'S')
    }

    private fun refreshConfirmedRecordsLocked() {
        val records = HashMap<String, ByteArray?>()
        val names = db.query("save_records", arrayOf("store_name"), "slot_id=? AND record_id=1 AND deleted=0", arrayOf(SLOT), null, null, null)
        try {
            while (names.moveToNext()) {
                val name = names.getString(0)
                records[name] = readLocked(name)
            }
        } finally { names.close() }
        confirmedRecords = Collections.unmodifiableMap(records)
        if (!logicalReady) synchronized(CACHE_LOCK) {
            logicalRecords = confirmedRecords
            logicalReady = true
        }
    }

    private fun publishConfirmedRecordLocked(name: String, value: ByteArray?) {
        val change = hashMapOf(name to value?.copyOf())
        confirmedRecords = overlayRecords(confirmedRecords, change)
        if (writeDispatcher == null && APPLYING_SNAPSHOT.get() == null) synchronized(CACHE_LOCK) {
            if (!logicalVersions.containsKey(name)) logicalRecords = overlayRecords(logicalRecords, change)
        }
    }

    private fun publishCommittedRecordsLocked() {
        if (RECORD_CHANGES.isEmpty()) return
        confirmedRecords = overlayRecords(confirmedRecords, RECORD_CHANGES)
        if (APPLYING_SNAPSHOT.get() == null) acceptSnapshot(RECORD_CHANGES, false, false, false)
    }

    private fun digest(data: ByteArray): ByteArray {
        try { return MessageDigest.getInstance("SHA-256").digest(data) }
        catch (cause: NoSuchAlgorithmException) { throw failed("Game checksum algorithm is unavailable", cause) }
    }

    @Throws(IOException::class)
    private fun importLegacyLocked() {
        val directory = if (com.gameloft.android.wrapper.d.bfb) context!!.getExternalFilesDir(null) else context!!.filesDir
        if (directory == null || !directory.isDirectory) throw IOException("Legacy save directory is unavailable")
        val files = directory.listFiles() ?: throw IOException("Cannot inspect legacy save directory")
        val stores = TreeSet<String>()
        for (file in files) {
            val matcher = LEGACY_FILE.matcher(file.name)
            if (matcher.matches()) {
                val name = matcher.group(1)
                val header = File(directory, name)
                if ((header.isFile && header.length() == 1L) || isGameplayName(name)) stores.add(name)
            }
            if (file.isFile && file.length() == 1L && isGameplayName(file.name)) stores.add(file.name)
        }
        for (name in stores) importLegacyStoreLocked(directory, name, files)
    }

    private fun isGameplayName(name: String): Boolean = name == "RMSHeader" || name == "save_gameplay" ||
        name.matches(Regex("save_farm[0-9]+")) || name == "online_gameplay" || name.startsWith("RMS_") ||
        name == "GDPRUPDATEFlag" || name == "ADSUPDATEFlag"

    @Throws(IOException::class)
    private fun importLegacyStoreLocked(directory: File, name: String, files: Array<File>) {
        val known = db.query("legacy_imports", arrayOf("store_name"), "slot_id = ? AND store_name = ?", arrayOf(SLOT, name), null, null, null)
        try { if (known.moveToFirst()) return } finally { known.close() }
        val existing = db.query("save_records", arrayOf("store_name"), "slot_id = ? AND store_name = ? AND record_id = 1", arrayOf(SLOT, name), null, null, null)
        val present: Boolean
        try { present = existing.moveToFirst() } finally { existing.close() }
        if (!present && (!isPrimaryStateRecord(name) || slotNumber("generation") == 0L)) {
            val value = readLegacy(directory, name, files)
            if (value != null) putLocked(name, value, false, 0)
        }
        val imported = ContentValues()
        imported.put("slot_id", SLOT)
        imported.put("store_name", name)
        imported.put("imported_at", System.currentTimeMillis())
        db.insertOrThrow("legacy_imports", null, imported)
    }

    private fun isPrimaryStateRecord(name: String): Boolean = isGameplayName(name) || name == "pending_rewards_v1" || name == "entity_pose_v1"

    @Throws(IOException::class)
    private fun readLegacy(directory: File, name: String, files: Array<File>): ByteArray? {
        val generations = TreeSet<Int>()
        val prefix = name + "_1_saved"
        var artifacts = false
        for (file in files) {
            if (file.name.startsWith(name + "_1")) artifacts = true
            if (file.name.startsWith(prefix)) {
                try {
                    val generation = Integer.parseInt(file.name.substring(prefix.length))
                    if (generation >= 1 && generation <= 10000) generations.add(generation)
                } catch (_: NumberFormatException) { /* unrelated filename */ }
            }
        }
        while (!generations.isEmpty()) {
            val generation = generations.last()
            generations.remove(generation)
            val record = File(directory, prefix + generation)
            val crc = File(directory, name + "_1_CRC" + generation)
            if (!crc.isFile || crc.length() != 8L) continue
            try {
                val payload = readLegacyPayload(record)
                val input = DataInputStream(FileInputStream(crc))
                val expected: Long
                try { expected = input.readLong() } finally { input.close() }
                val actual = CRC32()
                actual.update(payload)
                if (expected == actual.value) return payload
            } catch (_: IOException) { /* try an earlier valid backup */ }
        }
        val canonical = File(directory, name + "_1")
        if (canonical.isFile) return readLegacyPayload(canonical)
        val header = File(directory, name)
        if (header.isFile) {
            val input = FileInputStream(header)
            val count: Int
            try { count = input.read() } finally { input.close() }
            if (count > 0) throw IOException("Legacy save record is missing: $name")
        }
        if (artifacts) throw IOException("No valid legacy save generation: $name")
        return null
    }

    @Throws(IOException::class)
    private fun readLegacyPayload(file: File): ByteArray {
        val input = DataInputStream(FileInputStream(file))
        try {
            val id = input.readUnsignedByte()
            val length = input.readInt()
            if (id != 1 || length < 0 || length > MAX_RECORD_BYTES || file.length() != 5L + length) throw IOException("Invalid legacy save record: " + file.name)
            val payload = ByteArray(length)
            input.readFully(payload)
            return payload
        } finally { input.close() }
    }

    private class CorruptionHandler : DatabaseErrorHandler {
        override fun onCorruption(broken: SQLiteDatabase) {
            throw failed("Game progress database is corrupt; data was retained", null)
        }
    }

    private class Helper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION, CorruptionHandler()) {
        override fun onConfigure(db: SQLiteDatabase) {
            db.setForeignKeyConstraintsEnabled(true)
            db.execSQL("PRAGMA synchronous=FULL")
            db.rawQuery("PRAGMA busy_timeout=" + SaveBatchPolicy.BUSY_TIMEOUT_MILLIS, null).use { it.moveToFirst() }
        }
        override fun onCreate(db: SQLiteDatabase) {
            createVersionOne(db)
            upgradeOneToTwo(db)
            upgradeTwoToThree(db)
            upgradeThreeToFour(db)
        }
        override fun onOpen(db: SQLiteDatabase) {
            val slots = hasTable(db, "save_slots")
            val records = hasTable(db, "save_records")
            val commits = hasTable(db, "save_commits")
            val imports = hasTable(db, "legacy_imports")
            if (!slots && !records && !commits && !imports) throw failed("The progress database schema is missing; data was not reset", null)
            var previousGeneration = 0L
            if (slots) previousGeneration = Math.max(previousGeneration, maxGeneration(db, "save_slots"))
            if (commits) previousGeneration = Math.max(previousGeneration, maxGeneration(db, "save_commits"))
            if (previousGeneration > 0 && (!records || !hasAnyRecords(db))) throw failed("Saved SQLite records are missing; legacy files were not used to reset progress", null)
            db.beginTransaction()
            try {
                createVersionOne(db)
                val columns = db.rawQuery("PRAGMA table_info(save_slots)", null)
                var completeColumn = false
                try {
                    while (columns.moveToNext()) if (columns.getString(1) == "complete_snapshot_generation") completeColumn = true
                } finally { columns.close() }
                if (!completeColumn) upgradeOneToTwo(db)
                upgradeTwoToThree(db)
                upgradeThreeToFour(db)
                db.setTransactionSuccessful()
            } finally { db.endTransaction() }
        }
        private fun hasTable(db: SQLiteDatabase, name: String): Boolean {
            val cursor = db.rawQuery("SELECT 1 FROM sqlite_master WHERE type='table' AND name=?", arrayOf(name))
            try { return cursor.moveToFirst() } finally { cursor.close() }
        }
        private fun maxGeneration(db: SQLiteDatabase, table: String): Long {
            val cursor = db.rawQuery("SELECT MAX(generation) FROM $table WHERE slot_id=?", arrayOf(SLOT))
            try { return if (cursor.moveToFirst() && !cursor.isNull(0)) cursor.getLong(0) else 0L }
            finally { cursor.close() }
        }
        private fun hasAnyRecords(db: SQLiteDatabase): Boolean {
            val cursor = db.rawQuery("SELECT 1 FROM save_records WHERE slot_id=? LIMIT 1", arrayOf(SLOT))
            try { return cursor.moveToFirst() } finally { cursor.close() }
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            if (oldVersion < 1 || oldVersion > VERSION || newVersion != VERSION) throw failed("Unsupported progress database migration; data was retained", null)
            if (oldVersion < 2) upgradeOneToTwo(db)
            if (oldVersion < 3) upgradeTwoToThree(db)
            if (oldVersion < 4) upgradeThreeToFour(db)
        }
        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            throw failed("Cannot downgrade the progress database; data was retained", null)
        }
        private fun createVersionOne(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS save_slots (slot_id TEXT PRIMARY KEY NOT NULL, generation INTEGER NOT NULL DEFAULT 0 CHECK(generation>=0), created_at INTEGER NOT NULL, updated_at INTEGER NOT NULL)")
            db.execSQL("CREATE TABLE IF NOT EXISTS save_records (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, record_id INTEGER NOT NULL CHECK(record_id=1), payload BLOB, checksum BLOB, deleted INTEGER NOT NULL DEFAULT 0 CHECK(deleted IN(0,1)), generation INTEGER NOT NULL CHECK(generation>=0), PRIMARY KEY(slot_id,store_name,record_id), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id), CHECK((deleted=1 AND payload IS NULL AND checksum IS NULL) OR (deleted=0 AND payload IS NOT NULL AND checksum IS NOT NULL AND length(checksum)=32 AND length(payload)<=67108864)))")
            db.execSQL("CREATE TABLE IF NOT EXISTS save_commits (slot_id TEXT NOT NULL, generation INTEGER NOT NULL, committed_at INTEGER NOT NULL, changed_records INTEGER NOT NULL, is_snapshot INTEGER NOT NULL CHECK(is_snapshot IN(0,1)), PRIMARY KEY(slot_id,generation), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))")
            db.execSQL("CREATE TABLE IF NOT EXISTS legacy_imports (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, imported_at INTEGER NOT NULL, PRIMARY KEY(slot_id,store_name), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))")
            db.execSQL("CREATE INDEX IF NOT EXISTS save_records_generation ON save_records(slot_id,generation)")
        }
        private fun upgradeOneToTwo(db: SQLiteDatabase) {
            db.execSQL("ALTER TABLE save_slots ADD COLUMN complete_snapshot_generation INTEGER NOT NULL DEFAULT 0")
            db.execSQL("UPDATE save_slots SET complete_snapshot_generation=generation WHERE EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_gameplay' AND deleted=0) AND EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_farm0' AND deleted=0)")
        }
        private fun upgradeTwoToThree(db: SQLiteDatabase) {
            if (!hasColumn(db, "save_slots", "snapshot_format")) db.execSQL("ALTER TABLE save_slots ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))")
            if (!hasColumn(db, "save_commits", "snapshot_format")) db.execSQL("ALTER TABLE save_commits ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))")
        }
        private fun upgradeThreeToFour(db: SQLiteDatabase) {
            if (!hasColumn(db, "save_slots", "pose_snapshot")) db.execSQL("ALTER TABLE save_slots ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1))")
            if (!hasColumn(db, "save_commits", "pose_snapshot")) db.execSQL("ALTER TABLE save_commits ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1))")
        }
        private fun hasColumn(db: SQLiteDatabase, table: String, name: String): Boolean {
            val cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null)
            try {
                while (cursor.moveToNext()) if (name == cursor.getString(1)) return true
                return false
            } finally { cursor.close() }
        }
    }
}
