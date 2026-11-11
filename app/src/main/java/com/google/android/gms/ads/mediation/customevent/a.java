package com.google.android.gms.ads.mediation.customevent;

import android.view.View;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.internal.ads.zzane;

/* JADX INFO: loaded from: classes.dex */
final class a implements CustomEventBannerListener {
    private final CustomEventAdapter bEe;
    private final MediationBannerListener byZ;

    public a(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
        this.bEe = customEventAdapter;
        this.byZ = mediationBannerListener;
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClicked() {
        zzane.zzck("Custom event adapter called onAdClicked.");
        this.byZ.onAdClicked(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdClosed() {
        zzane.zzck("Custom event adapter called onAdClosed.");
        this.byZ.onAdClosed(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdFailedToLoad(int i) {
        zzane.zzck("Custom event adapter called onAdFailedToLoad.");
        this.byZ.onAdFailedToLoad(this.bEe, i);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdLeftApplication() {
        zzane.zzck("Custom event adapter called onAdLeftApplication.");
        this.byZ.onAdLeftApplication(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener
    public final void onAdLoaded(View view) {
        zzane.zzck("Custom event adapter called onAdLoaded.");
        this.bEe.P(view);
        this.byZ.onAdLoaded(this.bEe);
    }

    @Override // com.google.android.gms.ads.mediation.customevent.CustomEventListener
    public final void onAdOpened() {
        zzane.zzck("Custom event adapter called onAdOpened.");
        this.byZ.onAdOpened(this.bEe);
    }
}
