package android.support.v4.e;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: MapCollections.java */
/* JADX INFO: loaded from: classes.dex */
final class n<K, V> implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
    final /* synthetic */ j gN;
    int gO;
    boolean gP = false;
    int mIndex = -1;

    n(j jVar) {
        this.gN = jVar;
        this.gO = jVar.aH() - 1;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mIndex < this.gO;
    }

    @Override // java.util.Iterator
    /* JADX INFO: renamed from: aO, reason: merged with bridge method [inline-methods] */
    public Map.Entry<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.mIndex++;
        this.gP = true;
        return this;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.gP) {
            throw new IllegalStateException();
        }
        this.gN.p(this.mIndex);
        this.mIndex--;
        this.gO--;
        this.gP = false;
    }

    @Override // java.util.Map.Entry
    public K getKey() {
        if (!this.gP) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return (K) this.gN.d(this.mIndex, 0);
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        if (!this.gP) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return (V) this.gN.d(this.mIndex, 1);
    }

    @Override // java.util.Map.Entry
    public V setValue(V v) {
        if (!this.gP) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return (V) this.gN.a(this.mIndex, v);
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!this.gP) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return e.b(entry.getKey(), this.gN.d(this.mIndex, 0)) && e.b(entry.getValue(), this.gN.d(this.mIndex, 1));
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        if (!this.gP) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        Object objD = this.gN.d(this.mIndex, 0);
        Object objD2 = this.gN.d(this.mIndex, 1);
        return (objD2 != null ? objD2.hashCode() : 0) ^ (objD == null ? 0 : objD.hashCode());
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
