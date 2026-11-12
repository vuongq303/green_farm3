package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
final class o implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        GLLib.be("\n\n **************** IAP_SILENT_READY ****************\n\n");
        try {
            Thread.sleep(2000L);
        } catch (Exception e) {
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        n.p(jCurrentTimeMillis);
        n.setResult(12);
    }
}
