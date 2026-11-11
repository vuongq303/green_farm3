package com.google.android.gms.internal.ads;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaki {
    public static final zzaod zzcrj = zzaoe.zza(new ThreadPoolExecutor(2, Integer.MAX_VALUE, 10, TimeUnit.SECONDS, new SynchronousQueue(), zzct("Default")));
    private static final zzaod zzcrk;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), zzct("Loader"));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        zzcrk = zzaoe.zza(threadPoolExecutor);
    }

    public static <T> zzanz<T> zza(Callable<T> callable) {
        return zzcrj.submit(callable);
    }

    public static zzanz<?> zzb(Runnable runnable) {
        return zzcrj.submit(runnable);
    }

    public static zzanz<?> zzc(Runnable runnable) {
        return zzcrk.submit(runnable);
    }

    private static ThreadFactory zzct(String str) {
        return new zzakj(str);
    }
}
