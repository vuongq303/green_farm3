package com.google.android.gms.a;

import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
final class q extends Thread {
    q(Runnable runnable, String str) {
        super(runnable, str);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(10);
        super.run();
    }
}
