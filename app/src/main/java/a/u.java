package a;

/* JADX INFO: Add missing generic type declarations: [TContinuationResult] */
/* JADX INFO: compiled from: Task.java */
/* JADX INFO: loaded from: classes.dex */
class u<TContinuationResult> implements m<TContinuationResult, Void> {
    final /* synthetic */ t zW;

    u(t tVar) {
        this.zW = tVar;
    }

    @Override // a.m
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Void then(o<TContinuationResult> oVar) {
        if ((this.zW.zT != null && this.zW.zT.fw()) || oVar.isCancelled()) {
            this.zW.zQ.fG();
        } else if (oVar.fB()) {
            this.zW.zQ.c(oVar.getError());
        } else {
            this.zW.zQ.setResult(oVar.getResult());
        }
        return null;
    }
}
