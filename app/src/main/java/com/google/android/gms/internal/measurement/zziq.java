package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zziq implements Runnable {
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ zzig zzaqa;

    zziq(zzil zzilVar, zzig zzigVar) {
        this.zzapy = zzilVar;
        this.zzaqa = zzigVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzaqa == null) {
                zzeyVar.zza(0L, (String) null, (String) null, this.zzapy.getContext().getPackageName());
            } else {
                zzeyVar.zza(this.zzaqa.zzapb, this.zzaqa.zzug, this.zzaqa.zzapa, this.zzapy.getContext().getPackageName());
            }
            this.zzapy.zzcu();
        } catch (RemoteException e) {
            this.zzapy.zzgg().zzil().zzg("Failed to send current screen to the service", e);
        }
    }
}
