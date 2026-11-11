package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* JADX INFO: compiled from: ActionMenuView.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends bw implements android.support.v7.c.a.ak, android.support.v7.c.a.u {
    private android.support.v7.c.a.s nw;
    private Context rz;
    private boolean sD;
    private int sM;
    private int ta;
    private i tb;
    private android.support.v7.c.a.aj tc;
    android.support.v7.c.a.t td;
    private boolean te;
    private int tf;
    private int tg;
    v th;

    public q(Context context) {
        this(context, null);
    }

    public q(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.sM = (int) (56.0f * f);
        this.tg = (int) (f * 4.0f);
        this.rz = context;
        this.ta = 0;
    }

    public void setPopupTheme(int i) {
        if (this.ta != i) {
            this.ta = i;
            if (i == 0) {
                this.rz = getContext();
            } else {
                this.rz = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void g(i iVar) {
        this.tb = iVar;
        this.tb.a(this);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.tb != null) {
            this.tb.p(false);
            if (this.tb.isOverflowMenuShowing()) {
                this.tb.hideOverflowMenu();
                this.tb.showOverflowMenu();
            }
        }
    }

    public void a(v vVar) {
        this.th = vVar;
    }

    @Override // android.support.v7.widget.bw, android.view.View
    protected void onMeasure(int i, int i2) {
        boolean z = this.te;
        this.te = View.MeasureSpec.getMode(i) == 1073741824;
        if (z != this.te) {
            this.tf = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.te && this.nw != null && size != this.tf) {
            this.tf = size;
            this.nw.v(true);
        }
        int childCount = getChildCount();
        if (this.te && childCount > 0) {
            k(i, i2);
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            t tVar = (t) getChildAt(i3).getLayoutParams();
            tVar.rightMargin = 0;
            tVar.leftMargin = 0;
        }
        super.onMeasure(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:142:0x02f2 A[PHI: r7
  0x02f2: PHI (r7v9 float) = (r7v8 float), (r7v17 float), (r7v17 float) binds: [B:72:0x017e, B:79:0x01a7, B:81:0x01b9] A[DONT_GENERATE, DONT_INLINE]] */
    private void k(int i, int i2) {
        long j;
        boolean z;
        float f;
        boolean z2;
        int i3;
        int i4;
        int i5;
        boolean z3;
        int i6;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int i7 = size - paddingLeft;
        int i8 = i7 / this.sM;
        int i9 = i7 % this.sM;
        if (i8 == 0) {
            setMeasuredDimension(i7, 0);
            return;
        }
        int i10 = this.sM + (i9 / i8);
        int i11 = 0;
        int iMax = 0;
        int i12 = 0;
        int i13 = 0;
        boolean z4 = false;
        long j2 = 0;
        int childCount = getChildCount();
        int i14 = 0;
        while (i14 < childCount) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() == 8) {
                z3 = z4;
                i5 = i13;
                i6 = i8;
            } else {
                boolean z5 = childAt instanceof android.support.v7.c.a.b;
                i5 = i13 + 1;
                if (z5) {
                    childAt.setPadding(this.tg, 0, this.tg, 0);
                }
                t tVar = (t) childAt.getLayoutParams();
                tVar.tn = false;
                tVar.tk = 0;
                tVar.tj = 0;
                tVar.tl = false;
                tVar.leftMargin = 0;
                tVar.rightMargin = 0;
                tVar.tm = z5 && ((android.support.v7.c.a.b) childAt).hasText();
                int iA = a(childAt, i10, tVar.ti ? 1 : i8, childMeasureSpec, paddingTop);
                iMax = Math.max(iMax, iA);
                int i15 = tVar.tl ? i12 + 1 : i12;
                z3 = tVar.ti ? true : z4;
                i6 = i8 - iA;
                int iMax2 = Math.max(i11, childAt.getMeasuredHeight());
                if (iA == 1) {
                    j2 = ((long) (1 << i14)) | j2;
                    i12 = i15;
                    i11 = iMax2;
                } else {
                    i12 = i15;
                    i11 = iMax2;
                }
            }
            i14++;
            z4 = z3;
            i13 = i5;
            i8 = i6;
        }
        boolean z6 = z4 && i13 == 2;
        boolean z7 = false;
        long j3 = j2;
        int i16 = i8;
        while (true) {
            if (i12 <= 0 || i16 <= 0) {
                j = j3;
                break;
            }
            int i17 = Integer.MAX_VALUE;
            long j4 = 0;
            int i18 = 0;
            int i19 = 0;
            while (i19 < childCount) {
                t tVar2 = (t) getChildAt(i19).getLayoutParams();
                if (!tVar2.tl) {
                    i4 = i18;
                } else if (tVar2.tj < i17) {
                    int i20 = tVar2.tj;
                    j4 = 1 << i19;
                    i4 = 1;
                    i17 = i20;
                } else if (tVar2.tj == i17) {
                    j4 |= (long) (1 << i19);
                    i4 = i18 + 1;
                } else {
                    i4 = i18;
                }
                i19++;
                i18 = i4;
            }
            j3 |= j4;
            if (i18 > i16) {
                j = j3;
                break;
            }
            int i21 = i17 + 1;
            int i22 = 0;
            int i23 = i16;
            while (i22 < childCount) {
                View childAt2 = getChildAt(i22);
                t tVar3 = (t) childAt2.getLayoutParams();
                if ((((long) (1 << i22)) & j4) == 0) {
                    if (tVar3.tj == i21) {
                        j3 |= (long) (1 << i22);
                        i3 = i23;
                    } else {
                        i3 = i23;
                    }
                } else {
                    if (z6 && tVar3.tm && i23 == 1) {
                        childAt2.setPadding(this.tg + i10, 0, this.tg, 0);
                    }
                    tVar3.tj++;
                    tVar3.tn = true;
                    i3 = i23 - 1;
                }
                i22++;
                i23 = i3;
            }
            z7 = true;
            i16 = i23;
        }
        boolean z8 = !z4 && i13 == 1;
        if (i16 <= 0 || j == 0 || (i16 >= i13 - 1 && !z8 && iMax <= 1)) {
            z = z7;
        } else {
            float fBitCount = Long.bitCount(j);
            if (z8) {
                f = fBitCount;
            } else {
                if ((1 & j) != 0 && !((t) getChildAt(0).getLayoutParams()).tm) {
                    fBitCount -= 0.5f;
                }
                if ((((long) (1 << (childCount - 1))) & j) == 0 || ((t) getChildAt(childCount - 1).getLayoutParams()).tm) {
                    f = fBitCount;
                } else {
                    f = fBitCount - 0.5f;
                }
            }
            int i24 = f > 0.0f ? (int) ((i16 * i10) / f) : 0;
            int i25 = 0;
            z = z7;
            while (i25 < childCount) {
                if ((((long) (1 << i25)) & j) == 0) {
                    z2 = z;
                } else {
                    View childAt3 = getChildAt(i25);
                    t tVar4 = (t) childAt3.getLayoutParams();
                    if (childAt3 instanceof android.support.v7.c.a.b) {
                        tVar4.tk = i24;
                        tVar4.tn = true;
                        if (i25 == 0 && !tVar4.tm) {
                            tVar4.leftMargin = (-i24) / 2;
                        }
                        z2 = true;
                    } else if (tVar4.ti) {
                        tVar4.tk = i24;
                        tVar4.tn = true;
                        tVar4.rightMargin = (-i24) / 2;
                        z2 = true;
                    } else {
                        if (i25 != 0) {
                            tVar4.leftMargin = i24 / 2;
                        }
                        if (i25 != childCount - 1) {
                            tVar4.rightMargin = i24 / 2;
                        }
                        z2 = z;
                    }
                }
                i25++;
                z = z2;
            }
        }
        if (z) {
            int i26 = 0;
            while (true) {
                int i27 = i26;
                if (i27 >= childCount) {
                    break;
                }
                View childAt4 = getChildAt(i27);
                t tVar5 = (t) childAt4.getLayoutParams();
                if (tVar5.tn) {
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec(tVar5.tk + (tVar5.tj * i10), 1073741824), childMeasureSpec);
                }
                i26 = i27 + 1;
            }
        }
        if (mode == 1073741824) {
            i11 = size2;
        }
        setMeasuredDimension(i7, i11);
    }

    static int a(View view, int i, int i2, int i3, int i4) {
        int i5;
        boolean z = false;
        t tVar = (t) view.getLayoutParams();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3));
        android.support.v7.c.a.b bVar = view instanceof android.support.v7.c.a.b ? (android.support.v7.c.a.b) view : null;
        boolean z2 = bVar != null && bVar.hasText();
        if (i2 <= 0 || (z2 && i2 < 2)) {
            i5 = 0;
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i * i2, Integer.MIN_VALUE), iMakeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            i5 = measuredWidth / i;
            if (measuredWidth % i != 0) {
                i5++;
            }
            if (z2 && i5 < 2) {
                i5 = 2;
            }
        }
        if (!tVar.ti && z2) {
            z = true;
        }
        tVar.tl = z;
        tVar.tj = i5;
        view.measure(View.MeasureSpec.makeMeasureSpec(i5 * i, 1073741824), iMakeMeasureSpec);
        return i5;
    }

    @Override // android.support.v7.widget.bw, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int width;
        int paddingLeft;
        if (!this.te) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i8 = (i4 - i2) / 2;
        int iEL = eL();
        int i9 = 0;
        int i10 = 0;
        int paddingRight = ((i3 - i) - getPaddingRight()) - getPaddingLeft();
        boolean z2 = false;
        boolean zN = du.N(this);
        int i11 = 0;
        while (i11 < childCount) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() == 8) {
                i7 = paddingRight;
            } else {
                t tVar = (t) childAt.getLayoutParams();
                if (tVar.ti) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (am(i11)) {
                        measuredWidth += iEL;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zN) {
                        paddingLeft = tVar.leftMargin + getPaddingLeft();
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - tVar.rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i12 = i8 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i12, width, measuredHeight + i12);
                    i7 = paddingRight - measuredWidth;
                    z2 = true;
                } else {
                    int measuredWidth2 = childAt.getMeasuredWidth() + tVar.leftMargin + tVar.rightMargin;
                    int i13 = i9 + measuredWidth2;
                    i7 = paddingRight - measuredWidth2;
                    if (am(i11)) {
                        i13 += iEL;
                    }
                    i10++;
                    i9 = i13;
                }
            }
            i11++;
            paddingRight = i7;
        }
        if (childCount == 1 && !z2) {
            View childAt2 = getChildAt(0);
            int measuredWidth3 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i14 = ((i3 - i) / 2) - (measuredWidth3 / 2);
            int i15 = i8 - (measuredHeight2 / 2);
            childAt2.layout(i14, i15, measuredWidth3 + i14, measuredHeight2 + i15);
            return;
        }
        int i16 = i10 - (z2 ? 0 : 1);
        int iMax = Math.max(0, i16 > 0 ? paddingRight / i16 : 0);
        if (zN) {
            int width2 = getWidth() - getPaddingRight();
            int i17 = 0;
            while (i17 < childCount) {
                View childAt3 = getChildAt(i17);
                t tVar2 = (t) childAt3.getLayoutParams();
                if (childAt3.getVisibility() == 8) {
                    i6 = width2;
                } else if (tVar2.ti) {
                    i6 = width2;
                } else {
                    int i18 = width2 - tVar2.rightMargin;
                    int measuredWidth4 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i19 = i8 - (measuredHeight3 / 2);
                    childAt3.layout(i18 - measuredWidth4, i19, i18, measuredHeight3 + i19);
                    i6 = i18 - ((tVar2.leftMargin + measuredWidth4) + iMax);
                }
                i17++;
                width2 = i6;
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        int i20 = 0;
        while (i20 < childCount) {
            View childAt4 = getChildAt(i20);
            t tVar3 = (t) childAt4.getLayoutParams();
            if (childAt4.getVisibility() == 8) {
                i5 = paddingLeft2;
            } else if (tVar3.ti) {
                i5 = paddingLeft2;
            } else {
                int i21 = paddingLeft2 + tVar3.leftMargin;
                int measuredWidth5 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i22 = i8 - (measuredHeight4 / 2);
                childAt4.layout(i21, i22, i21 + measuredWidth5, measuredHeight4 + i22);
                i5 = tVar3.rightMargin + measuredWidth5 + iMax + i21;
            }
            i20++;
            paddingLeft2 = i5;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public boolean dX() {
        return this.sD;
    }

    public void J(boolean z) {
        this.sD = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.widget.bw, android.view.ViewGroup
    /* JADX INFO: renamed from: dY, reason: merged with bridge method [inline-methods] */
    public t generateDefaultLayoutParams() {
        t tVar = new t(-2, -2);
        tVar.gravity = 16;
        return tVar;
    }

    @Override // android.support.v7.widget.bw, android.view.ViewGroup
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public t generateLayoutParams(AttributeSet attributeSet) {
        return new t(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.widget.bw, android.view.ViewGroup
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public t generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null) {
            t tVar = layoutParams instanceof t ? new t((t) layoutParams) : new t(layoutParams);
            if (tVar.gravity <= 0) {
                tVar.gravity = 16;
                return tVar;
            }
            return tVar;
        }
        return generateDefaultLayoutParams();
    }

    @Override // android.support.v7.widget.bw, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof t);
    }

    public t dZ() {
        t tVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        tVarGenerateDefaultLayoutParams.ti = true;
        return tVarGenerateDefaultLayoutParams;
    }

    @Override // android.support.v7.c.a.u
    public boolean a(android.support.v7.c.a.w wVar) {
        return this.nw.a(wVar, 0);
    }

    @Override // android.support.v7.c.a.ak
    public void h(android.support.v7.c.a.s sVar) {
        this.nw = sVar;
    }

    public Menu getMenu() {
        if (this.nw == null) {
            Context context = getContext();
            this.nw = new android.support.v7.c.a.s(context);
            this.nw.a(new u(this));
            this.tb = new i(context);
            this.tb.H(true);
            this.tb.b(this.tc != null ? this.tc : new s());
            this.nw.a(this.tb, this.rz);
            this.tb.a(this);
        }
        return this.nw;
    }

    public void a(android.support.v7.c.a.aj ajVar, android.support.v7.c.a.t tVar) {
        this.tc = ajVar;
        this.td = tVar;
    }

    public android.support.v7.c.a.s ea() {
        return this.nw;
    }

    public boolean showOverflowMenu() {
        return this.tb != null && this.tb.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.tb != null && this.tb.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.tb != null && this.tb.isOverflowMenuShowing();
    }

    public boolean dS() {
        return this.tb != null && this.tb.dS();
    }

    public void dismissPopupMenus() {
        if (this.tb != null) {
            this.tb.dU();
        }
    }

    protected boolean am(int i) {
        boolean zCI = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof r)) {
            zCI = false | ((r) childAt).cI();
        }
        return (i <= 0 || !(childAt2 instanceof r)) ? zCI : ((r) childAt2).cH() | zCI;
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public void I(boolean z) {
        this.tb.I(z);
    }
}
