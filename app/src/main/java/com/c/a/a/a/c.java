package com.c.a.a.a;

import android.content.Context;

/* JADX INFO: compiled from: AvidLoader.java */
/* JADX INFO: loaded from: classes.dex */
public class c implements q {
    private static c ciN = new c();
    private e ciO;
    private p ciP;
    private g ciR;
    private Context context;
    private f ciQ = new f(this);
    private final Runnable ciS = new d(this);

    public static c aiz() {
        return ciN;
    }

    public void aY(Context context) {
        this.context = context;
        this.ciR = new g(this);
        aiB();
    }

    public void aiA() {
        if (this.ciR != null) {
            this.ciR.bM();
            this.ciR = null;
        }
        this.ciO = null;
        this.context = null;
    }

    public void a(e eVar) {
        this.ciO = eVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aiB() {
        if (!a.ait() && this.ciP == null) {
            this.ciP = new p();
            this.ciP.a(this);
            this.ciQ.a(this.ciP);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aiC() {
        if (this.ciR != null) {
            this.ciR.aiC();
        }
    }

    @Override // com.c.a.a.a.q
    public void iM(String str) {
        this.ciP = null;
        a.iL(str);
        if (this.ciO != null) {
            this.ciO.aiE();
        }
    }

    @Override // com.c.a.a.a.q
    public void aiD() {
        this.ciP = null;
        aiC();
    }
}
