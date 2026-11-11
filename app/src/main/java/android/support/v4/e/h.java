package android.support.v4.e;

/* JADX INFO: compiled from: LongSparseArray.java */
/* JADX INFO: loaded from: classes.dex */
public class h<E> implements Cloneable {
    private static final Object gE = new Object();
    private boolean gF;
    private long[] gG;
    private Object[] gH;
    private int mSize;

    public h() {
        this(10);
    }

    public h(int i) {
        this.gF = false;
        if (i == 0) {
            this.gG = e.gC;
            this.gH = e.gD;
        } else {
            int iR = e.r(i);
            this.gG = new long[iR];
            this.gH = new Object[iR];
        }
        this.mSize = 0;
    }

    /* JADX INFO: renamed from: aL, reason: merged with bridge method [inline-methods] */
    public h<E> clone() {
        try {
            h<E> hVar = (h) super.clone();
            try {
                hVar.gG = (long[]) this.gG.clone();
                hVar.gH = (Object[]) this.gH.clone();
                return hVar;
            } catch (CloneNotSupportedException e) {
                return hVar;
            }
        } catch (CloneNotSupportedException e2) {
            return null;
        }
    }

    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        int iA = e.a(this.gG, this.mSize, j);
        return (iA < 0 || this.gH[iA] == gE) ? e : (E) this.gH[iA];
    }

    public void delete(long j) {
        int iA = e.a(this.gG, this.mSize, j);
        if (iA >= 0 && this.gH[iA] != gE) {
            this.gH[iA] = gE;
            this.gF = true;
        }
    }

    private void gc() {
        int i = this.mSize;
        long[] jArr = this.gG;
        Object[] objArr = this.gH;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != gE) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.gF = false;
        this.mSize = i2;
    }

    public void put(long j, E e) {
        int iA = e.a(this.gG, this.mSize, j);
        if (iA >= 0) {
            this.gH[iA] = e;
            return;
        }
        int iA2 = iA ^ (-1);
        if (iA2 < this.mSize && this.gH[iA2] == gE) {
            this.gG[iA2] = j;
            this.gH[iA2] = e;
            return;
        }
        if (this.gF && this.mSize >= this.gG.length) {
            gc();
            iA2 = e.a(this.gG, this.mSize, j) ^ (-1);
        }
        if (this.mSize >= this.gG.length) {
            int iR = e.r(this.mSize + 1);
            long[] jArr = new long[iR];
            Object[] objArr = new Object[iR];
            System.arraycopy(this.gG, 0, jArr, 0, this.gG.length);
            System.arraycopy(this.gH, 0, objArr, 0, this.gH.length);
            this.gG = jArr;
            this.gH = objArr;
        }
        if (this.mSize - iA2 != 0) {
            System.arraycopy(this.gG, iA2, this.gG, iA2 + 1, this.mSize - iA2);
            System.arraycopy(this.gH, iA2, this.gH, iA2 + 1, this.mSize - iA2);
        }
        this.gG[iA2] = j;
        this.gH[iA2] = e;
        this.mSize++;
    }

    public int size() {
        if (this.gF) {
            gc();
        }
        return this.mSize;
    }

    public long keyAt(int i) {
        if (this.gF) {
            gc();
        }
        return this.gG[i];
    }

    public E valueAt(int i) {
        if (this.gF) {
            gc();
        }
        return (E) this.gH[i];
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
