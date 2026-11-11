package android.support.v4.e;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: ArrayMap.java */
/* JADX INFO: loaded from: classes.dex */
class b<K, V> extends j<K, V> {
    final /* synthetic */ a gq;

    b(a aVar) {
        this.gq = aVar;
    }

    @Override // android.support.v4.e.j
    protected int aH() {
        return this.gq.mSize;
    }

    @Override // android.support.v4.e.j
    protected Object d(int i, int i2) {
        return this.gq.gz[(i << 1) + i2];
    }

    @Override // android.support.v4.e.j
    protected int j(Object obj) {
        return this.gq.indexOfKey(obj);
    }

    @Override // android.support.v4.e.j
    protected int k(Object obj) {
        return this.gq.indexOfValue(obj);
    }

    @Override // android.support.v4.e.j
    protected Map<K, V> aI() {
        return this.gq;
    }

    @Override // android.support.v4.e.j
    protected void a(K k, V v) {
        this.gq.put(k, v);
    }

    @Override // android.support.v4.e.j
    protected V a(int i, V v) {
        return this.gq.setValueAt(i, v);
    }

    @Override // android.support.v4.e.j
    protected void p(int i) {
        this.gq.removeAt(i);
    }

    @Override // android.support.v4.e.j
    protected void aJ() {
        this.gq.clear();
    }
}
