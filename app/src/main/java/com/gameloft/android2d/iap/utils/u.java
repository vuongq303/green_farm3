package com.gameloft.android2d.iap.utils;

import android.content.SharedPreferences;
import com.facebook.appevents.AppEventsConstants;

/* JADX INFO: compiled from: RMS.java */
/* JADX INFO: loaded from: classes.dex */
public class u implements com.gameloft.android2d.iap.a {
    public static void af(String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = v.getContext().getSharedPreferences(v.mJ(com.b.d.cgF), 0).edit();
            if (str2 != null && !str2.equals("")) {
                str2 = aa.am(str2, Ti());
            }
            editorEdit.putString(str, str2);
            editorEdit.commit();
        } catch (Exception e) {
        }
    }

    public static String fJ(String str) {
        try {
            String string = v.getContext().getSharedPreferences(v.mJ(com.b.d.cgF), 0).getString(str, "");
            if (string == null || string.equals("")) {
                return "";
            }
            return aa.an(string, Ti());
        } catch (Exception e) {
            return "";
        }
    }

    public static String Ti() {
        String strFA;
        String strEL = com.gameloft.android2d.iap.b.eL(v.mJ(com.b.d.ciz));
        try {
            if (com.gameloft.android2d.iap.b.QL() != 2) {
                strFA = d.fA("IMEI");
            } else {
                strFA = d.fA("HDIDFV");
            }
            if (strFA != null && !strFA.equals("")) {
                strEL = strFA;
            }
            if (strEL.length() > 16) {
                strEL = strEL.substring(0, 16);
                return strEL;
            }
            return strEL;
        } catch (Exception e) {
            return strEL;
        }
    }

    public static int Tj() {
        String strFJ = fJ(bjj[2]);
        if (strFJ == null || strFJ.length() == 0) {
            return -1;
        }
        try {
            return Integer.parseInt(strFJ);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String Tk() {
        String strFJ = fJ(bjj[6]);
        return strFJ != null ? strFJ : "";
    }

    public static String Tl() {
        int iIndexOf;
        String strFJ = fJ(bjj[7]);
        if (strFJ == null || (iIndexOf = strFJ.indexOf(95)) <= 0) {
            return null;
        }
        return strFJ.substring(iIndexOf + 1);
    }

    public static String Tm() {
        int iIndexOf;
        String strFJ = fJ(bjj[7]);
        if (strFJ == null || (iIndexOf = strFJ.indexOf(95)) <= 0) {
            return null;
        }
        return strFJ.substring(0, iIndexOf);
    }

    public static void ag(String str, String str2) {
        af(bjj[7], str + "_" + str2);
    }

    public static void fK(String str) {
        af(bjj[8], str);
    }

    public static void fL(String str) {
        af(bjj[9], str);
    }

    public static String Tn() {
        String strFJ = fJ(bjj[8]);
        return strFJ != null ? strFJ : "";
    }

    public static String To() {
        String strFJ = fJ(bjj[9]);
        return strFJ != null ? strFJ : "";
    }

    public static void fM(String str) {
        af(bjj[13], str);
    }

    public static String Tp() {
        String strFJ = fJ(bjj[13]);
        return strFJ != null ? strFJ : "";
    }

    public static void fN(String str) {
        af(bjj[56], str);
    }

    public static void fO(String str) {
        af(bjj[65], str);
    }

    public static void fP(String str) {
        af(bjj[66], str);
    }

    public static void fQ(String str) {
        af(bjj[18], aa.gQ(str));
    }

    public static long Tq() {
        String strFJ = fJ(bjj[18]);
        if (strFJ == null || strFJ.length() == 0) {
            return 0L;
        }
        try {
            return Long.parseLong(aa.gR(strFJ));
        } catch (Exception e) {
            return 0L;
        }
    }

    public static void o(String[] strArr) {
        String str = "";
        if (strArr != null) {
            for (String str2 : strArr) {
                str = str + str2 + ";";
            }
        }
        af(bjj[19], aa.gQ(str));
    }

    public static String[] Tr() {
        String strFJ = fJ(bjj[19]);
        if (strFJ == null || strFJ.equals("")) {
            return null;
        }
        return aa.gR(strFJ).split(";");
    }

    public static boolean Ts() {
        String strFJ = fJ(bjj[0]);
        return strFJ != null && strFJ.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static boolean Tt() {
        String strFJ = fJ(bjj[4]);
        return strFJ != null && strFJ.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES);
    }

    public static String Tu() {
        String strFJ = fJ(bjj[1]);
        return strFJ != null ? strFJ : "";
    }

    public static boolean Tv() {
        boolean z;
        try {
            z = v.getContext().getSharedPreferences(v.mJ(com.b.d.cgd), 0).getBoolean(v.mJ(com.b.d.cge), true);
        } catch (Exception e) {
            z = false;
        }
        return !z;
    }

    public static void fR(String str) {
        af(bjj[10], str);
    }

    public static String Tw() {
        String strFJ = fJ(bjj[10]);
        if (strFJ != "") {
            return strFJ;
        }
        return null;
    }

    public static void fS(String str) {
        af(bjj[11], str);
    }

    public static void fT(String str) {
        af(bjj[14], str);
    }

    public static String Tx() {
        String strFJ = fJ(bjj[14]);
        return strFJ != "" ? strFJ : "";
    }

    public static void fU(String str) {
        af(bjj[15], str);
    }

    public static String getUserID() {
        String strFJ = fJ(bjj[15]);
        if (strFJ != "") {
            return strFJ;
        }
        return null;
    }

    public static void D(String str, int i) {
        af(bjj[i], str);
    }

    public static String mG(int i) {
        return fJ(bjj[i]);
    }

    public static void b(com.gameloft.android2d.iap.a.a aVar) {
        D(aVar.Rw(), 20);
        D(aVar.Rz(), 21);
        D(aVar.getURL(), 22);
        D(aVar.RX(), 23);
        D(aVar.RF(), 24);
        D(aVar.RG(), 25);
        D(aVar.RD(), 26);
        D(aVar.Sc(), 52);
        D(aVar.RS(), 64);
        String price = aVar.getPrice();
        if (price != null) {
            price = price.replace(',', '.');
        }
        D(price, 8);
    }

    public static String Ty() {
        return mG(20);
    }

    public static String Tz() {
        return mG(21);
    }

    public static String TA() {
        return mG(22);
    }

    public static String TB() {
        return mG(23);
    }

    public static String TC() {
        return mG(24);
    }

    public static String TD() {
        return mG(25);
    }

    public static void fV(String str) {
        af(bjj[34], str);
    }

    public static String TE() {
        String strFJ = fJ(bjj[34]);
        if (strFJ != "") {
            return strFJ;
        }
        return null;
    }

    public static String TF() {
        String strMG = mG(39);
        return strMG != "" ? strMG : "11111";
    }

    public static void fW(String str) {
        af(bjj[51], str);
    }

    public static void mH(int i) {
        af(bjj[40], "" + i);
    }

    public static int TG() {
        String strMG = mG(40);
        if (strMG != "") {
            return Integer.parseInt(strMG);
        }
        return 0;
    }

    public static void fX(String str) {
        af(bjj[45], str);
    }

    public static String Su() {
        String strFJ = fJ(bjj[45]);
        return strFJ != "" ? strFJ : "";
    }

    public static String TH() {
        String strFJ = fJ(bjj[50]);
        return strFJ != null ? strFJ : "";
    }

    public static void fY(String str) {
        af(bjj[57], str);
    }

    public static String TI() {
        String strFJ = fJ(bjj[57]);
        if (strFJ != "") {
            return strFJ;
        }
        return null;
    }

    public static String TJ() {
        return mG(64);
    }
}
