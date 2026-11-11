package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

/* JADX INFO: compiled from: ThemeUtils.java */
/* JADX INFO: loaded from: classes.dex */
class cv {
    private static final ThreadLocal<TypedValue> nz = new ThreadLocal<>();
    static final int[] xG = {-16842910};
    static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    static final int[] xH = {R.attr.state_activated};
    static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    static final int[] xI = {R.attr.state_checked};
    static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    static final int[] xJ = {-16842919, -16842908};
    static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] xK = new int[1];

    public static int o(Context context, int i) {
        xK[0] = i;
        da daVarA = da.a(context, (AttributeSet) null, xK);
        try {
            return daVarA.getColor(0, 0);
        } finally {
            daVarA.recycle();
        }
    }

    public static ColorStateList p(Context context, int i) {
        xK[0] = i;
        da daVarA = da.a(context, (AttributeSet) null, xK);
        try {
            return daVarA.getColorStateList(0);
        } finally {
            daVarA.recycle();
        }
    }

    public static int q(Context context, int i) {
        ColorStateList colorStateListP = p(context, i);
        if (colorStateListP != null && colorStateListP.isStateful()) {
            return colorStateListP.getColorForState(xG, colorStateListP.getDefaultColor());
        }
        TypedValue typedValueCk = ck();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValueCk, true);
        return a(context, i, typedValueCk.getFloat());
    }

    private static TypedValue ck() {
        TypedValue typedValue = nz.get();
        if (typedValue == null) {
            TypedValue typedValue2 = new TypedValue();
            nz.set(typedValue2);
            return typedValue2;
        }
        return typedValue;
    }

    static int a(Context context, int i, float f) {
        int iO = o(context, i);
        return android.support.v4.a.a.c(iO, Math.round(Color.alpha(iO) * f));
    }
}
