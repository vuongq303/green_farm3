package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaho implements Runnable {
    private final /* synthetic */ zzxq zzclu;
    private final /* synthetic */ zzahn zzclv;
    private final /* synthetic */ zzjj zzyh;

    zzaho(zzahn zzahnVar, zzjj zzjjVar, zzxq zzxqVar) {
        this.zzclv = zzahnVar;
        this.zzyh = zzjjVar;
        this.zzclu = zzxqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzclv.zza(this.zzyh, this.zzclu);
    }
}
