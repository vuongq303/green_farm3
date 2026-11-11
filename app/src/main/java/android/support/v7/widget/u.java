package android.support.v7.widget;

import android.view.MenuItem;

/* JADX INFO: compiled from: ActionMenuView.java */
/* JADX INFO: loaded from: classes.dex */
class u implements android.support.v7.c.a.t {
    final /* synthetic */ q tp;

    u(q qVar) {
        this.tp = qVar;
    }

    @Override // android.support.v7.c.a.t
    public boolean a(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        return this.tp.th != null && this.tp.th.onMenuItemClick(menuItem);
    }

    @Override // android.support.v7.c.a.t
    public void a(android.support.v7.c.a.s sVar) {
        if (this.tp.td != null) {
            this.tp.td.a(sVar);
        }
    }
}
