package com.gameloft.android.wrapper;

import android.os.Build;

/* JADX INFO: compiled from: LowProfileListener.java */
/* JADX INFO: loaded from: classes.dex */
class ad implements Runnable {
    final /* synthetic */ ac bfO;

    ad(ac acVar) {
        this.bfO = acVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (Build.VERSION.SDK_INT >= 19) {
            z.j(this.bfO.bfN);
        } else {
            this.bfO.bfN.getWindow().getDecorView().setSystemUiVisibility(0);
            this.bfO.bfN.getWindow().getDecorView().setSystemUiVisibility(1);
        }
    }
}
