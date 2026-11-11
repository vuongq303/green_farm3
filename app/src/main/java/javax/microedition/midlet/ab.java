package javax.microedition.midlet;

/* JADX INFO: compiled from: WindowCallbackWrapper.java */
/* JADX INFO: loaded from: classes.dex */
class ab implements Runnable {
    final /* synthetic */ boolean coX;
    final /* synthetic */ aa coY;

    ab(aa aaVar, boolean z) {
        this.coY = aaVar;
        this.coX = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        App.aks().cp(this.coX);
    }
}
