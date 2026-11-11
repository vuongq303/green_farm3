package com.gameloft.android2d.iap;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class k extends Thread {
    k() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        b.bkf = true;
        while (true) {
            try {
                if (System.currentTimeMillis() - b.bkk > 1800000 && com.gameloft.android2d.iap.utils.d.Pd() && ((b.bjp.state == 10 || b.bjp.state == 11) && !b.bkl)) {
                    b.bkl = true;
                    b bVar = b.bjp;
                    bVar.getClass();
                    new Thread(new ac(bVar, null)).start();
                    b.bkk = System.currentTimeMillis();
                }
                Thread.sleep(2000L);
            } catch (Exception e) {
                return;
            }
        }
    }
}
