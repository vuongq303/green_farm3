package com.google.android.gms.f;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class o<TResult> implements u<TResult> {
    private final Executor bYR;

    @GuardedBy("mLock")
    private b<TResult> bYU;
    private final Object mLock = new Object();

    public o(Executor executor, b<TResult> bVar) {
        this.bYR = executor;
        this.bYU = bVar;
    }

    @Override // com.google.android.gms.f.u
    public final void a(f<TResult> fVar) {
        synchronized (this.mLock) {
            if (this.bYU == null) {
                return;
            }
            this.bYR.execute(new p(this, fVar));
        }
    }
}
