package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ToolbarWidgetWrapper.java */
/* JADX INFO: loaded from: classes.dex */
class dh extends android.support.v4.view.aw {
    private boolean rG = false;
    final /* synthetic */ df yP;
    final /* synthetic */ int yQ;

    dh(df dfVar, int i) {
        this.yP = dfVar;
        this.yQ = i;
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void r(View view) {
        this.yP.yD.setVisibility(0);
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void s(View view) {
        if (!this.rG) {
            this.yP.yD.setVisibility(this.yQ);
        }
    }

    @Override // android.support.v4.view.aw, android.support.v4.view.av
    public void t(View view) {
        this.rG = true;
    }
}
