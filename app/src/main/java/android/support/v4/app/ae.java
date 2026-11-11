package android.support.v4.app;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class ae implements Runnable {
    final /* synthetic */ ad bv;

    ae(ad adVar) {
        this.bv = adVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bv.execPendingActions();
    }
}
