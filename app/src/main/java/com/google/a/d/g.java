package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: Code39Writer.java */
/* JADX INFO: loaded from: classes.dex */
public final class g extends p {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.CODE_39) {
            throw new IllegalArgumentException("Can only encode CODE_39, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        int length = str.length();
        if (length > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        }
        int[] iArr = new int[9];
        int i = length + 25;
        for (int i2 = 0; i2 < length; i2++) {
            int iIndexOf = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(str.charAt(i2));
            if (iIndexOf < 0) {
                throw new IllegalArgumentException("Bad contents: " + str);
            }
            g(f.cbP[iIndexOf], iArr);
            for (int i3 = 0; i3 < 9; i3++) {
                i += iArr[i3];
            }
        }
        boolean[] zArr = new boolean[i];
        g(f.ccb, iArr);
        int iA = a(zArr, 0, iArr, true);
        int[] iArr2 = {1};
        int iA2 = a(zArr, iA, iArr2, false) + iA;
        for (int i4 = 0; i4 < length; i4++) {
            g(f.cbP["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(str.charAt(i4))], iArr);
            int iA3 = iA2 + a(zArr, iA2, iArr, true);
            iA2 = iA3 + a(zArr, iA3, iArr2, false);
        }
        g(f.ccb, iArr);
        a(zArr, iA2, iArr, true);
        return zArr;
    }

    private static void g(int i, int[] iArr) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < 9) {
                iArr[i3] = ((1 << (8 - i3)) & i) == 0 ? 1 : 2;
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }
}
