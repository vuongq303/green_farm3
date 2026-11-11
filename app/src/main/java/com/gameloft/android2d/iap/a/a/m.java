package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class m implements ab {
    final /* synthetic */ a blU;

    m(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.ab
    public void a(ad adVar) {
        if (adVar.Sp()) {
            boolean unused = a.blO = true;
            a.blM.a(this.blU.blT);
        } else {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(adVar.So());
        }
    }
}
