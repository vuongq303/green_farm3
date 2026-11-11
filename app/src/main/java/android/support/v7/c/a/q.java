package android.support.v7.c.a;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: compiled from: ListMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class q extends BaseAdapter {
    private int qn = -1;
    final /* synthetic */ p qo;

    public q(p pVar) {
        this.qo = pVar;
        cV();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int size = this.qo.nw.dg().size() - this.qo.qk;
        return this.qn < 0 ? size : size - 1;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: ac, reason: merged with bridge method [inline-methods] */
    public w getItem(int i) {
        ArrayList<w> arrayListDg = this.qo.nw.dg();
        int i2 = this.qo.qk + i;
        if (this.qn >= 0 && i2 >= this.qn) {
            i2++;
        }
        return arrayListDg.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = view == null ? this.qo.kc.inflate(this.qo.pp, viewGroup, false) : view;
        ((al) viewInflate).a(getItem(i), 0);
        return viewInflate;
    }

    void cV() {
        w wVarDm = this.qo.nw.dm();
        if (wVarDm != null) {
            ArrayList<w> arrayListDg = this.qo.nw.dg();
            int size = arrayListDg.size();
            for (int i = 0; i < size; i++) {
                if (arrayListDg.get(i) == wVarDm) {
                    this.qn = i;
                    return;
                }
            }
        }
        this.qn = -1;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        cV();
        super.notifyDataSetChanged();
    }
}
