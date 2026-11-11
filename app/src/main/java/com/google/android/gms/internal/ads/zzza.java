package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.ads.b;

/* JADX INFO: loaded from: classes.dex */
final class zzza implements Runnable {
    private final /* synthetic */ zzyq zzbvd;
    private final /* synthetic */ b zzbve;

    zzza(zzyq zzyqVar, b bVar) {
        this.zzbvd = zzyqVar;
        this.zzbve = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzbvd.zzbuu.onAdFailedToLoad(zzzc.zza(this.zzbve));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }
}
