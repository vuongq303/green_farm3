package com.quanhoang.greenfarm3.S800x480;

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
        GameRuntime.c(i6 == 4 || i6 == 8, "PathFinding_Init nDirCount should be 4 or 8.");
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
        GameRuntime.c(this.aaK, "GLLibPathFinding not initialized");
        if (i6 < 0) {
            i8 += i6;
            i6 = 0;
        }
        if (i7 < 0) {
            i9 += i7;
            i7 = 0;
        }
        if (w.YV) {
            GameRuntime.be("------------- execPathFinding -------------");
            GameRuntime.be("execPathFinding from (" + i + ", " + i2 + ") to (" + i4 + ", " + i5 + ")");
            GameRuntime.be("  searching sub-area (" + i6 + ", " + i7 + ") to (" + (i6 + i8) + ", " + (i7 + i9) + ")");
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
                GameRuntime.be("a path has been found");
                break;
            }
            if (w.YV) {
                i16++;
                GameRuntime.be("current node[" + i17 + "] is {" + (i19 + i6) + " (" + i19 + " + " + i6 + "), " + (i20 + i7) + " (" + i20 + " + " + i7 + ")}");
            }
            int i21 = i16;
            for (int i22 = 0; i22 < this.aaC; i22++) {
                if (!z || (this.aaF[(this.aaD * i20) + i19] & this.aaG & (1 << i22)) != 0) {
                    int i23 = i19 + aat[(i22 << 1) + 0];
                    int i24 = i20 + aat[(i22 << 1) + 1];
                    if (w.YV) {
                        GameRuntime.be("position is {" + (i23 + i6) + " (" + i23 + " + " + i6 + "), " + (i24 + i7) + " (" + i24 + " + " + i7 + ")}");
                    }
                    if (i23 < 0 || i23 >= i8) {
                        if (w.YV) {
                            GameRuntime.be("x position is not in the map");
                        }
                    } else if (i24 >= 0 && i24 < i9) {
                        int i25 = (i24 * i8) + i23;
                        if (w.YV) {
                            GameRuntime.be("\tadjacent node[" + i25 + "] is {" + (i23 + i6) + " (" + i23 + " + " + i6 + "), " + (i24 + i7) + " (" + i24 + " + " + i7 + ")}");
                        }
                        if (this.aax[i25] != -1) {
                            if (w.YW) {
                                iA = a(i19 + i6, i20 + i7, i23 + i6, i24 + i7, i22, i17 == i13);
                            } else {
                                // Original cond_396 -> cond_3e1 -> cond_6fb joins the cost.
                                int cost = i22 >= 4 ? this.aaA : this.aaz;
                                if (i22 >= 4 && !z) {
                                    int column = i17 % i8;
                                    int row = i17 / i8;
                                    if (i22 == 4) {
                                        if ((this.aaF[(column - 1) + i6 + ((row + i7) * this.aaD)] & this.aaG) != 0
                                                || (this.aaF[column + i6 + (((row - 1) + i7) * this.aaD)] & this.aaG) != 0) {
                                            continue;
                                        }
                                    } else if (i22 == 5) {
                                        if ((this.aaF[column + 1 + i6 + ((row + i7) * this.aaD)] & this.aaG) != 0
                                                || (this.aaF[column + i6 + (((row - 1) + i7) * this.aaD)] & this.aaG) != 0) {
                                            continue;
                                        }
                                    } else if (i22 == 6) {
                                        if ((this.aaF[(column - 1) + i6 + ((row + i7) * this.aaD)] & this.aaG) != 0
                                                || (this.aaF[column + i6 + ((row + 1 + i7) * this.aaD)] & this.aaG) != 0) {
                                            continue;
                                        }
                                    } else if (i22 == 7) {
                                        if ((this.aaF[column + 1 + i6 + ((row + i7) * this.aaD)] & this.aaG) != 0
                                                || (this.aaF[column + i6 + ((row + 1 + i7) * this.aaD)] & this.aaG) != 0) {
                                            continue;
                                        }
                                    }
                                }
                                if (!z && (this.aaF[i23 + i6 + ((i24 + i7) * this.aaD)] & this.aaG) != 0) {
                                    if (w.YV) {
                                        GameRuntime.be("\tnode is a wall");
                                    }
                                    continue;
                                }
                                iA = cost;
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
                                    GameRuntime.be("\tadd adjacent node to open list (score : " + (i11 + i29) + ")");
                                }
                                dQ(i25);
                            } else if (this.aax[i25] > i29) {
                                this.aau[i25] = (short) i17;
                                this.aax[i25] = (short) i29;
                                if (w.YV) {
                                    GameRuntime.be("\trelink adjacent node to current node");
                                }
                                dR(i25);
                                dQ(i25);
                            }
                        } else if (w.YV) {
                            GameRuntime.be("\tnode has been already checked");
                        }
                    } else if (w.YV) {
                        GameRuntime.be("y position is not in the map");
                    }
                }
            }
            i17 = this.aaJ;
            if (w.YV) {
                GameRuntime.be("change current node to : " + i17 + " {" + ((i17 % i8) + i6) + "(" + (i17 % i8) + " + " + i6 + ")," + ((i17 / i8) + i7) + "(" + (i17 / i8) + " + " + i7 + ")}");
            }
            if (i17 != -1) {
                short s2 = this.aau[i17];
                int i30 = i17 % i8 != s2 % i8 ? i17 % i8 > s2 % i8 ? 3 : 2 : i17 / i8 != s2 / i8 ? 1 : 0;
                if (w.YV) {
                    GameRuntime.be("new node[" + i17 + "] (score : " + (this.aax[i17] + this.aay[i17]) + ") is {" + ((i17 % i8) + i6) + " (" + (i17 % i8) + " + " + i6 + "), " + ((i17 / i8) + i7) + " (" + (i17 / i8) + " + " + i7 + ")}");
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
                GameRuntime.be("opened list is empty... there is no path");
                GameRuntime.be("-------------------------------------------");
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
            GameRuntime.be("-------------------------------------------");
            GameRuntime.be("Path was found with " + i16 + " nodes visited");
            for (int i33 = this.aaI; i33 >= 0; i33--) {
                short s3 = this.aaH[i33];
                GameRuntime.be("node [" + i33 + "]\t= (" + (s3 % this.aaD) + ", " + (s3 / this.aaD) + ")");
            }
            GameRuntime.be("-------------------------------------------");
        }
    }

    public int lK() {
        GameRuntime.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaI + 1;
    }

    public int dO(int i) {
        GameRuntime.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaH[i] % this.aaD;
    }

    public int dP(int i) {
        GameRuntime.c(this.aaK, "GLLibPathFinding not initialized");
        return this.aaH[i] / this.aaD;
    }

    // Original ordered-open-list traversal uses the current node register throughout.
    private void dQ(int i) {
        if (this.aaJ == -1) {
            this.aaJ = i;
            return;
        }
        int score = this.aay[i] + this.aax[i];
        int current = this.aaJ;
        while (current != -1) {
            if (score < this.aax[current] + this.aay[current]) {
                if (this.aav[current] == -1) {
                    this.aaJ = i;
                } else {
                    this.aaw[this.aav[current]] = (short) i;
                }
                this.aav[i] = this.aav[current];
                this.aaw[i] = (short) current;
                this.aav[current] = (short) i;
                return;
            }
            if (this.aaw[current] == -1) {
                this.aaw[current] = (short) i;
                this.aav[i] = (short) current;
                return;
            }
            current = this.aaw[current];
        }
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
