package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: BillingMethod.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    private String Ki = "";
    private String Kj = "";
    private ba Kk = new ba();
    private ba Kl = new ba();

    public int a(bc bcVar) {
        Object objCd = bcVar.cd("type");
        if (objCd != null) {
            this.Kj = (String) objCd;
        } else {
            hM();
        }
        Object objCd2 = bcVar.cd("name");
        if (objCd2 != null) {
            this.Ki = (String) objCd2;
        } else {
            hM();
        }
        Object objCd3 = bcVar.cd("price");
        if (objCd3 != null) {
            if (objCd3.getClass().isArray()) {
                Object[] objArr = (Object[]) objCd3;
                bc[] bcVarArr = new bc[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    bcVarArr[i] = (bc) objArr[i];
                }
                this.Kk.a(bcVarArr);
            }
        } else {
            hM();
        }
        if (this.Kk.hP() < 1) {
            return -2147483646;
        }
        Object objCd4 = bcVar.cd("replaced_price");
        if (objCd4 == null || !objCd4.getClass().isArray()) {
            return 0;
        }
        Object[] objArr2 = (Object[]) objCd4;
        bc[] bcVarArr2 = new bc[objArr2.length];
        for (int i2 = 0; i2 < objArr2.length; i2++) {
            bcVarArr2[i2] = (bc) objArr2[i2];
        }
        this.Kl.a(bcVarArr2);
        return 0;
    }

    public void hM() {
        this.Ki = new String();
        this.Kj = new String();
        this.Kk = new ba();
        this.Kl = new ba();
    }

    public ba hN() {
        return this.Kk;
    }

    public ba hO() {
        return this.Kl;
    }
}
