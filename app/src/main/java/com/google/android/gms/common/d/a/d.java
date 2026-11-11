package com.google.android.gms.common.d.a;

import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
final class d implements Runnable {
    private final Runnable bQW;
    private final int priority;

    public d(Runnable runnable, int i) {
        this.bQW = runnable;
        this.priority = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(this.priority);
        this.bQW.run();
    }
}
