package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: SaveGameManager.java */
/* JADX INFO: loaded from: classes.dex */
class br extends Thread {
    Object[] adx;

    public br(Object[] objArr) {
        this.adx = objArr;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (w.ZT) {
            switch (bq.ake) {
                case 1:
                    bq.nH();
                    break;
                case 2:
                    bq.nM();
                    break;
                case 3:
                    bq.cu((String) this.adx[0]);
                    break;
                case 4:
                    bq.G((String) this.adx[0], (String) this.adx[1]);
                    break;
                case 5:
                    bq.a((String) this.adx[0], (e) this.adx[1]);
                    break;
                case 6:
                    bq.nN();
                    break;
            }
            bq.mJ();
        }
    }
}
