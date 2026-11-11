package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: compiled from: AppCompatImageHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class aj {
    private final ImageView tV;
    private cy tW;
    private cy tX;
    private cy tw;

    public aj(ImageView imageView) {
        this.tV = imageView;
    }

    public void a(AttributeSet attributeSet, int i) {
        int resourceId;
        da daVarA = da.a(this.tV.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
        try {
            Drawable drawable = this.tV.getDrawable();
            if (drawable == null && (resourceId = daVarA.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = android.support.v7.a.a.b.a(this.tV.getContext(), resourceId)) != null) {
                this.tV.setImageDrawable(drawable);
            }
            if (drawable != null) {
                bp.n(drawable);
            }
            if (daVarA.hasValue(R.styleable.AppCompatImageView_tint)) {
                android.support.v4.widget.k.a(this.tV, daVarA.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (daVarA.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                android.support.v4.widget.k.a(this.tV, bp.d(daVarA.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            daVarA.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            Drawable drawableA = android.support.v7.a.a.b.a(this.tV.getContext(), i);
            if (drawableA != null) {
                bp.n(drawableA);
            }
            this.tV.setImageDrawable(drawableA);
        } else {
            this.tV.setImageDrawable(null);
        }
        eh();
    }

    boolean hasOverlappingRendering() {
        return Build.VERSION.SDK_INT < 21 || !(this.tV.getBackground() instanceof RippleDrawable);
    }

    void c(ColorStateList colorStateList) {
        if (this.tX == null) {
            this.tX = new cy();
        }
        this.tX.xN = colorStateList;
        this.tX.xP = true;
        eh();
    }

    ColorStateList bm() {
        if (this.tX != null) {
            return this.tX.xN;
        }
        return null;
    }

    void c(PorterDuff.Mode mode) {
        if (this.tX == null) {
            this.tX = new cy();
        }
        this.tX.fK = mode;
        this.tX.xO = true;
        eh();
    }

    PorterDuff.Mode bn() {
        if (this.tX != null) {
            return this.tX.fK;
        }
        return null;
    }

    void eh() {
        Drawable drawable = this.tV.getDrawable();
        if (drawable != null) {
            bp.n(drawable);
        }
        if (drawable != null) {
            if (!ed() || !j(drawable)) {
                if (this.tX != null) {
                    ac.a(drawable, this.tX, this.tV.getDrawableState());
                } else if (this.tW != null) {
                    ac.a(drawable, this.tW, this.tV.getDrawableState());
                }
            }
        }
    }

    private boolean ed() {
        int i = Build.VERSION.SDK_INT;
        if (i > 21) {
            return this.tW != null;
        }
        return i == 21;
    }

    private boolean j(Drawable drawable) {
        if (this.tw == null) {
            this.tw = new cy();
        }
        cy cyVar = this.tw;
        cyVar.clear();
        ColorStateList colorStateListA = android.support.v4.widget.k.a(this.tV);
        if (colorStateListA != null) {
            cyVar.xP = true;
            cyVar.xN = colorStateListA;
        }
        PorterDuff.Mode modeB = android.support.v4.widget.k.b(this.tV);
        if (modeB != null) {
            cyVar.xO = true;
            cyVar.fK = modeB;
        }
        if (!cyVar.xP && !cyVar.xO) {
            return false;
        }
        ac.a(drawable, cyVar, this.tV.getDrawableState());
        return true;
    }
}
