package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.app.Activity;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
final class r extends Thread {
    final /* synthetic */ Activity Gh;

    r(Activity activity) {
        this.Gh = activity;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        aa.aj(this.Gh);
    }
}
