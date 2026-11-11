package android.support.v7.widget;

import android.view.ViewParent;

/* JADX INFO: compiled from: ForwardingListener.java */
/* JADX INFO: loaded from: classes.dex */
class bu implements Runnable {
    final /* synthetic */ bt vG;

    bu(bt btVar) {
        this.vG = btVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ViewParent parent = this.vG.vB.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }
}
