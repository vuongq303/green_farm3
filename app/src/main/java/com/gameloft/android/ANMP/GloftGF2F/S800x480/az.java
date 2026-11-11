package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: ItemPrice.java */
/* JADX INFO: loaded from: classes.dex */
public class az {
    private String ait = "";
    private double aiu = 0.0d;

    public int a(bc bcVar) {
        Object objCd = bcVar.cd("currency");
        if (objCd == null) {
            return -2147483646;
        }
        this.ait = (String) objCd;
        Object objCd2 = bcVar.cd("price");
        if (objCd2 == null) {
            return -2147483646;
        }
        try {
            this.aiu = Double.parseDouble((String) objCd2);
            return this.aiu < 0.0d ? -2147483646 : 0;
        } catch (NumberFormatException e) {
            return -2147483646;
        }
    }

    public double nm() {
        return this.aiu;
    }

    public String nn() {
        return this.ait;
    }
}
