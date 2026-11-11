package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zzis implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzil zzapy;

    zzis(zzil zzilVar, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzeyVar.zzb(this.zzanq);
            this.zzapy.zzcu();
        } catch (RemoteException e) {
            this.zzapy.zzgg().zzil().zzg("Failed to send measurementEnabled to the service", e);
        }
    }
}
