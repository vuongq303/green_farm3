package com.google.android.gms.f;

/* JADX INFO: loaded from: classes.dex */
final class n implements Runnable {
    private final /* synthetic */ m bYT;

    n(m mVar) {
        this.bYT = mVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bYT.mLock) {
            if (this.bYT.bYS != null) {
                this.bYT.bYS.onCanceled();
            }
        }
    }
}
