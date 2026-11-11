package com.gameloft.adsmanager;

/* JADX INFO: compiled from: UnityAdsInterstitial.java */
/* JADX INFO: loaded from: classes.dex */
class f implements Runnable {
    final /* synthetic */ UnityAdsInterstitial Ad;

    f(UnityAdsInterstitial unityAdsInterstitial) {
        this.Ad = unityAdsInterstitial;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.unity3d.ads.UnityAds.show(AdsManager.mainActivity, this.Ad.sdkLocation);
    }
}
