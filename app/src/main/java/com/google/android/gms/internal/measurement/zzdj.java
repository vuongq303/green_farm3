package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzdj extends zzdg {
    private final /* synthetic */ zzdi zzaci;

    zzdj(zzdi zzdiVar) {
        this.zzaci = zzdiVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdg, com.google.android.gms.internal.measurement.zzdq
    public final void zza(Status status) {
        this.zzaci.setResult(status);
    }
}
