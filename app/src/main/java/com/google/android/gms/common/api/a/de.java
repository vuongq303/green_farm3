package com.google.android.gms.common.api.a;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
public final class de implements com.google.android.gms.common.api.y, com.google.android.gms.common.api.z {
    private final boolean bKy;
    private df bMs;
    public final com.google.android.gms.common.api.a<?> mApi;

    public de(com.google.android.gms.common.api.a<?> aVar, boolean z) {
        this.mApi = aVar;
        this.bKy = z;
    }

    private final void ZN() {
        com.google.android.gms.common.internal.bf.d(this.bMs, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public final void a(df dfVar) {
        this.bMs = dfVar;
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnected(Bundle bundle) {
        ZN();
        this.bMs.onConnected(bundle);
    }

    @Override // com.google.android.gms.common.api.z
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        ZN();
        this.bMs.a(connectionResult, this.mApi, this.bKy);
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnectionSuspended(int i) {
        ZN();
        this.bMs.onConnectionSuspended(i);
    }
}
