package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzji implements Runnable {
    private final /* synthetic */ Runnable zzabo;
    private final /* synthetic */ zzgl zzafi;

    zzji(zzjf zzjfVar, zzgl zzglVar, Runnable runnable) {
        this.zzafi = zzglVar;
        this.zzabo = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzafi.zzka();
        this.zzafi.zze(this.zzabo);
        this.zzafi.zzjw();
    }
}
