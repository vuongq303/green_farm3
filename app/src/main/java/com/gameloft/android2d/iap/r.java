package com.gameloft.android2d.iap;

import android.view.View;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class r implements View.OnClickListener {
    final /* synthetic */ n bkZ;

    r(n nVar) {
        this.bkZ = nVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!b.bkP) {
            b.bkP = true;
            int iRT = b.bjp.bjq.Qr().gm(b.bjp.bjv.toLowerCase()).get(Integer.valueOf(b.bjp.bju)).SM().RT();
            String language = b.getLanguage();
            String strY = com.gameloft.android2d.iap.utils.v.Y(com.b.d.cfW, language);
            if (iRT == 17) {
                strY = com.gameloft.android2d.iap.utils.v.Y(com.b.d.cfU, language);
            }
            b.B(b.Qr().Ud(), b.QO(), strY);
        }
    }
}
