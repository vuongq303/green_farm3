package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzvo implements zzaoo<zzuu> {
    private final /* synthetic */ zzvf zzbqk;
    private final /* synthetic */ zzvw zzbqn;

    zzvo(zzvf zzvfVar, zzvw zzvwVar) {
        this.zzbqk = zzvfVar;
        this.zzbqn = zzvwVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaoo
    public final /* synthetic */ void zze(zzuu zzuuVar) {
        synchronized (this.zzbqk.mLock) {
            this.zzbqk.zzbqb = 0;
            if (this.zzbqk.zzbqa != null && this.zzbqn != this.zzbqk.zzbqa) {
                zzakb.v("New JS engine is loaded, marking previous one as destroyable.");
                this.zzbqk.zzbqa.zzmb();
            }
            this.zzbqk.zzbqa = this.zzbqn;
        }
    }
}
