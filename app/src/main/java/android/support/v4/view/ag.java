package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ag extends af {
    ag() {
    }

    @Override // android.support.v4.view.al
    public String i(View view) {
        return view.getTransitionName();
    }

    @Override // android.support.v4.view.ac, android.support.v4.view.al
    public void k(View view) {
        view.requestApplyInsets();
    }

    @Override // android.support.v4.view.al
    public void a(View view, float f) {
        view.setElevation(f);
    }

    @Override // android.support.v4.view.al
    public void a(View view, y yVar) {
        if (yVar == null) {
            view.setOnApplyWindowInsetsListener(null);
        } else {
            view.setOnApplyWindowInsetsListener(new ah(this, yVar));
        }
    }

    @Override // android.support.v4.view.al
    public void o(View view) {
        view.stopNestedScroll();
    }

    @Override // android.support.v4.view.al
    public ColorStateList m(View view) {
        return view.getBackgroundTintList();
    }

    @Override // android.support.v4.view.al
    public void a(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        if (Build.VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground();
            boolean z = (view.getBackgroundTintList() == null || view.getBackgroundTintMode() == null) ? false : true;
            if (background != null && z) {
                if (background.isStateful()) {
                    background.setState(view.getDrawableState());
                }
                view.setBackground(background);
            }
        }
    }

    @Override // android.support.v4.view.al
    public void a(View view, PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
        if (Build.VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground();
            boolean z = (view.getBackgroundTintList() == null || view.getBackgroundTintMode() == null) ? false : true;
            if (background != null && z) {
                if (background.isStateful()) {
                    background.setState(view.getDrawableState());
                }
                view.setBackground(background);
            }
        }
    }

    @Override // android.support.v4.view.al
    public PorterDuff.Mode n(View view) {
        return view.getBackgroundTintMode();
    }

    @Override // android.support.v4.view.al
    public ay a(View view, ay ayVar) {
        WindowInsets windowInsets = (WindowInsets) ay.a(ayVar);
        WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        if (windowInsetsOnApplyWindowInsets != windowInsets) {
            windowInsets = new WindowInsets(windowInsetsOnApplyWindowInsets);
        }
        return ay.l(windowInsets);
    }
}
