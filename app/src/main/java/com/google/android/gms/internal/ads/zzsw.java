package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzsw implements zzts {
    private final /* synthetic */ int zzbnx;

    zzsw(zzsu zzsuVar, int i) {
        this.zzbnx = i;
    }

    @Override // com.google.android.gms.internal.ads.zzts
    public final void zzb(zztt zzttVar) {
        if (zzttVar.zzxs != null) {
            zzttVar.zzxs.onAdFailedToLoad(this.zzbnx);
        }
    }
}
