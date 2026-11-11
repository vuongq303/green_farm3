package com.gameloft.android2d.iap.a.a;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class l implements Runnable {
    final /* synthetic */ j blX;

    l(j jVar) {
        this.blX = jVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(5000L);
        } catch (Exception e) {
        }
        this.blX.blU.RU();
    }
}
