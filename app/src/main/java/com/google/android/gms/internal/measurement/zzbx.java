package com.google.android.gms.internal.measurement;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
final class zzbx implements Runnable {
    private final /* synthetic */ zzbw zzya;

    zzbx(zzbw zzbwVar) {
        this.zzya = zzbwVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.zzya.zzvi.zzbw().c(this);
            return;
        }
        boolean zZzef = this.zzya.zzef();
        zzbw.zza(this.zzya, 0L);
        if (zZzef) {
            this.zzya.run();
        }
    }
}
