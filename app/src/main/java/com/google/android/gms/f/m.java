package com.google.android.gms.f;

import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class m<TResult> implements u<TResult> {
    private final Executor bYR;

    @GuardedBy("mLock")
    private a bYS;
    private final Object mLock = new Object();

    public m(Executor executor, a aVar) {
        this.bYR = executor;
        this.bYS = aVar;
    }

    @Override // com.google.android.gms.f.u
    public final void a(f fVar) {
        if (fVar.isCanceled()) {
            synchronized (this.mLock) {
                if (this.bYS != null) {
                    this.bYR.execute(new n(this));
                }
            }
        }
    }
}
