package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzhe implements Runnable {
    private final /* synthetic */ zzhd zzajt;

    zzhe(zzhd zzhdVar) {
        this.zzajt = zzhdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzajt.disconnect();
    }
}
