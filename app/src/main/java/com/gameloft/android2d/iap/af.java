package com.gameloft.android2d.iap;

import com.gameloft.android.ANMP.GloftGF2F.S800x480.ci;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: Links.java */
/* JADX INFO: loaded from: classes.dex */
public class af {
    static ci cGame800x480;

    public static String rj() {
        String strRj = null;
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            strRj = ci.rj();
        }
        if (strRj == null || strRj.equals("")) {
            return b.QE();
        }
        return strRj;
    }

    public static String rk() {
        if (au.bhM.indexOf("800x480") <= -1) {
            return null;
        }
        ci ciVar = cGame800x480;
        return ci.rk();
    }

    public static String Ru() {
        if (au.bhM.indexOf("800x480") <= -1) {
            return null;
        }
        return com.gameloft.android.ANMP.GloftGF2F.S800x480.s.js();
    }

    public static String Rv() {
        String strJ = com.gameloft.a.b.a.j(true, true);
        if (strJ != null && !strJ.contains("anonymous:")) {
            return "anonymous:" + strJ;
        }
        return strJ;
    }
}
