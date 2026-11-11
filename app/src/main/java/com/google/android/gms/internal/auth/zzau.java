package com.google.android.gms.internal.auth;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.f;

/* JADX INFO: loaded from: classes.dex */
final class zzau extends zzam {
    private f<Status> zzdu;

    zzau(f<Status> fVar) {
        this.zzdu = fVar;
    }

    @Override // com.google.android.gms.internal.auth.zzam, com.google.android.gms.internal.auth.zzba
    public final void zze(Status status) {
        this.zzdu.setResult(status);
    }
}
