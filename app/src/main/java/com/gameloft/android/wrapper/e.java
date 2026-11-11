package com.gameloft.android.wrapper;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: DataSharing.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    private static ArrayList<String> bfo = new ArrayList<>();
    private static Context bfp = null;
    private static HashMap<String, String> bfq = new HashMap<>();
    private static HashMap<String, String> bfr = new HashMap<>();

    public static void init(Context context) {
        if (bfp == null) {
            bfp = context;
            List<ProviderInfo> listQueryContentProviders = bfp.getPackageManager().queryContentProviders((String) null, 0, 0);
            for (int i = 0; i < listQueryContentProviders.size(); i++) {
                String str = listQueryContentProviders.get(i).authority;
                if (str.startsWith("com.gameloft")) {
                    bfo.add(str);
                }
            }
            for (int i2 = 0; i2 < bfo.size(); i2++) {
                Uri uri = Uri.parse("content://" + bfo.get(i2) + "/key/");
                if (bfo.get(i2).contains(bfp.getPackageName())) {
                    a(uri, true);
                } else {
                    a(uri, false);
                }
            }
        }
    }

    private static void a(Uri uri, boolean z) {
        if (z) {
        }
        String str = "Printing content for " + uri.toString();
        try {
            Cursor cursorQuery = au.getContext().getContentResolver().query(uri, new String[]{"key", FirebaseAnalytics.Param.VALUE}, null, null, null);
            if (cursorQuery != null) {
                String str2 = "columns = " + cursorQuery.getColumnCount();
                String str3 = "rows = " + cursorQuery.getCount();
                cursorQuery.moveToFirst();
                for (int i = 0; i < cursorQuery.getCount(); i++) {
                    String str4 = "For row " + i + ":";
                    String str5 = "key = " + cursorQuery.getString(0);
                    String str6 = "value = " + cursorQuery.getString(1);
                    if (z) {
                        bfr.put(cursorQuery.getString(0), cursorQuery.getString(1));
                    } else {
                        bfq.put(cursorQuery.getString(0), cursorQuery.getString(1));
                    }
                    if (!cursorQuery.isLast()) {
                        cursorQuery.moveToNext();
                    }
                }
                cursorQuery.close();
            }
        } catch (Exception e) {
            String str7 = "Error logging for provider " + uri.toString();
            String str8 = "Error logging with exception " + e.toString();
        }
        if (z) {
        }
    }

    public static Context getContext() {
        return bfp;
    }

    public static String ec(String str) {
        if (bfr.containsKey(str)) {
            String str2 = bfr.get(str);
            String str3 = "Reading " + str + " from local map = " + str2;
            if (TextUtils.isEmpty(str2)) {
                return "";
            }
            return str2;
        }
        if (bfq.containsKey(str)) {
            String str4 = bfq.get(str);
            String str5 = "Reading " + str + " from other GL app = " + str4;
            if (TextUtils.isEmpty(str4)) {
                str4 = "";
            }
            bfr.put(str, str4);
            new f(str, str4).start();
            return str4;
        }
        String str6 = str + " not found on local map or external apps.";
        return "";
    }

    public static void a(Uri uri, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FirebaseAnalytics.Param.VALUE, str2);
        try {
            if (bfp.getContentResolver().update(uri, contentValues, "key='" + str + "'", null) < 1) {
                contentValues.put("key", str);
                contentValues.put(FirebaseAnalytics.Param.VALUE, str2);
                bfp.getContentResolver().insert(uri, contentValues);
            }
        } catch (Exception e) {
            String str3 = "Error updating or creating " + str2 + " from provider " + uri.toString();
        }
    }

    public static void S(String str, String str2) {
        boolean z = false;
        String str3 = "setSharedValue " + str + " with value=" + str2;
        boolean z2 = bfq.containsKey(str) && bfq.get(str).equals(str2);
        if (bfr.containsKey(str) && bfr.get(str).equals(str2)) {
            z = true;
        }
        if (!z || !z2) {
            bfr.put(str, str2);
            bfq.put(str, str2);
            new g(str, str2).start();
            return;
        }
        String str4 = "setSharedValue " + str + " with same value as the already saved. SKIP";
    }
}
