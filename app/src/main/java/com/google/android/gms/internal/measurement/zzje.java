package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzje implements Runnable {
    private final /* synthetic */ zziz zzaqi;

    zzje(zziz zzizVar) {
        this.zzaqi = zzizVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzil.zza(this.zzaqi.zzapy, (zzey) null);
        this.zzaqi.zzapy.zzko();
    }
}
