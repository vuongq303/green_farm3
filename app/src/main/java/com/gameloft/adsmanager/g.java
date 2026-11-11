package com.gameloft.adsmanager;

/* JADX INFO: compiled from: UnityAdsInterstitial.java */
/* JADX INFO: loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ UnityAdsInterstitial Ad;

    g(UnityAdsInterstitial unityAdsInterstitial) {
        this.Ad = unityAdsInterstitial;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Ad.OnShowError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, this.Ad.sdkLocation);
    }
}
