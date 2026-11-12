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
            switch (bq.nO()) {
                case 1:
                    bq.access$100();
                    break;
                case 2:
                    bq.access$200();
                    break;
                case 3:
                    bq.cC((String) this.adx[0]);
                    break;
                case 4:
                    bq.H((String) this.adx[0], (String) this.adx[1]);
                    break;
                case 5:
                    bq.b((String) this.adx[0], (e) this.adx[1]);
                    break;
                case 6:
                    bq.access$600();
                    break;
            }
            bq.nP();
        }
    }
}
