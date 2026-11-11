package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class n extends bt {
    final /* synthetic */ i sY;
    final /* synthetic */ m sZ;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    n(m mVar, View view, i iVar) {
        super(view);
        this.sZ = mVar;
        this.sY = iVar;
    }

    @Override // android.support.v7.widget.bt
    public android.support.v7.c.a.ao cJ() {
        if (this.sZ.sV.sP == null) {
            return null;
        }
        return this.sZ.sV.sP.dz();
    }

    @Override // android.support.v7.widget.bt
    public boolean cK() {
        this.sZ.sV.showOverflowMenu();
        return true;
    }

    @Override // android.support.v7.widget.bt
    public boolean dW() {
        if (this.sZ.sV.sR != null) {
            return false;
        }
        this.sZ.sV.hideOverflowMenu();
        return true;
    }
}
