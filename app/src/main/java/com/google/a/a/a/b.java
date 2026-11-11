package com.google.a.a.a;

/* JADX INFO: compiled from: BinaryShiftToken.java */
/* JADX INFO: loaded from: classes.dex */
final class b extends h {
    private final short bZV;
    private final short bZW;

    b(h hVar, int i, int i2) {
        super(hVar);
        this.bZV = (short) i;
        this.bZW = (short) i2;
    }

    @Override // com.google.a.a.a.h
    public void a(com.google.a.b.a aVar, byte[] bArr) {
        for (int i = 0; i < this.bZW; i++) {
            if (i == 0 || (i == 31 && this.bZW <= 62)) {
                aVar.cv(31, 5);
                if (this.bZW > 62) {
                    aVar.cv(this.bZW - 31, 16);
                } else if (i == 0) {
                    aVar.cv(Math.min((int) this.bZW, 31), 5);
                } else {
                    aVar.cv(this.bZW - 31, 5);
                }
            }
            aVar.cv(bArr[this.bZV + i], 8);
        }
    }

    public String toString() {
        return "<" + ((int) this.bZV) + "::" + ((this.bZV + this.bZW) - 1) + '>';
    }
}
