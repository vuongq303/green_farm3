package android.support.v7.widget;

import android.content.res.Configuration;
import android.support.v7.appcompat.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/* JADX INFO: compiled from: ScrollingTabContainerView.java */
/* JADX INFO: loaded from: classes.dex */
public class cq extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {
    private static final Interpolator xB = new DecelerateInterpolator();
    private int rC;
    private int xA;
    Runnable xt;
    private ct xu;
    bw xv;
    private Spinner xw;
    private boolean xx;
    int xy;
    int xz;

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.xv.getChildCount();
        if (childCount > 1 && (mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            if (childCount > 2) {
                this.xy = (int) (View.MeasureSpec.getSize(i) * 0.4f);
            } else {
                this.xy = View.MeasureSpec.getSize(i) / 2;
            }
            this.xy = Math.min(this.xy, this.xz);
        } else {
            this.xy = -1;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.rC, 1073741824);
        if (!z && this.xx) {
            this.xv.measure(0, iMakeMeasureSpec);
            if (this.xv.getMeasuredWidth() > View.MeasureSpec.getSize(i)) {
                eV();
            } else {
                eW();
            }
        } else {
            eW();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, iMakeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (z && measuredWidth != measuredWidth2) {
            as(this.xA);
        }
    }

    private boolean eU() {
        return this.xw != null && this.xw.getParent() == this;
    }

    public void R(boolean z) {
        this.xx = z;
    }

    private void eV() {
        if (!eU()) {
            if (this.xw == null) {
                this.xw = eX();
            }
            removeView(this.xv);
            addView(this.xw, new ViewGroup.LayoutParams(-2, -1));
            if (this.xw.getAdapter() == null) {
                this.xw.setAdapter((SpinnerAdapter) new cs(this));
            }
            if (this.xt != null) {
                removeCallbacks(this.xt);
                this.xt = null;
            }
            this.xw.setSelection(this.xA);
        }
    }

    private boolean eW() {
        if (eU()) {
            removeView(this.xw);
            addView(this.xv, new ViewGroup.LayoutParams(-2, -1));
            as(this.xw.getSelectedItemPosition());
        }
        return false;
    }

    public void as(int i) {
        this.xA = i;
        int childCount = this.xv.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.xv.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                at(i);
            }
            i2++;
        }
        if (this.xw != null && i >= 0) {
            this.xw.setSelection(i);
        }
    }

    public void aj(int i) {
        this.rC = i;
        requestLayout();
    }

    private Spinner eX() {
        as asVar = new as(getContext(), null, R.attr.actionDropDownStyle);
        asVar.setLayoutParams(new bx(-2, -1));
        asVar.setOnItemSelectedListener(this);
        return asVar;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        android.support.v7.c.a aVarM = android.support.v7.c.a.m(getContext());
        aj(aVarM.cp());
        this.xz = aVarM.cr();
    }

    public void at(int i) {
        View childAt = this.xv.getChildAt(i);
        if (this.xt != null) {
            removeCallbacks(this.xt);
        }
        this.xt = new cr(this, childAt);
        post(this.xt);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.xt != null) {
            post(this.xt);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.xt != null) {
            removeCallbacks(this.xt);
        }
    }

    cu a(android.support.v7.app.d dVar, boolean z) {
        cu cuVar = new cu(this, getContext(), dVar, z);
        if (z) {
            cuVar.setBackgroundDrawable(null);
            cuVar.setLayoutParams(new AbsListView.LayoutParams(-1, this.rC));
        } else {
            cuVar.setFocusable(true);
            if (this.xu == null) {
                this.xu = new ct(this);
            }
            cuVar.setOnClickListener(this.xu);
        }
        return cuVar;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        ((cu) view).eY().select();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
