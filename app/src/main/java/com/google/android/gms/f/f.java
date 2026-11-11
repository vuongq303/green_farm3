package com.google.android.gms.f;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public abstract class f<TResult> {
    public f<TResult> a(b<TResult> bVar) {
        throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
    }

    public f<TResult> a(Executor executor, a aVar) {
        throw new UnsupportedOperationException("addOnCanceledListener is not implemented");
    }

    public f<TResult> a(Executor executor, b<TResult> bVar) {
        throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
    }

    public abstract f<TResult> a(Executor executor, c cVar);

    public abstract f<TResult> a(Executor executor, d<? super TResult> dVar);

    public abstract boolean agp();

    public abstract boolean agq();

    public abstract Exception getException();

    public abstract TResult getResult();

    public abstract boolean isCanceled();
}
