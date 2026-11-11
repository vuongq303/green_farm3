package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

/* JADX INFO: compiled from: NestedScrollingChildHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    private ViewParent hl;
    private ViewParent hm;
    private boolean hn;
    private int[] ho;
    private final View mView;

    public void setNestedScrollingEnabled(boolean z) {
        if (this.hn) {
            aa.o(this.mView);
        }
        this.hn = z;
    }

    public boolean isNestedScrollingEnabled() {
        return this.hn;
    }

    public boolean hasNestedScrollingParent() {
        return v(0);
    }

    public boolean v(int i) {
        return x(i) != null;
    }

    public boolean startNestedScroll(int i) {
        return e(i, 0);
    }

    public boolean e(int i, int i2) {
        if (v(i2)) {
            return true;
        }
        if (isNestedScrollingEnabled()) {
            View view = this.mView;
            for (ViewParent parent = this.mView.getParent(); parent != null; parent = parent.getParent()) {
                if (an.a(parent, view, this.mView, i, i2)) {
                    a(i2, parent);
                    an.b(parent, view, this.mView, i, i2);
                    return true;
                }
                if (parent instanceof View) {
                    view = (View) parent;
                }
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        w(0);
    }

    public void w(int i) {
        ViewParent viewParentX = x(i);
        if (viewParentX != null) {
            an.a(viewParentX, this.mView, i);
            a(i, null);
        }
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return a(i, i2, i3, i4, iArr, 0);
    }

    public boolean a(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        ViewParent viewParentX;
        int i6;
        int i7;
        if (!isNestedScrollingEnabled() || (viewParentX = x(i5)) == null) {
            return false;
        }
        if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
            if (iArr == null) {
                i6 = 0;
                i7 = 0;
            } else {
                this.mView.getLocationInWindow(iArr);
                int i8 = iArr[0];
                i6 = iArr[1];
                i7 = i8;
            }
            an.a(viewParentX, this.mView, i, i2, i3, i4, i5);
            if (iArr != null) {
                this.mView.getLocationInWindow(iArr);
                iArr[0] = iArr[0] - i7;
                iArr[1] = iArr[1] - i6;
            }
            return true;
        }
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = 0;
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return a(i, i2, iArr, iArr2, 0);
    }

    public boolean a(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        int i4;
        int i5;
        int[] iArr3;
        if (isNestedScrollingEnabled()) {
            ViewParent viewParentX = x(i3);
            if (viewParentX == null) {
                return false;
            }
            if (i != 0 || i2 != 0) {
                if (iArr2 != null) {
                    this.mView.getLocationInWindow(iArr2);
                    int i6 = iArr2[0];
                    i4 = iArr2[1];
                    i5 = i6;
                } else {
                    i4 = 0;
                    i5 = 0;
                }
                if (iArr == null) {
                    if (this.ho == null) {
                        this.ho = new int[2];
                    }
                    iArr3 = this.ho;
                } else {
                    iArr3 = iArr;
                }
                iArr3[0] = 0;
                iArr3[1] = 0;
                an.a(viewParentX, this.mView, i, i2, iArr3, i3);
                if (iArr2 != null) {
                    this.mView.getLocationInWindow(iArr2);
                    iArr2[0] = iArr2[0] - i5;
                    iArr2[1] = iArr2[1] - i4;
                }
                return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true;
            }
            if (iArr2 != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        ViewParent viewParentX;
        if (!isNestedScrollingEnabled() || (viewParentX = x(0)) == null) {
            return false;
        }
        return an.a(viewParentX, this.mView, f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent viewParentX;
        if (!isNestedScrollingEnabled() || (viewParentX = x(0)) == null) {
            return false;
        }
        return an.a(viewParentX, this.mView, f, f2);
    }

    private ViewParent x(int i) {
        switch (i) {
            case 0:
                return this.hl;
            case 1:
                return this.hm;
            default:
                return null;
        }
    }

    private void a(int i, ViewParent viewParent) {
        switch (i) {
            case 0:
                this.hl = viewParent;
                break;
            case 1:
                this.hm = viewParent;
                break;
        }
    }
}
