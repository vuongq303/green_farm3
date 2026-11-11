package com.gameloft.adsmanager;

import com.gameloft.android.ANMP.GloftGF2F.S800x480.ci;
import com.gameloft.android.wrapper.au;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class AdsManager_CallBack {
    static ci cGame800x480;

    public static void OnAdView(int i, int i2, String str, String str2, int i3, int i4) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.a(i, i2, str, str2, i3, i4);
        }
    }

    public static void OnAdError(int i, String str) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.L(i, str);
        }
    }

    public static void OnAdClicked(int i, int i2, String str, String str2) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.a(i, i2, str, str2);
        }
    }

    public static void OnAdFinished(int i, int i2, String str, String str2, int i3, int i4) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.b(i, i2, str, str2, i3, i4);
        }
    }

    public static void OnIncentivizedAdReward(int i, int i2, boolean z, String str, String str2, String str3, int i3, int i4) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.a(i, i2, z, str, str2, str3, i3, i4);
        }
    }

    public static void OnAdInGameRedirectTo(int i, String str) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.M(i, str);
        }
    }

    public static void OnGameShouldPauseMusic() {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.MJ();
        }
    }

    public static void OnGameShouldResumeMusic() {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.MK();
        }
    }

    public static void OnMRAIDBannerWillExpand(String str) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.dF(str);
        }
    }

    public static void OnMRAIDBannerWasClosed(String str) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.dG(str);
        }
    }

    public static void OnAdSendTracking(int i, String str) throws JSONException {
        com.gameloft.android2d.d.b bVarQm = com.gameloft.android2d.d.b.Qm();
        if (bVarQm != null) {
            bVarQm.O(i, str);
        }
    }

    public static void OnAdsManagerInitialized() {
    }

    public static void OnIncentivizedAvailabilityChanged(String str, int i) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.v(str, i);
        }
    }

    public static void OnOfferWallAvailabilityChanged(String str, int i) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.w(str, i);
        }
    }
}
