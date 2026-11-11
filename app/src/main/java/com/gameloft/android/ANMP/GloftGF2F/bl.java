package com.gameloft.android.ANMP.GloftGF2F;

import android.os.Environment;
import android.os.Looper;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class bl implements Runnable {
    final /* synthetic */ int bcK;

    bl(int i) {
        this.bcK = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        Looper.prepare();
        SplashScreenActivity.CJ = this.bcK;
        SplashScreenActivity.CA = true;
        if (Environment.getExternalStorageState().equals("mounted")) {
            str = com.gameloft.android.wrapper.au.getContext().getExternalFilesDir(null) + "/welcome";
        } else {
            str = com.gameloft.android.wrapper.au.getContext().getFilesDir() + "/welcome";
        }
        q qVar = new q(SplashScreenActivity.bcE, str, "welcome.html");
        qVar.gx();
        if (!qVar.gz()) {
            SplashScreenActivity.bcw = str + "/welcome.html";
            SplashScreenActivity.bcv = true;
        } else {
            SplashScreenActivity.bcv = false;
            SplashScreenActivity.bcw = "";
            SplashScreenActivity.CA = qVar.gy();
        }
        if (!SplashScreenActivity.bcx && SplashScreenActivity.CA) {
            com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new bm(this));
        }
    }
}
