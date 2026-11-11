package com.google.a.b.a;

/* JADX INFO: compiled from: GenericGF.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final a caT = new a(4201, 4096, 1);
    public static final a caU = new a(1033, 1024, 1);
    public static final a caV = new a(67, 64, 1);
    public static final a caW = new a(19, 16, 1);
    public static final a caX = new a(285, 256, 0);
    public static final a caY;
    public static final a caZ;
    public static final a cba;
    private final int[] cbb;
    private final int[] cbc;
    private final b cbd;
    private final b cbe;
    private final int cbf;
    private final int cbg;
    private final int size;

    static {
        a aVar = new a(301, 256, 1);
        caY = aVar;
        caZ = aVar;
        cba = caV;
    }

    public a(int i, int i2, int i3) {
        this.cbf = i;
        this.size = i2;
        this.cbg = i3;
        this.cbb = new int[i2];
        this.cbc = new int[i2];
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            this.cbb[i5] = i4;
            i4 <<= 1;
            if (i4 >= i2) {
                i4 = (i4 ^ i) & (i2 - 1);
            }
        }
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            this.cbc[this.cbb[i6]] = i6;
        }
        this.cbd = new b(this, new int[]{0});
        this.cbe = new b(this, new int[]{1});
    }

    b agE() {
        return this.cbd;
    }

    b cx(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.cbd;
        }
        int[] iArr = new int[i + 1];
        iArr[0] = i2;
        return new b(this, iArr);
    }

    static int cy(int i, int i2) {
        return i ^ i2;
    }

    int oE(int i) {
        return this.cbb[i];
    }

    int oF(int i) {
        if (i == 0) {
            throw new IllegalArgumentException();
        }
        return this.cbc[i];
    }

    int oG(int i) {
        if (i == 0) {
            throw new ArithmeticException();
        }
        return this.cbb[(this.size - this.cbc[i]) - 1];
    }

    int cz(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        return this.cbb[(this.cbc[i] + this.cbc[i2]) % (this.size - 1)];
    }

    public int agF() {
        return this.cbg;
    }

    public String toString() {
        return "GF(0x" + Integer.toHexString(this.cbf) + ',' + this.size + ')';
    }
}
