package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzdy implements Runnable {
    private final /* synthetic */ String zzadd;
    private final /* synthetic */ long zzade;
    private final /* synthetic */ zzdx zzadf;

    zzdy(zzdx zzdxVar, String str, long j) {
        this.zzadf = zzdxVar;
        this.zzadd = str;
        this.zzade = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzadf.zza(this.zzadd, this.zzade);
    }
}
