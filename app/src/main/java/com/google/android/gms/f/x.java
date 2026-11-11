package com.google.android.gms.f;

import com.google.android.gms.common.internal.bf;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
final class x<TResult> extends f<TResult> {

    @GuardedBy("mLock")
    private boolean bZe;

    @GuardedBy("mLock")
    private TResult bZf;

    @GuardedBy("mLock")
    private Exception bZg;
    private volatile boolean zzfi;
    private final Object mLock = new Object();
    private final v<TResult> bZd = new v<>();

    x() {
    }

    @GuardedBy("mLock")
    private final void WZ() {
        bf.a(this.bZe, "Task is not yet complete");
    }

    @GuardedBy("mLock")
    private final void agr() {
        bf.a(!this.bZe, "Task is already complete");
    }

    @GuardedBy("mLock")
    private final void ags() {
        if (this.zzfi) {
            throw new CancellationException("Task is already canceled.");
        }
    }

    private final void zzdt() {
        synchronized (this.mLock) {
            if (this.bZe) {
                this.bZd.d(this);
            }
        }
    }

    @Override // com.google.android.gms.f.f
    public final f<TResult> a(b<TResult> bVar) {
        return a(h.bYP, bVar);
    }

    @Override // com.google.android.gms.f.f
    public final f<TResult> a(Executor executor, a aVar) {
        this.bZd.a(new m(executor, aVar));
        zzdt();
        return this;
    }

    @Override // com.google.android.gms.f.f
    public final f<TResult> a(Executor executor, b<TResult> bVar) {
        this.bZd.a(new o(executor, bVar));
        zzdt();
        return this;
    }

    @Override // com.google.android.gms.f.f
    public final f<TResult> a(Executor executor, c cVar) {
        this.bZd.a(new q(executor, cVar));
        zzdt();
        return this;
    }

    @Override // com.google.android.gms.f.f
    public final f<TResult> a(Executor executor, d<? super TResult> dVar) {
        this.bZd.a(new s(executor, dVar));
        zzdt();
        return this;
    }

    @Override // com.google.android.gms.f.f
    public final boolean agp() {
        boolean z;
        synchronized (this.mLock) {
            z = this.bZe;
        }
        return z;
    }

    @Override // com.google.android.gms.f.f
    public final boolean agq() {
        boolean z;
        synchronized (this.mLock) {
            z = this.bZe && !this.zzfi && this.bZg == null;
        }
        return z;
    }

    public final void g(Exception exc) {
        bf.d(exc, "Exception must not be null");
        synchronized (this.mLock) {
            agr();
            this.bZe = true;
            this.bZg = exc;
        }
        this.bZd.d(this);
    }

    @Override // com.google.android.gms.f.f
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.bZg;
        }
        return exc;
    }

    @Override // com.google.android.gms.f.f
    public final TResult getResult() {
        TResult tresult;
        synchronized (this.mLock) {
            WZ();
            ags();
            if (this.bZg != null) {
                throw new e(this.bZg);
            }
            tresult = this.bZf;
        }
        return tresult;
    }

    public final boolean h(Exception exc) {
        boolean z = true;
        bf.d(exc, "Exception must not be null");
        synchronized (this.mLock) {
            if (this.bZe) {
                z = false;
            } else {
                this.bZe = true;
                this.bZg = exc;
                this.bZd.d(this);
            }
        }
        return z;
    }

    @Override // com.google.android.gms.f.f
    public final boolean isCanceled() {
        return this.zzfi;
    }

    public final boolean p(TResult tresult) {
        boolean z = true;
        synchronized (this.mLock) {
            if (this.bZe) {
                z = false;
            } else {
                this.bZe = true;
                this.bZf = tresult;
                this.bZd.d(this);
            }
        }
        return z;
    }

    public final void setResult(TResult tresult) {
        synchronized (this.mLock) {
            agr();
            this.bZe = true;
            this.bZf = tresult;
        }
        this.bZd.d(this);
    }
}
