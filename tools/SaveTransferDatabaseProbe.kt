package com.quanhoang.greenfarm3.testsupport

import android.content.Context
import android.content.ContextWrapper
import android.database.sqlite.SQLiteDatabase
import com.quanhoang.greenfarm3.migration.SaveArchive
import com.quanhoang.greenfarm3.migration.SaveArchiveDatabase
import com.quanhoang.greenfarm3.migration.SaveTransferGate
import com.quanhoang.greenfarm3.migration.SaveTransferIdentity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/** Synthetic opaque records in isolated directories, never a gameplay or player-data probe. */
object SaveTransferDatabaseProbe {
    private class FixtureContext(private val root: File, private val appPackage: String, base: Context?) : ContextWrapper(base) {
        override fun getFilesDir(): File = File(root, "files").also { it.mkdirs() }
        override fun getDatabasePath(name: String): File = File(root, "databases/$name").also { it.parentFile?.mkdirs() }
        override fun getPackageName(): String = appPackage
        override fun getApplicationContext(): Context = this
    }

    private const val BINDING = "12345678-abcd-1234-abcd-123456789012"
    private val checks = ArrayList<String>()
    private fun check(value: Boolean, message: String) {
        if (!value) throw AssertionError(message)
        checks.add(message)
        println("PASS " + message)
    }
    private fun fixture(): SaveArchive.Snapshot {
        val binding = BINDING.toByteArray(Charsets.UTF_8)
        val gameplay = ByteArray(96)
        gameplay[0] = 4
        ByteBuffer.wrap(gameplay, 1, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(binding.size)
        binding.copyInto(gameplay, 5)
        fun words(magic: Int, size: Int): ByteArray = ByteBuffer.allocate(12 + size * 4)
            .putInt(magic).putInt(1).putInt(1).apply { repeat(size) { putInt(0) } }.array()
        return SaveArchive.Snapshot("com.gameloft.android.ANMP.GloftGF2F", 17, 17, 1, 1, 1000, 2000, listOf(
            SaveArchive.Record("RMSHeader", ByteArray(35).also { it[4] = 16 }, 2),
            SaveArchive.Record("save_gameplay", gameplay, 17),
            SaveArchive.Record("save_farm0", ByteArray(128) { (it * 7).toByte() }, 16),
            SaveArchive.Record("pending_rewards_v1", words(0x47465257, 28), 17),
            SaveArchive.Record("entity_pose_v1", words(0x47465053, 6), 16),
            SaveArchive.Record("RMS_TimeClock", ByteArray(24) { it.toByte() }, 10),
            SaveArchive.Record("removed-record", null, 15)))
    }
    private fun same(left: SaveArchive.Snapshot, right: SaveArchive.Snapshot): Boolean =
        left.generation == right.generation && left.completeSnapshotGeneration == right.completeSnapshotGeneration &&
            left.snapshotFormat == right.snapshotFormat && left.poseSnapshot == right.poseSnapshot &&
            left.createdAtMillis == right.createdAtMillis && left.updatedAtMillis == right.updatedAtMillis &&
            left.records.size == right.records.size && left.records.all { record ->
                right.records.any { it.name == record.name && it.generation == record.generation && it.payload.contentEquals(record.payload) }
            }

    private fun database(context: Context): SQLiteDatabase = SQLiteDatabase.openDatabase(
        context.getDatabasePath(SaveArchive.DATABASE_NAME).absolutePath, null,
        SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING)
        .also { it.execSQL("PRAGMA synchronous=FULL") }

    private fun journalMode(context: Context): String = SQLiteDatabase.openDatabase(
        context.getDatabasePath(SaveArchive.DATABASE_NAME).absolutePath, null, SQLiteDatabase.OPEN_READONLY).use { db ->
        db.rawQuery("PRAGMA journal_mode", null).use { it.moveToFirst(); it.getString(0) }
    }

    @JvmStatic fun main(args: Array<String>) {
        try {
            println("SAVE_TRANSFER_PROBE_START")
            val path = File(args.single()).canonicalFile
            execute(path, null)
        } catch (failure: Throwable) {
            // RuntimeInit's uncaught handler otherwise SIGKILLs app_process and
            // hides the actual Java exception behind an unhelpful exit137.
            failure.printStackTrace(System.out)
            System.exit(2)
        }
    }

    @JvmStatic fun runInContext(context: Context, prefix: String): List<String> {
        require(prefix.matches(Regex("st_[A-Za-z0-9_-]{1,32}")))
        execute(File(context.filesDir, "save-transfer-probe-" + prefix).canonicalFile, context)
        return ArrayList(checks)
    }

    private fun execute(path: File, base: Context?) {
        checks.clear()
        require(path.name.matches(Regex("save-transfer-probe-[A-Za-z0-9_-]{1,48}")) && path.parentFile.name == "files")
        require(!path.exists()) { "Choose a fresh fixture directory" }
        path.mkdirs()
        val source = FixtureContext(File(path, "source"), "com.gameloft.android.ANMP.GloftGF2F", base)
        val target = FixtureContext(File(path, "target"), "com.quanhoang.greenfarm3", base)
        val snapshot = fixture()
        SaveTransferGate.withTransferLock(source) { SaveArchiveDatabase.install(source, snapshot) }
        check(journalMode(source).equals("wal", true), "source WAL active before export")
        val exported = SaveTransferGate.withTransferLock(source) { SaveArchiveDatabase.read(source) }
        check(same(snapshot, exported), "SELECT-only transaction exports exact committed snapshot")
        check(journalMode(source).equals("wal", true), "source WAL unchanged after export")
        val bytes = ByteArrayOutputStream().also { SaveArchive.write(exported, it) }.toByteArray()
        val decoded = SaveArchive.read(ByteArrayInputStream(bytes))
        SaveTransferGate.withTransferLock(target) { SaveArchiveDatabase.install(target, decoded) }
        check(same(snapshot, SaveArchiveDatabase.read(target)), "new-package import retains every opaque record byte and marker")
        check(SaveArchiveDatabase.read(target).records.single { it.name == "RMSHeader" }.payload!![4].toInt() == 16,
            "language retained")
        check(SaveArchiveDatabase.read(target).records.single { it.name == "removed-record" }.payload == null,
            "tombstone retained")
        SQLiteDatabase.openDatabase(target.getDatabasePath(SaveArchive.DATABASE_NAME).absolutePath, null, SQLiteDatabase.OPEN_READONLY).use { db ->
            check(db.version == 4, "SQLite schema4 retained")
            db.rawQuery("PRAGMA journal_mode", null).use { it.moveToFirst(); check(it.getString(0).equals("wal", true), "WAL retained") }
        }
        SaveTransferIdentity.initialize(target)
        check(SaveTransferIdentity.matches(BINDING, "new-device-binding"), "explicit imported binding accepted")
        check(!SaveTransferIdentity.matches("other-binding", "new-device-binding"), "unrelated binding rejected")
        check(SaveTransferIdentity.matches("new-device-binding", "new-device-binding"), "current binding still accepted")
        check(!SaveTransferIdentity.matches(null, null), "null identity rejected")
        val altered = FixtureContext(File(path, "altered"), "com.quanhoang.greenfarm3", base)
        SaveArchiveDatabase.install(altered, snapshot)
        database(altered).use { db ->
            val changed = snapshot.records.single { it.name == "save_gameplay" }.payload!!.copyOf().also { it[it.lastIndex] = 99 }
            val values = android.content.ContentValues().apply { put("payload", changed); put("checksum", SaveArchive.checksum(changed)) }
            db.update("save_records", values, "store_name='save_gameplay'", null)
        }
        SaveTransferIdentity.initialize(altered)
        check(!SaveTransferIdentity.matches(BINDING, "new-device-binding"), "binding authorization cannot cover another gameplay payload")
        val newer = snapshot.copy(generation = 18, completeSnapshotGeneration = 18, updatedAtMillis = 3000,
            records = snapshot.records.map { if (it.name == "save_farm0") it.copy(generation = 18, payload = it.payload!!.copyOf().also { b -> b[0] = 42 }) else it })
        database(target).use { db -> db.execSQL("CREATE TRIGGER reject_transfer BEFORE INSERT ON save_records WHEN NEW.store_name='entity_pose_v1' BEGIN SELECT RAISE(ABORT,'injected import failure'); END") }
        var failed = false
        try { SaveArchiveDatabase.install(target, newer) } catch (_: android.database.SQLException) { failed = true }
        check(failed, "injected import failure observed")
        check(same(snapshot, SaveArchiveDatabase.read(target)), "failed transaction preserves existing world exactly")
        val backup = File(target.filesDir, SaveArchiveDatabase.BACKUP_FILE)
        check(backup.isFile && same(snapshot, backup.inputStream().use(SaveArchive::read)), "synced pre-import backup retained")
        database(target).use { it.execSQL("DROP TRIGGER reject_transfer") }
        SaveArchiveDatabase.install(target, newer)
        check(same(newer, SaveArchiveDatabase.read(target)), "retry imports one absolute snapshot without additive rewards")
        val corrupt = FixtureContext(File(path, "corrupt"), "com.quanhoang.greenfarm3", base)
        SaveArchiveDatabase.install(corrupt, snapshot)
        val corruptFile = corrupt.getDatabasePath(SaveArchive.DATABASE_NAME)
        RandomAccessFile(corruptFile, "rw").use { it.seek(0); it.write(ByteArray(100) { 42 }) }
        val corruptBytes = corruptFile.readBytes()
        var rejected = false
        try { SaveArchiveDatabase.read(corrupt) } catch (_: android.database.SQLException) { rejected = true }
        check(rejected, "corrupt source export rejected")
        check(corruptFile.isFile && corruptFile.readBytes().contentEquals(corruptBytes), "corrupt source file preserved byte exactly")
        rejected = false
        try { SaveArchiveDatabase.install(corrupt, newer) } catch (_: android.database.SQLException) { rejected = true }
        check(rejected, "import refuses corrupt existing destination")
        check(corruptFile.isFile && corruptFile.readBytes().contentEquals(corruptBytes), "failed import does not delete or rewrite corrupt destination")
        SaveTransferIdentity.initialize(corrupt)
        rejected = false
        try { SaveTransferIdentity.matches(BINDING, "new-device-binding") } catch (_: android.database.SQLException) { rejected = true }
        check(rejected, "identity lookup refuses corrupt database")
        check(corruptFile.isFile && corruptFile.readBytes().contentEquals(corruptBytes), "identity lookup does not delete corrupt save")
        check(same(newer, SaveArchiveDatabase.read(target)), "corrupt fixture failure cannot affect another valid destination")
        val gate = FixtureContext(File(path, "gate"), "com.quanhoang.greenfarm3", base)
        check(SaveTransferGate.enterGame(gate), "game process gate acquired")
        var blocked = false
        try { SaveTransferGate.withTransferLock(gate) { throw AssertionError("transfer ran while game lock was held") } }
        catch (_: IOException) { blocked = true }
        check(blocked, "transfer blocked while game lifetime lock is held")
        check(SaveTransferGate.beginPreparation(), "preparation owns gameplay freeze")
        check(SaveTransferGate.isPreparingTransfer(), "preparation freeze visible to game frame/input gate")
        check(!SaveTransferGate.beginPreparation(), "duplicate preparation cannot start another checkpoint")
        SaveTransferGate.cancelPreparation()
        check(!SaveTransferGate.isPreparingTransfer(), "failed or cancelled preparation releases gameplay freeze")
        println("SAVE_TRANSFER_PROBE_PASS checks=" + checks.size)
    }
}
