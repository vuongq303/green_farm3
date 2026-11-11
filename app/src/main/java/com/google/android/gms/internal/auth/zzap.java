package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.b;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzap extends zzav<b> {
    private final /* synthetic */ CredentialRequest zzdr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzap(zzao zzaoVar, w wVar, CredentialRequest credentialRequest) {
        super(wVar);
        this.zzdr = credentialRequest;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return zzan.zzf(status);
    }

    @Override // com.google.android.gms.internal.auth.zzav
    protected final void zzd(Context context, zzbc zzbcVar) {
        zzbcVar.zzd(new zzaq(this), this.zzdr);
    }
}
