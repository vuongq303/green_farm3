package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class l implements Runnable {
    final /* synthetic */ i sV;
    private o sW;

    public l(i iVar, o oVar) {
        this.sV = iVar;
        this.sW = oVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.sV.nw != null) {
            this.sV.nw.da();
        }
        View view = (View) this.sV.pq;
        if (view != null && view.getWindowToken() != null && this.sW.dA()) {
            this.sV.sP = this.sW;
        }
        this.sV.sR = null;
    }
}
