package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: LinkedHash.java */
/* JADX INFO: loaded from: classes.dex */
class bd {
    private short[] aiS;
    private int[] aiT;
    private int[] aiU;
    private int size;

    public bd(int i) {
        if (i > 36863) {
            throw new RuntimeException("invalid table size");
        }
        this.aiS = new short[(int) (((double) i) * 1.333d)];
        this.aiT = new int[i];
        this.aiU = new int[i];
        reset();
    }

    void reset() {
        for (int i = 0; i < this.aiS.length; i++) {
            this.aiS[i] = -1;
        }
        this.size = 0;
    }

    private int eJ(int i) {
        return Math.abs((((((((i & 255) + 31) * 31) + ((i >>> 8) & 255)) * 31) + ((i >>> 16) & 255)) * 31) + ((i >>> 24) & 255)) % this.aiS.length;
    }

    private int eK(int i) {
        int iEJ = eJ(i);
        int i2 = 1;
        while (true) {
            short s = this.aiS[iEJ];
            if (s < 0 || this.aiT[s] == i) {
                break;
            }
            iEJ = Math.abs(iEJ + (i2 * i2)) % this.aiS.length;
            i2++;
        }
        return iEJ;
    }

    int a(int i, byte b, int i2) {
        int i3 = ((65535 & i) << 8) | (b & 255);
        if (i2 >= 0) {
            int iEK = eK(i3);
            if (this.aiS[iEK] < 0 && this.size != this.aiU.length) {
                this.aiS[iEK] = (short) this.size;
                this.aiT[this.size] = i3;
                this.aiU[this.size] = i2;
                this.size++;
            }
        }
        return i3;
    }

    int f(byte[] bArr, int i, int i2) {
        if (i2 > 2) {
            short s = this.aiS[eK(0 | ((bArr[i] & 255) << 16) | ((bArr[i + 1] & 255) << 8) | (bArr[i + 2] & 255))];
            if (s >= 0) {
                return this.aiU[s];
            }
        }
        return -1;
    }
}
