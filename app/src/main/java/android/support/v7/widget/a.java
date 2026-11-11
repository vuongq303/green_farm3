package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: compiled from: AbsActionBarView.java */
/* JADX INFO: loaded from: classes.dex */
abstract class a extends ViewGroup {
    protected q rA;
    protected i rB;
    protected int rC;
    protected android.support.v4.view.ar rD;
    private boolean rE;
    private boolean rF;
    protected final b ry;
    protected final Context rz;

    a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ry = new b(this);
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true) && typedValue.resourceId != 0) {
            this.rz = new ContextThemeWrapper(context, typedValue.resourceId);
        } else {
            this.rz = context;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        aj(typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0));
        typedArrayObtainStyledAttributes.recycle();
        if (this.rB != null) {
            this.rB.onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.rE = false;
        }
        if (!this.rE) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.rE = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.rE = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.rF = false;
        }
        if (!this.rF) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.rF = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.rF = false;
        }
        return true;
    }

    public void aj(int i) {
        this.rC = i;
        requestLayout();
    }

    public android.support.v4.view.ar a(int i, long j) {
        if (this.rD != null) {
            this.rD.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            android.support.v4.view.ar arVarA = android.support.v4.view.aa.h(this).a(1.0f);
            arVarA.d(j);
            arVarA.a(this.ry.a(arVarA, i));
            return arVarA;
        }
        android.support.v4.view.ar arVarA2 = android.support.v4.view.aa.h(this).a(0.0f);
        arVarA2.d(j);
        arVarA2.a(this.ry.a(arVarA2, i));
        return arVarA2;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility()) {
            if (this.rD != null) {
                this.rD.cancel();
            }
            super.setVisibility(i);
        }
    }

    public boolean showOverflowMenu() {
        if (this.rB != null) {
            return this.rB.showOverflowMenu();
        }
        return false;
    }

    protected int a(View view, int i, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    protected static int a(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    protected int a(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = ((i3 - measuredHeight) / 2) + i2;
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }
}
