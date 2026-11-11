package com.google.a.a.a;

/* JADX INFO: compiled from: SimpleToken.java */
/* JADX INFO: loaded from: classes.dex */
final class f extends h {
    private final short cae;
    private final short value;

    f(h hVar, int i, int i2) {
        super(hVar);
        this.value = (short) i;
        this.cae = (short) i2;
    }

    @Override // com.google.a.a.a.h
    void a(com.google.a.b.a aVar, byte[] bArr) {
        aVar.cv(this.value, this.cae);
    }

    public String toString() {
        return "<" + Integer.toBinaryString((this.value & ((1 << this.cae) - 1)) | (1 << this.cae) | (1 << this.cae)).substring(1) + '>';
    }
}
