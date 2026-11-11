package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
public class aq {
    public int Bj;
    public int acE;
    public String adA;
    public int adB;
    public ar adC;
    public am adD;
    public String adz;

    public aq() {
        this.adB = 0;
        this.adC = null;
        this.Bj = 0;
        this.adz = "";
        this.adA = "";
        this.acE = 0;
        this.adB = 20000;
        this.Bj = 0;
    }

    public aq(String str, String str2, int i, ar arVar, int i2) {
        this.adB = 0;
        this.adC = null;
        this.Bj = 0;
        this.adz = str;
        this.adA = str2;
        this.acE = i;
        this.adC = arVar;
        if (i2 > 0) {
            this.adB = i2;
        } else {
            this.adB = 20000;
        }
        this.Bj = 1;
    }

    public void a(aq aqVar, am amVar) {
        this.adz = aqVar.adz;
        this.adA = aqVar.adA;
        this.acE = aqVar.acE;
        this.adC = aqVar.adC;
        this.adB = aqVar.adB;
        this.Bj = aqVar.Bj;
        this.adD = amVar;
    }

    public void dV(int i) {
        if (this.adD == null) {
        }
        switch (this.Bj) {
            case 1:
            case 2:
                this.adD.c(this.adz, this.adA, this.acE);
                ez(3);
                break;
            case 3:
                if (!this.adD.isInProgress()) {
                    mL();
                    ez(7);
                }
                break;
        }
    }

    public void ez(int i) {
        this.Bj = i;
    }

    public synchronized boolean mK() {
        return this.adD == null || this.Bj == 0 || this.Bj == 4 || this.Bj == 5 || this.Bj == 6 || this.Bj == 7;
    }

    public void mL() {
        if (this.adC == null || this.adD == null) {
            return;
        }
        this.adC.D(this.adD.Bg, this.adD.BM);
    }
}
