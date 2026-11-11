package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzic implements Runnable {
    private final /* synthetic */ zzhm zzaop;
    private final /* synthetic */ long zzaov;

    zzic(zzhm zzhmVar, long j) {
        this.zzaop = zzhmVar;
        this.zzaov = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaop.zzgh().zzakh.set(this.zzaov);
        this.zzaop.zzgg().zziq().zzg("Minimum session duration set", Long.valueOf(this.zzaov));
    }
}
