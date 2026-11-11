package com.gameloft.android.wrapper;

import android.os.Build;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Tracking.java */
/* JADX INFO: loaded from: classes.dex */
public class ar {
    static String bhA;
    static String bhB;
    static String bhC;
    static String bhD;
    static String bhE;
    static String bhF;
    static String bhG;
    static String bhH;
    static String bhI;
    public static String bht = "";
    public static String bhu = "";
    public static String bhv = "";
    public static String bhw = "2.3";
    public static int bhx = 1500;
    public static String bhy = "https://201205igp.gameloft.com/redir/hdloading.php?game=#GAME#&country=#COUNTRY#&lg=#LANG#&ver=#IGP_VERSION#&device=#DEVICE#&f=#FIRMWARE#&hdidfv=#HDIDFV#&g_ver=#VERSION#&line_number=#LINE_NUMBER#";
    private static String bhz = "https://201205igp.gameloft.com/redir/?from=#GAME#&op=#CARRIER#&game=#GAME#&ver=#VERSION#&lg=#LANG#&country=#COUNTRY#&d=#DEVICE#&f=#FIRMWARE#";

    public static void a(MIDlet mIDlet) {
        if (d.beJ != 0) {
            bhy = "https://201205igp.gameloft.com/redir/hdloading.php?game=#GAME#&country=#COUNTRY#&lg=#LANG#&ver=#IGP_VERSION#&device=#DEVICE#&f=#FIRMWARE#&hdidfv=#HDIDFV#&g_ver=#VERSION#&line_number=#LINE_NUMBER#&google_adid=#GOOGLE_ADID#&google_optout=#GOOGLE_OPTOUT#";
            bhz = "https://201205igp.gameloft.com/redir/?from=#GAME#&op=#CARRIER#&game=#GAME#&ver=#VERSION#&lg=#LANG#&country=#COUNTRY#&d=#DEVICE#&f=#FIRMWARE#&hdidfv=#HDIDFV#";
        }
        if (d.beJ != 0) {
            bhG = au.Os();
            String str = "A_S" + d.beJ;
            au.Os();
        }
        bhA = au.Pk();
        bhB = au.Pl();
        bhC = Build.MANUFACTURER + "_" + Build.MODEL;
        bhD = Build.VERSION.RELEASE;
        bhF = d.beR;
        bht = MIDlet.cnM;
        bhu = MIDlet.VERSION;
        bhv = MIDlet.cnN;
        if (mIDlet.bY("IGP-VERSION") != null) {
            bhw = mIDlet.bY("IGP-VERSION");
        }
        bhE = au.Pb();
    }

    public static void OM() {
        my(2);
    }

    public static void my(int i) {
        new Thread(new as()).start();
    }

    public static void cm(int i, int i2) {
        new Thread(new at(i2, i)).start();
    }

    public static String mz(int i) {
        if (i == 0) {
            return "en";
        }
        if (i == 1) {
            return "de";
        }
        if (i == 2) {
            return "fr";
        }
        if (i == 3) {
            return "it";
        }
        if (i == 4) {
            return "es";
        }
        if (i == 5) {
            return "br";
        }
        if (i == 7) {
            return "ja";
        }
        if (i == 8) {
            return "zh";
        }
        if (i == 9) {
            return "ko";
        }
        if (i == 10) {
            return "ru";
        }
        if (i == 11) {
            return "tr";
        }
        if (i == 12) {
            return "pl";
        }
        if (i == 15) {
            return "th";
        }
        if (i == 16) {
            return "vi";
        }
        if (i == 17) {
            return "ar";
        }
        return i == 19 ? "zh" : "en";
    }

    public static String B(String str, int i) {
        if (str.compareTo(bhz) == 0) {
            bhA = mz(i);
        }
        return eh(str);
    }

    public static String eh(String str) {
        if (str.compareTo(bhy) == 0) {
            String strCrypt = a.crypt(bhE);
            bhA = au.Pk();
            bhB = au.Pl();
            if (d.beJ != 0) {
                bhH = au.NB();
                bhI = Integer.toString(au.Qe());
                return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#HDIDFV#", bhG).replace("#IGP_VERSION#", bhw).replace("#LINE_NUMBER#", strCrypt).replace("#GOOGLE_ADID#", bhH).replace("#GOOGLE_OPTOUT#", bhI).replace(" ", "") + "&enc=1";
            }
            return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#IGP_VERSION#", bhw).replace("#LINE_NUMBER#", strCrypt).replace(" ", "") + "&enc=1";
        }
        if (str.compareTo(bhz) == 0) {
            if (d.beJ != 0) {
                return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#HDIDFV#", bhG).replace("#CARRIER#", bhF).replace(" ", "");
            }
            return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#CARRIER#", bhF).replace(" ", "");
        }
        if (d.beJ != 0) {
            return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#HDIDFV#", bhG).replace("#IGP_VERSION#", bhw).replace("#LINE_NUMBER#", bhE).replace("#CARRIER#", bhF).replace(" ", "");
        }
        return str.replace("#GAME#", bht).replace("#COUNTRY#", bhB).replace("#LANG#", bhA).replace("#VERSION#", bhv).replace("#DEVICE#", bhC).replace("#FIRMWARE#", bhD).replace("#IGP_VERSION#", bhw).replace("#LINE_NUMBER#", bhE).replace("#CARRIER#", bhF).replace(" ", "");
    }
}
