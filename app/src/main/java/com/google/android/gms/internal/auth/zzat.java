package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzat extends zzav<Status> {
    zzat(zzao zzaoVar, w wVar) {
        super(wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return status;
    }

    @Override // com.google.android.gms.internal.auth.zzav
    protected final void zzd(Context context, zzbc zzbcVar) {
        zzbcVar.zzd(new zzau(this));
    }
}
