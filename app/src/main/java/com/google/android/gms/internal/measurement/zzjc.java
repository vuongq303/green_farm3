package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzjc implements Runnable {
    private final /* synthetic */ zziz zzaqi;
    private final /* synthetic */ zzey zzaqj;

    zzjc(zziz zzizVar, zzey zzeyVar) {
        this.zzaqi = zzizVar;
        this.zzaqj = zzeyVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzaqi) {
            zziz.zza(this.zzaqi, false);
            if (!this.zzaqi.zzapy.isConnected()) {
                this.zzaqi.zzapy.zzgg().zziq().log("Connected to remote service");
                this.zzaqi.zzapy.zza(this.zzaqj);
            }
        }
    }
}
