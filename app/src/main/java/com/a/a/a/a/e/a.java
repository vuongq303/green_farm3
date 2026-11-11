package com.a.a.a.a.e;

import android.os.Build;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static String ahA() {
        return Build.MANUFACTURER + "; " + Build.MODEL;
    }

    public static String aig() {
        return "Android";
    }

    public static JSONObject aih() {
        JSONObject jSONObject = new JSONObject();
        b.b(jSONObject, "deviceType", ahA());
        b.b(jSONObject, "osVersion", b());
        b.b(jSONObject, "os", aig());
        return jSONObject;
    }

    public static String b() {
        return Integer.toString(Build.VERSION.SDK_INT);
    }
}
