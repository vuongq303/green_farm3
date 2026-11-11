package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzabi implements Runnable {
    private final /* synthetic */ zzabh zzbzk;

    zzabi(zzabh zzabhVar) {
        this.zzbzk = zzabhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzbzk.onStop();
    }
}
