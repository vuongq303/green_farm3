package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzfq implements Runnable {
    private final /* synthetic */ boolean zzajp;
    private final /* synthetic */ zzfp zzajq;

    zzfq(zzfp zzfpVar, boolean z) {
        this.zzajq = zzfpVar;
        this.zzajp = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzajq.zzacr.zzi(this.zzajp);
    }
}
