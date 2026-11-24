package com.quanhoang.greenfarm3.S800x480

import android.os.SystemClock

class o : Runnable {
    override fun run() {
        GameRuntime.be("\n\n **************** IAP_SILENT_READY ****************\n\n")
        try { Thread.sleep(2000L) } catch (failure: Exception) {}
        var readyAtMillis = System.currentTimeMillis()
        if (w.Vl) readyAtMillis = (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To
        n.p(readyAtMillis)
        n.setResult(12)
    }
}
