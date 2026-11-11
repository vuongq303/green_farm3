package android.support.v4.app;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: OneShotPreDrawListener.java */
/* JADX INFO: loaded from: classes.dex */
class da implements View.OnAttachStateChangeListener, ViewTreeObserver.OnPreDrawListener {
    private ViewTreeObserver ea;
    private final Runnable eb;
    private final View mView;

    private da(View view, Runnable runnable) {
        this.mView = view;
        this.ea = view.getViewTreeObserver();
        this.eb = runnable;
    }

    public static da a(View view, Runnable runnable) {
        da daVar = new da(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(daVar);
        view.addOnAttachStateChangeListener(daVar);
        return daVar;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        ah();
        this.eb.run();
        return true;
    }

    public void ah() {
        if (this.ea.isAlive()) {
            this.ea.removeOnPreDrawListener(this);
        } else {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.mView.removeOnAttachStateChangeListener(this);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        this.ea = view.getViewTreeObserver();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        ah();
    }
}
