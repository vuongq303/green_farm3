package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzapm implements Runnable {
    private final /* synthetic */ zzapi zzcyd;
    private final /* synthetic */ boolean zzcye;

    zzapm(zzapi zzapiVar, boolean z) {
        this.zzcyd = zzapiVar;
        this.zzcye = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcyd.zza("windowVisibilityChanged", "isVisible", String.valueOf(this.zzcye));
    }
}
