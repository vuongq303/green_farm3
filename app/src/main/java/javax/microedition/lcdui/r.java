package javax.microedition.lcdui;

import com.gameloft.android.wrapper.au;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Display.java */
/* JADX INFO: loaded from: classes.dex */
public class r {
    MIDlet cmo;
    public static boolean cml = false;
    private static t cmm = null;
    private static r cmn = null;
    public static Runnable callback = null;
    public static boolean cmp = true;

    public static r b(MIDlet mIDlet) {
        if (cmn == null) {
            cmn = new r();
        }
        cmn.cmo = mIDlet;
        return cmn;
    }

    public t akf() {
        return cmm;
    }

    public void a(t tVar) {
        t tVar2 = cmm;
        if (tVar != null) {
            cmm = tVar;
            this.cmo.runOnUiThread(new s(this));
        }
        if (tVar2 != null) {
            ((MIDlet) au.getContext()).closeOptionsMenu();
        }
    }
}
