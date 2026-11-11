package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.view.aa;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

/* JADX INFO: compiled from: AutoScrollHelper.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a implements View.OnTouchListener {
    private static final int io = ViewConfiguration.getTapTimeout();
    private Runnable eb;
    final View hY;
    private int ib;
    private int ic;
    private boolean ih;
    boolean ii;
    boolean ij;
    boolean ik;
    private boolean il;
    private boolean im;
    final b hW = new b();
    private final Interpolator hX = new AccelerateInterpolator();
    private float[] hZ = {0.0f, 0.0f};
    private float[] ia = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] ie = {0.0f, 0.0f};

    /* JADX INFO: renamed from: if, reason: not valid java name */
    private float[] f1if = {0.0f, 0.0f};
    private float[] ig = {Float.MAX_VALUE, Float.MAX_VALUE};

    public abstract boolean H(int i);

    public abstract boolean I(int i);

    public abstract void f(int i, int i2);

    public a(View view) {
        this.hY = view;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int i = (int) ((1575.0f * displayMetrics.density) + 0.5f);
        int i2 = (int) ((displayMetrics.density * 315.0f) + 0.5f);
        a(i, i);
        b(i2, i2);
        D(1);
        e(Float.MAX_VALUE, Float.MAX_VALUE);
        d(0.2f, 0.2f);
        c(1.0f, 1.0f);
        E(io);
        F(500);
        G(500);
    }

    public a f(boolean z) {
        if (this.il && !z) {
            aX();
        }
        this.il = z;
        return this;
    }

    public a a(float f, float f2) {
        this.ig[0] = f / 1000.0f;
        this.ig[1] = f2 / 1000.0f;
        return this;
    }

    public a b(float f, float f2) {
        this.f1if[0] = f / 1000.0f;
        this.f1if[1] = f2 / 1000.0f;
        return this;
    }

    public a c(float f, float f2) {
        this.ie[0] = f / 1000.0f;
        this.ie[1] = f2 / 1000.0f;
        return this;
    }

    public a D(int i) {
        this.ib = i;
        return this;
    }

    public a d(float f, float f2) {
        this.hZ[0] = f;
        this.hZ[1] = f2;
        return this;
    }

    public a e(float f, float f2) {
        this.ia[0] = f;
        this.ia[1] = f2;
        return this;
    }

    public a E(int i) {
        this.ic = i;
        return this;
    }

    public a F(int i) {
        this.hW.J(i);
        return this;
    }

    public a G(int i) {
        this.hW.K(i);
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.il) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.ij = true;
                this.ih = false;
                this.hW.g(a(0, motionEvent.getX(), view.getWidth(), this.hY.getWidth()), a(1, motionEvent.getY(), view.getHeight(), this.hY.getHeight()));
                if (!this.ik && aV()) {
                    aW();
                }
                break;
            case 1:
            case 3:
                aX();
                break;
            case 2:
                this.hW.g(a(0, motionEvent.getX(), view.getWidth(), this.hY.getWidth()), a(1, motionEvent.getY(), view.getHeight(), this.hY.getHeight()));
                if (!this.ik) {
                    aW();
                }
                break;
        }
        return this.im && this.ik;
    }

    boolean aV() {
        b bVar = this.hW;
        int iBb = bVar.bb();
        int iBa = bVar.ba();
        return (iBb != 0 && I(iBb)) || (iBa != 0 && H(iBa));
    }

    private void aW() {
        if (this.eb == null) {
            this.eb = new c(this);
        }
        this.ik = true;
        this.ii = true;
        if (!this.ih && this.ic > 0) {
            aa.a(this.hY, this.eb, this.ic);
        } else {
            this.eb.run();
        }
        this.ih = true;
    }

    private void aX() {
        if (this.ii) {
            this.ik = false;
        } else {
            this.hW.aX();
        }
    }

    private float a(int i, float f, float f2, float f3) {
        float fA = a(this.hZ[i], f2, this.ia[i], f);
        if (fA == 0.0f) {
            return 0.0f;
        }
        float f4 = this.ie[i];
        float f5 = this.f1if[i];
        float f6 = this.ig[i];
        float f7 = f4 * f3;
        if (fA > 0.0f) {
            return a(fA * f7, f5, f6);
        }
        return -a((-fA) * f7, f5, f6);
    }

    private float a(float f, float f2, float f3, float f4) {
        float interpolation;
        float fA = a(f * f2, 0.0f, f3);
        float f5 = f(f2 - f4, fA) - f(f4, fA);
        if (f5 < 0.0f) {
            interpolation = -this.hX.getInterpolation(-f5);
        } else {
            if (f5 <= 0.0f) {
                return 0.0f;
            }
            interpolation = this.hX.getInterpolation(f5);
        }
        return a(interpolation, -1.0f, 1.0f);
    }

    private float f(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        switch (this.ib) {
            case 0:
            case 1:
                if (f >= f2) {
                    return 0.0f;
                }
                if (f >= 0.0f) {
                    return 1.0f - (f / f2);
                }
                return (this.ik && this.ib == 1) ? 1.0f : 0.0f;
            case 2:
                if (f < 0.0f) {
                    return f / (-f2);
                }
                return 0.0f;
            default:
                return 0.0f;
        }
    }

    static int a(int i, int i2, int i3) {
        if (i > i3) {
            return i3;
        }
        return i < i2 ? i2 : i;
    }

    static float a(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    void aY() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        this.hY.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }
}
