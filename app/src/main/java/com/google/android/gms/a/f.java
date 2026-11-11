package com.google.android.gms.a;

import android.content.BroadcastReceiver;

/* JADX INFO: loaded from: classes.dex */
final class f implements Runnable {
    private final /* synthetic */ BroadcastReceiver.PendingResult zzqp;

    f(a aVar, BroadcastReceiver.PendingResult pendingResult) {
        this.zzqp = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzqp != null) {
            this.zzqp.finish();
        }
    }
}
