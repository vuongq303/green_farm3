package com.google.android.gms.ads.internal;

import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzl;

/* JADX INFO: loaded from: classes.dex */
final class i implements Runnable {
    private final /* synthetic */ h bBx;
    private final /* synthetic */ AdOverlayInfoParcel zzzc;

    i(h hVar, AdOverlayInfoParcel adOverlayInfoParcel) {
        this.bBx = hVar;
        this.zzzc = adOverlayInfoParcel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzbv.zzei();
        zzl.zza(this.bBx.bBv.zzvw.zzrt, this.zzzc, true);
    }
}
