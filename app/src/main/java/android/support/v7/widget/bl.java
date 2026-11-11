package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: compiled from: ContentFrameLayout.java */
/* JADX INFO: loaded from: classes.dex */
public class bl extends FrameLayout {
    private TypedValue vj;
    private TypedValue vk;
    private TypedValue vl;
    private TypedValue vm;
    private TypedValue vn;
    private TypedValue vo;
    private final Rect vp;
    private bm vq;

    public bl(Context context) {
        this(context, null);
    }

    public bl(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public bl(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.vp = new Rect();
    }

    public void c(Rect rect) {
        fitSystemWindows(rect);
    }

    public void a(bm bmVar) {
        this.vq = bmVar;
    }

    public void c(int i, int i2, int i3, int i4) {
        this.vp.set(i, i2, i3, i4);
        if (android.support.v4.view.aa.p(this)) {
            requestLayout();
        }
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:62:0x0100  */
    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        boolean z;
        int iMakeMeasureSpec;
        int fraction;
        int fraction2;
        int fraction3;
        boolean z2 = false;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean z3 = displayMetrics.widthPixels < displayMetrics.heightPixels;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode != Integer.MIN_VALUE) {
            z = false;
        } else {
            TypedValue typedValue = z3 ? this.vm : this.vl;
            if (typedValue == null || typedValue.type == 0) {
                z = false;
            } else {
                if (typedValue.type == 5) {
                    fraction3 = (int) typedValue.getDimension(displayMetrics);
                } else {
                    fraction3 = typedValue.type == 6 ? (int) typedValue.getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels) : 0;
                }
                if (fraction3 > 0) {
                    i = View.MeasureSpec.makeMeasureSpec(Math.min(fraction3 - (this.vp.left + this.vp.right), View.MeasureSpec.getSize(i)), 1073741824);
                    z = true;
                } else {
                    z = false;
                }
            }
        }
        if (mode2 == Integer.MIN_VALUE) {
            TypedValue typedValue2 = z3 ? this.vn : this.vo;
            if (typedValue2 != null && typedValue2.type != 0) {
                if (typedValue2.type == 5) {
                    fraction2 = (int) typedValue2.getDimension(displayMetrics);
                } else {
                    fraction2 = typedValue2.type == 6 ? (int) typedValue2.getFraction(displayMetrics.heightPixels, displayMetrics.heightPixels) : 0;
                }
                if (fraction2 > 0) {
                    i2 = View.MeasureSpec.makeMeasureSpec(Math.min(fraction2 - (this.vp.top + this.vp.bottom), View.MeasureSpec.getSize(i2)), 1073741824);
                }
            }
        }
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824);
        if (z || mode != Integer.MIN_VALUE) {
            iMakeMeasureSpec = iMakeMeasureSpec2;
        } else {
            TypedValue typedValue3 = z3 ? this.vk : this.vj;
            if (typedValue3 == null || typedValue3.type == 0) {
                iMakeMeasureSpec = iMakeMeasureSpec2;
            } else {
                if (typedValue3.type == 5) {
                    fraction = (int) typedValue3.getDimension(displayMetrics);
                } else {
                    fraction = typedValue3.type == 6 ? (int) typedValue3.getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels) : 0;
                }
                if (fraction > 0) {
                    fraction -= this.vp.left + this.vp.right;
                }
                if (measuredWidth < fraction) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(fraction, 1073741824);
                    z2 = true;
                } else {
                    iMakeMeasureSpec = iMakeMeasureSpec2;
                }
            }
        }
        if (z2) {
            super.onMeasure(iMakeMeasureSpec, i2);
        }
    }

    public TypedValue ey() {
        if (this.vj == null) {
            this.vj = new TypedValue();
        }
        return this.vj;
    }

    public TypedValue ez() {
        if (this.vk == null) {
            this.vk = new TypedValue();
        }
        return this.vk;
    }

    public TypedValue eA() {
        if (this.vl == null) {
            this.vl = new TypedValue();
        }
        return this.vl;
    }

    public TypedValue eB() {
        if (this.vm == null) {
            this.vm = new TypedValue();
        }
        return this.vm;
    }

    public TypedValue eC() {
        if (this.vn == null) {
            this.vn = new TypedValue();
        }
        return this.vn;
    }

    public TypedValue eD() {
        if (this.vo == null) {
            this.vo = new TypedValue();
        }
        return this.vo;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.vq != null) {
            this.vq.bV();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.vq != null) {
            this.vq.onDetachedFromWindow();
        }
    }
}
