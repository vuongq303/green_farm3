package com.gameloft.android2d.iap;

import android.os.Looper;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class ad implements Runnable {
    final /* synthetic */ ac blg;

    ad(ac acVar) {
        this.blg = acVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Looper.prepare();
        try {
            com.gameloft.android2d.iap.a.b.fo(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgl)).Sb();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Looper.loop();
    }
}
