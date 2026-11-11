package android.support.v4.d;

/* JADX INFO: compiled from: SelfDestructiveThread.java */
/* JADX INFO: loaded from: classes.dex */
class l implements Runnable {
    private final /* synthetic */ n gi;
    final /* synthetic */ k gj;
    private final /* synthetic */ Object gk;

    l(k kVar, n nVar, Object obj) {
        this.gj = kVar;
        this.gi = nVar;
        this.gk = obj;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.gi.i(this.gk);
    }
}
