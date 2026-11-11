package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import java.util.Random;

/* JADX INFO: compiled from: SecureRandom.java */
/* JADX INFO: loaded from: classes.dex */
public class bs extends Random {
    private static long akg = 10;
    k akf;
    private long akh;
    private long aki;
    private byte[] akj;
    private byte[] akk;

    public bs() {
        super(0L);
        this.akf = new k(0);
        this.akk = new byte[this.akf.im()];
        this.aki = 1L;
        this.akj = new byte[this.akf.im()];
        this.akh = 1L;
        setSeed(w.Vl ? (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To : System.currentTimeMillis());
    }

    @Override // java.util.Random
    public void nextBytes(byte[] bArr) {
        h(bArr);
    }

    @Override // java.util.Random
    public void setSeed(long j) {
        if (j != 0) {
            t(j);
        }
    }

    @Override // java.util.Random
    public int nextInt() {
        byte[] bArr = new byte[4];
        nextBytes(bArr);
        int i = 0;
        int i2 = 0;
        while (i < 4) {
            int i3 = (i2 << 8) + (bArr[i] & 255);
            i++;
            i2 = i3;
        }
        return i2;
    }

    public void t(long j) {
        synchronized (this) {
            u(j);
            this.akf.b(this.akk, 0, this.akk.length);
            this.akf.f(this.akk, 0);
        }
    }

    public void h(byte[] bArr) {
        g(bArr, 0, bArr.length);
    }

    public void g(byte[] bArr, int i, int i2) {
        synchronized (this) {
            nR();
            int i3 = i + i2;
            int i4 = 0;
            while (i != i3) {
                if (i4 == this.akj.length) {
                    nR();
                    i4 = 0;
                }
                bArr[i] = this.akj[i4];
                i++;
                i4++;
            }
        }
    }

    private void nQ() {
        this.akf.b(this.akk, 0, this.akk.length);
        long j = this.aki;
        this.aki = 1 + j;
        u(j);
        this.akf.f(this.akk, 0);
    }

    private void nR() {
        long j = this.akh;
        this.akh = 1 + j;
        u(j);
        this.akf.b(this.akj, 0, this.akj.length);
        this.akf.b(this.akk, 0, this.akk.length);
        this.akf.f(this.akj, 0);
        if (this.akh % akg == 0) {
            nQ();
        }
    }

    private void u(long j) {
        for (int i = 0; i != 8; i++) {
            this.akf.c((byte) j);
            j >>>= 8;
        }
    }
}
