package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzafd implements zzaoo<zzwb> {
    private final /* synthetic */ zzafc zzcgm;

    zzafd(zzafc zzafcVar) {
        this.zzcgm = zzafcVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaoo
    public final /* synthetic */ void zze(zzwb zzwbVar) {
        try {
            zzwbVar.zzb("AFMA_getAdapterLessMediationAd", this.zzcgm.zzcgk);
        } catch (Exception e) {
            zzakb.zzb("Error requesting an ad url", e);
            zzafa.zzcgg.zzat(this.zzcgm.zzcgl);
        }
    }
}
