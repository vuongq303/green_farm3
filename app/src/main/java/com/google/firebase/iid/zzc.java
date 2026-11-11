package com.google.firebase.iid;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzc implements Runnable {
    private final /* synthetic */ Intent zzbpr;
    private final /* synthetic */ Intent zzbps;
    private final /* synthetic */ zzb zzbpt;

    zzc(zzb zzbVar, Intent intent, Intent intent2) {
        this.zzbpt = zzbVar;
        this.zzbpr = intent;
        this.zzbps = intent2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbpt.zzh(this.zzbpr);
        this.zzbpt.zze(this.zzbps);
    }
}
