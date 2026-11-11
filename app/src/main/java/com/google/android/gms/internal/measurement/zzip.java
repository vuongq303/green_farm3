package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zzip implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzil zzapy;

    zzip(zzil zzilVar, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzeyVar.zza(this.zzanq);
            this.zzapy.zza(zzeyVar, null, this.zzanq);
            this.zzapy.zzcu();
        } catch (RemoteException e) {
            this.zzapy.zzgg().zzil().zzg("Failed to send app launch to the service", e);
        }
    }
}
