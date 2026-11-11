package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class aq extends android.support.v4.view.aw {
    final /* synthetic */ aj lR;

    aq(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void r(View view) {
        this.lR.lx.setVisibility(0);
        this.lR.lx.sendAccessibilityEvent(32);
        if (this.lR.lx.getParent() instanceof View) {
            android.support.v4.view.aa.k((View) this.lR.lx.getParent());
        }
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        this.lR.lx.setAlpha(1.0f);
        this.lR.lA.a((android.support.v4.view.av) null);
        this.lR.lA = null;
    }
}
