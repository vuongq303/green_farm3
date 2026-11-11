package a;

/* JADX INFO: compiled from: UnobservedErrorNotifier.java */
/* JADX INFO: loaded from: classes.dex */
class y {
    private o<?> zX;

    public y(o<?> oVar) {
        this.zX = oVar;
    }

    protected void finalize() throws Throwable {
        w wVarFz;
        try {
            o<?> oVar = this.zX;
            if (oVar != null && (wVarFz = o.fz()) != null) {
                wVarFz.a(oVar, new z(oVar.getError()));
            }
        } finally {
            super.finalize();
        }
    }

    public void fH() {
        this.zX = null;
    }
}
