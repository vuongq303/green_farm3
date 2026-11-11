package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zztp implements zzts {
    private final /* synthetic */ int zzbnx;

    zztp(zzti zztiVar, int i) {
        this.zzbnx = i;
    }

    @Override // com.google.android.gms.internal.ads.zzts
    public final void zzb(zztt zzttVar) {
        if (zzttVar.zzboh != null) {
            zzttVar.zzboh.onRewardedVideoAdFailedToLoad(this.zzbnx);
        }
    }
}
