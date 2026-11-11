package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzjn implements Runnable {
    private final /* synthetic */ long zzade;
    private final /* synthetic */ zzjk zzaqr;

    zzjn(zzjk zzjkVar, long j) {
        this.zzaqr = zzjkVar;
        this.zzade = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqr.zzaf(this.zzade);
    }
}
