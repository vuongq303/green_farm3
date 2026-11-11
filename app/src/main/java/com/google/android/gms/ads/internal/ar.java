package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzov;

/* JADX INFO: loaded from: classes.dex */
final class ar implements Runnable {
    private final /* synthetic */ zzov bBU;
    private final /* synthetic */ zzq bDA;

    ar(zzq zzqVar, zzov zzovVar) {
        this.bDA = zzqVar;
        this.bBU = zzovVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bDA.zzvw.bDe != null) {
                this.bDA.zzvw.bDe.zza(this.bBU);
                this.bDA.a(this.bBU.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
