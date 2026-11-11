package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: WindowDecorActionBar.java */
/* JADX INFO: loaded from: classes.dex */
class bi extends android.support.v4.view.aw {
    final /* synthetic */ bh nu;

    bi(bh bhVar) {
        this.nu = bhVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        if (this.nu.nj && this.nu.mZ != null) {
            this.nu.mZ.setTranslationY(0.0f);
            this.nu.mX.setTranslationY(0.0f);
        }
        this.nu.mX.setVisibility(8);
        this.nu.mX.C(false);
        this.nu.no = null;
        this.nu.cb();
        if (this.nu.mW != null) {
            android.support.v4.view.aa.k(this.nu.mW);
        }
    }
}
