package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzma;

/* JADX INFO: loaded from: classes.dex */
public final class PublisherInterstitialAd {
    private final zzma bzt;

    public PublisherInterstitialAd(Context context) {
        this.bzt = new zzma(context, this);
        bf.d(context, "Context cannot be null");
    }

    public final AdListener getAdListener() {
        return this.bzt.getAdListener();
    }

    public final String getAdUnitId() {
        return this.bzt.getAdUnitId();
    }

    public final AppEventListener getAppEventListener() {
        return this.bzt.getAppEventListener();
    }

    public final String getMediationAdapterClassName() {
        return this.bzt.getMediationAdapterClassName();
    }

    public final OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.bzt.getOnCustomRenderedAdLoadedListener();
    }

    public final boolean isLoaded() {
        return this.bzt.isLoaded();
    }

    public final boolean isLoading() {
        return this.bzt.isLoading();
    }

    public final void loadAd(PublisherAdRequest publisherAdRequest) {
        this.bzt.zza(publisherAdRequest.zzay());
    }

    public final void setAdListener(AdListener adListener) {
        this.bzt.setAdListener(adListener);
    }

    public final void setAdUnitId(String str) {
        this.bzt.setAdUnitId(str);
    }

    public final void setAppEventListener(AppEventListener appEventListener) {
        this.bzt.setAppEventListener(appEventListener);
    }

    public final void setCorrelator(Correlator correlator) {
        this.bzt.setCorrelator(correlator);
    }

    public final void setImmersiveMode(boolean z) {
        this.bzt.setImmersiveMode(z);
    }

    public final void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.bzt.setOnCustomRenderedAdLoadedListener(onCustomRenderedAdLoadedListener);
    }

    public final void show() {
        this.bzt.show();
    }
}
