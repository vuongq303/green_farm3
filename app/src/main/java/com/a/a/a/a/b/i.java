package com.a.a.a.a.b;

import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class i extends b {
    private final d ced;
    private final c cee;
    private com.a.a.a.a.f.a ceg;
    private com.a.a.a.a.g.a ceh;
    private boolean cej;
    private final List<com.a.a.a.a.f.a> cef = new ArrayList();
    private boolean f = false;
    private boolean g = false;
    private String cei = UUID.randomUUID().toString();

    i(c cVar, d dVar) {
        this.cee = cVar;
        this.ced = dVar;
        S(null);
        if (dVar.ahK() == e.HTML) {
            this.ceh = new com.a.a.a.a.g.c(dVar.getWebView());
        } else {
            this.ceh = new com.a.a.a.a.g.d(dVar.ahH(), dVar.ahJ());
        }
        this.ceh.a();
        com.a.a.a.a.c.a.ahU().a(this);
        this.ceh.a(cVar);
    }

    private void S(View view) {
        this.ceg = new com.a.a.a.a.f.a(view);
    }

    private void T(View view) {
        Collection<i> collectionAhV = com.a.a.a.a.c.a.ahU().ahV();
        if (collectionAhV == null || collectionAhV.size() <= 0) {
            return;
        }
        for (i iVar : collectionAhV) {
            if (iVar != this && iVar.ahS() == view) {
                iVar.ceg.clear();
            }
        }
    }

    private void i() {
        if (this.cej) {
            throw new IllegalStateException("Impression event can only be sent once");
        }
    }

    @Override // com.a.a.a.a.b.b
    public void R(View view) {
        if (this.g) {
            return;
        }
        com.a.a.a.a.e.f.c(view, "AdView is null");
        if (ahS() != view) {
            S(view);
            ahQ().h();
            T(view);
        }
    }

    public void ahO() {
        if (this.g) {
            return;
        }
        this.cef.clear();
    }

    public List<com.a.a.a.a.f.a> ahP() {
        return this.cef;
    }

    public com.a.a.a.a.g.a ahQ() {
        return this.ceh;
    }

    public String ahR() {
        return this.cei;
    }

    public View ahS() {
        return (View) this.ceg.get();
    }

    public boolean ahT() {
        return this.f && !this.g;
    }

    void b() {
        i();
        ahQ().g();
        this.cej = true;
    }

    public boolean f() {
        return this.g;
    }

    @Override // com.a.a.a.a.b.b
    public void finish() {
        if (this.g) {
            return;
        }
        this.ceg.clear();
        ahO();
        this.g = true;
        ahQ().f();
        com.a.a.a.a.c.a.ahU().c(this);
        ahQ().b();
        this.ceh = null;
    }

    public boolean g() {
        return this.cee.ahE();
    }

    @Override // com.a.a.a.a.b.b
    public void start() {
        if (this.f) {
            return;
        }
        this.f = true;
        com.a.a.a.a.c.a.ahU().b(this);
        this.ceh.k(com.a.a.a.a.c.h.aib().aid());
        this.ceh.a(this, this.ced);
    }
}
