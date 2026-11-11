package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhi implements Runnable {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ String zzaof;
    private final /* synthetic */ String zzaog;
    private final /* synthetic */ long zzaoh;

    zzhi(zzgq zzgqVar, String str, String str2, String str3, long j) {
        this.zzany = zzgqVar;
        this.zzaof = str;
        this.zzaoc = str2;
        this.zzaog = str3;
        this.zzaoh = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzaof == null) {
            this.zzany.zzacr.zzfy().zza(this.zzaoc, (zzig) null);
            return;
        }
        zzig zzigVar = new zzig();
        zzigVar.zzug = this.zzaog;
        zzigVar.zzapa = this.zzaof;
        zzigVar.zzapb = this.zzaoh;
        this.zzany.zzacr.zzfy().zza(this.zzaoc, zzigVar);
    }
}
