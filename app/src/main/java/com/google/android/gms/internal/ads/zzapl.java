package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzapl implements Runnable {
    private final /* synthetic */ zzapi zzcyd;

    zzapl(zzapi zzapiVar) {
        this.zzcyd = zzapiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcyd.zza("surfaceDestroyed", new String[0]);
    }
}
