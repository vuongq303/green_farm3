package com.gameloft.adsmanager;

/* JADX INFO: loaded from: classes.dex */
public class JavaLoggingDefines {
    public static void LOG_INFO(String str, String str2, String str3) {
        JavaUtils.AdsManagerLog(str, str2, str3);
    }

    public static void LOG_WARNING(String str, String str2, String str3) {
        JavaUtils.AdsManagerLogWarning(str, str2, str3);
    }

    public static void LOG_ERROR(String str, String str2, String str3) {
        JavaUtils.AdsManagerLogError(str, str2, str3);
    }

    public static void LOG_EXCEPTION(String str, String str2, Throwable th) {
        JavaUtils.LogException(str, str2, th);
    }

    public static void LOG_DEBUG(String str, String str2, String str3) {
        JavaUtils.AdsManagerLog(str, str2, str3);
    }
}
