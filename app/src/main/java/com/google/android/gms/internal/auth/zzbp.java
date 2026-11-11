package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.api.proxy.ProxyResponse;

/* JADX INFO: loaded from: classes.dex */
final class zzbp extends zzbg {
    private final /* synthetic */ zzbo zzed;

    zzbp(zzbo zzboVar) {
        this.zzed = zzboVar;
    }

    @Override // com.google.android.gms.internal.auth.zzbg, com.google.android.gms.internal.auth.zzbi
    public final void zzd(ProxyResponse proxyResponse) {
        this.zzed.setResult(new zzbq(proxyResponse));
    }
}
