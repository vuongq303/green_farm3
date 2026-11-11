package com.google.android.gms.f;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class w implements Executor {
    w() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
