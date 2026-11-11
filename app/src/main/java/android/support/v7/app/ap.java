package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class ap extends android.support.v4.view.aw {
    final /* synthetic */ ao lS;

    ap(ao aoVar) {
        this.lS = aoVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void r(View view) {
        this.lS.lR.lx.setVisibility(0);
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        this.lS.lR.lx.setAlpha(1.0f);
        this.lS.lR.lA.a((android.support.v4.view.av) null);
        this.lS.lR.lA = null;
    }
}
