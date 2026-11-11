package javax.microedition.lcdui;

import android.view.MotionEvent;
import com.gameloft.android.wrapper.au;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
class l implements n {
    final /* synthetic */ f cmb;
    Method cmf;
    private float[] cmg;
    private float[] cmh;
    private int[] cmi;

    public l(f fVar, Method method) {
        this.cmb = fVar;
        this.cmf = method;
    }

    public void pf(int i) {
        try {
            this.cmf.invoke(this.cmb, new Integer(i));
        } catch (Exception e) {
        }
    }

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
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        int pointerCount = motionEvent.getPointerCount();
        float x = ((motionEvent.getX(actionIndex) * 1.0f) / f.clI) - f.bgr;
        float y = ((motionEvent.getY(actionIndex) * 1.0f) / f.clJ) - f.bgs;
        switch (actionMasked) {
            case 0:
                if (pointerId < f.clE) {
                    pf(pointerId);
                    this.cmb.V((int) x, (int) y);
                    this.cmg[pointerId] = x;
                    this.cmh[pointerId] = y;
                    this.cmi[pointerId] = 0;
                }
                if (pointerCount > 1) {
                    for (int i3 = 0; i3 < pointerCount; i3++) {
                        if (i3 != actionIndex) {
                            int pointerId2 = motionEvent.getPointerId(i3);
                            float x2 = ((motionEvent.getX(i3) * 1.0f) / f.clI) - f.bgr;
                            float y2 = ((motionEvent.getY(i3) * 1.0f) / f.clJ) - f.bgs;
                            if (pointerId2 < f.clE) {
                                pf(pointerId2);
                                this.cmb.V((int) x2, (int) y2);
                                this.cmg[pointerId2] = x2;
                                this.cmh[pointerId2] = y2;
                                this.cmi[pointerId2] = 0;
                            }
                        }
                    }
                }
                break;
            case 1:
                if (pointerId < f.clE) {
                    pf(pointerId);
                    if (this.cmb.clS) {
                        this.cmb.U((int) this.cmg[pointerId], (int) this.cmh[pointerId]);
                    } else {
                        this.cmb.U(-1, -1);
                    }
                    this.cmi[pointerId] = 1;
                }
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
                break;
            case 2:
                if (pointerId < f.clE) {
                    pf(pointerId);
                    this.cmb.W((int) x, (int) y);
                    this.cmg[pointerId] = x;
                    this.cmh[pointerId] = y;
                    this.cmi[pointerId] = 2;
                }
                if (pointerCount > 1) {
                    for (int i4 = 0; i4 < pointerCount; i4++) {
                        if (i4 != actionIndex) {
                            pointerId = motionEvent.getPointerId(i4);
                            float x3 = ((motionEvent.getX(i4) * 1.0f) / f.clI) - f.bgr;
                            float y3 = ((motionEvent.getY(i4) * 1.0f) / f.clJ) - f.bgs;
                            if (pointerId < f.clE) {
                                pf(pointerId);
                                this.cmb.W((int) x3, (int) y3);
                                this.cmg[pointerId] = x3;
                                this.cmh[pointerId] = y3;
                                this.cmi[pointerId] = 2;
                            }
                        }
                    }
                }
                if (au.Qj()) {
                    if (this.cmb.clS) {
                        this.cmb.U((int) this.cmg[pointerId], (int) this.cmh[pointerId]);
                    } else {
                        this.cmb.U(-1, -1);
                    }
                    this.cmi[pointerId] = 1;
                    au.bH(false);
                }
                break;
            case 3:
                String str = "Canvas FroyoMotionHandler cancel " + pointerId + " numPointers:" + pointerCount;
                if (pointerId < f.clE) {
                    pf(pointerId);
                    this.cmb.U(-1, -1);
                    this.cmi[pointerId] = 1;
                }
                if (pointerCount <= 1) {
                    return false;
                }
                for (int i5 = 0; i5 < f.clE; i5++) {
                    if (i5 != pointerId && this.cmi[i5] != 1) {
                        pf(i5);
                        this.cmb.U(-1, -1);
                        this.cmi[i5] = 1;
                    }
                }
                return false;
            case 5:
                if (pointerId < f.clE) {
                    pf(pointerId);
                    this.cmb.V((int) x, (int) y);
                    this.cmg[pointerId] = x;
                    this.cmh[pointerId] = y;
                    this.cmi[pointerId] = 0;
                }
                while (i < pointerCount) {
                    if (i != actionIndex) {
                        int pointerId3 = motionEvent.getPointerId(i);
                        float x4 = ((motionEvent.getX(i) * 1.0f) / f.clI) - f.bgr;
                        float y4 = ((motionEvent.getY(i) * 1.0f) / f.clJ) - f.bgs;
                        if (pointerId3 < f.clE) {
                            pf(pointerId3);
                            this.cmb.W((int) x4, (int) y4);
                            this.cmg[pointerId3] = x4;
                            this.cmh[pointerId3] = y4;
                            this.cmi[pointerId3] = 2;
                        }
                    }
                    i++;
                }
                break;
            case 6:
                if (pointerId < f.clE) {
                    pf(pointerId);
                    if (this.cmb.clS) {
                        this.cmb.U((int) this.cmg[pointerId], (int) this.cmh[pointerId]);
                    } else {
                        this.cmb.U(-1, -1);
                    }
                    this.cmi[pointerId] = 1;
                }
                while (i < pointerCount) {
                    if (i != actionIndex) {
                        int pointerId4 = motionEvent.getPointerId(i);
                        float x5 = ((motionEvent.getX(i) * 1.0f) / f.clI) - f.bgr;
                        float y5 = ((motionEvent.getY(i) * 1.0f) / f.clJ) - f.bgs;
                        if (pointerId4 < f.clE) {
                            pf(pointerId4);
                            this.cmb.W((int) x5, (int) y5);
                            this.cmg[pointerId4] = x5;
                            this.cmh[pointerId4] = y5;
                            this.cmi[pointerId4] = 2;
                        }
                    }
                    i++;
                }
                break;
        }
        return true;
    }
}
