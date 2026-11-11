package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/* JADX INFO: loaded from: classes.dex */
public final class GooglePlayServicesUtil extends m {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";

    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";

    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = m.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2) {
        return getErrorDialog(i, activity, i2, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener) {
        if (m.isPlayServicesPossiblyUpdating(activity, i)) {
            i = 18;
        }
        return e.YB().a(activity, i, i2, onCancelListener);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return m.getErrorPendingIntent(i, context, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return m.getErrorString(i);
    }

    public static Context getRemoteContext(Context context) {
        return m.getRemoteContext(context);
    }

    public static Resources getRemoteResource(Context context) {
        return m.getRemoteResource(context);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return m.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        return m.isUserRecoverableError(i);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2) {
        return showErrorDialogFragment(i, activity, i2, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener) {
        return showErrorDialogFragment(i, activity, null, i2, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int i, Activity activity, Fragment fragment, int i2, DialogInterface.OnCancelListener onCancelListener) {
        if (m.isPlayServicesPossiblyUpdating(activity, i)) {
            i = 18;
        }
        e eVarYB = e.YB();
        if (fragment == null) {
            return eVarYB.b(activity, i, i2, onCancelListener);
        }
        Dialog dialogA = e.a(activity, i, com.google.android.gms.common.internal.w.a(fragment, e.YB().b(activity, i, "d"), i2), onCancelListener);
        if (dialogA == null) {
            return false;
        }
        e.a(activity, dialogA, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(int i, Context context) {
        e eVarYB = e.YB();
        if (m.isPlayServicesPossiblyUpdating(context, i) || m.isPlayStorePossiblyUpdating(context, i)) {
            eVarYB.zza(context);
        } else {
            eVarYB.y(context, i);
        }
    }
}
