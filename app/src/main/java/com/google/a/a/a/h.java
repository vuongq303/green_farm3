package com.google.a.a.a;

/* JADX INFO: compiled from: Token.java */
/* JADX INFO: loaded from: classes.dex */
abstract class h {
    static final h caj = new f(null, 0, 0);
    private final h cak;

    abstract void a(com.google.a.b.a aVar, byte[] bArr);

    h(h hVar) {
        this.cak = hVar;
    }

    final h agz() {
        return this.cak;
    }

    final h ct(int i, int i2) {
        return new f(this, i, i2);
    }

    final h cu(int i, int i2) {
        return new b(this, i, i2);
    }
}
