package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;

/* JADX INFO: loaded from: classes.dex */
final class zzzx implements Runnable {
    private final /* synthetic */ zzzv zzbvr;
    private final /* synthetic */ AdOverlayInfoParcel zzzc;

    zzzx(zzzv zzzvVar, AdOverlayInfoParcel adOverlayInfoParcel) {
        this.zzbvr = zzzvVar;
        this.zzzc = adOverlayInfoParcel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.google.android.gms.ads.internal.zzbv.zzei();
        com.google.android.gms.ads.internal.overlay.zzl.zza(this.zzbvr.zzbvp, this.zzzc, true);
    }
}
