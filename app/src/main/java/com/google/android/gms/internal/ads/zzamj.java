package com.google.android.gms.internal.ads;

import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzamj {
    private long zzctx;

    @GuardedBy("mLock")
    private long zzcty = Long.MIN_VALUE;
    private Object mLock = new Object();

    public zzamj(long j) {
        this.zzctx = j;
    }

    public final boolean tryAcquire() {
        boolean z;
        synchronized (this.mLock) {
            long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
            if (this.zzcty + this.zzctx > jElapsedRealtime) {
                z = false;
            } else {
                this.zzcty = jElapsedRealtime;
                z = true;
            }
        }
        return z;
    }
}
