package com.google.android.gms.f;

/* JADX INFO: loaded from: classes.dex */
final class p implements Runnable {
    private final /* synthetic */ f bYV;
    private final /* synthetic */ o bYW;

    p(o oVar, f fVar) {
        this.bYW = oVar;
        this.bYV = fVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bYW.mLock) {
            if (this.bYW.bYU != null) {
                this.bYW.bYU.a(this.bYV);
            }
        }
    }
}
