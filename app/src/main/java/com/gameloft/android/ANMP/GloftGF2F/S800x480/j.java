package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class j {
    private static j[] KT;
    public int KU;
    public int KV;
    public int[][] KW;

    public static j bY(int i) {
        return KT[i];
    }

    public static void bZ(int i) {
        GLLib.be("START LOADING CLASSES");
        GLLib.be("START Opening file");
        GLLib.bk("/13");
        GLLib.be("START load currentClass = " + i);
        KT[i] = new j();
        byte[] bArrDb = GLLib.db(i);
        GLLib.be("Open File " + i);
        KT[i].KV = GLLib.l(bArrDb, 0);
        KT[i].KU = GLLib.l(bArrDb, 4);
        int i2 = 8;
        if (KT[i].KV > 0 && KT[i].KU > 0) {
            byte[] bArr = new byte[KT[i].KU];
            for (int i3 = 0; i3 < KT[i].KU; i3++) {
                bArr[i3] = bArrDb[i2];
                i2++;
            }
            KT[i].KW = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, KT[i].KV, KT[i].KU);
            for (int i4 = 0; i4 < KT[i].KV; i4++) {
                for (int i5 = 0; i5 < KT[i].KU; i5++) {
                    switch (bArr[i5]) {
                        case 1:
                            KT[i].KW[i4][i5] = bArrDb[i2];
                            break;
                        case 2:
                            KT[i].KW[i4][i5] = GLLib.k(bArrDb, i2);
                            break;
                        case 4:
                            KT[i].KW[i4][i5] = GLLib.l(bArrDb, i2);
                            break;
                    }
                    i2 += bArr[i5];
                }
            }
        }
        GLLib.ke();
    }

    public static void il() {
        GLLib.be("START LOADING CLASSES");
        KT = new j[34];
        GLLib.be("START Opening file");
        GLLib.bk("/13");
        GLLib.be("START loop");
        for (int i = 0; i < 34; i++) {
            KT[i] = new j();
            byte[] bArrDb = GLLib.db(i);
            GLLib.be("Open File " + i);
            KT[i].KV = GLLib.l(bArrDb, 0);
            KT[i].KU = GLLib.l(bArrDb, 4);
            int i2 = 8;
            if (KT[i].KV > 0 && KT[i].KU > 0) {
                byte[] bArr = new byte[KT[i].KU];
                for (int i3 = 0; i3 < KT[i].KU; i3++) {
                    bArr[i3] = bArrDb[i2];
                    i2++;
                }
                KT[i].KW = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, KT[i].KV, KT[i].KU);
                for (int i4 = 0; i4 < KT[i].KV; i4++) {
                    for (int i5 = 0; i5 < KT[i].KU; i5++) {
                        switch (bArr[i5]) {
                            case 1:
                                KT[i].KW[i4][i5] = bArrDb[i2];
                                break;
                            case 2:
                                KT[i].KW[i4][i5] = GLLib.k(bArrDb, i2);
                                break;
                            case 4:
                                KT[i].KW[i4][i5] = GLLib.l(bArrDb, i2);
                                break;
                        }
                        i2 += bArr[i5];
                    }
                }
            }
        }
        GLLib.ke();
    }
}
