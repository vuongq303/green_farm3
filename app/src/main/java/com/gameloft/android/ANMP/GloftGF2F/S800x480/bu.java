package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: SocialNetwork_ImageBuffer.java */
/* JADX INFO: loaded from: classes.dex */
public class bu {
    static ay akl = null;
    static String userId = "";
    static int ako = 0;
    static int akp = 50;
    static ay[] akm = new ay[akp];
    static String[] akn = new String[akp];
    static int akq = -1;

    public static void clear() {
        for (int i = 0; i < akp; i++) {
            akn[i] = null;
            if (akm[i] != null) {
                akm[i].nk();
            }
            akm[i] = null;
        }
        if (akl != null) {
            akl.nk();
        }
        akl = null;
    }

    static boolean cE(String str) {
        akq = -1;
        for (int i = 0; i < akp; i++) {
            if (akn[i] != null && akn[i].compareTo(str) == 0) {
                akq = i;
                return true;
            }
        }
        return false;
    }

    static void a(String str, ay ayVar) {
        ako++;
        ako %= akp;
        if (akm[ako] != null) {
            akm[ako].nk();
        }
        akm[ako] = ayVar;
        akn[ako] = str;
    }

    static ay oc() {
        if (akq > -1) {
            return akm[akq];
        }
        return null;
    }
}
