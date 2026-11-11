package com.google.ads.mediation;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.mediation.MediationNativeListener;

/* JADX INFO: loaded from: classes.dex */
final class g extends AdListener implements NativeAppInstallAd.OnAppInstallAdLoadedListener, NativeContentAd.OnContentAdLoadedListener, NativeCustomTemplateAd.OnCustomClickListener, NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener, UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
    private final a byY;
    private final MediationNativeListener bzb;

    public g(a aVar, MediationNativeListener mediationNativeListener) {
        this.byY = aVar;
        this.bzb = mediationNativeListener;
    }

    @Override // com.google.android.gms.ads.AdListener, com.google.android.gms.internal.ads.zzjd
    public final void onAdClicked() {
        this.bzb.onAdClicked(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdClosed() {
        this.bzb.onAdClosed(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdFailedToLoad(int i) {
        this.bzb.onAdFailedToLoad(this.byY, i);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdImpression() {
        this.bzb.onAdImpression(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLeftApplication() {
        this.bzb.onAdLeftApplication(this.byY);
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdLoaded() {
    }

    @Override // com.google.android.gms.ads.AdListener
    public final void onAdOpened() {
        this.bzb.onAdOpened(this.byY);
    }

    @Override // com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener
    public final void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
        this.bzb.onAdLoaded(this.byY, new b(nativeAppInstallAd));
    }

    @Override // com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener
    public final void onContentAdLoaded(NativeContentAd nativeContentAd) {
        this.bzb.onAdLoaded(this.byY, new c(nativeContentAd));
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomClickListener
    public final void onCustomClick(NativeCustomTemplateAd nativeCustomTemplateAd, String str) {
        this.bzb.zza(this.byY, nativeCustomTemplateAd, str);
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener
    public final void onCustomTemplateAdLoaded(NativeCustomTemplateAd nativeCustomTemplateAd) {
        this.bzb.zza(this.byY, nativeCustomTemplateAd);
    }

    @Override // com.google.android.gms.ads.formats.UnifiedNativeAd.OnUnifiedNativeAdLoadedListener
    public final void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
        this.bzb.onAdLoaded(this.byY, new d(unifiedNativeAd));
    }
}
