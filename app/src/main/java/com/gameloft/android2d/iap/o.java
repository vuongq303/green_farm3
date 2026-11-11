package com.gameloft.android2d.iap;

import android.view.View;
import android.widget.RadioGroup;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
class o implements View.OnClickListener {
    final /* synthetic */ boolean bkV;
    final /* synthetic */ int bkW;
    final /* synthetic */ int bkX;
    final /* synthetic */ int bkY;
    final /* synthetic */ n bkZ;

    o(n nVar, boolean z, int i, int i2, int i3) {
        this.bkZ = nVar;
        this.bkV = z;
        this.bkW = i;
        this.bkX = i2;
        this.bkY = i3;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.bkV) {
            com.gameloft.android2d.iap.utils.r rVar = b.bjp.bjq.Qr().gm(b.bjp.bjv.toLowerCase()).get(Integer.valueOf(b.bjp.bju));
            com.gameloft.android2d.iap.a.a aVarSM = rVar.SM();
            int checkedRadioButtonId = ((RadioGroup) b.bjp.bjJ.findViewById(com.b.b.cfx)).getCheckedRadioButtonId();
            if (checkedRadioButtonId == this.bkW) {
                aVarSM.eQ(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgO));
                rVar.fH(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgO));
            } else if (checkedRadioButtonId == this.bkX) {
                aVarSM.eQ(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgP));
                rVar.fH(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgP));
            } else if (checkedRadioButtonId == this.bkY) {
                aVarSM.eQ(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgN));
                rVar.fH(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgN));
            }
        }
        b.c(b.bjp.bju, b.bjp.bjv, this.bkZ.bkU);
        b.bjp.bjJ.cancel();
    }
}
