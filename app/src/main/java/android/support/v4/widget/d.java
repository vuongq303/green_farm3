package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.CompoundButton;

/* JADX INFO: compiled from: CompoundButtonCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static final g iA;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            iA = new f();
        } else if (Build.VERSION.SDK_INT >= 21) {
            iA = new e();
        } else {
            iA = new g();
        }
    }

    public static void a(CompoundButton compoundButton, ColorStateList colorStateList) {
        iA.a(compoundButton, colorStateList);
    }

    public static void a(CompoundButton compoundButton, PorterDuff.Mode mode) {
        iA.a(compoundButton, mode);
    }

    public static Drawable a(CompoundButton compoundButton) {
        return iA.a(compoundButton);
    }
}
