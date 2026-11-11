package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: WindowDecorActionBar.java */
/* JADX INFO: loaded from: classes.dex */
class bj extends android.support.v4.view.aw {
    final /* synthetic */ bh nu;

    bj(bh bhVar) {
        this.nu = bhVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        this.nu.no = null;
        this.nu.mX.requestLayout();
    }
}
