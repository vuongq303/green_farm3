package com.google.android.gms.f;

import com.google.android.gms.common.internal.bf;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    public static <TResult> f<TResult> G(TResult tresult) {
        x xVar = new x();
        xVar.setResult(tresult);
        return xVar;
    }

    public static <TResult> f<TResult> a(Executor executor, Callable<TResult> callable) {
        bf.d(executor, "Executor must not be null");
        bf.d(callable, "Callback must not be null");
        x xVar = new x();
        executor.execute(new y(xVar, callable));
        return xVar;
    }

    public static <TResult> TResult a(f<TResult> fVar, long j, TimeUnit timeUnit) throws TimeoutException {
        bf.aaA();
        bf.d(fVar, "Task must not be null");
        bf.d(timeUnit, "TimeUnit must not be null");
        if (fVar.agp()) {
            return (TResult) c(fVar);
        }
        k kVar = new k(null);
        a((f<?>) fVar, (l) kVar);
        if (kVar.await(j, timeUnit)) {
            return (TResult) c(fVar);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    private static void a(f<?> fVar, l lVar) {
        fVar.a(h.bYQ, (d<? super Object>) lVar);
        fVar.a(h.bYQ, (c) lVar);
        fVar.a(h.bYQ, (a) lVar);
    }

    public static <TResult> TResult b(f<TResult> fVar) throws InterruptedException {
        bf.aaA();
        bf.d(fVar, "Task must not be null");
        if (fVar.agp()) {
            return (TResult) c(fVar);
        }
        k kVar = new k(null);
        a((f<?>) fVar, (l) kVar);
        kVar.await();
        return (TResult) c(fVar);
    }

    private static <TResult> TResult c(f<TResult> fVar) throws ExecutionException {
        if (fVar.agq()) {
            return fVar.getResult();
        }
        if (fVar.isCanceled()) {
            throw new CancellationException("Task is already canceled");
        }
        throw new ExecutionException(fVar.getException());
    }

    public static <TResult> f<TResult> i(Exception exc) {
        x xVar = new x();
        xVar.g(exc);
        return xVar;
    }
}
