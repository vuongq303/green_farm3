package com.gameloft.android.ANMP.GloftGF2F;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: compiled from: Start.java */
/* JADX INFO: loaded from: classes.dex */
class bs implements View.OnApplyWindowInsetsListener {
    final /* synthetic */ Start bcX;

    bs(Start start) {
        this.bcX = start;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        this.bcX.bcS = new e(windowInsets.getDisplayCutout());
        return windowInsets;
    }
}
