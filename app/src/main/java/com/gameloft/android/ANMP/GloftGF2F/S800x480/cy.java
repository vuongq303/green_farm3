package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: loaded from: classes.dex */
class cy extends Thread {
    int Bj = 0;
    Object[] adx;
    int bbY;

    public cy(Object[] objArr, int i) {
        this.adx = objArr;
        this.bbY = i;
    }

    private boolean MQ() {
        switch (((Integer) this.adx[0]).intValue()) {
            case 0:
                return e(this.adx);
            case 1:
                return g(this.adx);
            case 2:
                return i(this.adx);
            case 3:
                return j(this.adx);
            case 4:
                return d(this.adx);
            case 5:
                return h(this.adx);
            case 6:
                return f(this.adx);
            case 7:
                return m(this.adx);
            case 8:
                return n(this.adx);
            case 9:
                return o(this.adx);
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                return k(this.adx);
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                return l(this.adx);
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                return p(this.adx);
            case 13:
                return q(this.adx);
            case 14:
                return r(this.adx);
            case 15:
                return s(this.adx);
            case 16:
                return t(this.adx);
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                return u(this.adx);
            case 18:
                return v(this.adx);
            case 19:
                return c(this.adx);
            default:
                return false;
        }
    }

    private void MR() {
        synchronized (ci.aXG) {
            if (this.bbY != ci.aXN) {
                this.Bj = -1;
                return;
            }
            if (this.Bj == 0 && !ci.aXH) {
                String str = "oss: thread launching " + this.bbY;
                this.Bj = 1;
                if (!MQ()) {
                    mJ();
                }
            } else if (this.Bj == 1 && ci.aXL != 1) {
                mJ();
            }
            if (this.Bj >= 0) {
                try {
                    Thread.sleep(250L);
                } catch (Exception e) {
                }
            }
        }
    }

    private boolean c(Object[] objArr) {
        return ci.mg(t.ND);
    }

    private boolean d(Object[] objArr) {
        return ci.mg(t.Nv);
    }

    private boolean e(Object[] objArr) {
        return ci.j((String) objArr[1], ((Boolean) objArr[2]).booleanValue());
    }

    private boolean f(Object[] objArr) {
        return ci.bx(((Boolean) objArr[1]).booleanValue());
    }

    private boolean g(Object[] objArr) {
        return ci.a((String) objArr[1], ((Boolean) objArr[2]).booleanValue(), ((Integer) objArr[3]).intValue(), (String) objArr[4], (String) objArr[5], (String) objArr[6]);
    }

    private boolean h(Object[] objArr) {
        return ci.a((String) objArr[1], ((Boolean) objArr[2]).booleanValue(), (String) objArr[3], ((Integer) objArr[4]).intValue(), (String) objArr[5], (String) objArr[6], (String) objArr[7], (String) objArr[8], (String) objArr[9], (String[]) objArr[10]);
    }

    private boolean i(Object[] objArr) {
        return ci.a((String) objArr[1], (String) objArr[2], (String) objArr[3], ((Boolean) objArr[4]).booleanValue(), (String) objArr[5], ((Boolean) objArr[6]).booleanValue());
    }

    private boolean j(Object[] objArr) {
        return ci.a((String) objArr[1], (String) objArr[2], (String) objArr[3], (String) objArr[4], ((Boolean) objArr[5]).booleanValue());
    }

    private boolean k(Object[] objArr) {
        return ci.q((String) objArr[1], (String) objArr[2], (String) objArr[3]);
    }

    private boolean l(Object[] objArr) {
        return ci.r((String) objArr[1], (String) objArr[2], (String) objArr[3]);
    }

    private boolean m(Object[] objArr) {
        return ci.LY();
    }

    private void mJ() {
        this.Bj = -1;
        synchronized (ci.aXG) {
            if (this.bbY == ci.aXN) {
                boolean unused = ci.adu = false;
            }
        }
    }

    private boolean n(Object[] objArr) {
        return ci.by(((Boolean) objArr[1]).booleanValue());
    }

    private boolean o(Object[] objArr) {
        return ci.N((String) objArr[1], (String) objArr[2]);
    }

    private boolean p(Object[] objArr) {
        return ci.dv((String) objArr[1]);
    }

    private boolean q(Object[] objArr) {
        return ci.dw((String) objArr[1]);
    }

    private boolean r(Object[] objArr) {
        return ci.s((String) objArr[1], (String) objArr[2], (String) objArr[3]);
    }

    private boolean s(Object[] objArr) {
        return ci.f((String) objArr[1], (String) objArr[2], (String) objArr[3], (String) objArr[4], (String) objArr[5]);
    }

    private boolean t(Object[] objArr) {
        return ci.t((String) objArr[1], (String) objArr[2], (String) objArr[3]);
    }

    private boolean u(Object[] objArr) {
        return ci.h((String) objArr[1], (String) objArr[2], (String) objArr[3], (String) objArr[4]);
    }

    private boolean v(Object[] objArr) {
        return ci.g((String) objArr[1], (String) objArr[2], (String) objArr[3], (String) objArr[4], (String) objArr[5], (String) objArr[6]);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (this.Bj >= 0) {
            MR();
        }
        String str = "oss: thread stopped " + this.bbY;
    }
}
