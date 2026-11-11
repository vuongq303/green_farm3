package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.unity3d.ads.metadata.InAppPurchaseMetaData;

/* JADX INFO: compiled from: IAPStore.java */
/* JADX INFO: loaded from: classes.dex */
public class au {
    private static au adN = null;
    private bc adO = null;
    private boolean adP = false;
    private int adQ = 0;

    public static au mN() {
        if (adN == null) {
            adN = new au();
        }
        return adN;
    }

    public boolean mO() {
        return this.adP;
    }

    public void eB(int i) {
        this.adQ = i;
    }

    public int bV(String str) {
        this.adP = true;
        this.adO = (bc) new bc(str).cd(InAppPurchaseMetaData.IAP_KEY);
        return this.adO == null ? -2147483622 : 0;
    }
}
