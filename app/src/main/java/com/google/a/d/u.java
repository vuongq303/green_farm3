package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: UPCEWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class u extends s {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.UPC_E) {
            throw new IllegalArgumentException("Can only encode UPC_E, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        if (str.length() != 8) {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + str.length());
        }
        int i = t.ccn[Integer.parseInt(str.substring(7, 8))];
        boolean[] zArr = new boolean[51];
        int iA = a(zArr, 0, r.ccj, true) + 0;
        for (int i2 = 1; i2 <= 6; i2++) {
            int i3 = Integer.parseInt(str.substring(i2, i2 + 1));
            if (((i >> (6 - i2)) & 1) == 1) {
                i3 += 10;
            }
            iA += a(zArr, iA, r.ccm[i3], false);
        }
        a(zArr, iA, r.cch, false);
        return zArr;
    }
}
