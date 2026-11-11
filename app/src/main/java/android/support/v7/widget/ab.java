package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/* JADX INFO: compiled from: AppCompatCompoundButtonHelper.java */
/* JADX INFO: loaded from: classes.dex */
class ab {
    private boolean tD;
    private final CompoundButton ty;
    private ColorStateList tz = null;
    private PorterDuff.Mode tA = null;
    private boolean tB = false;
    private boolean tC = false;

    ab(CompoundButton compoundButton) {
        this.ty = compoundButton;
    }

    void a(AttributeSet attributeSet, int i) {
        int resourceId;
        TypedArray typedArrayObtainStyledAttributes = this.ty.getContext().obtainStyledAttributes(attributeSet, R.styleable.CompoundButton, i, 0);
        try {
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_android_button) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CompoundButton_android_button, 0)) != 0) {
                this.ty.setButtonDrawable(android.support.v7.a.a.b.a(this.ty.getContext(), resourceId));
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_buttonTint)) {
                android.support.v4.widget.d.a(this.ty, typedArrayObtainStyledAttributes.getColorStateList(R.styleable.CompoundButton_buttonTint));
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.CompoundButton_buttonTintMode)) {
                android.support.v4.widget.d.a(this.ty, bp.d(typedArrayObtainStyledAttributes.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null));
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    void b(ColorStateList colorStateList) {
        this.tz = colorStateList;
        this.tB = true;
        ef();
    }

    void b(PorterDuff.Mode mode) {
        this.tA = mode;
        this.tC = true;
        ef();
    }

    void ee() {
        if (this.tD) {
            this.tD = false;
        } else {
            this.tD = true;
            ef();
        }
    }

    void ef() {
        Drawable drawableA = android.support.v4.widget.d.a(this.ty);
        if (drawableA != null) {
            if (this.tB || this.tC) {
                Drawable drawableMutate = android.support.v4.a.a.a.d(drawableA).mutate();
                if (this.tB) {
                    android.support.v4.a.a.a.a(drawableMutate, this.tz);
                }
                if (this.tC) {
                    android.support.v4.a.a.a.a(drawableMutate, this.tA);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(this.ty.getDrawableState());
                }
                this.ty.setButtonDrawable(drawableMutate);
            }
        }
    }

    int ao(int i) {
        Drawable drawableA;
        if (Build.VERSION.SDK_INT < 17 && (drawableA = android.support.v4.widget.d.a(this.ty)) != null) {
            return i + drawableA.getIntrinsicWidth();
        }
        return i;
    }
}
