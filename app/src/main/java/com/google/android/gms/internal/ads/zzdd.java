package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzdd implements Runnable {
    private final /* synthetic */ zzcz zzsl;

    zzdd(zzcz zzczVar) {
        this.zzsl = zzczVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zznk.initialize(this.zzsl.zzrt);
    }
}
