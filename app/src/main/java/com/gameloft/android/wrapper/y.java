package com.gameloft.android.wrapper;

import android.app.Dialog;
import java.util.ArrayList;

/* JADX INFO: compiled from: Interrupt.java */
/* JADX INFO: loaded from: classes.dex */
public class y {
    public static ArrayList<Dialog> bfH = new ArrayList<>();
    public static boolean bfI;
    public static Dialog bfJ;

    public static boolean bE(boolean z) {
        if (bfI) {
            String str = "Dialog interrupt: " + bfI;
            return true;
        }
        return z;
    }

    private static void update() {
        int i = 0;
        bfI = false;
        while (true) {
            int i2 = i;
            if (i2 < bfH.size()) {
                Dialog dialog = bfH.get(i2);
                if (dialog instanceof javax.microedition.lcdui.q) {
                    bfI = ((javax.microedition.lcdui.q) dialog).clt.booleanValue() | bfI;
                }
                if (dialog instanceof javax.microedition.lcdui.ap) {
                    bfI = ((javax.microedition.lcdui.ap) dialog).clt.booleanValue() | bfI;
                }
                if (dialog instanceof javax.microedition.lcdui.a) {
                    bfI = ((javax.microedition.lcdui.a) dialog).clt.booleanValue() | bfI;
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public static void a(Dialog dialog) {
        bfH.add(dialog);
        update();
    }

    public static void a(Dialog dialog, Boolean bool) {
        Dialog dialog2 = bfH.get(bfH.indexOf(dialog));
        if (dialog2 instanceof javax.microedition.lcdui.q) {
            ((javax.microedition.lcdui.q) dialog2).clt = bool;
        }
        if (dialog2 instanceof javax.microedition.lcdui.ap) {
            ((javax.microedition.lcdui.ap) dialog2).clt = bool;
        }
        if (dialog2 instanceof javax.microedition.lcdui.a) {
            ((javax.microedition.lcdui.a) dialog2).clt = bool;
        }
        bfJ = dialog;
        update();
    }

    public static void b(Dialog dialog, Boolean bool) {
        int iIndexOf = bfH.indexOf(dialog);
        if (iIndexOf > -1) {
            Dialog dialog2 = bfH.get(iIndexOf);
            if (dialog2 instanceof javax.microedition.lcdui.q) {
                ((javax.microedition.lcdui.q) dialog2).clt = bool;
            }
            if (dialog2 instanceof javax.microedition.lcdui.ap) {
                ((javax.microedition.lcdui.ap) dialog2).clt = bool;
            }
            if (dialog2 instanceof javax.microedition.lcdui.a) {
                ((javax.microedition.lcdui.a) dialog2).clt = bool;
            }
        }
        update();
    }

    public static void b(Dialog dialog) {
        bfH.remove(dialog);
        update();
        if (bfH.size() > 0) {
            bfJ = bfH.get(bfH.size() - 1);
        }
    }

    public static boolean c(Dialog dialog) {
        if (!bfJ.equals(dialog)) {
            return false;
        }
        String str = "is Current Dialog " + dialog;
        return true;
    }
}
