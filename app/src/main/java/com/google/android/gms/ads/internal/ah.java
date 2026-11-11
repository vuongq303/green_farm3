package com.google.android.gms.ads.internal;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ah implements Runnable {
    private final zzbl bDr;

    private ah(zzbl zzblVar) {
        this.bDr = zzblVar;
    }

    static Runnable b(zzbl zzblVar) {
        return new ah(zzblVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bDr.resume();
    }
}
