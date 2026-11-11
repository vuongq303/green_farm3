package android.support.v4.e;

/* JADX INFO: compiled from: SparseArrayCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class s<E> implements Cloneable {
    private static final Object gE = new Object();
    private boolean gF;
    private Object[] gH;
    private int[] gU;
    private int mSize;

    public s() {
        this(10);
    }

    public s(int i) {
        this.gF = false;
        if (i == 0) {
            this.gU = e.gB;
            this.gH = e.gD;
        } else {
            int iIdealIntArraySize = e.idealIntArraySize(i);
            this.gU = new int[iIdealIntArraySize];
            this.gH = new Object[iIdealIntArraySize];
        }
        this.mSize = 0;
    }

    /* JADX INFO: renamed from: aP, reason: merged with bridge method [inline-methods] */
    public s<E> clone() {
        try {
            s<E> sVar = (s) super.clone();
            try {
                sVar.gU = (int[]) this.gU.clone();
                sVar.gH = (Object[]) this.gH.clone();
                return sVar;
            } catch (CloneNotSupportedException e) {
                return sVar;
            }
        } catch (CloneNotSupportedException e2) {
            return null;
        }
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        int iA = e.a(this.gU, this.mSize, i);
        return (iA < 0 || this.gH[iA] == gE) ? e : (E) this.gH[iA];
    }

    public void delete(int i) {
        int iA = e.a(this.gU, this.mSize, i);
        if (iA >= 0 && this.gH[iA] != gE) {
            this.gH[iA] = gE;
            this.gF = true;
        }
    }

    public void remove(int i) {
        delete(i);
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.gU;
        Object[] objArr = this.gH;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != gE) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.gF = false;
        this.mSize = i2;
    }

    public void put(int i, E e) {
        int iA = e.a(this.gU, this.mSize, i);
        if (iA >= 0) {
            this.gH[iA] = e;
            return;
        }
        int iA2 = iA ^ (-1);
        if (iA2 < this.mSize && this.gH[iA2] == gE) {
            this.gU[iA2] = i;
            this.gH[iA2] = e;
            return;
        }
        if (this.gF && this.mSize >= this.gU.length) {
            gc();
            iA2 = e.a(this.gU, this.mSize, i) ^ (-1);
        }
        if (this.mSize >= this.gU.length) {
            int iIdealIntArraySize = e.idealIntArraySize(this.mSize + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr = new Object[iIdealIntArraySize];
            System.arraycopy(this.gU, 0, iArr, 0, this.gU.length);
            System.arraycopy(this.gH, 0, objArr, 0, this.gH.length);
            this.gU = iArr;
            this.gH = objArr;
        }
        if (this.mSize - iA2 != 0) {
            System.arraycopy(this.gU, iA2, this.gU, iA2 + 1, this.mSize - iA2);
            System.arraycopy(this.gH, iA2, this.gH, iA2 + 1, this.mSize - iA2);
        }
        this.gU[iA2] = i;
        this.gH[iA2] = e;
        this.mSize++;
    }

    public int size() {
        if (this.gF) {
            gc();
        }
        return this.mSize;
    }

    public int keyAt(int i) {
        if (this.gF) {
            gc();
        }
        return this.gU[i];
    }

    public E valueAt(int i) {
        if (this.gF) {
            gc();
        }
        return (E) this.gH[i];
    }

    public int indexOfKey(int i) {
        if (this.gF) {
            gc();
        }
        return e.a(this.gU, this.mSize, i);
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.gH;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.gF = false;
    }

    public void append(int i, E e) {
        if (this.mSize != 0 && i <= this.gU[this.mSize - 1]) {
            put(i, e);
            return;
        }
        if (this.gF && this.mSize >= this.gU.length) {
            gc();
        }
        int i2 = this.mSize;
        if (i2 >= this.gU.length) {
            int iIdealIntArraySize = e.idealIntArraySize(i2 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr = new Object[iIdealIntArraySize];
            System.arraycopy(this.gU, 0, iArr, 0, this.gU.length);
            System.arraycopy(this.gH, 0, objArr, 0, this.gH.length);
            this.gU = iArr;
            this.gH = objArr;
        }
        this.gU[i2] = i;
        this.gH[i2] = e;
        this.mSize = i2 + 1;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
