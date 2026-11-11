package com.google.android.gms.a;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
final class p implements ThreadFactory {
    private static final AtomicInteger bEK = new AtomicInteger();

    private p() {
    }

    /* synthetic */ p(r rVar) {
        this();
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return new q(runnable, new StringBuilder(23).append("measurement-").append(bEK.incrementAndGet()).toString());
    }
}
