package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzjo implements Runnable {
    private final /* synthetic */ long zzade;
    private final /* synthetic */ zzjk zzaqr;

    zzjo(zzjk zzjkVar, long j) {
        this.zzaqr = zzjkVar;
        this.zzade = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqr.zzag(this.zzade);
    }
}
