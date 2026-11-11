package com.gameloft.android.wrapper;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;

/* JADX INFO: compiled from: LowProfileListener.java */
/* JADX INFO: loaded from: classes.dex */
public class z {
    private static View.OnSystemUiVisibilityChangeListener bfK = null;
    private static String activityName = "";

    public static void a(Activity activity, int i) {
        if (Build.VERSION.SDK_INT >= 14 && !android.support.v4.view.am.a(ViewConfiguration.get(activity.getApplicationContext()))) {
            if (i == 25 || i == 24) {
                k(activity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void j(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }

    public static void k(Activity activity) {
        if (Build.VERSION.SDK_INT >= 14 && !android.support.v4.view.am.a(ViewConfiguration.get(activity.getApplicationContext()))) {
            activity.runOnUiThread(new aa(activity));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void l(Activity activity) {
        if (bfK == null || !activityName.equals(activity.getClass().getSimpleName())) {
            activityName = activity.getClass().getSimpleName();
            String str = "Register Low Profile Listener to " + activityName;
            bfK = new ac(activity);
        }
        activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(bfK);
    }

    public static void m(Activity activity) {
        if (Build.VERSION.SDK_INT <= 20) {
            if ((Build.VERSION.SDK_INT < 14 || !ViewConfiguration.get(activity.getApplicationContext()).hasPermanentMenuKey()) && Build.VERSION.SDK_INT >= 11) {
                try {
                    activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
                    activity.getWindow().getDecorView().setSystemUiVisibility(0);
                } catch (Exception e) {
                }
            }
        }
    }
}
