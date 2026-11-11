package com.c.a.a.a.g;

import org.json.JSONObject;

/* JADX INFO: compiled from: AvidCommand.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String iS(String str) {
        return iW("setNativeViewState(" + str + ")");
    }

    public static String iT(String str) {
        return iW("setAppState(" + JSONObject.quote(str) + ")");
    }

    public static String ajw() {
        return iW("publishReadyEventForDeferredAdSession()");
    }

    public static String iU(String str) {
        return iW("publishVideoEvent(" + JSONObject.quote(str) + ")");
    }

    public static String aD(String str, String str2) {
        return iW("publishVideoEvent(" + JSONObject.quote(str) + "," + str2 + ")");
    }

    public static String iV(String str) {
        return iW("setAvidAdSessionContext(" + str + ")");
    }

    public static String iW(String str) {
        return "javascript: if(window.avidbridge!==undefined){avidbridge." + str + "}";
    }

    public static String iX(String str) {
        return "javascript: " + str;
    }
}
