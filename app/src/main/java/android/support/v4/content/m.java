package android.support.v4.content;

import android.os.Handler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: ModernAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
abstract class m<Params, Progress, Result> {
    private static r eU;
    private static /* synthetic */ int[] fb;
    private static final ThreadFactory eS = new n();
    private static final BlockingQueue<Runnable> eT = new LinkedBlockingQueue(10);
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, eT, eS);
    private static volatile Executor eV = THREAD_POOL_EXECUTOR;
    private volatile s eY = s.PENDING;
    private final AtomicBoolean eZ = new AtomicBoolean();
    private final AtomicBoolean fa = new AtomicBoolean();
    private final t<Params, Result> eW = new o(this);
    private final FutureTask<Result> eX = new p(this, this.eW);

    protected abstract Result doInBackground(Params... paramsArr);

    static /* synthetic */ int[] al() {
        int[] iArr = fb;
        if (iArr == null) {
            iArr = new int[s.am().length];
            try {
                iArr[s.FINISHED.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[s.PENDING.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[s.RUNNING.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            fb = iArr;
        }
        return iArr;
    }

    private static Handler getHandler() {
        r rVar;
        synchronized (m.class) {
            if (eU == null) {
                eU = new r();
            }
            rVar = eU;
        }
        return rVar;
    }

    void f(Result result) {
        if (!this.fa.get()) {
            g(result);
        }
    }

    Result g(Result result) {
        getHandler().obtainMessage(1, new q(this, result)).sendToTarget();
        return result;
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.eZ.get();
    }

    public final boolean cancel(boolean z) {
        this.eZ.set(true);
        return this.eX.cancel(z);
    }

    public final m<Params, Progress, Result> a(Executor executor, Params... paramsArr) {
        if (this.eY != s.PENDING) {
            switch (al()[this.eY.ordinal()]) {
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 3:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                default:
                    throw new IllegalStateException("We should never reach this state");
            }
        }
        this.eY = s.RUNNING;
        onPreExecute();
        this.eW.mParams = paramsArr;
        executor.execute(this.eX);
        return this;
    }

    void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.eY = s.FINISHED;
    }
}
