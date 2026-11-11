package com.gameloft.android2d.iap.a.a;

import java.util.List;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class w implements Runnable {
    final /* synthetic */ List bmx;
    final /* synthetic */ v bmy;

    w(v vVar, List list) {
        this.bmy = vVar;
        this.bmx = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bmy.bmv.a((af) this.bmy.bmu.get(0), (ad) this.bmx.get(0));
    }
}
