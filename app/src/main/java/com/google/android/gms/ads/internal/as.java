package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzoo;

/* JADX INFO: loaded from: classes.dex */
final class as implements Runnable {
    private final /* synthetic */ zzoo bBT;
    private final /* synthetic */ zzq bDA;

    as(zzq zzqVar, zzoo zzooVar) {
        this.bDA = zzqVar;
        this.bBT = zzooVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bDA.zzvw.bDc != null) {
                this.bDA.zzvw.bDc.zza(this.bBT);
                this.bDA.a(this.bBT.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
