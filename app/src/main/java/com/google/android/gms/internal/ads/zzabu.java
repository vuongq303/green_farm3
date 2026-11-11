package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzabu implements Runnable {
    private final /* synthetic */ zzajh zzaam;
    private final /* synthetic */ zzabt zzbzw;

    zzabu(zzabt zzabtVar, zzajh zzajhVar) {
        this.zzbzw = zzabtVar;
        this.zzaam = zzajhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbzw.zzbzd.zzb(this.zzaam);
    }
}
