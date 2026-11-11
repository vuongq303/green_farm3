package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class c implements ab {
    final /* synthetic */ a blU;

    c(a aVar) {
        this.blU = aVar;
    }

    @Override // com.gameloft.android2d.iap.a.a.ab
    public void a(ad adVar) {
        if (adVar.Sp()) {
            if (a.blM != null) {
                a.blM.a(this.blU.blP);
                com.gameloft.android2d.iap.b.setResult(10);
                com.gameloft.android2d.iap.b.mB(0);
                return;
            } else {
                com.gameloft.android2d.iap.b.setResult(10);
                com.gameloft.android2d.iap.b.mB(0);
                return;
            }
        }
        com.gameloft.android2d.iap.b.setResult(10);
        com.gameloft.android2d.iap.b.mB(0);
    }
}
