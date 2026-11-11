package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class at extends android.support.v4.view.aw {
    final /* synthetic */ as lU;

    at(as asVar) {
        this.lU = asVar;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        this.lU.lR.lx.setVisibility(8);
        if (this.lU.lR.ly != null) {
            this.lU.lR.ly.dismiss();
        } else if (this.lU.lR.lx.getParent() instanceof View) {
            android.support.v4.view.aa.k((View) this.lU.lR.lx.getParent());
        }
        this.lU.lR.lx.removeAllViews();
        this.lU.lR.lA.a((android.support.v4.view.av) null);
        this.lU.lR.lA = null;
    }
}
