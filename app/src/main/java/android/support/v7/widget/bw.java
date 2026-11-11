package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: compiled from: LinearLayoutCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class bw extends ViewGroup {
    private int mOrientation;
    private boolean vH;
    private int vI;
    private int vJ;
    private int vK;
    private int vL;
    private float vM;
    private boolean vN;
    private int[] vO;
    private int[] vP;
    private Drawable vQ;
    private int vR;
    private int vS;
    private int vT;
    private int vU;

    public bw(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public bw(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.vH = true;
        this.vI = -1;
        this.vJ = 0;
        this.vK = 8388659;
        da daVarA = da.a(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0);
        int i2 = daVarA.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = daVarA.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = daVarA.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.vM = daVarA.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.vI = daVarA.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.vN = daVarA.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(daVarA.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.vT = daVarA.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.vU = daVarA.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        daVarA.recycle();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable != this.vQ) {
            this.vQ = drawable;
            if (drawable != null) {
                this.vR = drawable.getIntrinsicWidth();
                this.vS = drawable.getIntrinsicHeight();
            } else {
                this.vR = 0;
                this.vS = 0;
            }
            setWillNotDraw(drawable == null);
            requestLayout();
        }
    }

    public int eL() {
        return this.vR;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.vQ != null) {
            if (this.mOrientation == 1) {
                b(canvas);
            } else {
                c(canvas);
            }
        }
    }

    void b(Canvas canvas) {
        int bottom;
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && aq(i)) {
                a(canvas, (virtualChildAt.getTop() - ((bx) virtualChildAt.getLayoutParams()).topMargin) - this.vS);
            }
        }
        if (aq(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.vS;
            } else {
                bottom = ((bx) virtualChildAt2.getLayoutParams()).bottomMargin + virtualChildAt2.getBottom();
            }
            a(canvas, bottom);
        }
    }

    void c(Canvas canvas) {
        int right;
        int left;
        int virtualChildCount = getVirtualChildCount();
        boolean zN = du.N(this);
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && aq(i)) {
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                if (zN) {
                    left = bxVar.rightMargin + virtualChildAt.getRight();
                } else {
                    left = (virtualChildAt.getLeft() - bxVar.leftMargin) - this.vR;
                }
                b(canvas, left);
            }
        }
        if (aq(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 == null) {
                if (zN) {
                    right = getPaddingLeft();
                } else {
                    right = (getWidth() - getPaddingRight()) - this.vR;
                }
            } else {
                bx bxVar2 = (bx) virtualChildAt2.getLayoutParams();
                if (zN) {
                    right = (virtualChildAt2.getLeft() - bxVar2.leftMargin) - this.vR;
                } else {
                    right = bxVar2.rightMargin + virtualChildAt2.getRight();
                }
            }
            b(canvas, right);
        }
    }

    void a(Canvas canvas, int i) {
        this.vQ.setBounds(getPaddingLeft() + this.vU, i, (getWidth() - getPaddingRight()) - this.vU, this.vS + i);
        this.vQ.draw(canvas);
    }

    void b(Canvas canvas, int i) {
        this.vQ.setBounds(i, getPaddingTop() + this.vU, this.vR + i, (getHeight() - getPaddingBottom()) - this.vU);
        this.vQ.draw(canvas);
    }

    public void setBaselineAligned(boolean z) {
        this.vH = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        int bottom;
        int i;
        if (this.vI < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.vI) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.vI);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.vI != 0) {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
            return -1;
        }
        int i2 = this.vJ;
        if (this.mOrientation == 1 && (i = this.vK & 112) != 48) {
            switch (i) {
                case 16:
                    bottom = i2 + (((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.vL) / 2);
                    break;
                case 80:
                    bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.vL;
                    break;
                default:
                    bottom = i2;
                    break;
            }
        } else {
            bottom = i2;
        }
        return ((bx) childAt.getLayoutParams()).topMargin + bottom + baseline;
    }

    View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            m(i, i2);
        } else {
            o(i, i2);
        }
    }

    protected boolean aq(int i) {
        if (i == 0) {
            return (this.vT & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.vT & 4) != 0;
        }
        if ((this.vT & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    void m(int i, int i2) {
        int i3;
        int i4;
        float f;
        int i5;
        boolean z;
        int iD;
        boolean z2;
        boolean z3;
        int iMax;
        int iMax2;
        int i6;
        boolean z4;
        float f2;
        this.vL = 0;
        int iMax3 = 0;
        int iCombineMeasuredStates = 0;
        int i7 = 0;
        int i8 = 0;
        boolean z5 = true;
        float f3 = 0.0f;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z6 = false;
        boolean z7 = false;
        int i9 = this.vI;
        boolean z8 = this.vN;
        int iMax4 = Integer.MIN_VALUE;
        int iD2 = 0;
        while (iD2 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(iD2);
            if (virtualChildAt == null) {
                this.vL += ar(iD2);
                i6 = iMax4;
                z4 = z7;
                f2 = f3;
                z3 = z5;
            } else if (virtualChildAt.getVisibility() == 8) {
                iD2 += d(virtualChildAt, iD2);
                i6 = iMax4;
                z4 = z7;
                f2 = f3;
                z3 = z5;
            } else {
                if (aq(iD2)) {
                    this.vL += this.vS;
                }
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                float f4 = f3 + bxVar.weight;
                if (mode2 == 1073741824 && bxVar.height == 0 && bxVar.weight > 0.0f) {
                    int i10 = this.vL;
                    this.vL = Math.max(i10, bxVar.topMargin + i10 + bxVar.bottomMargin);
                    z7 = true;
                } else {
                    int i11 = Integer.MIN_VALUE;
                    if (bxVar.height == 0 && bxVar.weight > 0.0f) {
                        i11 = 0;
                        bxVar.height = -2;
                    }
                    int i12 = i11;
                    b(virtualChildAt, iD2, i, 0, i2, f4 == 0.0f ? this.vL : 0);
                    if (i12 != Integer.MIN_VALUE) {
                        bxVar.height = i12;
                    }
                    int measuredHeight = virtualChildAt.getMeasuredHeight();
                    int i13 = this.vL;
                    this.vL = Math.max(i13, i13 + measuredHeight + bxVar.topMargin + bxVar.bottomMargin + H(virtualChildAt));
                    if (z8) {
                        iMax4 = Math.max(measuredHeight, iMax4);
                    }
                }
                if (i9 >= 0 && i9 == iD2 + 1) {
                    this.vJ = this.vL;
                }
                if (iD2 < i9 && bxVar.weight > 0.0f) {
                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                }
                boolean z9 = false;
                if (mode == 1073741824 || bxVar.width != -1) {
                    z2 = z6;
                } else {
                    z2 = true;
                    z9 = true;
                }
                int i14 = bxVar.rightMargin + bxVar.leftMargin;
                int measuredWidth = virtualChildAt.getMeasuredWidth() + i14;
                iMax3 = Math.max(iMax3, measuredWidth);
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt.getMeasuredState());
                z3 = z5 && bxVar.width == -1;
                if (bxVar.weight > 0.0f) {
                    iMax2 = Math.max(i8, z9 ? i14 : measuredWidth);
                    iMax = i7;
                } else {
                    if (!z9) {
                        i14 = measuredWidth;
                    }
                    iMax = Math.max(i7, i14);
                    iMax2 = i8;
                }
                iD2 += d(virtualChildAt, iD2);
                i6 = iMax4;
                z4 = z7;
                z6 = z2;
                f2 = f4;
                i8 = iMax2;
                i7 = iMax;
            }
            iD2++;
            iMax4 = i6;
            z7 = z4;
            f3 = f2;
            z5 = z3;
        }
        if (this.vL > 0 && aq(virtualChildCount)) {
            this.vL += this.vS;
        }
        if (z8 && (mode2 == Integer.MIN_VALUE || mode2 == 0)) {
            this.vL = 0;
            int i15 = 0;
            while (i15 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(i15);
                if (virtualChildAt2 == null) {
                    this.vL += ar(i15);
                    iD = i15;
                } else if (virtualChildAt2.getVisibility() == 8) {
                    iD = d(virtualChildAt2, i15) + i15;
                } else {
                    bx bxVar2 = (bx) virtualChildAt2.getLayoutParams();
                    int i16 = this.vL;
                    this.vL = Math.max(i16, bxVar2.bottomMargin + i16 + iMax4 + bxVar2.topMargin + H(virtualChildAt2));
                    iD = i15;
                }
                i15 = iD + 1;
            }
        }
        this.vL += getPaddingTop() + getPaddingBottom();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.vL, getSuggestedMinimumHeight()), i2, 0);
        int i17 = (16777215 & iResolveSizeAndState) - this.vL;
        if (z7 || (i17 != 0 && f3 > 0.0f)) {
            if (this.vM > 0.0f) {
                f3 = this.vM;
            }
            this.vL = 0;
            int i18 = 0;
            float f5 = f3;
            boolean z10 = z5;
            int iMax5 = i7;
            int iCombineMeasuredStates2 = iCombineMeasuredStates;
            int iMax6 = iMax3;
            while (i18 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i18);
                if (virtualChildAt3.getVisibility() == 8) {
                    f = f5;
                    z = z10;
                } else {
                    bx bxVar3 = (bx) virtualChildAt3.getLayoutParams();
                    float f6 = bxVar3.weight;
                    if (f6 > 0.0f) {
                        int i19 = (int) ((i17 * f6) / f5);
                        float f7 = f5 - f6;
                        i5 = i17 - i19;
                        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + bxVar3.leftMargin + bxVar3.rightMargin, bxVar3.width);
                        if (bxVar3.height != 0 || mode2 != 1073741824) {
                            int measuredHeight2 = i19 + virtualChildAt3.getMeasuredHeight();
                            if (measuredHeight2 < 0) {
                                measuredHeight2 = 0;
                            }
                            virtualChildAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
                        } else {
                            if (i19 <= 0) {
                                i19 = 0;
                            }
                            virtualChildAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i19, 1073741824));
                        }
                        f = f7;
                        iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, virtualChildAt3.getMeasuredState() & (-256));
                    } else {
                        f = f5;
                        i5 = i17;
                    }
                    int i20 = bxVar3.leftMargin + bxVar3.rightMargin;
                    int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i20;
                    iMax6 = Math.max(iMax6, measuredWidth2);
                    if (!(mode != 1073741824 && bxVar3.width == -1)) {
                        i20 = measuredWidth2;
                    }
                    iMax5 = Math.max(iMax5, i20);
                    boolean z11 = z10 && bxVar3.width == -1;
                    int i21 = this.vL;
                    this.vL = Math.max(i21, bxVar3.bottomMargin + virtualChildAt3.getMeasuredHeight() + i21 + bxVar3.topMargin + H(virtualChildAt3));
                    i17 = i5;
                    z = z11;
                }
                i18++;
                f5 = f;
                z10 = z;
            }
            this.vL += getPaddingTop() + getPaddingBottom();
            z5 = z10;
            i3 = iMax5;
            iCombineMeasuredStates = iCombineMeasuredStates2;
            i4 = iMax6;
        } else {
            int iMax7 = Math.max(i7, i8);
            if (z8 && mode2 != 1073741824) {
                int i22 = 0;
                while (true) {
                    int i23 = i22;
                    if (i23 >= virtualChildCount) {
                        break;
                    }
                    View virtualChildAt4 = getVirtualChildAt(i23);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((bx) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(iMax4, 1073741824));
                    }
                    i22 = i23 + 1;
                }
            }
            i3 = iMax7;
            i4 = iMax3;
        }
        if (z5 || mode == 1073741824) {
            i3 = i4;
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(i3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, iCombineMeasuredStates), iResolveSizeAndState);
        if (z6) {
            n(virtualChildCount, i2);
        }
    }

    private void n(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                if (bxVar.width == -1) {
                    int i4 = bxVar.height;
                    bxVar.height = virtualChildAt.getMeasuredHeight();
                    measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i2, 0);
                    bxVar.height = i4;
                }
            }
        }
    }

    void o(int i, int i2) {
        int i3;
        int i4;
        float f;
        int i5;
        boolean z;
        int iMax;
        float f2;
        int baseline;
        int iD;
        boolean z2;
        boolean z3;
        int iMax2;
        int iMax3;
        int i6;
        boolean z4;
        float f3;
        int baseline2;
        this.vL = 0;
        int iMax4 = 0;
        int iCombineMeasuredStates = 0;
        int i7 = 0;
        int i8 = 0;
        boolean z5 = true;
        float f4 = 0.0f;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z6 = false;
        boolean z7 = false;
        if (this.vO == null || this.vP == null) {
            this.vO = new int[4];
            this.vP = new int[4];
        }
        int[] iArr = this.vO;
        int[] iArr2 = this.vP;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z8 = this.vH;
        boolean z9 = this.vN;
        boolean z10 = mode == 1073741824;
        int iMax5 = Integer.MIN_VALUE;
        int iD2 = 0;
        while (iD2 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(iD2);
            if (virtualChildAt == null) {
                this.vL += ar(iD2);
                i6 = iMax5;
                z4 = z7;
                f3 = f4;
                z3 = z5;
            } else if (virtualChildAt.getVisibility() == 8) {
                iD2 += d(virtualChildAt, iD2);
                i6 = iMax5;
                z4 = z7;
                f3 = f4;
                z3 = z5;
            } else {
                if (aq(iD2)) {
                    this.vL += this.vR;
                }
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                float f5 = f4 + bxVar.weight;
                if (mode == 1073741824 && bxVar.width == 0 && bxVar.weight > 0.0f) {
                    if (z10) {
                        this.vL += bxVar.leftMargin + bxVar.rightMargin;
                    } else {
                        int i9 = this.vL;
                        this.vL = Math.max(i9, bxVar.leftMargin + i9 + bxVar.rightMargin);
                    }
                    if (z8) {
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        virtualChildAt.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                    } else {
                        z7 = true;
                    }
                } else {
                    int i10 = Integer.MIN_VALUE;
                    if (bxVar.width == 0 && bxVar.weight > 0.0f) {
                        i10 = 0;
                        bxVar.width = -2;
                    }
                    int i11 = i10;
                    b(virtualChildAt, iD2, i, f5 == 0.0f ? this.vL : 0, i2, 0);
                    if (i11 != Integer.MIN_VALUE) {
                        bxVar.width = i11;
                    }
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    if (z10) {
                        this.vL += bxVar.leftMargin + measuredWidth + bxVar.rightMargin + H(virtualChildAt);
                    } else {
                        int i12 = this.vL;
                        this.vL = Math.max(i12, i12 + measuredWidth + bxVar.leftMargin + bxVar.rightMargin + H(virtualChildAt));
                    }
                    if (z9) {
                        iMax5 = Math.max(measuredWidth, iMax5);
                    }
                }
                boolean z11 = false;
                if (mode2 == 1073741824 || bxVar.height != -1) {
                    z2 = z6;
                } else {
                    z2 = true;
                    z11 = true;
                }
                int i13 = bxVar.bottomMargin + bxVar.topMargin;
                int measuredHeight = virtualChildAt.getMeasuredHeight() + i13;
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt.getMeasuredState());
                if (z8 && (baseline2 = virtualChildAt.getBaseline()) != -1) {
                    int i14 = ((((bxVar.gravity < 0 ? this.vK : bxVar.gravity) & 112) >> 4) & (-2)) >> 1;
                    iArr[i14] = Math.max(iArr[i14], baseline2);
                    iArr2[i14] = Math.max(iArr2[i14], measuredHeight - baseline2);
                }
                iMax4 = Math.max(iMax4, measuredHeight);
                z3 = z5 && bxVar.height == -1;
                if (bxVar.weight > 0.0f) {
                    iMax3 = Math.max(i8, z11 ? i13 : measuredHeight);
                    iMax2 = i7;
                } else {
                    if (!z11) {
                        i13 = measuredHeight;
                    }
                    iMax2 = Math.max(i7, i13);
                    iMax3 = i8;
                }
                iD2 += d(virtualChildAt, iD2);
                i6 = iMax5;
                z4 = z7;
                z6 = z2;
                f3 = f5;
                i8 = iMax3;
                i7 = iMax2;
            }
            iD2++;
            iMax5 = i6;
            z7 = z4;
            f4 = f3;
            z5 = z3;
        }
        if (this.vL > 0 && aq(virtualChildCount)) {
            this.vL += this.vR;
        }
        int iMax6 = (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1) ? iMax4 : Math.max(iMax4, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
        if (z9 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.vL = 0;
            int i15 = 0;
            while (i15 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(i15);
                if (virtualChildAt2 == null) {
                    this.vL += ar(i15);
                    iD = i15;
                } else if (virtualChildAt2.getVisibility() == 8) {
                    iD = d(virtualChildAt2, i15) + i15;
                } else {
                    bx bxVar2 = (bx) virtualChildAt2.getLayoutParams();
                    if (z10) {
                        this.vL = bxVar2.rightMargin + bxVar2.leftMargin + iMax5 + H(virtualChildAt2) + this.vL;
                        iD = i15;
                    } else {
                        int i16 = this.vL;
                        this.vL = Math.max(i16, bxVar2.rightMargin + i16 + iMax5 + bxVar2.leftMargin + H(virtualChildAt2));
                        iD = i15;
                    }
                }
                i15 = iD + 1;
            }
        }
        this.vL += getPaddingLeft() + getPaddingRight();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.vL, getSuggestedMinimumWidth()), i, 0);
        int i17 = (16777215 & iResolveSizeAndState) - this.vL;
        if (z7 || (i17 != 0 && f4 > 0.0f)) {
            if (this.vM > 0.0f) {
                f4 = this.vM;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            int iMax7 = -1;
            this.vL = 0;
            int i18 = 0;
            float f6 = f4;
            boolean z12 = z5;
            int iMax8 = i7;
            int iCombineMeasuredStates2 = iCombineMeasuredStates;
            while (i18 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i18);
                if (virtualChildAt3 == null) {
                    f = f6;
                    i5 = i17;
                    z = z12;
                    iMax = iMax7;
                } else if (virtualChildAt3.getVisibility() == 8) {
                    f = f6;
                    i5 = i17;
                    z = z12;
                    iMax = iMax7;
                } else {
                    bx bxVar3 = (bx) virtualChildAt3.getLayoutParams();
                    float f7 = bxVar3.weight;
                    if (f7 > 0.0f) {
                        int i19 = (int) ((i17 * f7) / f6);
                        f2 = f6 - f7;
                        i17 -= i19;
                        int childMeasureSpec = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + bxVar3.topMargin + bxVar3.bottomMargin, bxVar3.height);
                        if (bxVar3.width != 0 || mode != 1073741824) {
                            int measuredWidth2 = i19 + virtualChildAt3.getMeasuredWidth();
                            if (measuredWidth2 < 0) {
                                measuredWidth2 = 0;
                            }
                            virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824), childMeasureSpec);
                        } else {
                            if (i19 <= 0) {
                                i19 = 0;
                            }
                            virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(i19, 1073741824), childMeasureSpec);
                        }
                        iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, virtualChildAt3.getMeasuredState() & (-16777216));
                    } else {
                        f2 = f6;
                    }
                    if (z10) {
                        this.vL += virtualChildAt3.getMeasuredWidth() + bxVar3.leftMargin + bxVar3.rightMargin + H(virtualChildAt3);
                    } else {
                        int i20 = this.vL;
                        this.vL = Math.max(i20, virtualChildAt3.getMeasuredWidth() + i20 + bxVar3.leftMargin + bxVar3.rightMargin + H(virtualChildAt3));
                    }
                    boolean z13 = mode2 != 1073741824 && bxVar3.height == -1;
                    int i21 = bxVar3.topMargin + bxVar3.bottomMargin;
                    int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i21;
                    iMax = Math.max(iMax7, measuredHeight2);
                    iMax8 = Math.max(iMax8, z13 ? i21 : measuredHeight2);
                    boolean z14 = z12 && bxVar3.height == -1;
                    if (z8 && (baseline = virtualChildAt3.getBaseline()) != -1) {
                        int i22 = ((((bxVar3.gravity < 0 ? this.vK : bxVar3.gravity) & 112) >> 4) & (-2)) >> 1;
                        iArr[i22] = Math.max(iArr[i22], baseline);
                        iArr2[i22] = Math.max(iArr2[i22], measuredHeight2 - baseline);
                    }
                    f = f2;
                    i5 = i17;
                    z = z14;
                }
                i18++;
                f6 = f;
                i17 = i5;
                z12 = z;
                iMax7 = iMax;
            }
            this.vL += getPaddingLeft() + getPaddingRight();
            if (iArr[1] != -1 || iArr[0] != -1 || iArr[2] != -1 || iArr[3] != -1) {
                iMax7 = Math.max(iMax7, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
            }
            z5 = z12;
            i3 = iMax8;
            iCombineMeasuredStates = iCombineMeasuredStates2;
            i4 = iMax7;
        } else {
            int iMax9 = Math.max(i7, i8);
            if (z9 && mode != 1073741824) {
                int i23 = 0;
                while (true) {
                    int i24 = i23;
                    if (i24 >= virtualChildCount) {
                        break;
                    }
                    View virtualChildAt4 = getVirtualChildAt(i24);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((bx) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax5, 1073741824), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                    i23 = i24 + 1;
                }
            }
            i3 = iMax9;
            i4 = iMax6;
        }
        if (z5 || mode2 == 1073741824) {
            i3 = i4;
        }
        setMeasuredDimension(((-16777216) & iCombineMeasuredStates) | iResolveSizeAndState, View.resolveSizeAndState(Math.max(i3 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, iCombineMeasuredStates << 16));
        if (z6) {
            p(virtualChildCount, i);
        }
    }

    private void p(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                if (bxVar.height == -1) {
                    int i4 = bxVar.width;
                    bxVar.width = virtualChildAt.getMeasuredWidth();
                    measureChildWithMargins(virtualChildAt, i2, 0, iMakeMeasureSpec, 0);
                    bxVar.width = i4;
                }
            }
        }
    }

    int d(View view, int i) {
        return 0;
    }

    int ar(int i) {
        return 0;
    }

    void b(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    int G(View view) {
        return 0;
    }

    int H(View view) {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            d(i, i2, i3, i4);
        } else {
            e(i, i2, i3, i4);
        }
    }

    void d(int i, int i2, int i3, int i4) {
        int paddingTop;
        int iD;
        int i5;
        int paddingLeft = getPaddingLeft();
        int i6 = i3 - i;
        int paddingRight = i6 - getPaddingRight();
        int paddingRight2 = (i6 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i7 = this.vK & 112;
        int i8 = this.vK & 8388615;
        switch (i7) {
            case 16:
                paddingTop = getPaddingTop() + (((i4 - i2) - this.vL) / 2);
                break;
            case 80:
                paddingTop = ((getPaddingTop() + i4) - i2) - this.vL;
                break;
            default:
                paddingTop = getPaddingTop();
                break;
        }
        int i9 = 0;
        int iH = paddingTop;
        while (i9 < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(i9);
            if (virtualChildAt == null) {
                iH += ar(i9);
                iD = i9;
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                int i10 = bxVar.gravity;
                if (i10 < 0) {
                    i10 = i8;
                }
                switch (android.support.v4.view.j.getAbsoluteGravity(i10, android.support.v4.view.aa.f(this)) & 7) {
                    case 1:
                        i5 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + bxVar.leftMargin) - bxVar.rightMargin;
                        break;
                    case 5:
                        i5 = (paddingRight - measuredWidth) - bxVar.rightMargin;
                        break;
                    default:
                        i5 = paddingLeft + bxVar.leftMargin;
                        break;
                }
                int i11 = (aq(i9) ? this.vS + iH : iH) + bxVar.topMargin;
                b(virtualChildAt, i5, i11 + G(virtualChildAt), measuredWidth, measuredHeight);
                iH = i11 + bxVar.bottomMargin + measuredHeight + H(virtualChildAt);
                iD = d(virtualChildAt, i9) + i9;
            } else {
                iD = i9;
            }
            i9 = iD + 1;
        }
    }

    void e(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int iD;
        int baseline;
        int measuredHeight;
        boolean zN = du.N(this);
        int paddingTop = getPaddingTop();
        int i7 = i4 - i2;
        int paddingBottom = i7 - getPaddingBottom();
        int paddingBottom2 = (i7 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i8 = this.vK & 8388615;
        int i9 = this.vK & 112;
        boolean z = this.vH;
        int[] iArr = this.vO;
        int[] iArr2 = this.vP;
        switch (android.support.v4.view.j.getAbsoluteGravity(i8, android.support.v4.view.aa.f(this))) {
            case 1:
                paddingLeft = getPaddingLeft() + (((i3 - i) - this.vL) / 2);
                break;
            case 5:
                paddingLeft = ((getPaddingLeft() + i3) - i) - this.vL;
                break;
            default:
                paddingLeft = getPaddingLeft();
                break;
        }
        if (!zN) {
            i5 = 1;
            i6 = 0;
        } else {
            i5 = -1;
            i6 = virtualChildCount - 1;
        }
        int i10 = 0;
        while (i10 < virtualChildCount) {
            int i11 = i6 + (i5 * i10);
            View virtualChildAt = getVirtualChildAt(i11);
            if (virtualChildAt == null) {
                paddingLeft += ar(i11);
                iD = i10;
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight2 = virtualChildAt.getMeasuredHeight();
                bx bxVar = (bx) virtualChildAt.getLayoutParams();
                if (!z || bxVar.height == -1) {
                    baseline = -1;
                } else {
                    baseline = virtualChildAt.getBaseline();
                }
                int i12 = bxVar.gravity;
                if (i12 < 0) {
                    i12 = i9;
                }
                switch (i12 & 112) {
                    case 16:
                        measuredHeight = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + bxVar.topMargin) - bxVar.bottomMargin;
                        break;
                    case 48:
                        measuredHeight = paddingTop + bxVar.topMargin;
                        if (baseline != -1) {
                            measuredHeight += iArr[1] - baseline;
                        }
                        break;
                    case 80:
                        measuredHeight = (paddingBottom - measuredHeight2) - bxVar.bottomMargin;
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline);
                        }
                        break;
                    default:
                        measuredHeight = paddingTop;
                        break;
                }
                int i13 = (aq(i11) ? this.vR + paddingLeft : paddingLeft) + bxVar.leftMargin;
                b(virtualChildAt, i13 + G(virtualChildAt), measuredHeight, measuredWidth, measuredHeight2);
                paddingLeft = i13 + bxVar.rightMargin + measuredWidth + H(virtualChildAt);
                iD = d(virtualChildAt, i11) + i10;
            } else {
                iD = i10;
            }
            i10 = iD + 1;
        }
    }

    private void b(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i + i3, i2 + i4);
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setGravity(int i) {
        if (this.vK != i) {
            int i2 = (8388615 & i) == 0 ? 8388611 | i : i;
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.vK = i2;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public bx generateLayoutParams(AttributeSet attributeSet) {
        return new bx(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: eb, reason: merged with bridge method [inline-methods] */
    public bx generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new bx(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new bx(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public bx generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new bx(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof bx;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(bw.class.getName());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(bw.class.getName());
        }
    }
}
