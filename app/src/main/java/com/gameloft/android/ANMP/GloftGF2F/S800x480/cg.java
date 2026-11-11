package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: ZStream.java */
/* JADX INFO: loaded from: classes.dex */
class cg {
    static final int[] alm = new int[256];
    private boolean ali;
    private byte[] alj;
    private int alk;
    private int all;
    private boolean aln;
    private int alo;
    private boolean alp;
    private int alq;
    private int alr;
    private int bufferSize;
    private InputStream in;
    private OutputStream out;
    private int size;

    public cg(boolean z, int i) {
        this.alo = -1;
        this.aln = z;
        if (i > 0) {
            this.ali = true;
            this.alk = (1 << i) - 1;
            this.alj = new byte[1 << i];
        }
    }

    public int getSize() {
        return this.size;
    }

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                if ((i2 & 1) != 0) {
                    i2 = (i2 >>> 1) ^ (-306674912);
                } else {
                    i2 >>>= 1;
                }
            }
            alm[i] = i2;
        }
    }

    public void ar(boolean z) {
        this.aln = z;
    }

    void oC() {
        this.alo = -1;
        this.aln = true;
    }

    int oD() {
        return this.alo ^ (-1);
    }

    private void d(byte b) {
        int i = this.alo;
        this.alo = (i >>> 8) ^ alm[(i & 255) ^ (b & 255)];
    }

    public cg(OutputStream outputStream, boolean z, int i) {
        this(z, i);
        this.out = outputStream;
    }

    private void eR(int i) throws IOException {
        this.out.write(i);
        this.size++;
        if (this.aln) {
            d((byte) i);
        }
        if (this.ali) {
            this.alj[this.all] = (byte) i;
            this.all = (this.all + 1) & this.alk;
            if (this.bufferSize < this.alj.length) {
                this.bufferSize++;
            }
        }
    }

    void write(int i) throws IOException {
        if (this.alp) {
            throw new RuntimeException("Unaligned byte");
        }
        eR(i);
    }

    void as(int i, int i2) throws IOException {
        for (int i3 = 0; i3 < i2; i3++) {
            write((i >>> (i3 * 8)) & 255);
        }
    }

    void at(int i, int i2) throws IOException {
        if (!this.ali) {
            throw new RuntimeException("buffer unavailable");
        }
        if (i > this.bufferSize) {
            throw new RuntimeException("invalid distance");
        }
        int length = this.alk & ((this.all - i) + this.alj.length);
        for (int i3 = 0; i3 < i2; i3++) {
            write(this.alj[(length + i3) & this.alk]);
        }
    }

    void flush() throws IOException {
        this.out.flush();
    }

    public cg(InputStream inputStream, boolean z, int i) {
        this(z, i);
        this.in = inputStream;
    }

    private int oE() throws IOException {
        int i = this.in.read();
        if (i >= 0) {
            this.size++;
            if (this.aln) {
                d((byte) i);
            }
            if (this.ali) {
                this.alj[this.all] = (byte) i;
                this.all = (this.all + 1) & this.alk;
                if (this.bufferSize < this.alj.length) {
                    this.bufferSize++;
                }
            }
        }
        return i;
    }

    int read() {
        if (this.alp) {
            throw new RuntimeException("Unaligned byte");
        }
        return oE();
    }

    int read(byte[] bArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = read();
            if (i4 < 0) {
                return i3;
            }
            bArr[i + i3] = (byte) i4;
        }
        return i2;
    }

    String oF() {
        StringBuffer stringBuffer = new StringBuffer(128);
        while (true) {
            int i = read();
            if (i > 0) {
                stringBuffer.append((char) i);
            } else {
                return stringBuffer.toString();
            }
        }
    }

    private static void eS(int i) throws IOException {
        if (i < 0) {
            throw new IOException("Unexpected EOF.");
        }
    }

    void eT(int i) throws IOException {
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                eS(read());
                i = i2;
            } else {
                return;
            }
        }
    }

    int eU(int i) throws IOException {
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = read();
            eS(i4);
            int i5 = (i4 << (i2 << 3)) | i3;
            i2++;
            i3 = i5;
        }
        return i3;
    }

    void oG() throws IOException {
        eV(this.alr);
        this.alp = false;
    }

    int eV(int i) throws IOException {
        this.alp = true;
        while (this.alr < i) {
            int iOE = oE();
            if (iOE < 0) {
                eS(iOE);
            }
            this.alq = (iOE << this.alr) | this.alq;
            this.alr += 8;
        }
        int i2 = ((1 << i) - 1) & this.alq;
        this.alq >>>= i;
        this.alr -= i;
        return i2;
    }

    void au(int i, int i2) throws IOException {
        this.alp = true;
        this.alq = ((((1 << i2) - 1) & i) << this.alr) | this.alq;
        this.alr += i2;
        while (this.alr > 8) {
            eR((byte) this.alq);
            this.size++;
            this.alq >>>= 8;
            this.alr -= 8;
        }
    }

    void oH() throws IOException {
        while (this.alr > 0) {
            eR(this.alq & 255);
            this.size++;
            this.alq >>>= 8;
            this.alr -= 8;
        }
        this.alp = false;
        flush();
    }
}
