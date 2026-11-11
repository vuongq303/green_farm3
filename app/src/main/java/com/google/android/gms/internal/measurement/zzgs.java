package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzgs implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ zzef zzanz;

    zzgs(zzgq zzgqVar, zzef zzefVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzanz = zzefVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzc(this.zzanz, this.zzanq);
    }
}
