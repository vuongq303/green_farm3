package com.gameloft.android.wrapper;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

/* JADX INFO: compiled from: LowProfileListener.java */
/* JADX INFO: loaded from: classes.dex */
final class ac implements View.OnSystemUiVisibilityChangeListener {
    final /* synthetic */ Activity bfN;

    ac(Activity activity) {
        this.bfN = activity;
    }

    @Override // android.view.View.OnSystemUiVisibilityChangeListener
    public void onSystemUiVisibilityChange(int i) {
        if (i == 0) {
            new Handler().postDelayed(new ad(this), 3000L);
        }
    }
}
