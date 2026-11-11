package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
class br implements Runnable {
    final /* synthetic */ String bcP;
    final /* synthetic */ bq bcQ;

    br(bq bqVar, String str) {
        this.bcQ = bqVar;
        this.bcP = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        String strDP = this.bcQ.bcJ.dP(this.bcP);
        if (strDP == null) {
            strDP = this.bcP;
        }
        this.bcQ.bd(strDP.replace("link:", ""));
    }
}
