package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaqf implements Runnable {
    private final /* synthetic */ zzaqe zzdax;

    zzaqf(zzaqe zzaqeVar) {
        this.zzdax = zzaqeVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.google.android.gms.ads.internal.zzbv.zzff().zzb(this.zzdax);
    }
}
