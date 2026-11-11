package android.support.v7.c.a;

import android.view.View;

/* JADX INFO: compiled from: CascadingMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class j implements View.OnAttachStateChangeListener {
    final /* synthetic */ h pS;

    j(h hVar) {
        this.pS = hVar;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        if (this.pS.pP != null) {
            if (!this.pS.pP.isAlive()) {
                this.pS.pP = view.getViewTreeObserver();
            }
            this.pS.pP.removeGlobalOnLayoutListener(this.pS.pB);
        }
        view.removeOnAttachStateChangeListener(this);
    }
}
