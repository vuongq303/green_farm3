package com.google.android.gms.f;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class s<TResult> implements u<TResult> {
    private final Executor bYR;

    @GuardedBy("mLock")
    private d<? super TResult> bYZ;
    private final Object mLock = new Object();

    public s(Executor executor, d<? super TResult> dVar) {
        this.bYR = executor;
        this.bYZ = dVar;
    }

    @Override // com.google.android.gms.f.u
    public final void a(f<TResult> fVar) {
        if (fVar.agq()) {
            synchronized (this.mLock) {
                if (this.bYZ != null) {
                    this.bYR.execute(new t(this, fVar));
                }
            }
        }
    }
}
