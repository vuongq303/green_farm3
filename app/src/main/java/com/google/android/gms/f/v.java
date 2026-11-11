package com.google.android.gms.f;

import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class v<TResult> {

    @GuardedBy("mLock")
    private Queue<u<TResult>> bZb;

    @GuardedBy("mLock")
    private boolean bZc;
    private final Object mLock = new Object();

    v() {
    }

    public final void a(u<TResult> uVar) {
        synchronized (this.mLock) {
            if (this.bZb == null) {
                this.bZb = new ArrayDeque();
            }
            this.bZb.add(uVar);
        }
    }

    public final void d(f<TResult> fVar) {
        u<TResult> uVarPoll;
        synchronized (this.mLock) {
            if (this.bZb == null || this.bZc) {
                return;
            }
            this.bZc = true;
            while (true) {
                synchronized (this.mLock) {
                    uVarPoll = this.bZb.poll();
                    if (uVarPoll == null) {
                        this.bZc = false;
                        return;
                    }
                }
                uVarPoll.a(fVar);
            }
        }
    }
}
