package com.gameloft.adsmanager.OfferWall;

import com.gameloft.adsmanager.BaseAdsProvider;
import com.gameloft.adsmanager.JavaLoggingDefines;
import com.gameloft.adsmanager.JavaUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseOfferWallObject implements OfferWallObjectAPIInterface, OfferWallObjectInterface {
    private BaseAdsProvider m_provider;
    private boolean m_onDisplay = false;
    private String m_adsLocation = "";

    public BaseOfferWallObject(BaseAdsProvider baseAdsProvider) {
        this.m_provider = baseAdsProvider;
    }

    public void ShowInternal(String str, String str2) {
        if (this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseOfferWallObject", "ShowInternal", "Trying to show on screen offerwall");
            return;
        }
        this.m_adsLocation = str;
        try {
            Show(this.m_adsLocation, str2);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseOfferWallObject", "ShowInternal", e);
            this.m_provider.OnOfferWallShowError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, this.m_adsLocation, "exception");
        }
    }

    public boolean IsValidInternal() {
        try {
            return IsValid();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseOfferWallObject", "IsValidInternal", e);
            return false;
        }
    }

    public void DestroyInternal() {
        try {
            Destroy();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseOfferWallObject", "DestroyInternal", e);
        }
    }

    public void CloseInternal() {
        try {
            Close();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseOfferWallObject", "CloseInternal", e);
        }
    }

    @Override // com.gameloft.adsmanager.OfferWall.OfferWallObjectAPIInterface
    public void OnShowError(int i, String str) {
        this.m_onDisplay = false;
        this.m_provider.OnOfferWallShowError(i, this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.OfferWall.OfferWallObjectAPIInterface
    public void OnDisplay(String str) {
        this.m_onDisplay = true;
        this.m_provider.OnOfferWallOnScreen(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.OfferWall.OfferWallObjectAPIInterface
    public void OnClose(String str) {
        this.m_onDisplay = false;
        this.m_provider.OnOfferWallClosed(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.OfferWall.OfferWallObjectAPIInterface
    public void OnClick(String str) {
        this.m_provider.OnOfferWallClicked(this.m_adsLocation, str);
    }
}
