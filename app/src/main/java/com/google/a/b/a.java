package com.google.a.b;

import java.util.Arrays;

/* JADX INFO: compiled from: BitArray.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements Cloneable {
    private int[] cal;
    private int size;

    public a() {
        this.size = 0;
        this.cal = new int[1];
    }

    a(int[] iArr, int i) {
        this.cal = iArr;
        this.size = i;
    }

    public int getSize() {
        return this.size;
    }

    public int agA() {
        return (this.size + 7) / 8;
    }

    private void ensureCapacity(int i) {
        if (i > (this.cal.length << 5)) {
            int[] iArrOD = oD(i);
            System.arraycopy(this.cal, 0, iArrOD, 0, this.cal.length);
            this.cal = iArrOD;
        }
    }

    public boolean get(int i) {
        return (this.cal[i / 32] & (1 << (i & 31))) != 0;
    }

    public void cg(boolean z) {
        ensureCapacity(this.size + 1);
        if (z) {
            int[] iArr = this.cal;
            int i = this.size / 32;
            iArr[i] = iArr[i] | (1 << (this.size & 31));
        }
        this.size++;
    }

    public void cv(int i, int i2) {
        if (i2 < 0 || i2 > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        ensureCapacity(this.size + i2);
        while (i2 > 0) {
            cg(((i >> (i2 + (-1))) & 1) == 1);
            i2--;
        }
    }

    public void a(a aVar) {
        int i = aVar.size;
        ensureCapacity(this.size + i);
        for (int i2 = 0; i2 < i; i2++) {
            cg(aVar.get(i2));
        }
    }

    public void b(a aVar) {
        if (this.size != aVar.size) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i = 0; i < this.cal.length; i++) {
            int[] iArr = this.cal;
            iArr[i] = iArr[i] ^ aVar.cal[i];
        }
    }

    public void a(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = 0;
            for (int i7 = 0; i7 < 8; i7++) {
                if (get(i4)) {
                    i6 |= 1 << (7 - i7);
                }
                i4++;
            }
            bArr[i2 + i5] = (byte) i6;
        }
    }

    private static int[] oD(int i) {
        return new int[(i + 31) / 32];
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.size == aVar.size && Arrays.equals(this.cal, aVar.cal);
    }

    public int hashCode() {
        return (this.size * 31) + Arrays.hashCode(this.cal);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.size);
        for (int i = 0; i < this.size; i++) {
            if ((i & 7) == 0) {
                sb.append(' ');
            }
            sb.append(get(i) ? 'X' : '.');
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: agB, reason: merged with bridge method [inline-methods] */
    public a clone() {
        return new a((int[]) this.cal.clone(), this.size);
    }
}
