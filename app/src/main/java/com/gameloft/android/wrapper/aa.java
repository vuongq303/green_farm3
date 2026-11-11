package com.gameloft.android.wrapper;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;

/* JADX INFO: compiled from: LowProfileListener.java */
/* JADX INFO: loaded from: classes.dex */
final class aa implements Runnable {
    final /* synthetic */ Activity bfL;

    aa(Activity activity) {
        this.bfL = activity;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!d.bfk) {
            if (Build.VERSION.SDK_INT >= 19) {
                z.j(this.bfL);
                z.l(this.bfL);
                new Handler().postDelayed(new ab(this), 3000L);
                return;
            }
            this.bfL.getWindow().getDecorView().setSystemUiVisibility(0);
            this.bfL.getWindow().getDecorView().setSystemUiVisibility(1);
            z.l(this.bfL);
            return;
        }
        this.bfL.getWindow().getDecorView().setSystemUiVisibility(0);
        this.bfL.getWindow().getDecorView().setSystemUiVisibility(1);
        z.l(this.bfL);
    }
}
