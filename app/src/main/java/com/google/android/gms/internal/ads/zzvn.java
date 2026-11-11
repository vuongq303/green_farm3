package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class zzvn implements Runnable {
    private final zzuu zzbqh;

    private zzvn(zzuu zzuuVar) {
        this.zzbqh = zzuuVar;
    }

    static Runnable zza(zzuu zzuuVar) {
        return new zzvn(zzuuVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbqh.destroy();
    }
}
