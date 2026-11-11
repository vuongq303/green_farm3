package android.support.v7.app;

import android.support.v7.widget.bm;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class an implements bm {
    final /* synthetic */ aj lR;

    an(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // android.support.v7.widget.bm
    public void bV() {
    }

    @Override // android.support.v7.widget.bm
    public void onDetachedFromWindow() {
        this.lR.bU();
    }
}
