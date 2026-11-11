package com.gameloft.android2d.iap.a.a;

import java.util.List;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class n implements ac {
    final /* synthetic */ a blU;

    n(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.ac
    public void a(ad adVar, ae aeVar) {
        if (adVar.Sq()) {
            if (adVar.So() == 9) {
                this.blU.RZ();
                return;
            } else {
                com.gameloft.android2d.iap.b.setResult(3);
                com.gameloft.android2d.iap.b.mB(adVar.So());
                return;
            }
        }
        List<String> listSr = aeVar.Sr();
        for (int i = 0; i < listSr.size(); i++) {
            String str = listSr.get(i);
            if (str != null && !str.equals("")) {
                af afVarA = this.blU.a(aeVar, str, 0);
                if (afVarA != null) {
                    com.gameloft.android2d.iap.b.eO(afVarA.Sx());
                }
                com.gameloft.android2d.iap.b.k(str, aeVar.fw(str).St(), aeVar.fw(str).getToken(), "");
            }
        }
        com.gameloft.android2d.iap.b.Rg();
    }
}
