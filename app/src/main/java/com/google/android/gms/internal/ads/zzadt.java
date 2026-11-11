package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzadt implements Runnable {
    private final /* synthetic */ zzadk zzccn;
    private final /* synthetic */ zzaol zzcco;

    zzadt(zzadk zzadkVar, zzaol zzaolVar) {
        this.zzccn = zzadkVar;
        this.zzcco = zzaolVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzccn.zzbzh) {
            this.zzccn.zzccj = this.zzccn.zza(this.zzccn.zzccg.zzacr, this.zzcco);
            if (this.zzccn.zzccj == null) {
                this.zzccn.zzc(0, "Could not start the ad request service.");
                zzakk.zzcrm.removeCallbacks(this.zzccn.zzbzg);
            }
        }
    }
}
