package com.google.a.d;

/* JADX INFO: compiled from: UPCEANReader.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class r extends o {
    static final int[] ccj = {1, 1, 1};
    static final int[] cck = {1, 1, 1, 1, 1};
    static final int[] cch = {1, 1, 1, 1, 1, 1};
    static final int[][] ccl = {new int[]{3, 2, 1, 1}, new int[]{2, 2, 2, 1}, new int[]{2, 1, 2, 2}, new int[]{1, 4, 1, 1}, new int[]{1, 1, 3, 2}, new int[]{1, 2, 3, 1}, new int[]{1, 1, 1, 4}, new int[]{1, 3, 1, 2}, new int[]{1, 2, 1, 3}, new int[]{3, 1, 1, 2}};
    static final int[][] ccm = new int[20][];

    static {
        int i = 10;
        System.arraycopy(ccl, 0, ccm, 0, 10);
        while (true) {
            int i2 = i;
            if (i2 < 20) {
                int[] iArr = ccl[i2 - 10];
                int[] iArr2 = new int[iArr.length];
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    iArr2[i3] = iArr[(iArr.length - i3) - 1];
                }
                ccm[i2] = iArr2;
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    static boolean n(CharSequence charSequence) throws com.google.a.d {
        int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            int iCharAt = charSequence.charAt(i2) - '0';
            if (iCharAt < 0 || iCharAt > 9) {
                throw com.google.a.d.agu();
            }
            i += iCharAt;
        }
        int i3 = i * 3;
        for (int i4 = length - 1; i4 >= 0; i4 -= 2) {
            int iCharAt2 = charSequence.charAt(i4) - '0';
            if (iCharAt2 < 0 || iCharAt2 > 9) {
                throw com.google.a.d.agu();
            }
            i3 += iCharAt2;
        }
        return i3 % 10 == 0;
    }
}
