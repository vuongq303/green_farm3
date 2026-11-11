package android.support.v7.c.a;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.appcompat.R;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/* JADX INFO: compiled from: ListMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
public class p implements ai, AdapterView.OnItemClickListener {
    LayoutInflater kc;
    Context mContext;
    s nw;
    private aj pn;
    int pp;
    n qj;
    int qk;
    int ql;
    q qm;

    public p(Context context, int i) {
        this(i, 0);
        this.mContext = context;
        this.kc = LayoutInflater.from(this.mContext);
    }

    public p(int i, int i2) {
        this.pp = i;
        this.ql = i2;
    }

    @Override // android.support.v7.c.a.ai
    public void a(Context context, s sVar) {
        if (this.ql != 0) {
            this.mContext = new ContextThemeWrapper(context, this.ql);
            this.kc = LayoutInflater.from(this.mContext);
        } else if (this.mContext != null) {
            this.mContext = context;
            if (this.kc == null) {
                this.kc = LayoutInflater.from(this.mContext);
            }
        }
        this.nw = sVar;
        if (this.qm != null) {
            this.qm.notifyDataSetChanged();
        }
    }

    public ak g(ViewGroup viewGroup) {
        if (this.qj == null) {
            this.qj = (n) this.kc.inflate(R.layout.abc_expanded_menu_layout, viewGroup, false);
            if (this.qm == null) {
                this.qm = new q(this);
            }
            this.qj.setAdapter((ListAdapter) this.qm);
            this.qj.setOnItemClickListener(this);
        }
        return this.qj;
    }

    public ListAdapter getAdapter() {
        if (this.qm == null) {
            this.qm = new q(this);
        }
        return this.qm;
    }

    @Override // android.support.v7.c.a.ai
    public void p(boolean z) {
        if (this.qm != null) {
            this.qm.notifyDataSetChanged();
        }
    }

    @Override // android.support.v7.c.a.ai
    public void b(aj ajVar) {
        this.pn = ajVar;
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(as asVar) {
        if (!asVar.hasVisibleItems()) {
            return false;
        }
        new v(asVar).c((IBinder) null);
        if (this.pn != null) {
            this.pn.c(asVar);
        }
        return true;
    }

    @Override // android.support.v7.c.a.ai
    public void b(s sVar, boolean z) {
        if (this.pn != null) {
            this.pn.b(sVar, z);
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.nw.a(this.qm.getItem(i), this, 0);
    }

    @Override // android.support.v7.c.a.ai
    public boolean cM() {
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(s sVar, w wVar) {
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public boolean b(s sVar, w wVar) {
        return false;
    }
}
