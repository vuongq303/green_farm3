package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import com.facebook.R;

/* JADX INFO: compiled from: DrawableUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class bp {
    public static final Rect vr = new Rect();
    private static Class<?> vs;

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                vs = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException e) {
            }
        }
    }

    static void n(Drawable drawable) {
        if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName())) {
            p(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean o(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState instanceof DrawableContainer.DrawableContainerState) {
                Drawable[] children = ((DrawableContainer.DrawableContainerState) constantState).getChildren();
                for (Drawable drawable2 : children) {
                    if (!o(drawable2)) {
                        return false;
                    }
                }
            }
        } else {
            if (drawable instanceof android.support.v4.a.a.g) {
                return o(((android.support.v4.a.a.g) drawable).at());
            }
            if (drawable instanceof android.support.v7.b.a.a) {
                return o(((android.support.v7.b.a.a) drawable).at());
            }
            if (drawable instanceof ScaleDrawable) {
                return o(((ScaleDrawable) drawable).getDrawable());
            }
        }
        return true;
    }

    private static void p(Drawable drawable) {
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(cv.xI);
        } else {
            drawable.setState(cv.EMPTY_STATE_SET);
        }
        drawable.setState(state);
    }

    public static PorterDuff.Mode d(int i, PorterDuff.Mode mode) {
        switch (i) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 4:
            case 6:
            case 7:
            case 8:
            case R.styleable.CardView_contentPaddingRight /* 10 */:
            case R.styleable.CardView_contentPaddingTop /* 11 */:
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
            case 13:
            default:
                return mode;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                if (Build.VERSION.SDK_INT >= 11) {
                    return PorterDuff.Mode.valueOf("ADD");
                }
                return mode;
        }
    }
}
