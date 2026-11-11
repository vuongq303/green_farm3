package android.support.v4.widget;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.aa;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import com.facebook.appevents.codeless.internal.Constants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NestedScrollView extends FrameLayout implements android.support.v4.view.t, android.support.v4.view.v {
    private static final q jc = new q();
    private static final int[] jd = {R.attr.fillViewport};
    private long iG;
    private final Rect iH;
    private OverScroller iI;
    private EdgeEffect iJ;
    private EdgeEffect iK;
    private int iL;
    private boolean iM;
    private boolean iN;
    private View iO;
    private boolean iP;
    private VelocityTracker iQ;
    private boolean iR;
    private boolean iS;
    private int iT;
    private int iU;
    private int iV;
    private int iW;
    private final int[] iX;
    private final int[] iY;
    private int iZ;
    private int ja;
    private SavedState jb;
    private final android.support.v4.view.x je;
    private final android.support.v4.view.u jf;
    private float jg;
    private r jh;

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        this.jf.setNestedScrollingEnabled(z);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.jf.isNestedScrollingEnabled();
    }

    @Override // android.view.View
    public boolean startNestedScroll(int i) {
        return this.jf.startNestedScroll(i);
    }

    public boolean e(int i, int i2) {
        return this.jf.e(i, i2);
    }

    @Override // android.view.View, android.support.v4.view.s
    public void stopNestedScroll() {
        this.jf.stopNestedScroll();
    }

    public void w(int i) {
        this.jf.w(i);
    }

    @Override // android.view.View
    public boolean hasNestedScrollingParent() {
        return this.jf.hasNestedScrollingParent();
    }

    public boolean v(int i) {
        return this.jf.v(i);
    }

    @Override // android.view.View
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.jf.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    public boolean a(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        return this.jf.a(i, i2, i3, i4, iArr, i5);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.jf.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        return this.jf.a(i, i2, iArr, iArr2, i3);
    }

    @Override // android.view.View
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.jf.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.jf.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.je.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onStopNestedScroll(View view) {
        this.je.onStopNestedScroll(view);
        stopNestedScroll();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int scrollY = getScrollY();
        scrollBy(0, i4);
        int scrollY2 = getScrollY() - scrollY;
        dispatchNestedScroll(0, scrollY2, 0, i4 - scrollY2, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        dispatchNestedPreScroll(i, i2, iArr, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        M((int) f2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.v
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.je.getNestedScrollAxes();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (0.5f * getHeight());
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    public void a(r rVar) {
        this.jh = rVar;
    }

    private boolean be() {
        View childAt = getChildAt(0);
        if (childAt != null) {
            return getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom();
        }
        return false;
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.jh != null) {
            this.jh.a(this, i, i2, i3, i4);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.iR && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int measuredHeight = getMeasuredHeight();
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), ((FrameLayout.LayoutParams) childAt.getLayoutParams()).width), View.MeasureSpec.makeMeasureSpec((measuredHeight - getPaddingTop()) - getPaddingBottom(), 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0045 A[FALL_THROUGH] */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean zFullScroll;
        this.iH.setEmpty();
        if (!be()) {
            if (!isFocused() || keyEvent.getKeyCode() == 4) {
                return false;
            }
            View viewFindFocus = findFocus();
            if (viewFindFocus == this) {
                viewFindFocus = null;
            }
            View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, 130);
            return (viewFindNextFocus == null || viewFindNextFocus == this || !viewFindNextFocus.requestFocus(130)) ? false : true;
        }
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 19:
                    if (!keyEvent.isAltPressed()) {
                        zFullScroll = arrowScroll(33);
                    } else {
                        zFullScroll = fullScroll(33);
                    }
                    break;
                case 20:
                    if (!keyEvent.isAltPressed()) {
                        zFullScroll = arrowScroll(130);
                    } else {
                        zFullScroll = fullScroll(130);
                    }
                    break;
                case 62:
                    pageScroll(keyEvent.isShiftPressed() ? 33 : 130);
                default:
                    zFullScroll = false;
                    break;
            }
        } else {
            zFullScroll = false;
        }
        return zFullScroll;
    }

    private boolean g(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        return i2 >= childAt.getTop() - scrollY && i2 < childAt.getBottom() - scrollY && i >= childAt.getLeft() && i < childAt.getRight();
    }

    private void bf() {
        if (this.iQ == null) {
            this.iQ = VelocityTracker.obtain();
        } else {
            this.iQ.clear();
        }
    }

    private void bg() {
        if (this.iQ == null) {
            this.iQ = VelocityTracker.obtain();
        }
    }

    private void bh() {
        if (this.iQ != null) {
            this.iQ.recycle();
            this.iQ = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            bh();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 2 && this.iP) {
            return true;
        }
        switch (action & 255) {
            case 0:
                int y = (int) motionEvent.getY();
                if (!g((int) motionEvent.getX(), y)) {
                    this.iP = false;
                    bh();
                } else {
                    this.iL = y;
                    this.iW = motionEvent.getPointerId(0);
                    bf();
                    this.iQ.addMovement(motionEvent);
                    this.iI.computeScrollOffset();
                    this.iP = this.iI.isFinished() ? false : true;
                    e(2, 0);
                }
                break;
            case 1:
            case 3:
                this.iP = false;
                this.iW = -1;
                bh();
                if (this.iI.springBack(getScrollX(), getScrollY(), 0, 0, 0, bj())) {
                    aa.e(this);
                }
                w(0);
                break;
            case 2:
                int i = this.iW;
                if (i != -1) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(i);
                    if (iFindPointerIndex == -1) {
                        Log.e("NestedScrollView", "Invalid pointerId=" + i + " in onInterceptTouchEvent");
                    } else {
                        int y2 = (int) motionEvent.getY(iFindPointerIndex);
                        if (Math.abs(y2 - this.iL) > this.iT && (getNestedScrollAxes() & 2) == 0) {
                            this.iP = true;
                            this.iL = y2;
                            bg();
                            this.iQ.addMovement(motionEvent);
                            this.iZ = 0;
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                break;
            case 6:
                a(motionEvent);
                break;
        }
        return this.iP;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent;
        bg();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.iZ = 0;
        }
        motionEventObtain.offsetLocation(0.0f, this.iZ);
        switch (actionMasked) {
            case 0:
                if (getChildCount() == 0) {
                    return false;
                }
                boolean z = !this.iI.isFinished();
                this.iP = z;
                if (z && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.iI.isFinished()) {
                    this.iI.abortAnimation();
                }
                this.iL = (int) motionEvent.getY();
                this.iW = motionEvent.getPointerId(0);
                e(2, 0);
                break;
                break;
            case 1:
                VelocityTracker velocityTracker = this.iQ;
                velocityTracker.computeCurrentVelocity(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.iV);
                int yVelocity = (int) velocityTracker.getYVelocity(this.iW);
                if (Math.abs(yVelocity) > this.iU) {
                    M(-yVelocity);
                } else if (this.iI.springBack(getScrollX(), getScrollY(), 0, 0, 0, bj())) {
                    aa.e(this);
                }
                this.iW = -1;
                bk();
                break;
            case 2:
                int iFindPointerIndex = motionEvent.findPointerIndex(this.iW);
                if (iFindPointerIndex == -1) {
                    Log.e("NestedScrollView", "Invalid pointerId=" + this.iW + " in onTouchEvent");
                } else {
                    int y = (int) motionEvent.getY(iFindPointerIndex);
                    int i = this.iL - y;
                    if (a(0, i, this.iY, this.iX, 0)) {
                        i -= this.iY[1];
                        motionEventObtain.offsetLocation(0.0f, this.iX[1]);
                        this.iZ += this.iX[1];
                    }
                    if (!this.iP && Math.abs(i) > this.iT) {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.iP = true;
                        if (i > 0) {
                            i -= this.iT;
                        } else {
                            i += this.iT;
                        }
                    }
                    if (this.iP) {
                        this.iL = y - this.iX[1];
                        int scrollY = getScrollY();
                        int iBj = bj();
                        int overScrollMode = getOverScrollMode();
                        boolean z2 = overScrollMode == 0 || (overScrollMode == 1 && iBj > 0);
                        if (a(0, i, 0, getScrollY(), 0, iBj, 0, 0, true) && !v(0)) {
                            this.iQ.clear();
                        }
                        int scrollY2 = getScrollY() - scrollY;
                        if (a(0, scrollY2, 0, i - scrollY2, this.iX, 0)) {
                            this.iL -= this.iX[1];
                            motionEventObtain.offsetLocation(0.0f, this.iX[1]);
                            this.iZ += this.iX[1];
                        } else if (z2) {
                            bl();
                            int i2 = scrollY + i;
                            if (i2 < 0) {
                                h.a(this.iJ, i / getHeight(), motionEvent.getX(iFindPointerIndex) / getWidth());
                                if (!this.iK.isFinished()) {
                                    this.iK.onRelease();
                                }
                            } else if (i2 > iBj) {
                                h.a(this.iK, i / getHeight(), 1.0f - (motionEvent.getX(iFindPointerIndex) / getWidth()));
                                if (!this.iJ.isFinished()) {
                                    this.iJ.onRelease();
                                }
                            }
                            if (this.iJ != null && (!this.iJ.isFinished() || !this.iK.isFinished())) {
                                aa.e(this);
                            }
                        }
                    }
                }
                break;
            case 3:
                if (this.iP && getChildCount() > 0 && this.iI.springBack(getScrollX(), getScrollY(), 0, 0, 0, bj())) {
                    aa.e(this);
                }
                this.iW = -1;
                bk();
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.iL = (int) motionEvent.getY(actionIndex);
                this.iW = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                a(motionEvent);
                this.iL = (int) motionEvent.getY(motionEvent.findPointerIndex(this.iW));
                break;
        }
        if (this.iQ != null) {
            this.iQ.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.iW) {
            int i = actionIndex == 0 ? 1 : 0;
            this.iL = (int) motionEvent.getY(i);
            this.iW = motionEvent.getPointerId(i);
            if (this.iQ != null) {
                this.iQ.clear();
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 8:
                if (this.iP) {
                    return false;
                }
                float axisValue = motionEvent.getAxisValue(9);
                if (axisValue == 0.0f) {
                    return false;
                }
                int iBi = (int) (axisValue * bi());
                int iBj = bj();
                int scrollY = getScrollY();
                int i = scrollY - iBi;
                if (i < 0) {
                    iBj = 0;
                } else if (i <= iBj) {
                    iBj = i;
                }
                if (iBj == scrollY) {
                    return false;
                }
                super.scrollTo(getScrollX(), iBj);
                return true;
            default:
                return false;
        }
    }

    private float bi() {
        if (this.jg == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.jg = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.jg;
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    boolean a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        boolean z3;
        int overScrollMode = getOverScrollMode();
        boolean z4 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean z5 = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean z6 = overScrollMode == 0 || (overScrollMode == 1 && z4);
        boolean z7 = overScrollMode == 0 || (overScrollMode == 1 && z5);
        int i9 = i3 + i;
        if (!z6) {
            i7 = 0;
        }
        int i10 = i4 + i2;
        if (!z7) {
            i8 = 0;
        }
        int i11 = -i7;
        int i12 = i7 + i5;
        int i13 = -i8;
        int i14 = i8 + i6;
        if (i9 > i12) {
            z2 = true;
        } else if (i9 >= i11) {
            z2 = false;
            i12 = i9;
        } else {
            z2 = true;
            i12 = i11;
        }
        if (i10 > i14) {
            z3 = true;
        } else if (i10 >= i13) {
            z3 = false;
            i14 = i10;
        } else {
            z3 = true;
            i14 = i13;
        }
        if (z3 && !v(1)) {
            this.iI.springBack(i12, i14, 0, 0, 0, bj());
        }
        onOverScrolled(i12, i14, z2, z3);
        return z2 || z3;
    }

    int bj() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
        }
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0055  */
    private View a(boolean z, int i, int i2) {
        boolean z2;
        ArrayList focusables = getFocusables(2);
        View view = null;
        int size = focusables.size();
        int i3 = 0;
        boolean z3 = false;
        while (i3 < size) {
            View view2 = (View) focusables.get(i3);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i >= bottom || top >= i2) {
                z2 = z3;
                view2 = view;
            } else {
                z2 = i < top && bottom < i2;
                if (view != null) {
                    boolean z4 = (z && top < view.getTop()) || (!z && bottom > view.getBottom());
                    if (z3) {
                        if (z2 && z4) {
                            z2 = z3;
                        } else {
                            z2 = z3;
                            view2 = view;
                        }
                    } else if (z2) {
                        z2 = true;
                    } else if (z4) {
                        z2 = z3;
                    } else {
                        z2 = z3;
                        view2 = view;
                    }
                }
            }
            i3++;
            z3 = z2;
            view = view2;
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 130;
        int height = getHeight();
        if (z) {
            this.iH.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                if (this.iH.top + height > childAt.getBottom()) {
                    this.iH.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.iH.top = getScrollY() - height;
            if (this.iH.top < 0) {
                this.iH.top = 0;
            }
        }
        this.iH.bottom = this.iH.top + height;
        return b(i, this.iH.top, this.iH.bottom);
    }

    public boolean fullScroll(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        this.iH.top = 0;
        this.iH.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            this.iH.bottom = getChildAt(childCount - 1).getBottom() + getPaddingBottom();
            this.iH.top = this.iH.bottom - height;
        }
        return b(i, this.iH.top, this.iH.bottom);
    }

    private boolean b(int i, int i2, int i3) {
        boolean z = false;
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = scrollY + height;
        boolean z2 = i == 33;
        View viewA = a(z2, i2, i3);
        if (viewA == null) {
            viewA = this;
        }
        if (i2 < scrollY || i3 > i4) {
            L(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        }
        if (viewA != findFocus()) {
            viewA.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (viewFindNextFocus != null && b(viewFindNextFocus, maxScrollAmount, getHeight())) {
            viewFindNextFocus.getDrawingRect(this.iH);
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.iH);
            L(computeScrollDeltaToGetChildRectOnScreen(this.iH));
            viewFindNextFocus.requestFocus(i);
        } else {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == 130 && getChildCount() > 0) {
                int bottom = getChildAt(0).getBottom();
                int scrollY = (getScrollY() + getHeight()) - getPaddingBottom();
                if (bottom - scrollY < maxScrollAmount) {
                    maxScrollAmount = bottom - scrollY;
                }
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            L(maxScrollAmount);
        }
        if (viewFindFocus != null && viewFindFocus.isFocused() && v(viewFindFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    private boolean v(View view) {
        return !b(view, 0, getHeight());
    }

    private boolean b(View view, int i, int i2) {
        view.getDrawingRect(this.iH);
        offsetDescendantRectToMyCoords(view, this.iH);
        return this.iH.bottom + i >= getScrollY() && this.iH.top - i <= getScrollY() + i2;
    }

    private void L(int i) {
        if (i != 0) {
            if (this.iS) {
                smoothScrollBy(0, i);
            } else {
                scrollBy(0, i);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.iG > 250) {
                int iMax = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                int scrollY = getScrollY();
                this.iI.startScroll(getScrollX(), scrollY, 0, Math.max(0, Math.min(scrollY + i2, iMax)) - scrollY);
                aa.e(this);
            } else {
                if (!this.iI.isFinished()) {
                    this.iI.abortAnimation();
                }
                scrollBy(i, i2);
            }
            this.iG = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - getScrollX(), i2 - getScrollY());
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        int bottom = getChildAt(0).getBottom();
        int scrollY = getScrollY();
        int iMax = Math.max(0, bottom - height);
        if (scrollY < 0) {
            return bottom - scrollY;
        }
        if (scrollY > iMax) {
            return bottom + (scrollY - iMax);
        }
        return bottom;
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override // android.view.ViewGroup
    protected void measureChild(View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // android.view.ViewGroup
    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.bottomMargin + marginLayoutParams.topMargin, 0));
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.iI.computeScrollOffset()) {
            this.iI.getCurrX();
            int currY = this.iI.getCurrY();
            int i = currY - this.ja;
            if (a(0, i, this.iY, null, 1)) {
                i -= this.iY[1];
            }
            if (i != 0) {
                int iBj = bj();
                int scrollY = getScrollY();
                a(0, i, getScrollX(), scrollY, 0, iBj, 0, 0, false);
                int scrollY2 = getScrollY() - scrollY;
                if (!a(0, scrollY2, 0, i - scrollY2, null, 1)) {
                    int overScrollMode = getOverScrollMode();
                    if (overScrollMode == 0 || (overScrollMode == 1 && iBj > 0)) {
                        bl();
                        if (currY <= 0 && scrollY > 0) {
                            this.iJ.onAbsorb((int) this.iI.getCurrVelocity());
                        } else if (currY >= iBj && scrollY < iBj) {
                            this.iK.onAbsorb((int) this.iI.getCurrVelocity());
                        }
                    }
                }
            }
            this.ja = currY;
            aa.e(this);
            return;
        }
        if (v(1)) {
            w(1);
        }
        this.ja = 0;
    }

    private void w(View view) {
        view.getDrawingRect(this.iH);
        offsetDescendantRectToMyCoords(view, this.iH);
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.iH);
        if (iComputeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
        }
    }

    private boolean a(Rect rect, boolean z) {
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = iComputeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
            }
        }
        return z2;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int iMax;
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = (rect.top - scrollY) + 0;
            } else {
                i2 = (rect.bottom - i3) + 0;
            }
            iMax = Math.min(i2, getChildAt(0).getBottom() - i3);
        } else if (rect.top >= scrollY || rect.bottom >= i3) {
            iMax = 0;
        } else {
            if (rect.height() > height) {
                i = 0 - (i3 - rect.bottom);
            } else {
                i = 0 - (scrollY - rect.top);
            }
            iMax = Math.max(i, -getScrollY());
        }
        return iMax;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (!this.iM) {
            w(view2);
        } else {
            this.iO = view2;
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        View viewFindNextFocusFromRect;
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        if (rect == null) {
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocus(this, null, i);
        } else {
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (viewFindNextFocusFromRect == null || v(viewFindNextFocusFromRect)) {
            return false;
        }
        return viewFindNextFocusFromRect.requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return a(rect, z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.iM = true;
        super.requestLayout();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.iM = false;
        if (this.iO != null && a(this.iO, this)) {
            w(this.iO);
        }
        this.iO = null;
        if (!this.iN) {
            if (this.jb != null) {
                scrollTo(getScrollX(), this.jb.ji);
                this.jb = null;
            }
            int iMax = Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - (((i4 - i2) - getPaddingBottom()) - getPaddingTop()));
            if (getScrollY() > iMax) {
                scrollTo(getScrollX(), iMax);
            } else if (getScrollY() < 0) {
                scrollTo(getScrollX(), 0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.iN = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.iN = false;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View viewFindFocus = findFocus();
        if (viewFindFocus != null && this != viewFindFocus && b(viewFindFocus, 0, i4)) {
            viewFindFocus.getDrawingRect(this.iH);
            offsetDescendantRectToMyCoords(viewFindFocus, this.iH);
            L(computeScrollDeltaToGetChildRectOnScreen(this.iH));
        }
    }

    private static boolean a(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && a((View) parent, view2);
    }

    public void fling(int i) {
        if (getChildCount() > 0) {
            e(2, 1);
            this.iI.fling(getScrollX(), getScrollY(), 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.ja = getScrollY();
            aa.e(this);
        }
    }

    private void M(int i) {
        int scrollY = getScrollY();
        boolean z = (scrollY > 0 || i > 0) && (scrollY < bj() || i < 0);
        if (!dispatchNestedPreFling(0.0f, i)) {
            dispatchNestedFling(0.0f, i, z);
            fling(i);
        }
    }

    private void bk() {
        this.iP = false;
        bh();
        w(0);
        if (this.iJ != null) {
            this.iJ.onRelease();
            this.iK.onRelease();
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int iC = c(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int iC2 = c(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (iC != getScrollX() || iC2 != getScrollY()) {
                super.scrollTo(iC, iC2);
            }
        }
    }

    private void bl() {
        if (getOverScrollMode() != 2) {
            if (this.iJ == null) {
                Context context = getContext();
                this.iJ = new EdgeEffect(context);
                this.iK = new EdgeEffect(context);
                return;
            }
            return;
        }
        this.iJ = null;
        this.iK = null;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.iJ != null) {
            int scrollY = getScrollY();
            if (!this.iJ.isFinished()) {
                int iSave = canvas.save();
                int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.translate(getPaddingLeft(), Math.min(0, scrollY));
                this.iJ.setSize(width, getHeight());
                if (this.iJ.draw(canvas)) {
                    aa.e(this);
                }
                canvas.restoreToCount(iSave);
            }
            if (!this.iK.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int height = getHeight();
                canvas.translate((-width2) + getPaddingLeft(), Math.max(bj(), scrollY) + height);
                canvas.rotate(180.0f, width2, 0.0f);
                this.iK.setSize(width2, height);
                if (this.iK.draw(canvas)) {
                    aa.e(this);
                }
                canvas.restoreToCount(iSave2);
            }
        }
    }

    private static int c(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        if (i2 + i > i3) {
            return i3 - i2;
        }
        return i;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.jb = savedState;
        requestLayout();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.ji = getScrollY();
        return savedState;
    }

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new s();
        public int ji;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.ji = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.ji);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.ji + "}";
        }
    }
}
