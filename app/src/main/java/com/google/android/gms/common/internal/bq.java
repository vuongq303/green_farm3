package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class bq implements g {
    private final /* synthetic */ com.google.android.gms.common.api.z bOF;

    bq(com.google.android.gms.common.api.z zVar) {
        this.bOF = zVar;
    }

    @Override // com.google.android.gms.common.internal.g
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.bOF.onConnectionFailed(connectionResult);
    }
}
