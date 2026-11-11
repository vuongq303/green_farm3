package com.google.a.f.b;

/* JADX INFO: compiled from: MaskUtil.java */
/* JADX INFO: loaded from: classes.dex */
final class e {
    static int c(b bVar) {
        return a(bVar, true) + a(bVar, false);
    }

    static int d(b bVar) {
        byte[][] bArrAhy = bVar.ahy();
        int width = bVar.getWidth();
        int height = bVar.getHeight();
        int i = 0;
        for (int i2 = 0; i2 < height - 1; i2++) {
            for (int i3 = 0; i3 < width - 1; i3++) {
                byte b = bArrAhy[i2][i3];
                if (b == bArrAhy[i2][i3 + 1] && b == bArrAhy[i2 + 1][i3] && b == bArrAhy[i2 + 1][i3 + 1]) {
                    i++;
                }
            }
        }
        return i * 3;
    }

    static int e(b bVar) {
        byte[][] bArrAhy = bVar.ahy();
        int width = bVar.getWidth();
        int height = bVar.getHeight();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                byte[] bArr = bArrAhy[i2];
                if (i3 + 6 < width && bArr[i3] == 1 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == 1 && bArr[i3 + 3] == 1 && bArr[i3 + 4] == 1 && bArr[i3 + 5] == 0 && bArr[i3 + 6] == 1 && (m(bArr, i3 - 4, i3) || m(bArr, i3 + 7, i3 + 11))) {
                    i++;
                }
                if (i2 + 6 < height && bArrAhy[i2][i3] == 1 && bArrAhy[i2 + 1][i3] == 0 && bArrAhy[i2 + 2][i3] == 1 && bArrAhy[i2 + 3][i3] == 1 && bArrAhy[i2 + 4][i3] == 1 && bArrAhy[i2 + 5][i3] == 0 && bArrAhy[i2 + 6][i3] == 1 && (a(bArrAhy, i3, i2 - 4, i2) || a(bArrAhy, i3, i2 + 7, i2 + 11))) {
                    i++;
                }
            }
        }
        return i * 40;
    }

    private static boolean m(byte[] bArr, int i, int i2) {
        int iMin = Math.min(i2, bArr.length);
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (bArr[iMax] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(byte[][] bArr, int i, int i2, int i3) {
        int iMin = Math.min(i3, bArr.length);
        for (int iMax = Math.max(i2, 0); iMax < iMin; iMax++) {
            if (bArr[iMax][i] == 1) {
                return false;
            }
        }
        return true;
    }

    static int f(b bVar) {
        byte[][] bArrAhy = bVar.ahy();
        int width = bVar.getWidth();
        int height = bVar.getHeight();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            byte[] bArr = bArrAhy[i2];
            for (int i3 = 0; i3 < width; i3++) {
                if (bArr[i3] == 1) {
                    i++;
                }
            }
        }
        int height2 = bVar.getHeight() * bVar.getWidth();
        return ((Math.abs((i << 1) - height2) * 10) / height2) * 10;
    }

    static boolean af(int i, int i2, int i3) {
        int i4;
        switch (i) {
            case 0:
                i4 = (i3 + i2) & 1;
                break;
            case 1:
                i4 = i3 & 1;
                break;
            case 2:
                i4 = i2 % 3;
                break;
            case 3:
                i4 = (i3 + i2) % 3;
                break;
            case 4:
                i4 = ((i3 / 2) + (i2 / 3)) & 1;
                break;
            case 5:
                int i5 = i3 * i2;
                i4 = (i5 % 3) + (i5 & 1);
                break;
            case 6:
                int i6 = i3 * i2;
                i4 = ((i6 % 3) + (i6 & 1)) & 1;
                break;
            case 7:
                i4 = (((i3 * i2) % 3) + ((i3 + i2) & 1)) & 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid mask pattern: " + i);
        }
        return i4 == 0;
    }

    private static int a(b bVar, boolean z) {
        int height = z ? bVar.getHeight() : bVar.getWidth();
        int width = z ? bVar.getWidth() : bVar.getHeight();
        byte[][] bArrAhy = bVar.ahy();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            byte b = -1;
            int i3 = 0;
            for (int i4 = 0; i4 < width; i4++) {
                byte b2 = z ? bArrAhy[i2][i4] : bArrAhy[i4][i2];
                if (b2 == b) {
                    i3++;
                } else {
                    if (i3 >= 5) {
                        i += (i3 - 5) + 3;
                    }
                    i3 = 1;
                    b = b2;
                }
            }
            if (i3 >= 5) {
                i += (i3 - 5) + 3;
            }
        }
        return i;
    }
}
