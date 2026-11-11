package android.support.v7.app;

import android.view.Menu;
import android.view.MenuItem;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class as implements android.support.v7.c.c {
    final /* synthetic */ aj lR;
    private android.support.v7.c.c lT;

    public as(aj ajVar, android.support.v7.c.c cVar) {
        this.lR = ajVar;
        this.lT = cVar;
    }

    @Override // android.support.v7.c.c
    public boolean a(android.support.v7.c.b bVar, Menu menu) {
        return this.lT.a(bVar, menu);
    }

    @Override // android.support.v7.c.c
    public boolean b(android.support.v7.c.b bVar, Menu menu) {
        return this.lT.b(bVar, menu);
    }

    @Override // android.support.v7.c.c
    public boolean a(android.support.v7.c.b bVar, MenuItem menuItem) {
        return this.lT.a(bVar, menuItem);
    }

    @Override // android.support.v7.c.c
    public void c(android.support.v7.c.b bVar) {
        this.lT.c(bVar);
        if (this.lR.ly != null) {
            this.lR.jo.getDecorView().removeCallbacks(this.lR.lz);
        }
        if (this.lR.lx != null) {
            this.lR.bR();
            this.lR.lA = android.support.v4.view.aa.h(this.lR.lx).a(0.0f);
            this.lR.lA.a(new at(this));
        }
        if (this.lR.kT != null) {
            this.lR.kT.b(this.lR.lw);
        }
        this.lR.lw = null;
    }
}
