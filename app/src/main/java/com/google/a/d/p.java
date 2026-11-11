package com.google.a.d;

import java.util.Map;

/* JADX INFO: compiled from: OneDimensionalCodeWriter.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class p implements com.google.a.h {
    public abstract boolean[] iC(String str);

    @Override // com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + i + 'x' + i2);
        }
        int iAhe = ahe();
        if (map != null && map.containsKey(com.google.a.c.MARGIN)) {
            iAhe = Integer.parseInt(map.get(com.google.a.c.MARGIN).toString());
        }
        return a(iC(str), i, i2, iAhe);
    }

    private static com.google.a.b.b a(boolean[] zArr, int i, int i2, int i3) {
        int length = zArr.length;
        int i4 = length + i3;
        int iMax = Math.max(i, i4);
        int iMax2 = Math.max(1, i2);
        int i5 = iMax / i4;
        int i6 = (iMax - (length * i5)) / 2;
        com.google.a.b.b bVar = new com.google.a.b.b(iMax, iMax2);
        int i7 = 0;
        while (i7 < length) {
            if (zArr[i7]) {
                bVar.K(i6, 0, i5, iMax2);
            }
            i7++;
            i6 += i5;
        }
        return bVar;
    }

    protected static int a(boolean[] zArr, int i, int[] iArr, boolean z) {
        int length = iArr.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = i;
        while (i2 < length) {
            int i5 = iArr[i2];
            int i6 = 0;
            while (i6 < i5) {
                zArr[i4] = z;
                i6++;
                i4++;
            }
            i3 += i5;
            i2++;
            z = !z;
        }
        return i3;
    }

    public int ahe() {
        return 10;
    }
}
