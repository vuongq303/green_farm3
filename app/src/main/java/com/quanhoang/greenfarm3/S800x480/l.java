package com.quanhoang.greenfarm3.S800x480;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: EndianInputStream.java */
/* JADX INFO: loaded from: classes.dex */
public class l extends InputStream implements DataInput {
    public DataInputStream Lm;
    public InputStream Ln;
    private byte[] Lo = new byte[8];
    public int order;

    public void cf(int i) {
        this.order = i;
    }

    public l(InputStream inputStream) {
        this.Ln = inputStream;
        this.Lm = new DataInputStream(this.Ln);
    }

    @Override // java.io.InputStream
    public int available() {
        return this.Lm.available();
    }

    @Override // java.io.InputStream
    public int read() {
        return this.Ln.read();
    }

    @Override // java.io.DataInput
    public final float readFloat() {
        return this.order == 0 ? Float.intBitsToFloat(readInt()) : is();
    }

    @Override // java.io.DataInput
    public final double readDouble() {
        return this.order == 0 ? Double.longBitsToDouble(readLong()) : it();
    }

    @Override // java.io.DataInput
    public final short readShort() {
        return this.order == 0 ? this.Lm.readShort() : iu();
    }

    @Override // java.io.DataInput
    public final int readUnsignedShort() {
        return this.order == 0 ? this.Lm.readUnsignedShort() : ix();
    }

    @Override // java.io.DataInput
    public final long readLong() {
        return this.order == 0 ? this.Lm.readLong() : iw();
    }

    @Override // java.io.DataInput
    public final int readInt() {
        return this.order == 0 ? this.Lm.readInt() : iv();
    }

    private final float is() {
        return Float.intBitsToFloat(iv());
    }

    private final double it() {
        return Double.longBitsToDouble(iw());
    }

    private final short iu() throws IOException {
        this.Lm.readFully(this.Lo, 0, 2);
        return (short) (((this.Lo[1] & 255) << 8) | (this.Lo[0] & 255));
    }

    private final int iv() throws IOException {
        this.Lm.readFully(this.Lo, 0, 4);
        return (this.Lo[3] << 24) | ((this.Lo[2] & 255) << 16) | ((this.Lo[1] & 255) << 8) | (this.Lo[0] & 255);
    }

    private final long iw() throws IOException {
        this.Lm.readFully(this.Lo, 0, 8);
        return (((long) this.Lo[7]) << 56) | (((long) (this.Lo[6] & 255)) << 48) | (((long) (this.Lo[5] & 255)) << 40) | (((long) (this.Lo[4] & 255)) << 32) | (((long) (this.Lo[3] & 255)) << 24) | (((long) (this.Lo[2] & 255)) << 16) | (((long) (this.Lo[1] & 255)) << 8) | ((long) (this.Lo[0] & 255));
    }

    private final int ix() throws IOException {
        this.Lm.readFully(this.Lo, 0, 2);
        return ((this.Lo[1] & 255) << 8) | (this.Lo[0] & 255);
    }
}
