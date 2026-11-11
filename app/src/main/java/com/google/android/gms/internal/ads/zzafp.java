package com.google.android.gms.internal.ads;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zzafp implements Runnable {
    private final /* synthetic */ zzaef zzchk;
    private final /* synthetic */ zzaeq zzchl;
    private final /* synthetic */ zzafn zzchm;

    zzafp(zzafn zzafnVar, zzaef zzaefVar, zzaeq zzaeqVar) {
        this.zzchm = zzafnVar;
        this.zzchk = zzaefVar;
        this.zzchl = zzaeqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzaej zzaejVar;
        try {
            zzaejVar = this.zzchm.zzb(this.zzchk);
        } catch (Exception e) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "AdRequestServiceImpl.loadAdAsync");
            zzakb.zzc("Could not fetch ad response due to an Exception.", e);
            zzaejVar = null;
        }
        if (zzaejVar == null) {
            zzaejVar = new zzaej(0);
        }
        try {
            this.zzchl.zza(zzaejVar);
        } catch (RemoteException e2) {
            zzakb.zzc("Fail to forward ad response.", e2);
        }
    }
}
