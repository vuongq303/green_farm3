package com.google.android.gms.f;

/* JADX INFO: loaded from: classes.dex */
final class t implements Runnable {
    private final /* synthetic */ f bYV;
    private final /* synthetic */ s bZa;

    t(s sVar, f fVar) {
        this.bZa = sVar;
        this.bYV = fVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bZa.mLock) {
            if (this.bZa.bYZ != null) {
                this.bZa.bYZ.onSuccess(this.bYV.getResult());
            }
        }
    }
}
