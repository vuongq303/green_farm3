package com.google.android.gms.common.d;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    private static Boolean bQu;
    private static Boolean bQv;
    private static Boolean bQw;

    public static boolean aM(Context context) {
        if (bQu == null) {
            bQu = Boolean.valueOf(s.abB() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return bQu.booleanValue();
    }

    public static boolean aN(Context context) {
        return (!s.abE() || aO(context)) && aM(context);
    }

    public static boolean aO(Context context) {
        if (bQv == null) {
            bQv = Boolean.valueOf(s.abC() && context.getPackageManager().hasSystemFeature(com.google.android.gms.common.m.FEATURE_SIDEWINDER));
        }
        return bQv.booleanValue();
    }

    public static boolean aP(Context context) {
        if (bQw == null) {
            bQw = Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded"));
        }
        return bQw.booleanValue();
    }

    public static boolean abs() {
        return com.google.android.gms.common.m.sIsTestMode ? com.google.android.gms.common.m.sTestIsUserBuild : "user".equals(Build.TYPE);
    }
}
