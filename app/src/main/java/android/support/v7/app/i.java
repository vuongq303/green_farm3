package android.support.v7.app;

import android.view.View;
import android.widget.AbsListView;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class i implements AbsListView.OnScrollListener {
    final /* synthetic */ e jZ;
    final /* synthetic */ View ka;
    final /* synthetic */ View kb;

    i(e eVar, View view, View view2) {
        this.jZ = eVar;
        this.ka = view;
        this.kb = view2;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        e.a(absListView, this.ka, this.kb);
    }
}
