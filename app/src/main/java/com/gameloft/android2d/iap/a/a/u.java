package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class u implements Runnable {
    final /* synthetic */ ad bmr;
    final /* synthetic */ ae bms;
    final /* synthetic */ t bmt;

    u(t tVar, ad adVar, ae aeVar) {
        this.bmt = tVar;
        this.bmr = adVar;
        this.bms = aeVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bmt.bmq.a(this.bmr, this.bms);
    }
}
