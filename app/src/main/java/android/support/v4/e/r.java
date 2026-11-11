package android.support.v4.e;

import java.util.ConcurrentModificationException;
import java.util.Map;

/* JADX INFO: compiled from: SimpleArrayMap.java */
/* JADX INFO: loaded from: classes.dex */
public class r<K, V> {
    static Object[] gQ;
    static int gR;
    static Object[] gS;
    static int gT;
    int[] gy;
    Object[] gz;
    int mSize;

    private static int b(int[] iArr, int i, int i2) {
        try {
            return e.a(iArr, i, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int iB = b(this.gy, i2, i);
        if (iB >= 0 && !obj.equals(this.gz[iB << 1])) {
            int i3 = iB + 1;
            while (i3 < i2 && this.gy[i3] == i) {
                if (obj.equals(this.gz[i3 << 1])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = iB - 1; i4 >= 0 && this.gy[i4] == i; i4--) {
                if (obj.equals(this.gz[i4 << 1])) {
                    return i4;
                }
            }
            return i3 ^ (-1);
        }
        return iB;
    }

    int aK() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int iB = b(this.gy, i, 0);
        if (iB >= 0 && this.gz[iB << 1] != null) {
            int i2 = iB + 1;
            while (i2 < i && this.gy[i2] == 0) {
                if (this.gz[i2 << 1] == null) {
                    return i2;
                }
                i2++;
            }
            for (int i3 = iB - 1; i3 >= 0 && this.gy[i3] == 0; i3--) {
                if (this.gz[i3 << 1] == null) {
                    return i3;
                }
            }
            return i2 ^ (-1);
        }
        return iB;
    }

    private void q(int i) {
        if (i == 8) {
            synchronized (a.class) {
                if (gS != null) {
                    Object[] objArr = gS;
                    this.gz = objArr;
                    gS = (Object[]) objArr[0];
                    this.gy = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    gT--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (a.class) {
                if (gQ != null) {
                    Object[] objArr2 = gQ;
                    this.gz = objArr2;
                    gQ = (Object[]) objArr2[0];
                    this.gy = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    gR--;
                    return;
                }
            }
        }
        this.gy = new int[i];
        this.gz = new Object[i << 1];
    }

    private static void a(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (a.class) {
                if (gT < 10) {
                    objArr[0] = gS;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    gS = objArr;
                    gT++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (a.class) {
                if (gR < 10) {
                    objArr[0] = gQ;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    gQ = objArr;
                    gR++;
                }
            }
        }
    }

    public r() {
        this.gy = e.gB;
        this.gz = e.gD;
        this.mSize = 0;
    }

    public r(int i) {
        if (i == 0) {
            this.gy = e.gB;
            this.gz = e.gD;
        } else {
            q(i);
        }
        this.mSize = 0;
    }

    public void clear() {
        if (this.mSize > 0) {
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            int i = this.mSize;
            this.gy = e.gB;
            this.gz = e.gD;
            this.mSize = 0;
            a(iArr, objArr, i);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        if (this.gy.length < i) {
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            q(i);
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.gy, 0, i2);
                System.arraycopy(objArr, 0, this.gz, 0, i2 << 1);
            }
            a(iArr, objArr, i2);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public int indexOfKey(Object obj) {
        return obj == null ? aK() : indexOf(obj, obj.hashCode());
    }

    int indexOfValue(Object obj) {
        int i = 1;
        int i2 = this.mSize * 2;
        Object[] objArr = this.gz;
        if (obj == null) {
            while (i < i2) {
                if (objArr[i] != null) {
                    i += 2;
                } else {
                    return i >> 1;
                }
            }
        } else {
            while (i < i2) {
                if (!obj.equals(objArr[i])) {
                    i += 2;
                } else {
                    return i >> 1;
                }
            }
        }
        return -1;
    }

    public boolean containsValue(Object obj) {
        return indexOfValue(obj) >= 0;
    }

    public V get(Object obj) {
        int iIndexOfKey = indexOfKey(obj);
        if (iIndexOfKey >= 0) {
            return (V) this.gz[(iIndexOfKey << 1) + 1];
        }
        return null;
    }

    public K keyAt(int i) {
        return (K) this.gz[i << 1];
    }

    public V valueAt(int i) {
        return (V) this.gz[(i << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        int i2 = (i << 1) + 1;
        V v2 = (V) this.gz[i2];
        this.gz[i2] = v;
        return v2;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public V put(K k, V v) {
        int iHashCode;
        int iIndexOf;
        int i = 8;
        int i2 = this.mSize;
        if (k == null) {
            iIndexOf = aK();
            iHashCode = 0;
        } else {
            iHashCode = k.hashCode();
            iIndexOf = indexOf(k, iHashCode);
        }
        if (iIndexOf >= 0) {
            int i3 = (iIndexOf << 1) + 1;
            V v2 = (V) this.gz[i3];
            this.gz[i3] = v;
            return v2;
        }
        int i4 = iIndexOf ^ (-1);
        if (i2 >= this.gy.length) {
            if (i2 >= 8) {
                i = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i = 4;
            }
            int[] iArr = this.gy;
            Object[] objArr = this.gz;
            q(i);
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.gy.length > 0) {
                System.arraycopy(iArr, 0, this.gy, 0, iArr.length);
                System.arraycopy(objArr, 0, this.gz, 0, objArr.length);
            }
            a(iArr, objArr, i2);
        }
        if (i4 < i2) {
            System.arraycopy(this.gy, i4, this.gy, i4 + 1, i2 - i4);
            System.arraycopy(this.gz, i4 << 1, this.gz, (i4 + 1) << 1, (this.mSize - i4) << 1);
        }
        if (i2 != this.mSize || i4 >= this.gy.length) {
            throw new ConcurrentModificationException();
        }
        this.gy[i4] = iHashCode;
        this.gz[i4 << 1] = k;
        this.gz[(i4 << 1) + 1] = v;
        this.mSize++;
        return null;
    }

    public V remove(Object obj) {
        int iIndexOfKey = indexOfKey(obj);
        if (iIndexOfKey >= 0) {
            return removeAt(iIndexOfKey);
        }
        return null;
    }

    public V removeAt(int i) {
        int i2;
        V v = (V) this.gz[(i << 1) + 1];
        int i3 = this.mSize;
        if (i3 <= 1) {
            a(this.gy, this.gz, i3);
            this.gy = e.gB;
            this.gz = e.gD;
            i2 = 0;
        } else {
            int i4 = i3 - 1;
            if (this.gy.length > 8 && this.mSize < this.gy.length / 3) {
                int i5 = i3 > 8 ? (i3 >> 1) + i3 : 8;
                int[] iArr = this.gy;
                Object[] objArr = this.gz;
                q(i5);
                if (i3 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.gy, 0, i);
                    System.arraycopy(objArr, 0, this.gz, 0, i << 1);
                }
                if (i < i4) {
                    System.arraycopy(iArr, i + 1, this.gy, i, i4 - i);
                    System.arraycopy(objArr, (i + 1) << 1, this.gz, i << 1, (i4 - i) << 1);
                    i2 = i4;
                }
            } else {
                if (i < i4) {
                    System.arraycopy(this.gy, i + 1, this.gy, i, i4 - i);
                    System.arraycopy(this.gz, (i + 1) << 1, this.gz, i << 1, (i4 - i) << 1);
                }
                this.gz[i4 << 1] = null;
                this.gz[(i4 << 1) + 1] = null;
            }
            i2 = i4;
        }
        if (i3 != this.mSize) {
            throw new ConcurrentModificationException();
        }
        this.mSize = i2;
        return v;
    }

    public int size() {
        return this.mSize;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof r) {
            r rVar = (r) obj;
            if (size() != rVar.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    K kKeyAt = keyAt(i);
                    V vValueAt = valueAt(i);
                    Object obj2 = rVar.get(kKeyAt);
                    if (vValueAt == null) {
                        if (obj2 != null || !rVar.containsKey(kKeyAt)) {
                            return false;
                        }
                    } else if (!vValueAt.equals(obj2)) {
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
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (size() != map.size()) {
            return false;
        }
        for (int i2 = 0; i2 < this.mSize; i2++) {
            try {
                K kKeyAt2 = keyAt(i2);
                V vValueAt2 = valueAt(i2);
                Object obj3 = map.get(kKeyAt2);
                if (vValueAt2 == null) {
                    if (obj3 != null || !map.containsKey(kKeyAt2)) {
                        return false;
                    }
                } else if (!vValueAt2.equals(obj3)) {
                    return false;
                }
            } catch (ClassCastException e3) {
                return false;
            } catch (NullPointerException e4) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int[] iArr = this.gy;
        Object[] objArr = this.gz;
        int i = this.mSize;
        int i2 = 1;
        int i3 = 0;
        int iHashCode = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return iHashCode;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K kKeyAt = keyAt(i);
            if (kKeyAt != this) {
                sb.append(kKeyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V vValueAt = valueAt(i);
            if (vValueAt != this) {
                sb.append(vValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
