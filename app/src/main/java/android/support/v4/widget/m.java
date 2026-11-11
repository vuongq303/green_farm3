package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/* JADX INFO: compiled from: ImageViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
interface m {
    ColorStateList a(ImageView imageView);

    void a(ImageView imageView, ColorStateList colorStateList);

    void a(ImageView imageView, PorterDuff.Mode mode);

    PorterDuff.Mode b(ImageView imageView);
}
