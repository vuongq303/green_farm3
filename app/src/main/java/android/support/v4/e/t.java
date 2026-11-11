package android.support.v4.e;

import java.io.PrintWriter;

/* JADX INFO: compiled from: TimeUtils.java */
/* JADX INFO: loaded from: classes.dex */
public final class t {
    private static final Object gV = new Object();
    private static char[] gW = new char[24];

    private static int a(int i, int i2, boolean z, int i3) {
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3;
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2;
        }
        if (z || i > 0) {
            return i2 + 1;
        }
        return 0;
    }

    private static int a(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        int i4;
        int i5;
        if (z || i > 0) {
            if ((!z || i3 < 3) && i <= 99) {
                i4 = i2;
                i5 = i;
            } else {
                int i6 = i / 100;
                cArr[i2] = (char) (i6 + 48);
                i4 = i2 + 1;
                i5 = i - (i6 * 100);
            }
            if ((z && i3 >= 2) || i5 > 9 || i2 != i4) {
                int i7 = i5 / 10;
                cArr[i4] = (char) (i7 + 48);
                i4++;
                i5 -= i7 * 10;
            }
            cArr[i4] = (char) (i5 + 48);
            int i8 = i4 + 1;
            cArr[i8] = c;
            return i8 + 1;
        }
        return i2;
    }

    private static int a(long j, int i) {
        char c;
        int i2;
        int i3;
        int i4;
        int i5;
        if (gW.length < i) {
            gW = new char[i];
        }
        char[] cArr = gW;
        if (j == 0) {
            int i6 = i - 1;
            while (0 < i6) {
                cArr[0] = ' ';
            }
            cArr[0] = '0';
            return 1;
        }
        if (j > 0) {
            c = '+';
        } else {
            j = -j;
            c = '-';
        }
        int i7 = (int) (j % 1000);
        int iFloor = (int) Math.floor(j / 1000);
        int i8 = 0;
        if (iFloor > 86400) {
            i8 = iFloor / 86400;
            iFloor -= 86400 * i8;
        }
        if (iFloor > 3600) {
            int i9 = iFloor / 3600;
            iFloor -= i9 * 3600;
            i2 = i9;
        } else {
            i2 = 0;
        }
        if (iFloor > 60) {
            int i10 = iFloor / 60;
            i3 = i10;
            i4 = iFloor - (i10 * 60);
        } else {
            i3 = 0;
            i4 = iFloor;
        }
        if (i == 0) {
            i5 = 0;
        } else {
            int iA = a(i8, 1, false, 0);
            int iA2 = iA + a(i2, 1, iA > 0, 2);
            int iA3 = iA2 + a(i3, 1, iA2 > 0, 2);
            int iA4 = iA3 + a(i4, 1, iA3 > 0, 2);
            i5 = 0;
            for (int iA5 = iA4 + a(i7, 2, true, iA4 > 0 ? 3 : 0) + 1; iA5 < i; iA5++) {
                cArr[i5] = ' ';
                i5++;
            }
        }
        cArr[i5] = c;
        int i11 = i5 + 1;
        boolean z = i != 0;
        int iA6 = a(cArr, i8, 'd', i11, false, 0);
        int iA7 = a(cArr, i2, 'h', iA6, iA6 != i11, z ? 2 : 0);
        int iA8 = a(cArr, i3, 'm', iA7, iA7 != i11, z ? 2 : 0);
        int iA9 = a(cArr, i4, 's', iA8, iA8 != i11, z ? 2 : 0);
        int iA10 = a(cArr, i7, 'm', iA9, true, (!z || iA9 == i11) ? 0 : 3);
        cArr[iA10] = 's';
        return iA10 + 1;
    }

    public static void a(long j, PrintWriter printWriter, int i) {
        synchronized (gV) {
            printWriter.print(new String(gW, 0, a(j, i)));
        }
    }

    public static void a(long j, PrintWriter printWriter) {
        a(j, printWriter, 0);
    }

    public static void a(long j, long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            a(j - j2, printWriter, 0);
        }
    }
}
