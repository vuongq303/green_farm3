package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzop implements Runnable {
    private final /* synthetic */ zzoo zzbik;

    zzop(zzoo zzooVar) {
        this.zzbik = zzooVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzbik.zzbij != null) {
            this.zzbik.zzbij.zzkq();
            this.zzbik.zzbij.zzkp();
            this.zzbik.zzbij.zzcs();
        }
        zzoo.zza(this.zzbik, null);
    }
}
