package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzajo extends zzajx {
    private final /* synthetic */ zzajm zzcpw;

    zzajo(zzajm zzajmVar) {
        this.zzcpw = zzajmVar;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        zznm zznmVar = new zznm(this.zzcpw.mContext, this.zzcpw.zzyf.zzcw);
        synchronized (this.zzcpw.mLock) {
            try {
                com.google.android.gms.ads.internal.zzbv.zzet();
                zznp.zza(this.zzcpw.zzcpn, zznmVar);
            } catch (IllegalArgumentException e) {
                zzakb.zzc("Cannot config CSI reporter.", e);
            }
        }
    }
}
