package com.google.a.a;

import com.google.a.b.b;
import com.google.a.c;
import com.google.a.h;
import java.nio.charset.Charset;
import java.util.Map;

/* JADX INFO: compiled from: AztecWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements h {
    private static final Charset bZQ = Charset.forName("ISO-8859-1");

    /* JADX WARN: Code duplicated, block: B:15:0x0054 A[PHI: r0 r1
  0x0054: PHI (r0v1 java.nio.charset.Charset) = (r0v0 java.nio.charset.Charset), (r0v4 java.nio.charset.Charset) binds: [B:3:0x0005, B:11:0x0039] A[DONT_GENERATE, DONT_INLINE]
  0x0054: PHI (r1v1 int) = (r1v0 int), (r1v3 int) binds: [B:3:0x0005, B:11:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.a.h
    public b a(String str, com.google.a.a aVar, int i, int i2, Map<c, ?> map) {
        int i3;
        Charset charset;
        Charset charsetForName = bZQ;
        int i4 = 33;
        int i5 = 0;
        if (map == null) {
            i3 = i4;
            charset = charsetForName;
        } else {
            if (map.containsKey(c.CHARACTER_SET)) {
                charsetForName = Charset.forName(map.get(c.CHARACTER_SET).toString());
            }
            if (map.containsKey(c.ERROR_CORRECTION)) {
                i4 = Integer.parseInt(map.get(c.ERROR_CORRECTION).toString());
            }
            if (map.containsKey(c.AZTEC_LAYERS)) {
                i5 = Integer.parseInt(map.get(c.AZTEC_LAYERS).toString());
                i3 = i4;
                charset = charsetForName;
            } else {
                i3 = i4;
                charset = charsetForName;
            }
        }
        return a(str, aVar, i, i2, charset, i3, i5);
    }

    private static b a(String str, com.google.a.a aVar, int i, int i2, Charset charset, int i3, int i4) {
        if (aVar != com.google.a.a.AZTEC) {
            throw new IllegalArgumentException("Can only encode AZTEC, but got " + aVar);
        }
        return a(com.google.a.a.a.c.l(str.getBytes(charset), i3, i4), i, i2);
    }

    private static b a(com.google.a.a.a.a aVar, int i, int i2) {
        b bVarAgv = aVar.agv();
        if (bVarAgv == null) {
            throw new IllegalStateException();
        }
        int width = bVarAgv.getWidth();
        int height = bVarAgv.getHeight();
        int iMax = Math.max(i, width);
        int iMax2 = Math.max(i2, height);
        int iMin = Math.min(iMax / width, iMax2 / height);
        int i3 = (iMax - (width * iMin)) / 2;
        b bVar = new b(iMax, iMax2);
        int i4 = (iMax2 - (height * iMin)) / 2;
        for (int i5 = 0; i5 < height; i5++) {
            int i6 = i3;
            int i7 = 0;
            while (i7 < width) {
                if (bVarAgv.cw(i7, i5)) {
                    bVar.K(i6, i4, iMin, iMin);
                }
                i7++;
                i6 += iMin;
            }
            i4 += iMin;
        }
        return bVar;
    }
}
