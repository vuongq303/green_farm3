package javax.microedition.midlet;

import java.util.TimerTask;

/* JADX INFO: compiled from: MIDlet.java */
/* JADX INFO: loaded from: classes.dex */
public class l extends TimerTask {
    final /* synthetic */ MIDlet coU;

    public l(MIDlet mIDlet) {
        this.coU = mIDlet;
    }

    public void akP() {
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        this.coU.akD();
    }
}
