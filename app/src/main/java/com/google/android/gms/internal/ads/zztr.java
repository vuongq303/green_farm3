package com.google.android.gms.internal.ads;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final class zztr implements Runnable {
    private final /* synthetic */ zzts zzbob;
    private final /* synthetic */ zztt zzboc;

    zztr(zzst zzstVar, zzts zztsVar, zztt zzttVar) {
        this.zzbob = zztsVar;
        this.zzboc = zzttVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzbob.zzb(this.zzboc);
        } catch (RemoteException e) {
            zzakb.zzc("Could not propagate interstitial ad event.", e);
        }
    }
}
