package com.google.a.e;

import com.google.a.b.b;
import com.google.a.c;
import com.google.a.e.a.d;
import com.google.a.e.a.e;
import com.google.a.h;
import com.google.a.i;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Map;

/* JADX INFO: compiled from: PDF417Writer.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements h {
    @Override // com.google.a.h
    public b a(String str, com.google.a.a aVar, int i, int i2, Map<c, ?> map) {
        int i3;
        int i4;
        if (aVar != com.google.a.a.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + aVar);
        }
        e eVar = new e();
        int i5 = 2;
        if (map == null) {
            i3 = 30;
        } else {
            if (map.containsKey(c.PDF417_COMPACT)) {
                eVar.cf(Boolean.valueOf(map.get(c.PDF417_COMPACT).toString()).booleanValue());
            }
            if (map.containsKey(c.PDF417_COMPACTION)) {
                eVar.a(com.google.a.e.a.c.iE(map.get(c.PDF417_COMPACTION).toString()));
            }
            if (map.containsKey(c.PDF417_DIMENSIONS)) {
                d dVar = (d) map.get(c.PDF417_DIMENSIONS);
                eVar.N(dVar.ahi(), dVar.ahh(), dVar.getMaxRows(), dVar.ahj());
            }
            if (!map.containsKey(c.MARGIN)) {
                i4 = 30;
            } else {
                i4 = Integer.parseInt(map.get(c.MARGIN).toString());
            }
            if (map.containsKey(c.ERROR_CORRECTION)) {
                i5 = Integer.parseInt(map.get(c.ERROR_CORRECTION).toString());
            }
            if (map.containsKey(c.CHARACTER_SET)) {
                eVar.a(Charset.forName(map.get(c.CHARACTER_SET).toString()));
            }
            i3 = i4;
        }
        return a(eVar, str, i5, i, i2, i3);
    }

    private static b a(e eVar, String str, int i, int i2, int i3, int i4) throws i {
        boolean z;
        eVar.U(str, i);
        byte[][] bArrCD = eVar.ahk().cD(1, 4);
        if ((i3 > i2) ^ (bArrCD[0].length < bArrCD.length)) {
            bArrCD = a(bArrCD);
            z = true;
        } else {
            z = false;
        }
        int length = i2 / bArrCD[0].length;
        int length2 = i3 / bArrCD.length;
        if (length >= length2) {
            length = length2;
        }
        if (length > 1) {
            byte[][] bArrCD2 = eVar.ahk().cD(length, length << 2);
            return a(z ? a(bArrCD2) : bArrCD2, i4);
        }
        return a(bArrCD, i4);
    }

    private static b a(byte[][] bArr, int i) {
        b bVar = new b(bArr[0].length + (i * 2), bArr.length + (i * 2));
        bVar.clear();
        int height = (bVar.getHeight() - i) - 1;
        int i2 = 0;
        while (i2 < bArr.length) {
            for (int i3 = 0; i3 < bArr[0].length; i3++) {
                if (bArr[i2][i3] == 1) {
                    bVar.set(i3 + i, height);
                }
            }
            i2++;
            height--;
        }
        return bVar;
    }

    private static byte[][] a(byte[][] bArr) {
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bArr[0].length, bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            int length = (bArr.length - i) - 1;
            for (int i2 = 0; i2 < bArr[0].length; i2++) {
                bArr2[i2][length] = bArr[i][i2];
            }
        }
        return bArr2;
    }
}
