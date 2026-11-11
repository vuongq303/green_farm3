package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: compiled from: GLLibPathFinding.java */
/* JADX INFO: loaded from: classes.dex */
public class z {
    private static int[] aat = {0, -1, 0, 1, -1, 0, 1, 0, -1, -1, 1, -1, -1, 1, 1, 1};
    private int aaA;
    private int aaB;
    private int aaC;
    public int aaD;
    private int aaE;
    private byte[] aaF;
    public int aaG;
    public short[] aaH;
    private int aaI;
    private int aaJ;
    private boolean aaK = false;
    private short[] aau;
    private short[] aav;
    private short[] aaw;
    private short[] aax;
    private short[] aay;
    private int aaz;

    public void a(int i, int i2, byte[] bArr, int i3, int i4, int i5, int i6) {
        a(i, i2, bArr, i3, i4, i5, i6, -1);
    }

    public void a(int i, int i2, byte[] bArr, int i3, int i4, int i5, int i6, int i7) {
        GLLib.c(i6 == 4 || i6 == 8, "PathFinding_Init nDirCount should be 4 or 8.");
        this.aaD = i;
        this.aaE = i2;
        this.aaF = bArr;
        this.aaG = i7;
        this.aaz = i3;
        this.aaA = i4;
        this.aaB = i5;
        this.aaC = i6;
        this.aau = new short[w.YU];
        this.aav = new short[w.YU];
        this.aaw = new short[w.YU];
        this.aax = new short[w.YU];
        this.aay = new short[w.YU];
        this.aaJ = -1;
        this.aaH = new short[w.YU];
        this.aaI = -1;
        this.aaK = true;
    }

    private int a(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6 = this.aaz;
        int i7 = this.aaz * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
        int i8 = this.aaz * 10;
        boolean z2 = (z || (this.aaF[(this.aaD * i2) + i] & this.aaG) == 0) ? false : true;
        if (i5 >= 4) {
            i6 = this.aaA;
            if (!z2) {
                if (i5 == 4) {
                    if ((this.aaF[(i - 1) + (this.aaD * i2)] & this.aaG) != 0 || (this.aaF[((i2 - 1) * this.aaD) + i] & this.aaG) != 0) {
                        i6 += i7;
                    }
                } else if (i5 == 5) {
                    if ((this.aaF[i + 1 + (this.aaD * i2)] & this.aaG) != 0 || (this.aaF[((i2 - 1) * this.aaD) + i] & this.aaG) != 0) {
                        i6 += i7;
                    }
                } else if (i5 == 6) {
                    if ((this.aaF[(i - 1) + (this.aaD * i2)] & this.aaG) != 0 || (this.aaF[((i2 + 1) * this.aaD) + i] & this.aaG) != 0) {
                        i6 += i7;
                    }
                } else if (i5 == 7 && ((this.aaF[i + 1 + (this.aaD * i2)] & this.aaG) != 0 || (this.aaF[((i2 + 1) * this.aaD) + i] & this.aaG) != 0)) {
                    i6 += i7;
                }
            }
        }
        if ((this.aaF[(this.aaD * i4) + i3] & this.aaG) == 0) {
            return i6;
        }
        if (z2) {
            return i6 + i8;
        }
        return i6 < i7 ? i6 + i7 : i6;
    }

    public void c(int i, int i2, int i3, int i4, int i5) {
        a(i, i2, i3, i4, i5, 0, 0, this.aaD, this.aaE, false);
    }

