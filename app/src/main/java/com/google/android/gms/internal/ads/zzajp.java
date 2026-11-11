package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzajp {
    private final Object mLock;
    private volatile int zzcpx;
    private volatile long zzcpy;

    private zzajp() {
        this.mLock = new Object();
        this.zzcpx = zzajq.zzcpz;
        this.zzcpy = 0L;
    }

    /* synthetic */ zzajp(zzajo zzajoVar) {
        this();
    }

    private final void zzd(int i, int i2) {
        zzqk();
        long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
        synchronized (this.mLock) {
            if (this.zzcpx != i) {
                return;
            }
            this.zzcpx = i2;
            if (this.zzcpx == zzajq.zzcqb) {
                this.zzcpy = jCurrentTimeMillis;
            }
        }
    }

    private final void zzqk() {
        long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
        synchronized (this.mLock) {
            if (this.zzcpx == zzajq.zzcqb) {
                if (this.zzcpy + ((Long) zzkb.zzik().zzd(zznk.zzbfn)).longValue() <= jCurrentTimeMillis) {
                    this.zzcpx = zzajq.zzcpz;
                }
            }
        }
    }

    public final void zzaa(boolean z) {
        if (z) {
            zzd(zzajq.zzcpz, zzajq.zzcqa);
        } else {
            zzd(zzajq.zzcqa, zzajq.zzcpz);
        }
    }

    public final boolean zzqa() {
        zzqk();
        return this.zzcpx == zzajq.zzcqa;
    }

    public final boolean zzqb() {
        zzqk();
        return this.zzcpx == zzajq.zzcqb;
    }

    public final void zzqc() {
        zzd(zzajq.zzcqa, zzajq.zzcqb);
    }
}
