package com.google.android.gms.a;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class r implements Runnable {
    private final /* synthetic */ n bEJ;
    private final /* synthetic */ j bEL;

    r(n nVar, j jVar) {
        this.bEJ = nVar;
        this.bEL = jVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bEL.Xv().a(this.bEL);
        Iterator it = this.bEJ.bEF.iterator();
        while (it.hasNext()) {
            ((t) it.next()).a(this.bEL);
        }
        n nVar = this.bEJ;
        n.b(this.bEL);
    }
}
