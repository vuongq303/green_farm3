package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzabp implements Runnable {
    private final /* synthetic */ zzajh zzbzl;
    private final /* synthetic */ zzabo zzbzm;

    zzabp(zzabo zzaboVar, zzajh zzajhVar) {
        this.zzbzm = zzaboVar;
        this.zzbzl = zzajhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbzm.zzbzd.zzb(this.zzbzl);
    }
}
