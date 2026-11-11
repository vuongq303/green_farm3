package com.gameloft.adsmanager.Native;

import com.gameloft.adsmanager.BaseAdsProvider;
import com.gameloft.adsmanager.JavaLoggingDefines;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseNativeObject implements NativeObjectAPIInterface, NativeObjectInterface {
    private BaseAdsProvider m_provider;
    private boolean m_onDisplay = false;
    private String m_adsLocation = "";

    public void ShowInternal(int[] iArr, String[] strArr) {
        if (this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseNativeObject", "ShowInternal", "Trying to show on screen native");
            return;
        }
        try {
            this.m_adsLocation = strArr[0];
            Show(strArr[0], iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], strArr[1], strArr[2], strArr[3]);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseNativeObject", "ShowInternal", e);
        }
    }

    public void CloseInternal() {
        if (!this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseNativeObject", "ShowInternal", "Trying to close native that is not on screen");
            return;
        }
        try {
            Close();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseNativeObject", "CloseInternal", e);
        }
    }

    void DestroyInternal() {
        try {
            Destroy();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseNativeObject", "DestroyInternal", e);
        }
    }

    public void ChangeBoundsInternal(int i, int i2, int i3, int i4, int i5, int i6) {
        if (!this.m_onDisplay) {
            JavaLoggingDefines.LOG_ERROR("BaseNativeObject", "ChangeBoundsInternal", "Trying to chage the bound for native that is not on screen");
            return;
        }
        try {
            ChangeBounds(i, i2, i3, i4, i5, i6);
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseNativeObject", "ChangeBoundsInternal", e);
        }
    }

    public boolean IsValidInternal() {
        try {
            return IsValid();
        } catch (Exception e) {
            JavaLoggingDefines.LOG_EXCEPTION("BaseNativeObject", "IsValidInternal", e);
            return false;
        }
    }

    public BaseNativeObject(BaseAdsProvider baseAdsProvider) {
        this.m_provider = baseAdsProvider;
    }

    @Override // com.gameloft.adsmanager.Native.NativeObjectAPIInterface
    public void OnShowError(int i, String str) {
        this.m_onDisplay = false;
        this.m_provider.OnNativeShowError(i, this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Native.NativeObjectAPIInterface
    public void OnDisplay(String str) {
        this.m_onDisplay = true;
        this.m_provider.OnNativeOnScreen(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Native.NativeObjectAPIInterface
    public void OnClosed(String str) {
        this.m_onDisplay = false;
        this.m_provider.OnNativeClosed(this.m_adsLocation, str);
    }

    @Override // com.gameloft.adsmanager.Native.NativeObjectAPIInterface
    public void OnClick(String str) {
        this.m_provider.OnNativeClicked(this.m_adsLocation, str);
    }
}
