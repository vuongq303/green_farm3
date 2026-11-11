package com.gameloft.android2d.e;

import com.gameloft.android.ANMP.GloftGF2F.IGPFreemiumActivity;

/* JADX INFO: compiled from: IGPFreemium.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private static int bph = 0;
    private static boolean bpi = false;

    public static void M(int i, boolean z) {
        bph = i;
        bpi = z;
        Us();
    }

    public static void Us() {
        IGPFreemiumActivity.CJ = bph;
        IGPFreemiumActivity.CK = bpi;
        IGPFreemiumActivity.gD();
    }

    public static boolean isRunning() {
        return IGPFreemiumActivity.CG;
    }

    public static void Ut() {
        if (IGPFreemiumActivity.Dh != null) {
            IGPFreemiumActivity.Dh.dismiss();
        }
        Us();
    }
}
