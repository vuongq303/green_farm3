package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: PlatformRequestWorker.java */
/* JADX INFO: loaded from: classes.dex */
class bj implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        if (GLLib.QF != null) {
            GLLib.Qu.jf(GLLib.QF);
        }
        if (!w.Vi) {
            return;
        }
        GLLib.be("GLLib.quit");
        GLLib.Qn = -1;
        GLLib.Qu.akD();
    }
}
