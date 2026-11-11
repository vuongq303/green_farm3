package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ h blV;

    i(h hVar) {
        this.blV = hVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(5000L);
        } catch (Exception e) {
        }
        this.blV.blU.Rc();
    }
}
