package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzp extends zzq {
    private final /* synthetic */ zzo zzai;

    zzp(zzo zzoVar) {
        this.zzai = zzoVar;
    }

    @Override // com.google.android.gms.internal.auth.zzq, com.google.android.gms.auth.a.d
    public final void zzd(boolean z) {
        this.zzai.setResult(new zzt(z ? Status.bIO : zzk.zzaf));
    }
}
