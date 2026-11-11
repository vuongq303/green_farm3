package com.gameloft.android2d.iap;

import android.os.Looper;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    c() {
    }

    @Override // java.lang.Runnable
    public void run() {
        Looper.prepare();
        b.QU();
        Looper.loop();
    }
}
