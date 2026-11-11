package com.gameloft.adsmanager.Incentivized;

import com.gameloft.adsmanager.BaseAdsProvider;
import com.gameloft.adsmanager.JavaLoggingDefines;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseIncentivizedObject implements IncentivizedObjectAPIInterface, IncentivizedObjectInterface {
    private BaseAdsProvider m_provider;
    private boolean m_onDisplay = false;
    private String m_adsLocation = "";

    public BaseIncentivizedObject(BaseAdsProvider baseAdsProvider) {
        this.m_provider = baseAdsProvider;
    }

    public void ShowInternal(String str, String str2, String str3, String str4) {
        if (this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseIncentivizedObject", "ShowInternal", "Trying to show on screen incentivized");
            return;
        }
        this.m_adsLocation = str;
        try {
            Show(this.m_adsLocation, str2, str3, str4);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseIncentivizedObject", "ShowInternal", e);
        }
    }

    public void DestroyInternal() {
        try {
            Destroy();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseIncentivizedObject", "DestroyInternal", e);
        }
    }

    public boolean IsValidInternal() {
        try {
            return IsValid();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseIncentivizedObject", "IsValidInternal", e);
            return false;
        }
    }

    public void CloseInternal() {
        try {
            Close();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseIncentivizedObject", "CloseInternal", e);
        }
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectAPIInterface
    public void OnShowError(int i, String str) {
        this.m_onDisplay = false;
        this.m_provider.OnIncentivizedShowError(i, this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectAPIInterface
    public void OnDisplay(String str) {
        this.m_onDisplay = true;
        this.m_provider.OnIncentivizedOnScreen(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectAPIInterface
    public void OnClose(String str) {
        this.m_onDisplay = false;
        this.m_provider.OnIncentivizedClosed(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectAPIInterface
    public void OnClick(String str) {
        this.m_provider.OnIncentivizedClicked(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Incentivized.IncentivizedObjectAPIInterface
    public void OnReward(String str, boolean z, String str2) {
        this.m_provider.OnIncentivizedReward(str, z, this.m_adsLocation, str2);
    }
}
