package com.google.a.c.a;

import java.util.Arrays;

/* JADX INFO: compiled from: DefaultPlacement.java */
/* JADX INFO: loaded from: classes.dex */
public class e {
    private final CharSequence cbk;
    private final int cbl;
    private final int cbm;
    private final byte[] cbn;

    public e(CharSequence charSequence, int i, int i2) {
        this.cbk = charSequence;
        this.cbm = i;
        this.cbl = i2;
        this.cbn = new byte[i * i2];
        Arrays.fill(this.cbn, (byte) -1);
    }

    public final boolean cB(int i, int i2) {
        return this.cbn[(this.cbm * i2) + i] == 1;
    }

    private void s(int i, int i2, boolean z) {
        this.cbn[(this.cbm * i2) + i] = (byte) (z ? 1 : 0);
    }

    private boolean cC(int i, int i2) {
        return this.cbn[(this.cbm * i2) + i] >= 0;
    }

    public final void agK() {
        int i;
        int i2 = 0;
        int i3 = 4;
        int i4 = 0;
        while (true) {
            if (i3 == this.cbl && i2 == 0) {
                oK(i4);
                i4++;
            }
            if (i3 == this.cbl - 2 && i2 == 0 && this.cbm % 4 != 0) {
                oL(i4);
                i4++;
            }
            if (i3 == this.cbl - 2 && i2 == 0 && this.cbm % 8 == 4) {
                oM(i4);
                i4++;
            }
            if (i3 == this.cbl + 4 && i2 == 2 && this.cbm % 8 == 0) {
                oN(i4);
                i4++;
            }
            do {
                if (i3 < this.cbl && i2 >= 0 && !cC(i2, i3)) {
                    ad(i3, i2, i4);
                    i4++;
                }
                i3 -= 2;
                i2 += 2;
                if (i3 < 0) {
                    break;
                }
            } while (i2 < this.cbm);
            int i5 = i2 + 3;
            int i6 = i3 + 1;
            int i7 = i4;
            while (true) {
                if (i6 < 0 || i5 >= this.cbm || cC(i5, i6)) {
                    i4 = i7;
                } else {
                    i4 = i7 + 1;
                    ad(i6, i5, i7);
                }
                i6 += 2;
                i = i5 - 2;
                if (i6 >= this.cbl || i < 0) {
                    break;
                }
                i5 = i;
                i7 = i4;
            }
            int i8 = i6 + 3;
            i2 = i + 1;
            if (i8 >= this.cbl && i2 >= this.cbm) {
                break;
            } else {
                i3 = i8;
            }
        }
        if (!cC(this.cbm - 1, this.cbl - 1)) {
            s(this.cbm - 1, this.cbl - 1, true);
            s(this.cbm - 2, this.cbl - 2, true);
        }
    }

    private void L(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i < 0) {
            i6 = i + this.cbl;
            i5 = (4 - ((this.cbl + 4) % 8)) + i2;
        } else {
            i5 = i2;
            i6 = i;
        }
        if (i5 < 0) {
            i5 += this.cbm;
            i6 += 4 - ((this.cbm + 4) % 8);
        }
        s(i5, i6, (this.cbk.charAt(i3) & (1 << (8 - i4))) != 0);
    }

    private void ad(int i, int i2, int i3) {
        L(i - 2, i2 - 2, i3, 1);
        L(i - 2, i2 - 1, i3, 2);
        L(i - 1, i2 - 2, i3, 3);
        L(i - 1, i2 - 1, i3, 4);
        L(i - 1, i2, i3, 5);
        L(i, i2 - 2, i3, 6);
        L(i, i2 - 1, i3, 7);
        L(i, i2, i3, 8);
    }

    private void oK(int i) {
        L(this.cbl - 1, 0, i, 1);
        L(this.cbl - 1, 1, i, 2);
        L(this.cbl - 1, 2, i, 3);
        L(0, this.cbm - 2, i, 4);
        L(0, this.cbm - 1, i, 5);
        L(1, this.cbm - 1, i, 6);
        L(2, this.cbm - 1, i, 7);
        L(3, this.cbm - 1, i, 8);
    }

    private void oL(int i) {
        L(this.cbl - 3, 0, i, 1);
        L(this.cbl - 2, 0, i, 2);
        L(this.cbl - 1, 0, i, 3);
        L(0, this.cbm - 4, i, 4);
        L(0, this.cbm - 3, i, 5);
        L(0, this.cbm - 2, i, 6);
        L(0, this.cbm - 1, i, 7);
        L(1, this.cbm - 1, i, 8);
    }

    private void oM(int i) {
        L(this.cbl - 3, 0, i, 1);
        L(this.cbl - 2, 0, i, 2);
        L(this.cbl - 1, 0, i, 3);
        L(0, this.cbm - 2, i, 4);
        L(0, this.cbm - 1, i, 5);
        L(1, this.cbm - 1, i, 6);
        L(2, this.cbm - 1, i, 7);
        L(3, this.cbm - 1, i, 8);
    }

    private void oN(int i) {
        L(this.cbl - 1, 0, i, 1);
        L(this.cbl - 1, this.cbm - 1, i, 2);
        L(0, this.cbm - 3, i, 3);
        L(0, this.cbm - 2, i, 4);
        L(0, this.cbm - 1, i, 5);
        L(1, this.cbm - 3, i, 6);
        L(1, this.cbm - 2, i, 7);
        L(1, this.cbm - 1, i, 8);
    }
}
