package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.android.wrapper.au;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/* JADX INFO: compiled from: LocalPushManager.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    public static int Ft = 2073600;

    static void K(Context context) {
        if (au.getContext() == null) {
            au.an(context);
        }
        Init();
    }

    static void Init() {
        Context context = au.getContext();
        if (context != null) {
            k.Q(context);
        }
    }

    static int hd() {
        int i = 0;
        while (k.Q(au.getContext()).contains("PN_LID_" + i)) {
            i++;
        }
        return i;
    }

    static String a(Bundle bundle, int i) {
        int iHd = hd();
        Context context = au.getContext();
        SharedPreferences.Editor editorEdit = k.Q(context).edit();
        editorEdit.putInt("PN_AID", iHd);
        editorEdit.commit();
        Calendar.getInstance().add(13, i);
        String packageName = context.getPackageName();
        bundle.putString("lID", "PN_LID_" + iHd);
        Intent intent = new Intent(context, (Class<?>) LocalPushReceiver.class);
        intent.putExtra(packageName + ".alarm_content", bundle);
        ((AlarmManager) context.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + ((long) (i * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS)), PendingIntent.getBroadcast(context, iHd, intent, 134217728));
        a("PN_LID_" + iHd, Integer.toString(i), bundle);
        return "PN_LID_" + iHd;
    }

    public static void a(String str, String str2, Bundle bundle) {
        String str3 = "";
        Iterator<String> it = bundle.keySet().iterator();
        if (bundle != null) {
            while (it.hasNext()) {
                String next = it.next();
                str3 = (next == null || bundle.getString(next) == null || bundle.getString(next).length() <= 0) ? str3 : str3 + "|key|" + next + "=" + bundle.getString(next);
            }
            str3 = str3 + "|key|delay=" + str2;
        }
        SharedPreferences.Editor editorEdit = k.Q(au.getContext()).edit();
        editorEdit.putString(str, str3);
        editorEdit.commit();
    }

    public static void he() {
        int length;
        Context context = au.getContext();
        SharedPreferences sharedPreferencesQ = k.Q(context);
        String str = "";
        String str2 = "";
        int i = 0;
        for (int i2 = sharedPreferencesQ.getInt("PN_AID", 0); i2 >= 0; i2--) {
            if (sharedPreferencesQ.contains("PN_LID_" + i2)) {
                String string = sharedPreferencesQ.getString("PN_LID_" + i2, null);
                Bundle bundle = new Bundle();
                int i3 = 0;
                length = i;
                while (length < string.length()) {
                    int iIndexOf = string.indexOf("|key|");
                    int iIndexOf2 = string.indexOf("=", iIndexOf);
                    String strSubstring = string.substring(iIndexOf + "|key|".length(), iIndexOf2);
                    String strSubstring2 = string.substring(iIndexOf2 + 1);
                    int i4 = iIndexOf2 + 1;
                    if (strSubstring2.contains("|key|")) {
                        length = strSubstring2.indexOf("|key|");
                    } else {
                        length = strSubstring2.length();
                    }
                    String strSubstring3 = strSubstring2.substring(0, length);
                    if ("delay".equals(strSubstring)) {
                        i3 = Integer.parseInt(strSubstring3);
                    } else if ("schedule_time".equals(strSubstring)) {
                        str = strSubstring3;
                    } else if ("lID".equals(strSubstring)) {
                        str2 = strSubstring3;
                    }
                    bundle.putString(strSubstring, strSubstring3);
                    string = strSubstring2;
                }
                String string2 = bundle.getString("pn_group_ID");
                if (!q.ai(string2)) {
                    try {
                        q.u(context, Integer.parseInt(string2));
                    } catch (Exception e) {
                    }
                }
                if (!ad(str)) {
                    a(str2, i2, bundle, i3);
                }
            } else {
                length = i;
            }
            i = length;
        }
    }

    static void a(String str, int i, Bundle bundle, int i2) {
        hd();
        Context context = au.getContext();
        Calendar.getInstance().add(13, i2);
        String packageName = context.getPackageName();
        bundle.putString("lID", str);
        Intent intent = new Intent(context, (Class<?>) LocalPushReceiver.class);
        intent.putExtra(packageName + ".alarm_content", bundle);
        ((AlarmManager) context.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + ((long) (i2 * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS)), PendingIntent.getBroadcast(context, i, intent, 134217728));
        bundle.putString("creation_time", af(AppEventsConstants.EVENT_PARAM_VALUE_NO));
        bundle.putString("schedule_time", af(Integer.toString(i2)));
        a(str, Integer.toString(i2), bundle);
    }

    public static void ac(String str) {
        Context context = au.getContext();
        ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, Integer.parseInt(str.substring("PN_LID_".length())), new Intent(context, (Class<?>) LocalPushReceiver.class), 134217728));
        SharedPreferences.Editor editorEdit = k.Q(context).edit();
        editorEdit.remove(str);
        editorEdit.commit();
    }

    public static void hf() {
        Context context = au.getContext();
        SharedPreferences sharedPreferencesQ = k.Q(context);
        SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
        Intent intent = new Intent(context, (Class<?>) LocalPushReceiver.class);
        for (int i = 100; i >= 0; i--) {
            String str = "PN_LID_" + i;
            if (sharedPreferencesQ.contains(str)) {
                ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, i, intent, 134217728));
                editorEdit.remove(str);
            }
        }
        editorEdit.putInt("PN_AID", 0);
        editorEdit.commit();
    }

    static boolean ad(String str) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(ae(AppEventsConstants.EVENT_PARAM_VALUE_NO)).getTime() >= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(str).getTime();
        } catch (Exception e) {
            String str2 = "Exception:" + e;
            return false;
        }
    }

    static String ae(String str) {
        try {
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(5);
            int i2 = calendar.get(2);
            return i + "/" + (i2 + 1) + "/" + calendar.get(1) + " " + calendar.get(11) + ":" + calendar.get(12) + ":" + (calendar.get(13) + Integer.parseInt(str));
        } catch (Exception e) {
            return "";
        }
    }

    static boolean i(String str, String str2) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(str2);
            Date date2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(str);
            if (date.getYear() > date2.getYear()) {
                return true;
            }
            if (date.getYear() != date2.getYear() || date.getMonth() <= date2.getMonth()) {
                return date.getYear() == date2.getYear() && date.getMonth() == date2.getMonth() && date.getDate() > date2.getDate();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String af(String str) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(System.currentTimeMillis() + (Long.parseLong(str) * 1000)));
        } catch (Exception e) {
            return "";
        }
    }
}
