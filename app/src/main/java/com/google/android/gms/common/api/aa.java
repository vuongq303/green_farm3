package com.google.android.gms.common.api;

import com.google.android.gms.common.api.ae;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class aa<R extends ae> {
    public void addStatusListener(ab abVar) {
        throw new UnsupportedOperationException();
    }

    public abstract R await();

    public abstract R await(long j, TimeUnit timeUnit);

    public abstract void cancel();

    public abstract boolean isCanceled();

    public abstract void setResultCallback(af<? super R> afVar);

    public abstract void setResultCallback(af<? super R> afVar, long j, TimeUnit timeUnit);

    public <S extends ae> ai<S> then(ah<? super R, ? extends S> ahVar) {
        throw new UnsupportedOperationException();
    }

    public Integer zzo() {
        throw new UnsupportedOperationException();
    }
}
