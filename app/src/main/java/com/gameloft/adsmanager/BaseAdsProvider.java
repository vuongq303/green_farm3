package com.gameloft.adsmanager;

import com.gameloft.adsmanager.Banner.BaseBannerObject;
import com.gameloft.adsmanager.Incentivized.BaseIncentivizedObject;
import com.gameloft.adsmanager.Interstitial.BaseInterstitialObject;
import com.gameloft.adsmanager.Native.BaseNativeObject;
import com.gameloft.adsmanager.OfferWall.BaseOfferWallObject;

/* JADX INFO: loaded from: classes.dex */
public class BaseAdsProvider implements AdsProviderAPIInterface, AdsProviderInterface {
    private long m_handleToNative;

    private native void NativeOnBannerAvailable(long j, BaseBannerObject baseBannerObject);

    private native void NativeOnBannerClicked(long j, String str, String str2);

    private native void NativeOnBannerClosed(long j, String str, String str2);

    private native void NativeOnBannerLoadError(long j, int i, String str);

    private native void NativeOnBannerOnScreen(long j, String str, String str2);

    private native void NativeOnBannerRefreshed(long j, String str, String str2);

    private native void NativeOnBannerShowError(long j, int i, String str, String str2);

    private native void NativeOnConfigurationFailed(long j);

    private native void NativeOnConfigurationSucceeded(long j);

    private native void NativeOnIncentivizedAvailable(long j, BaseIncentivizedObject baseIncentivizedObject);

    private native void NativeOnIncentivizedClicked(long j, String str, String str2);

    private native void NativeOnIncentivizedClosed(long j, String str, String str2);

    private native void NativeOnIncentivizedLoadError(long j, int i, String str);

    private native void NativeOnIncentivizedOnScreen(long j, String str, String str2);

    private native void NativeOnIncentivizedReward(long j, String str, boolean z, String str2, String str3);

    private native void NativeOnIncentivizedShowError(long j, int i, String str, String str2);

    private native void NativeOnInterstitialAvailable(long j, BaseInterstitialObject baseInterstitialObject);

    private native void NativeOnInterstitialClicked(long j, String str, String str2);

    private native void NativeOnInterstitialClosed(long j, String str, String str2);

    private native void NativeOnInterstitialLoadError(long j, int i, String str);

    private native void NativeOnInterstitialOnScreen(long j, String str, String str2);

    private native void NativeOnInterstitialShowError(long j, int i, String str, String str2);

    private native void NativeOnNativeAvailable(long j, BaseNativeObject baseNativeObject);

    private native void NativeOnNativeClicked(long j, String str, String str2);

    private native void NativeOnNativeClosed(long j, String str, String str2);

    private native void NativeOnNativeLoadError(long j, int i, String str);

    private native void NativeOnNativeOnScreen(long j, String str, String str2);

    private native void NativeOnNativeShowError(long j, int i, String str, String str2);

    private native void NativeOnOfferWallAvailable(long j, BaseOfferWallObject baseOfferWallObject);

    private native void NativeOnOfferWallClicked(long j, String str, String str2);

    private native void NativeOnOfferWallClosed(long j, String str, String str2);

    private native void NativeOnOfferWallLoadError(long j, int i, String str);

    private native void NativeOnOfferWallOnScreen(long j, String str, String str2);

    private native void NativeOnOfferWallShowError(long j, int i, String str, String str2);

    private native void NativeOnPauseGameAudio(long j);

    private native void NativeOnProgrammaticIncentivizedAvailable(long j, long j2, BaseIncentivizedObject baseIncentivizedObject);

    private native void NativeOnResumeGameAudio(long j);

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnPauseGameAudio() {
        NativeOnPauseGameAudio(this.m_handleToNative);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnResumeGameAudio() {
        NativeOnResumeGameAudio(this.m_handleToNative);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnConfigurationSucceeded() {
        NativeOnConfigurationSucceeded(this.m_handleToNative);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnConfigurationFailed() {
        NativeOnConfigurationFailed(this.m_handleToNative);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnBannerAvailable(BaseBannerObject baseBannerObject) {
        NativeOnBannerAvailable(this.m_handleToNative, baseBannerObject);
    }

    public BaseAdsProvider(long j) {
        this.m_handleToNative = j;
    }

    public void RequestBannerInternal() {
        try {
            RequestBanner();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestBannerInternal", e);
            OnBannerLoadError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, "exception");
        }
    }

    public void RequestNativeInternal() {
        try {
            RequestNative();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestNativeInternal", e);
            OnNativeLoadError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, "exception");
        }
    }

    public void RequestInterstitialInternal() {
        try {
            RequestInterstitial();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestInterstitialInternal", e);
            OnInterstitialLoadError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, "exception");
        }
    }

    public void RequestIncentivizedInternal() {
        try {
            RequestIncentivized();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestIncentivizedInternal", e);
            OnIncentivizedLoadError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, "exception");
        }
    }

    public void RequestOfferWallInternal() {
        try {
            RequestOfferWall();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestOfferWallInternal", e);
            OnOfferWallLoadError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, "exception");
        }
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public String RequestIncentivizedProgrammaticData() {
        return "";
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void ShowProgrammaticIncentivizedAd(String str, String str2) {
        OnIncentivizedShowError(JavaUtils.Errors.ERROR_PROGRAMATIC_SHOW, str2, "");
    }

    public String RequestIncentivizedProgrammaticDataInternal() {
        try {
            return RequestIncentivizedProgrammaticData();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "RequestIncentivizedProgrammaticDataInternal", e);
            return "";
        }
    }

