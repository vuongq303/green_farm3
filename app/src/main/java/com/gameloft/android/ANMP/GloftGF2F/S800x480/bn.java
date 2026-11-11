package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: compiled from: PromotionOI.java */
/* JADX INFO: loaded from: classes.dex */
public class bn {
    private String Kq;
    private String ajO;

    public int a(bc bcVar) {
        Object objCd = bcVar.cd("promotion");
        if (objCd == null) {
            return -2147479542;
        }
        bc bcVar2 = (bc) objCd;
        Object objCd2 = bcVar2.cd(FirebaseAnalytics.Param.END_DATE);
        if (objCd2 == null) {
            return -2147483646;
        }
        this.ajO = (String) objCd2;
        Object objCd3 = bcVar2.cd("description");
        if (objCd3 == null) {
            return -2147483646;
        }
        this.Kq = (String) objCd3;
        return 0;
    }

    public void hM() {
        this.ajO = "";
        this.Kq = "";
    }

    public String nE() {
        return this.ajO;
    }

    public String hX() {
        return this.Kq;
    }
}
