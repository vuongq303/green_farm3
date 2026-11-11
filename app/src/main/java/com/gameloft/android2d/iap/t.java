package com.gameloft.android2d.iap;

import android.os.Looper;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class t implements Runnable {
    final /* synthetic */ int bkS;
    final /* synthetic */ String bkT;
    final /* synthetic */ long bkU;

    t(String str, int i, long j) {
        this.bkT = str;
        this.bkS = i;
        this.bkU = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        Looper.prepare();
        com.gameloft.android2d.iap.utils.r rVar = b.bjp.bjq.Qr().gm(this.bkT.toLowerCase()).get(Integer.valueOf(this.bkS));
        com.gameloft.android2d.iap.a.a aVarSM = rVar.SM();
        int iRT = b.bjp.bjq.Qr().RT();
        if (!b.bjL) {
            b.bjp.bju = this.bkS;
            b.bjp.bjv = this.bkT;
            com.gameloft.android2d.iap.utils.u.af(a.bjj[2], String.valueOf(b.bjp.bju));
            com.gameloft.android2d.iap.utils.u.af(a.bjj[6], b.bjp.bjv);
            com.gameloft.android2d.iap.utils.u.fK(aVarSM.getPrice());
            com.gameloft.android2d.iap.utils.u.fL(aVarSM.getProfileId());
            com.gameloft.android2d.iap.utils.u.fM(aVarSM.getCurrency());
            com.gameloft.android2d.iap.utils.u.fQ(this.bkU > 0 ? String.valueOf(this.bkU) : String.valueOf(b.g(this.bkS, this.bkT)));
            com.gameloft.android2d.iap.utils.u.o(rVar.SL());
            com.gameloft.android2d.iap.utils.u.D(rVar.ST(), 50);
            com.gameloft.android2d.iap.utils.u.b(aVarSM);
            com.gameloft.android2d.iap.utils.u.mH(iRT);
            com.gameloft.android2d.iap.utils.u.fW(b.P(this.bkS, this.bkT));
            com.gameloft.android2d.iap.utils.u.D(String.valueOf(b.l(this.bkS, this.bkT)), 54);
            aVarSM.fn(rVar.getId());
            aVarSM.eP(rVar.getId());
        } else {
            int iA = b.a(rVar);
            if (iA == -1000 && !aVarSM.Rw().equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgG))) {
                b.setResult(3);
                b.mB(-1);
                return;
            }
            if (iA != 403 && iA != 429) {
                b.bjp.bju = this.bkS;
                b.bjp.bjv = this.bkT;
                com.gameloft.android2d.iap.utils.u.af(a.bjj[2], String.valueOf(b.bjp.bju));
                com.gameloft.android2d.iap.utils.u.af(a.bjj[6], b.bjp.bjv);
                com.gameloft.android2d.iap.utils.u.fK(aVarSM.getPrice());
                com.gameloft.android2d.iap.utils.u.fL(aVarSM.getProfileId());
                com.gameloft.android2d.iap.utils.u.fM(aVarSM.getCurrency());
                com.gameloft.android2d.iap.utils.u.fQ(this.bkU > 0 ? String.valueOf(this.bkU) : String.valueOf(b.g(this.bkS, this.bkT)));
                com.gameloft.android2d.iap.utils.u.o(rVar.SL());
                com.gameloft.android2d.iap.utils.u.D(rVar.ST(), 50);
                com.gameloft.android2d.iap.utils.u.b(aVarSM);
                com.gameloft.android2d.iap.utils.u.mH(iRT);
                com.gameloft.android2d.iap.utils.u.fW(b.P(this.bkS, this.bkT));
                com.gameloft.android2d.iap.utils.u.D(String.valueOf(b.l(this.bkS, this.bkT)), 54);
                aVarSM.fn(rVar.getId());
                aVarSM.eP(rVar.getId());
                if (this.bkT.toLowerCase().equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgy).toLowerCase())) {
                    com.gameloft.android2d.iap.utils.u.fN(b.V(this.bkS, this.bkT));
                } else if (this.bkT.toLowerCase().equals(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgB).toLowerCase())) {
                    com.gameloft.android2d.iap.utils.u.fO(b.W(this.bkS, this.bkT));
                    com.gameloft.android2d.iap.utils.u.fP(b.X(this.bkS, this.bkT));
                }
            } else {
                b.eH(b.Rb());
                return;
            }
        }
        Looper.loop();
    }
}
