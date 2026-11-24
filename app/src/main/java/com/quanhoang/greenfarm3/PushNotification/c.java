package com.quanhoang.greenfarm3.PushNotification;

import android.os.Bundle;

/* JADX INFO: compiled from: AsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ a Fe;
    final /* synthetic */ Bundle val$params;

    c(a aVar, Bundle bundle) {
        this.Fe = aVar;
        this.val$params = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Fe.b(Integer.valueOf(this.Fe.e(this.val$params).intValue()));
    }
}
