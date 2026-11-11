package com.google.android.gms.plus;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.m;
import com.google.android.gms.common.api.w;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.plus.zze;
import com.google.android.gms.internal.plus.zzh;
import com.google.android.gms.internal.plus.zzi;
import com.google.android.gms.internal.plus.zzj;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class d {
    public static final m<com.google.android.gms.plus.internal.f> CLIENT_KEY = new m<>();
    private static final com.google.android.gms.common.api.b<com.google.android.gms.plus.internal.f, e> CLIENT_BUILDER = new k();

    @Deprecated
    public static final com.google.android.gms.common.api.a<e> API = new com.google.android.gms.common.api.a<>("Plus.API", CLIENT_BUILDER, CLIENT_KEY);

    @Deprecated
    public static final Scope bXM = new Scope("https://www.googleapis.com/auth/plus.login");

    @Deprecated
    public static final Scope bXN = new Scope("https://www.googleapis.com/auth/plus.me");

    @Deprecated
    public static final b bXO = new zzj();

    @Deprecated
    public static final a bXP = new zze();

    @Deprecated
    private static final j bXQ = new zzi();
    private static final i bXR = new zzh();

    public static com.google.android.gms.plus.internal.f c(w wVar, boolean z) {
        bf.b(wVar != null, "GoogleApiClient parameter is required.");
        bf.a(wVar.isConnected(), "GoogleApiClient must be connected.");
        bf.a(wVar.a(API), "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        boolean zB = wVar.b(API);
        if (z && !zB) {
            throw new IllegalStateException("GoogleApiClient has an optional Plus.API and is not connected to Plus. Use GoogleApiClient.hasConnectedApi(Plus.API) to guard this call.");
        }
        if (zB) {
            return (com.google.android.gms.plus.internal.f) wVar.a(CLIENT_KEY);
        }
        return null;
    }
}
