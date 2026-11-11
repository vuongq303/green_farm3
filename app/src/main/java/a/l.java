package a;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;

/* JADX INFO: compiled from: CancellationTokenSource.java */
/* JADX INFO: loaded from: classes.dex */
public class l implements Closeable {
    private boolean closed;
    private final Object lock;
    private final List<k> zA;
    private ScheduledFuture<?> zB;
    private boolean zC;

    public boolean fw() {
        boolean z;
        synchronized (this.lock) {
            fx();
            z = this.zC;
        }
        return z;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            if (!this.closed) {
                fy();
                Iterator<k> it = this.zA.iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.zA.clear();
                this.closed = true;
            }
        }
    }

    void a(k kVar) {
        synchronized (this.lock) {
            fx();
            this.zA.remove(kVar);
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(fw()));
    }

    private void fx() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void fy() {
        if (this.zB != null) {
            this.zB.cancel(true);
            this.zB = null;
        }
    }
}