    /* JADX WARN: Code duplicated, block: B:96:0x03e1 A[PHI: r2
  0x03e1: PHI (r2v66 int) = (r2v65 int), (r2v69 int), (r2v69 int), (r2v69 int), (r2v69 int), (r2v69 int), (r2v69 int) binds: [B:87:0x039b, B:89:0x03a1, B:117:0x0480, B:121:0x04b7, B:114:0x047b, B:107:0x043f, B:95:0x03df] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:97:0x03e3  */
    public void a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z) {
        int i10;
        int iA;
        int i11;
        GLLib.c(this.aaK, "GLLibPathFinding not initialized");
        if (i6 < 0) {
            i8 += i6;
            i6 = 0;
        }
        if (i7 < 0) {
            i9 += i7;
            i7 = 0;
        }
        if (w.YV) {
            GLLib.be("------------- execPathFinding -------------");
            GLLib.be("execPathFinding from (" + i + ", " + i2 + ") to (" + i4 + ", " + i5 + ")");
            GLLib.be("  searching sub-area (" + i6 + ", " + i7 + ") to (" + (i6 + i8) + ", " + (i7 + i9) + ")");
        }
        for (int i12 = 0; i12 < w.YU; i12++) {
            this.aau[i12] = -1;
            this.aav[i12] = -1;
            this.aaw[i12] = -1;
            this.aax[i12] = 0;
            this.aay[i12] = 0;
            this.aaH[i12] = 0;
        }
        this.aaJ = -1;
        int i13 = ((i2 - i7) * i8) + (i - i6);
        int i14 = i4 - i6;
        int i15 = i5 - i7;
        int i16 = 0;
        int i17 = i13;
        int i18 = i3;
        while (i17 != -1) {
            dR(i17);
            short s = this.aax[i17];
            this.aax[i17] = -1;
            this.aay[i17] = -1;
            int i19 = i17 % i8;
            int i20 = i17 / i8;
            if (i19 == i14 && i20 == i15) {
                if (!w.YV) {
                    break;
                }
                GLLib.be("a path has been found");
                break;
            }
            if (w.YV) {
                i16++;
                GLLib.be("current node[" + i17 + "] is {" + (i19 + i6) + " (" + i19 + " + " + i6 + "), " + (i20 + i7) + " (" + i20 + " + " + i7 + ")}");
            }
            int i21 = i16;
            int i22 = 0;
            while (i22 < this.aaC) {
                if (!z || (this.aaF[(this.aaD * i20) + i19] & this.aaG & (1 << i22)) != 0) {
                    int i23 = i19 + aat[(i22 << 1) + 0];
                    int i24 = i20 + aat[(i22 << 1) + 1];
                    if (w.YV) {
                        GLLib.be("position is {" + (i23 + i6) + " (" + i23 + " + " + i6 + "), " + (i24 + i7) + " (" + i24 + " + " + i7 + ")}");
                    }
                    if (i23 < 0 || i23 >= i8) {
                        if (w.YV) {
                            GLLib.be("x position is not in the map");
                        }
                    } else if (i24 >= 0 && i24 < i9) {
                        int i25 = (i24 * i8) + i23;
                        if (w.YV) {
                            GLLib.be("\tadjacent node[" + i25 + "] is {" + (i23 + i6) + " (" + i23 + " + " + i6 + "), " + (i24 + i7) + " (" + i24 + " + " + i7 + ")}");
                        }
                        if (this.aax[i25] != -1) {
                            if (w.YW) {
                                iA = a(i19 + i6, i20 + i7, i23 + i6, i24 + i7, i22, i17 == i13);
                            } else {
                                int i26 = this.aaz;
                                if (i22 >= 4) {
                                    i26 = this.aaA;
                                    if (z) {
                                        if (z) {
                                        }
                                        iA = i26;
                                    } else {
                                        int i27 = i17 % i8;
                                        int i28 = i17 / i8;
                                        if (i22 == 4) {
                                            if ((this.aaF[(i27 - 1) + i6 + ((i28 + i7) * this.aaD)] & this.aaG) == 0 && (this.aaF[i27 + i6 + (((i28 - 1) + i7) * this.aaD)] & this.aaG) == 0) {
                                                if (!z || (this.aaF[i23 + i6 + ((i24 + i7) * this.aaD)] & this.aaG) == 0) {
                                                    iA = i26;
                                                } else if (w.YV) {
                                                    GLLib.be("\tnode is a wall");
                                                }
                                            }
                                        } else if (i22 == 5) {
                                            if ((this.aaF[i27 + 1 + i6 + ((i28 + i7) * this.aaD)] & this.aaG) == 0 && (this.aaF[i27 + i6 + (((i28 - 1) + i7) * this.aaD)] & this.aaG) == 0) {
                                                if (z) {
                                                }
                                                iA = i26;
                                            }
                                        } else if (i22 == 6) {
                                            if ((this.aaF[(i27 - 1) + i6 + ((i28 + i7) * this.aaD)] & this.aaG) == 0 && (this.aaF[i27 + i6 + ((i28 + 1 + i7) * this.aaD)] & this.aaG) == 0) {
                                                if (z) {
                                                }
                                                iA = i26;
                                            }
                                        } else if (i22 != 7 || ((this.aaF[i27 + 1 + i6 + ((i28 + i7) * this.aaD)] & this.aaG) == 0 && (this.aaF[i27 + i6 + ((i28 + 1 + i7) * this.aaD)] & this.aaG) == 0)) {
                                            if (z) {
                                            }
                                            iA = i26;
                                        }
                                    }
                                } else {
                                    if (z) {
                                    }
                                    iA = i26;
                                }
                            }
                            int i29 = iA + s + (i18 == i22 ? 0 : this.aaB);
                            int iAbs = Math.abs(i23 - i14);
                            int iAbs2 = Math.abs(i24 - i15);
                            if (this.aaC == 4) {
                                i11 = (iAbs + iAbs2) * this.aaz;
                            } else if (iAbs > iAbs2) {
                                i11 = ((iAbs - iAbs2) * this.aaz) + (this.aaA * iAbs2);
                            } else {
                                i11 = ((iAbs2 - iAbs) * this.aaz) + (this.aaA * iAbs);
                            }
                            if (this.aav[i25] == -1 && this.aaw[i25] == -1 && this.aaJ != i25) {
                                this.aau[i25] = (short) i17;
                                this.aax[i25] = (short) i29;
                                this.aay[i25] = (short) i11;
                                if (w.YV) {
                                    GLLib.be("\tadd adjacent node to open list (score : " + (i11 + i29) + ")");
                                }
                                dQ(i25);
                            } else if (this.aax[i25] > i29) {
                                this.aau[i25] = (short) i17;
                                this.aax[i25] = (short) i29;
                                if (w.YV) {
                                    GLLib.be("\trelink adjacent node to current node");
                                }
                                dR(i25);
                                dQ(i25);
                            }
                        } else if (w.YV) {
                            GLLib.be("\tnode has been already checked");
                        }
                    } else if (w.YV) {
                        GLLib.be("y position is not in the map");
                    }
                }
                i22++;
            }
            i17 = this.aaJ;
            if (w.YV) {
                GLLib.be("change current node to : " + i17 + " {" + ((i17 % i8) + i6) + "(" + (i17 % i8) + " + " + i6 + ")," + ((i17 / i8) + i7) + "(" + (i17 / i8) + " + " + i7 + ")}");
            }
            if (i17 != -1) {
                short s2 = this.aau[i17];
                int i30 = i17 % i8 != s2 % i8 ? i17 % i8 > s2 % i8 ? 3 : 2 : i17 / i8 != s2 / i8 ? 1 : 0;
                if (w.YV) {
                    GLLib.be("new node[" + i17 + "] (score : " + (this.aax[i17] + this.aay[i17]) + ") is {" + ((i17 % i8) + i6) + " (" + (i17 % i8) + " + " + i6 + "), " + ((i17 / i8) + i7) + " (" + (i17 / i8) + " + " + i7 + ")}");
                }
                i10 = i30;
            } else {
                i10 = i18;
            }
            i16 = i21;
            i18 = i10;
        }
        if (i17 == -1) {
            if (w.YV) {
                GLLib.be("opened list is empty... there is no path");
                GLLib.be("-------------------------------------------");
            }
            this.aaI = -1;
            return;
        }
        this.aaI = 0;
        int i31 = i17;
        while (i31 != -1) {
            short[] sArr = this.aaH;
            int i32 = this.aaI;
            this.aaI = i32 + 1;
            sArr[i32] = (short) ((i31 % i8) + i6 + (((i31 / i8) + i7) * this.aaD));
            i31 = this.aau[i31];
        }
        this.aaI--;
        if (w.YV) {
            GLLib.be("-------------------------------------------");
            GLLib.be("Path was found with " + i16 + " nodes visited");
            for (int i33 = this.aaI; i33 >= 0; i33--) {
                short s3 = this.aaH[i33];
                GLLib.be("node [" + i33 + "]\t= (" + (s3 % this.aaD) + ", " + (s3 / this.aaD) + ")");
            }
            GLLib.be("-------------------------------------------");
        }
    }

    public int lK() {
        GLLib.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaI + 1;
    }

    public int dO(int i) {
        GLLib.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaH[i] % this.aaD;
    }

    public int dP(int i) {
        GLLib.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaH[i] / this.aaD;
    }

    private void dQ(int i) {
        int i2;
        if (this.aaJ == -1) {
            this.aaJ = i;
            return;
        }
        int i3 = this.aay[i] + this.aax[i];
        int i4 = this.aaJ;
        while (i2 != -1) {
            if (i3 < this.aax[i2] + this.aay[i2]) {
                i2 = i4;
                if (this.aav[i2] == -1) {
                    this.aaJ = i;
                } else {
                    this.aaw[this.aav[i2]] = (short) i;
                }
                this.aav[i] = this.aav[i2];
                this.aaw[i] = (short) i2;
                this.aav[i2] = (short) i;
                return;
            }
            if (this.aaw[i2] != -1) {
                i2 = i4;
                i2 = this.aaw[i2];
            } else {
                i2 = i4;
                this.aaw[i2] = (short) i;
                this.aav[i] = (short) i2;
                return;
            }
        }
        i2 = i4;
    }

    private void dR(int i) {
        if (this.aaw[i] != -1) {
            this.aav[this.aaw[i]] = this.aav[i];
        }
        if (this.aaJ == i) {
            this.aaJ = this.aaw[i];
        } else if (this.aav[i] != -1) {
            this.aaw[this.aav[i]] = this.aaw[i];
        }
        this.aav[i] = -1;
        this.aaw[i] = -1;
    }
}
