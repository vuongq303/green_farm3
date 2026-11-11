package com.google.android.gms.common.d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static boolean A(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            return (com.google.android.gms.common.e.c.aV(context).getApplicationInfo(str, 0).flags & 2097152) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean abm() {
        return false;
    }

    public static int c(PackageInfo packageInfo) {
        Bundle bundle;
        if (packageInfo == null || packageInfo.applicationInfo == null || (bundle = packageInfo.applicationInfo.metaData) == null) {
            return -1;
        }
        return bundle.getInt(com.google.android.gms.common.m.KEY_METADATA_GOOGLE_PLAY_SERVICES_VERSION, -1);
    }

    public static int y(Context context, String str) {
        return c(z(context, str));
    }

    public static PackageInfo z(Context context, String str) {
        try {
            return com.google.android.gms.common.e.c.aV(context).getPackageInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
