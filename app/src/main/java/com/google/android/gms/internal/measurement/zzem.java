package com.google.android.gms.internal.measurement;

import android.os.Handler;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
abstract class zzem {
    private static volatile Handler handler;
    private boolean enabled;
    private final zzgl zzacr;
    private final Runnable zzxy;
    private volatile long zzxz;

    zzem(zzgl zzglVar) {
        bf.checkNotNull(zzglVar);
        this.zzacr = zzglVar;
        this.enabled = true;
        this.zzxy = new zzen(this, zzglVar);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzem.class) {
            if (handler == null) {
                handler = new Handler(this.zzacr.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    static /* synthetic */ long zza(zzem zzemVar, long j) {
        zzemVar.zzxz = 0L;
        return 0L;
    }

    public final void cancel() {
        this.zzxz = 0L;
        getHandler().removeCallbacks(this.zzxy);
    }

    public abstract void run();

    public final boolean zzef() {
        return this.zzxz != 0;
    }

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzxz = this.zzacr.zzbt().currentTimeMillis();
            if (getHandler().postDelayed(this.zzxy, j)) {
                return;
            }
            this.zzacr.zzgg().zzil().zzg("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }
}
