package android.support.v7.app;

import android.view.MenuItem;

/* JADX INFO: compiled from: ToolbarActionBar.java */
/* JADX INFO: loaded from: classes.dex */
final class bd implements android.support.v7.c.a.t {
    final /* synthetic */ bb mG;

    bd(bb bbVar) {
        this.mG = bbVar;
    }

    @Override // android.support.v7.c.a.t
    public boolean a(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        return false;
    }

    @Override // android.support.v7.c.a.t
    public void a(android.support.v7.c.a.s sVar) {
        if (this.mG.mB != null) {
            if (this.mG.mA.isOverflowMenuShowing()) {
                this.mG.mB.onPanelClosed(108, sVar);
            } else if (this.mG.mB.onPreparePanel(0, null, sVar)) {
                this.mG.mB.onMenuOpened(108, sVar);
            }
        }
    }
}
