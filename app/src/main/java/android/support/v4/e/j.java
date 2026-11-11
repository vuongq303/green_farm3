package android.support.v4.e;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: MapCollections.java */
/* JADX INFO: loaded from: classes.dex */
abstract class j<K, V> {
    j<K, V>.l gI;
    j<K, V>.m gJ;
    j<K, V>.o gK;

    protected abstract V a(int i, V v);

    protected abstract void a(K k, V v);

    protected abstract int aH();

    protected abstract Map<K, V> aI();

    protected abstract void aJ();

    protected abstract Object d(int i, int i2);

    protected abstract int j(Object obj);

    protected abstract int k(Object obj);

    protected abstract void p(int i);

    j() {
    }

    /* JADX INFO: compiled from: MapCollections.java */
    final class l implements Set<Map.Entry<K, V>> {
        l() {
        }

        @Override // java.util.Set, java.util.Collection
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int iAH = j.this.aH();
            for (Map.Entry<K, V> entry : collection) {
                j.this.a(entry.getKey(), entry.getValue());
            }
            return iAH != j.this.aH();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            j.this.aJ();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iJ = j.this.j(entry.getKey());
            if (iJ >= 0) {
                return e.b(j.this.d(iJ, 1), entry.getValue());
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return j.this.aH() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new n(j.this);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return j.this.aH();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return j.a((Set) this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iAH = j.this.aH() - 1;
            int iHashCode = 0;
            while (iAH >= 0) {
                Object objD = j.this.d(iAH, 0);
                Object objD2 = j.this.d(iAH, 1);
                iAH--;
                iHashCode += (objD2 == null ? 0 : objD2.hashCode()) ^ (objD == null ? 0 : objD.hashCode());
            }
            return iHashCode;
        }
    }

    /* JADX INFO: compiled from: MapCollections.java */
    final class m implements Set<K> {
        m() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            j.this.aJ();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return j.this.j(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return j.a((Map) j.this.aI(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return j.this.aH() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new k(j.this, 0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int iJ = j.this.j(obj);
            if (iJ < 0) {
                return false;
            }
            j.this.p(iJ);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return j.b(j.this.aI(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return j.c(j.this.aI(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return j.this.aH();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return j.this.t(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) j.this.a(tArr, 0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return j.a((Set) this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iAH = j.this.aH() - 1; iAH >= 0; iAH--) {
                Object objD = j.this.d(iAH, 0);
                iHashCode += objD == null ? 0 : objD.hashCode();
            }
            return iHashCode;
        }
    }

    /* JADX INFO: compiled from: MapCollections.java */
    final class o implements Collection<V> {
        o() {
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            j.this.aJ();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return j.this.k(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return j.this.aH() == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new k(j.this, 1);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int iK = j.this.k(obj);
            if (iK < 0) {
                return false;
            }
            j.this.p(iK);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int iAH = j.this.aH();
            int i = 0;
            boolean z = false;
            while (i < iAH) {
                if (collection.contains(j.this.d(i, 1))) {
                    j.this.p(i);
                    i--;
                    iAH--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int iAH = j.this.aH();
            int i = 0;
            boolean z = false;
            while (i < iAH) {
                if (!collection.contains(j.this.d(i, 1))) {
                    j.this.p(i);
                    i--;
                    iAH--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return j.this.aH();
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return j.this.t(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) j.this.a(tArr, 1);
        }
    }

    public static <K, V> boolean a(Map<K, V> map, Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean b(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static <K, V> boolean c(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    public Object[] t(int i) {
        int iAH = aH();
        Object[] objArr = new Object[iAH];
        for (int i2 = 0; i2 < iAH; i2++) {
            objArr[i2] = d(i2, i);
        }
        return objArr;
    }

    public <T> T[] a(T[] tArr, int i) {
        int iAH = aH();
        Object[] objArr = tArr.length < iAH ? (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iAH)) : tArr;
        for (int i2 = 0; i2 < iAH; i2++) {
            objArr[i2] = d(i2, i);
        }
        if (objArr.length > iAH) {
            objArr[iAH] = null;
        }
        return (T[]) objArr;
    }

    public static <T> boolean a(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            return set.size() == set2.size() && set.containsAll(set2);
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public Set<Map.Entry<K, V>> aM() {
        if (this.gI == null) {
            this.gI = new l();
        }
        return this.gI;
    }

    public Set<K> getKeySet() {
        if (this.gJ == null) {
            this.gJ = new m();
        }
        return this.gJ;
    }

    public Collection<V> aN() {
        if (this.gK == null) {
            this.gK = new o();
        }
        return this.gK;
    }
}
