package com.a.a.a.a.c;

import android.content.Context;
import android.os.Handler;
import com.a.a.a.a.b.i;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class h implements com.a.a.a.a.a.c, d {
    private static h cew;
    private com.a.a.a.a.a.d ceA;
    private a ceB;
    private float cex = 0.0f;
    private final com.a.a.a.a.a.e cey;
    private final com.a.a.a.a.a.b cez;

    public h(com.a.a.a.a.a.e eVar, com.a.a.a.a.a.b bVar) {
        this.cey = eVar;
        this.cez = bVar;
    }

    public static h aib() {
        if (cew == null) {
            cew = new h(new com.a.a.a.a.a.e(), new com.a.a.a.a.a.b());
        }
        return cew;
    }

    private a aic() {
        if (this.ceB == null) {
            this.ceB = a.ahU();
        }
        return this.ceB;
    }

    public void a(Context context) {
        this.ceA = this.cey.a(new Handler(), context, this.cez.ahB(), this);
    }

    public float aid() {
        return this.cex;
    }

    public void b() {
        b.ahX().a(this);
        b.ahX().b();
        if (b.ahX().ahT()) {
            com.a.a.a.a.h.a.aik().a();
        }
        this.ceA.a();
    }

    public void c() {
        com.a.a.a.a.h.a.aik().b();
        b.ahX().c();
        this.ceA.b();
    }

    @Override // com.a.a.a.a.c.d
    public void ch(boolean z) {
        if (z) {
            com.a.a.a.a.h.a.aik().a();
        } else {
            com.a.a.a.a.h.a.aik().c();
        }
    }

    @Override // com.a.a.a.a.a.c
    public void k(float f) {
        this.cex = f;
        Iterator<i> it = aic().ahW().iterator();
        while (it.hasNext()) {
            it.next().ahQ().k(f);
        }
    }
}
