package android.support.v4.d;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: SelfDestructiveThread.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    private final int da;
    private HandlerThread gb;
    private final int ge;
    private final String gf;
    private Handler mHandler;
    private final Object mLock = new Object();
    private Handler.Callback gd = new j(this);
    private int gc = 0;

    public i(String str, int i, int i2) {
        this.gf = str;
        this.da = i;
        this.ge = i2;
    }

    private void a(Runnable runnable) {
        synchronized (this.mLock) {
            if (this.gb == null) {
                this.gb = new HandlerThread(this.gf, this.da);
                this.gb.start();
                this.mHandler = new Handler(this.gb.getLooper(), this.gd);
                this.gc++;
            }
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, runnable));
        }
    }

    public <T> void a(Callable<T> callable, n<T> nVar) {
        a(new k(this, callable, new Handler(), nVar));
    }

    public <T> T a(Callable<T> callable, int i) {
        T t;
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionNewCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        a(new m(this, atomicReference, callable, reentrantLock, atomicBoolean, conditionNewCondition));
        reentrantLock.lock();
        try {
            if (!atomicBoolean.get()) {
                t = (T) atomicReference.get();
                reentrantLock.unlock();
            } else {
                long nanos = TimeUnit.MILLISECONDS.toNanos(i);
                do {
                    try {
                        nanos = conditionNewCondition.awaitNanos(nanos);
                    } catch (InterruptedException e) {
                    }
                    if (!atomicBoolean.get()) {
                        t = (T) atomicReference.get();
                        reentrantLock.unlock();
                    }
                } while (nanos > 0);
                throw new InterruptedException("timeout");
            }
            return t;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Runnable runnable) {
        runnable.run();
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), this.ge);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aF() {
        synchronized (this.mLock) {
            if (!this.mHandler.hasMessages(1)) {
                this.gb.quit();
                this.gb = null;
                this.mHandler = null;
            }
        }
    }
}
