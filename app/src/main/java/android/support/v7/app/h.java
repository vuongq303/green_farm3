package android.support.v7.app;

import android.view.View;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class h implements Runnable {
    final /* synthetic */ e jZ;
    final /* synthetic */ View ka;
    final /* synthetic */ View kb;

    h(e eVar, View view, View view2) {
        this.jZ = eVar;
        this.ka = view;
        this.kb = view2;
    }

    @Override // java.lang.Runnable
    public void run() {
        e.a(this.jZ.jH, this.ka, this.kb);
    }
}
