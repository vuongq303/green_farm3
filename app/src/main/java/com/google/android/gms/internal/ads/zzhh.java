package com.google.android.gms.internal.ads;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.g;

/* JADX INFO: loaded from: classes.dex */
final class zzhh implements g {
    private final /* synthetic */ zzhd zzajt;

    zzhh(zzhd zzhdVar) {
        this.zzajt = zzhdVar;
    }

    @Override // com.google.android.gms.common.internal.g
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        synchronized (this.zzajt.mLock) {
            this.zzajt.zzajs = null;
            if (this.zzajt.zzajr != null) {
                zzhd.zza(this.zzajt, (zzhk) null);
            }
            this.zzajt.mLock.notifyAll();
        }
    }
}
