package com.google.android.gms.ads.internal;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ag implements Runnable {
    private final zzbl bDr;

    private ag(zzbl zzblVar) {
        this.bDr = zzblVar;
    }

    static Runnable b(zzbl zzblVar) {
        return new ag(zzblVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bDr.pause();
    }
}
