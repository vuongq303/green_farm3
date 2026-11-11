package com.google.android.gms.internal.ads;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzano {
    public static <V> zzanz<V> zza(zzanz<V> zzanzVar, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        final zzaoj zzaojVar = new zzaoj();
        zza((zzanz) zzaojVar, (Future) zzanzVar);
        final ScheduledFuture<?> scheduledFutureSchedule = scheduledExecutorService.schedule(new Runnable(zzaojVar) { // from class: com.google.android.gms.internal.ads.zzans
            private final zzaoj zzbnu;

            {
                this.zzbnu = zzaojVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzbnu.setException(new TimeoutException());
            }
        }, j, timeUnit);
        zza((zzanz) zzanzVar, zzaojVar);
        zzaojVar.zza(new Runnable(scheduledFutureSchedule) { // from class: com.google.android.gms.internal.ads.zzant
            private final Future zzcvo;

            {
                this.zzcvo = scheduledFutureSchedule;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Future future = this.zzcvo;
                if (future.isDone()) {
                    return;
                }
                future.cancel(true);
            }
        }, zzaoe.zzcvz);
        return zzaojVar;
    }

    public static <A, B> zzanz<B> zza(final zzanz<A> zzanzVar, final zzanj<? super A, ? extends B> zzanjVar, Executor executor) {
        final zzaoj zzaojVar = new zzaoj();
        zzanzVar.zza(new Runnable(zzaojVar, zzanjVar, zzanzVar) { // from class: com.google.android.gms.internal.ads.zzanr
            private final zzaoj zzbnu;
            private final zzanz zzcvm;
            private final zzanj zzcvn;

            {
                this.zzbnu = zzaojVar;
                this.zzcvn = zzanjVar;
                this.zzcvm = zzanzVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzano.zza(this.zzbnu, this.zzcvn, this.zzcvm);
            }
        }, executor);
        zza((zzanz) zzaojVar, (Future) zzanzVar);
        return zzaojVar;
    }

    public static <A, B> zzanz<B> zza(final zzanz<A> zzanzVar, final zzank<A, B> zzankVar, Executor executor) {
        final zzaoj zzaojVar = new zzaoj();
        zzanzVar.zza(new Runnable(zzaojVar, zzankVar, zzanzVar) { // from class: com.google.android.gms.internal.ads.zzanq
            private final zzaoj zzbnu;
            private final zzank zzcvl;
            private final zzanz zzcvm;

            {
                this.zzbnu = zzaojVar;
                this.zzcvl = zzankVar;
                this.zzcvm = zzanzVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzaoj zzaojVar2 = this.zzbnu;
                try {
                    zzaojVar2.set(this.zzcvl.apply(this.zzcvm.get()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    zzaojVar2.setException(e);
                } catch (CancellationException e2) {
                    zzaojVar2.cancel(true);
                } catch (ExecutionException e3) {
                    e = e3;
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        e = cause;
                    }
                    zzaojVar2.setException(e);
                } catch (Exception e4) {
                    zzaojVar2.setException(e4);
                }
            }
        }, executor);
        zza((zzanz) zzaojVar, (Future) zzanzVar);
        return zzaojVar;
    }

    public static <V, X extends Throwable> zzanz<V> zza(final zzanz<? extends V> zzanzVar, final Class<X> cls, final zzanj<? super X, ? extends V> zzanjVar, final Executor executor) {
        final zzaoj zzaojVar = new zzaoj();
        zza((zzanz) zzaojVar, (Future) zzanzVar);
        zzanzVar.zza(new Runnable(zzaojVar, zzanzVar, cls, zzanjVar, executor) { // from class: com.google.android.gms.internal.ads.zzanu
            private final zzaoj zzbnu;
            private final zzanz zzcvk;
            private final Class zzcvp;
            private final zzanj zzcvq;
            private final Executor zzcvr;

            {
                this.zzbnu = zzaojVar;
                this.zzcvk = zzanzVar;
                this.zzcvp = cls;
                this.zzcvq = zzanjVar;
                this.zzcvr = executor;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzano.zza(this.zzbnu, this.zzcvk, this.zzcvp, this.zzcvq, this.zzcvr);
            }
        }, zzaoe.zzcvz);
        return zzaojVar;
    }

    public static <T> T zza(Future<T> future, T t) {
        try {
            return future.get(((Long) zzkb.zzik().zzd(zznk.zzbam)).longValue(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            future.cancel(true);
            zzakb.zzc("InterruptedException caught while resolving future.", e);
            Thread.currentThread().interrupt();
            com.google.android.gms.ads.internal.zzbv.zzeo().zzb(e, "Futures.resolveFuture");
            return t;
        } catch (Exception e2) {
            future.cancel(true);
            zzakb.zzb("Error waiting for future.", e2);
            com.google.android.gms.ads.internal.zzbv.zzeo().zzb(e2, "Futures.resolveFuture");
            return t;
        }
    }

    public static <T> T zza(Future<T> future, T t, long j, TimeUnit timeUnit) {
        try {
            return future.get(j, timeUnit);
        } catch (InterruptedException e) {
            future.cancel(true);
            zzakb.zzc("InterruptedException caught while resolving future.", e);
            Thread.currentThread().interrupt();
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "Futures.resolveFuture");
            return t;
        } catch (Exception e2) {
            future.cancel(true);
            zzakb.zzb("Error waiting for future.", e2);
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e2, "Futures.resolveFuture");
            return t;
        }
    }

    public static <V> void zza(final zzanz<V> zzanzVar, final zzanl<V> zzanlVar, Executor executor) {
        zzanzVar.zza(new Runnable(zzanlVar, zzanzVar) { // from class: com.google.android.gms.internal.ads.zzanp
            private final zzanl zzcvj;
            private final zzanz zzcvk;

            {
                this.zzcvj = zzanlVar;
                this.zzcvk = zzanzVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzanl zzanlVar2 = this.zzcvj;
                try {
                    zzanlVar2.zzh(this.zzcvk.get());
                } catch (InterruptedException e) {
                    e = e;
                    Thread.currentThread().interrupt();
                    zzanlVar2.zzb(e);
                } catch (ExecutionException e2) {
                    e = e2.getCause();
                    zzanlVar2.zzb(e);
                } catch (Exception e3) {
                    e = e3;
                    zzanlVar2.zzb(e);
                }
            }
        }, executor);
    }

    private static <V> void zza(final zzanz<? extends V> zzanzVar, final zzaoj<V> zzaojVar) {
        zza((zzanz) zzaojVar, (Future) zzanzVar);
        zzanzVar.zza(new Runnable(zzaojVar, zzanzVar) { // from class: com.google.android.gms.internal.ads.zzanv
            private final zzaoj zzbnu;
            private final zzanz zzcvk;

            {
                this.zzbnu = zzaojVar;
                this.zzcvk = zzanzVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzaoj zzaojVar2 = this.zzbnu;
                try {
                    zzaojVar2.set(this.zzcvk.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    zzaojVar2.setException(e);
                } catch (ExecutionException e2) {
                    zzaojVar2.setException(e2.getCause());
                } catch (Exception e3) {
                    zzaojVar2.setException(e3);
                }
            }
        }, zzaoe.zzcvz);
    }

    private static <A, B> void zza(final zzanz<A> zzanzVar, final Future<B> future) {
        zzanzVar.zza(new Runnable(zzanzVar, future) { // from class: com.google.android.gms.internal.ads.zzanw
            private final Future zzbnv;
            private final zzanz zzcvs;

            {
                this.zzcvs = zzanzVar;
                this.zzbnv = future;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zzanz zzanzVar2 = this.zzcvs;
                Future future2 = this.zzbnv;
                if (zzanzVar2.isCancelled()) {
                    future2.cancel(true);
                }
            }
        }, zzaoe.zzcvz);
    }

    static final /* synthetic */ void zza(zzaoj zzaojVar, zzanj zzanjVar, zzanz zzanzVar) {
        if (zzaojVar.isCancelled()) {
            return;
        }
        try {
            zza(zzanjVar.zzc(zzanzVar.get()), zzaojVar);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            zzaojVar.setException(e);
        } catch (CancellationException e2) {
            zzaojVar.cancel(true);
        } catch (ExecutionException e3) {
            zzaojVar.setException(e3.getCause());
        } catch (Exception e4) {
            zzaojVar.setException(e4);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0028  */
    /* JADX WARN: Code duplicated, block: B:8:0x0013  */
    static final /* synthetic */ void zza(zzaoj zzaojVar, zzanz zzanzVar, Class cls, zzanj zzanjVar, Executor executor) {
        try {
            zzaojVar.set(zzanzVar.get());
        } catch (InterruptedException e) {
            e = e;
            Thread.currentThread().interrupt();
            if (cls.isInstance(e)) {
                zza(zza(zzi(e), zzanjVar, executor), zzaojVar);
            } else {
                zzaojVar.setException(e);
            }
        } catch (ExecutionException e2) {
            e = e2.getCause();
            if (cls.isInstance(e)) {
                zza(zza(zzi(e), zzanjVar, executor), zzaojVar);
            } else {
                zzaojVar.setException(e);
            }
        } catch (Exception e3) {
            e = e3;
            if (cls.isInstance(e)) {
                zza(zza(zzi(e), zzanjVar, executor), zzaojVar);
            } else {
                zzaojVar.setException(e);
            }
        }
    }

    public static <T> zzanx<T> zzd(Throwable th) {
        return new zzanx<>(th);
    }

    public static <T> zzany<T> zzi(T t) {
        return new zzany<>(t);
    }
}
