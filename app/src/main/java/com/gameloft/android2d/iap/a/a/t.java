package com.gameloft.android2d.iap.a.a;

import android.os.Handler;
import java.util.List;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class t implements Runnable {
    final /* synthetic */ r bmn;
    final /* synthetic */ boolean bmo;
    final /* synthetic */ List bmp;
    final /* synthetic */ ac bmq;
    final /* synthetic */ Handler val$handler;

    t(r rVar, boolean z, List list, Handler handler, ac acVar) {
        this.bmn = rVar;
        this.bmo = z;
        this.bmp = list;
        this.val$handler = handler;
        this.bmq = acVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ad adVar = new ad(0, "Inventory refresh successful.");
        ae aeVarA = null;
        try {
            aeVarA = this.bmn.a(this.bmo, this.bmp);
        } catch (q e) {
            adVar = e.Sl();
        } catch (IllegalStateException e2) {
            adVar = new ad(6, "Helper is not setup." + e2.toString());
        }
        this.bmn.Sn();
        this.val$handler.post(new u(this, adVar, aeVarA));
    }
}
