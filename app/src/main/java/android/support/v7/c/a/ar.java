package android.support.v7.c.a;

import android.view.View;

/* JADX INFO: compiled from: StandardMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class ar implements View.OnAttachStateChangeListener {
    final /* synthetic */ ap rv;

    ar(ap apVar) {
        this.rv = apVar;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        if (this.rv.pP != null) {
            if (!this.rv.pP.isAlive()) {
                this.rv.pP = view.getViewTreeObserver();
            }
            this.rv.pP.removeGlobalOnLayoutListener(this.rv.pB);
        }
        view.removeOnAttachStateChangeListener(this);
    }
}
