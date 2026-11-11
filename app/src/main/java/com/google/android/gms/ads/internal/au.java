package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzqs;

/* JADX INFO: loaded from: classes.dex */
final class au implements Runnable {
    private final /* synthetic */ zzqs bBX;
    private final /* synthetic */ zzq bDA;

    au(zzq zzqVar, zzqs zzqsVar) {
        this.bDA = zzqVar;
        this.bBX = zzqsVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.bDA.zzvw.bDg.get(this.bBX.getCustomTemplateId()).zzb(this.bBX);
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
