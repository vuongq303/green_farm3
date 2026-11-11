package javax.microedition.midlet;

import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child_IAP.java */
/* JADX INFO: loaded from: classes.dex */
final class s implements Runnable {
    s() {
    }

    @Override // java.lang.Runnable
    public void run() {
        while (MIDlet.coM) {
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
            }
        }
        com.gameloft.android2d.iap.b.a(au.getActivity(), MIDlet.cnT, MIDlet.coh, MIDlet.cnU, MIDlet.cnV, MIDlet.cnW, MIDlet.cnY, MIDlet.cnZ, MIDlet.coa, MIDlet.cob, MIDlet.coc, MIDlet.cof, com.gameloft.android.wrapper.d.beJ, com.gameloft.android.wrapper.d.beK, MIDlet.cod, MIDlet.coe, com.gameloft.android.wrapper.d.beN, com.gameloft.android.wrapper.d.beO, MIDlet.cnz, MIDlet.cog);
    }
}
