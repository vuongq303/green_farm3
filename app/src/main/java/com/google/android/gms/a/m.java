package com.google.android.gms.a;

import com.google.android.gms.a.m;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class m<T extends m> {
    private final n bEB;
    protected final j bEC;
    private final List<k> bED;

    protected m(n nVar, com.google.android.gms.common.d.e eVar) {
        bf.checkNotNull(nVar);
        this.bEB = nVar;
        this.bED = new ArrayList();
        j jVar = new j(this, eVar);
        jVar.zzx();
        this.bEC = jVar;
    }

    public j Xn() {
        j jVarXp = this.bEC.Xp();
        d(jVarXp);
        return jVarXp;
    }

    protected final n Xw() {
        return this.bEB;
    }

    protected void a(j jVar) {
    }

    protected final void d(j jVar) {
        Iterator<k> it = this.bED.iterator();
        while (it.hasNext()) {
            it.next().a(this, jVar);
        }
    }
}
