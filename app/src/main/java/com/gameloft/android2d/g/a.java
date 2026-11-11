package com.gameloft.android2d.g;

import com.gameloft.android.ANMP.GloftGF2F.SplashScreenActivity;

/* JADX INFO: compiled from: WelcomeScreen.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static int bsf = 1;

    public static void mY(int i) {
        bsf = i;
    }

    public static void mZ(int i) {
        SplashScreenActivity.mr(i);
    }

    public static void x(String str, int i) {
        if (bsf == 0) {
            SplashScreenActivity.x(str, i);
        } else if (bsf == 1) {
            SplashScreenActivity.mt(i);
        }
    }

    public static int y(String str, int i) {
        if (bsf == 0) {
            return SplashScreenActivity.y(str, i);
        }
        if (bsf == 1) {
            return SplashScreenActivity.mu(i);
        }
        return -1;
    }

    public static void dQ(String str) {
        if (bsf == 0) {
            SplashScreenActivity.dQ(str);
        } else if (bsf == 1) {
            SplashScreenActivity.dK(str);
        }
    }

    public static void VC() {
        SplashScreenActivity.Ne();
    }

    public static boolean isRunning() {
        return SplashScreenActivity.CG;
    }
}
