package android.support.v7.c.a;

import android.content.Context;
import android.view.ActionProvider;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: compiled from: MenuItemWrapperICS.java */
/* JADX INFO: loaded from: classes.dex */
class z extends android.support.v4.view.g {
    final ActionProvider rg;
    final /* synthetic */ y rh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public z(y yVar, Context context, ActionProvider actionProvider) {
        super(context);
        this.rh = yVar;
        this.rg = actionProvider;
    }

    @Override // android.support.v4.view.g
    public View onCreateActionView() {
        return this.rg.onCreateActionView();
    }

    @Override // android.support.v4.view.g
    public boolean onPerformDefaultAction() {
        return this.rg.onPerformDefaultAction();
    }

    @Override // android.support.v4.view.g
    public boolean hasSubMenu() {
        return this.rg.hasSubMenu();
    }

    @Override // android.support.v4.view.g
    public void onPrepareSubMenu(SubMenu subMenu) {
        this.rg.onPrepareSubMenu(this.rh.a(subMenu));
    }
}
