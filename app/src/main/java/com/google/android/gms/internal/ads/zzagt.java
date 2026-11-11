package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzagt implements Runnable {
    private final /* synthetic */ zzagr zzclh;
    private final /* synthetic */ zzaji zzwg;

    zzagt(zzagr zzagrVar, zzaji zzajiVar) {
        this.zzclh = zzagrVar;
        this.zzwg = zzajiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzclh.zzb(new zzajh(this.zzwg, null, null, null, null, null, null, null));
    }
}
