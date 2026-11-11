package android.support.v7.widget;

import android.content.Context;

/* JADX INFO: compiled from: Toolbar.java */
/* JADX INFO: loaded from: classes.dex */
class dc implements android.support.v7.c.a.ai {
    android.support.v7.c.a.s nw;
    final /* synthetic */ Toolbar yy;
    android.support.v7.c.a.w yz;

    dc(Toolbar toolbar) {
        this.yy = toolbar;
    }

    @Override // android.support.v7.c.a.ai
    public void a(Context context, android.support.v7.c.a.s sVar) {
        if (this.nw != null && this.yz != null) {
            this.nw.e(this.yz);
        }
        this.nw = sVar;
    }

    @Override // android.support.v7.c.a.ai
    public void p(boolean z) {
        boolean z2 = false;
        if (this.yz != null) {
            if (this.nw != null) {
                int size = this.nw.size();
                for (int i = 0; i < size; i++) {
                    if (this.nw.getItem(i) == this.yz) {
                        z2 = true;
                        break;
                    }
                }
            }
            if (!z2) {
                b(this.nw, this.yz);
            }
        }
    }

    @Override // android.support.v7.c.a.ai
    public void b(android.support.v7.c.a.aj ajVar) {
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(android.support.v7.c.a.as asVar) {
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public void b(android.support.v7.c.a.s sVar, boolean z) {
    }

    @Override // android.support.v7.c.a.ai
    public boolean cM() {
        return false;
    }

    @Override // android.support.v7.c.a.ai
    public boolean a(android.support.v7.c.a.s sVar, android.support.v7.c.a.w wVar) {
        this.yy.fd();
        if (this.yy.xY.getParent() != this.yy) {
            this.yy.addView(this.yy.xY);
        }
        this.yy.xZ = wVar.getActionView();
        this.yz = wVar;
        if (this.yy.xZ.getParent() != this.yy) {
            dd ddVarFg = this.yy.generateDefaultLayoutParams();
            ddVarFg.gravity = 8388611 | (this.yy.yc & 112);
            ddVarFg.yA = 2;
            this.yy.xZ.setLayoutParams(ddVarFg);
            this.yy.addView(this.yy.xZ);
        }
        this.yy.fi();
        this.yy.requestLayout();
        wVar.B(true);
        if (this.yy.xZ instanceof android.support.v7.c.d) {
            ((android.support.v7.c.d) this.yy.xZ).onActionViewExpanded();
        }
        return true;
    }

    @Override // android.support.v7.c.a.ai
    public boolean b(android.support.v7.c.a.s sVar, android.support.v7.c.a.w wVar) {
        if (this.yy.xZ instanceof android.support.v7.c.d) {
            ((android.support.v7.c.d) this.yy.xZ).onActionViewCollapsed();
        }
        this.yy.removeView(this.yy.xZ);
        this.yy.removeView(this.yy.xY);
        this.yy.xZ = null;
        this.yy.fj();
        this.yz = null;
        this.yy.requestLayout();
        wVar.B(false);
        return true;
    }
}
