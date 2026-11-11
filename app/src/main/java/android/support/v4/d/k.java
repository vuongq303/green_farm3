package android.support.v4.d;

import android.os.Handler;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: SelfDestructiveThread.java */
/* JADX INFO: loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ i gg;
    private final /* synthetic */ Handler gh;
    private final /* synthetic */ n gi;
    private final /* synthetic */ Callable val$callable;

    k(i iVar, Callable callable, Handler handler, n nVar) {
        this.gg = iVar;
        this.val$callable = callable;
        this.gh = handler;
        this.gi = nVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object objCall;
        try {
            objCall = this.val$callable.call();
        } catch (Exception e) {
            objCall = null;
        }
        this.gh.post(new l(this, this.gi, objCall));
    }
}
