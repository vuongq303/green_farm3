package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: OfflineStore.java */
/* JADX INFO: loaded from: classes.dex */
public class bg {
    private static bg aji = null;
    private boolean Jn = false;
    private bw ajj = new bw();
    private bn ajk = new bn();

    public static bg nv() {
        if (aji == null) {
            aji = new bg();
        }
        return aji;
    }

    public int aw(String str) {
        if (this.Jn) {
            GLLib.be("Offline Store is already initialized.");
            return -2147483645;
        }
        this.ajj = new bw();
        this.ajk = new bn();
        int iE = e((bc) new bc(str).cd("offline_store"));
        if (iE != 0) {
            GLLib.be("Offline Store failed to parse the buffer.");
            return iE;
        }
        this.Jn = true;
        return iE;
    }

    public int cs(String str) {
        if (e((bc) new bc(str).cd("offline_store")) == 0) {
            return 0;
        }
        GLLib.be("Offline Store failed to parse the buffer.");
        return -2147483622;
    }

    public bw nw() {
        return this.ajj;
    }

    public int e(bc bcVar) {
        if (bcVar == null) {
            return -2147483622;
        }
        int iA = this.ajj.a(bcVar);
        if (iA != 0) {
            GLLib.be("Offline Store failed to parse the items buffer.");
            return iA;
        }
        this.ajk.hM();
        this.ajk.a(bcVar);
        if (iA != 0) {
            GLLib.be("Offline Store failed to parse the promotion buffer.");
            return iA;
        }
        return iA;
    }

    public String nx() {
        return this.ajk.hX();
    }

    public String ny() {
        return this.ajk.nE();
    }
}
