package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* JADX INFO: compiled from: ActionBarContainer.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends FrameLayout {
    private int mHeight;
    Drawable qd;
    private boolean rJ;
    private View rK;
    private View rL;
    private View rM;
    Drawable rN;
    Drawable rO;
    boolean rP;
    boolean rQ;

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.rL = findViewById(R.id.action_bar);
        this.rM = findViewById(R.id.action_context_bar);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.qd != null) {
            this.qd.setVisible(z, false);
        }
        if (this.rN != null) {
            this.rN.setVisible(z, false);
        }
        if (this.rO != null) {
            this.rO.setVisible(z, false);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return (drawable == this.qd && !this.rP) || (drawable == this.rN && this.rQ) || ((drawable == this.rO && this.rP) || super.verifyDrawable(drawable));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.qd != null && this.qd.isStateful()) {
            this.qd.setState(getDrawableState());
        }
        if (this.rN != null && this.rN.isStateful()) {
            this.rN.setState(getDrawableState());
        }
        if (this.rO != null && this.rO.isStateful()) {
            this.rO.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.qd != null) {
            this.qd.jumpToCurrentState();
        }
        if (this.rN != null) {
            this.rN.jumpToCurrentState();
        }
        if (this.rO != null) {
            this.rO.jumpToCurrentState();
        }
    }

    public void C(boolean z) {
        this.rJ = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.rJ || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    public void a(cq cqVar) {
        if (this.rK != null) {
            removeView(this.rK);
        }
        this.rK = cqVar;
        if (cqVar != null) {
            addView(cqVar);
            ViewGroup.LayoutParams layoutParams = cqVar.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            cqVar.R(false);
        }
    }

    public View dE() {
        return this.rK;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 0) {
            return super.startActionModeForChild(view, callback, i);
        }
        return null;
    }

    private boolean D(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    private int E(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin + view.getMeasuredHeight() + layoutParams.topMargin;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int iE;
        if (this.rL == null && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.mHeight >= 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, View.MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.rL != null) {
            int mode = View.MeasureSpec.getMode(i2);
            if (this.rK != null && this.rK.getVisibility() != 8 && mode != 1073741824) {
                if (!D(this.rL)) {
                    iE = E(this.rL);
                } else if (!D(this.rM)) {
                    iE = E(this.rM);
                } else {
                    iE = 0;
                }
                setMeasuredDimension(getMeasuredWidth(), Math.min(iE + E(this.rK), mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i2) : Integer.MAX_VALUE));
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        boolean z3 = true;
        super.onLayout(z, i, i2, i3, i4);
        View view = this.rK;
        boolean z4 = (view == null || view.getVisibility() == 8) ? false : true;
        if (view != null && view.getVisibility() != 8) {
            int measuredHeight = getMeasuredHeight();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            view.layout(i, (measuredHeight - view.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin);
        }
        if (this.rP) {
            if (this.rO != null) {
                this.rO.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            } else {
                z3 = false;
            }
        } else {
            if (this.qd != null) {
                if (this.rL.getVisibility() == 0) {
                    this.qd.setBounds(this.rL.getLeft(), this.rL.getTop(), this.rL.getRight(), this.rL.getBottom());
                } else if (this.rM != null && this.rM.getVisibility() == 0) {
                    this.qd.setBounds(this.rM.getLeft(), this.rM.getTop(), this.rM.getRight(), this.rM.getBottom());
                } else {
                    this.qd.setBounds(0, 0, 0, 0);
                }
                z2 = true;
            } else {
                z2 = false;
            }
            this.rQ = z4;
            if (!z4 || this.rN == null) {
                z3 = z2;
            } else {
                this.rN.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
        }
        if (z3) {
            invalidate();
        }
    }
}
