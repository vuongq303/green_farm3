package com.gameloft.android2d.iap.a.a;

import android.app.Activity;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class d implements ac {
    final /* synthetic */ a blU;

    d(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.ac
    public void a(ad adVar, ae aeVar) {
        if (!adVar.Sq()) {
            af afVarA = this.blU.a(aeVar, this.blU.blN, 0);
            if (afVarA == null) {
                afVarA = this.blU.a(aeVar, "", 1);
            }
            if (afVarA != null) {
                if (!this.blU.fq(afVarA.Su())) {
                    a.blM.a(afVarA, this.blU.blR);
                    return;
                } else {
                    this.blU.a(afVarA, true);
                    return;
                }
            }
            if (!com.gameloft.android2d.iap.b.bkg) {
                String strAm = com.gameloft.android2d.iap.utils.aa.am(this.blU.blN + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgh), com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgh));
                if (a.blM != null) {
                    a.blM.Sn();
                }
                a.blM.a((Activity) com.gameloft.android2d.iap.utils.v.getContext(), this.blU.blN, 10001, this.blU.blQ, strAm);
                return;
            }
            com.gameloft.android2d.iap.b.setResult(10);
            com.gameloft.android2d.iap.b.mB(0);
            return;
        }
        if (adVar.So() == 9) {
            boolean unused = a.blO = false;
            this.blU.Sf();
        } else {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(adVar.So());
        }
    }
}
