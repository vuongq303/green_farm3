package com.google.ads.mediation;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.ads.zzjd;

/* JADX INFO: loaded from: classes.dex */
final class f extends AdListener implements zzjd {
    private final a byY;
    private final MediationInterstitialListener bza;

    public f(a aVar, MediationInterstitialListener mediationInterstitialListener) {
        this.byY = aVar;
        this.bza = mediationInterstitialListener;
    }

    @Override // com.google.android.gms.ads.AdListener, com.google.android.gms.internal.ads.zzjd
    public final void onAdClicked() {
        this.bza.onAdClicked(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdClosed() {
        this.bza.onAdClosed(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdFailedToLoad(int i) {
        this.bza.onAdFailedToLoad(this.byY, i);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLeftApplication() {
        this.bza.onAdLeftApplication(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLoaded() {
        this.bza.onAdLoaded(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdOpened() {
        this.bza.onAdOpened(this.byY);
    }
}
