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
        this.ady.adu = false;
        this.ady.adw = true;
        GLLib.be("thread ended");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        switch (this.ady.adv) {
            case 0:
                int unused = ao.adh = ao.ms().D((String) this.adx[1], (String) this.adx[2]);
                break;
            case 1:
                int unused2 = ao.adh = this.ady.ew(((Integer) this.adx[1]).intValue());
                break;
        }
        mJ();
    }
}
