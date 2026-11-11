package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/* JADX INFO: compiled from: PopupWindowCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class t {
    static final x jj;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            jj = new w();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            jj = new v();
        } else if (Build.VERSION.SDK_INT >= 19) {
            jj = new u();
        } else {
            jj = new x();
        }
    }

    public static void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        jj.a(popupWindow, view, i, i2, i3);
    }

    public static void a(PopupWindow popupWindow, boolean z) {
        jj.a(popupWindow, z);
    }

    public static void a(PopupWindow popupWindow, int i) {
        jj.a(popupWindow, i);
    }
}
