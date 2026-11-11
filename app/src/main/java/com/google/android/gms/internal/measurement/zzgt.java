package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzgt implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzgq zzany;
    private final /* synthetic */ zzef zzanz;

    zzgt(zzgq zzgqVar, zzef zzefVar, zzec zzecVar) {
        this.zzany = zzgqVar;
        this.zzanz = zzefVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzany.zzacr.zzka();
        this.zzany.zzacr.zzb(this.zzanz, this.zzanq);
    }
}
