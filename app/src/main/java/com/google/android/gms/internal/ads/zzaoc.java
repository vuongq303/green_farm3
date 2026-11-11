package com.google.android.gms.internal.ads;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zzaoc<V> extends FutureTask<V> implements zzanz<V> {
    private final zzaoa zzcvt;

    zzaoc(Runnable runnable, @Nullable V v) {
        super(runnable, v);
        this.zzcvt = new zzaoa();
    }

    zzaoc(Callable<V> callable) {
        super(callable);
        this.zzcvt = new zzaoa();
    }

    @Override // java.util.concurrent.FutureTask
    protected final void done() {
        this.zzcvt.zzsm();
    }

    @Override // com.google.android.gms.internal.ads.zzanz
    public final void zza(Runnable runnable, Executor executor) {
        this.zzcvt.zza(runnable, executor);
    }
}
