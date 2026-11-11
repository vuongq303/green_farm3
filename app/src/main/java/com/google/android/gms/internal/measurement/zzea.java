package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzea implements Runnable {
    private final /* synthetic */ long zzade;
    private final /* synthetic */ zzdx zzadf;

    zzea(zzdx zzdxVar, long j) {
        this.zzadf = zzdxVar;
        this.zzade = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzadf.zzl(this.zzade);
    }
}
