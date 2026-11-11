package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzaq extends zzam {
    private final /* synthetic */ zzap zzds;

    zzaq(zzap zzapVar) {
        this.zzds = zzapVar;
    }

    @Override // com.google.android.gms.internal.auth.zzam, com.google.android.gms.internal.auth.zzba
    public final void zzd(Status status, Credential credential) {
        this.zzds.setResult(new zzan(status, credential));
    }

    @Override // com.google.android.gms.internal.auth.zzam, com.google.android.gms.internal.auth.zzba
    public final void zze(Status status) {
        this.zzds.setResult(zzan.zzf(status));
    }
}
