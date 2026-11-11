package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class cl implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        while (ci.bbI != null) {
            if (!ci.bbM.isEmpty()) {
                try {
                    dd ddVar = (dd) ci.bbM.firstElement();
                    ci.baf.b(ddVar.bcm, ddVar.bcn);
                    ci.bbM.removeElementAt(0);
                    Thread.sleep(62L);
                } catch (Exception e) {
                }
            }
        }
    }
}
