package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzcx implements zzca {
    private final /* synthetic */ Runnable zzabo;
    private final /* synthetic */ zzcu zzabp;

    zzcx(zzcu zzcuVar, Runnable runnable) {
        this.zzabp = zzcuVar;
        this.zzabo = runnable;
    }

    @Override // com.google.android.gms.internal.measurement.zzca
    public final void zza(Throwable th) {
        this.zzabp.handler.post(this.zzabo);
    }
}
