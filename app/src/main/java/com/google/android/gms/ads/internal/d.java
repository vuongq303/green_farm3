package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzjj;

/* JADX INFO: loaded from: classes.dex */
final class d implements Runnable {
    private final /* synthetic */ zzah bBf;
    private final /* synthetic */ zzjj zzyh;

    d(zzah zzahVar, zzjj zzjjVar) {
        this.bBf = zzahVar;
        this.zzyh = zzjjVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bBf.mLock) {
            if (this.bBf.WV()) {
                this.bBf.a(this.zzyh);
            } else {
                this.bBf.a(this.zzyh, 1);
            }
        }
    }
}
