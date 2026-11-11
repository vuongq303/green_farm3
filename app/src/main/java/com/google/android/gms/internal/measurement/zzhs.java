package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzhs implements Runnable {
    private final /* synthetic */ zzhm zzaop;

    zzhs(zzhm zzhmVar) {
        this.zzaop = zzhmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzhm zzhmVar = this.zzaop;
        zzhmVar.zzab();
        zzhmVar.zzch();
        zzhmVar.zzgg().zziq().log("Resetting analytics data (FE)");
        boolean zIsEnabled = zzhmVar.zzacr.isEnabled();
        if (!zzhmVar.zzgi().zzhi()) {
            zzhmVar.zzgh().zzh(!zIsEnabled);
        }
        zzhmVar.zzfx().resetAnalyticsData();
        zzhmVar.zzaon = zIsEnabled ? false : true;
    }
}
