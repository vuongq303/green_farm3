package javax.microedition.midlet;

/* JADX INFO: compiled from: MIDlet.java */
/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ MIDlet coU;

    i(MIDlet mIDlet) {
        this.coU = mIDlet;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!MIDlet.cnH) {
            this.coU.getWindow().setFlags(128, 128);
            MIDlet.cnH = true;
        }
    }
}
