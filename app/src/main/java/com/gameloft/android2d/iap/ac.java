package com.gameloft.android2d.iap;

import android.content.IntentFilter;
import android.os.SystemClock;
import com.gameloft.android2d.iap.a.a.ai;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class ac implements Runnable {
    final /* synthetic */ b blf;

    private ac(b bVar) {
        this.blf = bVar;
    }

    /* synthetic */ ac(b bVar, c cVar) {
        this(bVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (!com.gameloft.android2d.iap.utils.d.Pd()) {
                try {
                    Thread.sleep(3000L);
                } catch (Exception e) {
                }
                if (!b.bkl) {
                    b.setResult(11);
                    b.mB(-6);
                    return;
                } else {
                    b.bkl = false;
                    return;
                }
            }
            if (b.bjM.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgt)) || b.bjM.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgr)) || b.bjM.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgp)) || b.bjM.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgq)) || b.bjM.equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgx))) {
                b.QW();
            }
            long unused = b.bjW = SystemClock.elapsedRealtime();
            if (this.blf.bjq.gl(this.blf.bjB)) {
                if (!this.blf.bjq.Qr().TU()) {
                    long unused2 = b.bjV = com.gameloft.android2d.iap.utils.v.ai(b.QR(), b.QS());
                    b.Qn();
                    com.gameloft.android2d.iap.utils.u.D(this.blf.bjq.Qr().Uf(), 48);
                    com.gameloft.android2d.iap.a.a.o unused3 = b.bkE = new com.gameloft.android2d.iap.a.a.o(new ai());
                    IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
                    if (intentFilter != null) {
                        com.gameloft.android2d.iap.utils.v.getContext().registerReceiver(b.bkE, intentFilter);
                    }
                    new Thread(new ad(this)).start();
                    return;
                }
                b.setResult(11);
                b.mB(-7);
                return;
            }
            b.setResult(11);
            b.mB(-6);
        } catch (Exception e2) {
            b.setResult(11);
            b.mB(-6);
        }
    }
}
