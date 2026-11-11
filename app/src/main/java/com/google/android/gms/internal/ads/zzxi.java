package com.google.android.gms.internal.ads;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class zzxi implements Callable<zzxe> {
    private final /* synthetic */ zzxb zzbuj;
    private final /* synthetic */ zzxh zzbuk;

    zzxi(zzxh zzxhVar, zzxb zzxbVar) {
        this.zzbuk = zzxhVar;
        this.zzbuj = zzxbVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: zzmn, reason: merged with bridge method [inline-methods] */
    public final zzxe call() {
        synchronized (this.zzbuk.mLock) {
            if (this.zzbuk.zzbuf) {
                return null;
            }
            return this.zzbuj.zza(this.zzbuk.mStartTime, this.zzbuk.zzbud);
        }
    }
}
