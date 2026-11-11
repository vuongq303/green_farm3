package com.gameloft.android2d.iap.utils;

/* JADX INFO: compiled from: IABAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ Integer Fd;
    final /* synthetic */ j bnB;

    k(j jVar, Integer num) {
        this.bnB = jVar;
        this.Fd = num;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.bnB.Fc) {
                this.bnB.onCancelled();
            } else {
                this.bnB.a(this.Fd);
            }
        } catch (Exception e) {
        }
    }
}
