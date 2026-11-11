package android.support.v4.widget;

import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

/* JADX INFO: compiled from: PopupWindowCompat.java */
/* JADX INFO: loaded from: classes.dex */
class v extends u {
    private static Field jk;

    v() {
    }

    static {
        try {
            jk = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            jk.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
        }
    }

    @Override // android.support.v4.widget.x
    public void a(PopupWindow popupWindow, boolean z) {
        if (jk != null) {
            try {
                jk.set(popupWindow, Boolean.valueOf(z));
            } catch (IllegalAccessException e) {
                Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e);
            }
        }
    }
}
