package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: XXTEA_Encrypter.java */
/* JADX INFO: loaded from: classes.dex */
public class by {
    public static int[] a(int[] iArr, int[] iArr2) {
        if (iArr != null && iArr2 != null) {
            int length = iArr.length - 1;
            if (length >= 1) {
                if (iArr2.length < 4) {
                    int[] iArr3 = new int[4];
                    System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                    iArr2 = iArr3;
                }
                int i = iArr[length];
                int i2 = iArr[0];
                int i3 = (52 / (length + 1)) + 6;
                int i4 = 0;
                while (true) {
                    int i5 = i3 - 1;
                    if (i3 > 0) {
                        i4 -= 1640531527;
                        int i6 = (i4 >>> 2) & 3;
                        int i7 = 0;
                        while (i7 < length) {
                            int i8 = iArr[i7 + 1];
                            i = (((i ^ iArr2[(i7 & 3) ^ i6]) + (i8 ^ i4)) ^ (((i >>> 5) ^ (i8 << 2)) + ((i8 >>> 3) ^ (i << 4)))) + iArr[i7];
                            iArr[i7] = i;
                            i7++;
                        }
                        int i9 = iArr[0];
                        i = iArr[length] + (((iArr2[(i7 & 3) ^ i6] ^ i) + (i9 ^ i4)) ^ (((i >>> 5) ^ (i9 << 2)) + ((i9 >>> 3) ^ (i << 4))));
                        iArr[length] = i;
                        i3 = i5;
                    } else {
                        return iArr;
                    }
                }
            } else {
                return iArr;
            }
        }
        return null;
    }

    public static int[] b(int[] iArr, int[] iArr2) {
        if (iArr != null && iArr2 != null) {
            int length = iArr.length - 1;
            if (length >= 1) {
                if (iArr2.length < 4) {
                    int[] iArr3 = new int[4];
                    System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                    iArr2 = iArr3;
                }
                int i = iArr[length];
                int i2 = iArr[0];
                for (int i3 = ((52 / (length + 1)) + 6) * (-1640531527); i3 != 0; i3 -= -1640531527) {
                    int i4 = (i3 >>> 2) & 3;
                    int i5 = length;
                    while (i5 > 0) {
                        int i6 = iArr[i5 - 1];
                        i2 = iArr[i5] - (((i2 ^ i3) + (i6 ^ iArr2[(i5 & 3) ^ i4])) ^ (((i6 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i6 << 4))));
                        iArr[i5] = i2;
                        i5--;
                    }
                    int i7 = iArr[length];
                    i2 = iArr[0] - (((iArr2[(i5 & 3) ^ i4] ^ i7) + (i2 ^ i3)) ^ (((i7 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i7 << 4))));
                    iArr[0] = i2;
                }
                return iArr;
            }
            return iArr;
        }
        return null;
    }
}
