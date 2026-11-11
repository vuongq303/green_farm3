package com.google.android.gms.f;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class q<TResult> implements u<TResult> {
    private final Executor bYR;

    @GuardedBy("mLock")
    private c bYX;
    private final Object mLock = new Object();

    public q(Executor executor, c cVar) {
        this.bYR = executor;
        this.bYX = cVar;
    }

    @Override // com.google.android.gms.f.u
    public final void a(f<TResult> fVar) {
        if (fVar.agq() || fVar.isCanceled()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.bYX != null) {
                this.bYR.execute(new r(this, fVar));
            }
        }
    }
}
