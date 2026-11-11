package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzgc implements Runnable {
    private final /* synthetic */ zzgl zzafi;
    private final /* synthetic */ zzfg zzala;

    zzgc(zzgb zzgbVar, zzgl zzglVar, zzfg zzfgVar) {
        this.zzafi = zzglVar;
        this.zzala = zzfgVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzafi.zzjm() == null) {
            this.zzala.zzil().log("Install Referrer Reporter is null");
        } else {
            this.zzafi.zzjm().zzjd();
        }
    }
}
