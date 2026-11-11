package android.support.v7.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* JADX INFO: compiled from: ForwardingListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class bt implements View.OnAttachStateChangeListener, View.OnTouchListener {
    private int iW;
    private final int vA;
    final View vB;
    private Runnable vC;
    private Runnable vD;
    private boolean vE;
    private final int[] vF = new int[2];
    private final float vy;
    private final int vz;

    public abstract android.support.v7.c.a.ao cJ();

    public bt(View view) {
        this.vB = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.vy = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.vz = ViewConfiguration.getTapTimeout();
        this.vA = (this.vz + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        boolean z2 = this.vE;
        if (z2) {
            z = c(motionEvent) || !dW();
        } else {
            boolean z3 = b(motionEvent) && cK();
            if (z3) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                this.vB.onTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
            }
            z = z3;
        }
        this.vE = z;
        return z || z2;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        this.vE = false;
        this.iW = -1;
        if (this.vC != null) {
            this.vB.removeCallbacks(this.vC);
        }
    }

    protected boolean cK() {
        android.support.v7.c.a.ao aoVarCJ = cJ();
        if (aoVarCJ != null && !aoVarCJ.isShowing()) {
            aoVarCJ.show();
            return true;
        }
        return true;
    }

    protected boolean dW() {
        android.support.v7.c.a.ao aoVarCJ = cJ();
        if (aoVarCJ != null && aoVarCJ.isShowing()) {
            aoVarCJ.dismiss();
            return true;
        }
        return true;
    }

    private boolean b(MotionEvent motionEvent) {
        View view = this.vB;
        if (!view.isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.iW = motionEvent.getPointerId(0);
                if (this.vC == null) {
                    this.vC = new bu(this);
                }
                view.postDelayed(this.vC, this.vz);
                if (this.vD == null) {
                    this.vD = new bv(this);
                }
                view.postDelayed(this.vD, this.vA);
                return false;
            case 1:
            case 3:
                eJ();
                return false;
            case 2:
                int iFindPointerIndex = motionEvent.findPointerIndex(this.iW);
                if (iFindPointerIndex < 0 || a(view, motionEvent.getX(iFindPointerIndex), motionEvent.getY(iFindPointerIndex), this.vy)) {
                    return false;
                }
                eJ();
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            default:
                return false;
        }
    }

    private void eJ() {
        if (this.vD != null) {
            this.vB.removeCallbacks(this.vD);
        }
        if (this.vC != null) {
            this.vB.removeCallbacks(this.vC);
        }
    }

    void eK() {
        eJ();
        View view = this.vB;
        if (view.isEnabled() && !view.isLongClickable() && cK()) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
            view.onTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
            this.vE = true;
        }
    }

    private boolean c(MotionEvent motionEvent) {
        bq bqVar;
        View view = this.vB;
        android.support.v7.c.a.ao aoVarCJ = cJ();
        if (aoVarCJ == null || !aoVarCJ.isShowing() || (bqVar = (bq) aoVarCJ.getListView()) == null || !bqVar.isShown()) {
            return false;
        }
        MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
        b(view, motionEventObtainNoHistory);
        a(bqVar, motionEventObtainNoHistory);
        boolean zA = bqVar.a(motionEventObtainNoHistory, this.iW);
        motionEventObtainNoHistory.recycle();
        int actionMasked = motionEvent.getActionMasked();
        return zA && (actionMasked != 1 && actionMasked != 3);
    }

    private static boolean a(View view, float f, float f2, float f3) {
        return f >= (-f3) && f2 >= (-f3) && f < ((float) (view.getRight() - view.getLeft())) + f3 && f2 < ((float) (view.getBottom() - view.getTop())) + f3;
    }

    private boolean a(View view, MotionEvent motionEvent) {
        int[] iArr = this.vF;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation(-iArr[0], -iArr[1]);
        return true;
    }

    private boolean b(View view, MotionEvent motionEvent) {
        int[] iArr = this.vF;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation(iArr[0], iArr[1]);
        return true;
    }
}
