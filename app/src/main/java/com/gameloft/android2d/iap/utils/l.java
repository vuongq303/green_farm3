package com.gameloft.android2d.iap.utils;

import android.os.Bundle;

/* JADX INFO: compiled from: IABAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class l implements Runnable {
    final /* synthetic */ j bnB;
    final /* synthetic */ Bundle val$params;

    l(j jVar, Bundle bundle) {
        this.bnB = jVar;
        this.val$params = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.bnB.b(Integer.valueOf(this.bnB.e(this.val$params).intValue()));
    }
}
