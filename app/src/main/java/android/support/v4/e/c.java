package android.support.v4.e;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: ArraySet.java */
/* JADX INFO: loaded from: classes.dex */
public final class c<E> implements Collection<E>, Set<E> {
    private static final int[] gr = new int[0];
    private static final Object[] gs = new Object[0];
    static Object[] gt;
    static int gu;
    static Object[] gv;
    static int gw;
    j<E, E> gp;
    final boolean gx;
    int[] gy;
    Object[] gz;
    int mSize;

    private int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int iA = e.a(this.gy, i2, i);
        if (iA >= 0 && !obj.equals(this.gz[iA])) {
            int i3 = iA + 1;
            while (i3 < i2 && this.gy[i3] == i) {
                if (obj.equals(this.gz[i3])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = iA - 1; i4 >= 0 && this.gy[i4] == i; i4--) {
                if (obj.equals(this.gz[i4])) {
                    return i4;
                }
            }
            return i3 ^ (-1);
        }
        return iA;
    }

    private int aK() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int iA = e.a(this.gy, i, 0);
        if (iA >= 0 && this.gz[iA] != null) {
            int i2 = iA + 1;
            while (i2 < i && this.gy[i2] == 0) {
                if (this.gz[i2] == null) {
                    return i2;
                }
                i2++;
            }
            for (int i3 = iA - 1; i3 >= 0 && this.gy[i3] == 0; i3--) {
                if (this.gz[i3] == null) {
                    return i3;
                }
            }
            return i2 ^ (-1);
        }
        return iA;
    }

    private void q(int i) {
        if (i == 8) {
            synchronized (c.class) {
                if (gv != null) {
                    Object[] objArr = gv;
                    this.gz = objArr;
                    gv = (Object[]) objArr[0];
                    this.gy = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    gw--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (c.class) {
                if (gt != null) {
                    Object[] objArr2 = gt;
                    this.gz = objArr2;
                    gt = (Object[]) objArr2[0];
                    this.gy = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    gu--;
                    return;
                }
            }
        }
        this.gy = new int[i];
        this.gz = new Object[i];
    }

    private static void a(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (c.class) {
                if (gw < 10) {
                    objArr[0] = gv;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    gv = objArr;
                    gw++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (c.class) {
                if (gu < 10) {
                    objArr[0] = gt;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    gt = objArr;
                    gu++;
                }
            }
        }
    }

    public c() {
        this(0, false);
    }

    public c(int i) {
        this(i, false);
    }

    public c(int i, boolean z) {
        this.gx = z;
        if (i == 0) {
            this.gy = gr;
            this.gz = gs;
        } else {
            q(i);
        }
        this.mSize = 0;
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        if (this.mSize != 0) {
            a(this.gy, this.gz, this.mSize);
            this.gy = gr;
            this.gz = gs;
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int i) {
        if (this.gy.length < i) {
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            q(i);
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.gy, 0, this.mSize);
                System.arraycopy(objArr, 0, this.gz, 0, this.mSize);
            }
            a(iArr, objArr, this.mSize);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(Object obj) {
        if (obj == null) {
            return aK();
        }
        return indexOf(obj, this.gx ? System.identityHashCode(obj) : obj.hashCode());
    }

    public E valueAt(int i) {
        return (E) this.gz[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e) {
        int iIndexOf;
        int i;
        int i2;
        if (e == null) {
            iIndexOf = aK();
            i = 0;
        } else {
            int iIdentityHashCode = this.gx ? System.identityHashCode(e) : e.hashCode();
            iIndexOf = indexOf(e, iIdentityHashCode);
            i = iIdentityHashCode;
        }
        if (iIndexOf >= 0) {
            return false;
        }
        int i3 = iIndexOf ^ (-1);
        if (this.mSize >= this.gy.length) {
            if (this.mSize >= 8) {
                i2 = this.mSize + (this.mSize >> 1);
            } else {
                i2 = this.mSize >= 4 ? 8 : 4;
            }
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            q(i2);
            if (this.gy.length > 0) {
                System.arraycopy(iArr, 0, this.gy, 0, iArr.length);
                System.arraycopy(objArr, 0, this.gz, 0, objArr.length);
            }
            a(iArr, objArr, this.mSize);
        }
        if (i3 < this.mSize) {
            System.arraycopy(this.gy, i3, this.gy, i3 + 1, this.mSize - i3);
            System.arraycopy(this.gz, i3, this.gz, i3 + 1, this.mSize - i3);
        }
        this.gy[i3] = i;
        this.gz[i3] = e;
        this.mSize++;
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public E removeAt(int i) {
        E e = (E) this.gz[i];
        if (this.mSize <= 1) {
            a(this.gy, this.gz, this.mSize);
            this.gy = gr;
            this.gz = gs;
            this.mSize = 0;
        } else if (this.gy.length > 8 && this.mSize < this.gy.length / 3) {
            int i2 = this.mSize > 8 ? this.mSize + (this.mSize >> 1) : 8;
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            q(i2);
            this.mSize--;
            if (i > 0) {
                System.arraycopy(iArr, 0, this.gy, 0, i);
                System.arraycopy(objArr, 0, this.gz, 0, i);
            }
            if (i < this.mSize) {
                System.arraycopy(iArr, i + 1, this.gy, i, this.mSize - i);
                System.arraycopy(objArr, i + 1, this.gz, i, this.mSize - i);
            }
        } else {
            this.mSize--;
            if (i < this.mSize) {
                System.arraycopy(this.gy, i + 1, this.gy, i, this.mSize - i);
                System.arraycopy(this.gz, i + 1, this.gz, i, this.mSize - i);
            }
            this.gz[this.mSize] = null;
        }
        return e;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.mSize;
    }

    @Override // java.util.Collection, java.util.Set
    public Object[] toArray() {
        Object[] objArr = new Object[this.mSize];
        System.arraycopy(this.gz, 0, objArr, 0, this.mSize);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        Object[] objArr = tArr.length < this.mSize ? (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.mSize)) : tArr;
        System.arraycopy(this.gz, 0, objArr, 0, this.mSize);
        if (objArr.length > this.mSize) {
            objArr[this.mSize] = null;
        }
        return (T[]) objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                if (!set.contains(valueAt(i))) {
                    return false;
                }
            } catch (ClassCastException e) {
                return false;
            } catch (NullPointerException e2) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.gy;
        int i = this.mSize;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = iArr[i2] + i3;
            i2++;
            i3 = i4;
        }
        return i3;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 14);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private j<E, E> aG() {
        if (this.gp == null) {
            this.gp = new d(this);
        }
        return this.gp;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return aG().getKeySet().iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends E> collection) {
        ensureCapacity(this.mSize + collection.size());
        boolean zAdd = false;
        Iterator<? extends E> it = collection.iterator();
        while (true) {
            boolean z = zAdd;
            if (it.hasNext()) {
                zAdd = add(it.next()) | z;
            } else {
                return z;
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        boolean zRemove = false;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.gz[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }
}
