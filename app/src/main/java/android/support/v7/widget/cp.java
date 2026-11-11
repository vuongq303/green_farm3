package android.support.v7.widget;

/* JADX INFO: compiled from: RtlSpacingHelper.java */
/* JADX INFO: loaded from: classes.dex */
class cp {
    private int xm = 0;
    private int xn = 0;
    private int xo = Integer.MIN_VALUE;
    private int gO = Integer.MIN_VALUE;
    private int xp = 0;
    private int xq = 0;
    private boolean xr = false;
    private boolean xs = false;

    cp() {
    }

    public int getStart() {
        return this.xr ? this.xn : this.xm;
    }

    public int getEnd() {
        return this.xr ? this.xm : this.xn;
    }

    public void q(int i, int i2) {
        this.xo = i;
        this.gO = i2;
        this.xs = true;
        if (this.xr) {
            if (i2 != Integer.MIN_VALUE) {
                this.xm = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.xn = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.xm = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.xn = i2;
        }
    }

    public void Q(boolean z) {
        if (z != this.xr) {
            this.xr = z;
            if (this.xs) {
                if (z) {
                    this.xm = this.gO != Integer.MIN_VALUE ? this.gO : this.xp;
                    this.xn = this.xo != Integer.MIN_VALUE ? this.xo : this.xq;
                    return;
                } else {
                    this.xm = this.xo != Integer.MIN_VALUE ? this.xo : this.xp;
                    this.xn = this.gO != Integer.MIN_VALUE ? this.gO : this.xq;
                    return;
                }
            }
            this.xm = this.xp;
            this.xn = this.xq;
        }
    }
}
