package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzvu implements zzaoo<zzwb> {
    private final /* synthetic */ zzvs zzbqs;

    zzvu(zzvs zzvsVar) {
        this.zzbqs = zzvsVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaoo
    public final /* synthetic */ void zze(zzwb zzwbVar) {
        zzakb.v("Releasing engine reference.");
        this.zzbqs.zzbqq.zzma();
    }
}
