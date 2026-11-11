package com.c.a.a.a.g;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: compiled from: AvidLogs.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    public static void e(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.e("AVID", str);
        }
    }

    public static void b(String str, Exception exc) {
        if (!TextUtils.isEmpty(str) || exc != null) {
            Log.e("AVID", str, exc);
        }
    }
}
