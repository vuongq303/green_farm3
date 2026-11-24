package com.quanhoang.greenfarm3;

import android.graphics.drawable.ColorDrawable;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
class bm implements Runnable {
    final /* synthetic */ bl bcL;

    bm(bl blVar) {
        this.bcL = blVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        SplashScreenActivity.bcA = new SplashScreenActivity(com.gameloft.android.wrapper.au.getContext(), R.style.NoBorderDialogTheme);
        SplashScreenActivity.bcA.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        SplashScreenActivity.bcA.show();
    }
}
