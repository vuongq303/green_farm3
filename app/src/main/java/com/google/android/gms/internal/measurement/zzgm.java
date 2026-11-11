package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzgm implements Runnable {
    private final /* synthetic */ zzhl zzano;
    private final /* synthetic */ zzgl zzanp;

    zzgm(zzgl zzglVar, zzhl zzhlVar) {
        this.zzanp = zzglVar;
        this.zzano = zzhlVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzanp.zza(this.zzano);
        this.zzanp.start();
    }
}
