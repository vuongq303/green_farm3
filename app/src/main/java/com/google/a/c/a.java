package com.google.a.c;

import com.google.a.b.b;
import com.google.a.c;
import com.google.a.c.a.e;
import com.google.a.c.a.i;
import com.google.a.c.a.j;
import com.google.a.c.a.k;
import com.google.a.c.a.l;
import com.google.a.h;
import java.util.Map;

/* JADX INFO: compiled from: DataMatrixWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements h {
    @Override // com.google.a.h
    public b a(String str, com.google.a.a aVar, int i, int i2, Map<c, ?> map) {
        com.google.a.b bVar;
        com.google.a.b bVar2;
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (aVar != com.google.a.a.DATA_MATRIX) {
            throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + aVar);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + i2);
        }
        l lVar = l.FORCE_NONE;
        if (map != null) {
            l lVar2 = (l) map.get(c.DATA_MATRIX_SHAPE);
            if (lVar2 != null) {
                lVar = lVar2;
            }
            com.google.a.b bVar3 = (com.google.a.b) map.get(c.MIN_SIZE);
            bVar2 = bVar3 != null ? bVar3 : null;
            bVar = (com.google.a.b) map.get(c.MAX_SIZE);
            if (bVar == null) {
                bVar = null;
            }
        } else {
            bVar = null;
            bVar2 = null;
        }
        String strA = j.a(str, lVar, bVar2, bVar);
        k kVarA = k.a(strA.length(), lVar, bVar2, bVar, true);
        e eVar = new e(i.a(strA, kVarA), kVarA.agY(), kVarA.agZ());
        eVar.agK();
        return a(eVar, kVarA);
    }

    private static b a(e eVar, k kVar) {
        int i;
        int i2;
        int iAgY = kVar.agY();
        int iAgZ = kVar.agZ();
        com.google.a.f.b.b bVar = new com.google.a.f.b.b(kVar.aha(), kVar.ahb());
        int i3 = 0;
        int i4 = 0;
        while (i3 < iAgZ) {
            if (i3 % kVar.cbG == 0) {
                int i5 = 0;
                for (int i6 = 0; i6 < kVar.aha(); i6++) {
                    bVar.set(i5, i4, i6 % 2 == 0);
                    i5++;
                }
                i = i4 + 1;
            } else {
                i = i4;
            }
            int i7 = 0;
            for (int i8 = 0; i8 < iAgY; i8++) {
                if (i8 % kVar.cbF == 0) {
                    bVar.set(i7, i, true);
                    i7++;
                }
                bVar.set(i7, i, eVar.cB(i8, i3));
                int i9 = i7 + 1;
                if (i8 % kVar.cbF == kVar.cbF - 1) {
                    bVar.set(i9, i, i3 % 2 == 0);
                    i7 = i9 + 1;
                } else {
                    i7 = i9;
                }
            }
            int i10 = i + 1;
            if (i3 % kVar.cbG == kVar.cbG - 1) {
                int i11 = 0;
                for (int i12 = 0; i12 < kVar.aha(); i12++) {
                    bVar.set(i11, i10, true);
                    i11++;
                }
                i2 = i10 + 1;
            } else {
                i2 = i10;
            }
            i3++;
            i4 = i2;
        }
        return a(bVar);
    }

    private static b a(com.google.a.f.b.b bVar) {
        int width = bVar.getWidth();
        int height = bVar.getHeight();
        b bVar2 = new b(width, height);
        bVar2.clear();
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                if (bVar.cF(i, i2) == 1) {
                    bVar2.set(i, i2);
                }
            }
        }
        return bVar2;
    }
}
