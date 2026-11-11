package com.google.a.e.a;

/* JADX INFO: compiled from: BarcodeRow.java */
/* JADX INFO: loaded from: classes.dex */
final class b {
    private final byte[] ccs;
    private int cct = 0;

    b(int i) {
        this.ccs = new byte[i];
    }

    private void set(int i, boolean z) {
        this.ccs[i] = (byte) (z ? 1 : 0);
    }

    void k(boolean z, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = this.cct;
            this.cct = i3 + 1;
            set(i3, z);
        }
    }

    byte[] oS(int i) {
        byte[] bArr = new byte[this.ccs.length * i];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = this.ccs[i2 / i];
        }
        return bArr;
    }
}
