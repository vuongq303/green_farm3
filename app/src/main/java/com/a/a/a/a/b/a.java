package com.a.a.a.a.b;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final i cdI;

    private a(i iVar) {
        this.cdI = iVar;
    }

    public static a a(b bVar) {
        i iVar = (i) bVar;
        com.a.a.a.a.e.f.c(bVar, "AdSession is null");
        com.a.a.a.a.e.f.d(iVar);
        com.a.a.a.a.e.f.b(iVar);
        a aVar = new a(iVar);
        iVar.ahQ().a(aVar);
        return aVar;
    }

    public void ahD() {
        com.a.a.a.a.e.f.b(this.cdI);
        com.a.a.a.a.e.f.e(this.cdI);
        if (!this.cdI.ahT()) {
            try {
                this.cdI.start();
            } catch (Exception e) {
            }
        }
        if (this.cdI.ahT()) {
            this.cdI.b();
        }
    }
}
