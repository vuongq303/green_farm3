package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: EAN8Writer.java */
/* JADX INFO: loaded from: classes.dex */
public final class l extends s {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.EAN_8) {
            throw new IllegalArgumentException("Can only encode EAN_8, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        if (str.length() != 8) {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + str.length());
        }
        boolean[] zArr = new boolean[67];
        int iA = a(zArr, 0, r.ccj, true) + 0;
        for (int i = 0; i <= 3; i++) {
            iA += a(zArr, iA, r.ccl[Integer.parseInt(str.substring(i, i + 1))], false);
        }
        int iA2 = iA + a(zArr, iA, r.cck, false);
        for (int i2 = 4; i2 <= 7; i2++) {
            iA2 += a(zArr, iA2, r.ccl[Integer.parseInt(str.substring(i2, i2 + 1))], true);
        }
        a(zArr, iA2, r.ccj, true);
        return zArr;
    }
}
