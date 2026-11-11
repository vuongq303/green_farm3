package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhb implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ zzeu zzaod;

    zzhb(zzgq zzgqVar, zzeu zzeuVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzaod = zzeuVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzb(this.zzaod, this.zzanq);
    }
}
