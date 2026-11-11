package a;

/* JADX INFO: compiled from: TaskCompletionSource.java */
/* JADX INFO: loaded from: classes.dex */
public class x<TResult> {
    private final o<TResult> zX = new o<>();

    public o<TResult> fF() {
        return this.zX;
    }

    public boolean fE() {
        return this.zX.fE();
    }

    public boolean p(TResult tresult) {
        return this.zX.p(tresult);
    }

    public boolean b(Exception exc) {
        return this.zX.b(exc);
    }

    public void fG() {
        if (!fE()) {
            throw new IllegalStateException("Cannot cancel a completed task.");
        }
    }

    public void setResult(TResult tresult) {
        if (!p(tresult)) {
            throw new IllegalStateException("Cannot set the result of a completed task.");
        }
    }

    public void c(Exception exc) {
        if (!b(exc)) {
            throw new IllegalStateException("Cannot set the error on a completed task.");
        }
    }
}
