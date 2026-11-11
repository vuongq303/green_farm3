package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzgn implements Callable<String> {
    private final /* synthetic */ zzgl zzanp;
    private final /* synthetic */ zzec zzanq;

    zzgn(zzgl zzglVar, zzec zzecVar) {
        this.zzanp = zzglVar;
        this.zzanq = zzecVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() {
        zzeb zzebVarZzh = this.zzanp.zzgi().zzav(this.zzanq.packageName) ? this.zzanp.zzh(this.zzanq) : this.zzanp.zzga().zzax(this.zzanq.packageName);
        if (zzebVarZzh != null) {
            return zzebVarZzh.getAppInstanceId();
        }
        this.zzanp.zzgg().zzin().log("App info was null when attempting to get app instance id");
        return null;
    }
}
