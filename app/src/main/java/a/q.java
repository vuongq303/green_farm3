package a;

import java.util.concurrent.Executor;

/* JADX INFO: Add missing generic type declarations: [TResult] */
/* JADX INFO: compiled from: Task.java */
/* JADX INFO: loaded from: classes.dex */
class q<TResult> implements m<TResult, Void> {
    final /* synthetic */ x zQ;
    final /* synthetic */ m zR;
    final /* synthetic */ Executor zS;
    final /* synthetic */ j zT;
    final /* synthetic */ o zU;

    q(o oVar, x xVar, m mVar, Executor executor, j jVar) {
        this.zU = oVar;
        this.zQ = xVar;
        this.zR = mVar;
        this.zS = executor;
        this.zT = jVar;
    }

    @Override // a.m
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Void then(o<TResult> oVar) {
        o.b(this.zQ, this.zR, oVar, this.zS, this.zT);
        return null;
    }
}
