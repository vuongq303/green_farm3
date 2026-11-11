package com.gameloft.adsmanager;

/* JADX INFO: loaded from: classes.dex */
public interface AdsProviderInterface {
    void RequestBanner();

    void RequestIncentivized();

    String RequestIncentivizedProgrammaticData();

    void RequestInterstitial();

    void RequestNative();

    void RequestOfferWall();

    void ShowProgrammaticIncentivizedAd(String str, String str2);
}
