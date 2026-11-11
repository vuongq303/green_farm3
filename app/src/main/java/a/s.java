package a;

import java.util.concurrent.CancellationException;

/* JADX INFO: compiled from: Task.java */
/* JADX INFO: loaded from: classes.dex */
final class s implements Runnable {
    final /* synthetic */ x zQ;
    final /* synthetic */ m zR;
    final /* synthetic */ j zT;
    final /* synthetic */ o zV;

    s(j jVar, x xVar, m mVar, o oVar) {
        this.zT = jVar;
        this.zQ = xVar;
        this.zR = mVar;
        this.zV = oVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.zT != null && this.zT.fw()) {
            this.zQ.fG();
            return;
        }
        try {
            this.zQ.setResult(this.zR.then(this.zV));
        } catch (CancellationException e) {
            this.zQ.fG();
        } catch (Exception e2) {
            this.zQ.c(e2);
        }
    }
}
