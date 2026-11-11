package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzgu implements Runnable {
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ zzef zzanz;

    zzgu(zzgq zzgqVar, zzef zzefVar) {
        this.zzany = zzgqVar;
        this.zzanz = zzefVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zze(this.zzanz);
    }
}
