package com.gameloft.adsmanager;

import com.gameloft.adsmanager.Banner.BaseBannerObject;
import com.gameloft.adsmanager.Incentivized.BaseIncentivizedObject;
import com.gameloft.adsmanager.Interstitial.BaseInterstitialObject;
import com.gameloft.adsmanager.Native.BaseNativeObject;
import com.gameloft.adsmanager.OfferWall.BaseOfferWallObject;

/* JADX INFO: loaded from: classes.dex */
public interface AdsProviderAPIInterface {
    void OnBannerAvailable(BaseBannerObject baseBannerObject);

    void OnBannerLoadError(int i, String str);

    void OnConfigurationFailed();

    void OnConfigurationSucceeded();

    void OnIncentivizedAvailable(BaseIncentivizedObject baseIncentivizedObject);

    void OnIncentivizedLoadError(int i, String str);

    void OnInterstitialAvailable(BaseInterstitialObject baseInterstitialObject);

    void OnInterstitialLoadError(int i, String str);

    void OnNativeAvailable(BaseNativeObject baseNativeObject);

    void OnNativeLoadError(int i, String str);

    void OnOfferWallAvailable(BaseOfferWallObject baseOfferWallObject);

    void OnOfferWallLoadError(int i, String str);

    void OnPauseGameAudio();

    void OnResumeGameAudio();
}
