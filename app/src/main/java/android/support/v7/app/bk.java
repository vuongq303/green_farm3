package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: WindowDecorActionBar.java */
/* JADX INFO: loaded from: classes.dex */
class bk implements android.support.v4.view.ax {
    final /* synthetic */ bh nu;

    bk(bh bhVar) {
        this.nu = bhVar;
    }

    @Override // android.support.v4.view.ax
    public void u(View view) {
        ((View) this.nu.mX.getParent()).invalidate();
    }
}
