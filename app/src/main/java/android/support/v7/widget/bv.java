package android.support.v7.widget;

/* JADX INFO: compiled from: ForwardingListener.java */
/* JADX INFO: loaded from: classes.dex */
class bv implements Runnable {
    final /* synthetic */ bt vG;

    bv(bt btVar) {
        this.vG = btVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.vG.eK();
    }
}
