package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: compiled from: AppCompatTextHelper.java */
/* JADX INFO: loaded from: classes.dex */
class az {
    private int mStyle = 0;
    final TextView uB;
    private cy uC;
    private cy uD;
    private cy uE;
    private cy uF;
    private final bc uG;
    private Typeface uH;

    static az a(TextView textView) {
        return Build.VERSION.SDK_INT >= 17 ? new ba(textView) : new az(textView);
    }

    az(TextView textView) {
        this.uB = textView;
        this.uG = new bc(this.uB);
    }

    void a(AttributeSet attributeSet, int i) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        boolean z;
        boolean z2;
        Context context = this.uB.getContext();
        ac acVarEg = ac.eg();
        da daVarA = da.a(context, attributeSet, R.styleable.AppCompatTextHelper, i, 0);
        int resourceId = daVarA.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (daVarA.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.uC = a(context, acVarEg, daVarA.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (daVarA.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.uD = a(context, acVarEg, daVarA.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (daVarA.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.uE = a(context, acVarEg, daVarA.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (daVarA.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.uF = a(context, acVarEg, daVarA.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        daVarA.recycle();
        boolean z3 = this.uB.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId != -1) {
            da daVarA2 = da.a(context, resourceId, R.styleable.TextAppearance);
            if (z3 || !daVarA2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z = false;
                z2 = false;
            } else {
                z2 = daVarA2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z = true;
            }
            a(context, daVarA2);
            if (Build.VERSION.SDK_INT < 23) {
                colorStateList3 = daVarA2.hasValue(R.styleable.TextAppearance_android_textColor) ? daVarA2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                colorStateList2 = daVarA2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? daVarA2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                colorStateList = daVarA2.hasValue(R.styleable.TextAppearance_android_textColorLink) ? daVarA2.getColorStateList(R.styleable.TextAppearance_android_textColorLink) : null;
            } else {
                colorStateList = null;
                colorStateList2 = null;
                colorStateList3 = null;
            }
            daVarA2.recycle();
        } else {
            colorStateList = null;
            colorStateList2 = null;
            colorStateList3 = null;
            z = false;
            z2 = false;
        }
        da daVarA3 = da.a(context, attributeSet, R.styleable.TextAppearance, i, 0);
        if (!z3 && daVarA3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z2 = daVarA3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (daVarA3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList3 = daVarA3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (daVarA3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList2 = daVarA3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            if (daVarA3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList = daVarA3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            }
        }
        a(context, daVarA3);
        daVarA3.recycle();
        if (colorStateList3 != null) {
            this.uB.setTextColor(colorStateList3);
        }
        if (colorStateList2 != null) {
            this.uB.setHintTextColor(colorStateList2);
        }
        if (colorStateList != null) {
            this.uB.setLinkTextColor(colorStateList);
        }
        if (!z3 && z) {
            setAllCaps(z2);
        }
        if (this.uH != null) {
            this.uB.setTypeface(this.uH, this.mStyle);
        }
        this.uG.a(attributeSet, i);
        if (Build.VERSION.SDK_INT >= 26 && this.uG.getAutoSizeTextType() != 0) {
            int[] autoSizeTextAvailableSizes = this.uG.getAutoSizeTextAvailableSizes();
            if (autoSizeTextAvailableSizes.length > 0) {
                if (this.uB.getAutoSizeStepGranularity() != -1.0f) {
                    this.uB.setAutoSizeTextTypeUniformWithConfiguration(this.uG.getAutoSizeMinTextSize(), this.uG.getAutoSizeMaxTextSize(), this.uG.getAutoSizeStepGranularity(), 0);
                } else {
                    this.uB.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextAvailableSizes, 0);
                }
            }
        }
    }

    private void a(Context context, da daVar) {
        this.mStyle = daVar.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        if (daVar.hasValue(R.styleable.TextAppearance_android_fontFamily) || daVar.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.uH = null;
            int i = daVar.hasValue(R.styleable.TextAppearance_android_fontFamily) ? R.styleable.TextAppearance_android_fontFamily : R.styleable.TextAppearance_fontFamily;
            if (!context.isRestricted()) {
                try {
                    this.uH = daVar.a(i, this.mStyle, this.uB);
                } catch (Resources.NotFoundException e) {
                } catch (UnsupportedOperationException e2) {
                }
            }
            if (this.uH == null) {
                this.uH = Typeface.create(daVar.getString(i), this.mStyle);
            }
        }
    }

    void n(Context context, int i) {
        ColorStateList colorStateList;
        da daVarA = da.a(context, i, R.styleable.TextAppearance);
        if (daVarA.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(daVarA.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && daVarA.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = daVarA.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.uB.setTextColor(colorStateList);
        }
        a(context, daVarA);
        daVarA.recycle();
        if (this.uH != null) {
            this.uB.setTypeface(this.uH, this.mStyle);
        }
    }

    void setAllCaps(boolean z) {
        this.uB.setAllCaps(z);
    }

    void em() {
        if (this.uC != null || this.uD != null || this.uE != null || this.uF != null) {
            Drawable[] compoundDrawables = this.uB.getCompoundDrawables();
            a(compoundDrawables[0], this.uC);
            a(compoundDrawables[1], this.uD);
            a(compoundDrawables[2], this.uE);
            a(compoundDrawables[3], this.uF);
        }
    }

    final void a(Drawable drawable, cy cyVar) {
        if (drawable != null && cyVar != null) {
            ac.a(drawable, cyVar, this.uB.getDrawableState());
        }
    }

    protected static cy a(Context context, ac acVar, int i) {
        ColorStateList colorStateListK = acVar.k(context, i);
        if (colorStateListK == null) {
            return null;
        }
        cy cyVar = new cy();
        cyVar.xP = true;
        cyVar.xN = colorStateListK;
        return cyVar;
    }

    void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT < 26) {
            en();
        }
    }

    void setTextSize(int i, float f) {
        if (Build.VERSION.SDK_INT < 26 && !eo()) {
            b(i, f);
        }
    }

    void en() {
        this.uG.en();
    }

    boolean eo() {
        return this.uG.eo();
    }

    private void b(int i, float f) {
        this.uG.b(i, f);
    }

    void setAutoSizeTextTypeWithDefaults(int i) {
        this.uG.setAutoSizeTextTypeWithDefaults(i);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        this.uG.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        this.uG.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    int getAutoSizeTextType() {
        return this.uG.getAutoSizeTextType();
    }

    int getAutoSizeStepGranularity() {
        return this.uG.getAutoSizeStepGranularity();
    }

    int getAutoSizeMinTextSize() {
        return this.uG.getAutoSizeMinTextSize();
    }

    int getAutoSizeMaxTextSize() {
        return this.uG.getAutoSizeMaxTextSize();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.uG.getAutoSizeTextAvailableSizes();
    }
}
