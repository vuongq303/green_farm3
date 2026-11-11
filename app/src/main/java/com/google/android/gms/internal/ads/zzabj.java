package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzabj implements Runnable {
    private final /* synthetic */ zzajh zzaam;
    private final /* synthetic */ zzabh zzbzk;

    zzabj(zzabh zzabhVar, zzajh zzajhVar) {
        this.zzbzk = zzabhVar;
        this.zzaam = zzajhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzbzk.mLock) {
            zzabh zzabhVar = this.zzbzk;
            zzabhVar.zzbzd.zzb(this.zzaam);
        }
    }
}
