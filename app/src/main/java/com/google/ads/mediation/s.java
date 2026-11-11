package com.google.ads.mediation;

import com.google.android.gms.ads.reward.zza;

/* JADX INFO: loaded from: classes.dex */
final class s extends zza {
    private final /* synthetic */ a bzl;

    s(a aVar) {
        this.bzl = aVar;
    }

    @Override // com.google.android.gms.ads.reward.zza
    public final void zzt() {
        if (this.bzl.zzha == null || this.bzl.zzhb == null) {
            return;
        }
        this.bzl.zzhb.zzc(this.bzl.zzha.zzba());
    }
}
