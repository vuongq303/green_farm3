package android.support.v7.c.a;

import android.view.MenuItem;

/* JADX INFO: compiled from: MenuItemWrapperICS.java */
/* JADX INFO: loaded from: classes.dex */
class ab extends g<MenuItem.OnActionExpandListener> implements MenuItem.OnActionExpandListener {
    final /* synthetic */ y rh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ab(y yVar, MenuItem.OnActionExpandListener onActionExpandListener) {
        super(onActionExpandListener);
        this.rh = yVar;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener) this.pt).onMenuItemActionExpand(this.rh.b(menuItem));
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener) this.pt).onMenuItemActionCollapse(this.rh.b(menuItem));
    }
}
