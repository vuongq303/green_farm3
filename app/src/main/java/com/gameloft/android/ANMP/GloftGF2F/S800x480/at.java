package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import java.io.IOException;

/* JADX INFO: compiled from: Huffman.java */
/* JADX INFO: loaded from: classes.dex */
class at {
    private static final int[] adG = {3, 4, 5, 6, 7, 8, 9, 10, 523, 525, 527, 529, 1043, 1047, 1051, 1055, 1571, 1579, 1587, 1595, 2115, 2131, 2147, 2163, 2691, 2723, 2755, 2787, 258};
    private static final int[] adH = {1, 2, 3, 4, NativeProtocol.MESSAGE_GET_INSTALL_DATA_REPLY, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REPLY, 131081, 131085, 196625, 196633, 262177, 262193, 327745, 327777, 393345, 393409, 459009, 459137, 524801, 525057, 590849, 591361, 657409, 658433, 724993, 727041, 794625, 798721, 868353, 876545};
    static final int[] adI;
    static final int[] adJ;
    static final byte[] adK;
    private static final char[] adL;
    private static final byte[] adM;

    public static int eA(int i) {
        if (i >= 32768) {
            return i - 32768;
        }
        return -1;
    }

    static int c(int[] iArr, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = 0;
        int i8 = i;
        while (true) {
            int i9 = i3 - 1;
            if (i3 > 0) {
                int i10 = ((1 << i9) & i2) == 0 ? 16 : 0;
                int i11 = (iArr[i7] >>> i10) & 65535;
                if (i11 == 0) {
                    if (i8 >= 32768) {
                        throw new RuntimeException("Too many nodes: " + i8);
                    }
                    if (i9 > 0) {
                        iArr[i7] = (i8 << i10) | iArr[i7];
                        i5 = i8;
                        i6 = i8 + 1;
                    } else {
                        iArr[i7] = ((i4 + 32768) << i10) | iArr[i7];
                        i5 = i7;
                        i6 = i8;
                    }
                } else {
                    if (i11 >= 32768) {
                        throw new RuntimeException("Invalid tree");
                    }
                    i5 = i11;
                    i6 = i8;
                }
                i7 = i5;
                i3 = i9;
                i8 = i6;
            } else {
                return i8;
            }
        }
    }

    public static int[] a(int i, char[] cArr) {
        if (cArr.length > 32767) {
            throw new RuntimeException("Too many leaves: " + cArr.length);
        }
        char[] cArr2 = new char[i + 1];
        for (char c : cArr) {
            if (c > 0) {
                cArr2[c] = (char) (cArr2[c] + 1);
            }
        }
        char[] cArr3 = new char[cArr2.length + 1];
        char c2 = 0;
        for (int i2 = 1; i2 <= cArr2.length; i2++) {
            c2 = (char) ((c2 + cArr2[i2 - 1]) << 1);
            cArr3[i2] = c2;
        }
        int[] iArr = new int[Math.min((1 << (i + 1)) - 1, (cArr.length * i) + 1)];
        int iC = 1;
        for (int i3 = 0; i3 < cArr.length; i3++) {
            char c3 = cArr[i3];
            if (c3 > 0) {
                char c4 = cArr3[c3];
                if (c4 > (1 << c3) - 1) {
                    throw new RuntimeException("Invalid symbol");
                }
                cArr3[c3] = (char) (cArr3[c3] + 1);
                iC = c(iArr, iC, c4, c3, i3);
            }
        }
        if (((double) iC) / ((double) iArr.length) >= 0.8d) {
            return iArr;
        }
        int[] iArr2 = new int[iC];
        System.arraycopy(iArr, 0, iArr2, 0, iC);
        return iArr2;
    }

    static int a(cg cgVar, int[] iArr) throws IOException {
        int i = 0;
        do {
            i = (iArr[i] >>> (cgVar.eV(1) == 0 ? 16 : 0)) & 65535;
            int iEA = eA(i);
            if (iEA >= 0) {
                return iEA;
            }
        } while (i > 0);
        throw new IOException("code not found");
    }

