package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.auth.api.a;
import com.google.android.gms.common.api.a.e;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
abstract class zzav<R extends ae> extends e<R, zzax> {
    zzav(w wVar) {
        super(a.bFf, wVar);
    }

    @Override // com.google.android.gms.common.api.a.e
    protected /* synthetic */ void doExecute(c cVar) {
        zzax zzaxVar = (zzax) cVar;
        zzd(zzaxVar.getContext(), (zzbc) zzaxVar.getService());
    }

    protected abstract void zzd(Context context, zzbc zzbcVar);
}
