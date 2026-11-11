package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: TrackingConnection.java */
/* JADX INFO: loaded from: classes.dex */
public class bv {
    String Ba;
    boolean bda;
    private String bdb;
    private bx AG = null;
    private n bdc = null;
    m bcZ = new m(this);

    public bv(String str) {
        this.Ba = str;
    }

    void b(bx bxVar) {
        this.AG = bxVar;
    }

    void a(n nVar) {
        this.bdc = nVar;
    }

    void mg() {
        if (this.bcZ != null && !this.bda && !this.bcZ.gj()) {
            String strValueOf = this.bcZ.BN <= 0 ? null : String.valueOf(this.bcZ.BN);
            if (strValueOf == null) {
                o.u("RESPONSE NULL CONNECTION FAILED >>>>>>>>>>> Response code: " + this.bcZ.BN);
                a(strValueOf, false);
            } else if (strValueOf.indexOf("200") != -1) {
                o.u("RESPONSE OK CONNECTION SUCCESS >>>>>>>>>>> " + strValueOf);
                int i = this.bcZ.BO;
                if (this.bdc != null && i > 0) {
                    this.bdc.aN(i);
                }
                a(strValueOf, true);
            } else {
                o.u("RESPONSE FAILED >>>>>>>>>>> " + strValueOf + " Response code: " + this.bcZ.BN);
                this.AG.c(1405, "[Connection]: (!) Received non-200 HTTP status code! Code was: " + this.bcZ.BN, this.bdc.gn());
                a(strValueOf, false);
            }
            this.bda = true;
            if (this.bdb != null) {
                this.bdb = null;
            }
            this.bcZ.gh();
            this.bcZ = null;
        }
    }

    void a(String str, boolean z) {
        if (this.bdc != null) {
            this.bdc.a(str, z);
        }
    }

    void N(int i, String str) {
        this.AG.c(i, str, this.bdc.gn());
    }

    int gr() {
        if (this.bdc != null) {
            return this.bdc.gr();
        }
        return -1;
    }

    void dR(String str) {
        if (this.bcZ == null) {
        }
        this.bcZ = new m(this);
        if (this.Ba == null) {
            o.u("SERVERCONFIG HAS NOT BEEN LOADED YET !!!!!");
            return;
        }
        String str2 = this.Ba;
        this.bdb = str;
        this.bcZ.d(str2, this.bdb);
        this.bda = false;
    }
}
