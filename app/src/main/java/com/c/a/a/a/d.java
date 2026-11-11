package com.c.a.a.a;

/* JADX INFO: compiled from: AvidLoader.java */
/* JADX INFO: loaded from: classes.dex */
class d implements Runnable {
    final /* synthetic */ c ciT;

    d(c cVar) {
        this.ciT = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.ciT.context == null || !com.c.a.a.a.g.f.aZ(this.ciT.context)) {
            this.ciT.aiC();
        } else {
            this.ciT.aiB();
        }
    }
}
