package android.support.v7.c.a;

import android.view.MenuItem;

/* JADX INFO: compiled from: MenuItemWrapperICS.java */
/* JADX INFO: loaded from: classes.dex */
class ac extends g<MenuItem.OnMenuItemClickListener> implements MenuItem.OnMenuItemClickListener {
    final /* synthetic */ y rh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ac(y yVar, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        super(onMenuItemClickListener);
        this.rh = yVar;
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        return ((MenuItem.OnMenuItemClickListener) this.pt).onMenuItemClick(this.rh.b(menuItem));
    }
}
