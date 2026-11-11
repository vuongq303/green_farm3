package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

/* JADX INFO: compiled from: ImageViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class n extends l {
    n() {
    }

    @Override // android.support.v4.widget.l, android.support.v4.widget.m
    public ColorStateList a(ImageView imageView) {
        return imageView.getImageTintList();
    }

    @Override // android.support.v4.widget.l, android.support.v4.widget.m
    public void a(ImageView imageView, ColorStateList colorStateList) {
        imageView.setImageTintList(colorStateList);
        if (Build.VERSION.SDK_INT == 21) {
            Drawable drawable = imageView.getDrawable();
            boolean z = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true;
            if (drawable != null && z) {
                if (drawable.isStateful()) {
                    drawable.setState(imageView.getDrawableState());
                }
                imageView.setImageDrawable(drawable);
            }
        }
    }

    @Override // android.support.v4.widget.l, android.support.v4.widget.m
    public void a(ImageView imageView, PorterDuff.Mode mode) {
        imageView.setImageTintMode(mode);
        if (Build.VERSION.SDK_INT == 21) {
            Drawable drawable = imageView.getDrawable();
            boolean z = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true;
            if (drawable != null && z) {
                if (drawable.isStateful()) {
                    drawable.setState(imageView.getDrawableState());
                }
                imageView.setImageDrawable(drawable);
            }
        }
    }

    @Override // android.support.v4.widget.l, android.support.v4.widget.m
    public PorterDuff.Mode b(ImageView imageView) {
        return imageView.getImageTintMode();
    }
}
