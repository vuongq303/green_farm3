package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzapb implements Runnable {
    private final /* synthetic */ zzaov zzcxf;
    private final /* synthetic */ int zzcxi;
    private final /* synthetic */ int zzcxj;

    zzapb(zzaov zzaovVar, int i, int i2) {
        this.zzcxf = zzaovVar;
        this.zzcxi = i;
        this.zzcxj = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzcxf.zzcxd != null) {
            this.zzcxf.zzcxd.zzf(this.zzcxi, this.zzcxj);
        }
    }
}
