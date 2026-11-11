package com.gameloft.android.ANMP.GloftGF2F;

import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: AutoUpdate.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    private static int Ak = -6;
    private static String Al = "";
    public static boolean Am = false;
    public static boolean An = false;
    private static long Ao = 0;
    private static String Ap = null;
    private static String Aq = null;
    private static String Ar = null;
    private static String As = null;
    private static String At = null;
    private static String Au = null;
    public static boolean Av = false;

    public static boolean fL() {
        return Am;
    }

    public static void setStatus(int i) {
        com.gameloft.android.wrapper.au.a("PREFERENCE_UPATE_STATUS_" + MIDlet.d(MIDlet.cnz, "MIDlet-Version"), Integer.valueOf(i), "AUTOUPDATE");
    }

    public static int getStatus() {
        String strD = MIDlet.d(MIDlet.cnz, "MIDlet-Version");
        if (com.gameloft.android.wrapper.au.c("PREFERENCE_VERSION_BLOCKING_FORCE_TO_UPDATE_" + strD, 0, "AUTOUPDATE") == 3) {
            return 3;
        }
        int iC = com.gameloft.android.wrapper.au.c("PREFERENCE_UPATE_STATUS_" + strD, 0, "AUTOUPDATE");
        com.gameloft.android.wrapper.au.c("PREFERENCE_KEY_GAME_LAUNCHES", 0, "AUTOUPDATE");
        return iC;
    }
}
