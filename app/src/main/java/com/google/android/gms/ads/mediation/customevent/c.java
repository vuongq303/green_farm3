package com.google.android.gms.ads.mediation.customevent;

import com.google.android.gms.ads.mediation.MediationNativeListener;
import com.google.android.gms.ads.mediation.NativeAdMapper;
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper;
import com.google.android.gms.internal.ads.zzane;

/* JADX INFO: loaded from: classes.dex */
final class c implements CustomEventNativeListener {
    private final CustomEventAdapter bEe;
    private final MediationNativeListener bzb;

    public c(CustomEventAdapter customEventAdapter, MediationNativeListener mediationNativeListener) {
        this.bEe = customEventAdapter;
        this.bzb = mediationNativeListener;
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClicked() {
        zzane.zzck("Custom event adapter called onAdClicked.");
        this.bzb.onAdClicked(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClosed() {
        zzane.zzck("Custom event adapter called onAdClosed.");
        this.bzb.onAdClosed(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdFailedToLoad(int i) {
        zzane.zzck("Custom event adapter called onAdFailedToLoad.");
        this.bzb.onAdFailedToLoad(this.bEe, i);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener
    public final void onAdImpression() {
        zzane.zzck("Custom event adapter called onAdImpression.");
        this.bzb.onAdImpression(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdLeftApplication() {
        zzane.zzck("Custom event adapter called onAdLeftApplication.");
        this.bzb.onAdLeftApplication(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener
    public final void onAdLoaded(NativeAdMapper nativeAdMapper) {
        zzane.zzck("Custom event adapter called onAdLoaded.");
        this.bzb.onAdLoaded(this.bEe, nativeAdMapper);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener
    public final void onAdLoaded(UnifiedNativeAdMapper unifiedNativeAdMapper) {
        zzane.zzck("Custom event adapter called onAdLoaded.");
        this.bzb.onAdLoaded(this.bEe, unifiedNativeAdMapper);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdOpened() {
        zzane.zzck("Custom event adapter called onAdOpened.");
        this.bzb.onAdOpened(this.bEe);
    }
}
