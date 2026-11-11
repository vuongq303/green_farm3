package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzabf implements Cloneable {
    private static final zzabg zzbzl = new zzabg();
    private int mSize;
    private boolean zzbzm;
    private int[] zzbzn;
    private zzabg[] zzbzo;

    zzabf() {
        this(10);
    }

    private zzabf(int i) {
        this.zzbzm = false;
        int iIdealIntArraySize = idealIntArraySize(i);
        this.zzbzn = new int[iIdealIntArraySize];
        this.zzbzo = new zzabg[iIdealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        for (int i3 = 4; i3 < 32; i3++) {
            if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            }
        }
        return i2 / 4;
    }

    private final int zzax(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzbzn[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else {
                if (i5 <= i) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return i2 ^ (-1);
    }

    public final /* synthetic */ Object clone() {
        int i = this.mSize;
        zzabf zzabfVar = new zzabf(i);
        System.arraycopy(this.zzbzn, 0, zzabfVar.zzbzn, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzbzo[i2] != null) {
                zzabfVar.zzbzo[i2] = (zzabg) this.zzbzo[i2].clone();
            }
        }
        zzabfVar.mSize = i;
        return zzabfVar;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzabf)) {
            return false;
        }
        zzabf zzabfVar = (zzabf) obj;
        if (this.mSize != zzabfVar.mSize) {
            return false;
        }
        int[] iArr = this.zzbzn;
        int[] iArr2 = zzabfVar.zzbzn;
        int i = this.mSize;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            }
            if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            }
            i2++;
        }
        if (z) {
            zzabg[] zzabgVarArr = this.zzbzo;
            zzabg[] zzabgVarArr2 = zzabfVar.zzbzo;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                }
                if (!zzabgVarArr[i4].equals(zzabgVarArr2[i4])) {
                    z2 = false;
                    break;
                }
                i4++;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = 17;
        for (int i = 0; i < this.mSize; i++) {
            iHashCode = (((iHashCode * 31) + this.zzbzn[i]) * 31) + this.zzbzo[i].hashCode();
        }
        return iHashCode;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, zzabg zzabgVar) {
        int iZzax = zzax(i);
        if (iZzax >= 0) {
            this.zzbzo[iZzax] = zzabgVar;
            return;
        }
        int i2 = iZzax ^ (-1);
        if (i2 < this.mSize && this.zzbzo[i2] == zzbzl) {
            this.zzbzn[i2] = i;
            this.zzbzo[i2] = zzabgVar;
            return;
        }
        if (this.mSize >= this.zzbzn.length) {
            int iIdealIntArraySize = idealIntArraySize(this.mSize + 1);
            int[] iArr = new int[iIdealIntArraySize];
            zzabg[] zzabgVarArr = new zzabg[iIdealIntArraySize];
            System.arraycopy(this.zzbzn, 0, iArr, 0, this.zzbzn.length);
            System.arraycopy(this.zzbzo, 0, zzabgVarArr, 0, this.zzbzo.length);
            this.zzbzn = iArr;
            this.zzbzo = zzabgVarArr;
        }
        if (this.mSize - i2 != 0) {
            System.arraycopy(this.zzbzn, i2, this.zzbzn, i2 + 1, this.mSize - i2);
            System.arraycopy(this.zzbzo, i2, this.zzbzo, i2 + 1, this.mSize - i2);
        }
        this.zzbzn[i2] = i;
        this.zzbzo[i2] = zzabgVar;
        this.mSize++;
    }

    final zzabg zzav(int i) {
        int iZzax = zzax(i);
        if (iZzax < 0 || this.zzbzo[iZzax] == zzbzl) {
            return null;
        }
        return this.zzbzo[iZzax];
    }

    final zzabg zzaw(int i) {
        return this.zzbzo[i];
    }
}
