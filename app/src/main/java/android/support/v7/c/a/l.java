package android.support.v7.c.a;

import android.view.MenuItem;

/* JADX INFO: compiled from: CascadingMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class l implements Runnable {
    final /* synthetic */ m pT;
    final /* synthetic */ MenuItem pU;
    final /* synthetic */ s pV;
    final /* synthetic */ k pW;

    l(k kVar, m mVar, MenuItem menuItem, s sVar) {
        this.pW = kVar;
        this.pT = mVar;
        this.pU = menuItem;
        this.pV = sVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.pT != null) {
            this.pW.pS.pR = true;
            this.pT.lZ.u(false);
            this.pW.pS.pR = false;
        }
        if (this.pU.isEnabled() && this.pU.hasSubMenu()) {
            this.pV.a(this.pU, 4);
        }
    }
}
