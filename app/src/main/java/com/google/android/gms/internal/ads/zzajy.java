package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzajy implements Runnable {
    private final /* synthetic */ zzajx zzcqt;

    zzajy(zzajx zzajxVar) {
        this.zzcqt = zzajxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcqt.zzcqr = Thread.currentThread();
        this.zzcqt.zzdn();
    }
}
