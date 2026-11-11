package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ad;

/* JADX INFO: loaded from: classes.dex */
public class g {
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = m.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final g bIg = new g();

    g() {
    }

    public static g YD() {
        return bIg;
    }

    private static String w(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("gcore_");
        sb.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        sb.append("-");
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        sb.append("-");
        if (context != null) {
            sb.append(context.getPackageName());
        }
        sb.append("-");
        if (context != null) {
            try {
                sb.append(com.google.android.gms.common.e.c.aV(context).getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return sb.toString();
    }

    public PendingIntent a(Context context, int i, int i2) {
        return a(context, i, i2, null);
    }

    public PendingIntent a(Context context, int i, int i2, String str) {
        Intent intentB = b(context, i, str);
        if (intentB == null) {
            return null;
        }
        return PendingIntent.getActivity(context, i2, intentB, 134217728);
    }

    public Intent b(Context context, int i, String str) {
        switch (i) {
            case 1:
            case 2:
                return (context == null || !com.google.android.gms.common.d.i.aN(context)) ? ad.aw("com.google.android.gms", w(context, str)) : ad.aaw();
            case 3:
                return ad.ib("com.google.android.gms");
            default:
                return null;
        }
    }

    public void cancelAvailabilityErrorNotifications(Context context) {
        m.cancelAvailabilityErrorNotifications(context);
    }

    public int getApkVersion(Context context) {
        return m.getApkVersion(context);
    }

    public String getErrorString(int i) {
        return m.getErrorString(i);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    public int isGooglePlayServicesAvailable(Context context, int i) {
        int iIsGooglePlayServicesAvailable = m.isGooglePlayServicesAvailable(context, i);
        if (m.isPlayServicesPossiblyUpdating(context, iIsGooglePlayServicesAvailable)) {
            return 18;
        }
        return iIsGooglePlayServicesAvailable;
    }

    public boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return m.isPlayServicesPossiblyUpdating(context, i);
    }

    public boolean isUninstalledAppPossiblyUpdating(Context context, String str) {
        return m.isUninstalledAppPossiblyUpdating(context, str);
    }

    public boolean ns(int i) {
        return m.isUserRecoverableError(i);
    }

    @Deprecated
    public Intent nt(int i) {
        return b(null, i, null);
    }
}
