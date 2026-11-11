package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: Code93Writer.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends p {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.CODE_93) {
            throw new IllegalArgumentException("Can only encode CODE_93, but got " + aVar);
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
        boolean[] zArr = new boolean[((str.length() + 2 + 2) * 9) + 1];
        g(h.cbP[47], iArr);
        int iA = a(zArr, 0, iArr, true);
        for (int i = 0; i < length; i++) {
            g(h.cbP["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(i))], iArr);
            iA += a(zArr, iA, iArr, true);
        }
        int iT = T(str, 20);
        g(h.cbP[iT], iArr);
        int iA2 = iA + a(zArr, iA, iArr, true);
        g(h.cbP[T(str + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(iT), 15)], iArr);
        int iA3 = a(zArr, iA2, iArr, true) + iA2;
        g(h.cbP[47], iArr);
        zArr[iA3 + a(zArr, iA3, iArr, true)] = true;
        return zArr;
    }

    private static void g(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            iArr[i2] = ((1 << (8 - i2)) & i) == 0 ? 0 : 1;
        }
    }

    protected static int a(boolean[] zArr, int i, int[] iArr, boolean z) {
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            zArr[i] = iArr[i2] != 0;
            i2++;
            i = i3;
        }
        return 9;
    }

    private static int T(String str, int i) {
        int iIndexOf = 0;
        int length = str.length() - 1;
        int i2 = 1;
        while (length >= 0) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i2;
            int i3 = i2 + 1;
            if (i3 > i) {
                i3 = 1;
            }
            length--;
            i2 = i3;
        }
        return iIndexOf % 47;
    }
}
