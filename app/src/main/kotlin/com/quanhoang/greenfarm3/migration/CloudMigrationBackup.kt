package com.quanhoang.greenfarm3.migration

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer
import java.security.DigestOutputStream
import java.security.MessageDigest

/** Recovery evidence for an additive schema upgrade, including incomplete saves. Never auto-executed. */
object CloudMigrationBackup {
    const val FILE_NAME = "greenfarm-before-cloud-migration.gf3dbbackup"
    private val MAGIC = byteArrayOf(71, 70, 51, 68, 66, 75, 48, 49) // GF3DBK01
    private const val FORMAT_VERSION = 1
    private const val MAX_BYTES = 128L * 1024 * 1024
    private const val MAX_ROWS = 65_536
    private const val MAX_RECORD_ROWS = 2_048
    private const val MAX_COLUMNS = 128
    private const val MAX_FIELD_BYTES = 64 * 1024 * 1024
    private val TABLES = listOf("save_slots", "save_records", "save_commits", "legacy_imports", "save_transfer_identity")

    /** Must run in the existing SQLite upgrade transaction before any schema DDL. */
    fun preserve(db: SQLiteDatabase, context: Context) {
        check(db.inTransaction()) { "A consistent pre-migration backup requires the upgrade transaction" }
        val destination = File(context.filesDir, FILE_NAME)
        if (destination.exists()) {
            verifyExisting(destination)
            return // Retain the first verified pre-migration copy.
        }
        val definitions = LinkedHashMap<String, String>()
        var hasRows = false
        for (name in TABLES) {
            db.rawQuery("SELECT sql FROM sqlite_master WHERE type='table' AND name=?", arrayOf(name)).use {
                if (it.moveToFirst()) {
                    if (it.isNull(0)) throw IOException("The original save table definition is missing; progress retained")
                    definitions[name] = it.getString(0)
                }
            }
            if (definitions.containsKey(name)) db.rawQuery("SELECT 1 FROM \"$name\" LIMIT 1", null).use {
                if (it.moveToFirst()) hasRows = true
            }
        }
        if (!hasRows) return // A genuinely new database has no player data to migrate.
        val temporary = File(context.filesDir, "$FILE_NAME.pending")
        var published = false
        try {
            FileOutputStream(temporary).use { file ->
                val bounded = BoundedOutput(file)
                val digest = MessageDigest.getInstance("SHA-256")
                val hashed = DigestOutputStream(bounded, digest)
                val output = DataOutputStream(hashed)
                output.write(MAGIC)
                output.writeInt(FORMAT_VERSION)
                output.writeInt(db.version)
                output.writeInt(definitions.size)
                var totalRows = 0
                for ((name, definition) in definitions) {
                    writeUtf8(output, name, 512)
                    writeUtf8(output, definition, 1024 * 1024)
                    db.rawQuery("SELECT * FROM \"$name\"", null).use { rows ->
                        val columns = rows.columnNames
                        val count = rows.count
                        if (columns.size !in 1..MAX_COLUMNS || count < 0 || count > MAX_ROWS - totalRows ||
                            (name == "save_records" && count > MAX_RECORD_ROWS)) {
                            throw IOException("The pre-migration backup exceeds supported bounds; original progress retained")
                        }
                        totalRows += count
                        output.writeInt(columns.size)
                        columns.forEach { writeUtf8(output, it, 4096) }
                        output.writeInt(count)
                        var copied = 0
                        while (rows.moveToNext()) {
                            for (column in columns.indices) {
                                val type = rows.getType(column)
                                output.writeByte(type)
                                when (type) {
                                    Cursor.FIELD_TYPE_NULL -> Unit
                                    Cursor.FIELD_TYPE_INTEGER -> output.writeLong(rows.getLong(column))
                                    Cursor.FIELD_TYPE_FLOAT -> output.writeDouble(rows.getDouble(column))
                                    Cursor.FIELD_TYPE_STRING -> writeUtf8(output, rows.getString(column), MAX_FIELD_BYTES)
                                    Cursor.FIELD_TYPE_BLOB -> writeBytes(output, rows.getBlob(column), MAX_FIELD_BYTES)
                                    else -> throw IOException("Unknown SQLite value type; original progress retained")
                                }
                            }
                            copied++
                        }
                        if (copied != count) throw IOException("The source snapshot changed during backup; original progress retained")
                    }
                }
                output.flush()
                hashed.on(false)
                output.write(digest.digest())
                output.flush()
                file.fd.sync()
            }
            verifyExisting(temporary)
            if (destination.exists()) {
                verifyExisting(destination)
                return
            }
            if (!temporary.renameTo(destination)) throw IOException("Cannot publish the pre-migration backup; original progress retained")
            published = true
        } finally {
            if (!published) temporary.delete() // Only this operation's unfinished backup, never a database/save.
        }
    }

    private fun writeUtf8(output: DataOutputStream, value: String, limit: Int) =
        writeBytes(output, value.toByteArray(Charsets.UTF_8), limit)

    private fun writeBytes(output: DataOutputStream, value: ByteArray, limit: Int) {
        if (value.size > limit) throw IOException("A backup field exceeds supported bounds; original progress retained")
        output.writeInt(value.size)
        output.write(value)
    }

    private fun verifyExisting(file: File) {
        val length = file.length()
        if (!file.isFile || length !in 52L..MAX_BYTES) throw IOException("The existing migration backup is invalid; original progress retained")
        val digest = MessageDigest.getInstance("SHA-256")
        DataInputStream(file.inputStream()).use { input ->
            val header = ByteArray(12).also(input::readFully)
            if (!header.copyOfRange(0, MAGIC.size).contentEquals(MAGIC) || ByteBuffer.wrap(header, 8, 4).int != FORMAT_VERSION) {
                throw IOException("The existing migration backup format is unsupported; original progress retained")
            }
            digest.update(header)
            val buffer = ByteArray(64 * 1024)
            var remaining = length - header.size - 32
            while (remaining > 0) {
                val count = input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
                if (count <= 0) throw IOException("The migration backup is truncated; original progress retained")
                digest.update(buffer, 0, count)
                remaining -= count
            }
            val expected = ByteArray(32).also(input::readFully)
            if (input.read() != -1 || !MessageDigest.isEqual(expected, digest.digest())) {
                throw IOException("The migration backup checksum is invalid; original progress retained")
            }
        }
    }

    private class BoundedOutput(private val delegate: OutputStream) : OutputStream() {
        private var size = 0L
        private fun reserve(length: Int) {
            if (length < 0 || size > MAX_BYTES - length) throw IOException("The migration backup exceeds128MiB; original progress retained")
            size += length
        }
        override fun write(value: Int) { reserve(1); delegate.write(value) }
        override fun write(bytes: ByteArray, offset: Int, length: Int) { reserve(length); delegate.write(bytes, offset, length) }
        override fun flush() = delegate.flush()
    }
}
