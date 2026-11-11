package com.google.firebase.iid;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class zzg implements Runnable {
    private final /* synthetic */ zzd zzbpz;
    private final /* synthetic */ zzf zzbqa;

    zzg(zzf zzfVar, zzd zzdVar) {
        this.zzbqa = zzfVar;
        this.zzbpz = zzdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzbqa.zzbpy.zzh(this.zzbpz.intent);
        this.zzbpz.finish();
    }
}
