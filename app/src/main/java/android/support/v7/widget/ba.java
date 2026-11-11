package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: compiled from: AppCompatTextHelperV17.java */
/* JADX INFO: loaded from: classes.dex */
class ba extends az {
    private cy uI;
    private cy uJ;

    ba(TextView textView) {
        super(textView);
    }

    @Override // android.support.v7.widget.az
    void a(AttributeSet attributeSet, int i) {
        super.a(attributeSet, i);
        Context context = this.uB.getContext();
        ac acVarEg = ac.eg();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppCompatTextHelper, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
            this.uI = a(context, acVarEg, typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.uJ = a(context, acVarEg, typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.support.v7.widget.az
    void em() {
        super.em();
        if (this.uI != null || this.uJ != null) {
            Drawable[] compoundDrawablesRelative = this.uB.getCompoundDrawablesRelative();
            a(compoundDrawablesRelative[0], this.uI);
            a(compoundDrawablesRelative[2], this.uJ);
        }
    }
}
