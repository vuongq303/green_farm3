package android.support.v7.app;

/* JADX INFO: compiled from: ToolbarActionBar.java */
/* JADX INFO: loaded from: classes.dex */
final class bc implements android.support.v7.c.a.aj {
    private boolean lG;
    final /* synthetic */ bb mG;

    bc(bb bbVar) {
        this.mG = bbVar;
    }

    @Override // android.support.v7.c.a.aj
    public boolean c(android.support.v7.c.a.s sVar) {
        if (this.mG.mB == null) {
            return false;
        }
        this.mG.mB.onMenuOpened(108, sVar);
        return true;
    }

    @Override // android.support.v7.c.a.aj
    public void b(android.support.v7.c.a.s sVar, boolean z) {
        if (!this.lG) {
            this.lG = true;
            this.mG.mA.dismissPopupMenus();
            if (this.mG.mB != null) {
                this.mG.mB.onPanelClosed(108, sVar);
            }
            this.lG = false;
        }
    }
}
