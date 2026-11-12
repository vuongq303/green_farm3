package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class ap extends Thread {
    Object[] adx;
    final /* synthetic */ ao ady;

    public ap(ao aoVar, Object[] objArr) {
        this.ady = aoVar;
        this.adx = objArr;
    }

    private void mJ() {
        ao.a(this.ady, false);
        ao.b(this.ady, true);
        GLLib.be("thread ended");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        switch (ao.a(this.ady)) {
            case 0:
                ao.access$302(ao.ms().D((String) this.adx[1], (String) this.adx[2]));
                break;
            case 1:
                ao.access$302(ao.a(this.ady, ((Integer) this.adx[1]).intValue()));
                break;
        }
        mJ();
    }
}
