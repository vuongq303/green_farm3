package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
final class zzit implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ String zzaoc;
    private final /* synthetic */ zzeu zzaod;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ boolean zzaqb = true;
    private final /* synthetic */ boolean zzaqc;

    zzit(zzil zzilVar, boolean z, boolean z2, zzeu zzeuVar, zzec zzecVar, String str) {
        this.zzapy = zzilVar;
        this.zzaqc = z2;
        this.zzaod = zzeuVar;
        this.zzanq = zzecVar;
        this.zzaoc = str;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzaqb) {
            this.zzapy.zza(zzeyVar, this.zzaqc ? null : this.zzaod, this.zzanq);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzaoc)) {
                    zzeyVar.zza(this.zzaod, this.zzanq);
                } else {
                    zzeyVar.zza(this.zzaod, this.zzaoc, this.zzapy.zzgg().zzit());
                }
            } catch (RemoteException e) {
                this.zzapy.zzgg().zzil().zzg("Failed to send event to the service", e);
            }
        }
        this.zzapy.zzcu();
    }
}
