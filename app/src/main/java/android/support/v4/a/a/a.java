package android.support.v4.a.a;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

/* JADX INFO: compiled from: DrawableCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    static final f fz;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            fz = new e();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            fz = new d();
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            fz = new c();
        } else if (Build.VERSION.SDK_INT >= 17) {
            fz = new b();
        } else {
            fz = new f();
        }
    }

    public static void b(Drawable drawable) {
        fz.b(drawable);
    }

    public static void a(Drawable drawable, boolean z) {
        fz.a(drawable, z);
    }

    public static boolean c(Drawable drawable) {
        return fz.c(drawable);
    }

    public static void a(Drawable drawable, float f, float f2) {
        fz.a(drawable, f, f2);
    }

    public static void a(Drawable drawable, int i, int i2, int i3, int i4) {
        fz.a(drawable, i, i2, i3, i4);
    }

    public static void a(Drawable drawable, int i) {
        fz.a(drawable, i);
    }

    public static void a(Drawable drawable, ColorStateList colorStateList) {
        fz.a(drawable, colorStateList);
    }

    public static void a(Drawable drawable, PorterDuff.Mode mode) {
        fz.a(drawable, mode);
    }

    public static Drawable d(Drawable drawable) {
        return fz.d(drawable);
    }

    public static boolean b(Drawable drawable, int i) {
        return fz.b(drawable, i);
    }
}
