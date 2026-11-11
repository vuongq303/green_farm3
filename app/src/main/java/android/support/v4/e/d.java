package android.support.v4.e;

import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [E] */
/* JADX INFO: compiled from: ArraySet.java */
/* JADX INFO: loaded from: classes.dex */
class d<E> extends j<E, E> {
    final /* synthetic */ c gA;

    d(c cVar) {
        this.gA = cVar;
    }

    @Override // android.support.v4.e.j
    protected int aH() {
        return this.gA.mSize;
    }

    @Override // android.support.v4.e.j
    protected Object d(int i, int i2) {
        return this.gA.gz[i];
    }

    @Override // android.support.v4.e.j
    protected int j(Object obj) {
        return this.gA.indexOf(obj);
    }

    @Override // android.support.v4.e.j
    protected int k(Object obj) {
        return this.gA.indexOf(obj);
    }

    @Override // android.support.v4.e.j
    protected Map<E, E> aI() {
        throw new UnsupportedOperationException("not a map");
    }

    @Override // android.support.v4.e.j
    protected void a(E e, E e2) {
        this.gA.add(e);
    }

    @Override // android.support.v4.e.j
    protected E a(int i, E e) {
        throw new UnsupportedOperationException("not a map");
    }

    @Override // android.support.v4.e.j
    protected void p(int i) {
        this.gA.removeAt(i);
    }

    @Override // android.support.v4.e.j
    protected void aJ() {
        this.gA.clear();
    }
}
