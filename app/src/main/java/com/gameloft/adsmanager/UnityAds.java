package com.gameloft.adsmanager;

import android.os.Build;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;
import com.unity3d.ads.metadata.MetaData;

/* JADX INFO: loaded from: classes.dex */
public class UnityAds extends BaseAdsProvider implements IUnityAdsExtendedListener {
    private static final int MINIMUM_API_LEVEL = 19;
    private JavaUtils.PlacementState incentivizedAds;
    private JavaUtils.PlacementState interstitials;
    public UnityAdsIncentivized onScreenIncentivized;
    public UnityAdsInterstitial onScreenInterstitial;

    public UnityAds(long j) {
        super(j);
    }

    public void Configure(String str, String[] strArr, String[] strArr2, boolean z, boolean z2, boolean z3) {
        if (Build.VERSION.SDK_INT < MINIMUM_API_LEVEL) {
            JavaLoggingDefines.LOG_WARNING("UnityAds.java", "Configure", "Current API level is lower than the accepted API level: 19. Unity SDK will not be configured");
            OnConfigurationFailed();
            return;
        }
        this.interstitials = new JavaUtils.PlacementState(strArr2);
        this.incentivizedAds = new JavaUtils.PlacementState(strArr);
        MetaData metaData = new MetaData(AdsManager.mainActivity.getApplicationContext());
        metaData.set("gdpr.consent", Boolean.valueOf(z));
        JavaLoggingDefines.LOG_INFO("UnityAds.java", "Configure", "User consent set= (" + z + ")");
        metaData.commit();
        try {
            com.unity3d.ads.UnityAds.initialize(AdsManager.mainActivity, str, this, false);
            OnConfigurationSucceeded();
            JavaLoggingDefines.LOG_INFO("UnityAds.java", "Configure", "OnConfigurationSucceeded");
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("UnityAds.java", "Configure", e);
            OnConfigurationFailed();
            JavaLoggingDefines.LOG_INFO("UnityAds.java", "Configure", "OnConfigurationFailed");
        }
    }

    @Override // com.unity3d.ads.mediation.IUnityAdsExtendedListener
    public void onUnityAdsClick(String str) {
        if (this.onScreenIncentivized != null && this.onScreenIncentivized.sdkLocation.equals(str)) {
            this.onScreenIncentivized.OnClick(this.onScreenIncentivized.sdkLocation);
        } else if (this.onScreenInterstitial != null && this.onScreenInterstitial.sdkLocation.equals(str)) {
            this.onScreenInterstitial.OnClick(this.onScreenInterstitial.sdkLocation);
        }
    }

    @Override // com.unity3d.ads.mediation.IUnityAdsExtendedListener
    public void onUnityAdsPlacementStateChanged(String str, com.unity3d.ads.UnityAds.PlacementState placementState, com.unity3d.ads.UnityAds.PlacementState placementState2) {
    }

    @Override // com.unity3d.ads.IUnityAdsListener
    public void onUnityAdsReady(String str) {
        if (this.incentivizedAds.PopSpecificPlacement(str)) {
            OnIncentivizedAvailable(new UnityAdsIncentivized(this, str));
        } else if (this.interstitials.PopSpecificPlacement(str)) {
            OnInterstitialAvailable(new UnityAdsInterstitial(this, str));
        }
    }

    @Override // com.unity3d.ads.IUnityAdsListener
    public void onUnityAdsStart(String str) {
        OnPauseGameAudio();
        if (this.onScreenIncentivized != null && this.onScreenIncentivized.sdkLocation.equals(str)) {
            this.onScreenIncentivized.OnDisplay(this.onScreenIncentivized.sdkLocation);
        } else if (this.onScreenInterstitial != null && this.onScreenInterstitial.sdkLocation.equals(str)) {
            this.onScreenInterstitial.OnDisplay(this.onScreenInterstitial.sdkLocation);
        }
    }

    @Override // com.unity3d.ads.IUnityAdsListener
    public void onUnityAdsFinish(String str, com.unity3d.ads.UnityAds.FinishState finishState) {
        if (this.onScreenIncentivized != null && this.onScreenIncentivized.sdkLocation.equals(str)) {
            this.incentivizedAds.PushPlacement(str);
            this.onScreenIncentivized.OnReward(this.onScreenIncentivized.UUID, finishState == com.unity3d.ads.UnityAds.FinishState.COMPLETED, this.onScreenIncentivized.sdkLocation);
            this.onScreenIncentivized.OnClose(this.onScreenIncentivized.sdkLocation);
        } else if (this.onScreenInterstitial != null && this.onScreenInterstitial.sdkLocation.equals(str)) {
            this.interstitials.PushPlacement(str);
            this.onScreenInterstitial.OnClose(this.onScreenInterstitial.sdkLocation);
        }
        OnResumeGameAudio();
    }

    @Override // com.unity3d.ads.IUnityAdsListener
    public void onUnityAdsError(com.unity3d.ads.UnityAds.UnityAdsError unityAdsError, String str) {
        JavaLoggingDefines.LOG_ERROR("UnityAds.java", "onUnityAdsError", "unityAdsError = (" + unityAdsError.toString() + "), message = (" + str + ")");
    }
}
