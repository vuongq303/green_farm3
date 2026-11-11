package com.gameloft.android.ANMP.GloftGF2F;

import android.os.Build;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class s implements Runnable {
    s() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (Build.VERSION.SDK_INT == 28) {
            IGPFreemiumActivity.De = new IGPFreemiumActivity(com.gameloft.android.wrapper.au.getActivity(), R.style.NoFullscreenDialogTheme);
        } else {
            IGPFreemiumActivity.De = new IGPFreemiumActivity(com.gameloft.android.wrapper.au.getActivity(), R.style.NoBorderDialogTheme);
        }
        IGPFreemiumActivity.De.show();
    }
}
