package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzar extends zzav<Status> {
    private final /* synthetic */ Credential zzdt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzar(zzao zzaoVar, w wVar, Credential credential) {
        super(wVar);
        this.zzdt = credential;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return status;
    }

    @Override // com.google.android.gms.internal.auth.zzav
    protected final void zzd(Context context, zzbc zzbcVar) {
        zzbcVar.zzd(new zzau(this), new zzbe(this.zzdt));
    }
}
