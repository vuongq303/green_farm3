package com.gameloft.android.ANMP.GloftGF2F;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ak implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ InGameBrowserActivity Ex;
    final /* synthetic */ View Ey;

    ak(InGameBrowserActivity inGameBrowserActivity, View view) {
        this.Ex = inGameBrowserActivity;
        this.Ey = view;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        Rect rect = new Rect();
        this.Ey.getWindowVisibleDisplayFrame(rect);
        int i = rect.bottom - rect.top;
        int height = this.Ey.getHeight();
        boolean z = height == 0 || ((double) i) / ((double) height) > 0.8d;
        if (!z) {
            this.Ex.El = true;
            this.Ex.Ed.setVisibility(4);
        } else {
            this.Ex.El = false;
            this.Ex.Ed.setVisibility(0);
        }
    }
}
