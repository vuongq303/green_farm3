package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zzin implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzil zzapy;

    zzin(zzil zzilVar, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzeyVar.zzd(this.zzanq);
        } catch (RemoteException e) {
            this.zzapy.zzgg().zzil().zzg("Failed to reset data on the service", e);
        }
        this.zzapy.zzcu();
    }
}
