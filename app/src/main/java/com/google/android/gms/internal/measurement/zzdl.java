package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzdl extends zzdg {
    private final /* synthetic */ zzdk zzacj;

    zzdl(zzdk zzdkVar) {
        this.zzacj = zzdkVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdg, com.google.android.gms.internal.measurement.zzdq
    public final void zza(Status status) {
        this.zzacj.setResult(status);
    }
}
