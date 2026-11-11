package android.support.v4.widget;

import android.view.animation.AnimationUtils;

/* JADX INFO: compiled from: AutoScrollHelper.java */
/* JADX INFO: loaded from: classes.dex */
class b {
    private int ip;
    private int iq;
    private float ir;
    private float is;
    private float ix;
    private int iy;
    private long mStartTime = Long.MIN_VALUE;
    private long iw = -1;
    private long it = 0;
    private int iu = 0;
    private int iv = 0;

    b() {
    }

    public void J(int i) {
        this.ip = i;
    }

    public void K(int i) {
        this.iq = i;
    }

    public void start() {
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.iw = -1L;
        this.it = this.mStartTime;
        this.ix = 0.5f;
        this.iu = 0;
        this.iv = 0;
    }

    public void aX() {
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.iy = a.a((int) (jCurrentAnimationTimeMillis - this.mStartTime), 0, this.iq);
        this.ix = f(jCurrentAnimationTimeMillis);
        this.iw = jCurrentAnimationTimeMillis;
    }

    public boolean isFinished() {
        return this.iw > 0 && AnimationUtils.currentAnimationTimeMillis() > this.iw + ((long) this.iy);
    }

    private float f(long j) {
        if (j < this.mStartTime) {
            return 0.0f;
        }
        if (this.iw < 0 || j < this.iw) {
            return a.a((j - this.mStartTime) / this.ip, 0.0f, 1.0f) * 0.5f;
        }
        return (a.a((j - this.iw) / this.iy, 0.0f, 1.0f) * this.ix) + (1.0f - this.ix);
    }

    private float c(float f) {
        return ((-4.0f) * f * f) + (4.0f * f);
    }

    public void aZ() {
        if (this.it == 0) {
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        float fC = c(f(jCurrentAnimationTimeMillis));
        long j = jCurrentAnimationTimeMillis - this.it;
        this.it = jCurrentAnimationTimeMillis;
        this.iu = (int) (j * fC * this.ir);
        this.iv = (int) (j * fC * this.is);
    }

    public void g(float f, float f2) {
        this.ir = f;
        this.is = f2;
    }

    public int ba() {
        return (int) (this.ir / Math.abs(this.ir));
    }

    public int bb() {
        return (int) (this.is / Math.abs(this.is));
    }

    public int bc() {
        return this.iu;
    }

    public int bd() {
        return this.iv;
    }
}
