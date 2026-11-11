package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.OverScroller;

/* JADX INFO: compiled from: ActionBarOverlayLayout.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends ViewGroup implements android.support.v4.view.v, bn {
    static final int[] sz = {R.attr.actionBarSize, android.R.attr.windowContentOverlay};
    private final android.support.v4.view.x je;
    private bo mA;
    private boolean nq;
    private int sc;
    private int sd;
    private bl se;
    c sf;
    private Drawable sg;
    private boolean sh;
    private boolean si;
    private boolean sj;
    boolean sk;
    private int sl;
    private int sm;
    private final Rect sn;
    private final Rect so;
    private final Rect sp;
    private final Rect sq;
    private final Rect sr;
    private final Rect ss;
    private final Rect st;
    private g su;
    private OverScroller sv;
    ViewPropertyAnimator sw;
    private final Runnable sx;
    private final Runnable sy;

    private void init(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(sz);
        this.sc = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.sg = typedArrayObtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.sg == null);
        typedArrayObtainStyledAttributes.recycle();
        this.sh = context.getApplicationInfo().targetSdkVersion < 19;
        this.sv = new OverScroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dM();
    }

    public void a(g gVar) {
        this.su = gVar;
        if (getWindowToken() != null) {
            this.su.onWindowVisibilityChanged(this.sd);
            if (this.sm != 0) {
                onWindowSystemUiVisibilityChanged(this.sm);
                android.support.v4.view.aa.k(this);
            }
        }
    }

    public void E(boolean z) {
        this.si = z;
        this.sh = z && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean dI() {
        return this.si;
    }

    public void F(boolean z) {
        this.sj = z;
    }

    public void G(boolean z) {
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        init(getContext());
        android.support.v4.view.aa.k(this);
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i);
        }
        dK();
        int i2 = this.sm ^ i;
        this.sm = i;
        boolean z = (i & 4) == 0;
        boolean z2 = (i & 256) != 0;
        if (this.su != null) {
            this.su.k(z2 ? false : true);
            if (z || !z2) {
                this.su.cd();
            } else {
                this.su.cf();
            }
        }
        if ((i2 & 256) != 0 && this.su != null) {
            android.support.v4.view.aa.k(this);
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.sd = i;
        if (this.su != null) {
            this.su.onWindowVisibilityChanged(i);
        }
    }

    private boolean a(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        h hVar = (h) view.getLayoutParams();
        if (z && hVar.leftMargin != rect.left) {
            hVar.leftMargin = rect.left;
            z5 = true;
        }
        if (z2 && hVar.topMargin != rect.top) {
            hVar.topMargin = rect.top;
            z5 = true;
        }
        if (z4 && hVar.rightMargin != rect.right) {
            hVar.rightMargin = rect.right;
            z5 = true;
        }
        if (!z3 || hVar.bottomMargin == rect.bottom) {
            return z5;
        }
        hVar.bottomMargin = rect.bottom;
        return true;
    }

    @Override // android.view.View
    protected boolean fitSystemWindows(Rect rect) {
        dK();
        if ((android.support.v4.view.aa.j(this) & 256) != 0) {
        }
        boolean zA = a(this.sf, rect, true, true, false, true);
        this.sq.set(rect);
        du.a(this, this.sq, this.sn);
        if (!this.sr.equals(this.sq)) {
            this.sr.set(this.sq);
            zA = true;
        }
        if (!this.so.equals(this.sn)) {
            this.so.set(this.sn);
            zA = true;
        }
        if (zA) {
            requestLayout();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: dJ, reason: merged with bridge method [inline-methods] */
    public h generateDefaultLayoutParams() {
        return new h(-1, -1);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public h generateLayoutParams(AttributeSet attributeSet) {
        return new h(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new h(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof h;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int measuredHeight;
        dK();
        measureChildWithMargins(this.sf, i, 0, i2, 0);
        h hVar = (h) this.sf.getLayoutParams();
        int iMax = Math.max(0, this.sf.getMeasuredWidth() + hVar.leftMargin + hVar.rightMargin);
        int iMax2 = Math.max(0, hVar.bottomMargin + this.sf.getMeasuredHeight() + hVar.topMargin);
        int iCombineMeasuredStates = View.combineMeasuredStates(0, this.sf.getMeasuredState());
        boolean z = (android.support.v4.view.aa.j(this) & 256) != 0;
        if (z) {
            measuredHeight = this.sc;
            if (this.sj && this.sf.dE() != null) {
                measuredHeight += this.sc;
            }
        } else {
            measuredHeight = this.sf.getVisibility() != 8 ? this.sf.getMeasuredHeight() : 0;
        }
        this.sp.set(this.sn);
        this.ss.set(this.sq);
        if (!this.si && !z) {
            Rect rect = this.sp;
            rect.top = measuredHeight + rect.top;
            this.sp.bottom += 0;
        } else {
            Rect rect2 = this.ss;
            rect2.top = measuredHeight + rect2.top;
            this.ss.bottom += 0;
        }
        a(this.se, this.sp, true, true, true, true);
        if (!this.st.equals(this.ss)) {
            this.st.set(this.ss);
            this.se.c(this.ss);
        }
        measureChildWithMargins(this.se, i, 0, i2, 0);
        h hVar2 = (h) this.se.getLayoutParams();
        int iMax3 = Math.max(iMax, this.se.getMeasuredWidth() + hVar2.leftMargin + hVar2.rightMargin);
        int iMax4 = Math.max(iMax2, hVar2.bottomMargin + this.se.getMeasuredHeight() + hVar2.topMargin);
        int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.se.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(iMax4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = (i3 - i) - getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                h hVar = (h) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = hVar.leftMargin + paddingLeft;
                int i7 = hVar.topMargin + paddingTop;
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7);
            }
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.sg != null && !this.sh) {
            int bottom = this.sf.getVisibility() == 0 ? (int) (this.sf.getBottom() + this.sf.getTranslationY() + 0.5f) : 0;
            this.sg.setBounds(0, bottom, getWidth(), this.sg.getIntrinsicHeight() + bottom);
            this.sg.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.sf.getVisibility() != 0) {
            return false;
        }
        return this.nq;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.je.onNestedScrollAccepted(view, view2, i);
        this.sl = dL();
        dM();
        if (this.su != null) {
            this.su.ch();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        this.sl += i2;
        ak(this.sl);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onStopNestedScroll(View view) {
        if (this.nq && !this.sk) {
            if (this.sl <= this.sf.getHeight()) {
                dN();
            } else {
                dO();
            }
        }
        if (this.su != null) {
            this.su.ci();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.nq || !z) {
            return false;
        }
        if (h(f, f2)) {
            dQ();
        } else {
            dP();
        }
        this.sk = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.je.getNestedScrollAxes();
    }

    void dK() {
        if (this.se == null) {
            this.se = (bl) findViewById(R.id.action_bar_activity_content);
            this.sf = (c) findViewById(R.id.action_bar_container);
            this.mA = A(findViewById(R.id.action_bar));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private bo A(View view) {
        if (view instanceof bo) {
            return (bo) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).fh();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.nq) {
            this.nq = z;
            if (!z) {
                dM();
                ak(0);
            }
        }
    }

    public int dL() {
        if (this.sf != null) {
            return -((int) this.sf.getTranslationY());
        }
        return 0;
    }

    public void ak(int i) {
        dM();
        this.sf.setTranslationY(-Math.max(0, Math.min(i, this.sf.getHeight())));
    }

    void dM() {
        removeCallbacks(this.sx);
        removeCallbacks(this.sy);
        if (this.sw != null) {
            this.sw.cancel();
        }
    }

    private void dN() {
        dM();
        postDelayed(this.sx, 600L);
    }

    private void dO() {
        dM();
        postDelayed(this.sy, 600L);
    }

    private void dP() {
        dM();
        this.sx.run();
    }

    private void dQ() {
        dM();
        this.sy.run();
    }

    private boolean h(float f, float f2) {
        this.sv.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.sv.getFinalY() > this.sf.getHeight();
    }

    @Override // android.support.v7.widget.bn
    public void b(Window.Callback callback) {
        dK();
        this.mA.b(callback);
    }

    @Override // android.support.v7.widget.bn
    public void h(CharSequence charSequence) {
        dK();
        this.mA.h(charSequence);
    }

    @Override // android.support.v7.widget.bn
    public void al(int i) {
        dK();
        switch (i) {
            case 2:
                this.mA.eF();
                break;
            case 5:
                this.mA.eG();
                break;
            case 109:
                E(true);
                break;
        }
    }

    @Override // android.support.v7.widget.bn
    public boolean dR() {
        dK();
        return this.mA.dR();
    }

    @Override // android.support.v7.widget.bn
    public boolean isOverflowMenuShowing() {
        dK();
        return this.mA.isOverflowMenuShowing();
    }

    @Override // android.support.v7.widget.bn
    public boolean dS() {
        dK();
        return this.mA.dS();
    }

    @Override // android.support.v7.widget.bn
    public boolean showOverflowMenu() {
        dK();
        return this.mA.showOverflowMenu();
    }

    @Override // android.support.v7.widget.bn
    public boolean hideOverflowMenu() {
        dK();
        return this.mA.hideOverflowMenu();
    }

    @Override // android.support.v7.widget.bn
    public void dT() {
        dK();
        this.mA.dT();
    }

    @Override // android.support.v7.widget.bn
    public void a(Menu menu, android.support.v7.c.a.aj ajVar) {
        dK();
        this.mA.a(menu, ajVar);
    }

    @Override // android.support.v7.widget.bn
    public void bU() {
        dK();
        this.mA.dismissPopupMenus();
    }
}
