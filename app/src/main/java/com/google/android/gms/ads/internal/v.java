package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzov;

/* JADX INFO: loaded from: classes.dex */
final class v implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ zzov bBU;

    v(zzbc zzbcVar, zzov zzovVar) {
        this.bBN = zzbcVar;
        this.bBU = zzovVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bBN.zzvw.bDe != null) {
                this.bBN.zzvw.bDe.zza(this.bBU);
                this.bBN.a(this.bBU.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
