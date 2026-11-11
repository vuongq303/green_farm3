package javax.microedition.lcdui;

import android.view.MotionEvent;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
class k implements n {
    final /* synthetic */ f cmb;
    Method cmf;
    private float[] cmg;
    private float[] cmh;
    private int[] cmi;

    public k(f fVar, Method method) {
        this.cmb = fVar;
        this.cmf = method;
    }

    public void pf(int i) {
        try {
            this.cmf.invoke(this.cmb, new Integer(i));
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // javax.microedition.lcdui.n
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (this.cmg == null) {
            this.cmg = new float[f.clE];
            this.cmh = new float[f.clE];
            this.cmi = new int[f.clE];
            for (int i2 = 0; i2 < f.clE; i2++) {
                this.cmg[i2] = -1.0f;
                this.cmh[i2] = -1.0f;
                this.cmi[i2] = 1;
            }
        }
        int action = motionEvent.getAction();
        int i3 = action & 255;
        int i4 = (action & 65280) >> 8;
        int pointerId = motionEvent.getPointerId(i4);
        int pointerCount = motionEvent.getPointerCount();
        float x = ((motionEvent.getX(i4) * 1.0f) / f.clI) - f.bgr;
        float y = ((motionEvent.getY(i4) * 1.0f) / f.clJ) - f.bgs;
        switch (i3) {
            case 0:
                pf(pointerId);
                this.cmb.V((int) x, (int) y);
                this.cmg[pointerId] = x;
                this.cmh[pointerId] = y;
                this.cmi[pointerId] = 0;
                if (pointerCount > 1) {
                    for (int i5 = 0; i5 < pointerCount; i5++) {
                        if (i5 != i4) {
                            int pointerId2 = motionEvent.getPointerId(i5);
                            float x2 = ((motionEvent.getX(i5) * 1.0f) / f.clI) - f.bgr;
                            float y2 = ((motionEvent.getY(i5) * 1.0f) / f.clJ) - f.bgs;
                            pf(pointerId2);
                            this.cmb.V((int) x2, (int) y2);
                            this.cmg[pointerId2] = x2;
                            this.cmh[pointerId2] = y2;
                            this.cmi[pointerId2] = 0;
                        }
                    }
                }
                return true;
            case 1:
                pf(pointerId);
                if (this.cmb.clS) {
                    this.cmb.U((int) this.cmg[pointerId], (int) this.cmh[pointerId]);
                } else {
                    this.cmb.U(-1, -1);
                }
                this.cmi[pointerId] = 1;
                if (pointerCount > 1) {
                    while (i < f.clE) {
                        if (i != pointerId && this.cmi[i] != 1) {
                            pf(i);
                            if (this.cmb.clS) {
                                this.cmb.U((int) this.cmg[i], (int) this.cmh[i]);
                            } else {
                                this.cmb.U(-1, -1);
                            }
                            this.cmi[i] = 1;
                        }
                        i++;
                    }
                }
                return true;
            case 2:
                pf(pointerId);
                this.cmb.W((int) x, (int) y);
                this.cmg[pointerId] = x;
                this.cmh[pointerId] = y;
                this.cmi[pointerId] = 2;
                if (pointerCount > 1) {
                    while (i < pointerCount) {
                        if (i != i4) {
                            int pointerId3 = motionEvent.getPointerId(i);
                            float x3 = ((motionEvent.getX(i) * 1.0f) / f.clI) - f.bgr;
                            float y3 = ((motionEvent.getY(i) * 1.0f) / f.clJ) - f.bgs;
                            pf(pointerId3);
                            this.cmb.W((int) x3, (int) y3);
                            this.cmg[pointerId3] = x3;
                            this.cmh[pointerId3] = y3;
                            this.cmi[pointerId3] = 2;
                        }
                        i++;
                    }
                }
                return true;
            case 3:
            case 4:
            default:
                return true;
            case 5:
                pf(pointerId);
                this.cmb.V((int) x, (int) y);
                this.cmg[pointerId] = x;
                this.cmh[pointerId] = y;
                this.cmi[pointerId] = 0;
                while (i < pointerCount) {
                    if (i != i4) {
                        int pointerId4 = motionEvent.getPointerId(i);
                        float x4 = ((motionEvent.getX(i) * 1.0f) / f.clI) - f.bgr;
                        float y4 = ((motionEvent.getY(i) * 1.0f) / f.clJ) - f.bgs;
                        pf(pointerId4);
                        this.cmb.W((int) x4, (int) y4);
                        this.cmg[pointerId4] = x4;
                        this.cmh[pointerId4] = y4;
                        this.cmi[pointerId4] = 2;
                    }
                    i++;
                }
                return true;
            case 6:
                pf(pointerId);
                if (this.cmb.clS) {
                    this.cmb.U((int) this.cmg[pointerId], (int) this.cmh[pointerId]);
                } else {
                    this.cmb.U(-1, -1);
                }
                this.cmi[pointerId] = 1;
                while (i < pointerCount) {
                    if (i != i4) {
                        int pointerId5 = motionEvent.getPointerId(i);
                        float x5 = ((motionEvent.getX(i) * 1.0f) / f.clI) - f.bgr;
                        float y5 = ((motionEvent.getY(i) * 1.0f) / f.clJ) - f.bgs;
                        pf(pointerId5);
                        this.cmb.W((int) x5, (int) y5);
                        this.cmg[pointerId5] = x5;
                        this.cmh[pointerId5] = y5;
                        this.cmi[pointerId5] = 2;
                    }
                    i++;
                }
                return true;
        }
    }
}
