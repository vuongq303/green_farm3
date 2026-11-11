package android.support.v4.app;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class aj implements Runnable {
    final /* synthetic */ ai bA;

    aj(ai aiVar) {
        this.bA = aiVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bA.mView.setLayerType(0, null);
    }
}
