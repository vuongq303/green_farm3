package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ScrollingTabContainerView.java */
/* JADX INFO: loaded from: classes.dex */
class cr implements Runnable {
    final /* synthetic */ View xC;
    final /* synthetic */ cq xD;

    cr(cq cqVar, View view) {
        this.xD = cqVar;
        this.xC = view;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.xD.smoothScrollTo(this.xC.getLeft() - ((this.xD.getWidth() - this.xC.getWidth()) / 2), 0);
        this.xD.xt = null;
    }
}
