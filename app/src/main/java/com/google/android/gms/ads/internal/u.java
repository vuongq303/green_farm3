package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzoo;

/* JADX INFO: loaded from: classes.dex */
final class u implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ zzoo bBT;

    u(zzbc zzbcVar, zzoo zzooVar) {
        this.bBN = zzbcVar;
        this.bBT = zzooVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.bBN.zzvw.bDc != null) {
                this.bBN.zzvw.bDc.zza(this.bBT);
                this.bBN.a(this.bBT.zzka());
            }
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
