package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhe implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ zzjs zzaoe;

    zzhe(zzgq zzgqVar, zzjs zzjsVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzaoe = zzjsVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzc(this.zzaoe, this.zzanq);
    }
}
