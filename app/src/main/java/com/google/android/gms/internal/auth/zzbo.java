package com.google.android.gms.internal.auth;

import android.content.Context;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzbo extends zzbm {
    private final /* synthetic */ ProxyRequest zzec;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbo(zzbn zzbnVar, w wVar, ProxyRequest proxyRequest) {
        super(wVar);
        this.zzec = proxyRequest;
    }

    @Override // com.google.android.gms.internal.auth.zzbm
    protected final void zzd(Context context, zzbk zzbkVar) {
        zzbkVar.zzd(new zzbp(this), this.zzec);
    }
}
