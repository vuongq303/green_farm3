package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class e implements aa {
    final /* synthetic */ a blU;

    e(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.aa
    public void a(ad adVar, af afVar, int i) {
        if (i != 7) {
            this.blU.o(this.blU.blN, false);
            this.blU.a(this.blU.blN, String.valueOf(com.gameloft.android2d.iap.utils.u.Tq()), com.gameloft.android2d.iap.utils.u.Tr());
        }
        if (!adVar.Sq()) {
            if (!this.blU.fq(this.blU.blN)) {
                a.blM.a(afVar, this.blU.blR);
                return;
            } else {
                this.blU.a(afVar, true);
                return;
            }
        }
        com.gameloft.android2d.iap.b.setResult(3);
        com.gameloft.android2d.iap.b.mB(adVar.So());
    }
}
