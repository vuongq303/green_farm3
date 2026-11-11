package android.support.v4.a.a;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: DrawableCompat.java */
/* JADX INFO: loaded from: classes.dex */
class b extends f {
    private static Method fA;
    private static boolean fB;

    b() {
    }

    @Override // android.support.v4.a.a.f
    public boolean b(Drawable drawable, int i) {
        if (!fB) {
            try {
                fA = Drawable.class.getDeclaredMethod("setLayoutDirection", Integer.TYPE);
                fA.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("DrawableCompatApi17", "Failed to retrieve setLayoutDirection(int) method", e);
            }
            fB = true;
        }
        if (fA != null) {
            try {
                fA.invoke(drawable, Integer.valueOf(i));
                return true;
            } catch (Exception e2) {
                Log.i("DrawableCompatApi17", "Failed to invoke setLayoutDirection(int) via reflection", e2);
                fA = null;
            }
        }
        return false;
    }
}
