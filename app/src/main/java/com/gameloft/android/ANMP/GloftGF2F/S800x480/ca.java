package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: UserProfile.java */
/* JADX INFO: loaded from: classes.dex */
class ca extends Thread {
    Object[] adx;
    final /* synthetic */ bz akV;

    public ca(bz bzVar, Object[] objArr) {
        this.akV = bzVar;
        this.adx = objArr;
    }

    private void mJ() {
        boolean unused = bz.adu = false;
        boolean unused2 = bz.adw = true;
        GLLib.be("oss: thread ended");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (w.ZU) {
            switch (bz.adv) {
                case 0:
                    int unused = bz.adh = this.akV.cF((String) this.adx[1]);
                    break;
                case 1:
                    int unused2 = bz.adh = this.akV.oo();
                    break;
                case 2:
                    int unused3 = bz.adh = this.akV.oq();
                    break;
                case 3:
                    int unused4 = bz.adh = this.akV.or();
                    break;
                case 4:
                    int unused5 = bz.adh = this.akV.c((bc) this.adx[1], (String) this.adx[2]);
                    break;
                case 5:
                    int unused6 = bz.adh = this.akV.e((bc) this.adx[1], (String) this.adx[2]);
                    break;
                case 6:
                    int unused7 = bz.adh = this.akV.g((bc) this.adx[1], (String) this.adx[2]);
                    break;
            }
            mJ();
        }
    }
}
