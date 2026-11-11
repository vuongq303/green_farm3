package android.support.v7.widget;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class p implements android.support.v7.c.a.aj {
    final /* synthetic */ i sV;

    p(i iVar) {
        this.sV = iVar;
    }

    @Override // android.support.v7.c.a.aj
    public boolean c(android.support.v7.c.a.s sVar) {
        if (sVar == null) {
            return false;
        }
        this.sV.sU = ((android.support.v7.c.a.as) sVar).getItem().getItemId();
        android.support.v7.c.a.aj ajVarCL = this.sV.cL();
        return ajVarCL != null ? ajVarCL.c(sVar) : false;
    }

    @Override // android.support.v7.c.a.aj
    public void b(android.support.v7.c.a.s sVar, boolean z) {
        if (sVar instanceof android.support.v7.c.a.as) {
            sVar.dk().u(false);
        }
        android.support.v7.c.a.aj ajVarCL = this.sV.cL();
        if (ajVarCL != null) {
            ajVarCL.b(sVar, z);
        }
    }
}
