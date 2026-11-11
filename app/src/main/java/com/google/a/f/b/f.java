package com.google.a.f.b;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.a.i;

/* JADX INFO: compiled from: MatrixUtil.java */
/* JADX INFO: loaded from: classes.dex */
final class f {
    private static final int[][] cdt = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    private static final int[][] cdu = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    private static final int[][] cdv = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, 138, -1}, new int[]{6, 30, 58, 86, 114, 142, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION, 126, 150}, new int[]{6, 24, 50, 76, FacebookRequestErrorClassification.EC_INVALID_SESSION, 128, 154}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, 166}, new int[]{6, 30, 58, 86, 114, 142, 170}};
    private static final int[][] cdw = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    static void g(b bVar) {
        bVar.e((byte) -1);
    }

    static void a(com.google.a.b.a aVar, com.google.a.f.a.a aVar2, com.google.a.f.a.c cVar, int i, b bVar) throws i {
        g(bVar);
        a(cVar, bVar);
        a(aVar2, i, bVar);
        b(cVar, bVar);
        a(aVar, i, bVar);
    }

    static void a(com.google.a.f.a.c cVar, b bVar) throws i {
        j(bVar);
        i(bVar);
        c(cVar, bVar);
        h(bVar);
    }

    static void a(com.google.a.f.a.a aVar, int i, b bVar) throws i {
        com.google.a.b.a aVar2 = new com.google.a.b.a();
        a(aVar, i, aVar2);
        for (int i2 = 0; i2 < aVar2.getSize(); i2++) {
            boolean z = aVar2.get((aVar2.getSize() - 1) - i2);
            bVar.set(cdw[i2][0], cdw[i2][1], z);
            if (i2 < 8) {
                bVar.set((bVar.getWidth() - i2) - 1, 8, z);
            } else {
                bVar.set(8, (bVar.getHeight() - 7) + (i2 - 8), z);
            }
        }
    }

    static void b(com.google.a.f.a.c cVar, b bVar) throws i {
        if (cVar.ahn() >= 7) {
            com.google.a.b.a aVar = new com.google.a.b.a();
            a(cVar, aVar);
            int i = 17;
            for (int i2 = 0; i2 < 6; i2++) {
                for (int i3 = 0; i3 < 3; i3++) {
                    boolean z = aVar.get(i);
                    i--;
                    bVar.set(i2, (bVar.getHeight() - 11) + i3, z);
                    bVar.set((bVar.getHeight() - 11) + i3, i2, z);
                }
            }
        }
    }

    static void a(com.google.a.b.a aVar, int i, b bVar) throws i {
        boolean z;
        int width = bVar.getWidth() - 1;
        int height = bVar.getHeight() - 1;
        int i2 = -1;
        int i3 = 0;
        while (width > 0) {
            int i4 = width == 6 ? width - 1 : width;
            while (height >= 0 && height < bVar.getHeight()) {
                int i5 = 0;
                while (i5 < 2) {
                    int i6 = i4 - i5;
                    if (oX(bVar.cF(i6, height))) {
                        if (i3 < aVar.getSize()) {
                            z = aVar.get(i3);
                            i3++;
                        } else {
                            z = false;
                        }
                        if (i != -1 && e.af(i, i6, height)) {
                            z = !z;
                        }
                        bVar.set(i6, height, z);
                    }
                    i5++;
                    i3 = i3;
                }
                height += i2;
            }
            i2 = -i2;
            height += i2;
            width = i4 - 2;
        }
        if (i3 != aVar.getSize()) {
            throw new i("Not all bits consumed: " + i3 + '/' + aVar.getSize());
        }
    }

    static int oW(int i) {
        return 32 - Integer.numberOfLeadingZeros(i);
    }

    static int cG(int i, int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int iOW = oW(i2);
        int iOW2 = i << (iOW - 1);
        while (oW(iOW2) >= iOW) {
            iOW2 ^= i2 << (oW(iOW2) - iOW);
        }
        return iOW2;
    }

    static void a(com.google.a.f.a.a aVar, int i, com.google.a.b.a aVar2) throws i {
        if (!g.oZ(i)) {
            throw new i("Invalid mask pattern");
        }
        int iAhl = (aVar.ahl() << 3) | i;
        aVar2.cv(iAhl, 5);
        aVar2.cv(cG(iAhl, 1335), 10);
        com.google.a.b.a aVar3 = new com.google.a.b.a();
        aVar3.cv(21522, 15);
        aVar2.b(aVar3);
        if (aVar2.getSize() != 15) {
            throw new i("should not happen but we got: " + aVar2.getSize());
        }
    }

    static void a(com.google.a.f.a.c cVar, com.google.a.b.a aVar) throws i {
        aVar.cv(cVar.ahn(), 6);
        aVar.cv(cG(cVar.ahn(), 7973), 12);
        if (aVar.getSize() != 18) {
            throw new i("should not happen but we got: " + aVar.getSize());
        }
    }

    private static boolean oX(int i) {
        return i == -1;
    }

    private static void h(b bVar) {
        for (int i = 8; i < bVar.getWidth() - 8; i++) {
            int i2 = (i + 1) % 2;
            if (oX(bVar.cF(i, 6))) {
                bVar.set(i, 6, i2);
            }
            if (oX(bVar.cF(6, i))) {
                bVar.set(6, i, i2);
            }
        }
    }

    private static void i(b bVar) throws i {
        if (bVar.cF(8, bVar.getHeight() - 8) == 0) {
            throw new i();
        }
        bVar.set(8, bVar.getHeight() - 8, 1);
    }

    private static void a(int i, int i2, b bVar) throws i {
        for (int i3 = 0; i3 < 8; i3++) {
            if (!oX(bVar.cF(i + i3, i2))) {
                throw new i();
            }
            bVar.set(i + i3, i2, 0);
        }
    }

    private static void b(int i, int i2, b bVar) throws i {
        for (int i3 = 0; i3 < 7; i3++) {
            if (!oX(bVar.cF(i, i2 + i3))) {
                throw new i();
            }
            bVar.set(i, i2 + i3, 0);
        }
    }

    private static void c(int i, int i2, b bVar) {
        for (int i3 = 0; i3 < 5; i3++) {
            for (int i4 = 0; i4 < 5; i4++) {
                bVar.set(i + i4, i2 + i3, cdu[i3][i4]);
            }
        }
    }

    private static void d(int i, int i2, b bVar) {
        for (int i3 = 0; i3 < 7; i3++) {
            for (int i4 = 0; i4 < 7; i4++) {
                bVar.set(i + i4, i2 + i3, cdt[i3][i4]);
            }
        }
    }

    private static void j(b bVar) throws i {
        int length = cdt[0].length;
        d(0, 0, bVar);
        d(bVar.getWidth() - length, 0, bVar);
        d(0, bVar.getWidth() - length, bVar);
        a(0, 7, bVar);
        a(bVar.getWidth() - 8, 7, bVar);
        a(0, bVar.getWidth() - 8, bVar);
        b(7, 0, bVar);
        b((bVar.getHeight() - 7) - 1, 0, bVar);
        b(7, bVar.getHeight() - 7, bVar);
    }

    private static void c(com.google.a.f.a.c cVar, b bVar) {
        if (cVar.ahn() >= 2) {
            int iAhn = cVar.ahn() - 1;
            int[] iArr = cdv[iAhn];
            int length = cdv[iAhn].length;
            for (int i = 0; i < length; i++) {
                for (int i2 = 0; i2 < length; i2++) {
                    int i3 = iArr[i];
                    int i4 = iArr[i2];
                    if (i4 != -1 && i3 != -1 && oX(bVar.cF(i4, i3))) {
                        c(i4 - 2, i3 - 2, bVar);
                    }
                }
            }
        }
    }
}
