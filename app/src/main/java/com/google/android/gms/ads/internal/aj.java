package com.google.android.gms.ads.internal;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class aj implements Callable<String> {
    private final /* synthetic */ zzd bDs;

    aj(zzd zzdVar) {
        this.bDs = zzdVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() {
        return this.bDs.zzvw.bCV.zzaa().zza(this.bDs.zzvw.zzrt);
    }
}
