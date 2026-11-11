package com.google.a.f.b;

import java.lang.reflect.Array;

/* JADX INFO: compiled from: ByteMatrix.java */
/* JADX INFO: loaded from: classes.dex */
public final class b {
    private final byte[][] cdq;
    private final int height;
    private final int width;

    public b(int i, int i2) {
        this.cdq = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i2, i);
        this.width = i;
        this.height = i2;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public byte cF(int i, int i2) {
        return this.cdq[i2][i];
    }

    public byte[][] ahy() {
        return this.cdq;
    }

    public void set(int i, int i2, int i3) {
        this.cdq[i2][i] = (byte) i3;
    }

    public void set(int i, int i2, boolean z) {
        this.cdq[i2][i] = (byte) (z ? 1 : 0);
    }

    public void e(byte b) {
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                this.cdq[i][i2] = b;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.width * 2 * this.height) + 2);
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                switch (this.cdq[i][i2]) {
                    case 0:
                        sb.append(" 0");
                        break;
                    case 1:
                        sb.append(" 1");
                        break;
                    default:
                        sb.append("  ");
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
