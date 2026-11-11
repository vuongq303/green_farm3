package com.google.android.gms.internal.ads;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class zzgi implements Runnable {
    private final /* synthetic */ zzgh zzahx;

    zzgi(zzgh zzghVar) {
        this.zzahx = zzghVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzahx.mLock) {
            if (this.zzahx.zzahr && this.zzahx.zzahs) {
                zzgh.zza(this.zzahx, false);
                zzakb.zzck("App went background");
                Iterator it = this.zzahx.zzaht.iterator();
                while (it.hasNext()) {
                    try {
                        ((zzgj) it.next()).zzh(false);
                    } catch (Exception e) {
                        zzane.zzb("", e);
                    }
                }
            } else {
                zzakb.zzck("App is still foreground");
            }
        }
    }
}
