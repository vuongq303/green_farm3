package com.gameloft.glads;

import android.app.ActivityManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.gameloft.android.wrapper.au;
import java.io.File;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class GLDevice {
    private static String TAG = "GLADS";
    static String userAgent = "";

    public static String GetLanguage() {
        return au.Pk();
    }

    public static String GetSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String GetModel() {
        return (Build.MANUFACTURER + "_" + Build.MODEL).replaceAll(" ", "");
    }

    public static String GetHDIDFV() {
        return au.Os();
    }

    public static String GetGoogleAdID() {
        return au.Qe() == 0 ? au.NB() : "";
    }

    public static String GetGoogleOptOut() {
        return "" + au.Qe();
    }

    public static String GetNetworkCountryIso() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getNetworkCountryIso();
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetOperatorMCC() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getSimOperator().substring(0, 3);
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetOperatorMNC() {
        if (!au.PT()) {
            return "";
        }
        try {
            return au.getSimOperator().substring(3);
        } catch (Exception e) {
            return "";
        }
    }

    public static String GetConnectionType() {
        if (HasInternetConnection()) {
            return au.Pg() == 1 ? "wifi" : "cellular";
        }
        return "none";
    }

    public static String GetCachePath() {
        return AndroidUtils.GetContext().getFilesDir().getAbsolutePath();
    }

    public static boolean HasInternetConnection() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) AndroidUtils.GetContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
    }

    public static boolean IsDeviceRooted() {
        String str = Build.TAGS;
        if (str != null && str.contains("test-keys")) {
            return true;
        }
        try {
            return new File("/system/app/Superuser.apk").exists();
        } catch (Exception e) {
        }
    }

    public static float GetUsedMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) AndroidUtils.GetContext().getSystemService("activity")).getMemoryInfo(memoryInfo);
        if (Build.VERSION.SDK_INT >= 16) {
            return memoryInfo.totalMem - memoryInfo.availMem;
        }
        return 0.0f;
    }

    public static float GetTotalMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) AndroidUtils.GetContext().getSystemService("activity")).getMemoryInfo(memoryInfo);
        if (Build.VERSION.SDK_INT >= 16) {
            return memoryInfo.totalMem;
        }
        return 0.0f;
    }

    public static String GetCountry() {
        return Locale.getDefault().getCountry();
    }

    public static String GenerateUUID() {
        return au.GenerateUUID();
    }

    public static int GetUserLocationStatus() {
        return au.PO();
    }

    public static float GetUserLocationLatitude() {
        return (float) au.PP();
    }

    public static float GetUserLocationLongitude() {
        return (float) au.PQ();
    }

    public static float GetUserLocationAccuracy() {
        return au.PR();
    }
}
