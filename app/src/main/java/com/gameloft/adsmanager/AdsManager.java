package com.gameloft.adsmanager;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdsManager {
    public static String adsv2_AnonymousAccount;
    public static String adsv2_ClientID;
    public static String adsv2_DataCenter;
    public static String adsv2_FedID;
    public static String adsv2_GDID;
    public static String adsv2_GGI;
    public static String adsv2_GameCode;
    public static String adsv2_GameLanguage;
    public static String adsv2_GameVersion;
    public static String adsv2_ServerTrackingEvents;
    public static String adsv2_Urlbase;
    public static String adsv2_deviceCountry;
    public static int adsv2_userGender;
    static String TAG = "AdsManagerLib";
    static ViewGroup parentViewGroup = null;
    static Activity mainActivity = null;
    static RelativeLayout relativeLayout = null;

    public static native void ChangeBannerPosition(int i, int i2, int i3);

    public static native void ChangeNativePosition(int i, int i2, int i3, int i4, int i5, int i6);

    public static native void DisableProviders(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6);

    public static native void EnableGLAdsTracking(boolean z);

    public static native void HandleBackKey();

    public static native void HideAd(int i);

    public static native boolean IsAdOnScreen(int i);

    public static native boolean IsAdsManagerInitialized();

    public static native boolean IsFullScreenAdOnScreen();

    public static native int IsIncentivizedAvailable(String str);

    public static native int IsOfferWallAvailable(String str);

    public static native void NativeCreateSingleAdsManagerInstance();

    public static native void NativeInitializeAdsManager(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, boolean z);

    public static native void OnPause();

    public static native void OnResume();

    public static native void SetAge(int i);

    public static native void SetGLAdsAnonymousID(String str);

    public static native void SetGLAdsFacebookAccessToken(String str);

    public static native void SetGLAdsFacebookID(String str);

    public static native void SetGLAdsGDID(String str);

    public static native void SetGLAdsGameCenterUID(String str);

    public static native void SetGLAdsGliveAccount(String str);

    public static native void SetGLAdsGoogleID(String str);

    public static native void SetGLAdsServerTrackingEvents(String str);

    public static native void ShowBanner(String str, int i, int i2, int i3);

    public static native void ShowIncentivized(String str);

    public static native void ShowInterstitial(String str);

    public static native void ShowNativeAds(String str, int i, int i2, int i3, int i4, int i5, int i6, String str2, String str3);

    public static native void ShowOfferwall(String str);

    public static native void UpdateSettings(String str, String str2);

    public static void Init(Activity activity, ViewGroup viewGroup) {
        JavaUtils.AdsManagerLogInfo("AdsManager.java", "Init", "Init AdsManager Java");
        parentViewGroup = viewGroup;
        mainActivity = activity;
        parentViewGroup.post(new a());
        JavaUtils.Init();
    }

    static void SetActivity(Activity activity) {
        mainActivity = activity;
        JavaUtils.Init();
    }

    static void SetView(ViewGroup viewGroup) {
        parentViewGroup = viewGroup;
        parentViewGroup.post(new b());
    }

    public static void Pause() {
    }

    public static void Resume() {
    }

    public static String GetAppID(int i) {
        switch (i) {
            case 0:
                return "57e13d65ee991d8438000061";
            case 1:
            case 2:
                return "";
            case 3:
                return "3004674";
            case 4:
                return "5c3dc3fa7c736545b2150941";
            case 5:
                return "866987d5";
            case 6:
                return "";
            default:
                JavaUtils.AdsManagerLog("AdsManager.java", "GetAppID", "Wrong ad provider " + i);
                return "";
        }
    }

    public static void GetServerTimeStampCallback(int i, String str) {
    }

    public static void ProcessDailyReportCRMConfig(JSONObject jSONObject) {
    }

    public static void ProcessDailyReportCRMConfig(String str) {
    }

    public static void InitializeAdsManager(String str, String str2) {
        String str3 = adsv2_AnonymousAccount;
        String str4 = "" + adsv2_userGender;
        String str5 = adsv2_FedID;
        String str6 = adsv2_GameLanguage;
        String str7 = adsv2_Urlbase;
        String str8 = adsv2_GameCode;
        String str9 = adsv2_GameVersion;
        String str10 = adsv2_DataCenter;
        String str11 = adsv2_GGI;
        String str12 = adsv2_GDID;
        String str13 = adsv2_deviceCountry;
        NativeInitializeAdsManager(str, str2, adsv2_AnonymousAccount, adsv2_userGender, adsv2_FedID, adsv2_GameLanguage, adsv2_Urlbase, adsv2_GameCode, adsv2_GameVersion, adsv2_ClientID, adsv2_DataCenter, adsv2_GGI, adsv2_GDID, adsv2_deviceCountry, false);
    }

    public static void CreateSingleAdsManagerInstance() {
        NativeCreateSingleAdsManagerInstance();
    }
}
