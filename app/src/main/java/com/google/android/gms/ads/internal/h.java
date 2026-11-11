package com.google.android.gms.ads.internal;

import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajx;
import com.google.android.gms.internal.ads.zzakk;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class h extends zzajx {
    final /* synthetic */ zzal bBv;
    private final int bBw;

    public h(zzal zzalVar, int i) {
        this.bBv = zzalVar;
        this.bBw = i;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        zzaq zzaqVar = new zzaq(this.bBv.zzvw.zzze, this.bBv.WY(), this.bBv.bBp, this.bBv.bBq, this.bBv.zzvw.zzze ? this.bBw : -1, this.bBv.zzyu, this.bBv.zzvw.zzacw.zzzl, this.bBv.zzvw.zzacw.zzzm);
        int requestedOrientation = this.bBv.zzvw.zzacw.zzbyo.getRequestedOrientation();
        if (requestedOrientation == -1) {
            requestedOrientation = this.bBv.zzvw.zzacw.orientation;
        }
        zzakk.zzcrm.post(new i(this, new AdOverlayInfoParcel(this.bBv, this.bBv, this.bBv, this.bBv.zzvw.zzacw.zzbyo, requestedOrientation, this.bBv.zzvw.zzacr, this.bBv.zzvw.zzacw.zzcev, zzaqVar)));
    }
}
