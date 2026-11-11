package com.google.android.gms.ads.internal;

/* JADX INFO: loaded from: classes.dex */
final class aq implements Runnable {
    private final /* synthetic */ zzq bDA;

    aq(zzq zzqVar) {
        this.bDA = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bDA.zzb(this.bDA.bDy);
    }
}
