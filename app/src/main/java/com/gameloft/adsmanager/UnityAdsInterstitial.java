package com.gameloft.adsmanager;

import com.gameloft.adsmanager.Interstitial.BaseInterstitialObject;

/* JADX INFO: loaded from: classes.dex */
public class UnityAdsInterstitial extends BaseInterstitialObject {
    private UnityAds parent;
    public String sdkLocation;

    public UnityAdsInterstitial(UnityAds unityAds, String str) {
        super(unityAds);
        this.sdkLocation = str;
        this.parent = unityAds;
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectInterface
    public void Show(String str, String str2) {
        this.parent.onScreenInterstitial = this;
        JavaUtils.PostAndLogException(AdsManager.parentViewGroup, new f(this), new g(this));
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectInterface
    public boolean IsValid() {
        return com.unity3d.ads.UnityAds.isReady(this.sdkLocation);
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectInterface
    public void Close() {
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectInterface
    public void Destroy() {
    }
}