    public void ShowProgrammaticIncentivizedAdInternal(String str, String str2) {
        try {
            ShowProgrammaticIncentivizedAd(str, str2);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseAdsProvider", "ShowProgrammaticIncentivizedAdInternal", e);
        }
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnBannerLoadError(int i, String str) {
        NativeOnBannerLoadError(this.m_handleToNative, i, str);
    }

    public void OnBannerShowError(int i, String str, String str2) {
        NativeOnBannerShowError(this.m_handleToNative, i, str, str2);
    }

    public void OnBannerOnScreen(String str, String str2) {
        NativeOnBannerOnScreen(this.m_handleToNative, str, str2);
    }

    public void OnBannerClosed(String str, String str2) {
        NativeOnBannerClosed(this.m_handleToNative, str, str2);
    }

    public void OnBannerClicked(String str, String str2) {
        NativeOnBannerClicked(this.m_handleToNative, str, str2);
    }

    public void OnBannerRefreshed(String str, String str2) {
        NativeOnBannerRefreshed(this.m_handleToNative, str, str2);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnNativeAvailable(BaseNativeObject baseNativeObject) {
        NativeOnNativeAvailable(this.m_handleToNative, baseNativeObject);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnNativeLoadError(int i, String str) {
        NativeOnNativeLoadError(this.m_handleToNative, i, str);
    }

    public void OnNativeShowError(int i, String str, String str2) {
        NativeOnNativeShowError(this.m_handleToNative, i, str, str2);
    }

    public void OnNativeOnScreen(String str, String str2) {
        NativeOnNativeOnScreen(this.m_handleToNative, str, str2);
    }

    public void OnNativeClosed(String str, String str2) {
        NativeOnNativeClosed(this.m_handleToNative, str, str2);
    }

    public void OnNativeClicked(String str, String str2) {
        NativeOnNativeClicked(this.m_handleToNative, str, str2);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnInterstitialAvailable(BaseInterstitialObject baseInterstitialObject) {
        NativeOnInterstitialAvailable(this.m_handleToNative, baseInterstitialObject);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnInterstitialLoadError(int i, String str) {
        NativeOnInterstitialLoadError(this.m_handleToNative, i, str);
    }

    public void OnInterstitialShowError(int i, String str, String str2) {
        NativeOnInterstitialShowError(this.m_handleToNative, i, str, str2);
    }

    public void OnInterstitialOnScreen(String str, String str2) {
        NativeOnInterstitialOnScreen(this.m_handleToNative, str, str2);
    }

    public void OnInterstitialClosed(String str, String str2) {
        NativeOnInterstitialClosed(this.m_handleToNative, str, str2);
    }

    public void OnInterstitialClicked(String str, String str2) {
        NativeOnInterstitialClicked(this.m_handleToNative, str, str2);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnIncentivizedAvailable(BaseIncentivizedObject baseIncentivizedObject) {
        NativeOnIncentivizedAvailable(this.m_handleToNative, baseIncentivizedObject);
    }

    public void OnProgrammaticIncentivizedAvailable(long j, BaseIncentivizedObject baseIncentivizedObject) {
        NativeOnProgrammaticIncentivizedAvailable(this.m_handleToNative, j, baseIncentivizedObject);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnIncentivizedLoadError(int i, String str) {
        NativeOnIncentivizedLoadError(this.m_handleToNative, i, str);
    }

    public void OnIncentivizedShowError(int i, String str, String str2) {
        NativeOnIncentivizedShowError(this.m_handleToNative, i, str, str2);
    }

    public void OnIncentivizedOnScreen(String str, String str2) {
        NativeOnIncentivizedOnScreen(this.m_handleToNative, str, str2);
    }

    public void OnIncentivizedClosed(String str, String str2) {
        NativeOnIncentivizedClosed(this.m_handleToNative, str, str2);
    }

    public void OnIncentivizedClicked(String str, String str2) {
        NativeOnIncentivizedClicked(this.m_handleToNative, str, str2);
    }

    public void OnIncentivizedReward(String str, boolean z, String str2, String str3) {
        NativeOnIncentivizedReward(this.m_handleToNative, str, z, str2, str3);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnOfferWallAvailable(BaseOfferWallObject baseOfferWallObject) {
        NativeOnOfferWallAvailable(this.m_handleToNative, baseOfferWallObject);
    }

    @Override // com.gameloft.adsmanager.AdsProviderAPIInterface
    public void OnOfferWallLoadError(int i, String str) {
        NativeOnOfferWallLoadError(this.m_handleToNative, i, str);
    }

    public void OnOfferWallShowError(int i, String str, String str2) {
        NativeOnOfferWallShowError(this.m_handleToNative, i, str, str2);
    }

    public void OnOfferWallOnScreen(String str, String str2) {
        NativeOnOfferWallOnScreen(this.m_handleToNative, str, str2);
    }

    public void OnOfferWallClosed(String str, String str2) {
        NativeOnOfferWallClosed(this.m_handleToNative, str, str2);
    }

    public void OnOfferWallClicked(String str, String str2) {
        NativeOnOfferWallClicked(this.m_handleToNative, str, str2);
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void RequestBanner() {
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void RequestInterstitial() {
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void RequestNative() {
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void RequestIncentivized() {
    }

    @Override // com.gameloft.adsmanager.AdsProviderInterface
    public void RequestOfferWall() {
    }
}
