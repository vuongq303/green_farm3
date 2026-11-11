package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzja implements Runnable {
    private final /* synthetic */ zzey zzaqh;
    private final /* synthetic */ zziz zzaqi;

    zzja(zziz zzizVar, zzey zzeyVar) {
        this.zzaqi = zzizVar;
        this.zzaqh = zzeyVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzaqi) {
            zziz.zza(this.zzaqi, false);
            if (!this.zzaqi.zzapy.isConnected()) {
                this.zzaqi.zzapy.zzgg().zzir().log("Connected to service");
                this.zzaqi.zzapy.zza(this.zzaqh);
            }
        }
    }
}
