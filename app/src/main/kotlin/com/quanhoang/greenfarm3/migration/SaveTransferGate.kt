package com.quanhoang.greenfarm3.migration

import android.content.Context
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException

/** Cross-process exclusion: gameplay holds this lock until its process ends. */
object SaveTransferGate {
    private var gameFile: RandomAccessFile? = null
    private var gameLock: FileLock? = null
    @Volatile private var preparingTransfer = false

    /** The existing game input/frame gate observes this during the explicit transfer transition. */
    @JvmStatic @Synchronized fun beginPreparation(): Boolean {
        if (preparingTransfer) return false
        preparingTransfer = true
        return true
    }

    @JvmStatic fun cancelPreparation() { preparingTransfer = false }
    @JvmStatic fun isPreparingTransfer(): Boolean = preparingTransfer

    @JvmStatic @Synchronized fun enterGame(context: Context): Boolean {
        SaveTransferIdentity.initialize(context)
        if (gameLock?.isValid == true) return true
        val file = RandomAccessFile(File(context.filesDir, "save-transfer.lock"), "rw")
        val lock = try { file.channel.tryLock() } catch (_: OverlappingFileLockException) { null }
        if (lock == null) { file.close(); return false }
        gameFile = file
        gameLock = lock
        return true
    }

    fun <T> withTransferLock(context: Context, block: () -> T): T {
        val file = RandomAccessFile(File(context.filesDir, "save-transfer.lock"), "rw")
        try {
            val lock = try { file.channel.tryLock() } catch (_: OverlappingFileLockException) { null }
            if (lock == null) throw IOException("Game đang mở. Hãy đóng và lưu phiên game trước khi chuyển bản lưu.")
            try { return block() } finally { lock.release() }
        } finally { file.close() }
    }
}
