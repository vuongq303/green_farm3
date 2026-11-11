package android.support.v7.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/* JADX INFO: compiled from: ScrollingTabContainerView.java */
/* JADX INFO: loaded from: classes.dex */
class cs extends BaseAdapter {
    final /* synthetic */ cq xD;

    cs(cq cqVar) {
        this.xD = cqVar;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.xD.xv.getChildCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return ((cu) this.xD.xv.getChildAt(i)).eY();
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            return this.xD.a((android.support.v7.app.d) getItem(i), true);
        }
        ((cu) view).a((android.support.v7.app.d) getItem(i));
        return view;
    }
}
