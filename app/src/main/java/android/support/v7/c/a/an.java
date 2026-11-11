package android.support.v7.c.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

/* JADX INFO: compiled from: MenuWrapperICS.java */
/* JADX INFO: loaded from: classes.dex */
class an extends f<android.support.v4.b.a.a> implements Menu {
    an(Context context, android.support.v4.b.a.a aVar) {
        super(context, aVar);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return b(((android.support.v4.b.a.a) this.pt).add(charSequence));
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return b(((android.support.v4.b.a.a) this.pt).add(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return b(((android.support.v4.b.a.a) this.pt).add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return b(((android.support.v4.b.a.a) this.pt).add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return a(((android.support.v4.b.a.a) this.pt).addSubMenu(charSequence));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return a(((android.support.v4.b.a.a) this.pt).addSubMenu(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return a(((android.support.v4.b.a.a) this.pt).addSubMenu(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return a(((android.support.v4.b.a.a) this.pt).addSubMenu(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = null;
        if (menuItemArr != null) {
            menuItemArr2 = new MenuItem[menuItemArr.length];
        }
        int iAddIntentOptions = ((android.support.v4.b.a.a) this.pt).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = b(menuItemArr2[i5]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        aa(i);
        ((android.support.v4.b.a.a) this.pt).removeItem(i);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        Z(i);
        ((android.support.v4.b.a.a) this.pt).removeGroup(i);
    }

    @Override // android.view.Menu
    public void clear() {
        cN();
        ((android.support.v4.b.a.a) this.pt).clear();
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        ((android.support.v4.b.a.a) this.pt).setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        ((android.support.v4.b.a.a) this.pt).setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        ((android.support.v4.b.a.a) this.pt).setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        return ((android.support.v4.b.a.a) this.pt).hasVisibleItems();
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        return b(((android.support.v4.b.a.a) this.pt).findItem(i));
    }

    @Override // android.view.Menu
    public int size() {
        return ((android.support.v4.b.a.a) this.pt).size();
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return b(((android.support.v4.b.a.a) this.pt).getItem(i));
    }

    @Override // android.view.Menu
    public void close() {
        ((android.support.v4.b.a.a) this.pt).close();
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return ((android.support.v4.b.a.a) this.pt).performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return ((android.support.v4.b.a.a) this.pt).isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return ((android.support.v4.b.a.a) this.pt).performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        ((android.support.v4.b.a.a) this.pt).setQwertyMode(z);
    }
}
