package com.gameloft.glads;

import com.gameloft.adsmanager.AdsManager;
import com.gameloft.android.wrapper.au;
import com.gameloft.android2d.d.b;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: loaded from: classes.dex */
public class GLAds {
    public static final int AD_AVAILABLE = 2;
    public static final int AD_AVAILABLE_CAPPING_REACHED = 1;
    public static final int AD_BOTTOM_CENTER = 3;
    public static final int AD_BOTTOM_LEFT = 4;
    public static final int AD_BOTTOM_RIGHT = 5;
    public static final int AD_NOT_AVAILABLE = 0;
    public static final int AD_NOT_DISPLAY_REASON_CANCELLED = 212346;
    public static final int AD_NOT_DISPLAY_REASON_INVALID_FORMAT = 209321;
    public static final int AD_NOT_DISPLAY_REASON_NETWORK_ERROR = 207943;
    public static final int AD_NOT_DISPLAY_REASON_NONE = 207956;
    public static final int AD_NOT_DISPLAY_REASON_NO_AD_AVAILABLE = 207944;
    public static final int AD_NOT_DISPLAY_REASON_TIMEOUT = 207942;
    public static final int AD_TOP_CENTER = 2;
    public static final int AD_TOP_LEFT = 0;
    public static final int AD_TOP_RIGHT = 1;
    public static final int AD_TYPE_BANNER = 0;
    public static final int AD_TYPE_FULLSCREEN = 1;
    public static final int AD_TYPE_NATIVEAD = 2;
    public static final int AD_TYPE_NR_AD_TYPES = 3;
    public static final int BANNER_STATE_NOT_DISPLAYED = 2;
    public static final int BANNER_STATE_ON_SCREEN = 1;
    public static final int BANNER_STATE_QUERY = 0;
    public static final int FULLSCREEN_AD_STATE_FINISHED = 2;
    public static final int FULLSCREEN_AD_STATE_ON_SCREEN = 1;
    public static final int FULLSCREEN_AD_STATE_QUERY = 0;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    private static String K_LINK_DEFAULT_ADS_BASE_URL = "https://578756.gameloft.com/ads/adserver/index.php";
    public static boolean b_hasCustomId = false;
    public static final int[] GLADS_EVENT_ID_LIST = {219666, 219672, 165062, 209296, 209318, 219670, 219671, 229216, 222356, 222353};
    public static String gameLanguage = "en";

    public static void init() {
        setBaseURLFromFederation();
        AdsManager.adsv2_GameCode = MIDlet.d(au.getContext(), "GAME-IGP-CODE");
        AdsManager.adsv2_GameVersion = MIDlet.cnN;
        AdsManager.adsv2_GameLanguage = gameLanguage;
        AdsManager.adsv2_ClientID = GLAds_CallBack.getClientId();
        AdsManager.adsv2_DataCenter = GLAds_CallBack.getDataCenter();
        AdsManager.SetGLAdsServerTrackingEvents(getServerTrackingEvents());
        b bVarQm = b.Qm();
        AdsManager.adsv2_GDID = bVarQm.ga();
        AdsManager.adsv2_GGI = "" + bVarQm.Nv();
        AdsManager.adsv2_deviceCountry = "" + au.Pl();
        AndroidDevice.CalculateScreenSize();
    }

    public static void setGameLanguage(String str) {
        if (str != null && str.toLowerCase().equals("es_latam")) {
            gameLanguage = "es";
        } else {
            gameLanguage = str;
        }
        AdsManager.adsv2_GameLanguage = gameLanguage;
    }

    public static void SetFedID(String str) {
        AdsManager.adsv2_FedID = str;
    }

    public static void setAnonymousAccount(String str) {
        AdsManager.adsv2_AnonymousAccount = str;
    }

    public static void SetAge(int i) {
    }

    public static void setGender(int i) {
        AdsManager.adsv2_userGender = i;
    }

    public static void setBaseURLFromFederation() {
        String adsAgency = GLAds_CallBack.getAdsAgency();
        if (adsAgency == null || adsAgency.equals("")) {
            adsAgency = K_LINK_DEFAULT_ADS_BASE_URL;
        }
        AdsManager.adsv2_Urlbase = adsAgency;
    }

    public static String getServerTrackingEvents() {
        String strSubstring = "[";
        b bVarQm = b.Qm();
        for (int i = 0; i < GLADS_EVENT_ID_LIST.length; i++) {
            if (!bVarQm.aU(GLADS_EVENT_ID_LIST[i])) {
                strSubstring = strSubstring + GLADS_EVENT_ID_LIST[i] + ",";
            }
        }
        if (!strSubstring.equals("[")) {
            strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
        }
        return strSubstring + "]";
    }

    public static String getGameLanguage(String str) {
        return gameLanguage;
    }

    public static void PushDisplayAdsEvent(Object[] objArr, int i) {
    }

    public static boolean hasCustomID() {
        return b_hasCustomId;
    }
}
