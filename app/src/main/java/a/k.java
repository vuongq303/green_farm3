package a;

import java.io.Closeable;

/* JADX INFO: compiled from: CancellationTokenRegistration.java */
/* JADX INFO: loaded from: classes.dex */
public class k implements Closeable {
    private boolean closed;
    private final Object lock;
    private l zy;
    private Runnable zz;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            if (!this.closed) {
                this.closed = true;
                this.zy.a(this);
                this.zy = null;
                this.zz = null;
            }
        }
    }
}
