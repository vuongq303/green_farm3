package com.google.android.gms.internal.ads;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
final class zzakm implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzakk zzcru;

    zzakm(zzakk zzakkVar, Context context) {
        this.zzcru = zzakkVar;
        this.val$context = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzcru.mLock) {
            this.zzcru.zzcpq = zzakk.zzam(this.val$context);
            this.zzcru.mLock.notifyAll();
        }
    }
}
