package android.support.v4.view;

import android.graphics.drawable.Drawable;
import android.view.View;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ac extends ab {
    ac() {
    }

    @Override // android.support.v4.view.al
    public void e(View view) {
        view.postInvalidateOnAnimation();
    }

    @Override // android.support.v4.view.al
    public void b(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    @Override // android.support.v4.view.al
    public void a(View view, Runnable runnable, long j) {
        view.postOnAnimationDelayed(runnable, j);
    }

    @Override // android.support.v4.view.al
    public int g(View view) {
        return view.getMinimumHeight();
    }

    @Override // android.support.v4.view.al
    public void k(View view) {
        view.requestFitSystemWindows();
    }

    @Override // android.support.v4.view.al
    public boolean l(View view) {
        return view.hasOverlappingRendering();
    }

    @Override // android.support.v4.view.al
    public void setBackground(View view, Drawable drawable) {
        view.setBackground(drawable);
    }
}
