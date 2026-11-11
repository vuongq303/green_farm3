package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzqs;

/* JADX INFO: loaded from: classes.dex */
final class y implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ zzqs bBX;

    y(zzbc zzbcVar, zzqs zzqsVar) {
        this.bBN = zzbcVar;
        this.bBX = zzqsVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.bBN.zzvw.bDg.get(this.bBX.getCustomTemplateId()).zzb(this.bBX);
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
