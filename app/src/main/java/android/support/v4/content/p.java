package android.support.v4.content;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* JADX INFO: Add missing generic type declarations: [Result] */
/* JADX INFO: compiled from: ModernAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class p<Result> extends FutureTask<Result> {
    final /* synthetic */ m fd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    p(m mVar, Callable callable) {
        super(callable);
        this.fd = mVar;
    }

    @Override // java.util.concurrent.FutureTask
    protected void done() {
        try {
            this.fd.f(get());
        } catch (InterruptedException e) {
            Log.w("AsyncTask", e);
        } catch (CancellationException e2) {
            this.fd.f(null);
        } catch (ExecutionException e3) {
            throw new RuntimeException("An error occurred while executing doInBackground()", e3.getCause());
        } catch (Throwable th) {
            throw new RuntimeException("An error occurred while executing doInBackground()", th);
        }
    }
}
