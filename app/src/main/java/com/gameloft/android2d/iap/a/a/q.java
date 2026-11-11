package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: IabException.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends Exception {
    ad blZ;

    public q(ad adVar) {
        this(adVar, (Exception) null);
    }

    public q(int i, String str) {
        this(new ad(i, str));
    }

    public q(ad adVar, Exception exc) {
        super(adVar.getMessage(), exc);
        this.blZ = adVar;
    }

    public q(int i, String str, Exception exc) {
        this(new ad(i, str), exc);
    }

    public ad Sl() {
        return this.blZ;
    }
}
