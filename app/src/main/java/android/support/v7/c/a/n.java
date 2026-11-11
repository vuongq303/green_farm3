package android.support.v7.c.a;

import android.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: compiled from: ExpandedMenuView.java */
/* JADX INFO: loaded from: classes.dex */
public final class n extends ListView implements ak, u, AdapterView.OnItemClickListener {
    private static final int[] pY = {R.attr.background, R.attr.divider};
    private s nw;

    @Override // android.support.v7.c.a.ak
    public void h(s sVar) {
        this.nw = sVar;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.support.v7.c.a.u
    public boolean a(w wVar) {
        return this.nw.a(wVar, 0);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        a((w) getAdapter().getItem(i));
    }
}
