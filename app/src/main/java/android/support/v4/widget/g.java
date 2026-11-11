package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

/* JADX INFO: compiled from: CompoundButtonCompat.java */
/* JADX INFO: loaded from: classes.dex */
class g {
    private static Field iB;
    private static boolean iC;

    g() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(CompoundButton compoundButton, ColorStateList colorStateList) {
        if (compoundButton instanceof y) {
            ((y) compoundButton).b(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(CompoundButton compoundButton, PorterDuff.Mode mode) {
        if (compoundButton instanceof y) {
            ((y) compoundButton).b(mode);
        }
    }

    public Drawable a(CompoundButton compoundButton) {
        if (!iC) {
            try {
                iB = CompoundButton.class.getDeclaredField("mButtonDrawable");
                iB.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", e);
            }
            iC = true;
        }
        if (iB != null) {
            try {
                return (Drawable) iB.get(compoundButton);
            } catch (IllegalAccessException e2) {
                Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", e2);
                iB = null;
            }
        }
        return null;
    }
}
