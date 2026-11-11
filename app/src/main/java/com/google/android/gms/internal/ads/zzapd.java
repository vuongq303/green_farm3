package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzapd implements Runnable {
    private final /* synthetic */ zzaov zzcxf;

    zzapd(zzaov zzaovVar) {
        this.zzcxf = zzaovVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzcxf.zzcxd != null) {
            this.zzcxf.zzcxd.zzsw();
        }
    }
}
