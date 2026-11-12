package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cs extends Thread {
    final /* synthetic */ ci bbN;

    public cs(ci ciVar) {
        this.bbN = ciVar;
    }

    public /* synthetic */ cs(ci ciVar, cj cjVar) {
        this(ciVar);
    }

    private void mJ() {
        ci.a(this.bbN, false);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        ci.a(this.bbN, true);
        ci.Mn();
        mJ();
    }
}
