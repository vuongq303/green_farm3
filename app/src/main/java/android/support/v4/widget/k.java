package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.widget.ImageView;

/* JADX INFO: compiled from: ImageViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    static final m iE;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            iE = new n();
        } else {
            iE = new l();
        }
    }

    public static ColorStateList a(ImageView imageView) {
        return iE.a(imageView);
    }

    public static void a(ImageView imageView, ColorStateList colorStateList) {
        iE.a(imageView, colorStateList);
    }

    public static PorterDuff.Mode b(ImageView imageView) {
        return iE.b(imageView);
    }

    public static void a(ImageView imageView, PorterDuff.Mode mode) {
        iE.a(imageView, mode);
    }
}
