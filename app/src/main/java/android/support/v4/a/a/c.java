package android.support.v4.a.a;

import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableCompat.java */
/* JADX INFO: loaded from: classes.dex */
class c extends b {
    c() {
    }

    @Override // android.support.v4.a.a.f
    public void a(Drawable drawable, boolean z) {
        drawable.setAutoMirrored(z);
    }

    @Override // android.support.v4.a.a.f
    public boolean c(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    @Override // android.support.v4.a.a.f
    public Drawable d(Drawable drawable) {
        if (!(drawable instanceof o)) {
            return new k(drawable);
        }
        return drawable;
    }
}
