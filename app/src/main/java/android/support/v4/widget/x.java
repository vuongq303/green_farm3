package android.support.v4.widget;

import android.support.v4.view.aa;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: PopupWindowCompat.java */
/* JADX INFO: loaded from: classes.dex */
class x {
    private static Method jl;
    private static boolean jm;

    x() {
    }

    public void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        if ((android.support.v4.view.j.getAbsoluteGravity(i3, aa.f(view)) & 7) == 5) {
            i -= popupWindow.getWidth() - view.getWidth();
        }
        popupWindow.showAsDropDown(view, i, i2);
    }

    public void a(PopupWindow popupWindow, boolean z) {
    }

    public void a(PopupWindow popupWindow, int i) {
        if (!jm) {
            try {
                jl = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                jl.setAccessible(true);
            } catch (Exception e) {
            }
            jm = true;
        }
        if (jl != null) {
            try {
                jl.invoke(popupWindow, Integer.valueOf(i));
            } catch (Exception e2) {
            }
        }
    }
}
