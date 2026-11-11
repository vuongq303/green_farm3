package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: AsyncTaskLoader.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a<D> extends f<D> {
    private final Executor eo;
    volatile a<D>.b ep;
    volatile a<D>.b eq;
    long er;
    long es;
    Handler mHandler;

    public abstract D loadInBackground();

    /* JADX INFO: compiled from: AsyncTaskLoader.java */
    final class b extends m<Void, Void, D> implements Runnable {
        private final CountDownLatch et = new CountDownLatch(1);
        boolean eu;

        b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.support.v4.content.m
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public D doInBackground(Void... voidArr) {
            try {
                return (D) a.this.onLoadInBackground();
            } catch (android.support.v4.c.b e) {
                if (!isCancelled()) {
                    throw e;
                }
                return null;
            }
        }

        @Override // android.support.v4.content.m
        protected void onPostExecute(D d) {
            try {
                a.this.b(this, d);
            } finally {
                this.et.countDown();
            }
        }

        @Override // android.support.v4.content.m
        protected void onCancelled(D d) {
            try {
                a.this.a(this, d);
            } finally {
                this.et.countDown();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.eu = false;
            a.this.aj();
        }
    }

    public a(Context context) {
        this(context, m.THREAD_POOL_EXECUTOR);
    }

    private a(Context context, Executor executor) {
        super(context);
        this.es = -10000L;
        this.eo = executor;
    }

    @Override // android.support.v4.content.f
    protected void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.ep = new b();
        aj();
    }

    @Override // android.support.v4.content.f
    protected boolean onCancelLoad() {
        boolean zCancel = false;
        if (this.ep != null) {
            if (!this.mStarted) {
                this.eH = true;
            }
            if (this.eq != null) {
                if (this.ep.eu) {
                    this.ep.eu = false;
                    this.mHandler.removeCallbacks(this.ep);
                }
                this.ep = null;
            } else if (this.ep.eu) {
                this.ep.eu = false;
                this.mHandler.removeCallbacks(this.ep);
                this.ep = null;
            } else {
                zCancel = this.ep.cancel(false);
                if (zCancel) {
                    this.eq = this.ep;
                    cancelLoadInBackground();
                }
                this.ep = null;
            }
        }
        return zCancel;
    }

    public void onCanceled(D d) {
    }

    void aj() {
        if (this.eq == null && this.ep != null) {
            if (this.ep.eu) {
                this.ep.eu = false;
                this.mHandler.removeCallbacks(this.ep);
            }
            if (this.er > 0 && SystemClock.uptimeMillis() < this.es + this.er) {
                this.ep.eu = true;
                this.mHandler.postAtTime(this.ep, this.es + this.er);
            } else {
                this.ep.a(this.eo, null);
            }
        }
    }

    void a(a<D>.b bVar, D d) {
        onCanceled(d);
        if (this.eq == bVar) {
            rollbackContentChanged();
            this.es = SystemClock.uptimeMillis();
            this.eq = null;
            deliverCancellation();
            aj();
        }
    }

    void b(a<D>.b bVar, D d) {
        if (this.ep != bVar) {
            a((b) bVar, (Object) d);
            return;
        }
        if (isAbandoned()) {
            onCanceled(d);
            return;
        }
        commitContentChanged();
        this.es = SystemClock.uptimeMillis();
        this.ep = null;
        deliverResult(d);
    }

    protected D onLoadInBackground() {
        return loadInBackground();
    }

    public void cancelLoadInBackground() {
    }

    @Override // android.support.v4.content.f
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.ep != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.ep);
            printWriter.print(" waiting=");
            printWriter.println(this.ep.eu);
        }
        if (this.eq != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.eq);
            printWriter.print(" waiting=");
            printWriter.println(this.eq.eu);
        }
        if (this.er != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            android.support.v4.e.t.a(this.er, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            android.support.v4.e.t.a(this.es, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }
}
