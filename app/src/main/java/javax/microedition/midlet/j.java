package javax.microedition.midlet;

/* JADX INFO: compiled from: MIDlet.java */
/* JADX INFO: loaded from: classes.dex */
class j implements Runnable {
    final /* synthetic */ MIDlet coU;

    j(MIDlet mIDlet) {
        this.coU = mIDlet;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (MIDlet.cnH) {
            this.coU.getWindow().setFlags(0, 128);
            MIDlet.cnH = false;
        }
    }
}
