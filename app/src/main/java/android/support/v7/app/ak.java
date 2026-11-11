package android.support.v7.app;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class ak implements Runnable {
    final /* synthetic */ aj lR;

    ak(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if ((this.lR.lL & 1) != 0) {
            this.lR.S(0);
        }
        if ((this.lR.lL & 4096) != 0) {
            this.lR.S(108);
        }
        this.lR.lK = false;
        this.lR.lL = 0;
    }
}
