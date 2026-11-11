package com.gameloft.adsmanager;

/* JADX INFO: compiled from: UnityAdsIncentivized.java */
/* JADX INFO: loaded from: classes.dex */
class d implements Runnable {
    final /* synthetic */ UnityAdsIncentivized Ac;

    d(UnityAdsIncentivized unityAdsIncentivized) {
        this.Ac = unityAdsIncentivized;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.unity3d.ads.UnityAds.show(AdsManager.mainActivity, this.Ac.sdkLocation);
    }
}
