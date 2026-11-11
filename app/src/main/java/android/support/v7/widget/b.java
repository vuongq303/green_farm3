package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: AbsActionBarView.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements android.support.v4.view.av {
    private boolean rG = false;
    int rH;
    final /* synthetic */ a rI;

    protected b(a aVar) {
        this.rI = aVar;
    }

    public b a(android.support.v4.view.ar arVar, int i) {
        this.rI.rD = arVar;
        this.rH = i;
        return this;
    }

    @Override // android.support.v4.view.av
    public void r(View view) {
        super/*android.view.ViewGroup*/.setVisibility(0);
        this.rG = false;
    }

    @Override // android.support.v4.view.av
    public void s(View view) {
        if (!this.rG) {
            this.rI.rD = null;
            super/*android.view.ViewGroup*/.setVisibility(this.rH);
        }
    }

    @Override // android.support.v4.view.av
    public void t(View view) {
        this.rG = true;
    }
}
