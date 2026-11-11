package android.support.v4.view;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: compiled from: ViewParentCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class an {
    static final aq hz;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            hz = new ap();
        } else if (Build.VERSION.SDK_INT >= 19) {
            hz = new ao();
        } else {
            hz = new aq();
        }
    }

    public static boolean a(ViewParent viewParent, View view, View view2, int i, int i2) {
        if (viewParent instanceof w) {
            return ((w) viewParent).a(view, view2, i, i2);
        }
        if (i2 == 0) {
            return hz.a(viewParent, view, view2, i);
        }
        return false;
    }

    public static void b(ViewParent viewParent, View view, View view2, int i, int i2) {
        if (viewParent instanceof w) {
            ((w) viewParent).b(view, view2, i, i2);
        } else if (i2 == 0) {
            hz.b(viewParent, view, view2, i);
        }
    }

    public static void a(ViewParent viewParent, View view, int i) {
        if (viewParent instanceof w) {
            ((w) viewParent).a(view, i);
        } else if (i == 0) {
            hz.a(viewParent, view);
        }
    }

    public static void a(ViewParent viewParent, View view, int i, int i2, int i3, int i4, int i5) {
        if (viewParent instanceof w) {
            ((w) viewParent).a(view, i, i2, i3, i4, i5);
        } else if (i5 == 0) {
            hz.a(viewParent, view, i, i2, i3, i4);
        }
    }

    public static void a(ViewParent viewParent, View view, int i, int i2, int[] iArr, int i3) {
        if (viewParent instanceof w) {
            ((w) viewParent).a(view, i, i2, iArr, i3);
        } else if (i3 == 0) {
            hz.a(viewParent, view, i, i2, iArr);
        }
    }

    public static boolean a(ViewParent viewParent, View view, float f, float f2, boolean z) {
        return hz.a(viewParent, view, f, f2, z);
    }

    public static boolean a(ViewParent viewParent, View view, float f, float f2) {
        return hz.a(viewParent, view, f, f2);
    }
}
