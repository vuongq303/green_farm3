package com.google.a.e.a;

import java.lang.reflect.Array;

/* JADX INFO: compiled from: BarcodeMatrix.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final b[] ccq;
    private int ccr;
    private final int height;
    private final int width;

    a(int i, int i2) {
        this.ccq = new b[i];
        int length = this.ccq.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.ccq[i3] = new b(((i2 + 4) * 17) + 1);
        }
        this.width = i2 * 17;
        this.height = i;
        this.ccr = -1;
    }

    void ahf() {
        this.ccr++;
    }

    b ahg() {
        return this.ccq[this.ccr];
    }

    public byte[][] cD(int i, int i2) {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.height * i2, this.width * i);
        int i3 = this.height * i2;
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[(i3 - i4) - 1] = this.ccq[i4 / i2].oS(i);
        }
        return bArr;
    }
}
