package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class zze implements Runnable {
    private final /* synthetic */ Intent zzbpr;
    private final /* synthetic */ zzd zzbpx;

    zze(zzd zzdVar, Intent intent) {
        this.zzbpx = zzdVar;
        this.zzbpr = intent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String action = this.zzbpr.getAction();
        Log.w("EnhancedIntentService", new StringBuilder(String.valueOf(action).length() + 61).append("Service took too long to process intent: ").append(action).append(" App may get closed.").toString());
        this.zzbpx.finish();
    }
}
