package com.google.android.gms.common.api.a;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class cx implements com.google.android.gms.common.api.z {
    public final int bMc;
    public final com.google.android.gms.common.api.w bMd;
    public final com.google.android.gms.common.api.z bMe;
    private final /* synthetic */ cw bMf;

    public cx(cw cwVar, int i, com.google.android.gms.common.api.w wVar, com.google.android.gms.common.api.z zVar) {
        this.bMf = cwVar;
        this.bMc = i;
        this.bMd = wVar;
        this.bMe = zVar;
        wVar.a(this);
    }

    @Override // com.google.android.gms.common.api.z
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        String strValueOf = String.valueOf(connectionResult);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(strValueOf).length() + 27).append("beginFailureResolution for ").append(strValueOf).toString());
        this.bMf.c(connectionResult, this.bMc);
    }
}
