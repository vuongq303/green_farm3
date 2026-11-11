package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzib implements Runnable {
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ boolean zzaou;

    zzib(zzhm zzhmVar, boolean z) {
        this.zzaop = zzhmVar;
        this.zzaou = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzj(this.zzaou);
    }
}
