package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.auth.api.proxy.a;
import com.google.android.gms.auth.api.proxy.b;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzbn implements a {
    public final aa<b> performProxyRequest(w wVar, ProxyRequest proxyRequest) {
        bf.checkNotNull(wVar);
        bf.checkNotNull(proxyRequest);
        return wVar.b(new zzbo(this, wVar, proxyRequest));
    }
}
