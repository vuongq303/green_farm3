package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class bo {
    public static int ajP = 2016;

    public static int aq(int i, int i2) {
        int i3 = i % 10000;
        int i4 = i2 % 10000;
        return ((((((i / 10000) * i4) + (0 * i3)) % 10000) * 10000) + (i3 * i4)) % 100000000;
    }

    public static int next() {
        ajP = (aq(ajP, 3141581) + 1) % 100000000;
        return ajP;
    }
}
