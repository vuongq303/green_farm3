package com.google.android.gms.f;

/* JADX INFO: loaded from: classes.dex */
final class r implements Runnable {
    private final /* synthetic */ f bYV;
    private final /* synthetic */ q bYY;

    r(q qVar, f fVar) {
        this.bYY = qVar;
        this.bYV = fVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bYY.mLock) {
            if (this.bYY.bYX != null) {
                this.bYY.bYX.f(this.bYV.getException());
            }
        }
    }
}
