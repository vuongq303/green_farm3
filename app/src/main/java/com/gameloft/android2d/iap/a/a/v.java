package com.gameloft.android2d.iap.a.a;

import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class v implements Runnable {
    final /* synthetic */ r bmn;
    final /* synthetic */ List bmu;
    final /* synthetic */ y bmv;
    final /* synthetic */ z bmw;
    final /* synthetic */ Handler val$handler;

    v(r rVar, List list, y yVar, Handler handler, z zVar) {
        this.bmn = rVar;
        this.bmu = list;
        this.bmv = yVar;
        this.val$handler = handler;
        this.bmw = zVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayList = new ArrayList();
        for (af afVar : this.bmu) {
            try {
                this.bmn.b(afVar);
                arrayList.add(new ad(0, "Successful consume of sku " + afVar.Su()));
            } catch (q e) {
                arrayList.add(e.Sl());
            }
        }
        this.bmn.Sn();
        if (this.bmv != null) {
            this.val$handler.post(new w(this, arrayList));
        }
        if (this.bmw != null) {
            this.val$handler.post(new x(this, arrayList));
        }
    }
}
