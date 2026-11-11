package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AbsSavedState;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    private q rA;
    private boolean rE;
    private boolean rF;
    private Context rz;
    private int ta;
    private android.support.v7.c.a.aj tc;
    private android.support.v7.c.a.t td;
    private int vK;
    private TextView xS;
    private TextView xT;
    private ImageButton xU;
    private ImageView xV;
    private Drawable xW;
    private CharSequence xX;
    ImageButton xY;
    View xZ;
    private int ya;
    private int yb;
    int yc;
    private int yd;
    private int ye;
    private int yf;
    private int yg;
    private int yh;
    private cp yi;
    private int yj;
    private int yk;
    private CharSequence yl;
    private CharSequence ym;
    private int yn;
    private int yo;
    private final ArrayList<View> yp;
    private final ArrayList<View> yq;
    private final int[] yr;
    private final v ys;
    private df yt;
    private i yu;
    private dc yv;
    private boolean yw;
    private final Runnable yx;

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

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i);
        }
        fk();
        this.yi.Q(i == 1);
    }

    public boolean dR() {
        return getVisibility() == 0 && this.rA != null && this.rA.dX();
    }

    public boolean isOverflowMenuShowing() {
        return this.rA != null && this.rA.isOverflowMenuShowing();
    }

    public boolean dS() {
        return this.rA != null && this.rA.dS();
    }

    public boolean showOverflowMenu() {
        return this.rA != null && this.rA.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.rA != null && this.rA.hideOverflowMenu();
    }

    public void a(android.support.v7.c.a.s sVar, i iVar) {
        if (sVar != null || this.rA != null) {
            fb();
            android.support.v7.c.a.s sVarEa = this.rA.ea();
            if (sVarEa != sVar) {
                if (sVarEa != null) {
                    sVarEa.b(this.yu);
                    sVarEa.b(this.yv);
                }
                if (this.yv == null) {
                    this.yv = new dc(this);
                }
                iVar.I(true);
                if (sVar != null) {
                    sVar.a(iVar, this.rz);
                    sVar.a(this.yv, this.rz);
                } else {
                    iVar.a(this.rz, (android.support.v7.c.a.s) null);
                    this.yv.a(this.rz, (android.support.v7.c.a.s) null);
                    iVar.p(true);
                    this.yv.p(true);
                }
                this.rA.setPopupTheme(this.ta);
                this.rA.g(iVar);
                this.yu = iVar;
            }
        }
    }

    public void dismissPopupMenus() {
        if (this.rA != null) {
            this.rA.dismissPopupMenus();
        }
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            eZ();
            if (!L(this.xV)) {
                a((View) this.xV, true);
            }
        } else if (this.xV != null && L(this.xV)) {
            removeView(this.xV);
            this.yq.remove(this.xV);
        }
        if (this.xV != null) {
            this.xV.setImageDrawable(drawable);
        }
    }

    private void eZ() {
        if (this.xV == null) {
            this.xV = new AppCompatImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        return (this.yv == null || this.yv.yz == null) ? false : true;
    }

    public void collapseActionView() {
        android.support.v7.c.a.w wVar = this.yv == null ? null : this.yv.yz;
        if (wVar != null) {
            wVar.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.yl;
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.xS == null) {
                Context context = getContext();
                this.xS = new bb(context);
                this.xS.setSingleLine();
                this.xS.setEllipsize(TextUtils.TruncateAt.END);
                if (this.ya != 0) {
                    this.xS.setTextAppearance(context, this.ya);
                }
                if (this.yn != 0) {
                    this.xS.setTextColor(this.yn);
                }
            }
            if (!L(this.xS)) {
                a((View) this.xS, true);
            }
        } else if (this.xS != null && L(this.xS)) {
            removeView(this.xS);
            this.yq.remove(this.xS);
        }
        if (this.xS != null) {
            this.xS.setText(charSequence);
        }
        this.yl = charSequence;
    }

    public CharSequence getSubtitle() {
        return this.ym;
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.xT == null) {
                Context context = getContext();
                this.xT = new bb(context);
                this.xT.setSingleLine();
                this.xT.setEllipsize(TextUtils.TruncateAt.END);
                if (this.yb != 0) {
                    this.xT.setTextAppearance(context, this.yb);
                }
                if (this.yo != 0) {
                    this.xT.setTextColor(this.yo);
                }
            }
            if (!L(this.xT)) {
                a((View) this.xT, true);
            }
        } else if (this.xT != null && L(this.xT)) {
            removeView(this.xT);
            this.yq.remove(this.xT);
        }
        if (this.xT != null) {
            this.xT.setText(charSequence);
        }
        this.ym = charSequence;
    }

    public void setTitleTextAppearance(Context context, int i) {
        this.ya = i;
        if (this.xS != null) {
            this.xS.setTextAppearance(context, i);
        }
    }

    public void setSubtitleTextAppearance(Context context, int i) {
        this.yb = i;
        if (this.xT != null) {
            this.xT.setTextAppearance(context, i);
        }
    }

    public CharSequence getNavigationContentDescription() {
        if (this.xU != null) {
            return this.xU.getContentDescription();
        }
        return null;
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            fc();
        }
        if (this.xU != null) {
            this.xU.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            fc();
            if (!L(this.xU)) {
                a((View) this.xU, true);
            }
        } else if (this.xU != null && L(this.xU)) {
            removeView(this.xU);
            this.yq.remove(this.xU);
        }
        if (this.xU != null) {
            this.xU.setImageDrawable(drawable);
        }
    }

    public Drawable getNavigationIcon() {
        if (this.xU != null) {
            return this.xU.getDrawable();
        }
        return null;
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        fc();
        this.xU.setOnClickListener(onClickListener);
    }

    public Menu getMenu() {
        fa();
        return this.rA.getMenu();
    }

    private void fa() {
        fb();
        if (this.rA.ea() == null) {
            android.support.v7.c.a.s sVar = (android.support.v7.c.a.s) this.rA.getMenu();
            if (this.yv == null) {
                this.yv = new dc(this);
            }
            this.rA.I(true);
            sVar.a(this.yv, this.rz);
        }
    }

    private void fb() {
        if (this.rA == null) {
            this.rA = new q(getContext());
            this.rA.setPopupTheme(this.ta);
            this.rA.a(this.ys);
            this.rA.a(this.tc, this.td);
            dd ddVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            ddVarGenerateDefaultLayoutParams.gravity = 8388613 | (this.yc & 112);
            this.rA.setLayoutParams(ddVarGenerateDefaultLayoutParams);
            a((View) this.rA, false);
        }
    }

    public void setContentInsetsRelative(int i, int i2) {
        fk();
        this.yi.q(i, i2);
    }

    public int getContentInsetStart() {
        if (this.yi != null) {
            return this.yi.getStart();
        }
        return 0;
    }

    public int getContentInsetEnd() {
        if (this.yi != null) {
            return this.yi.getEnd();
        }
        return 0;
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.yj, 0)) : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        android.support.v7.c.a.s sVarEa;
        boolean z = (this.rA == null || (sVarEa = this.rA.ea()) == null || !sVarEa.hasVisibleItems()) ? false : true;
        if (z) {
            return Math.max(getContentInsetEnd(), Math.max(this.yk, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        return android.support.v4.view.aa.f(this) == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        return android.support.v4.view.aa.f(this) == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    private void fc() {
        if (this.xU == null) {
            this.xU = new ai(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            dd ddVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            ddVarGenerateDefaultLayoutParams.gravity = 8388611 | (this.yc & 112);
            this.xU.setLayoutParams(ddVarGenerateDefaultLayoutParams);
        }
    }

    void fd() {
        if (this.xY == null) {
            this.xY = new ai(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.xY.setImageDrawable(this.xW);
            this.xY.setContentDescription(this.xX);
            dd ddVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            ddVarGenerateDefaultLayoutParams.gravity = 8388611 | (this.yc & 112);
            ddVarGenerateDefaultLayoutParams.yA = 2;
            this.xY.setLayoutParams(ddVarGenerateDefaultLayoutParams);
            this.xY.setOnClickListener(new db(this));
        }
    }

    private void a(View view, boolean z) {
        dd ddVarGenerateLayoutParams;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            ddVarGenerateLayoutParams = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams)) {
            ddVarGenerateLayoutParams = generateLayoutParams(layoutParams);
        } else {
            ddVarGenerateLayoutParams = (dd) layoutParams;
        }
        ddVarGenerateLayoutParams.yA = 1;
        if (z && this.xZ != null) {
            view.setLayoutParams(ddVarGenerateLayoutParams);
            this.yq.add(view);
        } else {
            addView(view, ddVarGenerateLayoutParams);
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.yv != null && this.yv.yz != null) {
            savedState.yB = this.yv.yz.getItemId();
        }
        savedState.yC = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        android.support.v7.c.a.s sVarEa = this.rA != null ? this.rA.ea() : null;
        if (savedState.yB != 0 && this.yv != null && sVarEa != null && (menuItemFindItem = sVarEa.findItem(savedState.yB)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (savedState.yC) {
            fe();
        }
    }

    private void fe() {
        removeCallbacks(this.yx);
        post(this.yx);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.yx);
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

    private void c(View view, int i, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int a(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i5) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + iMax + i2, marginLayoutParams.width), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    private boolean ff() {
        if (!this.yw) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (I(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        char c;
        char c2;
        int iCombineMeasuredStates;
        int i3;
        int iMax = 0;
        int iCombineMeasuredStates2 = 0;
        int[] iArr = this.yr;
        if (du.N(this)) {
            c = 0;
            c2 = 1;
        } else {
            c = 1;
            c2 = 0;
        }
        int measuredWidth = 0;
        if (I(this.xU)) {
            c(this.xU, i, 0, i2, 0, this.yd);
            measuredWidth = this.xU.getMeasuredWidth() + J(this.xU);
            int iMax2 = Math.max(0, this.xU.getMeasuredHeight() + K(this.xU));
            iCombineMeasuredStates2 = View.combineMeasuredStates(0, this.xU.getMeasuredState());
            iMax = iMax2;
        }
        if (I(this.xY)) {
            c(this.xY, i, 0, i2, 0, this.yd);
            measuredWidth = this.xY.getMeasuredWidth() + J(this.xY);
            iMax = Math.max(iMax, this.xY.getMeasuredHeight() + K(this.xY));
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.xY.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = 0 + Math.max(currentContentInsetStart, measuredWidth);
        iArr[c2] = Math.max(0, currentContentInsetStart - measuredWidth);
        int measuredWidth2 = 0;
        if (I(this.rA)) {
            c(this.rA, i, iMax3, i2, 0, this.yd);
            measuredWidth2 = this.rA.getMeasuredWidth() + J(this.rA);
            iMax = Math.max(iMax, this.rA.getMeasuredHeight() + K(this.rA));
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.rA.getMeasuredState());
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax4 = iMax3 + Math.max(currentContentInsetEnd, measuredWidth2);
        iArr[c] = Math.max(0, currentContentInsetEnd - measuredWidth2);
        if (I(this.xZ)) {
            iMax4 += a(this.xZ, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, this.xZ.getMeasuredHeight() + K(this.xZ));
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.xZ.getMeasuredState());
        }
        if (I(this.xV)) {
            iMax4 += a(this.xV, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, this.xV.getMeasuredHeight() + K(this.xV));
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.xV.getMeasuredState());
        }
        int childCount = getChildCount();
        int i4 = 0;
        int iCombineMeasuredStates3 = iCombineMeasuredStates2;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            if (((dd) childAt.getLayoutParams()).yA != 0) {
                iCombineMeasuredStates = iCombineMeasuredStates3;
                i3 = iMax;
            } else if (I(childAt)) {
                iMax4 += a(childAt, i, iMax4, i2, 0, iArr);
                int iMax5 = Math.max(iMax, childAt.getMeasuredHeight() + K(childAt));
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates3, childAt.getMeasuredState());
                i3 = iMax5;
            } else {
                iCombineMeasuredStates = iCombineMeasuredStates3;
                i3 = iMax;
            }
            i4++;
            iCombineMeasuredStates3 = iCombineMeasuredStates;
            iMax = i3;
        }
        int iMax6 = 0;
        int measuredHeight = 0;
        int i5 = this.yg + this.yh;
        int i6 = this.ye + this.yf;
        if (I(this.xS)) {
            a(this.xS, i, iMax4 + i6, i2, i5, iArr);
            iMax6 = J(this.xS) + this.xS.getMeasuredWidth();
            measuredHeight = this.xS.getMeasuredHeight() + K(this.xS);
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, this.xS.getMeasuredState());
        }
        if (I(this.xT)) {
            iMax6 = Math.max(iMax6, a(this.xT, i, iMax4 + i6, i2, i5 + measuredHeight, iArr));
            measuredHeight += this.xT.getMeasuredHeight() + K(this.xT);
            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, this.xT.getMeasuredState());
        }
        int iMax7 = Math.max(iMax, measuredHeight);
        int paddingLeft = iMax6 + iMax4 + getPaddingLeft() + getPaddingRight();
        int paddingTop = iMax7 + getPaddingTop() + getPaddingBottom();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates3);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates3 << 16);
        if (ff()) {
            iResolveSizeAndState2 = 0;
        }
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iB;
        int iA;
        int iMin;
        int iMax;
        int measuredHeight;
        int paddingTop;
        int i5;
        int i6;
        int i7;
        int i8;
        int iMax2;
        boolean z2 = android.support.v4.view.aa.f(this) == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop2 = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i9 = width - paddingRight;
        int[] iArr = this.yr;
        iArr[1] = 0;
        iArr[0] = 0;
        int iG = android.support.v4.view.aa.g(this);
        int iMin2 = iG >= 0 ? Math.min(iG, i4 - i2) : 0;
        if (!I(this.xU)) {
            iB = i9;
            iA = paddingLeft;
        } else if (z2) {
            iB = b(this.xU, i9, iArr, iMin2);
            iA = paddingLeft;
        } else {
            iA = a(this.xU, paddingLeft, iArr, iMin2);
            iB = i9;
        }
        if (I(this.xY)) {
            if (z2) {
                iB = b(this.xY, iB, iArr, iMin2);
            } else {
                iA = a(this.xY, iA, iArr, iMin2);
            }
        }
        if (I(this.rA)) {
            if (z2) {
                iA = a(this.rA, iA, iArr, iMin2);
            } else {
                iB = b(this.rA, iB, iArr, iMin2);
            }
        }
        int currentContentInsetLeft = getCurrentContentInsetLeft();
        int currentContentInsetRight = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft - iA);
        iArr[1] = Math.max(0, currentContentInsetRight - ((width - paddingRight) - iB));
        int iMax3 = Math.max(iA, currentContentInsetLeft);
        int iMin3 = Math.min(iB, (width - paddingRight) - currentContentInsetRight);
        if (I(this.xZ)) {
            if (z2) {
                iMin3 = b(this.xZ, iMin3, iArr, iMin2);
            } else {
                iMax3 = a(this.xZ, iMax3, iArr, iMin2);
            }
        }
        if (!I(this.xV)) {
            iMin = iMin3;
            iMax = iMax3;
        } else if (z2) {
            iMin = b(this.xV, iMin3, iArr, iMin2);
            iMax = iMax3;
        } else {
            iMin = iMin3;
            iMax = a(this.xV, iMax3, iArr, iMin2);
        }
        boolean zI = I(this.xS);
        boolean zI2 = I(this.xT);
        int measuredHeight2 = 0;
        if (zI) {
            dd ddVar = (dd) this.xS.getLayoutParams();
            measuredHeight2 = 0 + ddVar.bottomMargin + ddVar.topMargin + this.xS.getMeasuredHeight();
        }
        if (zI2) {
            dd ddVar2 = (dd) this.xT.getLayoutParams();
            measuredHeight = ddVar2.bottomMargin + ddVar2.topMargin + this.xT.getMeasuredHeight() + measuredHeight2;
        } else {
            measuredHeight = measuredHeight2;
        }
        if (zI || zI2) {
            TextView textView = zI ? this.xS : this.xT;
            TextView textView2 = zI2 ? this.xT : this.xS;
            dd ddVar3 = (dd) textView.getLayoutParams();
            dd ddVar4 = (dd) textView2.getLayoutParams();
            boolean z3 = (zI && this.xS.getMeasuredWidth() > 0) || (zI2 && this.xT.getMeasuredWidth() > 0);
            switch (this.vK & 112) {
                case 48:
                    paddingTop = ddVar3.topMargin + getPaddingTop() + this.yg;
                    break;
                case 80:
                    paddingTop = (((height - paddingBottom) - ddVar4.bottomMargin) - this.yh) - measuredHeight;
                    break;
                default:
                    int i10 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                    if (i10 < ddVar3.topMargin + this.yg) {
                        iMax2 = ddVar3.topMargin + this.yg;
                    } else {
                        int i11 = (((height - paddingBottom) - measuredHeight) - i10) - paddingTop2;
                        iMax2 = i11 < ddVar3.bottomMargin + this.yh ? Math.max(0, i10 - ((ddVar4.bottomMargin + this.yh) - i11)) : i10;
                    }
                    paddingTop = paddingTop2 + iMax2;
                    break;
            }
            if (z2) {
                int i12 = (z3 ? this.ye : 0) - iArr[1];
                int iMax4 = iMin - Math.max(0, i12);
                iArr[1] = Math.max(0, -i12);
                if (zI) {
                    dd ddVar5 = (dd) this.xS.getLayoutParams();
                    int measuredWidth = iMax4 - this.xS.getMeasuredWidth();
                    int measuredHeight3 = this.xS.getMeasuredHeight() + paddingTop;
                    this.xS.layout(measuredWidth, paddingTop, iMax4, measuredHeight3);
                    int i13 = measuredWidth - this.yf;
                    paddingTop = measuredHeight3 + ddVar5.bottomMargin;
                    i7 = i13;
                } else {
                    i7 = iMax4;
                }
                if (zI2) {
                    dd ddVar6 = (dd) this.xT.getLayoutParams();
                    int i14 = ddVar6.topMargin + paddingTop;
                    int measuredWidth2 = iMax4 - this.xT.getMeasuredWidth();
                    int measuredHeight4 = this.xT.getMeasuredHeight() + i14;
                    this.xT.layout(measuredWidth2, i14, iMax4, measuredHeight4);
                    int i15 = iMax4 - this.yf;
                    int i16 = ddVar6.bottomMargin + measuredHeight4;
                    i8 = i15;
                } else {
                    i8 = iMax4;
                }
                iMin = z3 ? Math.min(i7, i8) : iMax4;
            } else {
                int i17 = (z3 ? this.ye : 0) - iArr[0];
                iMax += Math.max(0, i17);
                iArr[0] = Math.max(0, -i17);
                if (zI) {
                    dd ddVar7 = (dd) this.xS.getLayoutParams();
                    int measuredWidth3 = this.xS.getMeasuredWidth() + iMax;
                    int measuredHeight5 = this.xS.getMeasuredHeight() + paddingTop;
                    this.xS.layout(iMax, paddingTop, measuredWidth3, measuredHeight5);
                    int i18 = measuredWidth3 + this.yf;
                    int i19 = ddVar7.bottomMargin + measuredHeight5;
                    i5 = i18;
                    paddingTop = i19;
                } else {
                    i5 = iMax;
                }
                if (zI2) {
                    dd ddVar8 = (dd) this.xT.getLayoutParams();
                    int i20 = ddVar8.topMargin + paddingTop;
                    int measuredWidth4 = this.xT.getMeasuredWidth() + iMax;
                    int measuredHeight6 = this.xT.getMeasuredHeight() + i20;
                    this.xT.layout(iMax, i20, measuredWidth4, measuredHeight6);
                    int i21 = this.yf + measuredWidth4;
                    int i22 = ddVar8.bottomMargin + measuredHeight6;
                    i6 = i21;
                } else {
                    i6 = iMax;
                }
                if (z3) {
                    iMax = Math.max(i5, i6);
                }
            }
        }
        a(this.yp, 3);
        int size = this.yp.size();
        int iA2 = iMax;
        for (int i23 = 0; i23 < size; i23++) {
            iA2 = a(this.yp.get(i23), iA2, iArr, iMin2);
        }
        a(this.yp, 5);
        int size2 = this.yp.size();
        for (int i24 = 0; i24 < size2; i24++) {
            iMin = b(this.yp.get(i24), iMin, iArr, iMin2);
        }
        a(this.yp, 1);
        int iA3 = a(this.yp, iArr);
        int i25 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (iA3 / 2);
        int i26 = iA3 + i25;
        if (i25 < iA2) {
            i25 = iA2;
        } else if (i26 > iMin) {
            i25 -= i26 - iMin;
        }
        int size3 = this.yp.size();
        int iA4 = i25;
        for (int i27 = 0; i27 < size3; i27++) {
            iA4 = a(this.yp.get(i27), iA4, iArr, iMin2);
        }
        this.yp.clear();
    }

    private int a(List<View> list, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int size = list.size();
        int i3 = 0;
        int measuredWidth = 0;
        int iMax = i2;
        int iMax2 = i;
        while (i3 < size) {
            View view = list.get(i3);
            dd ddVar = (dd) view.getLayoutParams();
            int i4 = ddVar.leftMargin - iMax2;
            int i5 = ddVar.rightMargin - iMax;
            int iMax3 = Math.max(0, i4);
            int iMax4 = Math.max(0, i5);
            iMax2 = Math.max(0, -i4);
            iMax = Math.max(0, -i5);
            i3++;
            measuredWidth += view.getMeasuredWidth() + iMax3 + iMax4;
        }
        return measuredWidth;
    }

    private int a(View view, int i, int[] iArr, int i2) {
        dd ddVar = (dd) view.getLayoutParams();
        int i3 = ddVar.leftMargin - iArr[0];
        int iMax = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        int iE = e(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, iE, iMax + measuredWidth, view.getMeasuredHeight() + iE);
        return ddVar.rightMargin + measuredWidth + iMax;
    }

    private int b(View view, int i, int[] iArr, int i2) {
        dd ddVar = (dd) view.getLayoutParams();
        int i3 = ddVar.rightMargin - iArr[1];
        int iMax = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int iE = e(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, iE, iMax, view.getMeasuredHeight() + iE);
        return iMax - (ddVar.leftMargin + measuredWidth);
    }

    private int e(View view, int i) {
        int iMax;
        dd ddVar = (dd) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        switch (av(ddVar.gravity)) {
            case 48:
                return getPaddingTop() - i2;
            case 80:
                return (((getHeight() - getPaddingBottom()) - measuredHeight) - ddVar.bottomMargin) - i2;
            default:
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int height = getHeight();
                int i3 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
                if (i3 < ddVar.topMargin) {
                    iMax = ddVar.topMargin;
                } else {
                    int i4 = (((height - paddingBottom) - measuredHeight) - i3) - paddingTop;
                    iMax = i4 < ddVar.bottomMargin ? Math.max(0, i3 - (ddVar.bottomMargin - i4)) : i3;
                }
                return iMax + paddingTop;
        }
    }

    private int av(int i) {
        int i2 = i & 112;
        switch (i2) {
            case 16:
            case 48:
            case 80:
                return i2;
            default:
                return this.vK & 112;
        }
    }

    private void a(List<View> list, int i) {
        boolean z = android.support.v4.view.aa.f(this) == 1;
        int childCount = getChildCount();
        int absoluteGravity = android.support.v4.view.j.getAbsoluteGravity(i, android.support.v4.view.aa.f(this));
        list.clear();
        if (z) {
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                View childAt = getChildAt(i2);
                dd ddVar = (dd) childAt.getLayoutParams();
                if (ddVar.yA == 0 && I(childAt) && aw(ddVar.gravity) == absoluteGravity) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt2 = getChildAt(i3);
            dd ddVar2 = (dd) childAt2.getLayoutParams();
            if (ddVar2.yA == 0 && I(childAt2) && aw(ddVar2.gravity) == absoluteGravity) {
                list.add(childAt2);
            }
        }
    }

    private int aw(int i) {
        int iF = android.support.v4.view.aa.f(this);
        int absoluteGravity = android.support.v4.view.j.getAbsoluteGravity(i, iF) & 7;
        switch (absoluteGravity) {
            case 1:
            case 3:
            case 5:
                return absoluteGravity;
            case 2:
            case 4:
            default:
                return iF == 1 ? 5 : 3;
        }
    }

    private boolean I(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int J(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return android.support.v4.view.n.b(marginLayoutParams) + android.support.v4.view.n.a(marginLayoutParams);
    }

    private int K(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.bottomMargin + marginLayoutParams.topMargin;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public dd generateLayoutParams(AttributeSet attributeSet) {
        return new dd(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public dd generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof dd) {
            return new dd((dd) layoutParams);
        }
        if (layoutParams instanceof android.support.v7.app.b) {
            return new dd((android.support.v7.app.b) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new dd((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new dd(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: fg, reason: merged with bridge method [inline-methods] */
    public dd generateDefaultLayoutParams() {
        return new dd(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof dd);
    }

    public bo fh() {
        if (this.yt == null) {
            this.yt = new df(this, true);
        }
        return this.yt;
    }

    void fi() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (((dd) childAt.getLayoutParams()).yA != 2 && childAt != this.rA) {
                removeViewAt(childCount);
                this.yq.add(childAt);
            }
        }
    }

    void fj() {
        for (int size = this.yq.size() - 1; size >= 0; size--) {
            addView(this.yq.get(size));
        }
        this.yq.clear();
    }

    private boolean L(View view) {
        return view.getParent() == this || this.yq.contains(view);
    }

    public void L(boolean z) {
        this.yw = z;
        requestLayout();
    }

    public void a(android.support.v7.c.a.aj ajVar, android.support.v7.c.a.t tVar) {
        this.tc = ajVar;
        this.td = tVar;
        if (this.rA != null) {
            this.rA.a(ajVar, tVar);
        }
    }

    private void fk() {
        if (this.yi == null) {
            this.yi = new cp();
        }
    }

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new de();
        int yB;
        boolean yC;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.yB = parcel.readInt();
            this.yC = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.yB);
            parcel.writeInt(this.yC ? 1 : 0);
        }
    }
}
