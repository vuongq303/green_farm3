package com.google.android.gms.common.d.a;

import com.google.android.gms.common.internal.bf;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class c implements ThreadFactory {
    private final ThreadFactory bQT;
    private final String bQU;
    private final AtomicInteger bQV;
    private final int priority;

    public c(String str) {
        this(str, 0);
    }

    public c(String str, int i) {
        this.bQV = new AtomicInteger();
        this.bQT = Executors.defaultThreadFactory();
        this.bQU = (String) bf.d(str, (Object) "Name must not be null");
        this.priority = i;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread threadNewThread = this.bQT.newThread(new d(runnable, this.priority));
        String str = this.bQU;
        threadNewThread.setName(new StringBuilder(String.valueOf(str).length() + 13).append(str).append("[").append(this.bQV.getAndIncrement()).append("]").toString());
        return threadNewThread;
    }
}
