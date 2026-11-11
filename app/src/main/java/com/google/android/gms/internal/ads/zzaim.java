package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaim implements zzanl<Void> {
    private final /* synthetic */ zzanz zzcnb;

    zzaim(zzaii zzaiiVar, zzanz zzanzVar) {
        this.zzcnb = zzanzVar;
    }

    @Override // com.google.android.gms.internal.ads.zzanl
    public final void zzb(Throwable th) {
        zzaii.zzcml.remove(this.zzcnb);
    }

    @Override // com.google.android.gms.internal.ads.zzanl
    public final /* synthetic */ void zzh(Void r3) {
        zzaii.zzcml.remove(this.zzcnb);
    }
}
