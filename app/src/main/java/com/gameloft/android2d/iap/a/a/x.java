package com.gameloft.android2d.iap.a.a;

import java.util.List;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
class x implements Runnable {
    final /* synthetic */ List bmx;
    final /* synthetic */ v bmy;

    x(v vVar, List list) {
        this.bmy = vVar;
        this.bmx = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bmy.bmw.b(this.bmy.bmu, this.bmx);
    }
}
