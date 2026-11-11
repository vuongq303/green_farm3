package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.bf;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
final class zzgj<V> extends FutureTask<V> implements Comparable<zzgj> {
    private final String zzalw;
    private final /* synthetic */ zzgg zzalx;
    private final long zzaly;
    final boolean zzalz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzgj(zzgg zzggVar, Runnable runnable, boolean z, String str) {
        super(runnable, null);
        this.zzalx = zzggVar;
        bf.checkNotNull(str);
        this.zzaly = zzgg.zzalv.getAndIncrement();
        this.zzalw = str;
        this.zzalz = false;
        if (this.zzaly == Long.MAX_VALUE) {
            zzggVar.zzgg().zzil().log("Tasks index overflow");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzgj(zzgg zzggVar, Callable<V> callable, boolean z, String str) {
        super(callable);
        this.zzalx = zzggVar;
        bf.checkNotNull(str);
        this.zzaly = zzgg.zzalv.getAndIncrement();
        this.zzalw = str;
        this.zzalz = z;
        if (this.zzaly == Long.MAX_VALUE) {
            zzggVar.zzgg().zzil().log("Tasks index overflow");
        }
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzgj zzgjVar) {
        zzgj zzgjVar2 = zzgjVar;
        if (this.zzalz != zzgjVar2.zzalz) {
            return this.zzalz ? -1 : 1;
        }
        if (this.zzaly < zzgjVar2.zzaly) {
            return -1;
        }
        if (this.zzaly > zzgjVar2.zzaly) {
            return 1;
        }
        this.zzalx.zzgg().zzim().zzg("Two tasks share the same index. index", Long.valueOf(this.zzaly));
        return 0;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        this.zzalx.zzgg().zzil().zzg(this.zzalw, th);
        if (th instanceof zzgh) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
