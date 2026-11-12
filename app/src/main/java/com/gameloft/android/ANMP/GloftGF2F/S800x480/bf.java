package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
public class bf extends a {
    int[] aiX;
    int[] aiY;
    int[] aiZ;
    boolean[] aja;
    boolean[] ajb;
    int aiW = 0;
    boolean ajc = true;
    int ajd = 0;
    int aje = 0;
    int ajf = 0;
    boolean ajg = false;
    a ajh = null;

    public bf(int i) {
        this.aiX = new int[i * 4];
        this.aiY = new int[i * 4];
        this.aiZ = new int[i * 4];
        this.aja = new boolean[i];
        this.ajb = new boolean[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.aja[i2] = false;
            this.ajb[i2] = false;
        }
    }

    public void ap(int i, int i2) {
        this.aje = i;
        this.ajf = i2;
    }

    public void b(a aVar) {
        this.ajh = aVar;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.a
    public void bQ(int i) {
        this.aiW = i;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.a
    public int bS(int i) {
        return this.aiW;
    }

    public void f(int i, int i2, int i3, int i4, int i5) {
        this.aiX[i * 4] = i2;
        this.aiX[(i * 4) + 1] = i3;
        this.aiX[(i * 4) + 2] = i4;
        this.aiX[(i * 4) + 3] = i5;
    }

    public void g(int i, int i2, int i3, int i4, int i5) {
        this.aiY[i * 4] = i2;
        this.aiY[(i * 4) + 1] = i3;
        this.aiY[(i * 4) + 2] = i4;
        this.aiY[(i * 4) + 3] = i5;
        this.aja[i] = true;
    }

    public void h(int i, int i2, int i3, int i4, int i5) {
        this.aiZ[i * 4] = i2;
        this.aiZ[(i * 4) + 1] = i3;
        this.aiZ[(i * 4) + 2] = i4;
        this.aiZ[(i * 4) + 3] = i5;
        this.ajb[i] = true;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.a
    public void hs() {
        super.hs();
        if (this.ajh != null) {
            this.ajh.hs();
        }
    }

    public void nt() {
        GLLib.m2do(1);
        if (this.ajd == 0) {
            GLLib.g(this.aiX[this.aiW * 4], this.aiX[(this.aiW * 4) + 1], this.aiX[(this.aiW * 4) + 2], this.aiX[(this.aiW * 4) + 3]);
        } else if (this.ajd == 1) {
            GLLib.g(this.aiY[this.aiW * 4], this.aiY[(this.aiW * 4) + 1], this.aiY[(this.aiW * 4) + 2], this.aiY[(this.aiW * 4) + 3]);
        } else {
            GLLib.g(this.aiZ[this.aiW * 4], this.aiZ[(this.aiW * 4) + 1], this.aiZ[(this.aiW * 4) + 2], this.aiZ[(this.aiW * 4) + 3]);
        }
    }

    public void nu() {
        GLLib.g(255, 255, 255, 255);
        GLLib.m2do(0);
    }

    /* JADX WARN: Code duplicated, block: B:140:0x029d  */
    /* JADX WARN: Code duplicated, block: B:143:0x02c6  */
    /* JADX WARN: Code duplicated, block: B:190:0x03de  */
    /* JADX WARN: Code duplicated, block: B:192:0x03e2  */
    /* JADX WARN: Code duplicated, block: B:200:0x0408  */
    /* JADX WARN: Code duplicated, block: B:201:0x040f  */
    /* JADX WARN: Code duplicated, block: B:202:0x0412  */
    /* JADX WARN: Code duplicated, block: B:203:0x0415 A[PHI: r14
  0x0415: PHI (r14v4 int) = (r14v1 int), (r14v5 int), (r14v5 int) binds: [B:188:0x03cd, B:160:0x0346, B:162:0x034c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:205:0x041e  */
    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.a
    public void a(aj ajVar, String str, char[] cArr, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        int length;
        a aVar;
        int iA;
        int i5;
        int i6;
        int iHA;
        int i7;
        int i8;
        int i9;
        boolean z4 = str != null;
        if (str == null && cArr == null) {
            return;
        }
        if (!z3) {
            if (!w.Zo || this.Jc < 0) {
                i2 += this.IT;
                if (w.XE && this.IN) {
                    i -= bw(this.GM[0] + 0);
                }
            } else {
                i2 += (this.IT * this.Jc) >> 8;
            }
            if (z2) {
                a(str, cArr);
            }
            if ((i3 & 43) != 0) {
                if (!z2) {
                    a(str, cArr);
                }
                if (!(w.XE && this.IN) && (!w.XF || this.IS == 0)) {
                    if ((i3 & 8) != 0) {
                        i -= IJ;
                    } else if ((i3 & 1) != 0) {
                        i -= IJ >> 1;
                    }
                    if ((i3 & 32) != 0) {
                        i2 -= IK;
                    } else if ((i3 & 2) != 0) {
                        i2 -= IK >> 1;
                    }
                } else {
                    if ((i3 & 8) != 0) {
                        i -= IL;
                        i2 += IM;
                    } else if ((i3 & 1) != 0) {
                        i -= IL >> 1;
                        i2 += IM >> 1;
                    }
                    if ((i3 & 32) != 0) {
                        i -= IJ - IL;
                        i2 -= IK - Math.abs(IM);
                    } else if ((i3 & 2) != 0) {
                        i -= (IJ - IL) >> 1;
                        i2 -= (IK - Math.abs(IM)) >> 1;
                    }
                }
            }
        }
        if (this.ajc) {
            if (z) {
                II = this.aiW;
            }
            int i10 = Je >= 0 ? Je : 0;
            if (z4) {
                length = Jf >= 0 ? Jf : str.length();
            } else {
                length = Jf >= 0 ? Jf : cArr.length;
            }
            int i11 = (Jg < 0 || length <= Jg) ? length : Jg;
            if (i11 > i10) {
                if (this.ajd == 0) {
                    if (this.ajb[this.aiW]) {
                        this.ajd = 2;
                        a(ajVar, str, cArr, i + this.aje, i2 + this.ajf, i3, -1, false, false, true);
                        this.ajd = 0;
                    }
                    if (!this.aja[this.aiW] || this.ajh == null) {
                        aVar = this;
                    } else {
                        this.ajd = 1;
                        a(ajVar, str, cArr, i, i2, i3, -1, false, false, true);
                        this.ajd = 0;
                        aVar = this;
                    }
                } else if (this.ajd != 1 && this.ajh == null) {
                    aVar = this;
                } else {
                    aVar = this.ajh;
                }
                if (aVar == null) {
                    String str2 = "WHY???? " + Integer.toString(this.ajd);
                }
                nt();
                int i12 = i10;
                int i13 = i2;
                int i14 = i2;
                int iHD = i;
                int i15 = i;
                while (i12 < i11) {
                    char cA = a(str, cArr, i12);
                    if (w.Xq && cA == '\\') {
                        i12++;
                        char cA2 = a(str, cArr, i12);
                        if (cA2 == '_') {
                            this.IU = !this.IU;
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        } else if (cA2 == '^') {
                            this.IV = !this.IV;
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        } else {
                            bQ((cA2 & 255) - 48);
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        }
                    } else if (cA == In) {
                        i6 = i13;
                        iHA = i14;
                        i7 = i15;
                    } else if (cA > ' ' || cA == 2) {
                        if (cA > ' ' && Io && cA == Ip) {
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        } else {
                            // Original char-2 escape joins the ordinary glyph at goto_26a.
                            if (cA == 2) {
                                i12++;
                                iA = a(str, cArr, i12);
                            } else {
                                iA = bN(cA);
                            }
                            i5 = i12;
                            if (!w.XA || iA <= hv()) {
                                i8 = iA;
                            } else {
                                GLLib.be("Character not available: c = " + iA);
                                i8 = 0;
                            }
                            aVar.a(ajVar, i8, (float) iHD, (float) i14, 0);
                            if (this.IU) {
                                int iBN = bN(95);
                                aVar.a(ajVar, iBN, (float) (((bP(i8) - bP(iBN)) >> 1) + iHD), (float) i14, 0);
                            }
                            if (this.IV) {
                                int i16 = iHD + 1;
                                aVar.a(ajVar, i8, (float) i16, (float) i14, 0);
                                i9 = i16;
                            } else {
                                i9 = iHD;
                            }
                            iHD = i9 + bP(i8) + hF();
                            if (w.XE && this.IN) {
                                int iBO = i14 + bO(i8);
                                i12 = i5;
                                i6 = i13;
                                iHA = iBO;
                                i7 = i15;
                            } else if (!w.XF || this.IS == 0) {
                                i12 = i5;
                                i6 = i13;
                                iHA = i14;
                                i7 = i15;
                            } else {
                                i12 = i5;
                                i6 = i13;
                                iHA = i14 + this.IS;
                                i7 = i15;
                            }
                        }
                    } else if (cA == ' ') {
                        if (this.IU) {
                            int iBN2 = bN(95);
                            aVar.a(ajVar, iBN2, (float) (((hD() - bP(iBN2)) >> 1) + iHD), (float) i14, 0);
                        }
                        iHD += hD();
                        if (w.XE && this.IN) {
                            i6 = i13;
                            iHA = i14 + bO(bN(cA));
                            i7 = i15;
                        } else if (!w.XF || this.IS == 0) {
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        } else {
                            i6 = i13;
                            iHA = i14 + this.IS;
                            i7 = i15;
                        }
                    } else if (cA == '\n') {
                        if (!(w.XE && this.IN) && (!w.XF || this.IS == 0)) {
                            i6 = i13;
                            iHA = i14 + hA() + hG();
                            iHD = i15;
                            i7 = i15;
                        } else {
                            int iHH = (w.XE && this.IN) ? hH() + i15 : i15;
                            int iHG = hG() + hA() + i13;
                            i6 = iHG;
                            iHA = iHG;
                            iHD = iHH;
                            i7 = iHH;
                        }
                    } else if (cA == 1) {
                        i12++;
                        char cA3 = a(str, cArr, i12);
                        if (cA3 == 255) {
                            bQ(II);
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        } else {
                            bQ(cA3);
                            i6 = i13;
                            iHA = i14;
                            i7 = i15;
                        }
                    } else {
                        i6 = i13;
                        iHA = i14;
                        i7 = i15;
                    }
                    i12++;
                    i13 = i6;
                    i14 = iHA;
                    i15 = i7;
                }
                if (this.ajd == 0) {
                    nu();
                }
                if (z) {
                    bQ(II);
                }
            }
        }
    }
}
