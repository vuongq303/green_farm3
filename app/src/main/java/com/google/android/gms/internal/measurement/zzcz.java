package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
final class zzcz {
    private long startTime;
    private final e zzrj;

    public zzcz(e eVar) {
        bf.checkNotNull(eVar);
        this.zzrj = eVar;
    }

    public zzcz(e eVar, long j) {
        bf.checkNotNull(eVar);
        this.zzrj = eVar;
        this.startTime = j;
    }

    public final void clear() {
        this.startTime = 0L;
    }

    public final void start() {
        this.startTime = this.zzrj.elapsedRealtime();
    }

    public final boolean zzj(long j) {
        return this.startTime == 0 || this.zzrj.elapsedRealtime() - this.startTime > j;
    }
}
