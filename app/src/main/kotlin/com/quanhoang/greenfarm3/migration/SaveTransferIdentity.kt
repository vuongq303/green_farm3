package com.quanhoang.greenfarm3.migration

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.security.MessageDigest

/** A user-approved imported record may retain its original binding without changing SDK authentication. */
object SaveTransferIdentity {
    @Volatile private var context: Context? = null
    @Volatile private var checked = false
    @Volatile private var importedBinding: String? = null

    @JvmStatic @Synchronized fun initialize(supplied: Context) {
        val application = supplied.applicationContext ?: supplied
        if (context?.getDatabasePath(SaveArchive.DATABASE_NAME) != application.getDatabasePath(SaveArchive.DATABASE_NAME)) {
            checked = false
            importedBinding = null
        }
        context = application
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
        val db = SQLiteDatabase.openDatabase(path.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
        try {
            db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='save_transfer_identity'", null).use {
                if (!it.moveToFirst()) return null
            }
            db.beginTransaction()
            try {
                val metadata = db.rawQuery("SELECT original_binding,gameplay_checksum FROM save_transfer_identity WHERE singleton=1 AND version=1", null).use {
                    if (!it.moveToFirst()) return null
                    Pair(it.getString(0), it.getBlob(1))
                }
                val payload = db.rawQuery("SELECT payload,checksum FROM save_records WHERE slot_id=? AND store_name='save_gameplay' AND record_id=1 AND deleted=0", arrayOf(SaveArchive.SLOT_ID)).use {
                    if (!it.moveToFirst() || it.isNull(0) || it.isNull(1)) return null
                    val bytes = it.getBlob(0)
                    if (!MessageDigest.isEqual(it.getBlob(1), SaveArchive.checksum(bytes))) return null
                    bytes
                }
                if (!MessageDigest.isEqual(metadata.second, SaveArchive.checksum(payload))) return null
                if (metadata.first != SaveArchive.gameplayBinding(payload)) return null
                db.setTransactionSuccessful()
                return metadata.first
            } finally { db.endTransaction() }
        } finally { db.close() }
    }
}
