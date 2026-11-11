package android.support.v7.c.a;

import android.support.v7.appcompat.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: compiled from: MenuAdapter.java */
/* JADX INFO: loaded from: classes.dex */
public class r extends BaseAdapter {
    static final int qp = R.layout.abc_popup_menu_item_layout;
    private final LayoutInflater kc;
    private boolean pN;
    private final boolean px;
    private int qn = -1;
    s qq;

    public r(s sVar, LayoutInflater layoutInflater, boolean z) {
        this.px = z;
        this.kc = layoutInflater;
        this.qq = sVar;
        cV();
    }

    public void setForceShowIcon(boolean z) {
        this.pN = z;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<w> arrayListDg = this.px ? this.qq.dg() : this.qq.dd();
        if (this.qn < 0) {
            return arrayListDg.size();
        }
        return arrayListDg.size() - 1;
    }

    public s cW() {
        return this.qq;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: ac, reason: merged with bridge method [inline-methods] */
    public w getItem(int i) {
        ArrayList<w> arrayListDg = this.px ? this.qq.dg() : this.qq.dd();
        if (this.qn >= 0 && i >= this.qn) {
            i++;
        }
        return arrayListDg.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewInflate = view == null ? this.kc.inflate(qp, viewGroup, false) : view;
        al alVar = (al) viewInflate;
        if (this.pN) {
            ((o) viewInflate).setForceShowIcon(true);
        }
        alVar.a(getItem(i), 0);
        return viewInflate;
    }

    void cV() {
        w wVarDm = this.qq.dm();
        if (wVarDm != null) {
            ArrayList<w> arrayListDg = this.qq.dg();
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
