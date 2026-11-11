package android.support.v7.c.a;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: compiled from: MenuItemWrapperJB.java */
/* JADX INFO: loaded from: classes.dex */
class ae extends z implements ActionProvider.VisibilityListener {
    android.support.v4.view.i rj;
    final /* synthetic */ ad rk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ae(ad adVar, Context context, ActionProvider actionProvider) {
        super(adVar, context, actionProvider);
        this.rk = adVar;
    }

    @Override // android.support.v4.view.g
    public View onCreateActionView(MenuItem menuItem) {
        return this.rg.onCreateActionView(menuItem);
    }

    @Override // android.support.v4.view.g
    public boolean overridesItemVisibility() {
        return this.rg.overridesItemVisibility();
    }

    @Override // android.support.v4.view.g
    public boolean isVisible() {
        return this.rg.isVisible();
    }

    @Override // android.support.v4.view.g
    public void a(android.support.v4.view.i iVar) {
        this.rj = iVar;
        ActionProvider actionProvider = this.rg;
        if (iVar == null) {
            this = null;
        }
        actionProvider.setVisibilityListener(this);
    }

    @Override // android.view.ActionProvider.VisibilityListener
    public void onActionProviderVisibilityChanged(boolean z) {
        if (this.rj != null) {
            this.rj.onActionProviderVisibilityChanged(z);
        }
    }
}
