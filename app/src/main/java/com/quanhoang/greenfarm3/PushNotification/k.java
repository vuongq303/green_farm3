package com.quanhoang.greenfarm3.PushNotification;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.appevents.AppEventsConstants;

/* JADX INFO: compiled from: Prefs.java */
/* JADX INFO: loaded from: classes.dex */
public final class k {
    public static SharedPreferences Q(Context context) {
        return context.getSharedPreferences("GLPN", 0);
    }

    public static void init(Context context) {
        SharedPreferences sharedPreferencesQ = Q(context);
        SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
        if (!sharedPreferencesQ.contains("enablePushNotification")) {
            editorEdit.putBoolean("enablePushNotification", true);
        }
        if (!sharedPreferencesQ.contains("pn_local_enable")) {
            editorEdit.putBoolean("pn_local_enable", true);
        }
        if (!sharedPreferencesQ.contains("pn_remote_enable")) {
            editorEdit.putBoolean("pn_remote_enable", true);
        }
        if (!sharedPreferencesQ.contains("pn_online_enable")) {
            editorEdit.putBoolean("pn_online_enable", true);
        }
        if (!sharedPreferencesQ.contains("pn_dont_disturbe_enable")) {
            editorEdit.putBoolean("pn_dont_disturbe_enable", true);
            editorEdit.putInt("pn_dont_disturbe_start", 1380);
            editorEdit.putInt("pn_dont_disturbe_end", 480);
        }
        if (!sharedPreferencesQ.contains("pn_count_push_local")) {
            editorEdit.putInt("pn_count_push_local", 0);
        }
        if (!sharedPreferencesQ.contains("pn_time_first_push_local")) {
            editorEdit.putString("pn_time_first_push_local", i.ae(AppEventsConstants.EVENT_PARAM_VALUE_NO));
        }
        if (!sharedPreferencesQ.contains("pn_language")) {
            editorEdit.putString("pn_language", "");
        }
        editorEdit.commit();
    }

    public static boolean R(Context context) {
        return Q(context).getBoolean("enablePushNotification", true);
    }

    public static boolean S(Context context) {
        return Q(context).getBoolean("pn_online_enable", true);
    }

    public static void b(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = Q(context).edit();
        editorEdit.putBoolean("enablePushNotification", z);
        editorEdit.commit();
    }

    public static void c(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = Q(context).edit();
        editorEdit.putBoolean("pn_online_enable", z);
        editorEdit.commit();
    }

    public static boolean T(Context context) {
        return Q(context).getBoolean("pn_dont_disturbe_enable", true);
    }

    public static void t(Context context, int i) {
        SharedPreferences.Editor editorEdit = Q(context).edit();
        editorEdit.putInt("pn_count_push_local", i);
        editorEdit.commit();
    }

    public static void o(Context context, String str) {
        SharedPreferences.Editor editorEdit = Q(context).edit();
        editorEdit.putString("pn_time_first_push_local", str);
        editorEdit.commit();
    }

    public static String U(Context context) {
        return Q(context).getString("pn_time_first_push_local", i.ae(AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    public static int V(Context context) {
        return Q(context).getInt("pn_count_push_local", 0);
    }

    public static void p(Context context, String str) {
        SharedPreferences.Editor editorEdit = Q(context).edit();
        editorEdit.putString("pn_language", str);
        editorEdit.commit();
    }

    public static String W(Context context) {
        return Q(context).getString("pn_language", "");
    }
}
