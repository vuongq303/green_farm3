package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/* JADX INFO: compiled from: TintTypedArray.java */
/* JADX INFO: loaded from: classes.dex */
public class da {
    private final Context mContext;
    private TypedValue tS;
    private final TypedArray xR;

    public static da a(Context context, AttributeSet attributeSet, int[] iArr) {
        return new da(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public static da a(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new da(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public static da a(Context context, int i, int[] iArr) {
        return new da(context, context.obtainStyledAttributes(i, iArr));
    }

    private da(Context context, TypedArray typedArray) {
        this.mContext = context;
        this.xR = typedArray;
    }

    public Drawable getDrawable(int i) {
        int resourceId;
        return (!this.xR.hasValue(i) || (resourceId = this.xR.getResourceId(i, 0)) == 0) ? this.xR.getDrawable(i) : android.support.v7.a.a.b.a(this.mContext, resourceId);
    }

    public Drawable au(int i) {
        int resourceId;
        if (!this.xR.hasValue(i) || (resourceId = this.xR.getResourceId(i, 0)) == 0) {
            return null;
        }
        return ac.eg().a(this.mContext, resourceId, true);
    }

    public Typeface a(int i, int i2, TextView textView) {
        int resourceId = this.xR.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.tS == null) {
            this.tS = new TypedValue();
        }
        return android.support.v4.content.a.f.a(this.mContext, resourceId, this.tS, i2, textView);
    }

    public CharSequence getText(int i) {
        return this.xR.getText(i);
    }

    public String getString(int i) {
        return this.xR.getString(i);
    }

    public boolean getBoolean(int i, boolean z) {
        return this.xR.getBoolean(i, z);
    }

    public int getInt(int i, int i2) {
        return this.xR.getInt(i, i2);
    }

    public float getFloat(int i, float f) {
        return this.xR.getFloat(i, f);
    }

    public int getColor(int i, int i2) {
        return this.xR.getColor(i, i2);
    }

    public ColorStateList getColorStateList(int i) {
        int resourceId;
        ColorStateList colorStateListB;
        return (!this.xR.hasValue(i) || (resourceId = this.xR.getResourceId(i, 0)) == 0 || (colorStateListB = android.support.v7.a.a.b.b(this.mContext, resourceId)) == null) ? this.xR.getColorStateList(i) : colorStateListB;
    }

    public int getDimensionPixelOffset(int i, int i2) {
        return this.xR.getDimensionPixelOffset(i, i2);
    }

    public int getDimensionPixelSize(int i, int i2) {
        return this.xR.getDimensionPixelSize(i, i2);
    }

    public int getLayoutDimension(int i, int i2) {
        return this.xR.getLayoutDimension(i, i2);
    }

    public int getResourceId(int i, int i2) {
        return this.xR.getResourceId(i, i2);
    }

    public CharSequence[] getTextArray(int i) {
        return this.xR.getTextArray(i);
    }

    public boolean hasValue(int i) {
        return this.xR.hasValue(i);
    }

    public void recycle() {
        this.xR.recycle();
    }
}
