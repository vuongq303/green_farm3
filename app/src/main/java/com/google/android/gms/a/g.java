package com.google.android.gms.a;

import com.google.android.gms.internal.measurement.zzcl;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    private static String I(String str, int i) {
        if (i > 0) {
            return new StringBuilder(String.valueOf(str).length() + 11).append(str).append(i).toString();
        }
        zzcl.zzf("index out of range for prefix", str);
        return "";
    }

    public static String nf(int i) {
        return I("cd", i);
    }

    public static String ng(int i) {
        return I("cm", i);
    }

    public static String nh(int i) {
        return I("pr", i);
    }

    public static String ni(int i) {
        return I("promo", i);
    }

    public static String nj(int i) {
        return I("pi", i);
    }

    public static String nk(int i) {
        return I("il", i);
    }
}
