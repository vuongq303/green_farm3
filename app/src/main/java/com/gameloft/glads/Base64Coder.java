package com.gameloft.glads;

/* JADX INFO: loaded from: classes.dex */
public class Base64Coder {
    private static final byte[] map2;
    private static final String systemLineSeparator = System.getProperty("line.separator");
    private static final char[] map1 = new char[64];

    static {
        char c = 'A';
        int i = 0;
        while (c <= 'Z') {
            map1[i] = c;
            c = (char) (c + 1);
            i++;
        }
        char c2 = 'a';
        while (c2 <= 'z') {
            map1[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = '0';
        while (c3 <= '9') {
            map1[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        int i2 = i + 1;
        map1[i] = '+';
        int i3 = i2 + 1;
        map1[i2] = '/';
        map2 = new byte[128];
        for (int i4 = 0; i4 < map2.length; i4++) {
            map2[i4] = -1;
        }
        for (int i5 = 0; i5 < 64; i5++) {
            map2[map1[i5]] = (byte) i5;
        }
    }

    public static String encodeString(String str) {
        return new String(encode(str.getBytes()));
    }

    public static String encodeLines(byte[] bArr) {
        return encodeLines(bArr, 0, bArr.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte[] bArr, int i, int i2, int i3, String str) {
        int i4 = (i3 * 3) / 4;
        if (i4 <= 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder(((((i2 + i4) - 1) / i4) * str.length()) + (((i2 + 2) / 3) * 4));
        int i5 = 0;
        while (i5 < i2) {
            int iMin = Math.min(i2 - i5, i4);
            sb.append(encode(bArr, i + i5, iMin));
            sb.append(str);
            i5 += iMin;
        }
        return sb.toString();
    }

    public static char[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static char[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, i);
    }

    public static char[] encode(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = ((i2 * 4) + 2) / 3;
        char[] cArr = new char[((i2 + 2) / 3) * 4];
        int i8 = i + i2;
        int i9 = 0;
        while (i < i8) {
            int i10 = i + 1;
            int i11 = bArr[i] & 255;
            if (i10 < i8) {
                i4 = i10 + 1;
                i3 = bArr[i10] & 255;
            } else {
                i3 = 0;
                i4 = i10;
            }
            if (i4 < i8) {
                i6 = i4 + 1;
                i5 = bArr[i4] & 255;
            } else {
                i5 = 0;
                i6 = i4;
            }
            int i12 = i11 >>> 2;
            int i13 = ((i11 & 3) << 4) | (i3 >>> 4);
            int i14 = ((i3 & 15) << 2) | (i5 >>> 6);
            int i15 = i5 & 63;
            int i16 = i9 + 1;
            cArr[i9] = map1[i12];
            int i17 = i16 + 1;
            cArr[i16] = map1[i13];
            cArr[i17] = i17 < i7 ? map1[i14] : '=';
            int i18 = i17 + 1;
            cArr[i18] = i18 < i7 ? map1[i15] : '=';
            i9 = i18 + 1;
            i = i6;
        }
        return cArr;
    }

    public static String decodeString(String str) {
        return new String(decode(str));
    }

    public static byte[] decodeLines(String str) {
        char[] cArr = new char[str.length()];
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != ' ' && cCharAt != '\r' && cCharAt != '\n' && cCharAt != '\t') {
                cArr[i] = cCharAt;
                i++;
            }
        }
        return decode(cArr, 0, i);
    }

    public static byte[] decode(String str) {
        return decode(str.toCharArray());
    }

    public static byte[] decode(char[] cArr) {
        return decode(cArr, 0, cArr.length);
    }

    public static byte[] decode(char[] cArr, int i, int i2) {
        char c;
        int i3;
        char c2;
        int i4;
        int i5;
        if (i2 % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (i2 > 0 && cArr[(i + i2) - 1] == '=') {
            i2--;
        }
        int i6 = (i2 * 3) / 4;
        byte[] bArr = new byte[i6];
        int i7 = i + i2;
        int i8 = 0;
        while (i < i7) {
            int i9 = i + 1;
            char c3 = cArr[i];
            int i10 = i9 + 1;
            char c4 = cArr[i9];
            if (i10 < i7) {
                i3 = i10 + 1;
                c = cArr[i10];
            } else {
                c = 'A';
                i3 = i10;
            }
            if (i3 < i7) {
                c2 = cArr[i3];
                i3++;
            } else {
                c2 = 'A';
            }
            if (c3 > 127 || c4 > 127 || c > 127 || c2 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte b = map2[c3];
            byte b2 = map2[c4];
            byte b3 = map2[c];
            byte b4 = map2[c2];
            if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int i11 = (b << 2) | (b2 >>> 4);
            int i12 = ((b2 & 15) << 4) | (b3 >>> 2);
            int i13 = ((b3 & 3) << 6) | b4;
            int i14 = i8 + 1;
            bArr[i8] = (byte) i11;
            if (i14 < i6) {
                i4 = i14 + 1;
                bArr[i14] = (byte) i12;
            } else {
                i4 = i14;
            }
            if (i4 < i6) {
                i5 = i4 + 1;
                bArr[i4] = (byte) i13;
            } else {
                i5 = i4;
            }
            i8 = i5;
            i = i3;
        }
        return bArr;
    }

    private Base64Coder() {
    }
}
