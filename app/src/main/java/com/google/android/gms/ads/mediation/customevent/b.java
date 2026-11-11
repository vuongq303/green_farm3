package com.google.android.gms.ads.mediation.customevent;

import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.ads.zzane;

/* JADX INFO: loaded from: classes.dex */
final class b implements CustomEventInterstitialListener {
    private final CustomEventAdapter bEe;
    private final /* synthetic */ CustomEventAdapter bEf;
    private final MediationInterstitialListener bza;

    public b(CustomEventAdapter customEventAdapter, CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
        this.bEf = customEventAdapter;
        this.bEe = customEventAdapter2;
        this.bza = mediationInterstitialListener;
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClicked() {
        zzane.zzck("Custom event adapter called onAdClicked.");
        this.bza.onAdClicked(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClosed() {
        zzane.zzck("Custom event adapter called onAdClosed.");
        this.bza.onAdClosed(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdFailedToLoad(int i) {
        zzane.zzck("Custom event adapter called onFailedToReceiveAd.");
        this.bza.onAdFailedToLoad(this.bEe, i);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdLeftApplication() {
        zzane.zzck("Custom event adapter called onAdLeftApplication.");
        this.bza.onAdLeftApplication(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener
    public final void onAdLoaded() {
        zzane.zzck("Custom event adapter called onReceivedAd.");
        this.bza.onAdLoaded(this.bEf);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdOpened() {
        zzane.zzck("Custom event adapter called onAdOpened.");
        this.bza.onAdOpened(this.bEe);
    }
}
