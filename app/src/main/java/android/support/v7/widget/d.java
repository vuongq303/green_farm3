package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/* JADX INFO: compiled from: ActionBarContextView.java */
/* JADX INFO: loaded from: classes.dex */
public class d extends a {
    private TextView jL;
    private CharSequence jp;
    private CharSequence rR;
    private View rS;
    private View rT;
    private LinearLayout rU;
    private TextView rV;
    private int rW;
    private int rX;
    private boolean rY;
    private int rZ;

    @Override // android.support.v7.widget.a
    public /* bridge */ /* synthetic */ android.support.v4.view.ar a(int i, long j) {
        return super.a(i, j);
    }

    @Override // android.support.v7.widget.a, android.view.View
    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.support.v7.widget.a, android.view.View
    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.support.v7.widget.a, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public d(Context context) {
        this(context, null);
    }

    public d(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.actionModeStyle);
    }

    public d(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        da daVarA = da.a(context, attributeSet, R.styleable.ActionMode, i, 0);
        android.support.v4.view.aa.setBackground(this, daVarA.getDrawable(R.styleable.ActionMode_background));
        this.rW = daVarA.getResourceId(R.styleable.ActionMode_titleTextStyle, 0);
        this.rX = daVarA.getResourceId(R.styleable.ActionMode_subtitleTextStyle, 0);
        this.rC = daVarA.getLayoutDimension(R.styleable.ActionMode_height, 0);
        this.rZ = daVarA.getResourceId(R.styleable.ActionMode_closeItemLayout, R.layout.abc_action_mode_close_item_material);
        daVarA.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.rB != null) {
            this.rB.hideOverflowMenu();
            this.rB.dV();
        }
    }

    @Override // android.support.v7.widget.a
    public void aj(int i) {
        this.rC = i;
    }

    public void setCustomView(View view) {
        if (this.rT != null) {
            removeView(this.rT);
        }
        this.rT = view;
        if (view != null && this.rU != null) {
            removeView(this.rU);
            this.rU = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        dF();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.rR = charSequence;
        dF();
    }

    public CharSequence getTitle() {
        return this.jp;
    }

    public CharSequence getSubtitle() {
        return this.rR;
    }

    private void dF() {
        if (this.rU == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.abc_action_bar_title_item, this);
            this.rU = (LinearLayout) getChildAt(getChildCount() - 1);
            this.jL = (TextView) this.rU.findViewById(R.id.action_bar_title);
            this.rV = (TextView) this.rU.findViewById(R.id.action_bar_subtitle);
            if (this.rW != 0) {
                this.jL.setTextAppearance(getContext(), this.rW);
            }
            if (this.rX != 0) {
                this.rV.setTextAppearance(getContext(), this.rX);
            }
        }
        this.jL.setText(this.jp);
        this.rV.setText(this.rR);
        boolean z = !TextUtils.isEmpty(this.jp);
        boolean z2 = TextUtils.isEmpty(this.rR) ? false : true;
        this.rV.setVisibility(z2 ? 0 : 8);
        this.rU.setVisibility((z || z2) ? 0 : 8);
        if (this.rU.getParent() == null) {
            addView(this.rU);
        }
    }

    public void e(android.support.v7.c.b bVar) {
        if (this.rS == null) {
            this.rS = LayoutInflater.from(getContext()).inflate(this.rZ, (ViewGroup) this, false);
            addView(this.rS);
        } else if (this.rS.getParent() == null) {
            addView(this.rS);
        }
        this.rS.findViewById(R.id.action_mode_close_button).setOnClickListener(new e(this, bVar));
        android.support.v7.c.a.s sVar = (android.support.v7.c.a.s) bVar.getMenu();
        if (this.rB != null) {
            this.rB.dU();
        }
        this.rB = new i(getContext());
        this.rB.H(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        sVar.a(this.rB, this.rz);
        this.rA = (q) this.rB.g(this);
        android.support.v4.view.aa.setBackground(this.rA, null);
        addView(this.rA, layoutParams);
    }

    public void dG() {
        if (this.rS == null) {
            dH();
        }
    }

    public void dH() {
        removeAllViews();
        this.rT = null;
        this.rA = null;
    }

    @Override // android.support.v7.widget.a
    public boolean showOverflowMenu() {
        if (this.rB != null) {
            return this.rB.showOverflowMenu();
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = this.rC > 0 ? this.rC : View.MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int i3 = size2 - paddingTop;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        if (this.rS != null) {
            int iA = a(this.rS, paddingLeft, iMakeMeasureSpec, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.rS.getLayoutParams();
            paddingLeft = iA - (marginLayoutParams.rightMargin + marginLayoutParams.leftMargin);
        }
        if (this.rA != null && this.rA.getParent() == this) {
            paddingLeft = a(this.rA, paddingLeft, iMakeMeasureSpec, 0);
        }
        if (this.rU != null && this.rT == null) {
            if (this.rY) {
                this.rU.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec);
                int measuredWidth = this.rU.getMeasuredWidth();
                boolean z = measuredWidth <= paddingLeft;
                if (z) {
                    paddingLeft -= measuredWidth;
                }
                this.rU.setVisibility(z ? 0 : 8);
            } else {
                paddingLeft = a(this.rU, paddingLeft, iMakeMeasureSpec, 0);
            }
        }
        if (this.rT != null) {
            ViewGroup.LayoutParams layoutParams = this.rT.getLayoutParams();
            int i4 = layoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (layoutParams.width >= 0) {
                paddingLeft = Math.min(layoutParams.width, paddingLeft);
            }
            this.rT.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i4), View.MeasureSpec.makeMeasureSpec(layoutParams.height >= 0 ? Math.min(layoutParams.height, i3) : i3, layoutParams.height == -2 ? Integer.MIN_VALUE : 1073741824));
        }
        if (this.rC <= 0) {
            int childCount = getChildCount();
            int i5 = 0;
            int i6 = 0;
            while (i5 < childCount) {
                int measuredHeight = getChildAt(i5).getMeasuredHeight() + paddingTop;
                if (measuredHeight <= i6) {
                    measuredHeight = i6;
                }
                i5++;
                i6 = measuredHeight;
            }
            setMeasuredDimension(size, i6);
            return;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iA;
        boolean zN = du.N(this);
        int paddingRight = zN ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        if (this.rS == null || this.rS.getVisibility() == 8) {
            iA = paddingRight;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.rS.getLayoutParams();
            int i5 = zN ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = zN ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int iA2 = a(paddingRight, i5, zN);
            iA = a(a(this.rS, iA2, paddingTop, paddingTop2, zN) + iA2, i6, zN);
        }
        if (this.rU != null && this.rT == null && this.rU.getVisibility() != 8) {
            iA += a(this.rU, iA, paddingTop, paddingTop2, zN);
        }
        if (this.rT != null) {
            int iA3 = a(this.rT, iA, paddingTop, paddingTop2, zN) + iA;
        }
        int paddingLeft = zN ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        if (this.rA != null) {
            int iA4 = a(this.rA, paddingLeft, paddingTop, paddingTop2, !zN) + paddingLeft;
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.setSource(this);
            accessibilityEvent.setClassName(getClass().getName());
            accessibilityEvent.setPackageName(getContext().getPackageName());
            accessibilityEvent.setContentDescription(this.jp);
            return;
        }
        super.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public void D(boolean z) {
        if (z != this.rY) {
            requestLayout();
        }
        this.rY = z;
    }

    public boolean isTitleOptional() {
        return this.rY;
    }
}
