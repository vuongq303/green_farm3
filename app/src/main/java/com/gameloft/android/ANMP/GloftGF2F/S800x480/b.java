package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.gameloft.android2d.adsmanager.AdsManager;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private JSONObject Jo = null;
    private boolean Jp = false;
    private boolean Jq = false;
    private boolean Jr = false;
    private boolean Js = false;
    static final String[] Jl = {"ads_minigame_timer_skip", "ads_gems", "ads_spin", "ads_double_sc", "ads_double_passport", "ads_construction_skip_time", "ads_diamonds", "ads_cash", "ads_free_item", "ads_storage_slot", "ads_product_skip_time", "ads_special_currency", "ads_daily_reward", "ads_product_poi_skip_time", "ads_product_supply_skip_time"};
    private static b Jm = null;
    public static boolean Jn = false;
    private static boolean Jt = false;
    public static String Ju = "";
    public static int Jv = -1;
    public static boolean Jw = false;
    public static boolean Jx = false;
    public static boolean Jy = false;
    public static boolean Jz = false;
    public static boolean JA = false;
    public static boolean JB = false;
    public static boolean JC = false;
    public static boolean JD = false;
    public static boolean JE = false;
    public static boolean JF = false;
    public static boolean JG = false;
    public static boolean JH = false;
    public static boolean JI = false;
    public static boolean JJ = false;
    public static boolean JK = false;
    public static boolean JL = false;
    public static boolean JM = false;
    public static boolean JN = false;
    public static int JO = 0;
    public static int JP = 0;
    public static int JQ = 0;
    public static int JR = 0;
    public static int JS = 0;
    public static boolean JT = false;
    public static boolean JU = false;
    public static boolean JV = false;
    public static boolean JW = false;
    public static boolean JX = false;
    public static boolean JY = false;
    public static boolean JZ = false;
    public static boolean Ka = false;
    public static boolean Kb = true;
    public static boolean Kc = false;
    public static boolean Kd = false;
    public static boolean Ke = false;
    public static boolean Kf = false;
    public static boolean Kg = false;
    public static boolean Kh = false;

    public static void ar(String str) {
        String str2 = "THO DEBUG ADS :: Call IsIncentivizedAvailable location : " + str;
        AdsManager.IsIncentivizedAvailable(str);
    }

    public static b hL() {
        if (Jm == null) {
            Jm = new b();
        }
        return Jm;
    }

    public void CreateSingleAdsManagerInstance() {
        AdsManager.CreateSingleAdsManagerInstance();
    }

    public void Init() {
        try {
            CreateSingleAdsManagerInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        com.gameloft.android2d.c.a.setGameLanguage(GloftGF2F.acw.dr(ci.aYU).toLowerCase());
        AdsManager.SetAge(ci.aTL);
        AdsManager.SetAge(ci.aTL);
        com.gameloft.android2d.c.a.setGender(ci.auC == 1 ? 1 : 2);
        com.gameloft.android2d.c.a.setAnonymousAccount(s.js());
        if (com.gameloft.a.b.a.Wp() != null) {
            String strWp = com.gameloft.a.b.a.Wp();
            com.gameloft.android2d.c.a.SetFedID(strWp.substring(0, strWp.indexOf(",")));
        }
        AdsManager.EnableGLAdsTracking(true);
        com.gameloft.android2d.c.a.init();
        Jn = true;
    }

    public void OnAdError(int i, String str) {
        String str2 = "#HOAN OnAdError adType:" + i + "location:" + str;
        if (i == 3) {
            if (str.equals("enter_section_iap_shop")) {
                JO = 1;
                JK = true;
                return;
            }
            if (str.equals("enter_section_video_bubble")) {
                JP = 1;
                JL = true;
            } else if (str.equals("enter_section_video_lucky_wheel")) {
                JQ = 1;
                JM = true;
            } else if (str.equals("enter_section_video_daily_stamp")) {
                JR = 1;
                JN = true;
            }
        }
    }

    public void OnAdFinished(int i, int i2, String str, String str2, int i3, int i4) {
        String str3 = "#HOAN onAdCheckAdAvailable adType:" + i + " campaignID: " + i3 + " location:" + str;
        if (i == 3) {
            if (str.equals("enter_section_iap_shop")) {
                JO = 1;
                JK = true;
                return;
            }
            if (str.equals("enter_section_video_bubble")) {
                JP = 1;
                JL = true;
            } else if (str.equals("enter_section_video_lucky_wheel")) {
                JQ = 1;
                JM = true;
            } else if (str.equals("enter_section_video_daily_stamp")) {
                JR = 1;
                JN = true;
            }
        }
    }

    public void e(String str, int i) {
        String str2 = "THO DEBUG ADS :: AdsManager_IncentivizedAvailabilityChanged  availabilityStatus: " + i + " location:" + str;
        int i2 = 0;
        switch (i) {
            case 0:
                i2 = 2;
                break;
            case 1:
                i2 = 4;
                break;
            case 2:
                i2 = 3;
                break;
        }
        if (str.equals("enter_section_iap_shop")) {
            JO = i2;
            JK = true;
        } else if (str.equals("enter_section_video_bubble")) {
            JP = i2;
            JL = true;
        } else if (str.equals("enter_section_video_lucky_wheel")) {
            JQ = i2;
            JM = true;
        } else if (str.equals("enter_section_video_daily_stamp")) {
            JR = i2;
            JN = true;
        }
        Kb = true;
    }
}
