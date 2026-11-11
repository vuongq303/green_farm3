package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzahu;
import com.google.android.gms.internal.ads.zzaig;

/* JADX INFO: loaded from: classes.dex */
final class g implements zzahu {
    private final /* synthetic */ zzal bBv;

    g(zzal zzalVar) {
        this.bBv = zzalVar;
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void onRewardedVideoAdClosed() {
        this.bBv.zzcb();
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void onRewardedVideoAdLeftApplication() {
        this.bBv.zzbo();
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void onRewardedVideoAdOpened() {
        this.bBv.zzcc();
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void onRewardedVideoCompleted() {
        this.bBv.zzdl();
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void onRewardedVideoStarted() {
        this.bBv.zzdk();
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void zzc(zzaig zzaigVar) {
        this.bBv.zzb(zzaigVar);
    }

    @Override // com.google.android.gms.internal.ads.zzahu
    public final void zzdm() {
        this.bBv.onAdClicked();
    }
}
