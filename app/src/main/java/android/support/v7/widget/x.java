package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: compiled from: AppCompatBackgroundHelper.java */
/* JADX INFO: loaded from: classes.dex */
class x {
    private final View mView;
    private cy tu;
    private cy tv;
    private cy tw;
    private int tt = -1;
    private final ac ts = ac.eg();

    x(View view) {
        this.mView = view;
    }

    void a(AttributeSet attributeSet, int i) {
        da daVarA = da.a(this.mView.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0);
        try {
            if (daVarA.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.tt = daVarA.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateListK = this.ts.k(this.mView.getContext(), this.tt);
                if (colorStateListK != null) {
                    d(colorStateListK);
                }
            }
            if (daVarA.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                android.support.v4.view.aa.a(this.mView, daVarA.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (daVarA.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                android.support.v4.view.aa.a(this.mView, bp.d(daVarA.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            daVarA.recycle();
        }
    }

    void an(int i) {
        this.tt = i;
        d(this.ts != null ? this.ts.k(this.mView.getContext(), i) : null);
        ec();
    }

    void i(Drawable drawable) {
        this.tt = -1;
        d(null);
        ec();
    }

    void a(ColorStateList colorStateList) {
        if (this.tv == null) {
            this.tv = new cy();
        }
        this.tv.xN = colorStateList;
        this.tv.xP = true;
        ec();
    }

    ColorStateList aQ() {
        if (this.tv != null) {
            return this.tv.xN;
        }
        return null;
    }

    void a(PorterDuff.Mode mode) {
        if (this.tv == null) {
            this.tv = new cy();
        }
        this.tv.fK = mode;
        this.tv.xO = true;
        ec();
    }

    PorterDuff.Mode aR() {
        if (this.tv != null) {
            return this.tv.fK;
        }
        return null;
    }

    void ec() {
        Drawable background = this.mView.getBackground();
        if (background != null) {
            if (!ed() || !j(background)) {
                if (this.tv != null) {
                    ac.a(background, this.tv, this.mView.getDrawableState());
                } else if (this.tu != null) {
                    ac.a(background, this.tu, this.mView.getDrawableState());
                }
            }
        }
    }

    void d(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.tu == null) {
                this.tu = new cy();
            }
            this.tu.xN = colorStateList;
            this.tu.xP = true;
        } else {
            this.tu = null;
        }
        ec();
    }

    private boolean ed() {
        int i = Build.VERSION.SDK_INT;
        if (i > 21) {
            return this.tu != null;
        }
        return i == 21;
    }

    private boolean j(Drawable drawable) {
        if (this.tw == null) {
            this.tw = new cy();
        }
        cy cyVar = this.tw;
        cyVar.clear();
        ColorStateList colorStateListM = android.support.v4.view.aa.m(this.mView);
        if (colorStateListM != null) {
            cyVar.xP = true;
            cyVar.xN = colorStateListM;
        }
        PorterDuff.Mode modeN = android.support.v4.view.aa.n(this.mView);
        if (modeN != null) {
            cyVar.xO = true;
            cyVar.fK = modeN;
        }
        if (!cyVar.xP && !cyVar.xO) {
            return false;
        }
        ac.a(drawable, cyVar, this.mView.getDrawableState());
        return true;
    }
}
