package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class zzvj implements Runnable {
    private final zzuu zzbqh;

    private zzvj(zzuu zzuuVar) {
        this.zzbqh = zzuuVar;
    }

    static Runnable zza(zzuu zzuuVar) {
        return new zzvj(zzuuVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbqh.destroy();
    }
}
