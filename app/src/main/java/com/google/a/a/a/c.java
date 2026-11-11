package com.google.a.a.a;

import com.facebook.R;

/* JADX INFO: compiled from: Encoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final int[] bZX = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    public static a l(byte[] bArr, int i, int i2) {
        boolean z;
        int iS;
        com.google.a.b.a aVar;
        int i3;
        int iAbs;
        int i4;
        com.google.a.b.a aVarAgw = new d(bArr).agw();
        int size = ((aVarAgw.getSize() * i) / 100) + 11;
        int size2 = aVarAgw.getSize() + size;
        if (i2 != 0) {
            boolean z2 = i2 < 0;
            iAbs = Math.abs(i2);
            if (iAbs > (z2 ? 4 : 32)) {
                throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(i2)));
            }
            iS = S(iAbs, z2);
            int i5 = bZX[iAbs];
            int i6 = iS - (iS % i5);
            com.google.a.b.a aVarA = a(aVarAgw, i5);
            if (aVarA.getSize() + size > i6) {
                throw new IllegalArgumentException("Data to large for user specified layer");
            }
            if (z2 && aVarA.getSize() > (i5 << 6)) {
                throw new IllegalArgumentException("Data to large for user specified layer");
            }
            aVar = aVarA;
            i3 = i5;
            z = z2;
        } else {
            int i7 = 0;
            com.google.a.b.a aVarA2 = null;
            int i8 = 0;
            while (true) {
                if (i8 > 32) {
                    throw new IllegalArgumentException("Data too large for an Aztec code");
                }
                z = i8 <= 3;
                int i9 = z ? i8 + 1 : i8;
                iS = S(i9, z);
                if (size2 <= iS) {
                    if (i7 != bZX[i9]) {
                        i7 = bZX[i9];
                        aVarA2 = a(aVarAgw, i7);
                    }
                    int i10 = iS - (iS % i7);
                    if ((!z || aVarA2.getSize() <= (i7 << 6)) && aVarA2.getSize() + size <= i10) {
                        aVar = aVarA2;
                        i3 = i7;
                        iAbs = i9;
                        break;
                    }
                }
                i8++;
            }
        }
        com.google.a.b.a aVarA3 = a(aVar, iS, i3);
        int size3 = aVar.getSize() / i3;
        com.google.a.b.a aVarD = d(z, iAbs, size3);
        int i11 = (iAbs << 2) + (z ? 11 : 14);
        int[] iArr = new int[i11];
        if (z) {
            for (int i12 = 0; i12 < iArr.length; i12++) {
                iArr[i12] = i12;
            }
            i4 = i11;
        } else {
            i4 = i11 + 1 + ((((i11 / 2) - 1) / 15) * 2);
            int i13 = i11 / 2;
            int i14 = i4 / 2;
            for (int i15 = 0; i15 < i13; i15++) {
                int i16 = (i15 / 15) + i15;
                iArr[(i13 - i15) - 1] = (i14 - i16) - 1;
                iArr[i13 + i15] = i16 + i14 + 1;
            }
        }
        com.google.a.b.b bVar = new com.google.a.b.b(i4);
        int i17 = 0;
        for (int i18 = 0; i18 < iAbs; i18++) {
            int i19 = ((iAbs - i18) << 2) + (z ? 9 : 12);
            for (int i20 = 0; i20 < i19; i20++) {
                int i21 = i20 << 1;
                for (int i22 = 0; i22 < 2; i22++) {
                    if (aVarA3.get(i17 + i21 + i22)) {
                        bVar.set(iArr[(i18 << 1) + i22], iArr[(i18 << 1) + i20]);
                    }
                    if (aVarA3.get((i19 << 1) + i17 + i21 + i22)) {
                        bVar.set(iArr[(i18 << 1) + i20], iArr[((i11 - 1) - (i18 << 1)) - i22]);
                    }
                    if (aVarA3.get((i19 << 2) + i17 + i21 + i22)) {
                        bVar.set(iArr[((i11 - 1) - (i18 << 1)) - i22], iArr[((i11 - 1) - (i18 << 1)) - i20]);
                    }
                    if (aVarA3.get((i19 * 6) + i17 + i21 + i22)) {
                        bVar.set(iArr[((i11 - 1) - (i18 << 1)) - i20], iArr[(i18 << 1) + i22]);
                    }
                }
            }
            i17 = (i19 << 3) + i17;
        }
        a(bVar, z, i4, aVarD);
        if (z) {
            a(bVar, i4 / 2, 5);
        } else {
            a(bVar, i4 / 2, 7);
            int i23 = 0;
            int i24 = 0;
            while (i23 < (i11 / 2) - 1) {
                for (int i25 = (i4 / 2) & 1; i25 < i4; i25 += 2) {
                    bVar.set((i4 / 2) - i24, i25);
                    bVar.set((i4 / 2) + i24, i25);
                    bVar.set(i25, (i4 / 2) - i24);
                    bVar.set(i25, (i4 / 2) + i24);
                }
                i23 += 15;
                i24 += 16;
            }
        }
        a aVar2 = new a();
        aVar2.cf(z);
        aVar2.setSize(i4);
        aVar2.oy(iAbs);
        aVar2.oz(size3);
        aVar2.a(bVar);
        return aVar2;
    }

    private static void a(com.google.a.b.b bVar, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 2) {
            for (int i4 = i - i3; i4 <= i + i3; i4++) {
                bVar.set(i4, i - i3);
                bVar.set(i4, i + i3);
                bVar.set(i - i3, i4);
                bVar.set(i + i3, i4);
            }
        }
        bVar.set(i - i2, i - i2);
        bVar.set((i - i2) + 1, i - i2);
        bVar.set(i - i2, (i - i2) + 1);
        bVar.set(i + i2, i - i2);
        bVar.set(i + i2, (i - i2) + 1);
        bVar.set(i + i2, (i + i2) - 1);
    }

    static com.google.a.b.a d(boolean z, int i, int i2) {
        com.google.a.b.a aVar = new com.google.a.b.a();
        if (z) {
            aVar.cv(i - 1, 2);
            aVar.cv(i2 - 1, 6);
            return a(aVar, 28, 4);
        }
        aVar.cv(i - 1, 5);
        aVar.cv(i2 - 1, 11);
        return a(aVar, 40, 4);
    }

    private static void a(com.google.a.b.b bVar, boolean z, int i, com.google.a.b.a aVar) {
        int i2 = 0;
        int i3 = i / 2;
        if (z) {
            while (i2 < 7) {
                int i4 = (i3 - 3) + i2;
                if (aVar.get(i2)) {
                    bVar.set(i4, i3 - 5);
                }
                if (aVar.get(i2 + 7)) {
                    bVar.set(i3 + 5, i4);
                }
                if (aVar.get(20 - i2)) {
                    bVar.set(i4, i3 + 5);
                }
                if (aVar.get(27 - i2)) {
                    bVar.set(i3 - 5, i4);
                }
                i2++;
            }
            return;
        }
        while (i2 < 10) {
            int i5 = (i3 - 5) + i2 + (i2 / 5);
            if (aVar.get(i2)) {
                bVar.set(i5, i3 - 7);
            }
            if (aVar.get(i2 + 10)) {
                bVar.set(i3 + 7, i5);
            }
            if (aVar.get(29 - i2)) {
                bVar.set(i5, i3 + 7);
            }
            if (aVar.get(39 - i2)) {
                bVar.set(i3 - 7, i5);
            }
            i2++;
        }
    }

    private static com.google.a.b.a a(com.google.a.b.a aVar, int i, int i2) {
        int size = aVar.getSize() / i2;
        com.google.a.b.a.c cVar = new com.google.a.b.a.c(oA(i2));
        int i3 = i / i2;
        int[] iArrB = b(aVar, i2, i3);
        cVar.g(iArrB, i3 - size);
        com.google.a.b.a aVar2 = new com.google.a.b.a();
        aVar2.cv(0, i % i2);
        for (int i4 : iArrB) {
            aVar2.cv(i4, i2);
        }
        return aVar2;
    }

    private static int[] b(com.google.a.b.a aVar, int i, int i2) {
        int[] iArr = new int[i2];
        int size = aVar.getSize() / i;
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                i4 |= aVar.get((i3 * i) + i5) ? 1 << ((i - i5) - 1) : 0;
            }
            iArr[i3] = i4;
        }
        return iArr;
    }

    private static com.google.a.b.a.a oA(int i) {
        switch (i) {
            case 4:
                return com.google.a.b.a.a.caW;
            case 5:
            case 7:
            case 9:
            case R.styleable.CardView_contentPaddingTop /* 11 */:
            default:
                throw new IllegalArgumentException("Unsupported word size " + i);
            case 6:
                return com.google.a.b.a.a.caV;
            case 8:
                return com.google.a.b.a.a.caZ;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                return com.google.a.b.a.a.caU;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                return com.google.a.b.a.a.caT;
        }
    }

    static com.google.a.b.a a(com.google.a.b.a aVar, int i) {
        int i2;
        com.google.a.b.a aVar2 = new com.google.a.b.a();
        int size = aVar.getSize();
        int i3 = (1 << i) - 2;
        int i4 = 0;
        while (i4 < size) {
            int i5 = 0;
            for (int i6 = 0; i6 < i; i6++) {
                if (i4 + i6 >= size || aVar.get(i4 + i6)) {
                    i5 |= 1 << ((i - 1) - i6);
                }
            }
            if ((i5 & i3) == i3) {
                aVar2.cv(i5 & i3, i);
                i2 = i4 - 1;
            } else if ((i5 & i3) == 0) {
                aVar2.cv(i5 | 1, i);
                i2 = i4 - 1;
            } else {
                aVar2.cv(i5, i);
                i2 = i4;
            }
            i4 = i2 + i;
        }
        return aVar2;
    }

    private static int S(int i, boolean z) {
        return ((z ? 88 : 112) + (i << 4)) * i;
    }
}
