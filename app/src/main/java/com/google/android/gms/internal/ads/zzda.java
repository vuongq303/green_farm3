package com.google.android.gms.internal.ads;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
final class zzda implements ThreadFactory {
    private final ThreadFactory zzsk = Executors.defaultThreadFactory();

    zzda() {
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread threadNewThread = this.zzsk.newThread(runnable);
        threadNewThread.setName(String.valueOf(threadNewThread.getName()).concat(":"));
        return threadNewThread;
    }
}
