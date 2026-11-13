package com.quanhoang.greenfarm3.migration

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.os.ResultReceiver
import com.gameloft.android.ANMP.GloftGF2F.S800x480.ProgressSaveController

/** Runs in the gameplay process. The manifest must mark this receiver exported=false. */
class SaveTransferPreparationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_PREPARE) return
        val response = intent.getParcelableExtra<ResultReceiver>(EXTRA_RESPONSE) ?: return
        if (!SaveTransferGate.beginPreparation()) {
            response.send(RESULT_FAILED, Bundle())
            return
        }
        val pending = goAsync()
        Thread({
            var prepared = false
            try {
                prepared = ProgressSaveController.flushForLifecycle()
                response.send(if (prepared) RESULT_READY else RESULT_FAILED, Bundle().apply {
                    putInt("gameProcessId", Process.myPid())
                })
            } catch (_: Exception) {
                response.send(RESULT_FAILED, Bundle())
            } finally {
                if (!prepared) SaveTransferGate.cancelPreparation()
                pending.finish()
            }
            // The manager owns another process. After a durable checkpoint, killing this
            // process also disposes every old MIDlet loop/cache and releases its file lock.
            if (prepared) Process.killProcess(Process.myPid())
        }, "GreenFarm-SaveTransferPrepare").start()
    }

    companion object {
        const val ACTION_PREPARE = "com.quanhoang.greenfarm3.PREPARE_SAVE_TRANSFER"
        const val EXTRA_RESPONSE = "saveTransferResponse"
        const val RESULT_READY = 1
        const val RESULT_FAILED = 2
    }
}
