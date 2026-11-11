package com.google.android.gms.internal.ads;

import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
final class zzabs implements Runnable {
    private final /* synthetic */ zzabr zzbzt;
    private final /* synthetic */ CountDownLatch zzwd;

    zzabs(zzabr zzabrVar, CountDownLatch countDownLatch) {
        this.zzbzt = zzabrVar;
        this.zzwd = countDownLatch;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzbzt.zzbzh) {
            this.zzbzt.zzbzs = com.google.android.gms.ads.internal.zzas.zza(this.zzbzt.zzbnd, this.zzbzt.zzbzr, this.zzwd);
        }
    }
}
