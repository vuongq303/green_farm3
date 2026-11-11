package android.support.v4.a.a;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableCompat.java */
/* JADX INFO: loaded from: classes.dex */
class d extends c {
    d() {
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, float f, float f2) {
        drawable.setHotspot(f, f2);
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }

    @Override // android.support.v4.a.a.c, android.support.v4.a.a.f
    public Drawable d(Drawable drawable) {
        if (!(drawable instanceof o)) {
            return new m(drawable);
        }
        return drawable;
    }
}
