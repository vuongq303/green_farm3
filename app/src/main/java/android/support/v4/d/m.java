package android.support.v4.d;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: SelfDestructiveThread.java */
/* JADX INFO: loaded from: classes.dex */
class m implements Runnable {
    final /* synthetic */ i gg;
    private final /* synthetic */ AtomicReference gl;
    private final /* synthetic */ ReentrantLock gm;
    private final /* synthetic */ AtomicBoolean gn;
    private final /* synthetic */ Condition go;
    private final /* synthetic */ Callable val$callable;

    m(i iVar, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
        this.gg = iVar;
        this.gl = atomicReference;
        this.val$callable = callable;
        this.gm = reentrantLock;
        this.gn = atomicBoolean;
        this.go = condition;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.gl.set(this.val$callable.call());
        } catch (Exception e) {
        }
        this.gm.lock();
        try {
            this.gn.set(false);
            this.go.signal();
        } finally {
            this.gm.unlock();
        }
    }
}
