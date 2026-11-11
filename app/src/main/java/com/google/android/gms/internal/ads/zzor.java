package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzor implements Runnable {
    private final /* synthetic */ zzoq zzbin;

    zzor(zzoq zzoqVar) {
        this.zzbin = zzoqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbin.zzbij != null) {
            this.zzbin.zzbij.zzkq();
            this.zzbin.zzbij.zzkp();
            this.zzbin.zzbij.zzcs();
        }
        zzoq.zza(this.zzbin, null);
    }
}
