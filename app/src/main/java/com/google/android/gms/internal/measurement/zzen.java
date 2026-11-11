package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzen implements Runnable {
    private final /* synthetic */ zzgl zzafi;
    private final /* synthetic */ zzem zzafj;

    zzen(zzem zzemVar, zzgl zzglVar) {
        this.zzafj = zzemVar;
        this.zzafi = zzglVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzafi.zzgf();
        if (zzgg.isMainThread()) {
            this.zzafj.zzacr.zzgf().zzc(this);
            return;
        }
        boolean zZzef = this.zzafj.zzef();
        zzem.zza(this.zzafj, 0L);
        if (zZzef && this.zzafj.enabled) {
            this.zzafj.run();
        }
    }
}
