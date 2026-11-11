package a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: Task.java */
/* JADX INFO: loaded from: classes.dex */
public class o<TResult> {
    private static volatile w zG;
    private Exception error;
    private TResult result;
    private boolean zH;
    private boolean zI;
    private boolean zJ;
    private y zK;
    public static final ExecutorService zD = g.fs();
    private static final Executor zE = g.ft();
    public static final Executor zF = a.fq();
    private static o<?> zM = new o<>((Object) null);
    private static o<Boolean> zN = new o<>(true);
    private static o<Boolean> zO = new o<>(false);
    private static o<?> zP = new o<>(true);
    private final Object lock = new Object();
    private List<m<TResult, Void>> zL = new ArrayList();

    public static w fz() {
        return zG;
    }

    o() {
    }

    private o(TResult tresult) {
        p(tresult);
    }

    private o(boolean z) {
        if (z) {
            fE();
        } else {
            p(null);
        }
    }

    public static <TResult> o<TResult>.v fA() {
        o oVar = new o();
        oVar.getClass();
        return new v();
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.lock) {
            z = this.zH;
        }
        return z;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.lock) {
            z = this.zI;
        }
        return z;
    }

    public boolean fB() {
        boolean z;
        synchronized (this.lock) {
            z = getError() != null;
        }
        return z;
    }

    public TResult getResult() {
        TResult tresult;
        synchronized (this.lock) {
            tresult = this.result;
        }
        return tresult;
    }

    public Exception getError() {
        Exception exc;
        synchronized (this.lock) {
            if (this.error != null) {
                this.zJ = true;
                if (this.zK != null) {
                    this.zK.fH();
                    this.zK = null;
                }
            }
            exc = this.error;
        }
        return exc;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <TResult> o<TResult> o(TResult tresult) {
        if (tresult == 0) {
            return (o<TResult>) zM;
        }
        if (tresult instanceof Boolean) {
            return ((Boolean) tresult).booleanValue() ? (o<TResult>) zN : (o<TResult>) zO;
        }
        x xVar = new x();
        xVar.setResult(tresult);
        return xVar.fF();
    }

    public static <TResult> o<TResult> a(Exception exc) {
        x xVar = new x();
        xVar.c(exc);
        return xVar.fF();
    }

    public static <TResult> o<TResult> fC() {
        return (o<TResult>) zP;
    }

    public <TContinuationResult> o<TContinuationResult> a(m<TResult, TContinuationResult> mVar, Executor executor, j jVar) {
        boolean zIsCompleted;
        x xVar = new x();
        synchronized (this.lock) {
            zIsCompleted = isCompleted();
            if (!zIsCompleted) {
                this.zL.add(new p(this, xVar, mVar, executor, jVar));
            }
        }
        if (zIsCompleted) {
            a(xVar, mVar, this, executor, jVar);
        }
        return xVar.fF();
    }

    public <TContinuationResult> o<TContinuationResult> a(m<TResult, TContinuationResult> mVar) {
        return a(mVar, zE, null);
    }

    public <TContinuationResult> o<TContinuationResult> a(m<TResult, o<TContinuationResult>> mVar, Executor executor) {
        return b(mVar, executor, null);
    }

    public <TContinuationResult> o<TContinuationResult> b(m<TResult, o<TContinuationResult>> mVar, Executor executor, j jVar) {
        boolean zIsCompleted;
        x xVar = new x();
        synchronized (this.lock) {
            zIsCompleted = isCompleted();
            if (!zIsCompleted) {
                this.zL.add(new q(this, xVar, mVar, executor, jVar));
            }
        }
        if (zIsCompleted) {
            b(xVar, mVar, this, executor, jVar);
        }
        return xVar.fF();
    }

    public <TContinuationResult> o<TContinuationResult> c(m<TResult, TContinuationResult> mVar, Executor executor, j jVar) {
        return a(new r(this, jVar, mVar), executor);
    }

    public <TContinuationResult> o<TContinuationResult> b(m<TResult, TContinuationResult> mVar) {
        return c(mVar, zE, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <TContinuationResult, TResult> void a(x<TContinuationResult> xVar, m<TResult, TContinuationResult> mVar, o<TResult> oVar, Executor executor, j jVar) {
        try {
            executor.execute(new s(jVar, xVar, mVar, oVar));
        } catch (Exception e) {
            xVar.c(new n(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <TContinuationResult, TResult> void b(x<TContinuationResult> xVar, m<TResult, o<TContinuationResult>> mVar, o<TResult> oVar, Executor executor, j jVar) {
        try {
            executor.execute(new t(jVar, xVar, mVar, oVar));
        } catch (Exception e) {
            xVar.c(new n(e));
        }
    }

    private void fD() {
        synchronized (this.lock) {
            Iterator<m<TResult, Void>> it = this.zL.iterator();
            while (it.hasNext()) {
                try {
                    it.next().then(this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            this.zL = null;
        }
    }

    boolean fE() {
        boolean z = true;
        synchronized (this.lock) {
            if (this.zH) {
                z = false;
            } else {
                this.zH = true;
                this.zI = true;
                this.lock.notifyAll();
                fD();
            }
        }
        return z;
    }

    boolean p(TResult tresult) {
        boolean z = true;
        synchronized (this.lock) {
            if (this.zH) {
                z = false;
            } else {
                this.zH = true;
                this.result = tresult;
                this.lock.notifyAll();
                fD();
            }
        }
        return z;
    }

    boolean b(Exception exc) {
        synchronized (this.lock) {
            if (this.zH) {
                return false;
            }
            this.zH = true;
            this.error = exc;
            this.zJ = false;
            this.lock.notifyAll();
            fD();
            if (!this.zJ && fz() != null) {
                this.zK = new y(this);
            }
            return true;
        }
    }

    /* JADX INFO: compiled from: Task.java */
    public class v extends x<TResult> {
        v() {
        }
    }
}
