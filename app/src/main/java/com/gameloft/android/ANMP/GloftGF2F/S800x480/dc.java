package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class dc {
    int anG;
    int anM;
    int aoG;
    int aoH;

    public dc(int i, int i2) {
        this.anG = i;
        this.anM = ci.ih(i2);
        if (i != 59) {
            this.aoG = 1;
            this.aoH = this.anG;
        } else {
            int iIi = ci.ii(this.anM) & 65535;
            this.aoG = ci.aZM[iIi];
            this.aoH = ci.aZN[iIi];
        }
    }

    int MT() {
        return (this.anG == 37 || this.anG == 36 || this.anG == 6) ? ci.ii(this.anM) : (ci.ii(this.anM) & (-65536)) >> 16;
    }

    void MU() {
        if (this.anG == 37) {
            ci.hX(ci.ii(this.anM));
            return;
        }
        if (this.anG == 36) {
            ci.hS(ci.ii(this.anM));
            return;
        }
        if (this.anG == 6) {
            ci.hT(ci.ii(this.anM));
            return;
        }
        if (this.anG == 59) {
            int iIi = ci.ii(this.anM) & 65535;
            int iIi2 = (ci.ii(this.anM) & (-65536)) >> 16;
            if (iIi == 110) {
                ci.hV(iIi2);
                return;
            }
            if (iIi == 5) {
                ci.hU(iIi2);
                return;
            }
            if (iIi >= 111 && iIi <= 116) {
                ci.bN(iIi2, iIi - 111);
                return;
            }
            ci.O(0, iIi, iIi2);
            if (iIi2 > 0) {
                ci.hI(-iIi2);
            }
            ci.Fy();
        }
    }

    int MV() {
        return this.anG;
    }
}
