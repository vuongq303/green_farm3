package com.quanhoang.greenfarm3.S800x480;

import com.facebook.appevents.AppEventsConstants;

/* JADX INFO: compiled from: Digest.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    private static final int[] Ll = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int KX;
    private int KY;
    private int KZ;
    private int La;
    private byte[] Lb;
    private int Lc;
    private long Ld;
    private int[] Le;
    private int Lf;
    private int Lg;
    private int Lh;
    private int Li;
    private int Lj;
    private int Lk;

    public k(int i) {
        this.KX = i;
        if (this.KX == 0) {
            this.Le = new int[80];
        } else if (this.KX == 1) {
            this.Le = new int[64];
        }
        this.Lb = new byte[4];
        this.Lc = 0;
        ip();
    }

    public int im() {
        if (this.KX == 0) {
            return 20;
        }
        if (this.KX == 1) {
            return 32;
        }
        return -1;
    }

    private void e(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = (bArr[i] << 24) | ((bArr[i2] & 255) << 16);
        int i4 = i2 + 1;
        this.Le[this.Lf] = i3 | ((bArr[i4] & 255) << 8) | (bArr[i4 + 1] & 255);
        int i5 = this.Lf + 1;
        this.Lf = i5;
        if (i5 == 16) {
            in();
        }
    }

    private void in() {
        if (this.KX == 0) {
            ir();
        } else if (this.KX == 1) {
            iq();
        }
    }

    private void o(long j) {
        if (this.Lf > 14) {
            in();
        }
        this.Le[14] = (int) (j >>> 32);
        this.Le[15] = (int) ((-1) & j);
    }

    public int f(byte[] bArr, int i) {
        if (this.KX == 0) {
            return h(bArr, i);
        }
        if (this.KX == 1) {
            return g(bArr, i);
        }
        return -1;
    }

    public int g(byte[] bArr, int i) {
        io();
        GLLib.b(this.Lg, bArr, i);
        GLLib.b(this.Lh, bArr, i + 4);
        GLLib.b(this.Li, bArr, i + 8);
        GLLib.b(this.Lj, bArr, i + 12);
        GLLib.b(this.Lk, bArr, i + 16);
        GLLib.b(this.KY, bArr, i + 20);
        GLLib.b(this.KZ, bArr, i + 24);
        GLLib.b(this.La, bArr, i + 28);
        ip();
        return 32;
    }

    public int h(byte[] bArr, int i) {
        io();
        GLLib.b(this.Lg, bArr, i);
        GLLib.b(this.Lh, bArr, i + 4);
        GLLib.b(this.Li, bArr, i + 8);
        GLLib.b(this.Lj, bArr, i + 12);
        GLLib.b(this.Lk, bArr, i + 16);
        ip();
        return 20;
    }

    public void io() {
        long j = this.Ld << 3;
        c((byte) -128);
        while (this.Lc != 0) {
            c((byte) 0);
        }
        o(j);
        in();
    }

    public void ip() {
        this.Ld = 0L;
        this.Lc = 0;
        for (int i = 0; i < this.Lb.length; i++) {
            this.Lb[i] = 0;
        }
        if (this.KX == 0) {
            this.Lg = 1732584193;
            this.Lh = -271733879;
            this.Li = -1732584194;
            this.Lj = 271733878;
            this.Lk = -1009589776;
        } else if (this.KX == 1) {
            this.Lg = 1779033703;
            this.Lh = -1150833019;
            this.Li = 1013904242;
            this.Lj = -1521486534;
            this.Lk = 1359893119;
            this.KY = -1694144372;
            this.KZ = 528734635;
            this.La = 1541459225;
        }
        ca(this.Le.length);
    }

    public String b(byte[] bArr) {
        String str;
        String str2 = "";
        int i = 0;
        while (i < bArr.length) {
            if ((bArr[i] & 255) >= 15) {
                str = str2 + Integer.toHexString(bArr[i] & 255);
            } else {
                str = str2 + AppEventsConstants.EVENT_PARAM_VALUE_NO + Integer.toHexString(bArr[i] & 255);
            }
            i++;
            str2 = str;
        }
        return str2;
    }

    private int f(int i, int i2, int i3) {
        return (i & i2) | ((i ^ (-1)) & i3);
    }

    private int g(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private int h(int i, int i2, int i3) {
        return (i & i2) | (i & i3) | (i2 & i3);
    }

    protected void iq() {
        int i = 0;
        for (int i2 = 16; i2 <= 63; i2++) {
            this.Le[i2] = ce(this.Le[i2 - 2]) + this.Le[i2 - 7] + cd(this.Le[i2 - 15]) + this.Le[i2 - 16];
        }
        int iCb = this.Lg;
        int iCb2 = this.Lh;
        int iCb3 = this.Li;
        int iCb4 = this.Lj;
        int i3 = this.Lk;
        int i4 = this.KY;
        int i5 = this.KZ;
        int i6 = this.La;
        int i7 = 0;
        while (true) {
            int i8 = i;
            if (i7 < 8) {
                int iCc = cc(i3) + i(i3, i4, i5) + Ll[i8] + this.Le[i8] + i6;
                int i9 = iCb4 + iCc;
                int iCb5 = iCc + cb(iCb) + j(iCb, iCb2, iCb3);
                int i10 = i8 + 1;
                int iCc2 = i5 + cc(i9) + i(i9, i3, i4) + Ll[i10] + this.Le[i10];
                int i11 = iCb3 + iCc2;
                int iCb6 = iCc2 + cb(iCb5) + j(iCb5, iCb, iCb2);
                int i12 = i10 + 1;
                int iCc3 = i4 + cc(i11) + i(i11, i9, i3) + Ll[i12] + this.Le[i12];
                int i13 = iCb2 + iCc3;
                int iCb7 = iCc3 + cb(iCb6) + j(iCb6, iCb5, iCb);
                int i14 = i12 + 1;
                int iCc4 = i3 + cc(i13) + i(i13, i11, i9) + Ll[i14] + this.Le[i14];
                int i15 = iCb + iCc4;
                int iCb8 = iCc4 + cb(iCb7) + j(iCb7, iCb6, iCb5);
                int i16 = i14 + 1;
                int iCc5 = cc(i15) + i(i15, i13, i11) + Ll[i16] + this.Le[i16] + i9;
                i6 = iCb5 + iCc5;
                iCb4 = iCc5 + cb(iCb8) + j(iCb8, iCb7, iCb6);
                int i17 = i16 + 1;
                int iCc6 = cc(i6) + i(i6, i15, i13) + Ll[i17] + this.Le[i17] + i11;
                i5 = iCb6 + iCc6;
                iCb3 = cb(iCb4) + j(iCb4, iCb8, iCb7) + iCc6;
                int i18 = i17 + 1;
                int iCc7 = cc(i5) + i(i5, i6, i15) + Ll[i18] + this.Le[i18] + i13;
                i4 = iCb7 + iCc7;
                iCb2 = cb(iCb3) + j(iCb3, iCb4, iCb8) + iCc7;
                int i19 = i18 + 1;
                int iCc8 = cc(i4) + i(i4, i5, i6) + Ll[i19] + this.Le[i19] + i15;
                i3 = iCb8 + iCc8;
                iCb = cb(iCb2) + j(iCb2, iCb3, iCb4) + iCc8;
                i = i19 + 1;
                i7++;
            } else {
                this.Lg += iCb;
                this.Lh += iCb2;
                this.Li += iCb3;
                this.Lj += iCb4;
                this.Lk += i3;
                this.KY += i4;
                this.KZ += i5;
                this.La += i6;
                ca(16);
                return;
            }
        }
    }

    protected void ir() {
        for (int i = 16; i < 80; i++) {
            int i2 = ((this.Le[i - 3] ^ this.Le[i - 8]) ^ this.Le[i - 14]) ^ this.Le[i - 16];
            this.Le[i] = (i2 >>> 31) | (i2 << 1);
        }
        int iG = this.Lg;
        int iG2 = this.Lh;
        int i3 = this.Li;
        int i4 = this.Lj;
        int i5 = this.Lk;
        int i6 = 0;
        for (int i7 = 0; i7 < 4; i7++) {
            int i8 = i6 + 1;
            int iF = this.Le[i6] + ((iG << 5) | (iG >>> 27)) + f(iG2, i3, i4) + 1518500249 + i5;
            int i9 = (iG2 >>> 2) | (iG2 << 30);
            int i10 = i8 + 1;
            int iF2 = i4 + ((iF << 5) | (iF >>> 27)) + f(iG, i9, i3) + this.Le[i8] + 1518500249;
            int i11 = (iG >>> 2) | (iG << 30);
            int i12 = i10 + 1;
            int iF3 = i3 + ((iF2 << 5) | (iF2 >>> 27)) + f(iF, i11, i9) + this.Le[i10] + 1518500249;
            i5 = (iF << 30) | (iF >>> 2);
            int i13 = i12 + 1;
            iG2 = i9 + ((iF3 << 5) | (iF3 >>> 27)) + f(iF2, i5, i11) + this.Le[i12] + 1518500249;
            i4 = (iF2 >>> 2) | (iF2 << 30);
            i6 = i13 + 1;
            iG = i11 + f(iF3, i4, i5) + ((iG2 << 5) | (iG2 >>> 27)) + this.Le[i13] + 1518500249;
            i3 = (iF3 >>> 2) | (iF3 << 30);
        }
        for (int i14 = 0; i14 < 4; i14++) {
            int i15 = i6 + 1;
            int iG3 = this.Le[i6] + ((iG << 5) | (iG >>> 27)) + g(iG2, i3, i4) + 1859775393 + i5;
            int i16 = (iG2 >>> 2) | (iG2 << 30);
            int i17 = i15 + 1;
            int iG4 = i4 + ((iG3 << 5) | (iG3 >>> 27)) + g(iG, i16, i3) + this.Le[i15] + 1859775393;
            int i18 = (iG >>> 2) | (iG << 30);
            int i19 = i17 + 1;
            int iG5 = i3 + ((iG4 << 5) | (iG4 >>> 27)) + g(iG3, i18, i16) + this.Le[i17] + 1859775393;
            i5 = (iG3 << 30) | (iG3 >>> 2);
            int i20 = i19 + 1;
            iG2 = i16 + ((iG5 << 5) | (iG5 >>> 27)) + g(iG4, i5, i18) + this.Le[i19] + 1859775393;
            i4 = (iG4 >>> 2) | (iG4 << 30);
            i6 = i20 + 1;
            iG = i18 + g(iG5, i4, i5) + ((iG2 << 5) | (iG2 >>> 27)) + this.Le[i20] + 1859775393;
            i3 = (iG5 >>> 2) | (iG5 << 30);
        }
        for (int i21 = 0; i21 < 4; i21++) {
            int i22 = i6 + 1;
            int iH = ((this.Le[i6] + (((iG << 5) | (iG >>> 27)) + h(iG2, i3, i4))) - 1894007588) + i5;
            int i23 = (iG2 >>> 2) | (iG2 << 30);
            int i24 = i22 + 1;
            int iH2 = i4 + (((((iH << 5) | (iH >>> 27)) + h(iG, i23, i3)) + this.Le[i22]) - 1894007588);
            int i25 = (iG >>> 2) | (iG << 30);
            int i26 = i24 + 1;
            int iH3 = i3 + (((((iH2 << 5) | (iH2 >>> 27)) + h(iH, i25, i23)) + this.Le[i24]) - 1894007588);
            i5 = (iH << 30) | (iH >>> 2);
            int i27 = i26 + 1;
            iG2 = i23 + (((((iH3 << 5) | (iH3 >>> 27)) + h(iH2, i5, i25)) + this.Le[i26]) - 1894007588);
            i4 = (iH2 >>> 2) | (iH2 << 30);
            i6 = i27 + 1;
            iG = i25 + (((h(iH3, i4, i5) + ((iG2 << 5) | (iG2 >>> 27))) + this.Le[i27]) - 1894007588);
            i3 = (iH3 >>> 2) | (iH3 << 30);
        }
        for (int i28 = 0; i28 <= 3; i28++) {
            int i29 = i6 + 1;
            int iG6 = ((this.Le[i6] + (((iG << 5) | (iG >>> 27)) + g(iG2, i3, i4))) - 899497514) + i5;
            int i30 = (iG2 >>> 2) | (iG2 << 30);
            int i31 = i29 + 1;
            int iG7 = i4 + (((((iG6 << 5) | (iG6 >>> 27)) + g(iG, i30, i3)) + this.Le[i29]) - 899497514);
            int i32 = (iG >>> 2) | (iG << 30);
            int i33 = i31 + 1;
            int iG8 = i3 + (((((iG7 << 5) | (iG7 >>> 27)) + g(iG6, i32, i30)) + this.Le[i31]) - 899497514);
            i5 = (iG6 << 30) | (iG6 >>> 2);
            int i34 = i33 + 1;
            iG2 = i30 + (((((iG8 << 5) | (iG8 >>> 27)) + g(iG7, i5, i32)) + this.Le[i33]) - 899497514);
            i4 = (iG7 >>> 2) | (iG7 << 30);
            i6 = i34 + 1;
            iG = i32 + (((g(iG8, i4, i5) + ((iG2 << 5) | (iG2 >>> 27))) + this.Le[i34]) - 899497514);
            i3 = (iG8 >>> 2) | (iG8 << 30);
        }
        this.Lg += iG;
        this.Lh += iG2;
        this.Li += i3;
        this.Lj += i4;
        this.Lk += i5;
        ca(16);
    }

    private void ca(int i) {
        this.Lf = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            this.Le[i2] = 0;
        }
    }

    public void c(byte b) {
        byte[] bArr = this.Lb;
        int i = this.Lc;
        this.Lc = i + 1;
        bArr[i] = b;
        if (this.Lc == this.Lb.length) {
            e(this.Lb, 0);
            this.Lc = 0;
        }
        this.Ld++;
    }

    public void b(byte[] bArr, int i, int i2) {
        while (this.Lc != 0 && i2 > 0) {
            c(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.Lb.length) {
            e(bArr, i);
            i += this.Lb.length;
            i2 -= this.Lb.length;
            this.Ld += (long) this.Lb.length;
        }
        while (i2 > 0) {
            c(bArr[i]);
            i++;
            i2--;
        }
    }

    private int i(int i, int i2, int i3) {
        return (i & i2) ^ ((i ^ (-1)) & i3);
    }

    private int j(int i, int i2, int i3) {
        return ((i & i2) ^ (i & i3)) ^ (i2 & i3);
    }

    private int cb(int i) {
        return (((i >>> 2) | (i << 30)) ^ ((i >>> 13) | (i << 19))) ^ ((i >>> 22) | (i << 10));
    }

    private int cc(int i) {
        return (((i >>> 6) | (i << 26)) ^ ((i >>> 11) | (i << 21))) ^ ((i >>> 25) | (i << 7));
    }

    private int cd(int i) {
        return (((i >>> 7) | (i << 25)) ^ ((i >>> 18) | (i << 14))) ^ (i >>> 3);
    }

    private int ce(int i) {
        return (((i >>> 17) | (i << 15)) ^ ((i >>> 19) | (i << 13))) ^ (i >>> 10);
    }
}
