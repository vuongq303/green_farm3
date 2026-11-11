package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzvz implements zzaoo<zzuu> {
    final /* synthetic */ zzvw zzbqw;

    zzvz(zzvw zzvwVar) {
        this.zzbqw = zzvwVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaoo
    public final /* synthetic */ void zze(zzuu zzuuVar) {
        final zzuu zzuuVar2 = zzuuVar;
        zzaoe.zzcvy.execute(new Runnable(this, zzuuVar2) { // from class: com.google.android.gms.internal.ads.zzwa
            private final zzvz zzbqx;
            private final zzuu zzbqy;

            {
                this.zzbqx = this;
                this.zzbqy = zzuuVar2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzvz zzvzVar = this.zzbqx;
                zzuu zzuuVar3 = this.zzbqy;
                zzvzVar.zzbqw.zzbpz.zze(zzuuVar3);
                zzuuVar3.destroy();
            }
        });
    }
}
