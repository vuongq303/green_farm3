package android.support.v4.widget;

import android.support.v4.view.aa;

/* JADX INFO: compiled from: AutoScrollHelper.java */
/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ a iz;

    c(a aVar) {
        this.iz = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.iz.ik) {
            if (this.iz.ii) {
                this.iz.ii = false;
                this.iz.hW.start();
            }
            b bVar = this.iz.hW;
            if (bVar.isFinished() || !this.iz.aV()) {
                this.iz.ik = false;
                return;
            }
            if (this.iz.ij) {
                this.iz.ij = false;
                this.iz.aY();
            }
            bVar.aZ();
            this.iz.f(bVar.bc(), bVar.bd());
            aa.b(this.iz.hY, this);
        }
    }
}
