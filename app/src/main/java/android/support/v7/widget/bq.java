package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: compiled from: DropDownListView.java */
/* JADX INFO: loaded from: classes.dex */
class bq extends cg {
    private boolean vt;
    private boolean vu;
    private boolean vv;
    private android.support.v4.view.ar vw;
    private android.support.v4.widget.o vx;

    public bq(Context context, boolean z) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.vu = z;
        setCacheColorHint(0);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0034  */
    /* JADX WARN: Code duplicated, block: B:19:0x0037  */
    /* JADX WARN: Code duplicated, block: B:21:0x0048  */
    /* JADX WARN: Code duplicated, block: B:22:0x004a  */
    /* JADX WARN: Code duplicated, block: B:24:0x005b  */
    public boolean a(MotionEvent motionEvent, int i) {
        boolean z;
        int iFindPointerIndex;
        int x;
        int y;
        int iPointToPosition;
        View childAt;
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        switch (actionMasked) {
            case 1:
                z = false;
                iFindPointerIndex = motionEvent.findPointerIndex(i);
                if (iFindPointerIndex < 0) {
                    x = (int) motionEvent.getX(iFindPointerIndex);
                    y = (int) motionEvent.getY(iFindPointerIndex);
                    iPointToPosition = pointToPosition(x, y);
                    if (iPointToPosition == -1) {
                        childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                        a(childAt, iPointToPosition, x, y);
                        if (actionMasked == 1) {
                            c(childAt, iPointToPosition);
                        }
                        z2 = false;
                        z = true;
                    } else {
                        z2 = true;
                    }
                } else {
                    z2 = false;
                    z = false;
                }
                break;
            case 2:
                z = true;
                iFindPointerIndex = motionEvent.findPointerIndex(i);
                if (iFindPointerIndex < 0) {
                    x = (int) motionEvent.getX(iFindPointerIndex);
                    y = (int) motionEvent.getY(iFindPointerIndex);
                    iPointToPosition = pointToPosition(x, y);
                    if (iPointToPosition == -1) {
                        childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                        a(childAt, iPointToPosition, x, y);
                        if (actionMasked == 1) {
                            c(childAt, iPointToPosition);
                        }
                        z2 = false;
                        z = true;
                    } else {
                        z2 = true;
                    }
                } else {
                    z2 = false;
                    z = false;
                }
                break;
            case 3:
                z2 = false;
                z = false;
                break;
            default:
                z2 = false;
                z = true;
                break;
        }
        if (!z || z2) {
            eH();
        }
        if (z) {
            if (this.vx == null) {
                this.vx = new android.support.v4.widget.o(this);
            }
            this.vx.f(true);
            this.vx.onTouch(this, motionEvent);
        } else if (this.vx != null) {
            this.vx.f(false);
        }
        return z;
    }

    private void c(View view, int i) {
        performItemClick(view, i, getItemIdAtPosition(i));
    }

    void M(boolean z) {
        this.vt = z;
    }

    private void eH() {
        this.vv = false;
        setPressed(false);
        drawableStateChanged();
        View childAt = getChildAt(this.wE - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.setPressed(false);
        }
        if (this.vw != null) {
            this.vw.cancel();
            this.vw = null;
        }
    }

    private void a(View view, int i, float f, float f2) {
        View childAt;
        this.vv = true;
        if (Build.VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(f, f2);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        if (this.wE != -1 && (childAt = getChildAt(this.wE - getFirstVisiblePosition())) != null && childAt != view && childAt.isPressed()) {
            childAt.setPressed(false);
        }
        this.wE = i;
        float left = f - view.getLeft();
        float top = f2 - view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(left, top);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        a(i, view, f, f2);
        O(false);
        refreshDrawableState();
    }

    @Override // android.support.v7.widget.cg
    protected boolean eI() {
        return this.vv || super.eI();
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.vu && this.vt) || super.isInTouchMode();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.vu || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.vu || super.isFocused();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.vu || super.hasFocus();
    }
}
