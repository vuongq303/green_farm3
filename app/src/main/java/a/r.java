package a;

/* JADX INFO: Add missing generic type declarations: [TContinuationResult, TResult] */
/* JADX INFO: compiled from: Task.java */
/* JADX INFO: loaded from: classes.dex */
class r<TContinuationResult, TResult> implements m<TResult, o<TContinuationResult>> {
    final /* synthetic */ m zR;
    final /* synthetic */ j zT;
    final /* synthetic */ o zU;

    r(o oVar, j jVar, m mVar) {
        this.zU = oVar;
        this.zT = jVar;
        this.zR = mVar;
    }

    @Override // a.m
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public o<TContinuationResult> then(o<TResult> oVar) {
        if (this.zT != null && this.zT.fw()) {
            return o.fC();
        }
        if (oVar.fB()) {
            return o.a(oVar.getError());
        }
        if (oVar.isCancelled()) {
            return o.fC();
        }
        return oVar.a(this.zR);
    }
}
