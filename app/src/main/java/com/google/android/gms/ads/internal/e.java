package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzjj;

/* JADX INFO: loaded from: classes.dex */
final class e implements Runnable {
    private final /* synthetic */ zzah bBf;
    private final /* synthetic */ int bBg;
    private final /* synthetic */ zzjj zzyh;

    e(zzah zzahVar, zzjj zzjjVar, int i) {
        this.bBf = zzahVar;
        this.zzyh = zzjjVar;
        this.bBg = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.bBf.mLock) {
            this.bBf.a(this.zzyh, this.bBg);
        }
    }
}
