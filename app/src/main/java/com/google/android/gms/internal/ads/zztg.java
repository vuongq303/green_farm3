package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zztg extends zzkf {
    private final /* synthetic */ zzst zzbnw;

    zztg(zzst zzstVar) {
        this.zzbnw = zzstVar;
    }

    @Override // com.google.android.gms.internal.ads.zzke
    public final void onAdClicked() {
        this.zzbnw.zzxo.add(new zzth(this));
    }
}
