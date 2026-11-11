package com.google.android.gms.a;

import android.util.Log;
import java.util.concurrent.FutureTask;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes.dex */
final class s<T> extends FutureTask<T> {
    private final /* synthetic */ o bEM;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    s(o oVar, Runnable runnable, Object obj) {
        super(runnable, obj);
        this.bEM = oVar;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.bEM.bEJ.bEI;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
        } else if (Log.isLoggable("GAv4", 6)) {
            String strValueOf = String.valueOf(th);
            Log.e("GAv4", new StringBuilder(String.valueOf(strValueOf).length() + 37).append("MeasurementExecutor: job failed with ").append(strValueOf).toString());
        }
        super.setException(th);
    }
}
