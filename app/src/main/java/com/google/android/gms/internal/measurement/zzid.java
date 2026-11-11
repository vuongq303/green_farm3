package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzid implements Runnable {
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ long zzaov;

    zzid(zzhm zzhmVar, long j) {
        this.zzaop = zzhmVar;
        this.zzaov = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzgh().zzaki.set(this.zzaov);
        this.zzaop.zzgg().zziq().zzg("Session timeout duration set", Long.valueOf(this.zzaov));
    }
}
