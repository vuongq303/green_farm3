package com.gameloft.android2d.iap;

import android.os.Looper;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class h implements Runnable {
    h() {
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
