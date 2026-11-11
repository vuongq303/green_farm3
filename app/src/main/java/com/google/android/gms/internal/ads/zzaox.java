package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzaox implements Runnable {
    private final /* synthetic */ zzaov zzcxf;

    zzaox(zzaov zzaovVar) {
        this.zzcxf = zzaovVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzcxf.zzcxd != null) {
            this.zzcxf.zzcxd.zzsv();
        }
    }
}
