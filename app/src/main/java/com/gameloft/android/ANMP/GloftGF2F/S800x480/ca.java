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
        bz.access$002(false);
        bz.aq(true);
        GLLib.be("oss: thread ended");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (w.ZU) {
            switch (bz.os()) {
                case 0:
                    bz.access$302(bz.b(this.akV, (String) this.adx[1]));
                    break;
                case 1:
                    bz.access$302(bz.a(this.akV));
                    break;
                case 2:
                    bz.access$302(bz.b(this.akV));
                    break;
                case 3:
                    bz.access$302(bz.c(this.akV));
                    break;
                case 4:
                    bz.access$302(bz.a(this.akV, (bc) this.adx[1], (String) this.adx[2]));
                    break;
                case 5:
                    bz.access$302(bz.b(this.akV, (bc) this.adx[1], (String) this.adx[2]));
                    break;
                case 6:
                    bz.access$302(bz.c(this.akV, (bc) this.adx[1], (String) this.adx[2]));
                    break;
            }
            mJ();
        }
    }
}
