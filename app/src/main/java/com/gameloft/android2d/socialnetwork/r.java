package com.gameloft.android2d.socialnetwork;

import com.gameloft.android.ANMP.GloftGF2F.S800x480.ci;
import com.gameloft.android.wrapper.au;
import org.json.JSONException;

/* JADX INFO: compiled from: SocialNetwork_CallBack.java */
/* JADX INFO: loaded from: classes.dex */
public class r {
    static ci cGame800x480;

    public static void a(int i, int i2, String str) {
        com.gameloft.android2d.d.b bVarQm = com.gameloft.android2d.d.b.Qm();
        if (bVarQm != null) {
            bVarQm.b(51820, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), str});
        }
    }

    public static void E(int i, String str) throws JSONException {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.E(i, str);
        }
    }

    public static void F(int i, String str) throws JSONException {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.F(i, str);
        }
    }

    public static void G(int i, String str) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.G(i, str);
        }
    }

    public static void d(String[] strArr) {
        if (au.bhM.indexOf("800x480") > -1) {
            ci ciVar = cGame800x480;
            ci.d(strArr);
        }
    }

    public static void hb(String str) {
        if (au.bhM.indexOf("800x480") > -1) {
        }
    }

    public static i US() {
        if (au.bhM.indexOf("800x480") > -1) {
        }
        return new i();
    }
}
