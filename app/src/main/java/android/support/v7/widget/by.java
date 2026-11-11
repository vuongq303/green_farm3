package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
public class by implements android.support.v7.c.a.ao {
    private static Method vV;
    private static Method vW;
    private static Method vX;
    private final Rect iH;
    private ListAdapter jO;
    private Context mContext;
    final Handler mHandler;
    private int pF;
    private Rect rl;
    private boolean tZ;
    private int ur;
    bq vY;
    private int vZ;
    private int wa;
    private int wb;
    private int wc;
    private boolean wd;
    private boolean we;
    private boolean wf;
    private boolean wg;
    private boolean wh;
    int wi;
    private View wj;
    private int wk;
    private DataSetObserver wl;
    private View wm;
    private Drawable wn;
    private AdapterView.OnItemClickListener wo;
    private AdapterView.OnItemSelectedListener wp;
    final cf wq;
    private final ce wr;
    private final cd ws;
    private final cb wt;
    private Runnable wu;
    private boolean wv;
    PopupWindow ww;

    static {
        try {
            vV = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            vW = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
        } catch (NoSuchMethodException e2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            vX = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
        } catch (NoSuchMethodException e3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public by(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public by(Context context, AttributeSet attributeSet, int i, int i2) {
        this.vZ = -2;
        this.ur = -2;
        this.wc = 1002;
        this.we = true;
        this.pF = 0;
        this.wg = false;
        this.wh = false;
        this.wi = Integer.MAX_VALUE;
        this.wk = 0;
        this.wq = new cf(this);
        this.wr = new ce(this);
        this.ws = new cd(this);
        this.wt = new cb(this);
        this.iH = new Rect();
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2);
        this.wa = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.wb = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.wb != 0) {
            this.wd = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.ww = new al(context, attributeSet, i, i2);
        this.ww.setInputMethodMode(1);
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (this.wl == null) {
            this.wl = new cc(this);
        } else if (this.jO != null) {
            this.jO.unregisterDataSetObserver(this.wl);
        }
        this.jO = listAdapter;
        if (this.jO != null) {
            listAdapter.registerDataSetObserver(this.wl);
        }
        if (this.vY != null) {
            this.vY.setAdapter(this.jO);
        }
    }

    public void setPromptPosition(int i) {
        this.wk = i;
    }

    public void setModal(boolean z) {
        this.wv = z;
        this.ww.setFocusable(z);
    }

    public boolean isModal() {
        return this.wv;
    }

    public Drawable getBackground() {
        return this.ww.getBackground();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.ww.setBackgroundDrawable(drawable);
    }

    public void setAnimationStyle(int i) {
        this.ww.setAnimationStyle(i);
    }

    public View getAnchorView() {
        return this.wm;
    }

    public void setAnchorView(View view) {
        this.wm = view;
    }

    public int getHorizontalOffset() {
        return this.wa;
    }

    public void setHorizontalOffset(int i) {
        this.wa = i;
    }

    public int getVerticalOffset() {
        if (this.wd) {
            return this.wb;
        }
        return 0;
    }

    public void setVerticalOffset(int i) {
        this.wb = i;
        this.wd = true;
    }

    public void setEpicenterBounds(Rect rect) {
        this.rl = rect;
    }

    public void setDropDownGravity(int i) {
        this.pF = i;
    }

    public int getWidth() {
        return this.ur;
    }

    public void setWidth(int i) {
        this.ur = i;
    }

    public void setContentWidth(int i) {
        Drawable background = this.ww.getBackground();
        if (background != null) {
            background.getPadding(this.iH);
            this.ur = this.iH.left + this.iH.right + i;
        } else {
            setWidth(i);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.wo = onItemClickListener;
    }

    @Override // android.support.v7.c.a.ao
    public void show() {
        int width;
        int width2;
        int i;
        boolean z = false;
        int iEN = eN();
        boolean zIsInputMethodNotNeeded = isInputMethodNotNeeded();
        android.support.v4.widget.t.a(this.ww, this.wc);
        if (this.ww.isShowing()) {
            if (android.support.v4.view.aa.isAttachedToWindow(getAnchorView())) {
                if (this.ur == -1) {
                    width2 = -1;
                } else if (this.ur == -2) {
                    width2 = getAnchorView().getWidth();
                } else {
                    width2 = this.ur;
                }
                if (this.vZ == -1) {
                    if (!zIsInputMethodNotNeeded) {
                        iEN = -1;
                    }
                    if (zIsInputMethodNotNeeded) {
                        this.ww.setWidth(this.ur == -1 ? -1 : 0);
                        this.ww.setHeight(0);
                        i = iEN;
                    } else {
                        this.ww.setWidth(this.ur == -1 ? -1 : 0);
                        this.ww.setHeight(-1);
                        i = iEN;
                    }
                } else {
                    i = this.vZ == -2 ? iEN : this.vZ;
                }
                PopupWindow popupWindow = this.ww;
                if (!this.wh && !this.wg) {
                    z = true;
                }
                popupWindow.setOutsideTouchable(z);
                PopupWindow popupWindow2 = this.ww;
                View anchorView = getAnchorView();
                int i2 = this.wa;
                int i3 = this.wb;
                if (width2 < 0) {
                    width2 = -1;
                }
                popupWindow2.update(anchorView, i2, i3, width2, i >= 0 ? i : -1);
                return;
            }
            return;
        }
        if (this.ur == -1) {
            width = -1;
        } else if (this.ur == -2) {
            width = getAnchorView().getWidth();
        } else {
            width = this.ur;
        }
        if (this.vZ == -1) {
            iEN = -1;
        } else if (this.vZ != -2) {
            iEN = this.vZ;
        }
        this.ww.setWidth(width);
        this.ww.setHeight(iEN);
        N(true);
        this.ww.setOutsideTouchable((this.wh || this.wg) ? false : true);
        this.ww.setTouchInterceptor(this.wr);
        if (this.wf) {
            android.support.v4.widget.t.a(this.ww, this.tZ);
        }
        if (vX != null) {
            try {
                vX.invoke(this.ww, this.rl);
            } catch (Exception e) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e);
            }
        }
        android.support.v4.widget.t.a(this.ww, getAnchorView(), this.wa, this.wb, this.pF);
        this.vY.setSelection(-1);
        if (!this.wv || this.vY.isInTouchMode()) {
            clearListSelection();
        }
        if (!this.wv) {
            this.mHandler.post(this.wt);
        }
    }

    @Override // android.support.v7.c.a.ao
    public void dismiss() {
        this.ww.dismiss();
        eM();
        this.ww.setContentView(null);
        this.vY = null;
        this.mHandler.removeCallbacks(this.wq);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.ww.setOnDismissListener(onDismissListener);
    }

    private void eM() {
        if (this.wj != null) {
            ViewParent parent = this.wj.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.wj);
            }
        }
    }

    public void setInputMethodMode(int i) {
        this.ww.setInputMethodMode(i);
    }

    public void setSelection(int i) {
        bq bqVar = this.vY;
        if (isShowing() && bqVar != null) {
            bqVar.M(false);
            bqVar.setSelection(i);
            if (bqVar.getChoiceMode() != 0) {
                bqVar.setItemChecked(i, true);
            }
        }
    }

    public void clearListSelection() {
        bq bqVar = this.vY;
        if (bqVar != null) {
            bqVar.M(true);
            bqVar.requestLayout();
        }
    }

    @Override // android.support.v7.c.a.ao
    public boolean isShowing() {
        return this.ww.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.ww.getInputMethodMode() == 2;
    }

    @Override // android.support.v7.c.a.ao
    public ListView getListView() {
        return this.vY;
    }

    bq a(Context context, boolean z) {
        return new bq(context, z);
    }

    private int eN() {
        int measuredHeight;
        int i;
        int iMakeMeasureSpec;
        int measuredHeight2;
        View view;
        int i2;
        int i3;
        if (this.vY == null) {
            Context context = this.mContext;
            this.wu = new bz(this);
            this.vY = a(context, !this.wv);
            if (this.wn != null) {
                this.vY.setSelector(this.wn);
            }
            this.vY.setAdapter(this.jO);
            this.vY.setOnItemClickListener(this.wo);
            this.vY.setFocusable(true);
            this.vY.setFocusableInTouchMode(true);
            this.vY.setOnItemSelectedListener(new ca(this));
            this.vY.setOnScrollListener(this.ws);
            if (this.wp != null) {
                this.vY.setOnItemSelectedListener(this.wp);
            }
            bq bqVar = this.vY;
            View view2 = this.wj;
            if (view2 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.wk) {
                    case 0:
                        linearLayout.addView(view2);
                        linearLayout.addView(bqVar, layoutParams);
                        break;
                    case 1:
                        linearLayout.addView(bqVar, layoutParams);
                        linearLayout.addView(view2);
                        break;
                    default:
                        Log.e("ListPopupWindow", "Invalid hint position " + this.wk);
                        break;
                }
                if (this.ur >= 0) {
                    i3 = this.ur;
                    i2 = Integer.MIN_VALUE;
                } else {
                    i2 = 0;
                    i3 = 0;
                }
                view2.measure(View.MeasureSpec.makeMeasureSpec(i3, i2), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                measuredHeight2 = layoutParams2.bottomMargin + view2.getMeasuredHeight() + layoutParams2.topMargin;
                view = linearLayout;
            } else {
                measuredHeight2 = 0;
                view = bqVar;
            }
            this.ww.setContentView(view);
            measuredHeight = measuredHeight2;
        } else {
            View view3 = this.wj;
            if (view3 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                measuredHeight = layoutParams3.bottomMargin + view3.getMeasuredHeight() + layoutParams3.topMargin;
            } else {
                measuredHeight = 0;
            }
        }
        Drawable background = this.ww.getBackground();
        if (background != null) {
            background.getPadding(this.iH);
            int i4 = this.iH.top + this.iH.bottom;
            if (this.wd) {
                i = i4;
            } else {
                this.wb = -this.iH.top;
                i = i4;
            }
        } else {
            this.iH.setEmpty();
            i = 0;
        }
        int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.wb, this.ww.getInputMethodMode() == 2);
        if (this.wg || this.vZ == -1) {
            return maxAvailableHeight + i;
        }
        switch (this.ur) {
            case -2:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.iH.left + this.iH.right), Integer.MIN_VALUE);
                break;
            case -1:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.iH.left + this.iH.right), 1073741824);
                break;
            default:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.ur, 1073741824);
                break;
        }
        int iB = this.vY.b(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1);
        if (iB > 0) {
            measuredHeight += this.vY.getPaddingTop() + this.vY.getPaddingBottom() + i;
        }
        return iB + measuredHeight;
    }

    public void setOverlapAnchor(boolean z) {
        this.wf = true;
        this.tZ = z;
    }

    private void N(boolean z) {
        if (vV != null) {
            try {
                vV.invoke(this.ww, Boolean.valueOf(z));
            } catch (Exception e) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    private int getMaxAvailableHeight(View view, int i, boolean z) {
        if (vW != null) {
            try {
                return ((Integer) vW.invoke(this.ww, view, Integer.valueOf(i), Boolean.valueOf(z))).intValue();
            } catch (Exception e) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.ww.getMaxAvailableHeight(view, i);
    }
}
