package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzau implements Thread.UncaughtExceptionHandler {
    private final /* synthetic */ zzat zzvz;

    zzau(zzat zzatVar) {
        this.zzvz = zzatVar;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        zzcm zzcmVarZzcj = this.zzvz.zzcj();
        if (zzcmVarZzcj != null) {
            zzcmVarZzcj.zze("Job execution failed", th);
        }
    }
}
