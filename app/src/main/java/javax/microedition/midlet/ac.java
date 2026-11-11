package javax.microedition.midlet;

/* JADX INFO: compiled from: WindowCallbackWrapper.java */
/* JADX INFO: loaded from: classes.dex */
class ac implements Runnable {
    final /* synthetic */ aa coY;

    ac(aa aaVar) {
        this.coY = aaVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.coY) {
            if (this.coY.coV != null) {
                this.coY.coV.run();
            }
        }
    }
}
