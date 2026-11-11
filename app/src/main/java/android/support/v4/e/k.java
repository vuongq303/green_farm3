package android.support.v4.e;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: MapCollections.java */
/* JADX INFO: loaded from: classes.dex */
final class k<T> implements Iterator<T> {
    final int gL;
    boolean gM = false;
    final /* synthetic */ j gN;
    int mIndex;
    int mSize;

    k(j jVar, int i) {
        this.gN = jVar;
        this.gL = i;
        this.mSize = jVar.aH();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mIndex < this.mSize;
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T t = (T) this.gN.d(this.mIndex, this.gL);
        this.mIndex++;
        this.gM = true;
        return t;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.gM) {
            throw new IllegalStateException();
        }
        this.mIndex--;
        this.mSize--;
        this.gM = false;
        this.gN.p(this.mIndex);
    }
}
