package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zze implements Runnable {
    private final /* synthetic */ zzr zzn;
    private final /* synthetic */ zzd zzo;

    zze(zzd zzdVar, zzr zzrVar) {
        this.zzo = zzdVar;
        this.zzn = zzrVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzo.zzi.put(this.zzn);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
