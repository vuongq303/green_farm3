package com.google.a.c.a;

import java.util.Arrays;

/* JADX INFO: compiled from: HighLevelEncoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class j {
    private static char b(char c, int i) {
        int i2 = ((i * 149) % 253) + 1 + c;
        if (i2 > 254) {
            i2 -= 254;
        }
        return (char) i2;
    }

    public static String a(String str, l lVar, com.google.a.b bVar, com.google.a.b bVar2) {
        int iAgO = 0;
        g[] gVarArr = {new a(), new c(), new m(), new n(), new f(), new b()};
        h hVar = new h(str);
        hVar.a(lVar);
        hVar.a(bVar, bVar2);
        if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
            hVar.l((char) 236);
            hVar.oO(2);
            hVar.pos += 7;
        } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
            hVar.l((char) 237);
            hVar.oO(2);
            hVar.pos += 7;
        }
        while (hVar.agQ()) {
            gVarArr[iAgO].a(hVar);
            if (hVar.agO() >= 0) {
                iAgO = hVar.agO();
                hVar.agP();
            }
        }
        int iAgN = hVar.agN();
        hVar.agU();
        int iAhc = hVar.agT().ahc();
        if (iAgN < iAhc && iAgO != 0 && iAgO != 5) {
            hVar.l((char) 254);
        }
        StringBuilder sbAgM = hVar.agM();
        if (sbAgM.length() < iAhc) {
            sbAgM.append((char) 129);
        }
        while (sbAgM.length() < iAhc) {
            sbAgM.append(b((char) 129, sbAgM.length() + 1));
        }
        return hVar.agM().toString();
    }

    static int a(CharSequence charSequence, int i, int i2) {
        float[] fArr;
        char cCharAt;
        if (i >= charSequence.length()) {
            return i2;
        }
        if (i2 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            fArr = new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr[i2] = 0.0f;
        }
        int i3 = 0;
        while (i + i3 != charSequence.length()) {
            char cCharAt2 = charSequence.charAt(i + i3);
            i3++;
            if (isDigit(cCharAt2)) {
                fArr[0] = fArr[0] + 0.5f;
            } else if (m(cCharAt2)) {
                fArr[0] = (float) Math.ceil(fArr[0]);
                fArr[0] = fArr[0] + 2.0f;
            } else {
                fArr[0] = (float) Math.ceil(fArr[0]);
                fArr[0] = fArr[0] + 1.0f;
            }
            if (n(cCharAt2)) {
                fArr[1] = fArr[1] + 0.6666667f;
            } else if (m(cCharAt2)) {
                fArr[1] = fArr[1] + 2.6666667f;
            } else {
                fArr[1] = fArr[1] + 1.3333334f;
            }
            if (o(cCharAt2)) {
                fArr[2] = fArr[2] + 0.6666667f;
            } else if (m(cCharAt2)) {
                fArr[2] = fArr[2] + 2.6666667f;
            } else {
                fArr[2] = fArr[2] + 1.3333334f;
            }
            if (p(cCharAt2)) {
                fArr[3] = fArr[3] + 0.6666667f;
            } else if (m(cCharAt2)) {
                fArr[3] = fArr[3] + 4.3333335f;
            } else {
                fArr[3] = fArr[3] + 3.3333333f;
            }
            if (r(cCharAt2)) {
                fArr[4] = fArr[4] + 0.75f;
            } else if (m(cCharAt2)) {
                fArr[4] = fArr[4] + 4.25f;
            } else {
                fArr[4] = fArr[4] + 3.25f;
            }
            if (s(cCharAt2)) {
                fArr[5] = fArr[5] + 4.0f;
            } else {
                fArr[5] = fArr[5] + 1.0f;
            }
            if (i3 >= 4) {
                int[] iArr = new int[6];
                byte[] bArr = new byte[6];
                a(fArr, iArr, Integer.MAX_VALUE, bArr);
                int iU = u(bArr);
                if (iArr[0] < iArr[5] && iArr[0] < iArr[1] && iArr[0] < iArr[2] && iArr[0] < iArr[3] && iArr[0] < iArr[4]) {
                    return 0;
                }
                if (iArr[5] < iArr[0] || bArr[1] + bArr[2] + bArr[3] + bArr[4] == 0) {
                    return 5;
                }
                if (iU == 1 && bArr[4] > 0) {
                    return 4;
                }
                if (iU == 1 && bArr[2] > 0) {
                    return 2;
                }
                if (iU == 1 && bArr[3] > 0) {
                    return 3;
                }
                if (iArr[1] + 1 < iArr[0] && iArr[1] + 1 < iArr[5] && iArr[1] + 1 < iArr[4] && iArr[1] + 1 < iArr[2]) {
                    if (iArr[1] < iArr[3]) {
                        return 1;
                    }
                    if (iArr[1] == iArr[3]) {
                        int i4 = i + i3;
                        do {
                            i4++;
                            if (i4 >= charSequence.length()) {
                                break;
                            }
                            cCharAt = charSequence.charAt(i4);
                            if (q(cCharAt)) {
                                return 3;
                            }
                        } while (p(cCharAt));
                        return 1;
                    }
                }
            }
        }
        byte[] bArr2 = new byte[6];
        int[] iArr2 = new int[6];
        int iA = a(fArr, iArr2, Integer.MAX_VALUE, bArr2);
        int iU2 = u(bArr2);
        if (iArr2[0] == iA) {
            return 0;
        }
        if (iU2 == 1 && bArr2[5] > 0) {
            return 5;
        }
        if (iU2 == 1 && bArr2[4] > 0) {
            return 4;
        }
        if (iU2 != 1 || bArr2[2] <= 0) {
            return (iU2 != 1 || bArr2[3] <= 0) ? 1 : 3;
        }
        return 2;
    }

    private static int a(float[] fArr, int[] iArr, int i, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        int i2 = i;
        for (int i3 = 0; i3 < 6; i3++) {
            iArr[i3] = (int) Math.ceil(fArr[i3]);
            int i4 = iArr[i3];
            if (i2 > i4) {
                Arrays.fill(bArr, (byte) 0);
                i2 = i4;
            }
            if (i2 == i4) {
                bArr[i3] = (byte) (bArr[i3] + 1);
            }
        }
        return i2;
    }

    private static int u(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 < 6) {
                i = bArr[i2] + i3;
                i2++;
            } else {
                return i3;
            }
        }
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean m(char c) {
        return c >= 128 && c <= 255;
    }

    private static boolean n(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    private static boolean o(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z');
    }

    private static boolean p(char c) {
        return q(c) || c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    private static boolean q(char c) {
        return c == '\r' || c == '*' || c == '>';
    }

    private static boolean r(char c) {
        return c >= ' ' && c <= '^';
    }

    private static boolean s(char c) {
        return false;
    }

    public static int c(CharSequence charSequence, int i) {
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

    static void t(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }
}
