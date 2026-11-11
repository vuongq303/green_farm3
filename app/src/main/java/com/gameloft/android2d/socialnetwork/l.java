package com.gameloft.android2d.socialnetwork;

import com.gameloft.android.wrapper.au;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
public class l {
    private static int Kx = 1;

    public static int getType() {
        return Kx;
    }

    public static void I(String str, String str2) {
        if (Kx == 1) {
            s.I(str, str2);
        }
    }

    public static void ar(int i, int i2) {
        if (Kx == 1) {
            s.ar(i, i2);
        } else if (Kx == 6) {
            aj.ar(i, i2);
        }
    }

    public static void eL(int i) {
    }

    public static void mU(int i) {
        if (Kx == 1) {
            s.mU(i);
        }
    }

    public static void nT() {
        MIDlet.coD = 1;
        if (Kx == 1) {
            s.nT();
        } else if (Kx == 6) {
            aj.nT();
        }
    }

    public static void nU() {
        if (Kx == 1) {
            s.nU();
        } else if (Kx == 6) {
            aj.nU();
        }
    }

    public static boolean nY() {
        if (Kx == 1) {
            return s.nY();
        }
        if (Kx == 6) {
            return aj.Vh();
        }
        return false;
    }

    public static void nV() {
        au.getActivity().runOnUiThread(new n(Kx));
    }

    public static boolean nW() {
        if (Kx == 1) {
            return s.nW();
        }
        return false;
    }

    public static boolean nX() {
        if (Kx == 1) {
            return s.nX();
        }
        return false;
    }

    public static i UR() {
        if (Kx == 1) {
            return s.UR();
        }
        return null;
    }

    public static String getPassword() {
        if (Kx == 1) {
            return s.getPassword();
        }
        if (Kx == 6) {
            return aj.ju();
        }
        return null;
    }

    public static javax.microedition.lcdui.ao ha(String str) {
        if (Kx == 1) {
            return s.ha(str);
        }
        return null;
    }

    public static void a(i iVar, i[] iVarArr, String str, String str2) {
        au.getActivity().runOnUiThread(new o(iVar, iVarArr, str, str2));
    }

    public static void a(i[] iVarArr, i[] iVarArr2, String str, String str2) {
        au.getActivity().runOnUiThread(new p(iVarArr, iVarArr2, str, str2));
    }

    public static void ao(String str, String str2) {
        au.getActivity().runOnUiThread(new q(str, str2));
    }

    public static i mV(int i) {
        if (Kx == 1) {
            return s.mV(i);
        }
        return null;
    }

    public static int oa() {
        if (Kx == 1) {
            return s.oa();
        }
        return 0;
    }

    public static void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        MIDlet.coD = 1;
        au.getActivity().runOnUiThread(new m(str, str2, str3, str4, str5, str6, str7, str8, str9));
    }

    public static void g(String str, String str2, String str3, String str4) {
    }
}
