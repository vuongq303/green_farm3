package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
final class zzgi implements Thread.UncaughtExceptionHandler {
    private final String zzalw;
    private final /* synthetic */ zzgg zzalx;

    public zzgi(zzgg zzggVar, String str) {
        this.zzalx = zzggVar;
        bf.checkNotNull(str);
        this.zzalw = str;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzalx.zzgg().zzil().zzg(this.zzalw, th);
    }
}
