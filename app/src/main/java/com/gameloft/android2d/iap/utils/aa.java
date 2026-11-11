package com.gameloft.android2d.iap.utils;

/* JADX INFO: compiled from: XXTEA.java */
/* JADX INFO: loaded from: classes.dex */
public class aa implements com.gameloft.android2d.iap.a {
    static final char[] Ts = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] c(byte[] bArr, String str) {
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return b(bArr, bytes);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        return bArr.length == 0 ? bArr : a(a(a(bArr, true, (int[]) null), a(bArr2, false, (int[]) null)), false, (byte[]) null);
    }

    public static byte[] d(byte[] bArr, String str) {
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return c(bArr, bytes);
    }

    public static byte[] c(byte[] bArr, byte[] bArr2) {
        return (bArr == null || bArr.length == 0) ? bArr : a(b(a(bArr, false, (int[]) null), a(bArr2, false, (int[]) null)), true, (byte[]) null);
    }

    public static int[] a(int[] iArr, int[] iArr2) {
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
                if (i3 <= 0) {
                    break;
                }
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
            }
        }
        return iArr;
    }

    public static int[] b(int[] iArr, int[] iArr2) {
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
        }
        return iArr;
    }

    public static byte[] a(int[] iArr, boolean z, byte[] bArr) {
        int length = iArr.length << 2;
        if (z) {
            int i = iArr[iArr.length - 1];
            if (i > length) {
                return null;
            }
            length = i;
        }
        if (bArr == null) {
            bArr = new byte[length];
        }
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) ((iArr[i2 >>> 2] >>> ((i2 & 3) << 3)) & 255);
        }
        return bArr;
    }

    public static int[] a(byte[] bArr, boolean z, int[] iArr) {
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z) {
            if (iArr == null) {
                iArr = new int[length + 1];
            }
            iArr[length] = bArr.length;
        } else if (iArr == null) {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i = 0; i < length2; i++) {
            int i2 = i >>> 2;
            iArr[i2] = iArr[i2] | ((bArr[i] & 255) << ((i & 3) << 3));
        }
        return iArr;
    }

    public static String n(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(Ts[(bArr[i] >> 4) & 15]);
            stringBuffer.append(Ts[bArr[i] & 15]);
        }
        return stringBuffer.toString();
    }

    public static byte[] gP(String str) {
        try {
            byte[] bArr = new byte[str.length() >> 1];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) (Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16) & 255);
            }
            return bArr;
        } catch (Exception e) {
            return null;
        }
    }

    public static String gQ(String str) {
        return am(str, com.gameloft.android2d.iap.b.eL(v.mJ(com.b.d.ciz)));
    }

    public static String am(String str, String str2) {
        return n(c(str.getBytes(), str2));
    }

    public static String gR(String str) {
        return an(str, com.gameloft.android2d.iap.b.eL(v.mJ(com.b.d.ciz)));
    }

    public static String an(String str, String str2) {
        return new String(d(gP(str), str2));
    }
}
