package android.support.v7.app;

import android.support.v4.widget.NestedScrollView;
import android.view.View;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class g implements android.support.v4.widget.r {
    final /* synthetic */ e jZ;
    final /* synthetic */ View ka;
    final /* synthetic */ View kb;

    g(e eVar, View view, View view2) {
        this.jZ = eVar;
        this.ka = view;
        this.kb = view2;
    }

    @Override // android.support.v4.widget.r
    public void a(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        e.a(nestedScrollView, this.ka, this.kb);
    }
}
