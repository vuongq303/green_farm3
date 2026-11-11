package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzow implements Runnable {
    private final /* synthetic */ zzov zzbis;

    zzow(zzov zzovVar) {
        this.zzbis = zzovVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbis.zzbij != null) {
            this.zzbis.zzbij.zzkq();
            this.zzbis.zzbij.zzkp();
            this.zzbis.zzbij.zzcs();
        }
        zzov.zza(this.zzbis, null);
    }
}
