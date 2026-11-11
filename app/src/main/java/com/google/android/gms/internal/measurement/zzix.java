package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzix implements Runnable {
    private final /* synthetic */ zzec zzanq;
    private final /* synthetic */ zzjs zzaoe;
    private final /* synthetic */ zzil zzapy;
    private final /* synthetic */ boolean zzaqc;

    zzix(zzil zzilVar, boolean z, zzjs zzjsVar, zzec zzecVar) {
        this.zzapy = zzilVar;
        this.zzaqc = z;
        this.zzaoe = zzjsVar;
        this.zzanq = zzecVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzey zzeyVar = this.zzapy.zzaps;
        if (zzeyVar == null) {
            this.zzapy.zzgg().zzil().log("Discarding data. Failed to set user attribute");
        } else {
            this.zzapy.zza(zzeyVar, this.zzaqc ? null : this.zzaoe, this.zzanq);
            this.zzapy.zzcu();
        }
    }
}
