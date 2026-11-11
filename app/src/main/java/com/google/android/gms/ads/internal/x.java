package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzos;

/* JADX INFO: loaded from: classes.dex */
final class x implements Runnable {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ String bBW;
    private final /* synthetic */ zzajh zzaam;

    x(zzbc zzbcVar, String str, zzajh zzajhVar) {
        this.bBN = zzbcVar;
        this.bBW = str;
        this.zzaam = zzajhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.bBN.zzvw.bDg.get(this.bBW).zzb((zzos) this.zzaam.zzcoj);
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
