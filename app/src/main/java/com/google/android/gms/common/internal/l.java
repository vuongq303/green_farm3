package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
public class l implements i {
    private final /* synthetic */ e bNy;

    public l(e eVar) {
        this.bNy = eVar;
    }

    @Override // com.google.android.gms.common.internal.i
    public void d(ConnectionResult connectionResult) {
        if (connectionResult.Sp()) {
            this.bNy.getRemoteService(null, this.bNy.getScopes());
        } else if (this.bNy.zzro != null) {
            this.bNy.zzro.onConnectionFailed(connectionResult);
        }
    }
}
