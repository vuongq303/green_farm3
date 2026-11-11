package com.gameloft.android2d.iap.a.a;

import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
class j extends com.gameloft.android2d.iap.utils.m {
    final /* synthetic */ a blU;
    final /* synthetic */ boolean blW;

    j(a aVar, boolean z) {
        this.blU = aVar;
        this.blW = z;
    }

    @Override // com.gameloft.android2d.iap.utils.m
    public void p(Bundle bundle) {
        if (bundle.getInt("a5") == 1) {
            String string = bundle.getString("a4");
            String strG = com.gameloft.android2d.iap.utils.y.G(string, 0);
            com.gameloft.android2d.iap.utils.y.G(string, 1);
            String strG2 = com.gameloft.android2d.iap.utils.y.G(string, 2);
            String strG3 = com.gameloft.android2d.iap.utils.y.G(string, 3);
            if (TextUtils.isEmpty(strG) || strG.equals("FAILURE")) {
                if (a.blS > 0) {
                    a.Sj();
                    new Thread(new k(this)).start();
                    return;
                }
                com.gameloft.android2d.iap.utils.u.fT("");
                com.gameloft.android2d.iap.utils.u.fR("");
                com.gameloft.android2d.iap.utils.u.fY("");
                com.gameloft.android2d.iap.utils.u.fS("");
                com.gameloft.android2d.iap.b.setResult(3);
                com.gameloft.android2d.iap.b.mB(-5);
                return;
            }
            try {
                com.gameloft.android2d.iap.utils.u.D(strG3, 39);
                if (com.gameloft.android2d.iap.b.eF(strG2) && !TextUtils.isEmpty(strG) && strG.equals("SUCCESS")) {
                    String strSu = com.gameloft.android2d.iap.utils.u.Su();
                    if (this.blU.fq(strSu)) {
                        String strMG = com.gameloft.android2d.iap.utils.u.mG(53);
                        if (strMG != null) {
                            strSu = strMG + ";" + strSu;
                        }
                        com.gameloft.android2d.iap.utils.u.D(strSu, 53);
                    }
                    com.gameloft.android2d.iap.utils.u.D(this.blW ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO, 59);
                    bundle.clear();
                    return;
                }
                return;
            } catch (Exception e) {
                return;
            }
        }
        bundle.getInt("a6");
        if (com.gameloft.android2d.iap.utils.d.Pd()) {
            new Thread(new l(this)).start();
        } else {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(-1);
        }
    }
}
