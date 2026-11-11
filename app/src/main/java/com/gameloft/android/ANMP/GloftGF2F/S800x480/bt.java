package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
public class bt {
    static void I(String str, String str2) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.I(str, str2);
        }
    }

    static void ar(int i, int i2) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.ar(i, i2);
        }
    }

    static void nS() {
        if (w.ZR) {
            bu.clear();
        }
    }

    static void eL(int i) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.eL(i);
        }
    }

    static void nT() {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.nT();
            nS();
        }
    }

    static void nU() {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.nU();
            bu.clear();
        }
    }

    static void nV() {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.nV();
        }
    }

    static boolean nW() {
        if (w.ZR) {
            return com.gameloft.android2d.socialnetwork.l.nW();
        }
        return false;
    }

    static boolean nX() {
        if (w.ZR) {
            return com.gameloft.android2d.socialnetwork.l.nX();
        }
        return false;
    }

    static boolean nY() {
        if (w.ZR) {
            return com.gameloft.android2d.socialnetwork.l.nY();
        }
        return false;
    }

    public static ai nZ() {
        if (!w.ZR) {
            return new ai();
        }
        com.gameloft.android2d.socialnetwork.i iVarUR = com.gameloft.android2d.socialnetwork.l.UR();
        ai aiVar = new ai();
        aiVar.setName(iVarUR.getName());
        aiVar.gU(iVarUR.getFirstName());
        aiVar.gV(iVarUR.getLastName());
        aiVar.gW(iVarUR.getMiddleName());
        aiVar.fG(iVarUR.getId());
        aiVar.bL(iVarUR.UO());
        aiVar.gX(iVarUR.getCountry());
        aiVar.mT(iVarUR.getScore());
        aiVar.a(iVarUR.UP());
        aiVar.eu(-2);
        return aiVar;
    }

    static String getPassword() {
        return w.ZR ? com.gameloft.android2d.socialnetwork.l.getPassword() : "";
    }

    public static ay cD(String str) {
        if (!w.ZR) {
            return null;
        }
        if (!bu.cE(str)) {
            javax.microedition.lcdui.ao aoVarHa = com.gameloft.android2d.socialnetwork.l.ha(str);
            if (aoVarHa == null) {
                return null;
            }
            ay ayVar = new ay(aoVarHa);
            if (ayVar != null) {
                GLLib.be("Facebook : [Non-Friends] put to buffer " + str);
                bu.a(str, ayVar);
                return ayVar;
            }
            return ayVar;
        }
        return bu.oc();
    }

    static void a(ai aiVar, ai[] aiVarArr, String str, String str2) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.a(aiVar, aiVarArr, str, str2);
        }
    }

    static void a(ai[] aiVarArr, ai[] aiVarArr2, String str, String str2) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.a(aiVarArr, aiVarArr2, str, str2);
        }
    }

    static ai eM(int i) {
        if (oa() == 0) {
            return null;
        }
        com.gameloft.android2d.socialnetwork.i iVarMV = com.gameloft.android2d.socialnetwork.l.mV(i);
        ai aiVar = new ai();
        aiVar.setName(iVarMV.getName());
        aiVar.gU(iVarMV.getFirstName());
        aiVar.gV(iVarMV.getLastName());
        aiVar.gW(iVarMV.getMiddleName());
        aiVar.fG(iVarMV.getId());
        aiVar.bL(iVarMV.UO());
        aiVar.gX(iVarMV.getCountry());
        aiVar.mT(iVarMV.getScore());
        aiVar.a(iVarMV.UP());
        aiVar.eu(i);
        return aiVar;
    }

    static int oa() {
        return com.gameloft.android2d.socialnetwork.l.oa();
    }

    static ai[] ob() {
        if (w.ZR) {
            try {
                ai[] aiVarArr = new ai[oa()];
                for (int i = 0; i < oa(); i++) {
                    aiVarArr[i] = eM(i);
                }
                return aiVarArr;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.b(str, str2, str3, str4, str5, str6, str7, str8, str9);
        }
    }

    public static void g(String str, String str2, String str3, String str4) {
        if (w.ZR) {
            com.gameloft.android2d.socialnetwork.l.g(str, str2, str3, str4);
        }
    }
}
