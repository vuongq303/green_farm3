package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: compiled from: LinearLayoutCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class bx extends ViewGroup.MarginLayoutParams {
    public int gravity;
    public float weight;

    public bx(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.gravity = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
        this.weight = typedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
        this.gravity = typedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public bx(int i, int i2) {
        super(i, i2);
        this.gravity = -1;
        this.weight = 0.0f;
    }

    public bx(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.gravity = -1;
    }
}
