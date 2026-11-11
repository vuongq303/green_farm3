package android.support.v7.c.a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: compiled from: SubMenuBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public class as extends s implements SubMenu {
    private s rw;
    private w rx;

    public as(Context context, s sVar, w wVar) {
        super(context);
        this.rw = sVar;
        this.rx = wVar;
    }

    @Override // android.support.v7.c.a.s, android.view.Menu
    public void setQwertyMode(boolean z) {
        this.rw.setQwertyMode(z);
    }

    @Override // android.support.v7.c.a.s
    public boolean cY() {
        return this.rw.cY();
    }

    @Override // android.support.v7.c.a.s
    public boolean cZ() {
        return this.rw.cZ();
    }

    public Menu dC() {
        return this.rw;
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return this.rx;
    }

    @Override // android.support.v7.c.a.s
    public void a(t tVar) {
        this.rw.a(tVar);
    }

    @Override // android.support.v7.c.a.s
    public s dk() {
        return this.rw.dk();
    }

    @Override // android.support.v7.c.a.s
    boolean d(s sVar, MenuItem menuItem) {
        return super.d(sVar, menuItem) || this.rw.d(sVar, menuItem);
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        this.rx.setIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        this.rx.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        return (SubMenu) super.g(drawable);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        return (SubMenu) super.ai(i);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        return (SubMenu) super.k(charSequence);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        return (SubMenu) super.ah(i);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        return (SubMenu) super.C(view);
    }

    @Override // android.support.v7.c.a.s
    public boolean d(w wVar) {
        return this.rw.d(wVar);
    }

    @Override // android.support.v7.c.a.s
    public boolean e(w wVar) {
        return this.rw.e(wVar);
    }

    @Override // android.support.v7.c.a.s
    public String cX() {
        int itemId = this.rx != null ? this.rx.getItemId() : 0;
        if (itemId == 0) {
            return null;
        }
        return super.cX() + ":" + itemId;
    }
}
