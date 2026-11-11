package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzapk implements Runnable {
    private final /* synthetic */ zzapi zzcyd;

    zzapk(zzapi zzapiVar) {
        this.zzcyd = zzapiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcyd.zza("surfaceCreated", new String[0]);
    }
}
