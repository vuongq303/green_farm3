package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzoq;

/* JADX INFO: loaded from: classes.dex */
final class at implements Runnable {
    private final /* synthetic */ zzoq bBV;
    private final /* synthetic */ zzq bDA;

    at(zzq zzqVar, zzoq zzoqVar) {
        this.bDA = zzqVar;
        this.bBV = zzoqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bDA.zzvw.bDd != null) {
                this.bDA.zzvw.bDd.zza(this.bBV);
                this.bDA.a(this.bBV.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
