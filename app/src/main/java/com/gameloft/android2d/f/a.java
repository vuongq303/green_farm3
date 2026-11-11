package com.gameloft.android2d.f;

import com.gameloft.android.ANMP.GloftGF2F.S800x480.ci;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: KB_GameInstances.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    static ci cGame800x480;

    public static String Dy() {
        if (au.bhM.indexOf("800x480") <= -1) {
            return null;
        }
        ci ciVar = cGame800x480;
        return ci.Dy();
    }

    public static void gC(int i) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.gC(i);
        }
    }
}
