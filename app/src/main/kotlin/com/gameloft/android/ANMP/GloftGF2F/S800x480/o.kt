package com.gameloft.android.ANMP.GloftGF2F.S800x480

import android.os.SystemClock

class o : Runnable {
    override fun run() {
        GLLib.be("\n\n **************** IAP_SILENT_READY ****************\n\n")
        try { Thread.sleep(2000L) } catch (failure: Exception) {}
        var readyAtMillis = System.currentTimeMillis()
        if (w.Vl) readyAtMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To
        n.p(readyAtMillis)
        n.setResult(12)
    }
}
