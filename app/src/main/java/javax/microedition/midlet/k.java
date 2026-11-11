package javax.microedition.midlet;

import android.widget.Toast;

/* JADX INFO: compiled from: MIDlet.java */
/* JADX INFO: loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ String biX;
    final /* synthetic */ MIDlet coU;

    k(MIDlet mIDlet, String str) {
        this.coU = mIDlet;
        this.biX = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (MIDlet.bit != null) {
            MIDlet.bit.cancel();
        }
        MIDlet.bit = Toast.makeText(this.coU.getApplicationContext(), this.biX, 0);
        MIDlet.bit.show();
    }
}
