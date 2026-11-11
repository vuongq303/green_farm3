package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class j extends ar {
    private final int bNA;
    private e bNz;

    public j(e eVar, int i) {
        this.bNz = eVar;
        this.bNA = i;
    }

    @Override // com.google.android.gms.common.internal.aq
    public final void a(int i, IBinder iBinder, Bundle bundle) {
        bf.d(this.bNz, "onPostInitComplete can be called only once per call to getRemoteService");
        this.bNz.onPostInitHandler(i, iBinder, bundle, this.bNA);
        this.bNz = null;
    }

    @Override // com.google.android.gms.common.internal.aq
    public final void a(int i, IBinder iBinder, ConnectionInfo connectionInfo) {
        bf.d(this.bNz, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
        bf.checkNotNull(connectionInfo);
        this.bNz.zza(connectionInfo);
        a(i, iBinder, connectionInfo.aao());
    }

    @Override // com.google.android.gms.common.internal.aq
    public final void d(int i, Bundle bundle) {
        Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
    }
}
