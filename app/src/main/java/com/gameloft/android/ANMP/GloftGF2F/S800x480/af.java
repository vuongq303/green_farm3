package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class af {
    public static final int acf = 1 << w.VP;
    static final int acg = acf >> 1;
    public static final int ach = 1 << w.VQ;
    public static final int aci = (90 * ach) / 360;
    public static final int acj = (ach * 180) / 360;
    public static final int ack = (270 * ach) / 360;
    public static final int acl = (ach * 360) / 360;
    public static float Rw = 3.1415927f;
    public static final int acm = 1686629713 >> (29 - w.VP);
    public static final int acn = 1459366444 >> (29 - w.VP);
    public static final int Rz = af(180 << w.VP, acm);
    public static final int RA = af(1 << w.VQ, 360);
    private static int[][] aco = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 2, 2);

    public static final int af(int i, int i2) {
        GLLib.c(i2 != 0, "FixedPoint_Divide(): Division by Zero");
        return ((int) ((((((long) i) << w.VP) << 1) / ((long) i2)) + 1)) >> 1;
    }
}
