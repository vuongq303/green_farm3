package com.google.android.gms.internal.ads;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.g;

/* JADX INFO: loaded from: classes.dex */
final class zzsr implements g {
    private final /* synthetic */ zzsm zzbnn;
    private final /* synthetic */ zzaoj zzbno;

    zzsr(zzsm zzsmVar, zzaoj zzaojVar) {
        this.zzbnn = zzsmVar;
        this.zzbno = zzaojVar;
    }

    @Override // com.google.android.gms.common.internal.g
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        synchronized (this.zzbnn.mLock) {
            this.zzbno.setException(new RuntimeException("Connection failed."));
        }
    }
}
