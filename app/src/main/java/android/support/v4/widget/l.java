package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/* JADX INFO: compiled from: ImageViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class l implements m {
    l() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v4.widget.m
    public ColorStateList a(ImageView imageView) {
        if (imageView instanceof z) {
            return ((z) imageView).bm();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v4.widget.m
    public void a(ImageView imageView, ColorStateList colorStateList) {
        if (imageView instanceof z) {
            ((z) imageView).c(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v4.widget.m
    public void a(ImageView imageView, PorterDuff.Mode mode) {
        if (imageView instanceof z) {
            ((z) imageView).c(mode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v4.widget.m
    public PorterDuff.Mode b(ImageView imageView) {
        if (imageView instanceof z) {
            return ((z) imageView).bn();
        }
        return null;
    }
}
