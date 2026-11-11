package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class zzapj implements Runnable {
    private final zzapg zzcyc;

    private zzapj(zzapg zzapgVar) {
        this.zzcyc = zzapgVar;
    }

    static Runnable zza(zzapg zzapgVar) {
        return new zzapj(zzapgVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcyc.stop();
    }
}
