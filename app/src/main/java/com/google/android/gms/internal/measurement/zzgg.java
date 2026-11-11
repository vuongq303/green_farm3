package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class zzgg extends zzhk {
    private static final AtomicLong zzalv = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zzall;
    private zzgk zzalm;
    private zzgk zzaln;
    private final PriorityBlockingQueue<zzgj<?>> zzalo;
    private final BlockingQueue<zzgj<?>> zzalp;
    private final Thread.UncaughtExceptionHandler zzalq;
    private final Thread.UncaughtExceptionHandler zzalr;
    private final Object zzals;
    private final Semaphore zzalt;
    private volatile boolean zzalu;

    zzgg(zzgl zzglVar) {
        super(zzglVar);
        this.zzals = new Object();
        this.zzalt = new Semaphore(2);
        this.zzalo = new PriorityBlockingQueue<>();
        this.zzalp = new LinkedBlockingQueue();
        this.zzalq = new zzgi(this, "Thread death: Uncaught exception on worker thread");
        this.zzalr = new zzgi(this, "Thread death: Uncaught exception on network thread");
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    static /* synthetic */ zzgk zza(zzgg zzggVar, zzgk zzgkVar) {
        zzggVar.zzalm = null;
        return null;
    }

    private final void zza(zzgj<?> zzgjVar) {
        synchronized (this.zzals) {
            this.zzalo.add(zzgjVar);
            if (this.zzalm == null) {
                this.zzalm = new zzgk(this, "Measurement Worker", this.zzalo);
                this.zzalm.setUncaughtExceptionHandler(this.zzalq);
                this.zzalm.start();
            } else {
                this.zzalm.zzjj();
            }
        }
    }

    static /* synthetic */ zzgk zzb(zzgg zzggVar, zzgk zzgkVar) {
        zzggVar.zzaln = null;
        return null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final void zzab() {
        if (Thread.currentThread() != this.zzalm) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final <V> Future<V> zzb(Callable<V> callable) {
        zzch();
        bf.checkNotNull(callable);
        zzgj<?> zzgjVar = new zzgj<>(this, (Callable<?>) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzalm) {
            if (!this.zzalo.isEmpty()) {
                zzgg().zzin().log("Callable skipped the worker queue.");
            }
            zzgjVar.run();
        } else {
            zza(zzgjVar);
        }
        return zzgjVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    public final <V> Future<V> zzc(Callable<V> callable) {
        zzch();
        bf.checkNotNull(callable);
        zzgj<?> zzgjVar = new zzgj<>(this, (Callable<?>) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzalm) {
            zzgjVar.run();
        } else {
            zza(zzgjVar);
        }
        return zzgjVar;
    }

    public final void zzc(Runnable runnable) {
        zzch();
        bf.checkNotNull(runnable);
        zza(new zzgj<>(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzd(Runnable runnable) {
        zzch();
        bf.checkNotNull(runnable);
        zzgj<?> zzgjVar = new zzgj<>(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzals) {
            this.zzalp.add(zzgjVar);
            if (this.zzaln == null) {
                this.zzaln = new zzgk(this, "Measurement Network", this.zzalp);
                this.zzaln.setUncaughtExceptionHandler(this.zzalr);
                this.zzaln.start();
            } else {
                this.zzaln.zzjj();
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final void zzfr() {
        if (Thread.currentThread() != this.zzaln) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    public final boolean zzjg() {
        return Thread.currentThread() == this.zzalm;
    }

    final ExecutorService zzjh() {
        ExecutorService executorService;
        synchronized (this.zzals) {
            if (this.zzall == null) {
                this.zzall = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zzall;
        }
        return executorService;
    }
}
