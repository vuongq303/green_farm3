package com.google.android.gms.internal.measurement;

import android.os.Handler;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbw {
    private static volatile Handler handler;
    private final zzat zzvi;
    private final Runnable zzxy;
    private volatile long zzxz;

    zzbw(zzat zzatVar) {
        bf.checkNotNull(zzatVar);
        this.zzvi = zzatVar;
        this.zzxy = new zzbx(this);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzbw.class) {
            if (handler == null) {
                handler = new Handler(this.zzvi.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    static /* synthetic */ long zza(zzbw zzbwVar, long j) {
        zzbwVar.zzxz = 0L;
        return 0L;
    }

    public final void cancel() {
        this.zzxz = 0L;
        getHandler().removeCallbacks(this.zzxy);
    }

    public abstract void run();

    public final long zzee() {
        if (this.zzxz == 0) {
            return 0L;
        }
        return Math.abs(this.zzvi.zzbt().currentTimeMillis() - this.zzxz);
    }

    public final boolean zzef() {
        return this.zzxz != 0;
    }

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzxz = this.zzvi.zzbt().currentTimeMillis();
            if (getHandler().postDelayed(this.zzxy, j)) {
                return;
            }
            this.zzvi.zzbu().zze("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }

    public final void zzi(long j) {
        if (zzef()) {
            if (j < 0) {
                cancel();
                return;
            }
            long jAbs = j - Math.abs(this.zzvi.zzbt().currentTimeMillis() - this.zzxz);
            long j2 = jAbs >= 0 ? jAbs : 0L;
            getHandler().removeCallbacks(this.zzxy);
            if (getHandler().postDelayed(this.zzxy, j2)) {
                return;
            }
            this.zzvi.zzbu().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
        }
    }
}
