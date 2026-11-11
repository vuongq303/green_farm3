package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhc implements Runnable {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ zzeu zzaod;

    zzhc(zzgq zzgqVar, zzeu zzeuVar, String str) {
        this.zzany = zzgqVar;
        this.zzaod = zzeuVar;
        this.zzaoc = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzb(this.zzaod, this.zzaoc);
    }
}
