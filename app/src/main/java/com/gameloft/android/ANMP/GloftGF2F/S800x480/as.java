package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class as {
    private static am[] Ml = null;
    private static aq[] adE = null;
    private static Vector adF;

    public static void Init() {
        if (Ml == null) {
            Ml = new am[3];
            adE = new aq[3];
            for (int i = 0; i < 3; i++) {
                Ml[i] = new am(false);
                adE[i] = new aq();
            }
        }
        adF = new Vector();
    }

    public static void a(String str, String str2, int i, ar arVar, int i2) {
        aq aqVar = new aq(str, str2, i, arVar, i2);
        synchronized (adF) {
            adF.addElement(aqVar);
        }
    }

    public static void a(String str, String str2, ar arVar, int i) {
        a(str, str2, 1, arVar, i);
    }

    public static void dV(int i) {
        mM();
        for (int i2 = 0; i2 < 3; i2++) {
            adE[i2].dV(i);
        }
    }

    private static synchronized int jf() {
        for (int i = 0; i < 3; i++) {
            if (adE[i].mK()) {
                // Original goto_11 releases the class monitor and returns this slot.
                return i;
            }
        }
        return -1;
    }

    private static synchronized void mM() {
        while (true) {
            int iJf = jf();
            if (adF.size() <= 0) {
                break;
            }
            aq aqVar = (aq) adF.elementAt(0);
            if (iJf == -1 || aqVar == null) {
                break;
            }
            adE[iJf].a(aqVar, Ml[iJf]);
            adF.removeElementAt(0);
        }
    }
}
