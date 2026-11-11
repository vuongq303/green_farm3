package com.gameloft.android.wrapper;

/* JADX INFO: compiled from: MultipleScreen.java */
/* JADX INFO: loaded from: classes.dex */
public class af {
    public String bgk;
    public int bgl;
    public int bgm;
    public int bgn;
    public int bgo;
    public float bgp = 0.0f;
    public float bgq = 0.0f;
    public int bgr = 0;
    public int bgs = 0;

    public af() {
    }

    public af(String str, int i, int i2) {
        this.bgk = str;
        this.bgl = i;
        this.bgm = i2;
    }

    public void l(int i, int i2, boolean z) {
        int i3;
        int i4;
        if ((i <= i2 || this.bgl <= this.bgm) && (i >= i2 || this.bgl >= this.bgm)) {
            i3 = i;
            i4 = i2;
        } else {
            i3 = i2;
            i4 = i;
        }
        if (z) {
            this.bgp = i4 / this.bgl;
            this.bgq = i3 / this.bgm;
        } else {
            this.bgp = 1.0f;
            this.bgq = 1.0f;
        }
        if (Math.abs(this.bgp - this.bgq) > ae.bfP) {
            if (this.bgp > this.bgq) {
                this.bgp = this.bgq;
            } else if (this.bgp < this.bgq) {
                this.bgq = this.bgp;
            }
        }
        this.bgr = (int) ((i4 - (this.bgl * this.bgp)) / 2.0f);
        this.bgs = (int) ((i3 - (this.bgm * this.bgq)) / 2.0f);
        this.bgn = this.bgl;
        this.bgo = this.bgm;
        String str = "setTo " + this.bgk + " W=" + this.bgl + " H=" + this.bgm + " SW=" + this.bgp + " SH=" + this.bgq + " TX=" + this.bgr + " TY=" + this.bgs;
    }

    public void m(int i, int i2, boolean z) {
        int i3;
        int i4;
        if ((i <= i2 || this.bgl <= this.bgm) && (i >= i2 || this.bgl >= this.bgm)) {
            i3 = i;
            i4 = i2;
        } else {
            i3 = i2;
            i4 = i;
        }
        if (z) {
            this.bgp = i4 / this.bgl;
            this.bgq = i3 / this.bgm;
        } else {
            this.bgp = 1.0f;
            this.bgq = 1.0f;
        }
        if (Math.abs(this.bgp - this.bgq) <= ae.bfP) {
        }
        this.bgr = (int) ((i4 - (this.bgl * this.bgp)) / 2.0f);
        this.bgs = (int) ((i3 - (this.bgm * this.bgq)) / 2.0f);
        this.bgn = this.bgl;
        this.bgo = this.bgm;
        String str = "setToNoQuality " + this.bgk + " W=" + this.bgl + " H=" + this.bgm + " SW=" + this.bgp + " SH=" + this.bgq + " TX=" + this.bgr + " TY=" + this.bgs;
    }

    public void n(int i, int i2, boolean z) {
        int i3;
        int i4;
        if ((i <= i2 || this.bgn <= this.bgo) && (i >= i2 || this.bgn >= this.bgo)) {
            i3 = i;
            i4 = i2;
        } else {
            i3 = i2;
            i4 = i;
        }
        if (z) {
            this.bgp = i4 / this.bgn;
            this.bgq = i3 / this.bgo;
        } else {
            this.bgp = 1.0f;
            this.bgq = 1.0f;
        }
        if (Math.abs(this.bgp - this.bgq) > ae.bfP) {
            if (this.bgp > this.bgq) {
                this.bgp = this.bgq;
            } else if (this.bgp < this.bgq) {
                this.bgq = this.bgp;
            }
        }
        this.bgr = (int) ((i4 - (this.bgn * this.bgp)) / 2.0f);
        this.bgs = (int) ((i3 - (this.bgo * this.bgq)) / 2.0f);
        String str = "setToLogicalResolution " + this.bgk + " W=" + this.bgl + " H=" + this.bgm + " SW=" + this.bgp + " SH=" + this.bgq + " TX=" + this.bgr + " TY=" + this.bgs;
    }
}
