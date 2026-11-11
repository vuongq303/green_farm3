package com.gameloft.adsmanager.Interstitial;

import com.gameloft.adsmanager.BaseAdsProvider;
import com.gameloft.adsmanager.JavaLoggingDefines;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseInterstitialObject implements InterstitialObjectAPIInterface, InterstitialObjectInterface {
    private BaseAdsProvider m_provider;
    private boolean m_onDisplay = false;
    private String m_adsLocation = "";

    public BaseInterstitialObject(BaseAdsProvider baseAdsProvider) {
        this.m_provider = baseAdsProvider;
    }

    public void ShowInternal(String str, String str2) {
        if (this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseInterstitialObject", "ShowInternal", "Trying to show on screen interstitial");
            return;
        }
        this.m_adsLocation = str;
        try {
            Show(this.m_adsLocation, str2);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseInterstitialObject", "ShowInternal", e);
        }
    }

    public boolean IsValidInternal() {
        try {
            return IsValid();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseInterstitialObject", "IsValidInternal", e);
            return false;
        }
    }

    public void DestroyInternal() {
        try {
            Destroy();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseInterstitialObject", "DestroyInternal", e);
        }
    }

    public void CloseInternal() {
        try {
            Close();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseInterstitialObject", "CloseInternal", e);
        }
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectAPIInterface
    public void OnShowError(int i, String str) {
        this.m_onDisplay = false;
        this.m_provider.OnInterstitialShowError(i, this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectAPIInterface
    public void OnDisplay(String str) {
        this.m_onDisplay = true;
        this.m_provider.OnInterstitialOnScreen(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectAPIInterface
    public void OnClose(String str) {
        this.m_onDisplay = false;
        this.m_provider.OnInterstitialClosed(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Interstitial.InterstitialObjectAPIInterface
    public void OnClick(String str) {
        this.m_provider.OnInterstitialClicked(this.m_adsLocation, str);
    }
}
