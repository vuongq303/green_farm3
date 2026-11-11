package com.google.a.f;

import com.google.a.b.b;
import com.google.a.c;
import com.google.a.f.b.g;
import com.google.a.h;
import java.util.Map;

/* JADX INFO: compiled from: QRCodeWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements h {
    @Override // com.google.a.h
    public b a(String str, com.google.a.a aVar, int i, int i2, Map<c, ?> map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (aVar != com.google.a.a.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + aVar);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + i2);
        }
        com.google.a.f.a.a aVarIF = com.google.a.f.a.a.L;
        int i3 = 4;
        if (map != null) {
            if (map.containsKey(c.ERROR_CORRECTION)) {
                aVarIF = com.google.a.f.a.a.iF(map.get(c.ERROR_CORRECTION).toString());
            }
            if (map.containsKey(c.MARGIN)) {
                i3 = Integer.parseInt(map.get(c.MARGIN).toString());
            }
        }
        return a(com.google.a.f.b.c.a(str, aVarIF, map), i, i2, i3);
    }

    private static b a(g gVar, int i, int i2, int i3) {
        com.google.a.f.b.b bVarAhz = gVar.ahz();
        if (bVarAhz == null) {
            throw new IllegalStateException();
        }
        int width = bVarAhz.getWidth();
        int height = bVarAhz.getHeight();
        int i4 = (i3 << 1) + width;
        int i5 = (i3 << 1) + height;
        int iMax = Math.max(i, i4);
        int iMax2 = Math.max(i2, i5);
        int iMin = Math.min(iMax / i4, iMax2 / i5);
        int i6 = (iMax - (width * iMin)) / 2;
        b bVar = new b(iMax, iMax2);
        int i7 = (iMax2 - (height * iMin)) / 2;
        for (int i8 = 0; i8 < height; i8++) {
            int i9 = 0;
            int i10 = i6;
            while (i9 < width) {
                if (bVarAhz.cF(i9, i8) == 1) {
                    bVar.K(i10, i7, iMin, iMin);
                }
                i9++;
                i10 += iMin;
            }
            i7 += iMin;
        }
        return bVar;
    }
}
