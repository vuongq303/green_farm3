package com.google.android.gms.internal.auth;

import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.auth.api.proxy.b;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzbq implements b {
    private Status mStatus;
    private ProxyResponse zzee;

    public zzbq(ProxyResponse proxyResponse) {
        this.zzee = proxyResponse;
        this.mStatus = Status.bIO;
    }

    public zzbq(Status status) {
        this.mStatus = status;
    }

    public final ProxyResponse getResponse() {
        return this.zzee;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.mStatus;
    }
}
