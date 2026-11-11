package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class aa {
    static final al hq;

    static {
        if (android.support.v4.c.a.ax()) {
            hq = new ak();
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            hq = new aj();
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            hq = new ai();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            hq = new ag();
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            hq = new af();
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            hq = new ae();
            return;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            hq = new ad();
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            hq = new ac();
        } else if (Build.VERSION.SDK_INT >= 15) {
            hq = new ab();
        } else {
            hq = new al();
        }
    }

    public static void e(View view) {
        hq.e(view);
    }

    public static void b(View view, Runnable runnable) {
        hq.b(view, runnable);
    }

    public static void a(View view, Runnable runnable, long j) {
        hq.a(view, runnable, j);
    }

    public static int f(View view) {
        return hq.f(view);
    }

    public static int g(View view) {
        return hq.g(view);
    }

    public static ar h(View view) {
        return hq.h(view);
    }

    public static void a(View view, float f) {
        hq.a(view, f);
    }

    public static String i(View view) {
        return hq.i(view);
    }

    public static int j(View view) {
        return hq.j(view);
    }

    public static void k(View view) {
        hq.k(view);
    }

    public static void a(View view, y yVar) {
        hq.a(view, yVar);
    }

    public static ay a(View view, ay ayVar) {
        return hq.a(view, ayVar);
    }

    public static boolean l(View view) {
        return hq.l(view);
    }

    public static void setBackground(View view, Drawable drawable) {
        hq.setBackground(view, drawable);
    }

    public static ColorStateList m(View view) {
        return hq.m(view);
    }

    public static void a(View view, ColorStateList colorStateList) {
        hq.a(view, colorStateList);
    }

    public static PorterDuff.Mode n(View view) {
        return hq.n(view);
    }

    public static void a(View view, PorterDuff.Mode mode) {
        hq.a(view, mode);
    }

    public static void o(View view) {
        hq.o(view);
    }

    public static boolean p(View view) {
        return hq.p(view);
    }

    public static boolean isAttachedToWindow(View view) {
        return hq.isAttachedToWindow(view);
    }

    public static boolean q(View view) {
        return hq.q(view);
    }

    public static void a(View view, int i, int i2) {
        hq.a(view, i, i2);
    }
}
