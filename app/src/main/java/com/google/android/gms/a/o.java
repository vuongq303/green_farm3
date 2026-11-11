package com.google.android.gms.a;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class o extends ThreadPoolExecutor {
    final /* synthetic */ n bEJ;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o(n nVar) {
        super(1, 1, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
        this.bEJ = nVar;
        setThreadFactory(new p(null));
        allowCoreThreadTimeOut(true);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new s(this, runnable, t);
    }
}
