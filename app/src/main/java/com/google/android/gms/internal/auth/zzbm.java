package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.auth.api.f;
import com.google.android.gms.auth.api.proxy.b;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.e;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbm extends e<b, zzbh> {
    public zzbm(w wVar) {
        super(f.API, wVar);
    }

    @Override // com.google.android.gms.common.api.a.g
    protected /* synthetic */ ae createFailedResult(Status status) {
        return new zzbq(status);
    }

    @Override // com.google.android.gms.common.api.a.e
    protected /* synthetic */ void doExecute(c cVar) {
        zzbh zzbhVar = (zzbh) cVar;
        zzd(zzbhVar.getContext(), (zzbk) zzbhVar.getService());
    }

    protected abstract void zzd(Context context, zzbk zzbkVar);
}
