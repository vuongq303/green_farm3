package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
final class zziu implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ boolean zzaqb = true;
    private final /* synthetic */ boolean zzaqc;
    private final /* synthetic */ zzef zzaqd;
    private final /* synthetic */ zzef zzaqe;

    zziu(zzil zzilVar, boolean z, boolean z2, zzef zzefVar, zzec zzecVar, zzef zzefVar2) {
        this.zzapy = zzilVar;
        this.zzaqc = z2;
        this.zzaqd = zzefVar;
        this.zzanq = zzecVar;
        this.zzaqe = zzefVar2;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzaqb) {
            this.zzapy.zza(zzeyVar, this.zzaqc ? null : this.zzaqd, this.zzanq);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzaqe.packageName)) {
                    zzeyVar.zza(this.zzaqd, this.zzanq);
                } else {
                    zzeyVar.zzb(this.zzaqd);
                }
            } catch (RemoteException e) {
                this.zzapy.zzgg().zzil().zzg("Failed to send conditional user property to the service", e);
            }
        }
        this.zzapy.zzcu();
    }
}
