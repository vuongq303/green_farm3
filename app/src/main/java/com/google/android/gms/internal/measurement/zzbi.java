package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzbi implements Runnable {
    private final /* synthetic */ zzbf zzxf;

    zzbi(zzbf zzbfVar) {
        this.zzxf = zzbfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzxf.zzdc();
    }
}
