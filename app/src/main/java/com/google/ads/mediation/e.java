package com.google.ads.mediation;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.internal.ads.zzjd;

/* JADX INFO: loaded from: classes.dex */
final class e extends AdListener implements AppEventListener, zzjd {
    private final a byY;
    private final MediationBannerListener byZ;

    public e(a aVar, MediationBannerListener mediationBannerListener) {
        this.byY = aVar;
        this.byZ = mediationBannerListener;
    }

    @Override // com.google.android.gms.ads.AdListener, com.google.android.gms.internal.ads.zzjd
    public final void onAdClicked() {
        this.byZ.onAdClicked(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdClosed() {
        this.byZ.onAdClosed(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdFailedToLoad(int i) {
        this.byZ.onAdFailedToLoad(this.byY, i);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLeftApplication() {
        this.byZ.onAdLeftApplication(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLoaded() {
        this.byZ.onAdLoaded(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdOpened() {
        this.byZ.onAdOpened(this.byY);
    }

    @Override // com.google.android.gms.ads.doubleclick.AppEventListener
    public final void onAppEvent(String str, String str2) {
        this.byZ.zza(this.byY, str, str2);
    }
}
