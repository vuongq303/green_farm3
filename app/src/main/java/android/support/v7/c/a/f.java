package android.support.v7.c.a;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: BaseMenuWrapper.java */
/* JADX INFO: loaded from: classes.dex */
abstract class f<T> extends g<T> {
    final Context mContext;
    private Map<android.support.v4.b.a.b, MenuItem> pr;
    private Map<android.support.v4.b.a.c, SubMenu> ps;

    f(Context context, T t) {
        super(t);
        this.mContext = context;
    }

    final MenuItem b(MenuItem menuItem) {
        if (!(menuItem instanceof android.support.v4.b.a.b)) {
            return menuItem;
        }
        android.support.v4.b.a.b bVar = (android.support.v4.b.a.b) menuItem;
        if (this.pr == null) {
            this.pr = new android.support.v4.e.a();
        }
        MenuItem menuItem2 = this.pr.get(menuItem);
        if (menuItem2 == null) {
            MenuItem menuItemA = am.a(this.mContext, bVar);
            this.pr.put(bVar, menuItemA);
            return menuItemA;
        }
        return menuItem2;
    }

    final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof android.support.v4.b.a.c)) {
            return subMenu;
        }
        android.support.v4.b.a.c cVar = (android.support.v4.b.a.c) subMenu;
        if (this.ps == null) {
            this.ps = new android.support.v4.e.a();
        }
        SubMenu subMenu2 = this.ps.get(cVar);
        if (subMenu2 == null) {
            SubMenu subMenuA = am.a(this.mContext, cVar);
            this.ps.put(cVar, subMenuA);
            return subMenuA;
        }
        return subMenu2;
    }

    final void cN() {
        if (this.pr != null) {
            this.pr.clear();
        }
        if (this.ps != null) {
            this.ps.clear();
        }
    }

    final void Z(int i) {
        if (this.pr != null) {
            Iterator<android.support.v4.b.a.b> it = this.pr.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getGroupId()) {
                    it.remove();
                }
            }
        }
    }

    final void aa(int i) {
        if (this.pr != null) {
            Iterator<android.support.v4.b.a.b> it = this.pr.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getItemId()) {
                    it.remove();
                    return;
                }
            }
        }
    }
}
