package android.support.v7.app;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class ao implements Runnable {
    final /* synthetic */ aj lR;

    ao(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.lR.ly.showAtLocation(this.lR.lx, 55, 0, 0);
        this.lR.bR();
        if (this.lR.bQ()) {
            this.lR.lx.setAlpha(0.0f);
            this.lR.lA = android.support.v4.view.aa.h(this.lR.lx).a(1.0f);
            this.lR.lA.a(new ap(this));
            return;
        }
        this.lR.lx.setAlpha(1.0f);
        this.lR.lx.setVisibility(0);
    }
}
