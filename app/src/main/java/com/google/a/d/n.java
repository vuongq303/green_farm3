package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: ITFWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class n extends p {
    private static final int[] cce = {1, 1, 1, 1};
    private static final int[] cch = {3, 1, 1};

    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.ITF) {
            throw new IllegalArgumentException("Can only encode ITF, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The length of the input should be even");
        }
        if (length > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        }
        boolean[] zArr = new boolean[(length * 9) + 9];
        int iA = a(zArr, 0, cce, true);
        for (int i = 0; i < length; i += 2) {
            int iDigit = Character.digit(str.charAt(i), 10);
            int iDigit2 = Character.digit(str.charAt(i + 1), 10);
            int[] iArr = new int[18];
            for (int i2 = 0; i2 < 5; i2++) {
                iArr[i2 * 2] = m.ccg[iDigit][i2];
                iArr[(i2 * 2) + 1] = m.ccg[iDigit2][i2];
            }
            iA += a(zArr, iA, iArr, true);
        }
        a(zArr, iA, cch, true);
        return zArr;
    }
}
