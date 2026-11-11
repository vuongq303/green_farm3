package android.support.v4.a.a;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableCompat.java */
/* JADX INFO: loaded from: classes.dex */
class f {
    f() {
    }

    public void b(Drawable drawable) {
        drawable.jumpToCurrentState();
    }

    public void a(Drawable drawable, boolean z) {
    }

    public boolean c(Drawable drawable) {
        return false;
    }

    public void a(Drawable drawable, float f, float f2) {
    }

    public void a(Drawable drawable, int i, int i2, int i3, int i4) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(Drawable drawable, int i) {
        if (drawable instanceof o) {
            ((o) drawable).setTint(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(Drawable drawable, ColorStateList colorStateList) {
        if (drawable instanceof o) {
            ((o) drawable).setTintList(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(Drawable drawable, PorterDuff.Mode mode) {
        if (drawable instanceof o) {
            ((o) drawable).setTintMode(mode);
        }
    }

    public Drawable d(Drawable drawable) {
        if (!(drawable instanceof o)) {
            return new h(drawable);
        }
        return drawable;
    }

    public boolean b(Drawable drawable, int i) {
        return false;
    }
}
