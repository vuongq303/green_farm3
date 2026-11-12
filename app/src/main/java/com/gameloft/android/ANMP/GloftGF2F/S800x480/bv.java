package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.Vector;

/* JADX INFO: compiled from: StoreOfflineItem.java */
/* JADX INFO: loaded from: classes.dex */
public class bv {
    private String Ki;
    private int akr;
    private int akt;
    private Vector aku = new Vector();
    private d akv = new d();
    private int aks = -1;

    public int a(bc bcVar) {
        Object objCd = bcVar.cd("item");
        if (objCd == null) {
            return -2147483646;
        }
        this.Ki = (String) objCd;
        if (bcVar.cd("quantity") == null) {
            return -2147483646;
        }
        try {
            this.akr = bcVar.ce("quantity");
        } catch (Exception e) {
        }
        if (bcVar.cd("replaced_quantity") == null) {
            return -2147483646;
        }
        try {
            this.aks = bcVar.ce("replaced_quantity");
        } catch (Exception e2) {
        }
        Object objCd2 = bcVar.cd("category");
        if (objCd2 != null && objCd2.getClass().isArray()) {
            Object[] objArr = (Object[]) objCd2;
            this.akt = objArr.length;
            for (int i = 0; i < this.akt; i++) {
                this.aku.addElement((String) objArr[i]);
            }
        }
        Object objCd3 = bcVar.cd("billing_methods");
        if (objCd3 == null) {
            return 0;
        }
        if (objCd3.getClass().isArray()) {
            Object[] objArr2 = (Object[]) objCd3;
            bc[] bcVarArr = new bc[objArr2.length];
            for (int i2 = 0; i2 < objArr2.length; i2++) {
                bcVarArr[i2] = (bc) objArr2[i2];
            }
            this.akv.a(bcVarArr);
        }
        return this.akv.hP() < 1 ? -2147483646 : 0;
    }

    public String od() {
        return this.Ki;
    }

    public double oe() {
        return this.akr;
    }

    public double of() {
        return this.aks;
    }

    public boolean og() {
        return this.aks >= 0 && this.akr > this.aks;
    }

    public boolean a(c cVar) {
        return cVar.hN().hP() == 1 && cVar.hO().hP() == 1 && cVar.hN().eG(0).equals(cVar.hO().eG(0));
    }

    public int oh() {
        return this.akt;
    }

    public String eN(int i) {
        return (String) this.aku.elementAt(i);
    }

    public c bX(int i) {
        return this.akv.bX(i);
    }
}
