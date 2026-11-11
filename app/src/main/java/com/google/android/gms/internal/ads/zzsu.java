package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzsu extends zzki {
    private final /* synthetic */ zzst zzbnw;

    zzsu(zzst zzstVar) {
        this.zzbnw = zzstVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdClicked() {
        this.zzbnw.zzxo.add(new zztb(this));
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdClosed() {
        this.zzbnw.zzxo.add(new zzsv(this));
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdFailedToLoad(int i) {
        this.zzbnw.zzxo.add(new zzsw(this, i));
        zzakb.v("Pooled interstitial failed to load.");
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdImpression() {
        this.zzbnw.zzxo.add(new zzta(this));
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdLeftApplication() {
        this.zzbnw.zzxo.add(new zzsx(this));
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdLoaded() {
        this.zzbnw.zzxo.add(new zzsy(this));
        zzakb.v("Pooled interstitial loaded.");
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdOpened() {
        this.zzbnw.zzxo.add(new zzsz(this));
    }
}
