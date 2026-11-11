package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class g implements y {
    final /* synthetic */ a blU;

    g(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.y
    public void a(af afVar, ad adVar) {
        if (adVar.Sp()) {
            this.blU.a(afVar);
        } else {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(adVar.So());
        }
    }
}
