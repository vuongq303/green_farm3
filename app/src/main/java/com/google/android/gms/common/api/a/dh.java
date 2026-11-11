package com.google.android.gms.common.api.a;

/* JADX INFO: loaded from: classes.dex */
final class dh implements Runnable {
    private final /* synthetic */ dg bMD;

    @Override // java.lang.Runnable
    public final void run() {
        this.bMD.bKh.lock();
        try {
            this.bMD.ZO();
        } finally {
            this.bMD.bKh.unlock();
        }
    }
}
