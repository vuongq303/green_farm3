package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhh implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;

    zzhh(zzgq zzgqVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzg(this.zzanq);
    }
}
