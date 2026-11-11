package com.google.android.gms.f;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class k implements l {
    private final CountDownLatch zzfd;

    private k() {
        this.zzfd = new CountDownLatch(1);
    }

    /* synthetic */ k(y yVar) {
        this();
    }

    public final void await() throws InterruptedException {
        this.zzfd.await();
    }

    public final boolean await(long j, TimeUnit timeUnit) {
        return this.zzfd.await(j, timeUnit);
    }

    @Override // com.google.android.gms.f.c
    public final void f(Exception exc) {
        this.zzfd.countDown();
    }

    @Override // com.google.android.gms.f.a
    public final void onCanceled() {
        this.zzfd.countDown();
    }

    @Override // com.google.android.gms.f.d
    public final void onSuccess(Object obj) {
        this.zzfd.countDown();
    }
}
