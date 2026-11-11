package android.support.v7.c;

import android.content.Context;
import android.support.v4.e.r;
import android.support.v7.c.a.am;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

/* JADX INFO: compiled from: SupportActionModeWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class h implements c {
    final Context mContext;
    final ActionMode.Callback nM;
    final ArrayList<g> nN = new ArrayList<>();
    final r<Menu, Menu> nO = new r<>();

    public h(Context context, ActionMode.Callback callback) {
        this.mContext = context;
        this.nM = callback;
    }

    @Override // android.support.v7.c.c
    public boolean a(b bVar, Menu menu) {
        return this.nM.onCreateActionMode(d(bVar), b(menu));
    }

    @Override // android.support.v7.c.c
    public boolean b(b bVar, Menu menu) {
        return this.nM.onPrepareActionMode(d(bVar), b(menu));
    }

    @Override // android.support.v7.c.c
    public boolean a(b bVar, MenuItem menuItem) {
        return this.nM.onActionItemClicked(d(bVar), am.a(this.mContext, (android.support.v4.b.a.b) menuItem));
    }

    @Override // android.support.v7.c.c
    public void c(b bVar) {
        this.nM.onDestroyActionMode(d(bVar));
    }

    private Menu b(Menu menu) {
        Menu menu2 = this.nO.get(menu);
        if (menu2 == null) {
            Menu menuA = am.a(this.mContext, (android.support.v4.b.a.a) menu);
            this.nO.put(menu, menuA);
            return menuA;
        }
        return menu2;
    }

    public ActionMode d(b bVar) {
        int size = this.nN.size();
        for (int i = 0; i < size; i++) {
            g gVar = this.nN.get(i);
            if (gVar != null && gVar.nL == bVar) {
                return gVar;
            }
        }
        g gVar2 = new g(this.mContext, bVar);
        this.nN.add(gVar2);
        return gVar2;
    }
}
