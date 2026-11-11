package com.google.a.b.a;

/* JADX INFO: compiled from: GenericGFPoly.java */
/* JADX INFO: loaded from: classes.dex */
final class b {
    private final a cbh;
    private final int[] cbi;

    b(a aVar, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.cbh = aVar;
        int length = iArr.length;
        if (length > 1 && iArr[0] == 0) {
            int i = 1;
            while (i < length && iArr[i] == 0) {
                i++;
            }
            if (i == length) {
                this.cbi = new int[]{0};
                return;
            } else {
                this.cbi = new int[length - i];
                System.arraycopy(iArr, i, this.cbi, 0, this.cbi.length);
                return;
            }
        }
        this.cbi = iArr;
    }

    int[] agG() {
        return this.cbi;
    }

    int agH() {
        return this.cbi.length - 1;
    }

    boolean isZero() {
        return this.cbi[0] == 0;
    }

    int oH(int i) {
        return this.cbi[(this.cbi.length - 1) - i];
    }

    b a(b bVar) {
        int[] iArr;
        int[] iArr2;
        if (!this.cbh.equals(bVar.cbh)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (!isZero()) {
            if (bVar.isZero()) {
                return this;
            }
            int[] iArr3 = this.cbi;
            int[] iArr4 = bVar.cbi;
            if (iArr3.length > iArr4.length) {
                iArr = iArr3;
                iArr2 = iArr4;
            } else {
                iArr = iArr4;
                iArr2 = iArr3;
            }
            int[] iArr5 = new int[iArr.length];
            int length = iArr.length - iArr2.length;
            System.arraycopy(iArr, 0, iArr5, 0, length);
            for (int i = length; i < iArr.length; i++) {
                iArr5[i] = a.cy(iArr2[i - length], iArr[i]);
            }
            return new b(this.cbh, iArr5);
        }
        return bVar;
    }

    b b(b bVar) {
        if (!this.cbh.equals(bVar.cbh)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (isZero() || bVar.isZero()) {
            return this.cbh.agE();
        }
        int[] iArr = this.cbi;
        int length = iArr.length;
        int[] iArr2 = bVar.cbi;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            for (int i3 = 0; i3 < length2; i3++) {
                iArr3[i + i3] = a.cy(iArr3[i + i3], this.cbh.cz(i2, iArr2[i3]));
            }
        }
        return new b(this.cbh, iArr3);
    }

    b cA(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.cbh.agE();
        }
        int length = this.cbi.length;
        int[] iArr = new int[length + i];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.cbh.cz(this.cbi[i3], i2);
        }
        return new b(this.cbh, iArr);
    }

    b[] c(b bVar) {
        if (!this.cbh.equals(bVar.cbh)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (bVar.isZero()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        b bVarAgE = this.cbh.agE();
        int iOG = this.cbh.oG(bVar.oH(bVar.agH()));
        b bVarA = this;
        while (bVarA.agH() >= bVar.agH() && !bVarA.isZero()) {
            int iAgH = bVarA.agH() - bVar.agH();
            int iCz = this.cbh.cz(bVarA.oH(bVarA.agH()), iOG);
            b bVarCA = bVar.cA(iAgH, iCz);
            bVarAgE = bVarAgE.a(this.cbh.cx(iAgH, iCz));
            bVarA = bVarA.a(bVarCA);
        }
        return new b[]{bVarAgE, bVarA};
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(agH() * 8);
        for (int iAgH = agH(); iAgH >= 0; iAgH--) {
            int iOH = oH(iAgH);
            if (iOH != 0) {
                if (iOH < 0) {
                    sb.append(" - ");
                    iOH = -iOH;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iAgH == 0 || iOH != 1) {
                    int iOF = this.cbh.oF(iOH);
                    if (iOF == 0) {
                        sb.append('1');
                    } else if (iOF == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(iOF);
                    }
                }
                if (iAgH != 0) {
                    if (iAgH == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iAgH);
                    }
                }
            }
        }
        return sb.toString();
    }
}
