package android.support.v7.c.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* JADX INFO: compiled from: BaseMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class e implements ai {
    protected LayoutInflater kc;
    protected Context mContext;
    private int mId;
    protected s nw;
    protected Context pl;
    protected LayoutInflater pm;
    private aj pn;
    private int po;
    private int pp;
    protected ak pq;

    public abstract void a(w wVar, al alVar);

    public e(Context context, int i, int i2) {
        this.pl = context;
        this.pm = LayoutInflater.from(context);
        this.po = i;
        this.pp = i2;
    }

    @Override // android.support.v7.c.a.ai
    public void a(Context context, s sVar) {
        this.mContext = context;
        this.kc = LayoutInflater.from(this.mContext);
        this.nw = sVar;
    }

    public ak g(ViewGroup viewGroup) {
        if (this.pq == null) {
            this.pq = (ak) this.pm.inflate(this.po, viewGroup, false);
            this.pq.h(this.nw);
            p(true);
        }
        return this.pq;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.c.a.ai
    public void p(boolean z) {
        int i;
        int i2;
        ViewGroup viewGroup = (ViewGroup) this.pq;
        if (viewGroup != null) {
            if (this.nw != null) {
                this.nw.de();
                ArrayList<w> arrayListDd = this.nw.dd();
                int size = arrayListDd.size();
                int i3 = 0;
                i = 0;
                while (i3 < size) {
                    w wVar = arrayListDd.get(i3);
                    if (a(i, wVar)) {
                        View childAt = viewGroup.getChildAt(i);
                        w wVarCE = childAt instanceof al ? ((al) childAt).cE() : null;
                        View viewA = a(wVar, childAt, viewGroup);
                        if (wVar != wVarCE) {
                            viewA.setPressed(false);
                            viewA.jumpDrawablesToCurrentState();
                        }
                        if (viewA != childAt) {
                            b(viewA, i);
                        }
                        i2 = i + 1;
                    } else {
                        i2 = i;
                    }
                    i3++;
                    i = i2;
                }
            } else {
                i = 0;
            }
            while (i < viewGroup.getChildCount()) {
                if (!a(viewGroup, i)) {
                    i++;
                }
            }
        }
    }

    protected void b(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup) this.pq).addView(view, i);
    }

    protected boolean a(ViewGroup viewGroup, int i) {
        viewGroup.removeViewAt(i);
        return true;
    }

    @Override // android.support.v7.c.a.ai
    public void b(aj ajVar) {
        this.pn = ajVar;
    }

    public aj cL() {
        return this.pn;
    }

    public al h(ViewGroup viewGroup) {
        return (al) this.pm.inflate(this.pp, viewGroup, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View a(w wVar, View view, ViewGroup viewGroup) {
        al alVarH;
        if (view instanceof al) {
            alVarH = (al) view;
        } else {
            alVarH = h(viewGroup);
        }
        a(wVar, alVarH);
        return (View) alVarH;
    }

    public boolean a(int i, w wVar) {
        return true;
    }

    @Override // android.support.v7.c.a.ai
    public void b(s sVar, boolean z) {
        if (this.pn != null) {
            this.pn.b(sVar, z);
        }
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(as asVar) {
        if (this.pn != null) {
            return this.pn.c(asVar);
        }
        return false;
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

    public void setId(int i) {
        this.mId = i;
    }
}
