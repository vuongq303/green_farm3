package com.google.a.b;

import java.util.Arrays;

/* JADX INFO: compiled from: BitMatrix.java */
/* JADX INFO: loaded from: classes.dex */
public final class b implements Cloneable {
    private final int[] cal;
    private final int cam;
    private final int height;
    private final int width;

    public b(int i) {
        this(i, i);
    }

    public b(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i;
        this.height = i2;
        this.cam = (i + 31) / 32;
        this.cal = new int[this.cam * i2];
    }

    private b(int i, int i2, int i3, int[] iArr) {
        this.width = i;
        this.height = i2;
        this.cam = i3;
        this.cal = iArr;
    }

    public boolean cw(int i, int i2) {
        return ((this.cal[(this.cam * i2) + (i / 32)] >>> (i & 31)) & 1) != 0;
    }

    public void set(int i, int i2) {
        int i3 = (this.cam * i2) + (i / 32);
        int[] iArr = this.cal;
        iArr[i3] = iArr[i3] | (1 << (i & 31));
    }

    public void clear() {
        int length = this.cal.length;
        for (int i = 0; i < length; i++) {
            this.cal[i] = 0;
        }
    }

    public void K(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i4 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int i5 = i + i3;
        int i6 = i2 + i4;
        if (i6 > this.height || i5 > this.width) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (i2 < i6) {
            int i7 = i2 * this.cam;
            for (int i8 = i; i8 < i5; i8++) {
                int[] iArr = this.cal;
                int i9 = (i8 / 32) + i7;
                iArr[i9] = iArr[i9] | (1 << (i8 & 31));
            }
            i2++;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.width == bVar.width && this.height == bVar.height && this.cam == bVar.cam && Arrays.equals(this.cal, bVar.cal);
    }

    public int hashCode() {
        return (((((((this.width * 31) + this.width) * 31) + this.height) * 31) + this.cam) * 31) + Arrays.hashCode(this.cal);
    }

    public String toString() {
        return az("X ", "  ");
    }

    public String az(String str, String str2) {
        return E(str, str2, "\n");
    }

    private String E(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(this.height * (this.width + 1));
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                sb.append(cw(i2, i) ? str : str2);
            }
            sb.append(str3);
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: agC, reason: merged with bridge method [inline-methods] */
    public b clone() {
        return new b(this.width, this.height, this.cam, (int[]) this.cal.clone());
    }
}
