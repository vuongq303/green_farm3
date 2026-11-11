package com.gameloft.a.a;

/* JADX INFO: compiled from: Hestia.java */
/* JADX INFO: loaded from: classes.dex */
class p extends Thread {
    Object[] adx;
    final /* synthetic */ o bwy;

    p(o oVar, Object[] objArr) {
        this.bwy = oVar;
        this.adx = objArr;
    }

    private void mJ() {
        this.bwy.adu = false;
        this.bwy.adw = true;
        if (o.bwx != null) {
            o.bwx.c(this.bwy.adv, this.bwy.adc, this.bwy.mv());
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        switch (this.bwy.adv) {
            case 0:
                int unused = o.adh = o.VW().D((String) this.adx[1], (String) this.adx[2]);
                break;
            case 1:
                int unused2 = o.adh = this.bwy.ew(((Integer) this.adx[1]).intValue());
                break;
        }
        mJ();
    }
}
