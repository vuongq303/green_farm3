package com.google.a.e.a;

import com.facebook.appevents.AppEventsConstants;
import com.google.a.i;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

/* JADX INFO: compiled from: PDF417HighLevelEncoder.java */
/* JADX INFO: loaded from: classes.dex */
final class g {
    private static final byte[] ccH = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
    private static final byte[] ccI = {59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
    private static final byte[] ccJ = new byte[128];
    private static final byte[] ccK = new byte[128];
    private static final Charset ccL = Charset.forName("ISO-8859-1");

    static {
        Arrays.fill(ccJ, (byte) -1);
        for (int i = 0; i < ccH.length; i++) {
            byte b = ccH[i];
            if (b > 0) {
                ccJ[b] = (byte) i;
            }
        }
        Arrays.fill(ccK, (byte) -1);
        for (int i2 = 0; i2 < ccI.length; i2++) {
            byte b2 = ccI[i2];
            if (b2 > 0) {
                ccK[b2] = (byte) i2;
            }
        }
    }

    static String a(String str, c cVar, Charset charset) throws i {
        com.google.a.b.c cVarIA;
        StringBuilder sb = new StringBuilder(str.length());
        if (charset == null) {
            charset = ccL;
        } else if (!ccL.equals(charset) && (cVarIA = com.google.a.b.c.iA(charset.name())) != null) {
            a(cVarIA.getValue(), sb);
        }
        int length = str.length();
        if (cVar == c.TEXT) {
            a(str, 0, length, sb, 0);
        } else if (cVar == c.BYTE) {
            byte[] bytes = str.getBytes(charset);
            a(bytes, 0, bytes.length, 1, sb);
        } else if (cVar == c.NUMERIC) {
            sb.append((char) 902);
            a(str, 0, length, sb);
        } else {
            int i = 0;
            int iA = 0;
            int i2 = 0;
            while (i2 < length) {
                int iC = c(str, i2);
                if (iC >= 13) {
                    sb.append((char) 902);
                    i = 2;
                    a(str, i2, iC, sb);
                    iA = 0;
                    i2 = iC + i2;
                } else {
                    int iF = f(str, i2);
                    if (iF >= 5 || iC == length) {
                        if (i != 0) {
                            sb.append((char) 900);
                            i = 0;
                            iA = 0;
                        }
                        iA = a(str, i2, iF, sb, iA);
                        i2 += iF;
                    } else {
                        int iA2 = a(str, i2, charset);
                        if (iA2 == 0) {
                            iA2 = 1;
                        }
                        byte[] bytes2 = str.substring(i2, i2 + iA2).getBytes(charset);
                        if (bytes2.length == 1 && i == 0) {
                            a(bytes2, 0, 1, 0, sb);
                        } else {
                            a(bytes2, 0, bytes2.length, i, sb);
                            i = 1;
                            iA = 0;
                        }
                        i2 = iA2 + i2;
                    }
                }
            }
        }
        return sb.toString();
    }

    private static int a(CharSequence charSequence, int i, int i2, StringBuilder sb, int i3) {
        char cCharAt;
        StringBuilder sb2 = new StringBuilder(i2);
        int i4 = 0;
        while (true) {
            char cCharAt2 = charSequence.charAt(i + i4);
            switch (i3) {
                case 0:
                    if (u(cCharAt2)) {
                        if (cCharAt2 == ' ') {
                            sb2.append((char) 26);
                        } else {
                            sb2.append((char) (cCharAt2 - 'A'));
                        }
                    } else {
                        if (v(cCharAt2)) {
                            sb2.append((char) 27);
                            i3 = 1;
                        } else if (w(cCharAt2)) {
                            i3 = 2;
                            sb2.append((char) 28);
                        } else {
                            sb2.append((char) 29);
                            sb2.append((char) ccK[cCharAt2]);
                        }
                        break;
                    }
                    break;
                case 1:
                    if (v(cCharAt2)) {
                        if (cCharAt2 == ' ') {
                            sb2.append((char) 26);
                        } else {
                            sb2.append((char) (cCharAt2 - 'a'));
                        }
                    } else {
                        if (u(cCharAt2)) {
                            sb2.append((char) 27);
                            sb2.append((char) (cCharAt2 - 'A'));
                        } else if (w(cCharAt2)) {
                            i3 = 2;
                            sb2.append((char) 28);
                        } else {
                            sb2.append((char) 29);
                            sb2.append((char) ccK[cCharAt2]);
                        }
                        break;
                    }
                    break;
                case 2:
                    if (w(cCharAt2)) {
                        sb2.append((char) ccJ[cCharAt2]);
                    } else if (u(cCharAt2)) {
                        sb2.append((char) 28);
                        i3 = 0;
                    } else if (v(cCharAt2)) {
                        sb2.append((char) 27);
                        i3 = 1;
                    } else if (i + i4 + 1 < i2 && x(charSequence.charAt(i + i4 + 1))) {
                        i3 = 3;
                        sb2.append((char) 25);
                    } else {
                        sb2.append((char) 29);
                        sb2.append((char) ccK[cCharAt2]);
                    }
                    break;
                default:
                    if (x(cCharAt2)) {
                        sb2.append((char) ccK[cCharAt2]);
                    } else {
                        sb2.append((char) 29);
                        i3 = 0;
                    }
                    break;
            }
            i4++;
            if (i4 >= i2) {
                int length = sb2.length();
                int i5 = 0;
                char c = 0;
                while (i5 < length) {
                    if (i5 % 2 != 0) {
                        cCharAt = (char) ((c * 30) + sb2.charAt(i5));
                        sb.append(cCharAt);
                    } else {
                        cCharAt = sb2.charAt(i5);
                    }
                    i5++;
                    c = cCharAt;
                }
                if (length % 2 != 0) {
                    sb.append((char) ((c * 30) + 29));
                }
                return i3;
            }
        }
    }

    private static void a(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append((char) 913);
        } else if (i2 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + ((long) (bArr[i4 + i5] & 255));
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) (j % 900);
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    private static void a(String str, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int iMin = Math.min(44, i2 - i3);
            BigInteger bigInteger = new BigInteger(AppEventsConstants.EVENT_PARAM_VALUE_YES + str.substring(i + i3, i + i3 + iMin));
            do {
                sb2.append((char) bigInteger.mod(bigIntegerValueOf).intValue());
                bigInteger = bigInteger.divide(bigIntegerValueOf);
            } while (!bigInteger.equals(bigIntegerValueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += iMin;
        }
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean u(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }

    private static boolean v(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    private static boolean w(char c) {
        return ccJ[c] != -1;
    }

    private static boolean x(char c) {
        return ccK[c] != -1;
    }

    private static boolean y(char c) {
        return c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= '~');
    }

    private static int c(CharSequence charSequence, int i) {
        int i2 = 0;
        int length = charSequence.length();
        if (i < length) {
            char cCharAt = charSequence.charAt(i);
            while (isDigit(cCharAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    cCharAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    private static int f(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(cCharAt) && i2 < length) {
                i3++;
                i2++;
                if (i2 < length) {
                    cCharAt = charSequence.charAt(i2);
                }
            }
            if (i3 >= 13) {
                return (i2 - i) - i3;
            }
            if (i3 <= 0) {
                if (!y(charSequence.charAt(i2))) {
                    break;
                }
                i2++;
            }
        }
        return i2 - i;
    }

    private static int a(String str, int i, Charset charset) throws i {
        CharsetEncoder charsetEncoderNewEncoder = charset.newEncoder();
        int length = str.length();
        int i2 = i;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(cCharAt)) {
                i3++;
                int i4 = i2 + i3;
                if (i4 >= length) {
                    break;
                }
                cCharAt = str.charAt(i4);
            }
            if (i3 >= 13) {
                return i2 - i;
            }
            char cCharAt2 = str.charAt(i2);
            if (!charsetEncoderNewEncoder.canEncode(cCharAt2)) {
                throw new i("Non-encodable character detected: " + cCharAt2 + " (Unicode: " + ((int) cCharAt2) + ')');
            }
            i2++;
        }
        return i2 - i;
    }

    private static void a(int i, StringBuilder sb) throws i {
        if (i >= 0 && i < 900) {
            sb.append((char) 927);
            sb.append((char) i);
        } else if (i < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i / 900) - 1));
            sb.append((char) (i % 900));
        } else {
            if (i < 811800) {
                sb.append((char) 925);
                sb.append((char) (810900 - i));
                return;
            }
            throw new i("ECI number not in valid range from 0..811799, but was " + i);
        }
    }
}
