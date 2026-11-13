package com.quanhoang.greenfarm3.migration

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabaseCorruptException
import java.security.MessageDigest

/** A user-approved imported record may retain its original binding without changing SDK authentication. */
object SaveTransferIdentity {
    @Volatile private var context: Context? = null
    @Volatile private var checked = false
    @Volatile private var importedBinding: String? = null
    private val preserveCorruptDatabase = DatabaseErrorHandler {
        throw SQLiteDatabaseCorruptException("Không thể đọc định danh bản lưu bị hỏng; dữ liệu được giữ nguyên.")
    }

    @JvmStatic @Synchronized fun initialize(supplied: Context) {
        val application = supplied.applicationContext ?: supplied
        if (context?.getDatabasePath(SaveArchive.DATABASE_NAME) != application.getDatabasePath(SaveArchive.DATABASE_NAME)) {
            checked = false
            importedBinding = null
        }
        context = application
    }

    /** A same-process cloud restore has committed a different gameplay blob. */
    @JvmStatic @Synchronized fun invalidate() {
        importedBinding = null
        checked = false
    }

    @JvmStatic fun matches(savedBinding: String?, currentDeviceBinding: String?): Boolean {
        if (savedBinding != null && savedBinding == currentDeviceBinding) return true
        if (savedBinding == null) return false
        if (!checked) synchronized(this) {
            if (!checked) {
                importedBinding = loadVerifiedBinding()
                checked = true
            }
        }
        return importedBinding != null && savedBinding == importedBinding
    }

    private fun loadVerifiedBinding(): String? {
        val supplied = context ?: return null
        val path = supplied.getDatabasePath(SaveArchive.DATABASE_NAME)
        if (!path.isFile) return null
        val db = SQLiteDatabase.openDatabase(path.absolutePath, null, SQLiteDatabase.OPEN_READONLY, preserveCorruptDatabase)
        try {
            db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='save_transfer_identity'", null).use {
                if (!it.moveToFirst()) return null
            }
            // One joined row is an atomic SQLite read without asking Android's
            // read-only connection to start a write-reserving transaction.
            return db.rawQuery("SELECT identity.original_binding,identity.gameplay_checksum,record.payload,record.checksum FROM save_transfer_identity AS identity JOIN save_records AS record ON record.slot_id=? AND record.store_name='save_gameplay' AND record.record_id=1 AND record.deleted=0 WHERE identity.singleton=1 AND identity.version=1", arrayOf(SaveArchive.SLOT_ID)).use {
                if (!it.moveToFirst() || it.isNull(0) || it.isNull(1) || it.isNull(2) || it.isNull(3)) return null
                val binding = it.getString(0)
                val payload = it.getBlob(2)
                val checksum = SaveArchive.checksum(payload)
                if (!MessageDigest.isEqual(it.getBlob(3), checksum) || !MessageDigest.isEqual(it.getBlob(1), checksum)) return null
                if (binding != SaveArchive.gameplayBinding(payload)) return null
                binding
            }
        } finally { db.close() }
    }
}
