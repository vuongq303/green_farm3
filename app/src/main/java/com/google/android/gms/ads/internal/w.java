package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzoq;

/* JADX INFO: loaded from: classes.dex */
final class w implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ zzoq bBV;

    w(zzbc zzbcVar, zzoq zzoqVar) {
        this.bBN = zzbcVar;
        this.bBV = zzoqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bBN.zzvw.bDd != null) {
                this.bBN.zzvw.bDd.zza(this.bBV);
                this.bBN.a(this.bBV.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
