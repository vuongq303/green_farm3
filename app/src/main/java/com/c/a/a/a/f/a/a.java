package com.c.a.a.a.f.a;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

/* JADX INFO: compiled from: InternalAvidAdSession.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a<T extends View> implements com.c.a.a.a.f.a.a.b {
    private final c cjB;
    private com.c.a.a.a.f.a.a.f cjC;
    private com.c.a.a.a.i.b<T> cjD;
    private com.c.a.a.a.c.a cjE;
    private d cjF;
    private boolean cjG;
    private boolean cjH;
    private final i cjI;
    private b cjJ;
    private double cjK;
    private com.c.a.a.a.f.a.a.a cjo;

    public abstract j ajd();

    public abstract h aje();

    public abstract WebView getWebView();

    public a(Context context, String str, com.c.a.a.a.f.e eVar) {
        this.cjB = new c(context, str, ajd().toString(), aje().toString(), eVar);
        this.cjo = new com.c.a.a.a.f.a.a.a(this.cjB);
        this.cjo.a(this);
        this.cjC = new com.c.a.a.a.f.a.a.f(this.cjB, this.cjo);
        this.cjD = new com.c.a.a.a.i.b<>(null);
        this.cjG = !eVar.ajc();
        if (!this.cjG) {
            this.cjE = new com.c.a.a.a.c.a(this, this.cjo);
        }
        this.cjI = new i();
        ajm();
    }

    public String aiZ() {
        return this.cjB.aiZ();
    }

    public T getView() {
        return this.cjD.get();
    }

    public void a(d dVar) {
        this.cjF = dVar;
    }

    public boolean isEmpty() {
        return this.cjD.isEmpty();
    }

    public boolean isActive() {
        return this.cjH;
    }

    public com.c.a.a.a.f.a.a.a ajf() {
        return this.cjo;
    }

    public i ajg() {
        return this.cjI;
    }

    public void R(T t) {
        if (!ac(t)) {
            ajm();
            this.cjD.set(t);
            ajj();
            ajl();
        }
    }

    public boolean ac(View view) {
        return this.cjD.contains(view);
    }

    public void onStart() {
    }

    public void cB() {
        aji();
        if (this.cjE != null) {
            this.cjE.destroy();
        }
        this.cjo.destroy();
        this.cjC.destroy();
        this.cjG = false;
        ajl();
        if (this.cjF != null) {
            this.cjF.a(this);
        }
    }

    @Override // com.c.a.a.a.f.a.a.b
    public void ajh() {
        ajl();
    }

    public void ck(boolean z) {
        if (isActive()) {
            this.cjo.iR(z ? "active" : "inactive");
        }
    }

    public void c(String str, double d) {
        if (d > this.cjK) {
            this.cjo.iP(str);
            this.cjJ = b.AD_STATE_VISIBLE;
        }
    }

    public void d(String str, double d) {
        if (d > this.cjK && this.cjJ != b.AD_STATE_HIDDEN) {
            this.cjo.iP(str);
            this.cjJ = b.AD_STATE_HIDDEN;
        }
    }

    protected void aji() {
        if (isActive()) {
            this.cjo.iQ(com.c.a.a.a.g.b.ajx().toString());
        }
    }

    protected void ajj() {
    }

    protected void ajk() {
        this.cjC.setWebView(getWebView());
    }

    protected void ajl() {
        boolean z = this.cjo.isActive() && this.cjG && !isEmpty();
        if (this.cjH != z) {
            setActive(z);
        }
    }

    protected void setActive(boolean z) {
        this.cjH = z;
        if (this.cjF != null) {
            if (z) {
                this.cjF.b(this);
            } else {
                this.cjF.c(this);
            }
        }
    }

    private void ajm() {
        this.cjK = com.c.a.a.a.g.d.ajy();
        this.cjJ = b.AD_STATE_IDLE;
    }
}
