package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: GameHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class f {
    int bpH;
    int bpI;

    public int UL() {
        return this.bpH;
    }

    public int UM() {
        return this.bpI;
    }

    public f(int i, int i2) {
        this.bpH = 0;
        this.bpI = -100;
        this.bpH = i;
        this.bpI = i2;
    }

    public f(int i) {
        this(i, -100);
    }

    public String toString() {
        return "SignInFailureReason(serviceErrorCode:" + g.mR(this.bpH) + (this.bpI == -100 ? ")" : ",activityResultCode:" + g.mQ(this.bpI) + ")");
    }
}