    static {
        char[] cArr = new char[288];
        int i = 0;
        while (i < 144) {
            cArr[i] = '\b';
            i++;
        }
        while (i < 256) {
            cArr[i] = '\t';
            i++;
        }
        while (i < 280) {
            cArr[i] = 7;
            i++;
        }
        while (i < cArr.length) {
            cArr[i] = '\b';
            i++;
        }
        adI = a(9, cArr);
        char[] cArr2 = new char[30];
        for (int i2 = 0; i2 < cArr2.length; i2++) {
            cArr2[i2] = 5;
        }
        adJ = a(5, cArr2);
        adK = new byte[]{16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
        adL = new char[287];
        int i3 = 0;
        while (i3 < 144) {
            adL[i3] = ah(i3 + 48, 8);
            i3++;
        }
        while (i3 < 256) {
            adL[i3] = ah((i3 - 144) + 400, 9);
            i3++;
        }
        while (i3 < 280) {
            adL[i3] = ah((i3 - 256) + 0, 7);
            i3++;
        }
        while (i3 < 287) {
            adL[i3] = ah((i3 - 280) + 192, 8);
            i3++;
        }
        adM = new byte[30];
        for (int i4 = 0; i4 < adM.length; i4++) {
            adM[i4] = (byte) ag(i4, 5);
        }
    }

    static int a(int i, cg cgVar) {
        int i2 = adG[i - 257];
        int i3 = i2 & 511;
        int i4 = i2 >>> 9;
        if (i4 > 0) {
            return i3 + cgVar.eV(i4);
        }
        return i3;
    }

    static int b(int i, cg cgVar) {
        int i2 = adH[i];
        int i3 = 65535 & i2;
        int i4 = i2 >>> 16;
        if (i4 > 0) {
            return i3 + cgVar.eV(i4);
        }
        return i3;
    }

    static char[] a(cg cgVar, int[] iArr, int i) throws IOException {
        int iEV;
        char c;
        char[] cArr = new char[i];
        int i2 = 0;
        while (i2 < cArr.length) {
            int iA = a(cgVar, iArr);
            switch (iA) {
                case 16:
                    c = cArr[i2 - 1];
                    iEV = cgVar.eV(2) + 3;
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    iEV = cgVar.eV(3) + 3;
                    c = 0;
                    break;
                case 18:
                    iEV = cgVar.eV(3) + 11;
                    c = 0;
                    break;
                default:
                    cArr[i2] = (char) iA;
                    i2++;
                    continue;
            }
            while (iEV > 0 && i2 < cArr.length) {
                cArr[i2] = c;
                iEV--;
                i2++;
            }
        }
        return cArr;
    }

    private static int ag(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i4 << 1;
            if (((1 << i3) & i) != 0) {
                i5 |= 1;
            }
            i3++;
            i4 = i5;
        }
        return i4;
    }

    private static char ah(int i, int i2) {
        return (char) (ag(i, i2) | (i2 << 9));
    }

    static void c(int i, cg cgVar) throws IOException {
        char c = adL[i];
        cgVar.au(c & 511, c >>> '\t');
    }

    private static int a(int i, int[] iArr, int i2) {
        int i3 = 0;
        int length = iArr.length;
        while (i3 < length) {
            int i4 = ((i3 + length) + 1) / 2;
            int i5 = iArr[i4] & i2;
            if (i != i5) {
                if (i < i5) {
                    length = i4 - 1;
                } else {
                    i3 = i4;
                }
            } else {
                return i4;
            }
        }
        return i3;
    }

    static void d(int i, cg cgVar) throws IOException {
        int iA = a(i, adG, 511);
        char c = adL[iA + 257];
        cgVar.au(c & 511, c >>> '\t');
        int i2 = adG[iA];
        int i3 = i2 >>> 9;
        if (i3 > 0) {
            cgVar.au(i - (i2 & 511), i3);
        }
    }

    static void e(int i, cg cgVar) throws IOException {
        int iA = a(i, adH, 65535);
        cgVar.au(adM[iA], 5);
        int i2 = adH[iA];
        int i3 = i2 >>> 16;
        if (i3 > 0) {
            cgVar.au(i - (i2 & 65535), i3);
        }
    }
}
