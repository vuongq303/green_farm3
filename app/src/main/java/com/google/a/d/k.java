package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: EAN13Writer.java */
/* JADX INFO: loaded from: classes.dex */
public final class k extends s {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.EAN_13) {
            throw new IllegalArgumentException("Can only encode EAN_13, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        if (str.length() != 13) {
            throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + str.length());
        }
        try {
            if (!r.n(str)) {
                throw new IllegalArgumentException("Contents do not pass checksum");
            }
            int i = j.ccc[Integer.parseInt(str.substring(0, 1))];
            boolean[] zArr = new boolean[95];
            int iA = a(zArr, 0, r.ccj, true) + 0;
            for (int i2 = 1; i2 <= 6; i2++) {
                int i3 = Integer.parseInt(str.substring(i2, i2 + 1));
                if (((i >> (6 - i2)) & 1) == 1) {
                    i3 += 10;
                }
                iA += a(zArr, iA, r.ccm[i3], false);
            }
            int iA2 = iA + a(zArr, iA, r.cck, false);
            for (int i4 = 7; i4 <= 12; i4++) {
                iA2 += a(zArr, iA2, r.ccl[Integer.parseInt(str.substring(i4, i4 + 1))], true);
            }
            a(zArr, iA2, r.ccj, true);
            return zArr;
        } catch (com.google.a.d e) {
            throw new IllegalArgumentException("Illegal contents");
        }
    }
}
