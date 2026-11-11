package com.gameloft.adsmanager;

/* JADX INFO: compiled from: JavaUtils.java */
/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ Runnable Aa;
    final /* synthetic */ Runnable Ab;

    c(Runnable runnable, Runnable runnable2) {
        this.Aa = runnable;
        this.Ab = runnable2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.Aa.run();
        } catch (Exception e) {
            JavaUtils.LogException("PostAndLogExcepions", "PostAndLogExcepions", e);
            if (this.Ab != null) {
                this.Ab.run();
            }
        }
    }
}
