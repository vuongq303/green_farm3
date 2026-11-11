package com.google.android.gms.common.d.a;

import com.google.android.gms.common.internal.bf;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
public class b implements ThreadFactory {
    private final ThreadFactory bQT;
    private final String name;
    private final int priority;

    public b(String str) {
        this(str, 0);
    }

    public b(String str, int i) {
        this.bQT = Executors.defaultThreadFactory();
        this.name = (String) bf.d(str, (Object) "Name must not be null");
        this.priority = i;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread threadNewThread = this.bQT.newThread(new d(runnable, this.priority));
        threadNewThread.setName(this.name);
        return threadNewThread;
    }
}
